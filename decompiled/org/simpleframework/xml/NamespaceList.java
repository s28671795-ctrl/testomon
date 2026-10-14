/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.simpleframework.xml.Namespace;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface NamespaceList {
    public Namespace[] value() default {};
}

