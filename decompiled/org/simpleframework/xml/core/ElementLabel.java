/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.core.Composite;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Introspector;
import org.simpleframework.xml.core.OverrideType;
import org.simpleframework.xml.core.Primitive;
import org.simpleframework.xml.core.Qualifier;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;

class ElementLabel
extends TemplateLabel {
    private Decorator decorator;
    private Introspector detail;
    private Expression cache;
    private Element label;
    private Format format;
    private String override;
    private String path;
    private String name;
    private Class expect;
    private Class type;
    private boolean required;
    private boolean data;

    public ElementLabel(Contact contact, Element label, Format format) {
        this.detail = new Introspector(contact, this, format);
        this.decorator = new Qualifier(contact);
        this.required = label.required();
        this.type = contact.getType();
        this.override = label.name();
        this.expect = label.type();
        this.data = label.data();
        this.format = format;
        this.label = label;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return this.decorator;
    }

    @Override
    public Type getType(Class type) {
        Contact contact = this.getContact();
        if (this.expect == Void.TYPE) {
            return contact;
        }
        return new OverrideType(contact, this.expect);
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        Contact type = this.getContact();
        if (context.isPrimitive(type)) {
            return new Primitive(context, type);
        }
        if (this.expect == Void.TYPE) {
            return new Composite(context, type);
        }
        return new Composite(context, type, this.expect);
    }

    @Override
    public Object getEmpty(Context context) {
        return null;
    }

    @Override
    public String getName() throws Exception {
        if (this.name == null) {
            Style style = this.format.getStyle();
            String value = this.detail.getName();
            this.name = style.getElement(value);
        }
        return this.name;
    }

    @Override
    public String getPath() throws Exception {
        if (this.path == null) {
            Expression expression = this.getExpression();
            String name = this.getName();
            this.path = expression.getElement(name);
        }
        return this.path;
    }

    @Override
    public Expression getExpression() throws Exception {
        if (this.cache == null) {
            this.cache = this.detail.getExpression();
        }
        return this.cache;
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
    }

    @Override
    public Contact getContact() {
        return this.detail.getContact();
    }

    @Override
    public String getOverride() {
        return this.override;
    }

    @Override
    public Class getType() {
        if (this.expect == Void.TYPE) {
            return this.type;
        }
        return this.expect;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isData() {
        return this.data;
    }

    @Override
    public String toString() {
        return this.detail.toString();
    }
}

