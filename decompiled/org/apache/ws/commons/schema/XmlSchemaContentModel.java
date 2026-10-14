/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaContent;

public abstract class XmlSchemaContentModel
extends XmlSchemaAnnotated {
    protected XmlSchemaContentModel() {
    }

    public abstract void setContent(XmlSchemaContent var1);

    public abstract XmlSchemaContent getContent();
}

