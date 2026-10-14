/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Parameter;

class CacheParameter
implements Parameter {
    private final Annotation annotation;
    private final Expression expression;
    private final String name;
    private final String path;
    private final String string;
    private final Class type;
    private final Object key;
    private final int index;
    private final boolean primitive;
    private final boolean required;
    private final boolean attribute;
    private final boolean text;

    public CacheParameter(Parameter value, Label label) throws Exception {
        this.annotation = value.getAnnotation();
        this.expression = value.getExpression();
        this.attribute = value.isAttribute();
        this.primitive = value.isPrimitive();
        this.required = label.isRequired();
        this.string = value.toString();
        this.text = value.isText();
        this.index = value.getIndex();
        this.name = value.getName();
        this.path = value.getPath();
        this.type = value.getType();
        this.key = label.getKey();
    }

    @Override
    public Object getKey() {
        return this.key;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public Annotation getAnnotation() {
        return this.annotation;
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isPrimitive() {
        return this.primitive;
    }

    @Override
    public boolean isAttribute() {
        return this.attribute;
    }

    @Override
    public boolean isText() {
        return this.text;
    }

    @Override
    public String toString() {
        return this.string;
    }
}

