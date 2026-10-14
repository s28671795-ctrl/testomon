/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jvnet.hk2.annotations.InhabitantAnnotation;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Documented
@InhabitantAnnotation(value="default")
public @interface Service {
    public String name() default "";

    public String metadata() default "";

    public String analyzer() default "default";
}

