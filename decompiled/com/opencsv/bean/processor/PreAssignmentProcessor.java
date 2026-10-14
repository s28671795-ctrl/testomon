/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.processor;

import com.opencsv.bean.processor.StringProcessor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface PreAssignmentProcessor {
    public Class<? extends StringProcessor> processor();

    public String paramString() default "";
}

