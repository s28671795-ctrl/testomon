/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jooq.SQLDialect;

@Target(value={ElementType.METHOD, ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Support {
    public SQLDialect[] value() default {};
}

