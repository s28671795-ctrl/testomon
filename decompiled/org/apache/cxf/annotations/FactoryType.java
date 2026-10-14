/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.Factory;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface FactoryType {
    public Type value() default Type.Singleton;

    public String[] args() default {};

    public Class<? extends Factory> factoryClass() default DEFAULT.class;

    public static final class DEFAULT
    implements Factory {
        @Override
        public Object create(Exchange e) throws Throwable {
            return null;
        }

        @Override
        public void release(Exchange e, Object o) {
        }
    }

    public static enum Type {
        Singleton,
        Session,
        Pooled,
        PerRequest;

    }
}

