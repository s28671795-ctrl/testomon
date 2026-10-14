/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.jooq.Check;
import org.jooq.Comment;
import org.jooq.Constraint;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.CreateDomainAsStep;
import org.jooq.CreateDomainConstraintStep;
import org.jooq.CreateDomainDefaultStep;
import org.jooq.CreateIndexIncludeStep;
import org.jooq.CreateSequenceFlagsStep;
import org.jooq.CreateTableElementListStep;
import org.jooq.CreateViewAsStep;
import org.jooq.DDLExportConfiguration;
import org.jooq.DDLFlag;
import org.jooq.DSLContext;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Key;
import org.jooq.Meta;
import org.jooq.Named;
import org.jooq.Pro;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Sequence;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.Comparators;
import org.jooq.impl.CreateViewImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.ParserException;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

final class DDL {
    private static final JooqLogger log = JooqLogger.getLogger(DDL.class);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_REDUNDANT_INDEXES = SQLDialect.supportedBy(SQLDialect.ORACLE);
    private final DSLContext ctx;
    private final DDLExportConfiguration configuration;
    static final Pattern P_CREATE_VIEW = Pattern.compile("^(?i:\\bcreate\\b.*?\\bview\\b.*?\\bas\\b\\s+)(.*)$");

    DDL(DSLContext ctx, DDLExportConfiguration configuration) {
        this.ctx = ctx;
        this.configuration = configuration;
    }

    final List<Query> createTableOrView(Table<?> table, Collection<? extends Constraint> constraints) {
        boolean temporary = table.getTableType() == TableOptions.TableType.TEMPORARY;
        boolean view = table.getTableType().isView();
        TableOptions.OnCommit onCommit = table.getOptions().onCommit();
        if (view) {
            ArrayList<Query> result = new ArrayList<Query>();
            result.add(this.applyAs(this.configuration.createViewIfNotExists() ? this.ctx.createViewIfNotExists(table, table.fields()) : (this.configuration.createOrReplaceView() ? this.ctx.createOrReplaceView(table, table.fields()) : this.ctx.createView(table, table.fields())), table.getOptions()));
            if (!constraints.isEmpty() && this.configuration.includeConstraintsOnViews()) {
                result.addAll(this.alterTableAddConstraints(table));
            }
            return result;
        }
        CreateTableElementListStep s0 = (this.configuration.createTableIfNotExists() ? (temporary ? this.ctx.createTemporaryTableIfNotExists(table) : this.ctx.createTableIfNotExists(table)) : (temporary ? this.ctx.createTemporaryTable(table) : this.ctx.createTable(table))).columns(this.sortIf(Tools.map(table.fields(), f -> f.comment("")), !this.configuration.respectColumnOrder())).constraints(constraints);
        if (temporary && onCommit != null) {
            switch (onCommit) {
                case DELETE_ROWS: {
                    return Arrays.asList(s0.onCommitDeleteRows());
                }
                case PRESERVE_ROWS: {
                    return Arrays.asList(s0.onCommitPreserveRows());
                }
                case DROP: {
                    return Arrays.asList(s0.onCommitDrop());
                }
            }
            throw new IllegalStateException("Unsupported flag: " + String.valueOf((Object)onCommit));
        }
        return Arrays.asList(s0);
    }

    private final Query applyAs(CreateViewAsStep q, TableOptions options) {
        if (options.select() != null) {
            return q.as(options.select());
        }
        if (StringUtils.isBlank(options.source())) {
            return q.as("");
        }
        try {
            Query[] queries = this.ctx.parser().parse(options.source()).queries();
            if (queries.length > 0) {
                Query query = queries[0];
                if (query instanceof CreateViewImpl) {
                    CreateViewImpl cv = (CreateViewImpl)query;
                    return q.as(cv.$select());
                }
                query = queries[0];
                if (query instanceof Select) {
                    Select s2 = (Select)query;
                    return q.as(s2);
                }
                return q.as("");
            }
            return q.as("");
        }
        catch (ParserException e) {
            log.info((Object)("Cannot parse view source: " + options.source()), e);
            if (options.source().toLowerCase().startsWith("create")) {
                return q.as(P_CREATE_VIEW.matcher(options.source()).replaceFirst("$1"));
            }
            return q.as(options.source());
        }
    }

