/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.simpleframework.xml.ElementMap;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ElementMapUnion {
    public ElementMap[] value();
}

