/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.simpleframework.xml.DefaultType;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Default {
    public DefaultType value() default DefaultType.FIELD;

    public boolean required() default true;
}

