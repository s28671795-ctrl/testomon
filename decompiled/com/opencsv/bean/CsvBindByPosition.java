/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.CsvBindByPositions;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Repeatable(value=CsvBindByPositions.class)
public @interface CsvBindByPosition {
    public boolean required() default false;

    public int position();

    public String locale() default "";

    public boolean writeLocaleEqualsReadLocale() default true;

    public String writeLocale() default "";

    public String capture() default "";

    public String format() default "";

    public String[] profiles() default {""};
}

