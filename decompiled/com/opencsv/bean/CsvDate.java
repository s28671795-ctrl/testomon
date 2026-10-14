/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.CsvDates;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Repeatable(value=CsvDates.class)
public @interface CsvDate {
    public String value() default "yyyyMMdd'T'HHmmss";

    public boolean writeFormatEqualsReadFormat() default true;

    public String writeFormat() default "yyyyMMdd'T'HHmmss";

    public String chronology() default "ISO";

    public boolean writeChronologyEqualsReadChronology() default true;

    public String writeChronology() default "ISO";

    public String[] profiles() default {""};
}

