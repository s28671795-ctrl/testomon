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
@Target(value={ElementType.TYPE})
@Inherited
public @interface Component {
    public Class<?> role();

    public String hint() default "";

    public String version() default "";

    public String alias() default "";

    public String description() default "";

    public String lifecycleHandler() default "";

    public String instantiationStrategy() default "";

    public String factory() default "";

    public String type() default "";

    public String profile() default "";

    public String composer() default "";

    public String configurator() default "";

    public boolean isolatedRealm() default false;
}

