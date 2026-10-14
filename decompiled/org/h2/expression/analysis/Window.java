/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import org.h2.command.query.QueryOrderBy;
import org.h2.command.query.Select;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.analysis.WindowFrame;
import org.h2.expression.analysis.WindowFrameBound;
import org.h2.expression.analysis.WindowFrameBoundType;
import org.h2.expression.analysis.WindowFrameUnits;
import org.h2.message.DbException;
import org.h2.result.SortOrder;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueRow;

public final class Window {
    private ArrayList<Expression> partitionBy;
    private ArrayList<QueryOrderBy> orderBy;
    private WindowFrame frame;
    private String parent;

    public static void appendOrderBy(StringBuilder stringBuilder, ArrayList<QueryOrderBy> arrayList, int n, boolean bl) {
        if (arrayList != null && !arrayList.isEmpty()) {
            Window.appendOrderByStart(stringBuilder);
            for (int i = 0; i < arrayList.size(); ++i) {
                QueryOrderBy queryOrderBy = arrayList.get(i);
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                queryOrderBy.expression.getUnenclosedSQL(stringBuilder, n);
                SortOrder.typeToString(stringBuilder, queryOrderBy.sortType);
            }
        } else if (bl) {
            Window.appendOrderByStart(stringBuilder);
            stringBuilder.append("NULL");
        }
    }

    private static void appendOrderByStart(StringBuilder stringBuilder) {
        if (stringBuilder.charAt(stringBuilder.length() - 1) != '(') {
            stringBuilder.append(' ');
        }
        stringBuilder.append("ORDER BY ");
    }

    public Window(String string, ArrayList<Expression> arrayList, ArrayList<QueryOrderBy> arrayList2, WindowFrame windowFrame) {
        this.parent = string;
        this.partitionBy = arrayList;
        this.orderBy = arrayList2;
        this.frame = windowFrame;
    }

    public void mapColumns(ColumnResolver columnResolver, int n) {
        this.resolveWindows(columnResolver);
        if (this.partitionBy != null) {
            for (Expression object : this.partitionBy) {
                object.mapColumns(columnResolver, n, 1);
            }
        }
        if (this.orderBy != null) {
            for (QueryOrderBy queryOrderBy : this.orderBy) {
                queryOrderBy.expression.mapColumns(columnResolver, n, 1);
            }
        }
        if (this.frame != null) {
            this.frame.mapColumns(columnResolver, n, 1);
        }
    }

    private void resolveWindows(ColumnResolver columnResolver) {
        if (this.parent != null) {
            Window window;
            Select select = columnResolver.getSelect();
            while ((window = select.getWindow(this.parent)) == null) {
                if ((select = select.getParentSelect()) != null) continue;
                throw DbException.get(90136, this.parent);
            }
            window.resolveWindows(columnResolver);
            if (this.partitionBy == null) {
                this.partitionBy = window.partitionBy;
            }
            if (this.orderBy == null) {
                this.orderBy = window.orderBy;
            }
            if (this.frame == null) {
                this.frame = window.frame;
            }
            this.parent = null;
        }
    }

    public void optimize(SessionLocal sessionLocal) {
        Object object;
        Iterator<Object> iterator2;
        if (this.partitionBy != null) {
            iterator2 = this.partitionBy.listIterator();
            while (iterator2.hasNext()) {
                object = ((Expression)iterator2.next()).optimize(sessionLocal);
                if (((Expression)object).isConstant()) {
                    iterator2.remove();
                    continue;
                }
                iterator2.set(object);
            }
            if (this.partitionBy.isEmpty()) {
                this.partitionBy = null;
            }
        }
        if (this.orderBy != null) {
            iterator2 = this.orderBy.iterator();
            while (iterator2.hasNext()) {
                object = (QueryOrderBy)iterator2.next();
                Expression expression = ((QueryOrderBy)object).expression.optimize(sessionLocal);
                if (expression.isConstant()) {
                    iterator2.remove();
                    continue;
                }
                ((QueryOrderBy)object).expression = expression;
            }
            if (this.orderBy.isEmpty()) {
                this.orderBy = null;
            }
        }
        if (this.frame != null) {
            this.frame.optimize(sessionLocal);
        }
    }

    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        if (this.partitionBy != null) {
            for (Expression object : this.partitionBy) {
                object.setEvaluatable(tableFilter, bl);
            }
        }
        if (this.orderBy != null) {
            for (QueryOrderBy queryOrderBy : this.orderBy) {
                queryOrderBy.expression.setEvaluatable(tableFilter, bl);
            }
        }
    }

    public ArrayList<QueryOrderBy> getOrderBy() {
        return this.orderBy;
    }

    public WindowFrame getWindowFrame() {
        return this.frame;
    }

    public boolean isOrdered() {
        if (this.orderBy != null) {
            return true;
        }
        if (this.frame != null && this.frame.getUnits() == WindowFrameUnits.ROWS) {
            WindowFrameBound windowFrameBound;
            return this.frame.getStarting().getType() != WindowFrameBoundType.UNBOUNDED_PRECEDING || (windowFrameBound = this.frame.getFollowing()) == null || windowFrameBound.getType() != WindowFrameBoundType.UNBOUNDED_FOLLOWING;
        }
        return false;
    }

    public Value getCurrentKey(SessionLocal sessionLocal) {
        if (this.partitionBy == null) {
            return null;
        }
        int n = this.partitionBy.size();
        if (n == 1) {
            return this.partitionBy.get(0).getValue(sessionLocal);
        }
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            Expression expression = this.partitionBy.get(i);
            valueArray[i] = expression.getValue(sessionLocal);
        }
        return ValueRow.get(valueArray);
    }

    public StringBuilder getSQL(StringBuilder stringBuilder, int n, boolean bl) {
        stringBuilder.append("OVER (");
        if (this.partitionBy != null) {
            stringBuilder.append("PARTITION BY ");
            for (int i = 0; i < this.partitionBy.size(); ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                this.partitionBy.get(i).getUnenclosedSQL(stringBuilder, n);
            }
        }
        Window.appendOrderBy(stringBuilder, this.orderBy, n, bl);
        if (this.frame != null) {
            if (stringBuilder.charAt(stringBuilder.length() - 1) != '(') {
                stringBuilder.append(' ');
            }
            this.frame.getSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }

    public void updateAggregate(SessionLocal sessionLocal, int n) {
        if (this.partitionBy != null) {
            for (Expression object : this.partitionBy) {
                object.updateAggregate(sessionLocal, n);
            }
        }
        if (this.orderBy != null) {
            for (QueryOrderBy queryOrderBy : this.orderBy) {
                queryOrderBy.expression.updateAggregate(sessionLocal, n);
            }
        }
        if (this.frame != null) {
            this.frame.updateAggregate(sessionLocal, n);
        }
    }

    public String toString() {
        return this.getSQL(new StringBuilder(), 3, false).toString();
    }
}

