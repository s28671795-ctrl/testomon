/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.processor;

import com.opencsv.bean.processor.StringProcessor;

public class ConvertEmptyOrBlankStringsToNull
implements StringProcessor {
    @Override
    public String processString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value;
    }

    @Override
    public void setParameterString(String value) {
    }
}

