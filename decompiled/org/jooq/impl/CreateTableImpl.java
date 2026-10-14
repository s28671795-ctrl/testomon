/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateTableCommentStep;
import org.jooq.CreateTableElementListStep;
import org.jooq.CreateTableFinalStep;
import org.jooq.CreateTableOnCommitStep;
import org.jooq.CreateTableStorageStep;
import org.jooq.CreateTableWithDataStep;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Function9;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Nullability;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.ConstraintImpl;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.Tools;

final class CreateTableImpl
extends AbstractDDLQuery
implements QOM.CreateTable,
CreateTableElementListStep,
CreateTableAsStep,
CreateTableWithDataStep,
CreateTableOnCommitStep,
CreateTableCommentStep,
CreateTableStorageStep,
CreateTableFinalStep {
    final Table<?> table;
    final boolean temporary;
    final boolean ifNotExists;
    QueryPartListView<? extends TableElement> tableElements;
    Select<?> select;
    QOM.WithOrWithoutData withData;
    QOM.TableCommitAction onCommit;
    Comment comment;
    SQL storage;
    static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> NO_SUPPORT_WITH_DATA = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    static final Set<SQLDialect> NO_SUPPORT_CTAS_COLUMN_NAMES = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.H2);
    static final Set<SQLDialect> EMULATE_INDEXES_IN_BLOCK = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_SOME_ENUM_TYPES_AS_CHECK = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_STORED_ENUM_TYPES_AS_CHECK = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE);
    static final Set<SQLDialect> REQUIRES_WITH_DATA = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.TERADATA);
    static final Set<SQLDialect> WRAP_SELECT_IN_PARENS = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.TERADATA);
    static final Set<SQLDialect> SUPPORT_TEMPORARY = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_TABLE_COMMENT_IN_BLOCK = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_COLUMN_COMMENT_IN_BLOCK = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> REQUIRE_EXECUTE_IMMEDIATE = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.ORACLE);
    static final Set<SQLDialect> NO_SUPPORT_NULLABLE_PRIMARY_KEY = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    static final Set<SQLDialect> REQUIRE_NON_PK_COLUMNS = SQLDialect.supportedBy(SQLDialect.IGNITE, SQLDialect.VERTICA);
    @Pro
    static final Set<SQLDialect> EMULATE_CTAS_USING_SELECT_INTO = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);

    CreateTableImpl(Configuration configuration, Table<?> table, boolean temporary, boolean ifNotExists) {
        this(configuration, table, temporary, ifNotExists, null, null, null, null, null, null);
    }

    CreateTableImpl(Configuration configuration, Table<?> table, boolean temporary, boolean ifNotExists, Collection<? extends TableElement> tableElements, Select<?> select, QOM.WithOrWithoutData withData, QOM.TableCommitAction onCommit, Comment comment, SQL storage) {
        super(configuration);
        this.table = table;
        this.temporary = temporary;
        this.ifNotExists = ifNotExists;
        this.tableElements = new QueryPartList<TableElement>((Iterable<? extends TableElement>)tableElements);
        this.select = select;
        this.withData = withData;
        this.onCommit = onCommit;
        this.comment = comment;
        this.storage = storage;
    }

    @Override
    public final CreateTableImpl tableElements(TableElement ... tableElements) {
        return this.tableElements(Arrays.asList(tableElements));
    }

    @Override
    public final CreateTableImpl tableElements(Collection<? extends TableElement> tableElements) {
        if (this.tableElements == null) {
            this.tableElements = new QueryPartList<TableElement>((Iterable<? extends TableElement>)tableElements);
        } else {
            this.tableElements.addAll(tableElements);
        }
        return this;
    }

    @Override
    public final CreateTableImpl columns(String ... columns) {
        return this.columns((Field[])Tools.fieldsByName(columns));
    }

    @Override
    public final CreateTableImpl columns(Name ... columns) {
        return this.columns((Field[])Tools.fieldsByName(columns));
    }

    @Override
    public final CreateTableImpl columns(Field<?> ... columns) {
        return this.columns(Arrays.asList(columns));
    }

    @Override
    public final CreateTableImpl columns(Collection<? extends Field<?>> columns) {
        return this.tableElements((Collection)new QueryPartList((Iterable<? extends Field<?>>)columns));
    }

    @Override
    public final CreateTableImpl column(Field<?> column) {
        return this.tableElements(column);
    }

    @Override
    public final CreateTableImpl column(String field2, DataType<?> type) {
        return this.column(DSL.name(field2), (DataType)type);
    }

    @Override
    public final CreateTableImpl column(Name field2, DataType<?> type) {
        return this.tableElements(DSL.field(field2, type));
    }

    @Override
    public final CreateTableImpl column(Field<?> field2, DataType<?> type) {
        return this.tableElements(DSL.field(field2.getQualifiedName(), type));
    }

    @Override
    public final CreateTableImpl constraints(Constraint ... constraints) {
        return this.constraints(Arrays.asList(constraints));
    }

    @Override
    public final CreateTableImpl constraints(Collection<? extends Constraint> constraints) {
        return this.tableElements(new QueryPartList<Constraint>((Iterable<? extends Constraint>)constraints));
    }

    @Override
    public final CreateTableImpl constraint(Constraint constraint) {
        return this.tableElements(constraint);
    }

    @Override
    public final CreateTableImpl primaryKey(String ... fields) {
        return this.primaryKey((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final CreateTableImpl primaryKey(Name ... fields) {
        return this.primaryKey((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final CreateTableImpl primaryKey(Field<?> ... fields) {
        return this.primaryKey(Arrays.asList(fields));
    }

    @Override
    public final CreateTableImpl primaryKey(Collection<? extends Field<?>> fields) {
        return this.tableElements(DSL.primaryKey(new QueryPartList((Iterable<? extends Field<?>>)fields)));
    }

    @Override
    public final CreateTableImpl unique(String ... fields) {
        return this.unique((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final CreateTableImpl unique(Name ... fields) {
        return this.unique((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final CreateTableImpl unique(Field<?> ... fields) {
        return this.unique(Arrays.asList(fields));
    }

    @Override
    public final CreateTableImpl unique(Collection<? extends Field<?>> fields) {
        return this.tableElements(DSL.unique(new QueryPartList((Iterable<? extends Field<?>>)fields)));
    }

    @Override
    public final CreateTableImpl check(Condition condition) {
        return this.tableElements(DSL.check(condition));
    }

    @Override
    public final CreateTableImpl indexes(Index ... indexes) {
        return this.indexes(Arrays.asList(indexes));
    }

    @Override
    public final CreateTableImpl indexes(Collection<? extends Index> indexes) {
        return this.tableElements(new QueryPartList<Index>((Iterable<? extends Index>)indexes));
    }

    @Override
    public final CreateTableImpl index(Index index) {
        return this.tableElements(index);
    }

    @Override
    public final CreateTableImpl as(Select<?> select) {
        this.select = select;
        return this;
    }

    @Override
    public final CreateTableImpl withData() {
        this.withData = QOM.WithOrWithoutData.WITH_DATA;
        return this;
    }

    @Override
    public final CreateTableImpl withNoData() {
        this.withData = QOM.WithOrWithoutData.WITH_NO_DATA;
        return this;
    }

    @Override
    public final CreateTableImpl onCommitDeleteRows() {
        this.onCommit = QOM.TableCommitAction.DELETE_ROWS;
        return this;
    }

    @Override
    public final CreateTableImpl onCommitPreserveRows() {
        this.onCommit = QOM.TableCommitAction.PRESERVE_ROWS;
        return this;
    }

    @Override
    public final CreateTableImpl onCommitDrop() {
        this.onCommit = QOM.TableCommitAction.DROP;
        return this;
    }

    @Override
    public final CreateTableImpl comment(String comment) {
        return this.comment(DSL.comment(comment));
    }

    @Override
    public final CreateTableImpl comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public final CreateTableImpl storage(SQL storage) {
        this.storage = storage;
        return this;
    }

    @Override
    public final CreateTableImpl storage(String storage, QueryPart ... parts) {
        return this.storage(DSL.sql(storage, parts));
    }

    @Override
    public final CreateTableImpl storage(String storage, Object ... bindings) {
        return this.storage(DSL.sql(storage, bindings));
    }

    @Override
    public final CreateTableImpl storage(String storage) {
        return this.storage(DSL.sql(storage));
    }

    final QOM.UnmodifiableList<? extends Field<?>> $columns() {
        return QOM.unmodifiable(this.tableElements.stream().filter(e -> e instanceof Field).map(e -> (Field)e).collect(Collectors.toList()));
    }

    final QOM.UnmodifiableList<? extends Constraint> $constraints() {
        return QOM.unmodifiable(this.tableElements.stream().filter(e -> e instanceof Constraint).map(e -> (Constraint)e).collect(Collectors.toList()));
    }

    final QOM.UnmodifiableList<? extends Index> $indexes() {
        return QOM.unmodifiable(this.tableElements.stream().filter(e -> e instanceof Index).map(e -> (Index)e).collect(Collectors.toList()));
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_TABLE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        boolean bi;
        boolean btc = this.comment != null && EMULATE_TABLE_COMMENT_IN_BLOCK.contains((Object)ctx.dialect());
        boolean bcc = EMULATE_COLUMN_COMMENT_IN_BLOCK.contains((Object)ctx.dialect()) && Tools.anyMatch(this.$columns(), c -> !c.getComment().isEmpty());
        boolean bl = bi = !this.$indexes().isEmpty() && EMULATE_INDEXES_IN_BLOCK.contains((Object)ctx.dialect());
        if (btc || bcc || bi) {
            Tools.begin(ctx, c1 -> {
                Tools.executeImmediateIf(REQUIRE_EXECUTE_IMMEDIATE.contains((Object)c1.dialect()), c1, c2 -> this.accept1((Context<?>)c2));
                if (btc) {
                    c1.formatSeparator();
                    Tools.executeImmediateIf(REQUIRE_EXECUTE_IMMEDIATE.contains((Object)ctx.dialect()), c1, c2 -> c2.visit(DSL.commentOnTable(this.table).is(this.comment)));
                }
                if (bcc) {
                    c1.formatSeparator();
                    for (Field field2 : this.$columns()) {
                        if (field2.getComment().isEmpty()) continue;
                        Tools.executeImmediateIf(REQUIRE_EXECUTE_IMMEDIATE.contains((Object)ctx.dialect()), c1, c2 -> c2.visit(DSL.commentOnColumn(this.table.getQualifiedName().append(c.getUnqualifiedName())).is(c.getComment())));
                    }
                }
                if (bi) {
                    for (Index index : this.$indexes()) {
                        c1.formatSeparator();
                        Tools.executeImmediateIf(REQUIRE_EXECUTE_IMMEDIATE.contains((Object)c1.dialect()), c1, c2 -> {
                            if ("".equals(index.getName())) {
                                c2.visit(DSL.createIndex().on(index.getTable(), index.getFields()));
                            } else {
                                c2.visit(DSL.createIndex(index.getUnqualifiedName()).on(index.getTable(), index.getFields()));
                            }
                        });
                    }
                }
            });
        } else {
            this.accept1(ctx);
        }
    }

    private final void accept1(Context<?> ctx) {
        ctx.start(Clause.CREATE_TABLE);
        if (this.select != null) {
            if (EMULATE_CTAS_USING_SELECT_INTO.contains((Object)ctx.dialect())) {
                this.acceptSelectInto(ctx);
            } else if (ctx.family() == SQLDialect.DB2) {
                this.acceptCreateTableAsSelectDB2(ctx);
            } else {
                this.acceptCreateTableAsSelect(ctx);
            }
        } else {
            this.toSQLCreateTable(ctx);
            this.toSQLOnCommit(ctx);
        }
        if (this.comment != null && !EMULATE_TABLE_COMMENT_IN_BLOCK.contains((Object)ctx.dialect())) {
            ctx.formatSeparator().visit(Keywords.K_COMMENT).sql(' ');
            if (ctx.family() == SQLDialect.EXASOL) {
                ctx.visit(Keywords.K_IS).sql(' ');
            } else if (ctx.family() == SQLDialect.SNOWFLAKE) {
                ctx.sql(" = ");
            }
            ctx.visit(this.comment);
        }
        if (this.storage != null && ctx.configuration().data("org.jooq.ddl.ignore-storage-clauses") == null) {
            ctx.formatSeparator().visit(this.storage);
        }
        ctx.end(Clause.CREATE_TABLE);
    }

    private void toSQLCreateTable(Context<?> ctx) {
        this.toSQLCreateTableName(ctx);
        QOM.UnmodifiableList<Field<?>> columns = this.$columns();
        if (!(columns.isEmpty() || this.select != null && NO_SUPPORT_CTAS_COLUMN_NAMES.contains((Object)ctx.dialect()))) {
            DataType<Object> type;
            ctx.sqlIndentStart(" (").start(Clause.CREATE_TABLE_COLUMNS);
            Field identity = null;
            boolean qualify = ctx.qualify();
            boolean first = true;
            for (int i = 0; i < columns.size(); ++i) {
                Field field2 = (Field)columns.get(i);
                type = this.columnType(ctx, field2);
                if (type.computedOnClientVirtual(ctx.configuration())) continue;
                if (identity == null && type.identity()) {
                    identity = field2;
                }
                if (!first) {
                    ctx.sql(',').formatSeparator();
                }
                ctx.qualify(false);
                ctx.visit(Tools.uncollate(field2));
                ctx.qualify(qualify);
                if (this.select == null) {
                    ctx.sql(' ');
                    Tools.toSQLDDLTypeDeclarationForAddition(ctx, type);
                    CreateTableImpl.acceptColumnComment(ctx, field2);
                }
                first = false;
            }
            this.toSQLDummyColumns(ctx);
            ctx.end(Clause.CREATE_TABLE_COLUMNS).start(Clause.CREATE_TABLE_CONSTRAINTS);
            for (Constraint constraint : this.$constraints()) {
                if (!((ConstraintImpl)constraint).supported(ctx) || ctx.family() == SQLDialect.SQLITE && this.matchingPrimaryKey(constraint, identity)) continue;
                ctx.sql(',').formatSeparator().visit(constraint);
            }
            if (EMULATE_SOME_ENUM_TYPES_AS_CHECK.contains((Object)ctx.dialect())) {
                for (Field field3 : this.$columns()) {
                    type = field3.getDataType();
                    if (!EnumType.class.isAssignableFrom(type.getType())) continue;
                    DataType<Object> enumType = type;
                    if (!EMULATE_STORED_ENUM_TYPES_AS_CHECK.contains((Object)ctx.dialect()) && Tools.storedEnumType(enumType)) continue;
                    List<Field> literals = Tools.map(Tools.enums(enumType.getType()), e -> DSL.inline(e.getLiteral()));
                    ctx.sql(',').formatSeparator().visit(DSL.constraint(this.table.getName() + "_" + field3.getName() + "_chk").check(field3.in((Collection<?>)literals)));
                }
            }
            ctx.end(Clause.CREATE_TABLE_CONSTRAINTS);
            if (!this.$indexes().isEmpty() && !EMULATE_INDEXES_IN_BLOCK.contains((Object)ctx.dialect())) {
                ctx.qualify(false);
                for (Index index : this.$indexes()) {
                    ctx.sql(',').formatSeparator();
                    if (index.getUnique()) {
                        ctx.visit(Keywords.K_UNIQUE).sql(' ');
                    }
                    ctx.visit(Keywords.K_INDEX);
                    if (!"".equals(index.getName())) {
                        ctx.sql(' ').visit(index.getUnqualifiedName());
                    }
                    ctx.sql(" (").visit(new SortFieldList(index.getFields())).sql(')');
                }
                ctx.qualify(qualify);
            }
            ctx.sqlIndentEnd(')');
        }
    }

    static void acceptColumnComment(Context<?> ctx, Field<?> field2) {
        if (!field2.getComment().isEmpty() && !EMULATE_COLUMN_COMMENT_IN_BLOCK.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_COMMENT).sql(' ').visit(DSL.inline(field2.getComment()));
        }
    }

    private final void toSQLDummyColumns(Context<?> ctx) {
        Field<?>[] primaryKeyColumns;
        if (REQUIRE_NON_PK_COLUMNS.contains((Object)ctx.dialect()) && (primaryKeyColumns = this.primaryKeyColumns()) != null && primaryKeyColumns.length == this.$columns().size()) {
            ctx.sql(',').formatSeparator();
            ctx.visit(DSL.field(DSL.name("dummy")));
            if (this.select == null) {
                ctx.sql(' ');
                Tools.toSQLDDLTypeDeclarationForAddition(ctx, SQLDataType.INTEGER);
            }
        }
    }

    private final DataType<?> columnType(Context<?> ctx, Field<?> field2) {
        DataType type = field2.getDataType();
        if (NO_SUPPORT_NULLABLE_PRIMARY_KEY.contains((Object)ctx.dialect()) && type.nullability() == Nullability.DEFAULT && this.isPrimaryKey(field2)) {
            type = type.nullable(false);
        }
        return type;
    }

    private final Field<?>[] primaryKeyColumns() {
        return Tools.findAny(this.$constraints(), c -> c instanceof ConstraintImpl && ((ConstraintImpl)c).$primaryKey() != null, c -> ((ConstraintImpl)c).$primaryKey());
    }

    private final boolean isPrimaryKey(Field<?> field2) {
        return Tools.anyMatch(this.primaryKeyColumns(), field2::equals);
    }

    private final boolean matchingPrimaryKey(Constraint constraint, Field<?> identity) {
        if (constraint instanceof ConstraintImpl) {
            ConstraintImpl c = (ConstraintImpl)constraint;
            return c.matchingPrimaryKey(identity);
        }
        return false;
    }

    private final void acceptCreateTableAsSelect(Context<?> ctx) {
        this.toSQLCreateTable(ctx);
        this.toSQLOnCommit(ctx);
        ctx.formatSeparator().visit(Keywords.K_AS);
        if (WRAP_SELECT_IN_PARENS.contains((Object)ctx.dialect())) {
            ctx.sqlIndentStart(" (");
        } else {
            ctx.formatSeparator();
        }
        if (QOM.WithOrWithoutData.WITH_NO_DATA == this.withData && NO_SUPPORT_WITH_DATA.contains((Object)ctx.dialect())) {
            ctx.data(Tools.BooleanDataKey.DATA_SELECT_NO_DATA, true);
        }
        ctx.start(Clause.CREATE_TABLE_AS);
        if (!this.$columns().isEmpty() && NO_SUPPORT_CTAS_COLUMN_NAMES.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.select(DSL.asterisk()).from((TableLike<?>)this.select.asTable(DSL.table(DSL.name("t")), this.$columns())));
        } else {
            ctx.visit(this.select);
        }
        ctx.end(Clause.CREATE_TABLE_AS);
        if (QOM.WithOrWithoutData.WITH_NO_DATA == this.withData && NO_SUPPORT_WITH_DATA.contains((Object)ctx.dialect())) {
            ctx.data().remove(Tools.BooleanDataKey.DATA_SELECT_NO_DATA);
        }
        if (WRAP_SELECT_IN_PARENS.contains((Object)ctx.dialect())) {
            ctx.sqlIndentEnd(')');
        }
        if (QOM.WithOrWithoutData.WITH_NO_DATA == this.withData && !NO_SUPPORT_WITH_DATA.contains((Object)ctx.dialect())) {
            ctx.formatSeparator().visit(Keywords.K_WITH_NO_DATA);
        } else if (QOM.WithOrWithoutData.WITH_DATA == this.withData && !NO_SUPPORT_WITH_DATA.contains((Object)ctx.dialect())) {
            ctx.formatSeparator().visit(Keywords.K_WITH_DATA);
        } else if (REQUIRES_WITH_DATA.contains((Object)ctx.dialect())) {
            ctx.formatSeparator().visit(Keywords.K_WITH_DATA);
        }
    }

    @Pro
    private final void acceptCreateTableAsSelectDB2(Context<?> ctx) {
        if (QOM.WithOrWithoutData.WITH_NO_DATA != this.withData) {
            Tools.begin(ctx, c1 -> {
                Tools.executeImmediate(c1, c2 -> this.acceptCreateTableAsSelectDB20((Context<?>)c2));
                c1.formatSeparator();
                Tools.executeImmediate(c1, c2 -> c2.visit(DSL.insertInto(this.table).select(this.select)));
            });
        } else {
            this.acceptCreateTableAsSelectDB20(ctx);
        }
    }

    @Pro
    private final void acceptCreateTableAsSelectDB20(Context<?> ctx) {
        ctx.start(Clause.CREATE_TABLE);
        this.toSQLCreateTable(ctx);
        ctx.formatSeparator().visit(Keywords.K_AS).sqlIndentStart(" (");
        ctx.start(Clause.CREATE_TABLE_AS).visit(this.select).end(Clause.CREATE_TABLE_AS);
        ctx.sqlIndentEnd(") ").visit(Keywords.K_WITH_NO_DATA);
        ctx.end(Clause.CREATE_TABLE);
    }

    private final void toSQLCreateTableName(Context<?> ctx) {
        ctx.start(Clause.CREATE_TABLE_NAME).visit(Keywords.K_CREATE).sql(' ');
        if (this.temporary) {
            if (SUPPORT_TEMPORARY.contains((Object)ctx.dialect())) {
                ctx.visit(Keywords.K_TEMPORARY).sql(' ');
            } else {
                ctx.visit(Keywords.K_GLOBAL_TEMPORARY).sql(' ');
            }
        }
        ctx.visit(Keywords.K_TABLE).sql(' ');
        if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
            ctx.visit(Keywords.K_IF_NOT_EXISTS).sql(' ');
        }
        ctx.visit(this.table).end(Clause.CREATE_TABLE_NAME);
    }

    private final void toSQLOnCommit(Context<?> ctx) {
        if (this.temporary && this.onCommit != null) {
            switch (this.onCommit) {
                case DELETE_ROWS: {
                    ctx.formatSeparator().visit(Keywords.K_ON_COMMIT_DELETE_ROWS);
                    break;
                }
                case PRESERVE_ROWS: {
                    ctx.formatSeparator().visit(Keywords.K_ON_COMMIT_PRESERVE_ROWS);
                    break;
                }
                case DROP: {
                    ctx.formatSeparator().visit(Keywords.K_ON_COMMIT_DROP);
                }
            }
        }
    }

    private final void acceptSelectInto(Context<?> ctx) {
        if (QOM.WithOrWithoutData.WITH_NO_DATA == this.withData) {
            ctx.data(Tools.BooleanDataKey.DATA_SELECT_NO_DATA, true, c -> this.acceptSelectInto0((Context<?>)c));
        } else {
            this.acceptSelectInto0(ctx);
        }
    }

    private final void acceptSelectInto0(Context<?> ctx) {
        ctx.data(Tools.SimpleDataKey.DATA_SELECT_INTO_TABLE, this.table, c -> {
            if (!this.$columns().isEmpty()) {
                c.visit(DSL.select(DSL.asterisk()).from((TableLike<?>)this.select.asTable(DSL.table(DSL.name("t")), this.$columns())));
            } else {
                c.visit(this.select);
            }
        });
    }

    @Override
    public final Table<?> $table() {
        return this.table;
    }

    @Override
    public final boolean $temporary() {
        return this.temporary;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final QOM.UnmodifiableList<? extends TableElement> $tableElements() {
        return QOM.unmodifiable(this.tableElements);
    }

    @Override
    public final Select<?> $select() {
        return this.select;
    }

    @Override
    public final QOM.WithOrWithoutData $withData() {
        return this.withData;
    }

    @Override
    public final QOM.TableCommitAction $onCommit() {
        return this.onCommit;
    }

    @Override
    public final Comment $comment() {
        return this.comment;
    }

    @Override
    public final SQL $storage() {
        return this.storage;
    }

    @Override
    public final QOM.CreateTable $table(Table<?> newValue) {
        return this.$constructor().apply(newValue, this.$temporary(), this.$ifNotExists(), this.$tableElements(), this.$select(), this.$withData(), this.$onCommit(), this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $temporary(boolean newValue) {
        return this.$constructor().apply(this.$table(), newValue, this.$ifNotExists(), this.$tableElements(), this.$select(), this.$withData(), this.$onCommit(), this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $ifNotExists(boolean newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), newValue, this.$tableElements(), this.$select(), this.$withData(), this.$onCommit(), this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $tableElements(Collection<? extends TableElement> newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), this.$ifNotExists(), newValue, this.$select(), this.$withData(), this.$onCommit(), this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $select(Select<?> newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), this.$ifNotExists(), this.$tableElements(), newValue, this.$withData(), this.$onCommit(), this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $withData(QOM.WithOrWithoutData newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), this.$ifNotExists(), this.$tableElements(), this.$select(), newValue, this.$onCommit(), this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $onCommit(QOM.TableCommitAction newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), this.$ifNotExists(), this.$tableElements(), this.$select(), this.$withData(), newValue, this.$comment(), this.$storage());
    }

    @Override
    public final QOM.CreateTable $comment(Comment newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), this.$ifNotExists(), this.$tableElements(), this.$select(), this.$withData(), this.$onCommit(), newValue, this.$storage());
    }

    @Override
    public final QOM.CreateTable $storage(SQL newValue) {
        return this.$constructor().apply(this.$table(), this.$temporary(), this.$ifNotExists(), this.$tableElements(), this.$select(), this.$withData(), this.$onCommit(), this.$comment(), newValue);
    }

    public final Function9<? super Table<?>, ? super Boolean, ? super Boolean, ? super Collection<? extends TableElement>, ? super Select<?>, ? super QOM.WithOrWithoutData, ? super QOM.TableCommitAction, ? super Comment, ? super SQL, ? extends QOM.CreateTable> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8, a9) -> new CreateTableImpl(this.configuration(), (Table<?>)a1, (boolean)a2, (boolean)a3, (Collection<? extends TableElement>)a4, (Select<?>)a5, (QOM.WithOrWithoutData)((Object)a6), (QOM.TableCommitAction)((Object)a7), (Comment)a8, (SQL)a9);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$table(), this.$temporary(), this.$ifNotExists(), this.$tableElements(), this.$select(), this.$withData(), this.$onCommit(), this.$comment(), this.$storage(), (a1, a2, a3, a4, a5, a6, a7, a8, a9) -> this.$constructor().apply((Table<?>)a1, (Boolean)a2, (Boolean)a3, (Collection<TableElement>)a4, (Select<?>)a5, (QOM.WithOrWithoutData)((Object)a6), (QOM.TableCommitAction)((Object)a7), (Comment)a8, (SQL)a9), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$table(), this.$tableElements(), this.$select(), this.$comment(), this.$storage());
    }
}

