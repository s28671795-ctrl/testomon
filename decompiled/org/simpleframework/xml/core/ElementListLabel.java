/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.core.ClassType;
import org.simpleframework.xml.core.CollectionFactory;
import org.simpleframework.xml.core.CompositeInlineList;
import org.simpleframework.xml.core.CompositeList;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.ElementException;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Factory;
import org.simpleframework.xml.core.Introspector;
import org.simpleframework.xml.core.PrimitiveInlineList;
import org.simpleframework.xml.core.PrimitiveList;
import org.simpleframework.xml.core.Qualifier;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;

class ElementListLabel
extends TemplateLabel {
    private Decorator decorator;
    private Introspector detail;
    private ElementList label;
    private Expression cache;
    private Format format;
    private String override;
    private String entry;
    private String name;
    private String path;
    private Class type;
    private Class item;
    private boolean required;
    private boolean inline;
    private boolean data;

    public ElementListLabel(Contact contact, ElementList label, Format format) {
        this.detail = new Introspector(contact, this, format);
        this.decorator = new Qualifier(contact);
        this.required = label.required();
        this.type = contact.getType();
        this.override = label.name();
        this.inline = label.inline();
        this.entry = label.entry();
        this.data = label.data();
        this.item = label.type();
        this.format = format;
        this.label = label;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return this.decorator;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        String entry = this.getEntry();
        if (!this.label.inline()) {
            return this.getConverter(context, entry);
        }
        return this.getInlineConverter(context, entry);
    }

    private Converter getConverter(Context context, String name) throws Exception {
        Type item = this.getDependent();
        Contact type = this.getContact();
        if (!context.isPrimitive(item)) {
            return new CompositeList(context, type, item, name);
        }
        return new PrimitiveList(context, type, item, name);
    }

    private Converter getInlineConverter(Context context, String name) throws Exception {
        Type item = this.getDependent();
        Contact type = this.getContact();
        if (!context.isPrimitive(item)) {
            return new CompositeInlineList(context, type, item, name);
        }
        return new PrimitiveInlineList(context, type, item, name);
    }

    @Override
    public Object getEmpty(Context context) throws Exception {
        ClassType list = new ClassType(this.type);
        CollectionFactory factory2 = new CollectionFactory(context, list);
        if (!this.label.empty()) {
            return ((Factory)factory2).getInstance();
        }
        return null;
    }

    @Override
    public Type getDependent() throws Exception {
        Contact contact = this.getContact();
        if (this.item == Void.TYPE) {
            this.item = contact.getDependent();
        }
        if (this.item == null) {
            throw new ElementException("Unable to determine generic type for %s", contact);
        }
        return new ClassType(this.item);
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

