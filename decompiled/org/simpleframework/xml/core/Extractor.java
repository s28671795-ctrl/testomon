/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.core.Label;

interface Extractor<T extends Annotation> {
    public T[] getAnnotations() throws Exception;

    public Class getType(T var1) throws Exception;

    public Label getLabel(T var1) throws Exception;
}