    final Query createSequence(Sequence<?> sequence2) {
        CreateSequenceFlagsStep result;
        CreateSequenceFlagsStep createSequenceFlagsStep = result = this.configuration.createSequenceIfNotExists() ? this.ctx.createSequenceIfNotExists(sequence2) : this.ctx.createSequence(sequence2);
        if (sequence2.getStartWith() != null) {
            result = result.startWith(sequence2.getStartWith());
        } else if (this.configuration.defaultSequenceFlags()) {
            result = result.startWith(1);
        }
        if (sequence2.getIncrementBy() != null) {
            result = result.incrementBy(sequence2.getIncrementBy());
        } else if (this.configuration.defaultSequenceFlags()) {
            result = result.incrementBy(1);
        }
        if (sequence2.getMinvalue() != null) {
            result = result.minvalue(sequence2.getMinvalue());
        } else if (this.configuration.defaultSequenceFlags()) {
            result = result.noMinvalue();
        }
        if (sequence2.getMaxvalue() != null) {
            result = result.maxvalue(sequence2.getMaxvalue());
        } else if (this.configuration.defaultSequenceFlags()) {
            result = result.noMaxvalue();
        }
        if (sequence2.getCycle()) {
            result = result.cycle();
        } else if (this.configuration.defaultSequenceFlags()) {
            result = result.noCycle();
        }
        if (sequence2.getCache() != null) {
            result = result.cache(sequence2.getCache());
        } else if (this.configuration.defaultSequenceFlags()) {
            result = result.noCache();
        }
        return result;
    }

    final Query createDomain(Domain<?> domain) {
        CreateDomainDefaultStep s3;
        CreateDomainAsStep s1 = this.configuration.createDomainIfNotExists() ? this.ctx.createDomainIfNotExists(domain) : this.ctx.createDomain(domain);
        CreateDomainDefaultStep s2 = s1.as(domain.getDataType());
        CreateDomainConstraintStep createDomainConstraintStep = s3 = domain.getDataType().defaulted() ? s2.default_(domain.getDataType().default_()) : s2;
        if (domain.getChecks().isEmpty()) {
            return s3;
        }
        return s3.constraints(Tools.map(domain.getChecks(), c -> c.constraint()));
    }

    final List<Query> createTableOrView(Table<?> table) {
        return this.createTableOrView(table, this.constraints(table));
    }

    final List<Query> createIndex(Table<?> table) {
        ArrayList<Query> result = new ArrayList<Query>();
        if (this.configuration.flags().contains((Object)DDLFlag.INDEX)) {
            for (Index i : this.sortIf(table.getIndexes(), !this.configuration.respectIndexOrder())) {
                result.add(this.createIndex(i));
            }
        }
        return result;
    }

    final Query createIndex(Index i) {
        CreateIndexIncludeStep s1 = (this.configuration.createIndexIfNotExists() ? (i.getUnique() ? this.ctx.createUniqueIndexIfNotExists(i) : this.ctx.createIndexIfNotExists(i)) : (i.getUnique() ? this.ctx.createUniqueIndex(i) : this.ctx.createIndex(i))).on(i.getTable(), i.getFields());
        return i.getWhere() != null ? s1.where(i.getWhere()) : s1;
    }

    final List<Query> alterTableAddConstraints(Table<?> table) {
        return this.alterTableAddConstraints(table, this.constraints(table));
    }

    final List<Query> alterTableAddConstraints(Table<?> table, List<Constraint> constraints) {
        return Tools.map(constraints, c -> this.ctx.alterTable(table).add((Constraint)c));
    }

    final List<Constraint> constraints(Table<?> table) {
        ArrayList<Constraint> result = new ArrayList<Constraint>();
        result.addAll(this.primaryKeys(table));
        result.addAll(this.uniqueKeys(table));
        result.addAll(this.foreignKeys(table));
        result.addAll(this.checks(table));
        return result;
    }

    final List<Constraint> primaryKeys(Table<?> table) {
        ArrayList<Constraint> result = new ArrayList<Constraint>();
        if (this.configuration.flags().contains((Object)DDLFlag.PRIMARY_KEY) && (table.getTableType() != TableOptions.TableType.VIEW || this.configuration.includeConstraintsOnViews())) {
            for (UniqueKey<?> key : table.getKeys()) {
                if (!key.isPrimary()) continue;
                result.add(this.enforced(DSL.constraint(key.getUnqualifiedName()).primaryKey(key.getFieldsArray()), key.enforced()));
            }
        }
        return result;
    }

