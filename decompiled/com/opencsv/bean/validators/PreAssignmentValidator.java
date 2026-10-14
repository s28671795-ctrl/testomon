/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.validators;

import com.opencsv.bean.validators.StringValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface PreAssignmentValidator {
    public Class<? extends StringValidator> validator();

    public String paramString() default "";
}

