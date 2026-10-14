/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.customconverter;

import com.opencsv.bean.customconverter.ConverterLanguageToBoolean;

public class ConvertGermanToBoolean<T, I>
extends ConverterLanguageToBoolean<T, I> {
    private static final String WAHR = "wahr";
    private static final String FALSCH = "falsch";
    private static final String[] TRUE_STRINGS = new String[]{"wahr", "ja", "j", "1", "w"};
    private static final String[] FALSE_STRINGS = new String[]{"falsch", "nein", "n", "0", "f"};

    @Override
    protected String getLocalizedTrue() {
        return WAHR;
    }

    @Override
    protected String getLocalizedFalse() {
        return FALSCH;
    }

    @Override
    protected String[] getAllLocalizedTrueValues() {
        return TRUE_STRINGS;
    }

    @Override
    protected String[] getAllLocalizedFalseValues() {
        return FALSE_STRINGS;
    }
}

