/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.core.Contact;

class LabelKey {
    private final Class label;
    private final Class owner;
    private final Class type;
    private final String name;

    public LabelKey(Contact contact, Annotation label) {
        this.owner = contact.getDeclaringClass();
        this.label = label.annotationType();
        this.name = contact.getName();
        this.type = contact.getType();
    }

    public int hashCode() {
        return this.name.hashCode() ^ this.owner.hashCode();
    }

    public boolean equals(Object value) {
        if (value instanceof LabelKey) {
            return this.equals((LabelKey)value);
        }
        return false;
    }

    private boolean equals(LabelKey key) {
        if (key == this) {
            return true;
        }
        if (key.label != this.label) {
            return false;
        }
        if (key.owner != this.owner) {
            return false;
        }
        if (key.type != this.type) {
            return false;
        }
        return key.name.equals(this.name);
    }

    public String toString() {
        return String.format("key '%s' for %s", this.name, this.owner);
    }
}

