/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.CsvNumbers;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Repeatable(value=CsvNumbers.class)
public @interface CsvNumber {
    public String value();

    public boolean writeFormatEqualsReadFormat() default true;

    public String writeFormat() default "";

    public String[] profiles() default {""};

    public RoundingMode roundingMode() default RoundingMode.HALF_EVEN;
}

