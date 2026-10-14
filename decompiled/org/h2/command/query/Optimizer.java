/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.BitSet;
import java.util.Random;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.table.Plan;
import org.h2.table.PlanItem;
import org.h2.table.TableFilter;
import org.h2.util.Permutations;

class Optimizer {
    private static final int MAX_BRUTE_FORCE_FILTERS = 7;
    private static final int MAX_BRUTE_FORCE = 2000;
    private static final int MAX_GENETIC = 500;
    private long startNs;
    private BitSet switched;
    private final TableFilter[] filters;
    private final Expression condition;
    private final SessionLocal session;
    private Plan bestPlan;
    private TableFilter topFilter;
    private double cost;
    private Random random;
    private final AllColumnsForPlan allColumnsSet;

    Optimizer(TableFilter[] tableFilterArray, Expression expression, SessionLocal sessionLocal) {
        this.filters = tableFilterArray;
        this.condition = expression;
        this.session = sessionLocal;
        this.allColumnsSet = new AllColumnsForPlan(tableFilterArray);
    }

    private static int getMaxBruteForceFilters(int n) {
        int n2 = 0;
        int n3 = n;
        int n4 = n;
        while (n3 > 0 && n4 * (n3 * (n3 - 1) / 2) < 2000) {
            n4 *= --n3;
            ++n2;
        }
        return n2;
    }

    private void calculateBestPlan() {
        this.cost = -1.0;
        if (this.filters.length == 1) {
            this.testPlan(this.filters);
        } else {
            this.startNs = System.nanoTime();
            if (this.filters.length <= 7) {
                this.calculateBruteForceAll();
            } else {
                this.calculateBruteForceSome();
                this.random = new Random(0L);
                this.calculateGenetic();
            }
        }
    }

    private void calculateFakePlan() {
        this.cost = -1.0;
        this.bestPlan = new Plan(this.filters, this.filters.length, this.condition);
    }

    private boolean canStop(int n) {
        return (n & 0x7F) == 0 && this.cost >= 0.0 && (double)(System.nanoTime() - this.startNs) > this.cost * 100000.0;
    }

    private void calculateBruteForceAll() {
        TableFilter[] tableFilterArray = new TableFilter[this.filters.length];
        Permutations<TableFilter> permutations = Permutations.create(this.filters, tableFilterArray);
        int n = 0;
        while (!this.canStop(n) && permutations.next()) {
            this.testPlan(tableFilterArray);
            ++n;
        }
    }

    private void calculateBruteForceSome() {
        int n = Optimizer.getMaxBruteForceFilters(this.filters.length);
        TableFilter[] tableFilterArray = new TableFilter[this.filters.length];
        Permutations<TableFilter> permutations = Permutations.create(this.filters, tableFilterArray, n);
        int n2 = 0;
        while (!this.canStop(n2) && permutations.next()) {
            int n3;
            for (TableFilter tableFilter : this.filters) {
                tableFilter.setUsed(false);
            }
            for (n3 = 0; n3 < n; ++n3) {
                tableFilterArray[n3].setUsed(true);
            }
            for (n3 = n; n3 < this.filters.length; ++n3) {
                double d = -1.0;
                int n4 = -1;
                for (int i = 0; i < this.filters.length; ++i) {
                    if (this.filters[i].isUsed()) continue;
                    if (n3 == this.filters.length - 1) {
                        n4 = i;
                        break;
                    }
                    tableFilterArray[n3] = this.filters[i];
                    Plan plan = new Plan(tableFilterArray, n3 + 1, this.condition);
                    double d2 = plan.calculateCost(this.session, this.allColumnsSet);
                    if (!(d < 0.0) && !(d2 < d)) continue;
                    d = d2;
                    n4 = i;
                }
                this.filters[n4].setUsed(true);
                tableFilterArray[n3] = this.filters[n4];
            }
            this.testPlan(tableFilterArray);
            ++n2;
        }
    }

    private void calculateGenetic() {
        TableFilter[] tableFilterArray = new TableFilter[this.filters.length];
        TableFilter[] tableFilterArray2 = new TableFilter[this.filters.length];
        for (int i = 0; i < 500 && !this.canStop(i); ++i) {
            boolean bl;
            boolean bl2 = bl = (i & 0x7F) == 0;
            if (!bl) {
                System.arraycopy(tableFilterArray, 0, tableFilterArray2, 0, this.filters.length);
                if (!this.shuffleTwo(tableFilterArray2)) {
                    bl = true;
                }
            }
            if (bl) {
                this.switched = new BitSet();
                System.arraycopy(this.filters, 0, tableFilterArray, 0, this.filters.length);
                this.shuffleAll(tableFilterArray);
                System.arraycopy(tableFilterArray, 0, tableFilterArray2, 0, this.filters.length);
            }
            if (!this.testPlan(tableFilterArray2)) continue;
            this.switched = new BitSet();
            System.arraycopy(tableFilterArray2, 0, tableFilterArray, 0, this.filters.length);
        }
    }

    private boolean testPlan(TableFilter[] tableFilterArray) {
        Plan plan = new Plan(tableFilterArray, tableFilterArray.length, this.condition);
        double d = plan.calculateCost(this.session, this.allColumnsSet);
        if (this.cost < 0.0 || d < this.cost) {
            this.cost = d;
            this.bestPlan = plan;
            return true;
        }
        return false;
    }

    private void shuffleAll(TableFilter[] tableFilterArray) {
        for (int i = 0; i < tableFilterArray.length - 1; ++i) {
            int n = i + this.random.nextInt(tableFilterArray.length - i);
            if (n == i) continue;
            TableFilter tableFilter = tableFilterArray[i];
            tableFilterArray[i] = tableFilterArray[n];
            tableFilterArray[n] = tableFilter;
        }
    }

    private boolean shuffleTwo(TableFilter[] tableFilterArray) {
        int n;
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < 20; ++n) {
            int n4;
            n2 = this.random.nextInt(tableFilterArray.length);
            if (n2 == (n3 = this.random.nextInt(tableFilterArray.length))) continue;
            if (n2 < n3) {
                n4 = n2;
                n2 = n3;
                n3 = n4;
            }
            if (this.switched.get(n4 = n2 * tableFilterArray.length + n3)) continue;
            this.switched.set(n4);
            break;
        }
        if (n == 20) {
            return false;
        }
        TableFilter tableFilter = tableFilterArray[n2];
        tableFilterArray[n2] = tableFilterArray[n3];
        tableFilterArray[n3] = tableFilter;
        return true;
    }

    void optimize(boolean bl) {
        if (bl) {
            this.calculateFakePlan();
        } else {
            this.calculateBestPlan();
            this.bestPlan.removeUnusableIndexConditions();
        }
        TableFilter[] tableFilterArray = this.bestPlan.getFilters();
        this.topFilter = tableFilterArray[0];
        for (int i = 0; i < tableFilterArray.length - 1; ++i) {
            tableFilterArray[i].addJoin(tableFilterArray[i + 1], false, null);
        }
        if (bl) {
            return;
        }
        for (TableFilter tableFilter : tableFilterArray) {
            PlanItem planItem = this.bestPlan.getItem(tableFilter);
            tableFilter.setPlanItem(planItem);
        }
    }

    public TableFilter getTopFilter() {
        return this.topFilter;
    }

    double getCost() {
        return this.cost;
    }
}

