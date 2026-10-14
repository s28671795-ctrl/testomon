/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.core.ClassType;
import org.simpleframework.xml.core.CompositeInlineMap;
import org.simpleframework.xml.core.CompositeMap;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.ElementException;
import org.simpleframework.xml.core.Entry;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Factory;
import org.simpleframework.xml.core.Introspector;
import org.simpleframework.xml.core.MapFactory;
import org.simpleframework.xml.core.Qualifier;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;

class ElementMapLabel
extends TemplateLabel {
    private Decorator decorator;
    private Introspector detail;
    private ElementMap label;
    private Expression cache;
    private Format format;
    private Entry entry;
    private String override;
    private String parent;
    private String path;
    private String name;
    private Class[] items;
    private Class type;
    private boolean required;
    private boolean inline;
    private boolean data;

    public ElementMapLabel(Contact contact, ElementMap label, Format format) {
        this.detail = new Introspector(contact, this, format);
        this.decorator = new Qualifier(contact);
        this.entry = new Entry(contact, label);
        this.required = label.required();
        this.type = contact.getType();
        this.inline = label.inline();
        this.override = label.name();
        this.data = label.data();
        this.format = format;
        this.label = label;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return this.decorator;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        Type type = this.getMap();
        if (!this.label.inline()) {
            return new CompositeMap(context, this.entry, type);
        }
        return new CompositeInlineMap(context, this.entry, type);
    }

    @Override
    public Object getEmpty(Context context) throws Exception {
        ClassType map = new ClassType(this.type);
        MapFactory factory2 = new MapFactory(context, map);
        if (!this.label.empty()) {
            return ((Factory)factory2).getInstance();
        }
        return null;
    }

    @Override
    public Type getDependent() throws Exception {
        Contact contact = this.getContact();
        if (this.items == null) {
            this.items = contact.getDependents();
        }
        if (this.items == null) {
            throw new ElementException("Unable to determine type for %s", contact);
        }
        if (this.items.length == 0) {
            return new ClassType(Object.class);
        }
        return new ClassType(this.items[0]);
    }

    @Override
    public String getEntry() throws Exception {
        Style style = this.format.getStyle();
        if (this.detail.isEmpty(this.parent)) {
            this.parent = this.detail.getEntry();
        }
        return style.getElement(this.parent);
    }

    @Override
    public String getName() throws Exception {
        if (this.name == null) {
            Style style = this.format.getStyle();
            String value = this.entry.getEntry();
            if (!this.label.inline()) {
                value = this.detail.getName();
            }
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

    private Type getMap() {
        return new ClassType(this.type);
    }

    @Override
    public Annotation getAnnotation() {
        return this.label;
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
        return this.override;
    }

    @Override
    public boolean isData() {
        return this.data;
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isInline() {
        return this.inline;
    }

    @Override
    public String toString() {
        return this.detail.toString();
    }
}

