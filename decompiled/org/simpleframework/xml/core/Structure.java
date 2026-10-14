/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.Model;
import org.simpleframework.xml.core.ModelSection;
import org.simpleframework.xml.core.Section;

class Structure {
    private final Instantiator factory;
    private final Label version;
    private final Label text;
    private final Model model;
    private final boolean primitive;

    public Structure(Instantiator factory2, Model model, Label version, Label text, boolean primitive) {
        this.primitive = primitive;
        this.factory = factory2;
        this.version = version;
        this.model = model;
        this.text = text;
    }

    public Instantiator getInstantiator() {
        return this.factory;
    }

    public Section getSection() {
        return new ModelSection(this.model);
    }

    public boolean isPrimitive() {
        return this.primitive;
    }

    public Version getRevision() {
        if (this.version != null) {
            Contact contact = this.version.getContact();
            return contact.getAnnotation(Version.class);
        }
        return null;
    }

    public Label getVersion() {
        return this.version;
    }

    public Label getText() {
        return this.text;
    }
}

