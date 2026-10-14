/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.KeyBuilder;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.strategy.Type;

abstract class TemplateLabel
implements Label {
    private final KeyBuilder builder = new KeyBuilder(this);

    protected TemplateLabel() {
    }

    @Override
    public Type getType(Class type) throws Exception {
        return this.getContact();
    }

    @Override
    public Label getLabel(Class type) throws Exception {
        return this;
    }

    @Override
    public String[] getNames() throws Exception {
        String path = this.getPath();
        String name = this.getName();
        return new String[]{path, name};
    }

    @Override
    public String[] getPaths() throws Exception {
        String path = this.getPath();
        return new String[]{path};
    }

    @Override
    public Object getKey() throws Exception {
        return this.builder.getKey();
    }

    @Override
    public String getEntry() throws Exception {
        return null;
    }

    @Override
    public Type getDependent() throws Exception {
        return null;
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public boolean isInline() {
        return false;
    }

    @Override
    public boolean isText() {
        return false;
    }

    @Override
    public boolean isTextList() {
        return false;
    }

    @Override
    public boolean isUnion() {
        return false;
    }
}

