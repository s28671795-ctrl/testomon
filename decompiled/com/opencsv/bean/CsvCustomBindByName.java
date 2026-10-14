/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvCustomBindByNames;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Repeatable(value=CsvCustomBindByNames.class)
public @interface CsvCustomBindByName {
    public Class<? extends AbstractBeanField> converter();

    public String column() default "";

    public boolean required() default false;

    public String[] profiles() default {""};
}

