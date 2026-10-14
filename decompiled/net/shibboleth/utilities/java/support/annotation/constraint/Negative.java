/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.annotation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target(value={ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
public @interface Negative {
}