    final List<Constraint> uniqueKeys(Table<?> table) {
        ArrayList<Constraint> result = new ArrayList<Constraint>();
        if (this.configuration.flags().contains((Object)DDLFlag.UNIQUE) && (table.getTableType() != TableOptions.TableType.VIEW || this.configuration.includeConstraintsOnViews())) {
            for (UniqueKey<?> key : this.sortKeysIf(table.getKeys(), !this.configuration.respectConstraintOrder())) {
                if (key.isPrimary()) continue;
                result.add(this.enforced(DSL.constraint(key.getUnqualifiedName()).unique(key.getFieldsArray()), key.enforced()));
            }
        }
        return result;
    }

    final List<Constraint> foreignKeys(Table<?> table) {
        ArrayList<Constraint> result = new ArrayList<Constraint>();
        if (this.configuration.flags().contains((Object)DDLFlag.FOREIGN_KEY) && (table.getTableType() != TableOptions.TableType.VIEW || this.configuration.includeConstraintsOnViews())) {
            for (ForeignKey<?, ?> key : this.sortKeysIf(table.getReferences(), !this.configuration.respectConstraintOrder())) {
                result.add(this.enforced(DSL.constraint(key.getUnqualifiedName()).foreignKey(key.getFieldsArray()).references(key.getKey().getTable(), key.getKeyFieldsArray()), key.enforced()));
            }
        }
        return result;
    }

    final List<Constraint> checks(Table<?> table) {
        ArrayList<Constraint> result = new ArrayList<Constraint>();
        if (this.configuration.flags().contains((Object)DDLFlag.CHECK) && (table.getTableType() != TableOptions.TableType.VIEW || this.configuration.includeConstraintsOnViews())) {
            for (Check<?> check2 : this.sortIf(table.getChecks(), !this.configuration.respectConstraintOrder())) {
                result.add(this.enforced(DSL.constraint(check2.getUnqualifiedName()).check(check2.condition()), check2.enforced()));
            }
        }
        return result;
    }

    final Queries queries(Table<?> ... tables) {
        ArrayList<Query> queries = new ArrayList<Query>();
        for (Table<?> table : tables) {
            if (this.configuration.flags().contains((Object)DDLFlag.TABLE)) {
                queries.addAll(this.createTableOrView(table));
            } else {
                queries.addAll(this.alterTableAddConstraints(table));
            }
            queries.addAll(this.createIndex(table));
            queries.addAll(this.commentOn(table));
        }
        return this.ctx.queries(queries);
    }

    final List<Query> commentOn(Table<?> table) {
        ArrayList<Query> result = new ArrayList<Query>();
        if (this.configuration.flags().contains((Object)DDLFlag.COMMENT)) {
            Comment tComment = table.getCommentPart();
            if (!StringUtils.isEmpty(tComment.getComment())) {
                if (table.getTableType().isView()) {
                    result.add(this.ctx.commentOnView(table).is(tComment));
                } else {
                    result.add(this.ctx.commentOnTable(table).is(tComment));
                }
            }
            for (Field<?> field2 : this.sortIf(Arrays.asList(table.fields()), !this.configuration.respectColumnOrder())) {
                Comment fComment = field2.getCommentPart();
                if (StringUtils.isEmpty(fComment.getComment())) continue;
                result.add(this.ctx.commentOnColumn(field2).is(fComment));
            }
        }
        return result;
    }

