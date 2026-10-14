/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.AttributeException;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Introspector;
import org.simpleframework.xml.core.Primitive;
import org.simpleframework.xml.core.Qualifier;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;

class VersionLabel
extends TemplateLabel {
    private Decorator decorator;
    private Introspector detail;
    private Expression path;
    private Version label;
    private Format format;
    private Class type;
    private String name;
    private boolean required;

    public VersionLabel(Contact contact, Version label, Format format) {
        this.detail = new Introspector(contact, this, format);
        this.decorator = new Qualifier(contact);
        this.required = label.required();
        this.type = contact.getType();
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
        String ignore = this.getEmpty(context);
        Contact type = this.getContact();
        if (!context.isFloat(type)) {
            throw new AttributeException("Cannot use %s to represent %s", this.label, type);
        }
        return new Primitive(context, type, ignore);
    }

    @Override
    public String getEmpty(Context context) {
        return null;
    }

    @Override
    public String getName() throws Exception {
        Style style = this.format.getStyle();
        String name = this.detail.getName();
        return style.getAttribute(name);
    }

    @Override
    public String getPath() throws Exception {
        Expression path = this.getExpression();
        String name = this.getName();
        return path.getAttribute(name);
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
    public String getOverride() {
        return this.name;
    }

    @Override
    public Contact getContact() {
        return this.detail.getContact();
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public boolean isAttribute() {
        return true;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isData() {
        return false;
    }

    @Override
    public String toString() {
        return this.detail.toString();
    }
}

