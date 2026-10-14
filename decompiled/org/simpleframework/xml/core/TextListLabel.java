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
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.core.TextException;
import org.simpleframework.xml.core.TextList;
import org.simpleframework.xml.strategy.Type;

class TextListLabel
extends TemplateLabel {
    private final String empty;
    private final Label label;
    private final Text text;

    public TextListLabel(Label label, Text text) {
        this.empty = text.empty();
        this.label = label;
        this.text = text;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return null;
    }

    @Override
    public String[] getNames() throws Exception {
        return this.label.getNames();
    }

    @Override
    public String[] getPaths() throws Exception {
        return this.label.getPaths();
    }

    @Override
    public String getEmpty(Context context) throws Exception {
        return this.empty;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        Contact type = this.getContact();
        if (!this.label.isCollection()) {
            throw new TextException("Cannot use %s to represent %s", type, this.label);
        }
        return new TextList(context, type, this.label);
    }

    @Override
    public String getName() throws Exception {
        return this.label.getName();
    }

    @Override
    public String getPath() throws Exception {
        return this.label.getPath();
    }

    @Override
    public Expression getExpression() throws Exception {
        return this.label.getExpression();
    }

    @Override
    public Type getDependent() throws Exception {
        return this.label.getDependent();
    }

    @Override
    public String getEntry() throws Exception {
        return this.label.getEntry();
    }

    @Override
    public Object getKey() throws Exception {
        return this.label.getKey();
    }

    @Override
    public Annotation getAnnotation() {
        return this.label.getAnnotation();
    }

    @Override
    public Contact getContact() {
        return this.label.getContact();
    }

    @Override
    public Class getType() {
        return this.label.getType();
    }

    @Override
    public String getOverride() {
        return this.label.getOverride();
    }

    @Override
    public boolean isData() {
        return this.label.isData();
    }

    @Override
    public boolean isRequired() {
        return this.label.isRequired();
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public boolean isInline() {
        return this.label.isInline();
    }

    @Override
    public boolean isTextList() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.text, this.label);
    }
}

