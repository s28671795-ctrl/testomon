/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.core.Expression;

interface Parameter {
    public Object getKey();

    public Class getType();

    public int getIndex();

    public Annotation getAnnotation();

    public Expression getExpression();

    public String getName();

    public String getPath();

    public boolean isRequired();

    public boolean isPrimitive();

    public boolean isAttribute();

    public boolean isText();

    public String toString();
}

