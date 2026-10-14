/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import java.lang.annotation.Annotation;

interface Scanner {
    public <T extends Annotation> T scan(Class<T> var1);
}

