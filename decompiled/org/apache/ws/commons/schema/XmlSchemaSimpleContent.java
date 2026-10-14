/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaContent;
import org.apache.ws.commons.schema.XmlSchemaContentModel;

public class XmlSchemaSimpleContent
extends XmlSchemaContentModel {
    XmlSchemaContent content;

    @Override
    public XmlSchemaContent getContent() {
        return this.content;
    }

    @Override
    public void setContent(XmlSchemaContent content) {
        this.content = content;
    }
}

