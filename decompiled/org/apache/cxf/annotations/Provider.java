/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Provider {
    public Type value();

    public Scope scope() default Scope.All;

    public static enum Scope {
        Server,
        Client,
        All;

    }

    public static enum Type {
        InInterceptor,
        OutInterceptor,
        InFaultInterceptor,
        OutFaultInterceptor,
        Feature;

    }
}

