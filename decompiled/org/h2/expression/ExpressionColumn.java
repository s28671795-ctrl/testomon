/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.command.query.Select;
import org.h2.command.query.SelectGroups;
import org.h2.command.query.SelectListColumnResolver;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.index.IndexCondition;
import org.h2.message.DbException;
import org.h2.mode.ModeFunction;
import org.h2.schema.Constant;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTinyint;

public final class ExpressionColumn
extends Expression {
    private final Database database;
    private final String schemaName;
    private final String tableAlias;
    private final String columnName;
    private final boolean rowId;
    private final boolean quotedName;
    private ColumnResolver columnResolver;
    private int queryLevel;
    private Column column;

    public ExpressionColumn(Database database, Column column) {
        this.database = database;
        this.column = column;
        this.schemaName = null;
        this.tableAlias = null;
        this.columnName = null;
        this.rowId = column.isRowId();
        this.quotedName = true;
    }

    public ExpressionColumn(Database database, String string, String string2, String string3) {
        this(database, string, string2, string3, true);
    }

    public ExpressionColumn(Database database, String string, String string2, String string3, boolean bl) {
        this.database = database;
        this.schemaName = string;
        this.tableAlias = string2;
        this.columnName = string3;
        this.rowId = false;
        this.quotedName = bl;
    }

    public ExpressionColumn(Database database, String string, String string2) {
        this.database = database;
        this.schemaName = string;
        this.tableAlias = string2;
        this.columnName = "_ROWID_";
        this.rowId = true;
        this.quotedName = true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        if (this.schemaName != null) {
            ParserUtil.quoteIdentifier(stringBuilder, this.schemaName, n).append('.');
        }
        if (this.tableAlias != null) {
            ParserUtil.quoteIdentifier(stringBuilder, this.tableAlias, n).append('.');
        }
        if (this.column != null) {
            if (this.columnResolver != null && this.columnResolver.hasDerivedColumnList()) {
                ParserUtil.quoteIdentifier(stringBuilder, this.columnResolver.getColumnName(this.column), n);
            } else {
                this.column.getSQL(stringBuilder, n);
            }
        } else if (this.rowId) {
            stringBuilder.append(this.columnName);
        } else {
            ParserUtil.quoteIdentifier(stringBuilder, this.columnName, n);
        }
        return stringBuilder;
    }

    public TableFilter getTableFilter() {
        return this.columnResolver == null ? null : this.columnResolver.getTableFilter();
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (this.tableAlias != null && !this.database.equalsIdentifiers(this.tableAlias, columnResolver.getTableAlias())) {
            return;
        }
        if (this.schemaName != null && !this.database.equalsIdentifiers(this.schemaName, columnResolver.getSchemaName())) {
            return;
        }
        if (this.rowId) {
            Column column = columnResolver.getRowIdColumn();
            if (column != null) {
                this.mapColumn(columnResolver, column, n);
            }
            return;
        }
        Column column = columnResolver.findColumn(this.columnName);
        if (column != null) {
            this.mapColumn(columnResolver, column, n);
            return;
        }
        Column[] columnArray = columnResolver.getSystemColumns();
        for (int i = 0; columnArray != null && i < columnArray.length; ++i) {
            column = columnArray[i];
            if (!this.database.equalsIdentifiers(this.columnName, column.getName())) continue;
            this.mapColumn(columnResolver, column, n);
            return;
        }
    }

    private void mapColumn(ColumnResolver columnResolver, Column column, int n) {
        if (this.columnResolver == null) {
            this.queryLevel = n;
            this.column = column;
            this.columnResolver = columnResolver;
        } else if (this.queryLevel == n && this.columnResolver != columnResolver && !(columnResolver instanceof SelectListColumnResolver)) {
            throw DbException.get(90059, this.columnName);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        if (this.columnResolver == null) {
            Constant constant;
            Schema schema = sessionLocal.getDatabase().findSchema(this.tableAlias == null ? sessionLocal.getCurrentSchemaName() : this.tableAlias);
            if (schema != null && (constant = schema.findConstant(this.columnName)) != null) {
                return constant.getValue();
            }
            return this.optimizeOther();
        }
        return this.columnResolver.optimize(this, this.column);
    }

    private Expression optimizeOther() {
        Expression expression;
        if (this.tableAlias == null && !this.quotedName && (expression = ModeFunction.getCompatibilityDateTimeValueFunction(this.database, StringUtils.toUpperEnglish(this.columnName), -1)) != null) {
            return expression;
        }
        throw this.getColumnException(42122);
    }

    public DbException getColumnException(int n) {
        String string = this.columnName;
        if (this.tableAlias != null) {
            string = this.schemaName != null ? this.schemaName + '.' + this.tableAlias + '.' + string : this.tableAlias + '.' + string;
        }
        return DbException.get(n, string);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        Select select = this.columnResolver.getSelect();
        if (select == null) {
            throw DbException.get(90016, this.getTraceSQL());
        }
        if (n == 0) {
            return;
        }
        SelectGroups selectGroups = select.getGroupDataIfCurrent(false);
        if (selectGroups == null) {
            return;
        }
        Value value = (Value)selectGroups.getCurrentGroupExprData(this);
        if (value == null) {
            selectGroups.setCurrentGroupExprData(this, this.columnResolver.getValue(this.column));
        } else if (!select.isGroupWindowStage2() && !sessionLocal.areEqual(this.columnResolver.getValue(this.column), value)) {
            throw DbException.get(90016, this.getTraceSQL());
        }
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Object object;
        Select select = this.columnResolver.getSelect();
        if (select != null && (object = select.getGroupDataIfCurrent(false)) != null) {
            Value value = (Value)((SelectGroups)object).getCurrentGroupExprData(this);
            if (value != null) {
                return value;
            }
            if (select.isGroupWindowStage2()) {
                throw DbException.get(90016, this.getTraceSQL());
            }
        }
        if ((object = this.columnResolver.getValue(this.column)) == null) {
            if (select == null) {
                throw DbException.get(23502, this.getTraceSQL());
            }
            throw DbException.get(90016, this.getTraceSQL());
        }
        return object;
    }

    @Override
    public TypeInfo getType() {
        return this.column != null ? this.column.getType() : (this.rowId ? TypeInfo.TYPE_BIGINT : TypeInfo.TYPE_UNKNOWN);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
    }

    public Column getColumn() {
        return this.column;
    }

    public String getOriginalColumnName() {
        return this.columnName;
    }

    public String getOriginalTableAliasName() {
        return this.tableAlias;
    }

    @Override
    public String getColumnName(SessionLocal sessionLocal, int n) {
        if (this.column != null) {
            if (this.columnResolver != null) {
                return this.columnResolver.getColumnName(this.column);
            }
            return this.column.getName();
        }
        return this.columnName;
    }

    @Override
    public String getSchemaName() {
        Table table = this.column.getTable();
        return table == null ? null : table.getSchema().getName();
    }

    @Override
    public String getTableName() {
        Table table = this.column.getTable();
        return table == null ? null : table.getName();
    }

    @Override
    public String getAlias(SessionLocal sessionLocal, int n) {
        if (this.column != null) {
            if (this.columnResolver != null) {
                return this.columnResolver.getColumnName(this.column);
            }
            return this.column.getName();
        }
        if (this.tableAlias != null) {
            return this.tableAlias + '.' + this.columnName;
        }
        return this.columnName;
    }

    @Override
    public String getColumnNameForView(SessionLocal sessionLocal, int n) {
        return this.getAlias(sessionLocal, n);
    }

    @Override
    public boolean isIdentity() {
        return this.column.isIdentity();
    }

    @Override
    public int getNullable() {
        return this.column.isNullable() ? 1 : 0;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 1: {
                return false;
            }
            case 0: {
                return this.queryLevel < expressionVisitor.getQueryLevel();
            }
            case 3: {
                if (expressionVisitor.getQueryLevel() < this.queryLevel) {
                    return true;
                }
                if (this.getTableFilter() == null) {
                    return false;
                }
                return this.getTableFilter().isEvaluatable();
            }
            case 4: {
                expressionVisitor.addDataModificationId(this.column.getTable().getMaxDataModificationId());
                return true;
            }
            case 6: {
                return this.columnResolver != expressionVisitor.getResolver();
            }
            case 7: {
                if (this.column != null) {
                    expressionVisitor.addDependency(this.column.getTable());
                }
                return true;
            }
            case 9: {
                if (this.column == null) {
                    throw DbException.get(42122, this.getTraceSQL());
                }
                expressionVisitor.addColumn1(this.column);
                return true;
            }
            case 10: {
                if (this.column == null) {
                    throw DbException.get(42122, this.getTraceSQL());
                }
                expressionVisitor.addColumn2(this.column);
                return true;
            }
            case 11: {
                if (this.column == null) {
                    throw DbException.get(42122, this.getTraceSQL());
                }
                if (!expressionVisitor.getColumnResolvers().contains(this.columnResolver)) break;
                int n = expressionVisitor.getQueryLevel();
                if (n > 0) {
                    if (this.queryLevel > 0) {
                        --this.queryLevel;
                        return true;
                    }
                    throw DbException.getInternalError("queryLevel=0");
                }
                return this.queryLevel > 0;
            }
        }
        return true;
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        TableFilter tableFilter2 = this.getTableFilter();
        if (tableFilter == tableFilter2 && this.column.getType().getValueType() == 8) {
            tableFilter.addIndexCondition(IndexCondition.get(0, this, ValueExpression.TRUE));
        }
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        Value value;
        Expression expression = this.optimize(sessionLocal);
        if (expression != this) {
            return expression.getNotIfPossible(sessionLocal);
        }
        switch (this.column.getType().getValueType()) {
            case 8: {
                value = ValueBoolean.FALSE;
                break;
            }
            case 9: {
                value = ValueTinyint.get((byte)0);
                break;
            }
            case 10: {
                value = ValueSmallint.get((short)0);
                break;
            }
            case 11: {
                value = ValueInteger.get(0);
                break;
            }
            case 12: {
                value = ValueBigint.get(0L);
                break;
            }
            case 13: {
                value = ValueNumeric.ZERO;
                break;
            }
            case 14: {
                value = ValueReal.ZERO;
                break;
            }
            case 15: {
                value = ValueDouble.ZERO;
                break;
            }
            case 16: {
                value = ValueDecfloat.ZERO;
                break;
            }
            default: {
                return null;
            }
        }
        return new Comparison(0, this, ValueExpression.get(value), false);
    }
}

