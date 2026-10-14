/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.CsvBindAndJoinByPositions;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.commons.collections4.MultiValuedMap;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Repeatable(value=CsvBindAndJoinByPositions.class)
public @interface CsvBindAndJoinByPosition {
    public boolean required() default false;

    public String position();

    public String locale() default "";

    public boolean writeLocaleEqualsReadLocale() default true;

    public String writeLocale() default "";

    public Class<? extends MultiValuedMap> mapType() default MultiValuedMap.class;

    public Class<?> elementType();

    public Class<? extends AbstractCsvConverter> converter() default AbstractCsvConverter.class;

    public String capture() default "";

    public String format() default "";

    public String[] profiles() default {""};
}

