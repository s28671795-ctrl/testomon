/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.processor;

import com.opencsv.bean.processor.StringProcessor;

public class ConvertWordNullToNull
implements StringProcessor {
    @Override
    public String processString(String value) {
        return "null".equalsIgnoreCase(value) ? null : value;
    }

    @Override
    public void setParameterString(String value) {
    }
}

