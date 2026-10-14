/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractFieldMapEntry;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.BeanFieldJoin;
import com.opencsv.bean.util.OpencsvUtils;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexToBeanField<T>
extends AbstractFieldMapEntry<String, String, T> {
    private final Pattern regex;

    public RegexToBeanField(String pattern, BeanField<T, String> field2, Locale errorLocale) {
        super(field2, errorLocale);
        this.regex = OpencsvUtils.compilePattern(pattern, 2, BeanFieldJoin.class, this.errorLocale);
    }

    @Override
    public boolean contains(String key) {
        Matcher m4 = this.regex.matcher(key);
        return m4.matches();
    }

    @Override
    public String getInitializer() {
        return this.regex.pattern();
    }
}

