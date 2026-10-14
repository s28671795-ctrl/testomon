/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.EmptyExpression;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.PathParser;
import org.simpleframework.xml.core.Reflector;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;

class Introspector {
    private final Annotation marker;
    private final Contact contact;
    private final Format format;
    private final Label label;

    public Introspector(Contact contact, Label label, Format format) {
        this.marker = contact.getAnnotation();
        this.contact = contact;
        this.format = format;
        this.label = label;
    }

    public Contact getContact() {
        return this.contact;
    }

    public Type getDependent() throws Exception {
        return this.label.getDependent();
    }

    public String getEntry() throws Exception {
        Type depend = this.getDependent();
        Class<?> type = depend.getType();
        if (type.isArray()) {
            type = type.getComponentType();
        }
        return this.getName(type);
    }

    private String getName(Class type) throws Exception {
        String name = this.getRoot(type);
        if (name != null) {
            return name;
        }
        name = type.getSimpleName();
        return Reflector.getName(name);
    }

    private String getRoot(Class type) {
        Class real = type;
        while (type != null) {
            String name = this.getRoot(real, type);
            if (name != null) {
                return name;
            }
            type = type.getSuperclass();
        }
        return null;
    }

    private String getRoot(Class<?> real, Class<?> type) {
        String name = type.getSimpleName();
        Root root = type.getAnnotation(Root.class);
        if (root != null) {
            String text = root.name();
            if (!this.isEmpty(text)) {
                return text;
            }
            return Reflector.getName(name);
        }
        return null;
    }

    public String getName() throws Exception {
        String entry = this.label.getEntry();
        if (!this.label.isInline()) {
            entry = this.getDefault();
        }
        return entry;
    }

    private String getDefault() throws Exception {
        String name = this.label.getOverride();
        if (!this.isEmpty(name)) {
            return name;
        }
        return this.contact.getName();
    }

    public Expression getExpression() throws Exception {
        String path = this.getPath();
        if (path != null) {
            return new PathParser(path, this.contact, this.format);
        }
        return new EmptyExpression(this.format);
    }

    public String getPath() throws Exception {
        Path path = this.contact.getAnnotation(Path.class);
        if (path == null) {
            return null;
        }
        return path.value();
    }

    public boolean isEmpty(String value) {
        if (value != null) {
            return value.length() == 0;
        }
        return true;
    }

    public String toString() {
        return String.format("%s on %s", this.marker, this.contact);
    }
}

