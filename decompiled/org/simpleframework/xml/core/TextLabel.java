/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Introspector;
import org.simpleframework.xml.core.Primitive;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.core.TextException;
import org.simpleframework.xml.stream.Format;

class TextLabel
extends TemplateLabel {
    private Introspector detail;
    private Expression path;
    private Contact contact;
    private Text label;
    private Class type;
    private String empty;
    private boolean required;
    private boolean data;

    public TextLabel(Contact contact, Text label, Format format) {
        this.detail = new Introspector(contact, this, format);
        this.required = label.required();
        this.type = contact.getType();
        this.empty = label.empty();
        this.data = label.data();
        this.contact = contact;
        this.label = label;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return null;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        String ignore = this.getEmpty(context);
        Contact type = this.getContact();
        if (!context.isPrimitive(type)) {
            throw new TextException("Cannot use %s to represent %s", type, this.label);
        }
        return new Primitive(context, type, ignore);
    }

    @Override
    public String getEmpty(Context context) {
        if (this.detail.isEmpty(this.empty)) {
            return null;
        }
        return this.empty;
    }

    @Override
    public String getPath() throws Exception {
        return this.getExpression().getPath();
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
    public Contact getContact() {
        return this.contact;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getOverride() {
        return this.contact.toString();
    }

    @Override
    public Class getType() {
        return this.type;
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
    public boolean isText() {
        return true;
    }

    @Override
    public boolean isInline() {
        return true;
    }

    @Override
    public String toString() {
        return this.detail.toString();
    }
}

