/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ElementList {
    public String name() default "";

    public String entry() default "";

    public Class type() default void.class;

    public boolean data() default false;

    public boolean required() default true;

    public boolean inline() default false;

    public boolean empty() default true;
}

