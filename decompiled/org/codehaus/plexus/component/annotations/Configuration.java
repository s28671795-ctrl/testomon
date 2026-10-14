/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.component.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Inherited
public @interface Configuration {
    public String name() default "";

    public String value();
}

