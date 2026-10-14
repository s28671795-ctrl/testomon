/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class HeaderColumnNameMappingStrategyBuilder<T> {
    private boolean forceCorrectRecordLength = false;

    public HeaderColumnNameMappingStrategy<T> build() {
        return new HeaderColumnNameMappingStrategy(this.forceCorrectRecordLength);
    }

    public HeaderColumnNameMappingStrategyBuilder<T> withForceCorrectRecordLength(boolean forceCorrectRecordLength) {
        this.forceCorrectRecordLength = forceCorrectRecordLength;
        return this;
    }
}

