/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface InFaultInterceptors {
    public String[] interceptors() default {};

    public Class<? extends Interceptor<? extends Message>>[] classes() default {};
}

