/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Schema;
import org.simpleframework.xml.core.Section;

class ClassSchema
implements Schema {
    private final Instantiator factory;
    private final Decorator decorator;
    private final Section section;
    private final Version revision;
    private final Caller caller;
    private final Label version;
    private final Label text;
    private final Class type;
    private final boolean primitive;

    public ClassSchema(Scanner schema, Context context) throws Exception {
        this.caller = schema.getCaller(context);
        this.factory = schema.getInstantiator();
        this.revision = schema.getRevision();
        this.decorator = schema.getDecorator();
        this.primitive = schema.isPrimitive();
        this.version = schema.getVersion();
        this.section = schema.getSection();
        this.text = schema.getText();
        this.type = schema.getType();
    }

    @Override
    public boolean isPrimitive() {
        return this.primitive;
    }

    @Override
    public Instantiator getInstantiator() {
        return this.factory;
    }

    @Override
    public Label getVersion() {
        return this.version;
    }

    @Override
    public Version getRevision() {
        return this.revision;
    }

    @Override
    public Decorator getDecorator() {
        return this.decorator;
    }

    @Override
    public Caller getCaller() {
        return this.caller;
    }

    @Override
    public Section getSection() {
        return this.section;
    }

    @Override
    public Label getText() {
        return this.text;
    }

    public String toString() {
        return String.format("schema for %s", this.type);
    }
}

