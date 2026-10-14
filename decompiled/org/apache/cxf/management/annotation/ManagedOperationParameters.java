/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.management.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.cxf.management.annotation.ManagedOperationParameter;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ManagedOperationParameters {
    public ManagedOperationParameter[] value();
}

