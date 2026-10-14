/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.FuzzyMappingStrategy;

public class FuzzyMappingStrategyBuilder<T> {
    private boolean forceCorrectRecordLength = false;

    public FuzzyMappingStrategy<T> build() {
        return new FuzzyMappingStrategy(this.forceCorrectRecordLength);
    }

    public FuzzyMappingStrategyBuilder<T> withForceCorrectRecordLength(boolean forceCorrectRecordLength) {
        this.forceCorrectRecordLength = forceCorrectRecordLength;
        return this;
    }
}

