/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotation;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.w3c.dom.Attr;

public abstract class XmlSchemaAnnotated
extends XmlSchemaObject {
    private XmlSchemaAnnotation annotation;
    private String id;
    private Attr[] unhandledAttributes;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public XmlSchemaAnnotation getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(XmlSchemaAnnotation annotation) {
        this.annotation = annotation;
    }

    public Attr[] getUnhandledAttributes() {
        return this.unhandledAttributes;
    }

    public void setUnhandledAttributes(Attr[] unhandledAttributes) {
        this.unhandledAttributes = unhandledAttributes;
    }

    public String toString() {
        if (this.id == null) {
            return super.toString();
        }
        return super.toString() + " [id:" + this.id + "]";
    }
}

