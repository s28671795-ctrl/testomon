/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaContent;
import org.apache.ws.commons.schema.XmlSchemaContentModel;

public class XmlSchemaComplexContent
extends XmlSchemaContentModel {
    XmlSchemaContent content;
    private boolean mixed;

    @Override
    public XmlSchemaContent getContent() {
        return this.content;
    }

    @Override
    public void setContent(XmlSchemaContent content) {
        this.content = content;
    }

    public boolean isMixed() {
        return this.mixed;
    }

    public void setMixed(boolean mixed) {
        this.mixed = mixed;
    }
}

