/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ElementArray {
    public String name() default "";

    public String entry() default "";

    public boolean data() default false;

    public boolean required() default true;

    public boolean empty() default true;
}

