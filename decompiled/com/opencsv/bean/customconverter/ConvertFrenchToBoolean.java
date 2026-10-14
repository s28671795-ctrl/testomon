/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.customconverter;

import com.opencsv.bean.customconverter.ConverterLanguageToBoolean;

public class ConvertFrenchToBoolean<T, I>
extends ConverterLanguageToBoolean<T, I> {
    private static final String VRAI = "vrai";
    private static final String FAUX = "faux";
    private static final String[] TRUE_STRINGS = new String[]{"vrai", "oui", "o", "1", "v"};
    private static final String[] FALSE_STRINGS = new String[]{"faux", "non", "n", "0", "f"};

    @Override
    protected String getLocalizedTrue() {
        return VRAI;
    }

    @Override
    protected String getLocalizedFalse() {
        return FAUX;
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

