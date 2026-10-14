/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Internal;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableOptionalOnStep;
import org.jooq.TableOptions;
import org.jooq.Traverser;
import org.jooq.impl.AbstractContext;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Alias;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultMetaProvider;
import org.jooq.impl.DerivedTable;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.ForLock;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.ScopeMappable;
import org.jooq.impl.ScopeNestable;
import org.jooq.impl.SimpleCheckQueryPart;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Internal
public class TableImpl<R extends Record>
extends AbstractTable<R>
implements ScopeMappable,
ScopeNestable,
SimpleCheckQueryPart,
QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES_TABLE_REFERENCE = new Clause[]{Clause.TABLE, Clause.TABLE_REFERENCE};
    private static final Clause[] CLAUSES_TABLE_ALIAS = new Clause[]{Clause.TABLE, Clause.TABLE_ALIAS};
    private static final Set<SQLDialect> NO_SUPPORT_QUALIFIED_TVF_CALLS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> REQUIRES_TVF_TABLE_CONSTRUCTOR = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.ORACLE);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_FULLY_QUALIFIED_FIELDS = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
    final FieldsImpl<R> fields = new FieldsImpl(new SelectField[0]);
    final Alias<Table<R>> alias;
    protected final Field<?>[] parameters;
    final Table<?> child;
    final ForeignKey<?, R> childPath;

    @Deprecated
    public TableImpl(String name) {
        this(DSL.name(name));
    }

    @Deprecated
    public TableImpl(String name, Schema schema) {
        this(DSL.name(name), schema);
    }

    @Deprecated
    public TableImpl(String name, Schema schema, Table<R> aliased) {
        this(DSL.name(name), schema, aliased);
    }

    @Deprecated
    public TableImpl(String name, Schema schema, Table<R> aliased, Field<?>[] parameters) {
        this(DSL.name(name), schema, aliased, parameters);
    }

    @Deprecated
    public TableImpl(String name, Schema schema, Table<R> aliased, Field<?>[] parameters, String comment) {
        this(DSL.name(name), schema, aliased, parameters, comment);
    }

    public TableImpl(Name name) {
        this(name, null, null, null, null, null, null);
    }

    public TableImpl(Name name, Schema schema) {
        this(name, schema, null, null, null, null, null);
    }

    public TableImpl(Name name, Schema schema, Table<R> aliased) {
        this(name, schema, null, null, aliased, null, null);
    }

    public TableImpl(Name name, Schema schema, Table<R> aliased, Field<?>[] parameters) {
        this(name, schema, null, null, aliased, parameters, null);
    }

    @Deprecated
    public TableImpl(Name name, Schema schema, Table<R> aliased, Field<?>[] parameters, String comment) {
        this(name, schema, null, null, aliased, parameters, DSL.comment(comment));
    }

    public TableImpl(Name name, Schema schema, Table<R> aliased, Field<?>[] parameters, Comment comment) {
        this(name, schema, null, null, aliased, parameters, comment);
    }

    public TableImpl(Name name, Schema schema, Table<R> aliased, Field<?>[] parameters, Comment comment, TableOptions options) {
        this(name, schema, null, null, aliased, parameters, comment, options);
    }

    public TableImpl(Table<?> child, ForeignKey<?, R> path, Table<R> parent) {
        this(org.jooq.impl.Internal.createPathAlias(child, path), null, child, path, parent, null, parent.getCommentPart());
    }

    public TableImpl(Name name, Schema schema, Table<?> child, ForeignKey<?, R> path, Table<R> aliased, Field<?>[] parameters, Comment comment) {
        this(name, schema, child, path, aliased, parameters, comment, TableOptions.table());
    }

    public TableImpl(Name name, Schema schema, Table<?> child, ForeignKey<?, R> path, Table<R> aliased, Field<?>[] parameters, Comment comment, TableOptions options) {
        super(options, name, schema, comment);
        Alias<Table<R>> existingAlias;
        if (child != null) {
            this.child = child;
            this.childPath = path == null ? null : Tools.aliasedKey(path, child, this);
        } else if (aliased instanceof TableImpl) {
            TableImpl t2 = (TableImpl)aliased;
            this.child = t2.child;
            this.childPath = t2.childPath == null ? null : Tools.aliasedKey(t2.childPath, t2.child, this);
        } else {
            this.child = null;
            this.childPath = null;
        }
        this.alias = aliased != null ? ((existingAlias = Tools.alias(aliased)) != null ? new Alias<TableImpl>((TableImpl)((Table)existingAlias.wrapped), this, name, existingAlias.fieldAliases, existingAlias.wrapInParentheses) : new Alias<TableImpl>((TableImpl)aliased, this, name)) : null;
        this.parameters = parameters;
    }

    @Nullable
    final Table<?> rootChild() {
        Table<?> table = this.child;
        if (table instanceof TableImpl) {
            TableImpl t2 = (TableImpl)table;
            if (t2.child != null) {
                return t2.rootChild();
            }
            return this.child;
        }
        return null;
    }

    @Nullable
    Table<R> getAliasedTable() {
        if (this.alias != null) {
            return this.alias.wrapped();
        }
        return null;
    }

    @Internal
    protected boolean aliased() {
        return this.getAliasedTable() != null;
    }

    @Internal
    protected boolean isSynthetic() {
        return false;
    }

    @Override
    @Internal
    public final boolean isSimple(Context<?> ctx) {
        return this.alias == null && (this.parameters == null || this.parameters.length < 2);
    }

    @Override
    final FieldsImpl<R> fields0() {
        return this.fields;
    }

    @Override
    public Row fieldsRow() {
        return super.fieldsRow();
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return this.alias != null ? CLAUSES_TABLE_ALIAS : CLAUSES_TABLE_REFERENCE;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            if (DefaultMetaProvider.meta(ctx.configuration()).getTables(this.getQualifiedName()).isEmpty()) {
                ctx.sql("table(").visit(this.getQualifiedName()).sql(")");
            } else {
                ctx.sql(Stream.of(this.getQualifiedName().getName()).map(n -> ((AbstractContext)ctx).applyNameCase((String)n)).collect(Collectors.joining(".")));
            }
            return;
        }
        if ((this.getTableType().isView() || this.getTableType().isFunction()) && this.isSynthetic() && ctx.declareTables()) {
            Select<?> s2 = this.getOptions().select();
            ctx.visit(s2 != null ? s2 : new DerivedTable(ctx.dsl().parser().parseSelect(this.getOptions().source(), this.parameters == null ? Tools.EMPTY_OBJECT : this.parameters)).as(this.getUnqualifiedName()));
            return;
        }
        if (this.child != null) {
            ctx.scopeRegister(this);
        }
        if (this.alias != null) {
            ctx.visit(this.alias);
        } else if (this.parameters != null && REQUIRES_TVF_TABLE_CONSTRUCTOR.contains((Object)ctx.dialect()) && ctx.declareTables()) {
            ctx.visit(Keywords.K_TABLE).sql('(');
            this.accept0(ctx);
            ctx.sql(')');
            if (ctx.declareAliases()) {
                ctx.sql(' ').visit(Tools.getMappedTable(ctx, this).getUnqualifiedName());
            }
        } else {
            this.accept0(ctx);
        }
        if (ctx.declareTables()) {
            ForLock.emulateSQLServerLocking(ctx, this);
        }
    }

    private final void accept0(Context<?> ctx) {
        if (ctx.declareTables()) {
            ctx.scopeMarkStart(this);
        }
        if (ctx.qualify() && (ctx.declareTables() || (!NO_SUPPORT_QUALIFIED_TVF_CALLS.contains((Object)ctx.dialect()) || this.parameters == null) && !NO_SUPPORT_FULLY_QUALIFIED_FIELDS.contains((Object)ctx.dialect()))) {
            QualifiedImpl.acceptMappedSchemaPrefix(ctx, this.getSchema());
        }
        ctx.visit(Tools.getMappedTable(ctx, this).getUnqualifiedName());
        if (this.parameters != null && ctx.declareTables()) {
            if (ctx.family() == SQLDialect.FIREBIRD && this.parameters.length == 0) {
                ctx.visit(QueryPartListView.wrap((QueryPart[])this.parameters));
            } else {
                ctx.sql('(').visit(QueryPartListView.wrap((QueryPart[])this.parameters)).sql(')');
            }
        }
        if (ctx.declareTables()) {
            ctx.scopeMarkEnd(this);
        }
    }

    @Override
    public Table<R> as(Name as) {
        if (this.alias != null) {
            return this.alias.wrapped().as(as);
        }
        return new TableAlias(this, as);
    }

    @Override
    public Table<R> as(Name as, Name ... fieldAliases) {
        if (this.alias != null) {
            return this.alias.wrapped().as(as, fieldAliases);
        }
        return new TableAlias(this, as, fieldAliases);
    }

    public Table<R> rename(String rename) {
        return this.rename(DSL.name(rename));
    }

    public Table<R> rename(Name rename) {
        return new TableImpl<R>(rename, this.getSchema());
    }

    public Table<R> rename(Table<?> rename) {
        return this.rename(rename.getQualifiedName());
    }

    @Override
    public Class<? extends R> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    public boolean declaresTables() {
        return true;
    }

    @Override
    public final TableOptionalOnStep<Record> join(TableLike<?> table, JoinType type) {
        return super.join(table, type);
    }

    @Override
    public Field<?> field(String name) {
        return super.field(name);
    }

    @Override
    public <T> Field<T> field(String name, Class<T> type) {
        return super.field(name, type);
    }

    @Override
    public <T> Field<T> field(String name, DataType<T> dataType) {
        return super.field(name, dataType);
    }

    @Override
    public Field<?> field(Name name) {
        return super.field(name);
    }

    @Override
    public <T> Field<T> field(Name name, Class<T> type) {
        return super.field(name, type);
    }

    @Override
    public <T> Field<T> field(Name name, DataType<T> dataType) {
        return super.field(name, dataType);
    }

    @Override
    public final Table<R> $aliased() {
        return this.alias == null ? this : (Table)this.alias.wrapped;
    }

    @Override
    public final Name $alias() {
        return this.alias == null ? null : this.alias.alias;
    }

    public final <X> X $traverse(Traverser<?, X> traverser) {
        return QOM.traverse(traverser, this, this.getSchema());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.getSchema(), s2 -> new TableImpl<R>(this.getQualifiedName(), (Schema)s2), replacer);
    }

    @Override
    public int hashCode() {
        return StringUtils.defaultIfNull(this.getSchema(), SchemaImpl.DEFAULT_SCHEMA.get()).getQualifiedName().append(this.getUnqualifiedName()).hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof TableImpl) {
            TableImpl t2 = (TableImpl)that;
            return StringUtils.equals(StringUtils.defaultIfNull(this.getSchema(), SchemaImpl.DEFAULT_SCHEMA.get()), StringUtils.defaultIfNull(t2.getSchema(), SchemaImpl.DEFAULT_SCHEMA.get())) && StringUtils.equals(this.getName(), t2.getName()) && Arrays.equals(this.parameters, t2.parameters);
        }
        if (that instanceof TableAlias) {
            TableAlias t3 = (TableAlias)that;
            if (this.$alias() != null) {
                return t3.getUnqualifiedName().equals(this.$alias());
            }
            if (this.getSchema() == null) {
                return t3.getUnqualifiedName().equals(this.getQualifiedName());
            }
        }
        return super.equals(that);
    }
}

