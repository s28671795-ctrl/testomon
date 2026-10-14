/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.h2.command.query.QueryOrderBy;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.mode.DefaultNullOrdering;
import org.h2.table.Column;
import org.h2.table.TableFilter;
import org.h2.util.Utils;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class SortOrder
implements Comparator<Value[]> {
    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;
    public static final int NULLS_FIRST = 2;
    public static final int NULLS_LAST = 4;
    private final SessionLocal session;
    private final int[] queryColumnIndexes;
    private final int[] sortTypes;
    private final ArrayList<QueryOrderBy> orderList;

    public SortOrder(SessionLocal sessionLocal, int[] nArray) {
        this(sessionLocal, nArray, new int[nArray.length], null);
    }

    public SortOrder(SessionLocal sessionLocal, int[] nArray, int[] nArray2, ArrayList<QueryOrderBy> arrayList) {
        this.session = sessionLocal;
        this.queryColumnIndexes = nArray;
        this.sortTypes = nArray2;
        this.orderList = arrayList;
    }

    public StringBuilder getSQL(StringBuilder stringBuilder, Expression[] expressionArray, int n, int n2) {
        int n3 = 0;
        for (int n4 : this.queryColumnIndexes) {
            if (n3 > 0) {
                stringBuilder.append(", ");
            }
            if (n4 < n) {
                stringBuilder.append(n4 + 1);
            } else {
                expressionArray[n4].getUnenclosedSQL(stringBuilder, n2);
            }
            SortOrder.typeToString(stringBuilder, this.sortTypes[n3++]);
        }
        return stringBuilder;
    }

    public static void typeToString(StringBuilder stringBuilder, int n) {
        if ((n & 1) != 0) {
            stringBuilder.append(" DESC");
        }
        if ((n & 2) != 0) {
            stringBuilder.append(" NULLS FIRST");
        } else if ((n & 4) != 0) {
            stringBuilder.append(" NULLS LAST");
        }
    }

    @Override
    public int compare(Value[] valueArray, Value[] valueArray2) {
        int n = this.queryColumnIndexes.length;
        for (int i = 0; i < n; ++i) {
            boolean bl;
            int n2 = this.queryColumnIndexes[i];
            int n3 = this.sortTypes[i];
            Value value = valueArray[n2];
            Value value2 = valueArray2[n2];
            boolean bl2 = value == ValueNull.INSTANCE;
            boolean bl3 = bl = value2 == ValueNull.INSTANCE;
            if (bl2 || bl) {
                if (bl2 == bl) continue;
                return this.session.getDatabase().getDefaultNullOrdering().compareNull(bl2, n3);
            }
            int n4 = this.session.compare(value, value2);
            if (n4 == 0) continue;
            return (n3 & 1) == 0 ? n4 : -n4;
        }
        return 0;
    }

    public void sort(ArrayList<Value[]> arrayList) {
        arrayList.sort(this);
    }

    public void sort(ArrayList<Value[]> arrayList, int n, int n2) {
        if (n2 == 1 && n == 0) {
            arrayList.set(0, Collections.min(arrayList, this));
            return;
        }
        Value[][] valueArray = (Value[][])arrayList.toArray((T[])new Value[0][]);
        Utils.sortTopN(valueArray, n, n2, this);
        for (int i = n; i < n2; ++i) {
            arrayList.set(i, valueArray[i]);
        }
    }

    public int[] getQueryColumnIndexes() {
        return this.queryColumnIndexes;
    }

    public Column getColumn(int n, TableFilter tableFilter) {
        if (this.orderList == null) {
            return null;
        }
        QueryOrderBy queryOrderBy = this.orderList.get(n);
        Expression expression = queryOrderBy.expression;
        if (expression == null) {
            return null;
        }
        if ((expression = expression.getNonAliasExpression()).isConstant()) {
            return null;
        }
        if (!(expression instanceof ExpressionColumn)) {
            return null;
        }
        ExpressionColumn expressionColumn = (ExpressionColumn)expression;
        if (expressionColumn.getTableFilter() != tableFilter) {
            return null;
        }
        return expressionColumn.getColumn();
    }

    public int[] getSortTypes() {
        return this.sortTypes;
    }

    public ArrayList<QueryOrderBy> getOrderList() {
        return this.orderList;
    }

    public int[] getSortTypesWithNullOrdering() {
        return SortOrder.addNullOrdering(this.session.getDatabase(), (int[])this.sortTypes.clone());
    }

    public static int[] addNullOrdering(Database database, int[] nArray) {
        DefaultNullOrdering defaultNullOrdering = database.getDefaultNullOrdering();
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            nArray[i] = defaultNullOrdering.addExplicitNullOrdering(nArray[i]);
        }
        return nArray;
    }

    public Comparator<Value> getRowValueComparator() {
        return (value, value2) -> this.compare(((ValueRow)value).getList(), ((ValueRow)value2).getList());
    }
}

