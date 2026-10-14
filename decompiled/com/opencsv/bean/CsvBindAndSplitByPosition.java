/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.CsvBindAndSplitByPositions;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Repeatable(value=CsvBindAndSplitByPositions.class)
public @interface CsvBindAndSplitByPosition {
    public boolean required() default false;

    public int position();

    public String locale() default "";

    public boolean writeLocaleEqualsReadLocale() default true;

    public String writeLocale() default "";

    public String splitOn() default "\\s+";

    public String writeDelimiter() default " ";

    public Class<? extends Collection> collectionType() default Collection.class;

    public Class<?> elementType();

    public Class<? extends AbstractCsvConverter> converter() default AbstractCsvConverter.class;

    public String capture() default "";

    public String format() default "";

    public String[] profiles() default {""};
}

