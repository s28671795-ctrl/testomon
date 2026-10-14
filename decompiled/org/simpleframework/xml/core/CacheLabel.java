/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.strategy.Type;

class CacheLabel
implements Label {
    private final Annotation annotation;
    private final Expression expression;
    private final Decorator decorator;
    private final Contact contact;
    private final String[] names;
    private final String[] paths;
    private final Class type;
    private final String entry;
    private final String path;
    private final String override;
    private final String name;
    private final Label label;
    private final Object key;
    private final Type depend;
    private final boolean data;
    private final boolean text;
    private final boolean union;
    private final boolean attribute;
    private final boolean required;
    private final boolean collection;
    private final boolean inline;
    private final boolean list;

    public CacheLabel(Label label) throws Exception {
        this.annotation = label.getAnnotation();
        this.expression = label.getExpression();
        this.decorator = label.getDecorator();
        this.attribute = label.isAttribute();
        this.collection = label.isCollection();
        this.contact = label.getContact();
        this.depend = label.getDependent();
        this.required = label.isRequired();
        this.override = label.getOverride();
        this.list = label.isTextList();
        this.inline = label.isInline();
        this.union = label.isUnion();
        this.names = label.getNames();
        this.paths = label.getPaths();
        this.path = label.getPath();
        this.type = label.getType();
        this.name = label.getName();
        this.entry = label.getEntry();
        this.data = label.isData();
        this.text = label.isText();
        this.key = label.getKey();
        this.label = label;
    }

    @Override
    public Type getType(Class type) throws Exception {
        return this.label.getType(type);
    }

    @Override
    public Label getLabel(Class type) throws Exception {
        return this.label.getLabel(type);
    }

    @Override
    public String[] getNames() throws Exception {
        return this.names;
    }

    @Override
    public String[] getPaths() throws Exception {
        return this.paths;
    }

    @Override
    public Annotation getAnnotation() {
        return this.annotation;
    }

    @Override
    public Contact getContact() {
        return this.contact;
    }

    @Override
    public Decorator getDecorator() throws Exception {
        return this.decorator;
    }

    @Override
    public Converter getConverter(Context context) throws Exception {
        return this.label.getConverter(context);
    }

    @Override
    public Object getEmpty(Context context) throws Exception {
        return this.label.getEmpty(context);
    }

    @Override
    public Type getDependent() throws Exception {
        return this.depend;
    }

    @Override
    public Object getKey() throws Exception {
        return this.key;
    }

    @Override
    public String getEntry() throws Exception {
        return this.entry;
    }

    @Override
    public String getName() throws Exception {
        return this.name;
    }

    @Override
    public String getPath() throws Exception {
        return this.path;
    }

    @Override
    public Expression getExpression() throws Exception {
        return this.expression;
    }

    @Override
    public String getOverride() {
        return this.override;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public boolean isData() {
        return this.data;
    }

    @Override
    public boolean isText() {
        return this.text;
    }

    @Override
    public boolean isTextList() {
        return this.list;
    }

    @Override
    public boolean isInline() {
        return this.inline;
    }

    @Override
    public boolean isAttribute() {
        return this.attribute;
    }

    @Override
    public boolean isCollection() {
        return this.collection;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isUnion() {
        return this.union;
    }

    @Override
    public String toString() {
        return this.label.toString();
    }
}

