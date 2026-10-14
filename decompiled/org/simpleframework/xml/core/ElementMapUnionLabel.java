/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.ElementMapUnion;
import org.simpleframework.xml.core.CompositeMapUnion;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.ElementMapLabel;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.GroupExtractor;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.TemplateLabel;
import org.simpleframework.xml.core.UnionException;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;

class ElementMapUnionLabel
extends TemplateLabel {
    private GroupExtractor extractor;
    private Expression path;
    private Contact contact;
    private Label label;

    public ElementMapUnionLabel(Contact contact, ElementMapUnion union, ElementMap element, Format format) throws Exception {
        this.extractor = new GroupExtractor(contact, union, format);
        this.label = new ElementMapLabel(contact, element, format);
        this.contact = contact;
    }

    @Override
    public boolean isUnion() {
        return true;
    }

    @Override
    public Contact getContact() {
        return this.contact;
    }

    @Override
    public Annotation getAnnotation() {
        return this.label.getAnnotation();
    }

    @Override
    public Type getType(Class type) {
        return this.getContact();
    }

    @Override
    public Label getLabel(Class type) {
        return this;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        Expression path = this.getExpression();
        Contact type = this.getContact();
        if (type == null) {
            throw new UnionException("Union %s was not declared on a field or method", this.label);
        }
        return new CompositeMapUnion(context, this.extractor, path, type);
    }

    @Override
    public String[] getNames() throws Exception {
        return this.extractor.getNames();
    }

    @Override
    public String[] getPaths() throws Exception {
        return this.extractor.getPaths();
    }

    @Override
    public Object getEmpty(Context context) throws Exception {
        return this.label.getEmpty(context);
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return this.label.getDecorator();
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
    public String getName() throws Exception {
        return this.label.getName();
    }

    @Override
    public String getPath() throws Exception {
        return this.label.getPath();
    }

    @Override
    public Expression getExpression() throws Exception {
        if (this.path == null) {
            this.path = this.label.getExpression();
        }
        return this.path;
    }

    @Override
    public String getOverride() {
        return this.label.getOverride();
    }

    @Override
    public Class getType() {
        return this.label.getType();
    }

    @Override
    public boolean isCollection() {
        return this.label.isCollection();
    }

    @Override
    public boolean isData() {
        return this.label.isData();
    }

    @Override
    public boolean isInline() {
        return this.label.isInline();
    }

    @Override
    public boolean isRequired() {
        return this.label.isRequired();
    }

    @Override
    public String toString() {
        return this.label.toString();
    }
}

