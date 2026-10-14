/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeContent;
import org.apache.ws.commons.schema.XmlSchemaType;

public class XmlSchemaSimpleType
extends XmlSchemaType {
    XmlSchemaSimpleTypeContent content;

    public XmlSchemaSimpleType(XmlSchema schema, boolean topLevel) {
        super(schema, topLevel);
    }

    public XmlSchemaSimpleTypeContent getContent() {
        return this.content;
    }

    public void setContent(XmlSchemaSimpleTypeContent content) {
        this.content = content;
    }
}

