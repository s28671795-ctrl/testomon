/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jooq.AlterTableAddStep;
import org.jooq.AlterTableAlterConstraintStep;
import org.jooq.AlterTableAlterStep;
import org.jooq.AlterTableDropStep;
import org.jooq.AlterTableFinalStep;
import org.jooq.AlterTableRenameColumnToStep;
import org.jooq.AlterTableRenameConstraintToStep;
import org.jooq.AlterTableRenameIndexToStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterTableUsingIndexStep;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Nullability;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.Variable;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.ConstraintImpl;
import org.jooq.impl.ConstraintType;
import org.jooq.impl.CreateTableImpl;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class AlterTableImpl
extends AbstractDDLQuery
implements AlterTableStep,
AlterTableAddStep,
AlterTableDropStep,
AlterTableAlterStep,
AlterTableAlterConstraintStep,
AlterTableUsingIndexStep,
AlterTableRenameColumnToStep,
AlterTableRenameIndexToStep,
AlterTableRenameConstraintToStep,
QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES = new Clause[]{Clause.ALTER_TABLE};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS_COLUMN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS_CONSTRAINT = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS_COLUMN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> SUPPORT_RENAME_COLUMN = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.HANA, SQLDialect.INFORMIX);
    private static final Set<SQLDialect> SUPPORT_RENAME_TABLE = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.TERADATA);
    private static final Set<SQLDialect> NO_SUPPORT_RENAME_QUALIFIED_TABLE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_ALTER_TYPE_AND_NULL = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_DROP_CONSTRAINT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final Set<SQLDialect> REQUIRE_REPEAT_ADD_ON_MULTI_ALTER = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> REQUIRE_REPEAT_DROP_ON_MULTI_ALTER = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> REQUIRE_PARENS_ON_MULTI_ALTER = SQLDialect.supportedBy(SQLDialect.INFORMIX, SQLDialect.ORACLE);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_PARENS_ON_MULTI_ALTER = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    private static final Set<SQLDialect> SUPPORT_RENAME_OBJECT = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_DROP_PRIMARY_KEY = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private final Table<?> table;
    private final boolean ifExists;
    private boolean ifExistsColumn;
    private boolean ifExistsConstraint;
    private boolean ifNotExistsColumn;
    private Comment comment;
    private Table<?> renameTo;
    private Field<?> renameColumn;
    private Field<?> renameColumnTo;
    private Index renameIndex;
    private Index renameIndexTo;
    private Constraint renameConstraint;
    private Constraint renameConstraintTo;
    private QueryPartList<TableElement> add;
    private Field<?> addColumn;
    private DataType<?> addColumnType;
    private Constraint addConstraint;
    private boolean addFirst;
    private Field<?> addBefore;
    private Field<?> addAfter;
    @Pro
    private Index usingIndex;
    private Constraint alterConstraint;
    private boolean alterConstraintEnforced;
    private Field<?> alterColumn;
    private Nullability alterColumnNullability;
    private DataType<?> alterColumnType;
    private Field<?> alterColumnDefault;
    private boolean alterColumnDropDefault;
    private QueryPartList<Field<?>> dropColumns;
    private Constraint dropConstraint;
    private ConstraintType dropConstraintType;
    private QOM.Cascade dropCascade;

    AlterTableImpl(Configuration configuration, Table<?> table) {
        this(configuration, table, false);
    }

    AlterTableImpl(Configuration configuration, Table<?> table, boolean ifExists) {
        super(configuration);
        this.table = table;
        this.ifExists = ifExists;
    }

    final Table<?> $table() {
        return this.table;
    }

    final boolean $ifExists() {
        return this.ifExists;
    }

    final boolean $ifExistsColumn() {
        return this.ifExistsColumn;
    }

    final boolean $ifExistsConstraint() {
        return this.ifExistsConstraint;
    }

    final boolean $ifNotExistsColumn() {
        return this.ifNotExistsColumn;
    }

    final List<TableElement> $add() {
        return this.add;
    }

    final Field<?> $addColumn() {
        return this.addColumn;
    }

    final DataType<?> $addColumnType() {
        return this.addColumnType;
    }

    final Constraint $addConstraint() {
        return this.addConstraint;
    }

    final boolean $addFirst() {
        return this.addFirst;
    }

    final Field<?> $addBefore() {
        return this.addBefore;
    }

    final Field<?> $addAfter() {
        return this.addAfter;
    }

    final Field<?> $alterColumn() {
        return this.alterColumn;
    }

    final Nullability $alterColumnNullability() {
        return this.alterColumnNullability;
    }

    final DataType<?> $alterColumnType() {
        return this.alterColumnType;
    }

    final Field<?> $alterColumnDefault() {
        return this.alterColumnDefault;
    }

    final boolean $alterColumnDropDefault() {
        return this.alterColumnDropDefault;
    }

    final Constraint $alterConstraint() {
        return this.alterConstraint;
    }

    final boolean $alterConstraintEnforced() {
        return this.alterConstraintEnforced;
    }

    final Table<?> $renameTo() {
        return this.renameTo;
    }

    final Field<?> $renameColumn() {
        return this.renameColumn;
    }

    final Field<?> $renameColumnTo() {
        return this.renameColumnTo;
    }

    final Constraint $renameConstraint() {
        return this.renameConstraint;
    }

    final Constraint $renameConstraintTo() {
        return this.renameConstraintTo;
    }

    final List<Field<?>> $dropColumns() {
        return this.dropColumns;
    }

    final QOM.Cascade $dropCascade() {
        return this.dropCascade;
    }

    final Constraint $dropConstraint() {
        return this.dropConstraint;
    }

    final ConstraintType $dropConstraintType() {
        return this.dropConstraintType;
    }

    @Override
    public final AlterTableImpl comment(String c) {
        return this.comment(DSL.comment(c));
    }

    @Override
    public final AlterTableImpl comment(Comment c) {
        this.comment = c;
        return this;
    }

    @Override
    public final AlterTableImpl renameTo(Table<?> newName) {
        this.renameTo = newName;
        return this;
    }

    @Override
    public final AlterTableImpl renameTo(Name newName) {
        return this.renameTo((Table)DSL.table(newName));
    }

    @Override
    public final AlterTableImpl renameTo(String newName) {
        return this.renameTo(DSL.name(newName));
    }

    @Override
    public final AlterTableImpl renameColumn(Field<?> oldName) {
        this.renameColumn = oldName;
        return this;
    }

    @Override
    public final AlterTableImpl renameColumn(Name oldName) {
        return this.renameColumn((Field)DSL.field(oldName));
    }

    @Override
    public final AlterTableImpl renameColumn(String oldName) {
        return this.renameColumn(DSL.name(oldName));
    }

    @Override
    public final AlterTableImpl renameConstraint(Constraint oldName) {
        this.renameConstraint = oldName;
        return this;
    }

    @Override
    public final AlterTableImpl renameIndex(String oldName) {
        return this.renameIndex(DSL.name(oldName));
    }

    @Override
    public final AlterTableImpl renameIndex(Name oldName) {
        return this.renameIndex(DSL.index(oldName));
    }

    @Override
    public final AlterTableImpl renameIndex(Index oldName) {
        this.renameIndex = oldName;
        return this;
    }

    @Override
    public final AlterTableImpl renameConstraint(Name oldName) {
        return this.renameConstraint(DSL.constraint(oldName));
    }

    @Override
    public final AlterTableImpl renameConstraint(String oldName) {
        return this.renameConstraint(DSL.name(oldName));
    }

    @Override
    public final AlterTableImpl to(String newName) {
        return this.to(DSL.name(newName));
    }

    @Override
    public final AlterTableImpl to(Name newName) {
        if (this.renameColumn != null) {
            return this.to((Field)DSL.field(newName));
        }
        if (this.renameConstraint != null) {
            return this.to(DSL.constraint(newName));
        }
        if (this.renameIndex != null) {
            return this.to(DSL.index(newName));
        }
        throw new IllegalStateException();
    }

    @Override
    public final AlterTableImpl to(Field<?> newName) {
        if (this.renameColumn == null) {
            throw new IllegalStateException();
        }
        this.renameColumnTo = newName;
        return this;
    }

    @Override
    public final AlterTableImpl to(Constraint newName) {
        if (this.renameConstraint == null) {
            throw new IllegalStateException();
        }
        this.renameConstraintTo = newName;
        return this;
    }

    @Override
    public final AlterTableImpl to(Index newName) {
        if (this.renameIndex == null) {
            throw new IllegalStateException();
        }
        this.renameIndexTo = newName;
        return this;
    }

    @Override
    public final AlterTableImpl add(Field<?> field2) {
        return this.addColumn((Field)field2);
    }

    @Override
    public final AlterTableImpl add(TableElement ... fields) {
        return this.add(Arrays.asList(fields));
    }

    @Override
    public final AlterTableImpl add(Collection<? extends TableElement> fields) {
        if (fields.size() == 1) {
            TableElement first = fields.iterator().next();
            if (first instanceof Field) {
                Field f = (Field)first;
                return this.add(f);
            }
            if (first instanceof Constraint) {
                Constraint c = (Constraint)first;
                return this.add(c);
            }
            if (first instanceof Index) {
                Index i = (Index)first;
                throw new UnsupportedOperationException("ALTER TABLE .. ADD INDEX not yet supported, see https://github.com/jOOQ/jOOQ/issues/13006");
            }
        }
        this.add = new QueryPartList<TableElement>((Iterable<? extends TableElement>)fields);
        return this;
    }

    @Override
    public final <T> AlterTableImpl add(Field<T> field2, DataType<T> type) {
        return this.addColumn((Field)field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl add(Name field2, DataType<?> type) {
        return this.addColumn(field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl add(String field2, DataType<?> type) {
        return this.addColumn(field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl addIfNotExists(Field<?> field2) {
        return this.addColumnIfNotExists((Field)field2);
    }

    @Override
    public final <T> AlterTableImpl addIfNotExists(Field<T> field2, DataType<T> type) {
        return this.addColumnIfNotExists((Field)field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl addIfNotExists(Name field2, DataType<?> type) {
        return this.addColumnIfNotExists(field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl addIfNotExists(String field2, DataType<?> type) {
        return this.addColumnIfNotExists(field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl addColumn(String field2, DataType<?> type) {
        return this.addColumn(DSL.name(field2), (DataType)type);
    }

    @Override
    public final AlterTableImpl addColumn(Name field2, DataType<?> type) {
        return this.addColumn((Field)DSL.field(field2, type), (DataType)type);
    }

    @Override
    public final AlterTableImpl addColumn(Field<?> field2) {
        return this.addColumn((Field)field2, field2.getDataType());
    }

    @Override
    public final <T> AlterTableImpl addColumn(Field<T> field2, DataType<T> type) {
        this.addColumn = field2;
        this.addColumnType = type;
        return this;
    }

    @Override
    public final AlterTableImpl addColumnIfNotExists(String field2, DataType<?> type) {
        return this.addColumnIfNotExists(DSL.name(field2), (DataType)type);
    }

    @Override
    public final AlterTableImpl addColumnIfNotExists(Name field2, DataType<?> type) {
        return this.addColumnIfNotExists((Field)DSL.field(field2, type), (DataType)type);
    }

    @Override
    public final AlterTableImpl addColumnIfNotExists(Field<?> field2) {
        return this.addColumnIfNotExists((Field)field2, field2.getDataType());
    }

    @Override
    public final <T> AlterTableImpl addColumnIfNotExists(Field<T> field2, DataType<T> type) {
        this.ifNotExistsColumn = true;
        return this.addColumn((Field)field2, (DataType)type);
    }

    @Override
    public final AlterTableImpl add(Constraint constraint) {
        this.addConstraint = constraint;
        return this;
    }

    @Override
    public final AlterTableImpl first() {
        this.addFirst = true;
        return this;
    }

    @Override
    public final AlterTableImpl before(String columnName) {
        return this.before(DSL.name(columnName));
    }

    @Override
    public final AlterTableImpl before(Name columnName) {
        return this.before((Field)DSL.field(columnName));
    }

    @Override
    public final AlterTableImpl before(Field<?> columnName) {
        this.addBefore = columnName;
        return this;
    }

    @Override
    public final AlterTableImpl after(String columnName) {
        return this.after(DSL.name(columnName));
    }

    @Override
    public final AlterTableImpl after(Name columnName) {
        return this.after((Field)DSL.field(columnName));
    }

    @Override
    public final AlterTableImpl after(Field<?> columnName) {
        this.addAfter = columnName;
        return this;
    }

    @Override
    @Pro
    public final AlterTableImpl usingIndex(String index) {
        return this.usingIndex(DSL.name(index));
    }

    @Override
    @Pro
    public final AlterTableImpl usingIndex(Name index) {
        return this.usingIndex(DSL.index(index));
    }

    @Override
    @Pro
    public final AlterTableImpl usingIndex(Index index) {
        this.usingIndex = index;
        return this;
    }

    public final <T> AlterTableImpl alter(Field<T> field2) {
        return this.alterColumn((Field)field2);
    }

    public final AlterTableImpl alter(Name field2) {
        return this.alterColumn(field2);
    }

    public final AlterTableImpl alter(String field2) {
        return this.alterColumn(field2);
    }

    public final AlterTableImpl alterColumn(Name field2) {
        return this.alterColumn((Field)DSL.field(field2));
    }

    public final AlterTableImpl alterColumn(String field2) {
        return this.alterColumn(DSL.name(field2));
    }

    public final <T> AlterTableImpl alterColumn(Field<T> field2) {
        this.alterColumn = field2;
        return this;
    }

    @Override
    public final AlterTableImpl alter(Constraint constraint) {
        return this.alterConstraint(constraint);
    }

    @Override
    public final AlterTableImpl alterConstraint(Name constraint) {
        return this.alterConstraint(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl alterConstraint(String constraint) {
        return this.alterConstraint(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl alterConstraint(Constraint constraint) {
        this.alterConstraint = constraint;
        return this;
    }

    @Override
    public final AlterTableImpl enforced() {
        this.alterConstraintEnforced = true;
        return this;
    }

    @Override
    public final AlterTableImpl notEnforced() {
        this.alterConstraintEnforced = false;
        return this;
    }

    public final AlterTableImpl set(DataType type) {
        this.alterColumnType = type;
        return this;
    }

    @Override
    public final AlterTableImpl setNotNull() {
        this.alterColumnNullability = Nullability.NOT_NULL;
        return this;
    }

    @Override
    public final AlterTableImpl dropNotNull() {
        this.alterColumnNullability = Nullability.NULL;
        return this;
    }

    public final AlterTableImpl defaultValue(Object literal) {
        return this.setDefault(literal);
    }

    public final AlterTableImpl defaultValue(Field expression) {
        return this.setDefault(expression);
    }

    public final AlterTableImpl default_(Object literal) {
        return this.setDefault(literal);
    }

    public final AlterTableImpl default_(Field expression) {
        return this.setDefault(expression);
    }

    public final AlterTableImpl setDefault(Object literal) {
        return this.default_((Field)Tools.field(literal));
    }

    public final AlterTableImpl setDefault(Field expression) {
        this.alterColumnDefault = expression;
        return this;
    }

    @Override
    public final AlterTableImpl dropDefault() {
        this.alterColumnDropDefault = true;
        return this;
    }

    @Override
    public final AlterTableImpl drop(Field<?> field2) {
        return this.dropColumn((Field)field2);
    }

    @Override
    public final AlterTableImpl drop(Name field2) {
        return this.dropColumn(field2);
    }

    @Override
    public final AlterTableImpl drop(String field2) {
        return this.dropColumn(field2);
    }

    @Override
    public final AlterTableImpl dropIfExists(Field<?> field2) {
        return this.dropColumnIfExists((Field)field2);
    }

    @Override
    public final AlterTableImpl dropIfExists(Name field2) {
        return this.dropColumnIfExists(field2);
    }

    @Override
    public final AlterTableImpl dropIfExists(String field2) {
        return this.dropColumnIfExists(field2);
    }

    @Override
    public final AlterTableImpl dropColumn(Name field2) {
        return this.dropColumn((Field)DSL.field(field2));
    }

    @Override
    public final AlterTableImpl dropColumn(String field2) {
        return this.dropColumn(DSL.name(field2));
    }

    @Override
    public final AlterTableImpl dropColumn(Field<?> field2) {
        return this.dropColumns0(Collections.singletonList(field2));
    }

    @Override
    public final AlterTableImpl dropColumnIfExists(Name field2) {
        return this.dropColumnIfExists((Field)DSL.field(field2));
    }

    @Override
    public final AlterTableImpl dropColumnIfExists(String field2) {
        return this.dropColumnIfExists(DSL.name(field2));
    }

    @Override
    public final AlterTableImpl dropColumnIfExists(Field<?> field2) {
        this.ifExistsColumn = true;
        return this.dropColumn((Field)field2);
    }

    @Override
    public final AlterTableImpl drop(Field<?> ... fields) {
        return this.dropColumns((Field[])fields);
    }

    @Override
    public final AlterTableImpl drop(Name ... fields) {
        return this.dropColumns(fields);
    }

    @Override
    public final AlterTableImpl drop(String ... fields) {
        return this.dropColumns(fields);
    }

    @Override
    public final AlterTableImpl dropColumns(Field<?> ... fields) {
        return this.dropColumns(Arrays.asList(fields));
    }

    @Override
    public final AlterTableImpl dropColumns(Name ... fields) {
        return this.dropColumns((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final AlterTableImpl dropColumns(String ... fields) {
        return this.dropColumns((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final AlterTableImpl drop(Collection<? extends Field<?>> fields) {
        return this.dropColumns((Collection)fields);
    }

    @Override
    public final AlterTableImpl dropColumns(Collection<? extends Field<?>> fields) {
        return this.dropColumns0(fields);
    }

    private final AlterTableImpl dropColumns0(Collection<? extends Field<?>> fields) {
        this.dropColumns = new QueryPartList((Iterable<Field<?>>)fields);
        return this;
    }

    @Override
    public final AlterTableImpl drop(Constraint constraint) {
        this.dropConstraint = constraint;
        this.dropConstraintType = null;
        return this;
    }

    @Override
    public final AlterTableImpl dropConstraint(Constraint constraint) {
        return this.drop(constraint);
    }

    @Override
    public final AlterTableImpl dropConstraint(Name constraint) {
        return this.dropConstraint(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropConstraint(String constraint) {
        return this.dropConstraint(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropIfExists(Constraint constraint) {
        this.ifExistsConstraint = true;
        return this.drop(constraint);
    }

    @Override
    public final AlterTableImpl dropConstraintIfExists(Constraint constraint) {
        return this.dropIfExists(constraint);
    }

    @Override
    public final AlterTableImpl dropConstraintIfExists(Name constraint) {
        return this.dropIfExists(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropConstraintIfExists(String constraint) {
        return this.dropIfExists(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropPrimaryKey() {
        this.dropConstraintType = ConstraintType.PRIMARY_KEY;
        return this;
    }

    @Override
    public final AlterTableImpl dropPrimaryKey(Constraint constraint) {
        this.dropConstraint = constraint;
        this.dropConstraintType = ConstraintType.PRIMARY_KEY;
        return this;
    }

    @Override
    public final AlterTableImpl dropPrimaryKey(Name constraint) {
        return this.dropPrimaryKey(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropPrimaryKey(String constraint) {
        return this.dropPrimaryKey(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropUnique(Constraint constraint) {
        this.dropConstraint = constraint;
        this.dropConstraintType = ConstraintType.UNIQUE;
        return this;
    }

    @Override
    public final AlterTableImpl dropUnique(Name constraint) {
        return this.dropUnique(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropUnique(String constraint) {
        return this.dropUnique(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropForeignKey(Constraint constraint) {
        this.dropConstraint = constraint;
        this.dropConstraintType = ConstraintType.FOREIGN_KEY;
        return this;
    }

    @Override
    public final AlterTableImpl dropForeignKey(Name constraint) {
        return this.dropForeignKey(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableImpl dropForeignKey(String constraint) {
        return this.dropForeignKey(DSL.constraint(constraint));
    }

    @Override
    public final AlterTableFinalStep cascade() {
        this.dropCascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final AlterTableFinalStep restrict() {
        this.dropCascade = QOM.Cascade.RESTRICT;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    private final boolean supportsIfExistsColumn(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS_COLUMN.contains((Object)ctx.dialect());
    }

    private final boolean supportsIfNotExistsColumn(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS_COLUMN.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx) || (this.ifExistsColumn || this.ifExistsConstraint || this.ifNotExistsColumn) && !this.supportsIfExistsColumn(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_TABLE, this.ifExists ? Boolean.TRUE : null, this.ifExistsColumn || this.ifExistsConstraint ? Boolean.TRUE : (this.ifNotExistsColumn ? Boolean.FALSE : null), c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        List<Field<?>> comments;
        SQLDialect family = ctx.family();
        if (this.comment != null) {
            switch (family) {
                case AURORA_MYSQL: 
                case MEMSQL: 
                case MARIADB: 
                case MYSQL: {
                    break;
                }
                default: {
                    ctx.visit(DSL.commentOnTable(this.table).is(this.comment));
                    return;
                }
            }
        }
        if (family == SQLDialect.FIREBIRD && this.addFirst) {
            Tools.begin(ctx, c1 -> {
                Tools.executeImmediate(c1, c2 -> this.accept1((Context<?>)c2));
                c1.formatSeparator();
                Tools.executeImmediate(c1, c2 -> c2.visit(Keywords.K_ALTER_TABLE).sql(' ').visit(this.table).sql(' ').visit(Keywords.K_ALTER).sql(' ').visit(this.addColumn).sql(' ').visit(Keywords.K_POSITION).sql(" 1"));
            });
            return;
        }
        if (family == SQLDialect.SQLSERVER && (this.alterColumnDefault != null || this.alterColumnDropDefault)) {
            this.alterDefaultSQLServer(ctx);
            return;
        }
        if (family == SQLDialect.ASE && this.renameConstraintTo != null) {
            this.renameConstraintASE(ctx);
            return;
        }
        if (family == SQLDialect.ASE || family == SQLDialect.SQLSERVER) {
            if (this.renameTo != null) {
                this.renameTableSQLServer(ctx);
                return;
            }
            if (this.renameColumnTo != null) {
                this.renameColumnSQLServer(ctx);
                return;
            }
        }
        if (this.renameIndexTo != null) {
            switch (family) {
                case ASE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    ctx.visit(DSL.alterIndex(this.renameIndex.getQualifiedName().qualified() ? this.renameIndex.getQualifiedName() : this.table.getQualifiedName().append(this.renameIndex.getQualifiedName())).renameTo(this.renameIndexTo));
                    return;
                }
                case AURORA_MYSQL: 
                case MEMSQL: 
                case MARIADB: 
                case MYSQL: {
                    break;
                }
                default: {
                    ctx.visit(DSL.alterIndex(this.renameIndex).renameTo(this.renameIndexTo));
                    return;
                }
            }
        }
        if (this.alterColumnType != null && this.alterColumnType.nullability() != Nullability.DEFAULT) {
            switch (family) {
                case DB2: 
                case ORACLE: 
                case COCKROACHDB: 
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    this.alterColumnTypeAndNullabilityInBlock(ctx);
                    return;
                }
            }
        }
        if (this.dropConstraintType == ConstraintType.PRIMARY_KEY && this.dropConstraint == null && NO_SUPPORT_DROP_PRIMARY_KEY.contains((Object)ctx.dialect())) {
            Variable<String> n = DSL.variable("n", SQLDataType.VARCHAR);
            switch (family) {
                default: 
            }
            SelectConditionStep query = DSL.select(DSL.field("constraint_name", SQLDataType.VARCHAR)).from("information_schema.table_constraints").where(this.table.getSchema() == null ? DSL.noCondition() : DSL.condition("table_schema = {0}", DSL.inline(this.table.getSchema().getName()))).and("table_name = {0}", DSL.inline(this.table.getName())).and("constraint_type = 'PRIMARY KEY'");
            ctx.visit(DSL.begin(DSL.declare(n), n.set(query), DSL.execute(DSL.inline("alter table " + String.valueOf(this.table.getQualifiedName()) + " drop constraint ").concat(n))));
            return;
        }
        if (this.dropConstraintType == ConstraintType.UNIQUE && this.dropConstraint != null && family == SQLDialect.COCKROACHDB) {
            ctx.visit(DSL.dropIndex(this.dropConstraint.getUnqualifiedName()).on(this.table).cascade());
            return;
        }
        if (CreateTableImpl.EMULATE_COLUMN_COMMENT_IN_BLOCK.contains((Object)ctx.dialect()) && !(comments = this.addColumnComments()).isEmpty()) {
            Tools.begin(ctx, c1 -> {
                Tools.executeImmediateIf(CreateTableImpl.REQUIRE_EXECUTE_IMMEDIATE.contains((Object)c1.dialect()), c1, c2 -> this.accept1((Context<?>)c2));
                c1.formatSeparator();
                for (Field c : comments) {
                    Tools.executeImmediateIf(CreateTableImpl.REQUIRE_EXECUTE_IMMEDIATE.contains((Object)ctx.dialect()), c1, c2 -> c2.visit(DSL.commentOnColumn(this.table.getQualifiedName().append(c.getUnqualifiedName())).is(c.getComment())));
                }
            });
            return;
        }
        this.accept1(ctx);
    }

    private final List<Field<?>> addColumnComments() {
        if (this.addColumn != null) {
            if (!this.addColumn.getComment().isEmpty()) {
                return Arrays.asList(this.addColumn);
            }
        } else if (this.add != null) {
            return Tools.map(Tools.filter(this.add, c -> c instanceof Field && !c.getComment().isEmpty()), c -> (Field)c);
        }
        return Collections.emptyList();
    }

    private final void accept1(Context<?> ctx) {
        boolean renameObject;
        SQLDialect family = ctx.family();
        boolean omitAlterTable = this.renameConstraint != null && family == SQLDialect.HSQLDB || this.renameColumn != null && SUPPORT_RENAME_COLUMN.contains((Object)ctx.dialect());
        boolean renameTable = this.renameTo != null && SUPPORT_RENAME_TABLE.contains((Object)ctx.dialect());
        boolean bl = renameObject = this.renameTo != null && SUPPORT_RENAME_OBJECT.contains((Object)ctx.dialect());
        if (!omitAlterTable) {
            ctx.start(Clause.ALTER_TABLE_TABLE).visit(renameObject ? Keywords.K_RENAME_OBJECT : (renameTable ? Keywords.K_RENAME_TABLE : Keywords.K_ALTER_TABLE));
            if (this.ifExists && this.supportsIfExists(ctx)) {
                ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
            }
            ctx.sql(' ').visit(this.table).end(Clause.ALTER_TABLE_TABLE).formatIndentStart().formatSeparator();
        }
        if (this.comment != null) {
            ctx.visit(Keywords.K_COMMENT).sql(' ').visit(this.comment);
        } else if (this.renameTo != null) {
            boolean qualify = ctx.qualify();
            boolean unqualify = this.unqualifyRenameTo(ctx);
            ctx.start(Clause.ALTER_TABLE_RENAME);
            if (unqualify) {
                ctx.qualify(false);
            }
            ctx.visit(renameObject || renameTable ? Keywords.K_TO : Keywords.K_RENAME_TO).sql(' ').visit(this.renameTo);
            if (unqualify) {
                ctx.qualify(qualify);
            }
            ctx.end(Clause.ALTER_TABLE_RENAME);
        } else if (this.renameColumn != null) {
            ctx.start(Clause.ALTER_TABLE_RENAME_COLUMN);
            switch (ctx.family()) {
                case HANA: 
                case INFORMIX: 
                case DERBY: {
                    ctx.visit(Keywords.K_RENAME_COLUMN).sql(' ').visit(this.renameColumn).formatSeparator().visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameColumnTo));
                    break;
                }
                case H2: 
                case HSQLDB: {
                    ctx.visit(Keywords.K_ALTER_COLUMN).sql(' ').qualify(false, c -> c.visit(this.renameColumn)).formatSeparator().visit(Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameColumnTo));
                    break;
                }
                case FIREBIRD: {
                    ctx.visit(Keywords.K_ALTER_COLUMN).sql(' ').qualify(false, c -> c.visit(this.renameColumn)).formatSeparator().visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameColumnTo));
                    break;
                }
                case MEMSQL: {
                    ctx.visit(Keywords.K_CHANGE).sql(' ').qualify(false, c -> c.visit(this.renameColumn)).formatSeparator().sql(' ').qualify(false, c -> c.visit(this.renameColumnTo));
                    break;
                }
                case TERADATA: {
                    ctx.visit(Keywords.K_RENAME).sql(' ').qualify(false, c -> c.visit(this.renameColumn)).formatSeparator().visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameColumnTo));
                    break;
                }
                default: {
                    ctx.visit(Keywords.K_RENAME_COLUMN).sql(' ').qualify(false, c -> c.visit(this.renameColumn)).formatSeparator().visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameColumnTo));
                }
            }
            ctx.end(Clause.ALTER_TABLE_RENAME_COLUMN);
        } else if (this.renameIndex != null) {
            ctx.start(Clause.ALTER_TABLE_RENAME_INDEX).visit(Keywords.K_RENAME_INDEX).sql(' ').qualify(false, c -> c.visit(this.renameIndex)).formatSeparator().visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameIndexTo)).end(Clause.ALTER_TABLE_RENAME_INDEX);
        } else if (this.renameConstraint != null) {
            ctx.start(Clause.ALTER_TABLE_RENAME_CONSTRAINT);
            ctx.data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c1 -> {
                if (family == SQLDialect.HSQLDB) {
                    c1.visit(Keywords.K_ALTER_CONSTRAINT).sql(' ').qualify(false, c2 -> c2.visit(this.renameConstraint)).formatSeparator().visit(Keywords.K_RENAME_TO).sql(' ').qualify(false, c2 -> c2.visit(this.renameConstraintTo));
                } else {
                    c1.visit(family == SQLDialect.TERADATA ? Keywords.K_RENAME : Keywords.K_RENAME_CONSTRAINT).sql(' ').qualify(false, c2 -> c2.visit(this.renameConstraint)).formatSeparator().visit(Keywords.K_TO).sql(' ').qualify(false, c2 -> c2.visit(this.renameConstraintTo));
                }
            });
            ctx.end(Clause.ALTER_TABLE_RENAME_CONSTRAINT);
        } else if (this.add != null) {
            boolean indent;
            boolean multiAdd = REQUIRE_REPEAT_ADD_ON_MULTI_ALTER.contains((Object)ctx.dialect());
            boolean parens = !multiAdd && !NO_SUPPORT_PARENS_ON_MULTI_ALTER.contains((Object)ctx.dialect());
            boolean bl2 = !multiAdd || ctx.family() != SQLDialect.DB2;
            ctx.start(Clause.ALTER_TABLE_ADD).visit(this.addColumnKeyword(ctx)).sql(' ');
            if (parens) {
                ctx.sql('(');
            }
            boolean bl3 = indent = !multiAdd && this.add.size() > 1;
            if (indent) {
                ctx.formatIndentStart().formatNewLine();
            }
            for (int i = 0; i < this.add.size(); ++i) {
                if (i > 0) {
                    ctx.sql(bl2 ? "," : "").formatSeparator();
                    if (multiAdd) {
                        ctx.visit(this.addColumnKeyword(ctx)).sql(' ');
                    }
                }
                TableElement part = (TableElement)this.add.get(i);
                ctx.qualify(false, c -> c.visit(Tools.uncollate(part)));
                if (!(part instanceof Field)) continue;
                Field f = (Field)part;
                ctx.sql(' ');
                Tools.toSQLDDLTypeDeclarationForAddition(ctx, f.getDataType());
                CreateTableImpl.acceptColumnComment(ctx, f);
            }
            if (indent) {
                ctx.formatIndentEnd().formatNewLine();
            }
            if (parens) {
                ctx.sql(')');
            }
            this.acceptFirstBeforeAfter(ctx);
            ctx.end(Clause.ALTER_TABLE_ADD);
        } else if (this.addColumn != null) {
            ctx.start(Clause.ALTER_TABLE_ADD).visit(this.addColumnKeyword(ctx)).sql(' ');
            if (this.ifNotExistsColumn && this.supportsIfNotExistsColumn(ctx)) {
                ctx.visit(Keywords.K_IF_NOT_EXISTS).sql(' ');
            }
            if (family == SQLDialect.HANA) {
                ctx.sql('(');
            }
            ctx.qualify(false, c -> c.visit(Tools.uncollate(this.addColumn))).sql(' ');
            Tools.toSQLDDLTypeDeclarationForAddition(ctx, this.addColumnType);
            CreateTableImpl.acceptColumnComment(ctx, this.addColumn);
            if (family == SQLDialect.HANA) {
                ctx.sql(')');
            }
            this.acceptFirstBeforeAfter(ctx);
            ctx.end(Clause.ALTER_TABLE_ADD);
        } else if (this.addConstraint != null) {
            ctx.start(Clause.ALTER_TABLE_ADD);
            ctx.visit(Keywords.K_ADD).sql(' ');
            if (ctx.family() == SQLDialect.INFORMIX) {
                ctx.visit(Keywords.K_CONSTRAINT).sql(' ');
            }
            ctx.visit(this.addConstraint);
            if (this.usingIndex != null) {
                ctx.sql(' ').visit(Keywords.K_USING_INDEX).sql(' ').visit(this.usingIndex);
            }
            ctx.end(Clause.ALTER_TABLE_ADD);
        } else if (this.alterConstraint != null) {
            ctx.start(Clause.ALTER_TABLE_ALTER);
            ctx.data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c -> {
                switch (family) {
                    case ORACLE: {
                        ctx.visit(Keywords.K_MODIFY);
                        break;
                    }
                    default: {
                        ctx.visit(Keywords.K_ALTER);
                    }
                }
                ctx.sql(' ').visit(Keywords.K_CONSTRAINT).sql(' ').visit(this.alterConstraint);
                ConstraintImpl.acceptEnforced(ctx, this.alterConstraintEnforced);
            });
            ctx.end(Clause.ALTER_TABLE_ALTER);
        } else if (this.alterColumn != null) {
            ctx.start(Clause.ALTER_TABLE_ALTER);
            switch (family) {
                case EXASOL: {
                    if (this.alterColumnDefault != null || this.alterColumnDropDefault) {
                        ctx.visit(Keywords.K_ALTER);
                        break;
                    }
                    ctx.visit(Keywords.K_MODIFY);
                    break;
                }
                case MEMSQL: 
                case ORACLE: 
                case INFORMIX: {
                    ctx.visit(Keywords.K_MODIFY);
                    break;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case VERTICA: {
                    ctx.visit(Keywords.K_ALTER_COLUMN);
                    break;
                }
                case ASE: {
                    if (this.alterColumnDefault != null || this.alterColumnDropDefault) {
                        ctx.visit(Keywords.K_REPLACE);
                        break;
                    }
                    ctx.visit(Keywords.K_MODIFY);
                    break;
                }
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: 
                case CUBRID: {
                    if (this.alterColumnDefault == null && !this.alterColumnDropDefault) {
                        ctx.visit(Keywords.K_CHANGE_COLUMN).sql(' ').qualify(false, c -> c.visit(this.alterColumn));
                        break;
                    }
                    ctx.visit(Keywords.K_ALTER_COLUMN);
                    break;
                }
                default: {
                    ctx.visit(Keywords.K_ALTER);
                }
            }
            if (ctx.family() == SQLDialect.HANA) {
                ctx.sql('(');
            } else {
                ctx.sql(' ');
            }
            ctx.qualify(false, c -> c.visit(this.alterColumn));
            if (this.alterColumnType != null) {
                switch (family) {
                    case DB2: 
                    case DERBY: 
                    case VERTICA: {
                        ctx.sql(' ').visit(Keywords.K_SET_DATA_TYPE);
                        break;
                    }
                    case COCKROACHDB: 
                    case AURORA_POSTGRES: 
                    case POSTGRES: 
                    case YUGABYTEDB: 
                    case FIREBIRD: {
                        ctx.sql(' ').visit(Keywords.K_TYPE);
                    }
                }
                ctx.sql(' ');
                Tools.toSQLDDLTypeDeclaration(ctx, this.alterColumnType);
                Tools.toSQLDDLTypeDeclarationIdentityBeforeNull(ctx, this.alterColumnType);
                if (!NO_SUPPORT_ALTER_TYPE_AND_NULL.contains((Object)ctx.dialect())) {
                    switch (this.alterColumnType.nullability()) {
                        case NULL: {
                            ctx.sql(' ').visit(Keywords.K_NULL);
                            break;
                        }
                        case NOT_NULL: {
                            ctx.sql(' ').visit(Keywords.K_NOT_NULL);
                            break;
                        }
                    }
                }
                Tools.toSQLDDLTypeDeclarationIdentityAfterNull(ctx, this.alterColumnType);
            } else if (this.alterColumnDefault != null) {
                ctx.start(Clause.ALTER_TABLE_ALTER_DEFAULT);
                switch (family) {
                    case MEMSQL: 
                    case ASE: 
                    case ORACLE: {
                        ctx.sql(' ').visit(Keywords.K_DEFAULT);
                        break;
                    }
                    default: {
                        ctx.sql(' ').visit(Keywords.K_SET_DEFAULT);
                    }
                }
                ctx.sql(' ').visit(this.alterColumnDefault).end(Clause.ALTER_TABLE_ALTER_DEFAULT);
            } else if (this.alterColumnDropDefault) {
                ctx.start(Clause.ALTER_TABLE_ALTER_DEFAULT);
                switch (family) {
                    case MEMSQL: 
                    case ASE: 
                    case ORACLE: {
                        ctx.sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(Keywords.K_NULL);
                        break;
                    }
                    case AURORA_MYSQL: 
                    case MARIADB: 
                    case MYSQL: {
                        ctx.sql(' ').visit(Keywords.K_SET_DEFAULT).sql(' ').visit(Keywords.K_NULL);
                        break;
                    }
                    default: {
                        ctx.sql(' ').visit(Keywords.K_DROP_DEFAULT);
                    }
                }
                ctx.end(Clause.ALTER_TABLE_ALTER_DEFAULT);
            } else if (this.alterColumnNullability != null) {
                ctx.start(Clause.ALTER_TABLE_ALTER_NULL);
                switch (ctx.family()) {
                    case ASE: 
                    case ORACLE: {
                        ctx.sql(' ').visit(this.alterColumnNullability.nullable() ? Keywords.K_NULL : Keywords.K_NOT_NULL);
                        break;
                    }
                    default: {
                        ctx.sql(' ').visit(this.alterColumnNullability.nullable() ? Keywords.K_DROP_NOT_NULL : Keywords.K_SET_NOT_NULL);
                    }
                }
                ctx.end(Clause.ALTER_TABLE_ALTER_NULL);
            }
            if (ctx.family() == SQLDialect.HANA) {
                ctx.sql(')');
            }
            ctx.end(Clause.ALTER_TABLE_ALTER);
        } else if (this.dropColumns != null) {
            ctx.start(Clause.ALTER_TABLE_DROP);
            if (REQUIRE_REPEAT_DROP_ON_MULTI_ALTER.contains((Object)ctx.dialect())) {
                String separator = "";
                for (Field field2 : this.dropColumns) {
                    ctx.sql(separator);
                    this.acceptDropColumn(ctx);
                    if (this.ifExistsColumn && this.supportsIfExistsColumn(ctx)) {
                        ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
                    }
                    ctx.sql(' ').qualify(false, c -> c.visit(dropColumn));
                    if (family == SQLDialect.DB2) {
                        separator = " ";
                        continue;
                    }
                    separator = ", ";
                }
            } else {
                boolean parens;
                this.acceptDropColumn(ctx);
                if (this.ifExistsColumn && this.supportsIfExistsColumn(ctx)) {
                    ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
                }
                boolean bl4 = parens = this.dropColumns.size() != 1 && REQUIRE_PARENS_ON_MULTI_ALTER.contains((Object)ctx.dialect()) || ctx.family() == SQLDialect.HANA;
                if (parens) {
                    ctx.sql(" (");
                } else {
                    ctx.sql(' ');
                }
                ctx.qualify(false, c -> c.visit(this.dropColumns));
                if (parens) {
                    ctx.sql(')');
                }
            }
            this.acceptCascade(ctx);
            if (ctx.family() == SQLDialect.ASE) {
                ctx.formatSeparator().visit(Keywords.K_WITH_NO_DATACOPY);
            }
            ctx.end(Clause.ALTER_TABLE_DROP);
        } else if (this.dropConstraint != null) {
            ctx.start(Clause.ALTER_TABLE_DROP);
            ctx.data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE, true, c -> {
                if (this.dropConstraintType == ConstraintType.FOREIGN_KEY && NO_SUPPORT_DROP_CONSTRAINT.contains((Object)c.dialect())) {
                    c.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_FOREIGN_KEY).sql(' ').visit(this.dropConstraint);
                } else if (this.dropConstraintType == ConstraintType.PRIMARY_KEY && NO_SUPPORT_DROP_CONSTRAINT.contains((Object)c.dialect())) {
                    c.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_PRIMARY_KEY);
                } else {
                    c.visit(this.dropConstraint.getUnqualifiedName().empty() ? Keywords.K_DROP : Keywords.K_DROP_CONSTRAINT).sql(' ');
                    if (this.ifExistsConstraint && !NO_SUPPORT_IF_EXISTS_CONSTRAINT.contains((Object)c.dialect())) {
                        c.visit(Keywords.K_IF_EXISTS).sql(' ');
                    }
                    c.visit(this.dropConstraint);
                }
                this.acceptCascade((Context<?>)c);
            });
            ctx.end(Clause.ALTER_TABLE_DROP);
        } else if (this.dropConstraintType == ConstraintType.PRIMARY_KEY) {
            ctx.start(Clause.ALTER_TABLE_DROP);
            ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_PRIMARY_KEY);
            ctx.end(Clause.ALTER_TABLE_DROP);
        }
        if (!omitAlterTable) {
            ctx.formatIndentEnd();
        }
    }

    private final boolean unqualifyRenameTo(Context<?> ctx) {
        return NO_SUPPORT_RENAME_QUALIFIED_TABLE.contains((Object)ctx.dialect()) && this.renameTo.getQualifiedName().qualified() && this.renameTo.getQualifiedName().qualifier().equals(this.table.getQualifiedName().qualifier());
    }

    private final Keyword addColumnKeyword(Context<?> ctx) {
        if (ctx.family() == SQLDialect.BIGQUERY) {
            return Keywords.K_ADD_COLUMN;
        }
        return Keywords.K_ADD;
    }

    private final void acceptCascade(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: {
                if (ctx.dialect().precedes(SQLDialect.H2_1_4_200)) break;
                AlterTableImpl.acceptCascade(ctx, this.dropCascade);
                break;
            }
            case ORACLE: {
                if (this.dropCascade != QOM.Cascade.CASCADE) break;
                if (this.dropConstraint != null) {
                    ctx.sql(' ').visit(Keywords.K_CASCADE);
                    break;
                }
                ctx.sql(' ').visit(Keywords.K_CASCADE).sql(' ').visit(Keywords.K_CONSTRAINTS);
                break;
            }
            default: {
                AlterTableImpl.acceptCascade(ctx, this.dropCascade);
            }
        }
    }

    private final void acceptFirstBeforeAfter(Context<?> ctx) {
        if (this.addFirst && ctx.family() != SQLDialect.FIREBIRD) {
            ctx.sql(' ').visit(Keywords.K_FIRST);
        } else if (this.addBefore != null) {
            ctx.sql(' ').visit(Keywords.K_BEFORE).sql(' ').qualify(false, c -> c.visit(this.addBefore));
        } else if (this.addAfter != null) {
            ctx.sql(' ').visit(Keywords.K_AFTER).sql(' ').qualify(false, c -> c.visit(this.addAfter));
        }
    }

    private final void acceptDropColumn(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                if (this.dropColumns.size() == 1) {
                    ctx.visit(Keywords.K_DROP_COLUMN);
                    break;
                }
                ctx.visit(Keywords.K_DROP);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Keywords.K_DROP_COLUMN);
                break;
            }
            default: {
                ctx.visit(Keywords.K_DROP);
            }
        }
    }

    @Pro
    private final void alterDefaultSQLServer(Context<?> ctx) {
        DSLContext create = DSL.using(ctx.configuration());
        String inlinedTable = create.renderInlined(this.table);
        String inlinedAlter = create.renderInlined(this.alterColumn);
        String inlinedAlterDefault = this.alterColumnDropDefault ? null : create.renderInlined(this.alterColumnDefault);
        ctx.visit(DSL.sql("DECLARE @constraint NVARCHAR(max);\nDECLARE @command NVARCHAR(max);\n\nSELECT @constraint = name\nFROM sys.default_constraints\nWHERE parent_object_id = object_id({0})\nAND parent_column_id = columnproperty(object_id({0}), {1}, 'ColumnId');\n\nIF @constraint IS NOT NULL\nBEGIN\n  SET @command = 'ALTER TABLE ' + {0} + ' DROP CONSTRAINT ' + @constraint\n  EXECUTE sp_executesql @command\n\n  SET @command = 'ALTER TABLE ' + {0} + ' ADD CONSTRAINT ' + @constraint + ' DEFAULT ' + {2} + ' FOR ' + {1}\n  EXECUTE sp_executesql @command\nEND\nELSE\nBEGIN\n  SET @command = 'ALTER TABLE ' + {0} + ' ADD DEFAULT ' + {2} + ' FOR ' + {1}\n  EXECUTE sp_executesql @command\nEND\n", DSL.inline(inlinedTable), DSL.inline(inlinedAlter), DSL.inline(inlinedAlterDefault)));
    }

    @Pro
    private final void renameColumnSQLServer(Context<?> ctx) {
        boolean ase = ctx.family() == SQLDialect.ASE;
        ctx.visit(Keywords.K_EXEC).sql(' ').sql("sp_rename").sql(' ').sql('\'').stringLiteral(true).visit(ase ? this.table.getQualifiedName().unquotedName() : this.table.getQualifiedName()).sql('.').visit(ase ? this.renameColumn.getUnqualifiedName().unquotedName() : this.renameColumn.getUnqualifiedName()).stringLiteral(false).sql("', ").qualify(false, c -> c.visit(this.renameColumnTo)).sql(", ").visit(ase ? DSL.inline("column") : DSL.inline("COLUMN"));
    }

    @Pro
    private final void renameConstraintASE(Context<?> ctx) {
        boolean qualify = ctx.qualify();
        ctx.visit(Keywords.K_EXEC).sql(' ').sql("sp_rename").sql(" '").stringLiteral(true).visit(this.table.getQualifiedName().unquotedName()).sql('.').visit(this.renameConstraint.getUnqualifiedName().unquotedName()).stringLiteral(false).sql("', ").visit(this.renameConstraintTo.getUnqualifiedName()).sql(", ").visit(DSL.inline("index"));
    }

    @Pro
    private final void renameTableSQLServer(Context<?> ctx) {
        ctx.visit(Keywords.K_EXEC).sql(' ').sql("sp_rename").sql(" '").stringLiteral(true).qualify(ctx.family() != SQLDialect.ASE && ctx.qualify(), c -> c.visit(this.table)).stringLiteral(false).sql("', ").qualify(ctx.qualify() && !this.unqualifyRenameTo(ctx), c -> c.visit(this.renameTo));
    }

    private final void alterColumnTypeAndNullabilityInBlock(Context<?> ctx) {
        Tools.begin(ctx, c1 -> {
            if (CreateTableImpl.REQUIRE_EXECUTE_IMMEDIATE.contains((Object)c1.dialect())) {
                Tools.beginExecuteImmediate(c1);
            }
            this.accept1((Context<?>)c1);
            if (CreateTableImpl.REQUIRE_EXECUTE_IMMEDIATE.contains((Object)c1.dialect())) {
                Tools.endExecuteImmediate(c1);
            } else {
                c1.sql(';').formatSeparator();
            }
            switch (c1.family()) {
                case DB2: {
                    AlterTableAlterStep<?> step = c1.dsl().alterTable(this.table).alterColumn(this.alterColumn);
                    c1.formatSeparator();
                    Tools.executeImmediate(c1, c2 -> c2.visit(this.alterColumnType.nullable() ? step.dropNotNull() : step.setNotNull()));
                    break;
                }
                case ORACLE: {
                    AlterTableFinalStep noNullability = c1.dsl().alterTable(this.table).alterColumn(this.alterColumn).set(this.alterColumnType.nullability(Nullability.DEFAULT));
                    String ora = this.alterColumnType.nullable() ? "ORA-01451" : "ORA-01442";
                    c1.formatIndentEnd().formatSeparator().visit(Keywords.K_EXCEPTION).formatIndentStart().formatSeparator().visit(Keywords.K_WHEN).sql(" others ").visit(Keywords.K_THEN).formatIndentStart().formatSeparator().visit(Keywords.K_IF).sql(" sqlerrm ").visit(Keywords.K_LIKE).sql(" '" + ora + "%' ").visit(Keywords.K_THEN).formatIndentStart().formatSeparator();
                    Tools.executeImmediate(c1, c2 -> c2.visit(noNullability));
                    c1.formatIndentEnd().formatSeparator().visit(Keywords.K_ELSE).formatIndentStart().formatSeparator().visit(Keywords.K_RAISE).sql(';').formatIndentEnd().formatSeparator().visit(Keywords.K_END_IF).sql(';').formatIndentEnd();
                    break;
                }
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    AlterTableAlterStep<?> step = c1.dsl().alterTable(this.table).alterColumn(this.alterColumn);
                    c1.visit(this.alterColumnType.nullable() ? step.dropNotNull() : step.setNotNull()).sql(';');
                    break;
                }
            }
        });
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }
}

