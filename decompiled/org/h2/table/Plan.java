/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.message.Trace;
import org.h2.table.PlanItem;
import org.h2.table.TableFilter;

public class Plan {
    private final TableFilter[] filters;
    private final HashMap<TableFilter, PlanItem> planItems = new HashMap();
    private final Expression[] allConditions;
    private final TableFilter[] allFilters;

    public Plan(TableFilter[] tableFilterArray, int n, Expression expression) {
        this.filters = new TableFilter[n];
        System.arraycopy(tableFilterArray, 0, this.filters, 0, n);
        ArrayList<Expression> arrayList = new ArrayList<Expression>();
        ArrayList arrayList2 = new ArrayList();
        if (expression != null) {
            arrayList.add(expression);
        }
        for (int i = 0; i < n; ++i) {
            TableFilter tableFilter2 = tableFilterArray[i];
            tableFilter2.visit(tableFilter -> {
                arrayList2.add(tableFilter);
                if (tableFilter.getJoinCondition() != null) {
                    arrayList.add(tableFilter.getJoinCondition());
                }
            });
        }
        this.allConditions = arrayList.toArray(new Expression[0]);
        this.allFilters = arrayList2.toArray(new TableFilter[0]);
    }

    public PlanItem getItem(TableFilter tableFilter) {
        return this.planItems.get(tableFilter);
    }

    public TableFilter[] getFilters() {
        return this.filters;
    }

    public void removeUnusableIndexConditions() {
        for (int i = 0; i < this.allFilters.length; ++i) {
            TableFilter tableFilter = this.allFilters[i];
            this.setEvaluatable(tableFilter, true);
            if (i < this.allFilters.length - 1) {
                tableFilter.optimizeFullCondition();
            }
            tableFilter.removeUnusableIndexConditions();
        }
        for (TableFilter tableFilter : this.allFilters) {
            this.setEvaluatable(tableFilter, false);
        }
    }

    public double calculateCost(SessionLocal sessionLocal, AllColumnsForPlan allColumnsForPlan) {
        Trace trace = sessionLocal.getTrace();
        if (trace.isDebugEnabled()) {
            trace.debug("Plan       : calculate cost for plan {0}", Arrays.toString(this.allFilters));
        }
        double d = 1.0;
        boolean bl = false;
        for (int i = 0; i < this.allFilters.length; ++i) {
            TableFilter tableFilter = this.allFilters[i];
            if (trace.isDebugEnabled()) {
                trace.debug("Plan       :   for table filter {0}", tableFilter);
            }
            PlanItem planItem = tableFilter.getBestPlanItem(sessionLocal, this.allFilters, i, allColumnsForPlan);
            this.planItems.put(tableFilter, planItem);
            if (trace.isDebugEnabled()) {
                trace.debug("Plan       :   best plan item cost {0} index {1}", planItem.cost, planItem.getIndex().getPlanSQL());
            }
            d += d * planItem.cost;
            this.setEvaluatable(tableFilter, true);
            Expression object = tableFilter.getJoinCondition();
            if (object == null || object.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR)) continue;
            bl = true;
            break;
        }
        if (bl) {
            d = Double.POSITIVE_INFINITY;
        }
        if (trace.isDebugEnabled()) {
            sessionLocal.getTrace().debug("Plan       : plan cost {0}", d);
        }
        for (TableFilter tableFilter : this.allFilters) {
            this.setEvaluatable(tableFilter, false);
        }
        return d;
    }

    private void setEvaluatable(TableFilter tableFilter, boolean bl) {
        tableFilter.setEvaluatable(tableFilter, bl);
        for (Expression expression : this.allConditions) {
            expression.setEvaluatable(tableFilter, bl);
        }
    }
}

