/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ElementMap {
    public String name() default "";

    public String entry() default "";

    public String value() default "";

    public String key() default "";

    public Class keyType() default void.class;

    public Class valueType() default void.class;

    public boolean attribute() default false;

    public boolean required() default true;

    public boolean data() default false;

    public boolean inline() default false;

    public boolean empty() default true;
}

