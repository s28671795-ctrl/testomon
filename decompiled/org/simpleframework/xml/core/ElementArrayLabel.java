/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.core.ArrayFactory;
import org.simpleframework.xml.core.ClassType;
import org.simpleframework.xml.core.CompositeArray;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Factory;
import org.simpleframework.xml.core.InstantiationException;
import org.simpleframework.xml.core.Introspector;
import org.simpleframework.xml.core.PrimitiveArray;
import org.simpleframework.xml.core.Qualifier;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;

class ElementArrayLabel
extends TemplateLabel {
    private Decorator decorator;
    private ElementArray label;
    private Introspector detail;
    private Expression path;
    private Format format;
    private Class type;
    private String entry;
    private String name;
    private boolean required;
    private boolean data;

    public ElementArrayLabel(Contact contact, ElementArray label, Format format) {
        this.detail = new Introspector(contact, this, format);
        this.decorator = new Qualifier(contact);
        this.required = label.required();
        this.type = contact.getType();
        this.entry = label.entry();
        this.data = label.data();
        this.name = label.name();
        this.format = format;
        this.label = label;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return this.decorator;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        Contact contact = this.getContact();
        String entry = this.getEntry();
        if (!this.type.isArray()) {
            throw new InstantiationException("Type is not an array %s for %s", this.type, contact);
        }
        return this.getConverter(context, entry);
    }

    private Converter getConverter(Context context, String name) throws Exception {
        Type entry = this.getDependent();
        Contact type = this.getContact();
        if (!context.isPrimitive(entry)) {
            return new CompositeArray(context, type, entry, name);
        }
        return new PrimitiveArray(context, type, entry, name);
    }

    @Override
    public Object getEmpty(Context context) throws Exception {
        ClassType array = new ClassType(this.type);
        ArrayFactory factory2 = new ArrayFactory(context, array);
        if (!this.label.empty()) {
            return ((Factory)factory2).getInstance();
        }
        return null;
    }

    @Override
    public String getEntry() throws Exception {
        Style style = this.format.getStyle();
        if (this.detail.isEmpty(this.entry)) {
            this.entry = this.detail.getEntry();
        }
        return style.getElement(this.entry);
    }

    @Override
    public String getName() throws Exception {
        Style style = this.format.getStyle();
        String name = this.detail.getName();
        return style.getElement(name);
    }

    @Override
    public String getPath() throws Exception {
        Expression path = this.getExpression();
        String name = this.getName();
        return path.getElement(name);
    }

    @Override
    public Expression getExpression() throws Exception {
        if (this.path == null) {
            this.path = this.detail.getExpression();
        }
        return this.path;
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
    }

    @Override
    public Type getDependent() {
        Class<?> entry = this.type.getComponentType();
        if (entry == null) {
            return new ClassType(this.type);
        }
        return new ClassType(entry);
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public Contact getContact() {
        return this.detail.getContact();
    }

    @Override
    public String getOverride() {
        return this.name;
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

