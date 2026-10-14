/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.nullanalysis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
@Retention(value=RetentionPolicy.CLASS)
public @interface NotNullByDefault {
    public boolean value() default true;
}

