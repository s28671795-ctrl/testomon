/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.pf4j.ExtensionPoint;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
@Documented
public @interface Extension {
    public int ordinal() default 0;

    public Class<? extends ExtensionPoint>[] points() default {};

    public String[] plugins() default {};
}

