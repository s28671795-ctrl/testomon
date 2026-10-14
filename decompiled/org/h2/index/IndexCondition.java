/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.table.Column;
import org.h2.table.TableType;
import org.h2.value.Value;
import org.h2.value.ValueArray;

public class IndexCondition {
    public static final int EQUALITY = 1;
    public static final int START = 2;
    public static final int END = 4;
    public static final int RANGE = 6;
    public static final int ALWAYS_FALSE = 8;
    public static final int SPATIAL_INTERSECTS = 16;
    private final Column column;
    private final int compareType;
    private final Expression expression;
    private List<Expression> expressionList;
    private Query expressionQuery;

    private IndexCondition(int n, ExpressionColumn expressionColumn, Expression expression) {
        this.compareType = n;
        this.column = expressionColumn == null ? null : expressionColumn.getColumn();
        this.expression = expression;
    }

    public static IndexCondition get(int n, ExpressionColumn expressionColumn, Expression expression) {
        return new IndexCondition(n, expressionColumn, expression);
    }

    public static IndexCondition getInList(ExpressionColumn expressionColumn, List<Expression> list) {
        IndexCondition indexCondition = new IndexCondition(10, expressionColumn, null);
        indexCondition.expressionList = list;
        return indexCondition;
    }

    public static IndexCondition getInArray(ExpressionColumn expressionColumn, Expression expression) {
        return new IndexCondition(11, expressionColumn, expression);
    }

    public static IndexCondition getInQuery(ExpressionColumn expressionColumn, Query query) {
        assert (query.isRandomAccessResult());
        IndexCondition indexCondition = new IndexCondition(12, expressionColumn, null);
        indexCondition.expressionQuery = query;
        return indexCondition;
    }

    public Value getCurrentValue(SessionLocal sessionLocal) {
        return this.expression.getValue(sessionLocal);
    }

    public Value[] getCurrentValueList(SessionLocal sessionLocal) {
        Object object;
        TreeSet<Value> treeSet = new TreeSet<Value>(sessionLocal.getDatabase().getCompareMode());
        if (this.compareType == 10) {
            for (Expression expression : this.expressionList) {
                Value value = expression.getValue(sessionLocal);
                value = this.column.convert(sessionLocal, value);
                treeSet.add(value);
            }
        } else if (this.compareType == 11) {
            object = this.expression.getValue(sessionLocal);
            if (object instanceof ValueArray) {
                for (Value value : ((ValueArray)object).getList()) {
                    treeSet.add(value);
                }
            }
        } else {
            throw DbException.getInternalError("compareType = " + this.compareType);
        }
        object = treeSet.toArray(new Value[treeSet.size()]);
        Arrays.sort(object, sessionLocal.getDatabase().getCompareMode());
        return object;
    }

    public ResultInterface getCurrentResult() {
        return this.expressionQuery.query(0L);
    }

    public String getSQL(int n) {
        if (this.compareType == 9) {
            return "FALSE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.column.getSQL(stringBuilder, n);
        switch (this.compareType) {
            case 0: {
                stringBuilder.append(" = ");
                break;
            }
            case 6: {
                stringBuilder.append(this.expression.isNullConstant() || this.column.getType().getValueType() == 8 && this.expression.isConstant() ? " IS " : " IS NOT DISTINCT FROM ");
                break;
            }
            case 5: {
                stringBuilder.append(" >= ");
                break;
            }
            case 3: {
                stringBuilder.append(" > ");
                break;
            }
            case 4: {
                stringBuilder.append(" <= ");
                break;
            }
            case 2: {
                stringBuilder.append(" < ");
                break;
            }
            case 10: {
                Expression.writeExpressions(stringBuilder.append(" IN("), this.expressionList, n).append(')');
                break;
            }
            case 11: {
                return this.expression.getSQL(stringBuilder.append(" = ANY("), n, 0).append(')').toString();
            }
            case 12: {
                stringBuilder.append(" IN(");
                stringBuilder.append(this.expressionQuery.getPlanSQL(n));
                stringBuilder.append(')');
                break;
            }
            case 8: {
                stringBuilder.append(" && ");
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.compareType);
            }
        }
        if (this.expression != null) {
            this.expression.getSQL(stringBuilder, n, 0);
        }
        return stringBuilder.toString();
    }

    public int getMask(ArrayList<IndexCondition> arrayList) {
        switch (this.compareType) {
            case 9: {
                return 8;
            }
            case 0: 
            case 6: {
                return 1;
            }
            case 10: 
            case 11: 
            case 12: {
                if (arrayList.size() > 1 && TableType.TABLE != this.column.getTable().getTableType()) {
                    return 0;
                }
                return 1;
            }
            case 3: 
            case 5: {
                return 2;
            }
            case 2: 
            case 4: {
                return 4;
            }
            case 8: {
                return 16;
            }
        }
        throw DbException.getInternalError("type=" + this.compareType);
    }

    public boolean isAlwaysFalse() {
        return this.compareType == 9;
    }

    public boolean isStart() {
        switch (this.compareType) {
            case 0: 
            case 3: 
            case 5: 
            case 6: {
                return true;
            }
        }
        return false;
    }

    public boolean isEnd() {
        switch (this.compareType) {
            case 0: 
            case 2: 
            case 4: 
            case 6: {
                return true;
            }
        }
        return false;
    }

    public boolean isSpatialIntersects() {
        switch (this.compareType) {
            case 8: {
                return true;
            }
        }
        return false;
    }

    public int getCompareType() {
        return this.compareType;
    }

    public Column getColumn() {
        return this.column;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public List<Expression> getExpressionList() {
        return this.expressionList;
    }

    public Query getExpressionQuery() {
        return this.expressionQuery;
    }

    public boolean isEvaluatable() {
        if (this.expression != null) {
            return this.expression.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR);
        }
        if (this.expressionList != null) {
            for (Expression expression : this.expressionList) {
                if (expression.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR)) continue;
                return false;
            }
            return true;
        }
        return this.expressionQuery.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("column=").append(this.column).append(", compareType=");
        return IndexCondition.compareTypeToString(stringBuilder, this.compareType).append(", expression=").append(this.expression).append(", expressionList=").append(this.expressionList).append(", expressionQuery=").append(this.expressionQuery).toString();
    }

    private static StringBuilder compareTypeToString(StringBuilder stringBuilder, int n) {
        boolean bl = false;
        if ((n & 1) == 1) {
            bl = true;
            stringBuilder.append("EQUALITY");
        }
        if ((n & 2) == 2) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            stringBuilder.append("START");
        }
        if ((n & 4) == 4) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            stringBuilder.append("END");
        }
        if ((n & 8) == 8) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            stringBuilder.append("ALWAYS_FALSE");
        }
        if ((n & 0x10) == 16) {
            if (bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("SPATIAL_INTERSECTS");
        }
        return stringBuilder;
    }
}

