/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.ElementMapUnion;
import org.simpleframework.xml.core.ElementMapUnionLabel;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.ParameterContact;
import org.simpleframework.xml.core.TemplateParameter;
import org.simpleframework.xml.stream.Format;

class ElementMapUnionParameter
extends TemplateParameter {
    private final Expression expression;
    private final Contact contact;
    private final Label label;
    private final String path;
    private final String name;
    private final Class type;
    private final Object key;
    private final int index;

    public ElementMapUnionParameter(Constructor factory2, ElementMapUnion union, ElementMap element, Format format, int index) throws Exception {
        this.contact = new Contact(element, factory2, index);
        this.label = new ElementMapUnionLabel(this.contact, union, element, format);
        this.expression = this.label.getExpression();
        this.path = this.label.getPath();
        this.type = this.label.getType();
        this.name = this.label.getName();
        this.key = this.label.getKey();
        this.index = index;
    }

    @Override
    public Object getKey() {
        return this.key;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public Annotation getAnnotation() {
        return this.contact.getAnnotation();
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public boolean isRequired() {
        return this.label.isRequired();
    }

    @Override
    public boolean isPrimitive() {
        return this.type.isPrimitive();
    }

    @Override
    public String toString() {
        return this.contact.toString();
    }

    private static class Contact
    extends ParameterContact<ElementMap> {
        public Contact(ElementMap element, Constructor factory2, int index) {
            super(element, factory2, index);
        }

        @Override
        public String getName() {
            return ((ElementMap)this.label).name();
        }
    }
}