    final Queries queries(Meta meta) {
        ArrayList<Query> queries = new ArrayList<Query>();
        List<Schema> schemas = this.sortIf(meta.getSchemas(), !this.configuration.respectSchemaOrder());
        for (Schema schema : schemas) {
            if (!this.configuration.flags().contains((Object)DDLFlag.SCHEMA) || schema.getUnqualifiedName().empty()) continue;
            if (this.configuration.createSchemaIfNotExists()) {
                queries.add(this.ctx.createSchemaIfNotExists(schema.getUnqualifiedName()));
                continue;
            }
            queries.add(this.ctx.createSchema(schema.getUnqualifiedName()));
        }
        HashSet tablesWithInlineConstraints = new HashSet();
        if (this.configuration.flags().contains((Object)DDLFlag.TABLE)) {
            for (Schema schema : schemas) {
                for (Table<?> table : this.sortTablesIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
                    if (!this.hasConstraintsUsingIndexes(table)) {
                        tablesWithInlineConstraints.add(table);
                        constraints.addAll(this.primaryKeys(table));
                        constraints.addAll(this.uniqueKeys(table));
                        constraints.addAll(this.checks(table));
                    }
                    queries.addAll(this.createTableOrView(table, constraints));
                }
            }
        }
        if (this.configuration.flags().contains((Object)DDLFlag.INDEX)) {
            for (Schema schema : schemas) {
                for (Table<?> table : this.sortIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                    queries.addAll(this.createIndex(table));
                }
            }
        }
        for (Schema schema : schemas) {
            if (this.configuration.flags().contains((Object)DDLFlag.PRIMARY_KEY)) {
                for (Table<?> table : this.sortIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                    if (tablesWithInlineConstraints.contains(table)) continue;
                    for (Constraint constraint : this.sortIf(this.primaryKeys(table), !this.configuration.respectConstraintOrder())) {
                        queries.add(this.ctx.alterTable(table).add(constraint));
                    }
                }
            }
            if (this.configuration.flags().contains((Object)DDLFlag.UNIQUE)) {
                for (Table<?> table : this.sortIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                    if (tablesWithInlineConstraints.contains(table)) continue;
                    for (Constraint constraint : this.sortIf(this.uniqueKeys(table), !this.configuration.respectConstraintOrder())) {
                        queries.add(this.ctx.alterTable(table).add(constraint));
                    }
                }
            }
            if (!this.configuration.flags().contains((Object)DDLFlag.CHECK)) continue;
            for (Table table : this.sortIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                if (tablesWithInlineConstraints.contains(table)) continue;
                for (Constraint constraint : this.sortIf(this.checks(table), !this.configuration.respectConstraintOrder())) {
                    queries.add(this.ctx.alterTable(table).add(constraint));
                }
            }
        }
        if (this.configuration.flags().contains((Object)DDLFlag.FOREIGN_KEY)) {
            for (Schema schema : schemas) {
                for (Table<?> table : this.sortIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                    for (Constraint constraint : this.foreignKeys(table)) {
                        queries.add(this.ctx.alterTable(table).add(constraint));
                    }
                }
            }
        }
        if (this.configuration.flags().contains((Object)DDLFlag.DOMAIN)) {
            for (Schema schema : schemas) {
                for (Domain domain : this.sortIf(schema.getDomains(), !this.configuration.respectDomainOrder())) {
                    queries.add(this.createDomain(domain));
                }
            }
        }
        if (this.configuration.flags().contains((Object)DDLFlag.SEQUENCE)) {
            for (Schema schema : schemas) {
                for (Sequence sequence2 : this.sortIf(schema.getSequences(), !this.configuration.respectSequenceOrder())) {
                    queries.add(this.createSequence(sequence2));
                }
            }
        }
        if (this.configuration.flags().contains((Object)DDLFlag.COMMENT)) {
            for (Schema schema : schemas) {
                for (Table table : this.sortIf(schema.getTables(), !this.configuration.respectTableOrder())) {
                    queries.addAll(this.commentOn(table));
                }
            }
        }
        return this.ctx.queries(queries);
    }

    private final <R extends Record> boolean hasConstraintsUsingIndexes(Table<R> table) {
        if (NO_SUPPORT_REDUNDANT_INDEXES.contains((Object)this.ctx.dialect()) && this.configuration.flags().contains((Object)DDLFlag.INDEX)) {
            List<UniqueKey<R>> keys2 = table.getKeys();
            List<Index> indexes = table.getIndexes();
            for (UniqueKey<R> key : keys2) {
                for (Index index : indexes) {
                    if (!key.getFields().equals(Tools.map(index.getFields(), s2 -> s2.$sortOrder() != SortOrder.DESC ? s2.$field() : null))) continue;
                    return true;
                }
            }
        }
        return false;
    }

    private final <K extends Key<?>> List<K> sortKeysIf(List<K> input, boolean sort) {
        if (sort) {
            ArrayList<K> result = new ArrayList<K>(input);
            result.sort(Comparators.KEY_COMP);
            result.sort(Comparators.NAMED_COMP);
            return result;
        }
        return input;
    }

    private final <N extends Named> List<N> sortIf(List<N> input, boolean sort) {
        if (sort) {
            ArrayList<N> result = new ArrayList<N>(input);
            result.sort(Comparators.NAMED_COMP);
            return result;
        }
        return input;
    }

    private final <T extends Table<?>> List<T> sortTablesIf(List<T> input, boolean sort) {
        if (sort) {
            ArrayList<T> result = new ArrayList<T>(input);
            result.sort(Comparators.NAMED_COMP);
            result.sort(Comparators.TABLE_VIEW_COMP);
            return result;
        }
        return input;
    }

    private final Constraint enforced(ConstraintEnforcementStep check2, boolean enforced) {
        return enforced ? check2 : check2.notEnforced();
    }
}

