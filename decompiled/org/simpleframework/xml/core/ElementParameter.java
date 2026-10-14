/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.core.ElementLabel;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.ParameterContact;
import org.simpleframework.xml.core.TemplateParameter;
import org.simpleframework.xml.stream.Format;

class ElementParameter
extends TemplateParameter {
    private final Expression expression;
    private final Contact contact;
    private final Label label;
    private final String path;
    private final String name;
    private final Class type;
    private final Object key;
    private final int index;

    public ElementParameter(Constructor factory2, Element value, Format format, int index) throws Exception {
        this.contact = new Contact(value, factory2, index);
        this.label = new ElementLabel(this.contact, value, format);
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
    extends ParameterContact<Element> {
        public Contact(Element label, Constructor factory2, int index) {
            super(label, factory2, index);
        }

        @Override
        public String getName() {
            return ((Element)this.label).name();
        }
    }
}

