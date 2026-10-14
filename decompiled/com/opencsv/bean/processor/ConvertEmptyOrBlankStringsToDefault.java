/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.processor;

import com.opencsv.bean.processor.StringProcessor;

public class ConvertEmptyOrBlankStringsToDefault
implements StringProcessor {
    String defaultValue;

    @Override
    public String processString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return this.defaultValue;
        }
        return value;
    }

    @Override
    public void setParameterString(String value) {
        this.defaultValue = value;
    }
}

