/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class HeaderColumnNameTranslateMappingStrategyBuilder<T> {
    private boolean forceCorrectRecordLength = false;

    public HeaderColumnNameTranslateMappingStrategy<T> build() {
        return new HeaderColumnNameTranslateMappingStrategy(this.forceCorrectRecordLength);
    }

    public HeaderColumnNameTranslateMappingStrategyBuilder<T> withForceCorrectRecordLength(boolean forceCorrectRecordLength) {
        this.forceCorrectRecordLength = forceCorrectRecordLength;
        return this;
    }
}

