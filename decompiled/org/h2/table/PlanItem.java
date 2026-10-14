/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.index.Index;

public class PlanItem {
    double cost;
    private int[] masks;
    private Index index;
    private PlanItem joinPlan;
    private PlanItem nestedJoinPlan;

    void setMasks(int[] nArray) {
        this.masks = nArray;
    }

    int[] getMasks() {
        return this.masks;
    }

    void setIndex(Index index) {
        this.index = index;
    }

    public Index getIndex() {
        return this.index;
    }

    PlanItem getJoinPlan() {
        return this.joinPlan;
    }

    PlanItem getNestedJoinPlan() {
        return this.nestedJoinPlan;
    }

    void setJoinPlan(PlanItem planItem) {
        this.joinPlan = planItem;
    }

    void setNestedJoinPlan(PlanItem planItem) {
        this.nestedJoinPlan = planItem;
    }
}

