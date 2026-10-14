/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaContentProcessing;

public class XmlSchemaAnyAttribute
extends XmlSchemaAnnotated {
    String namespace;
    XmlSchemaContentProcessing processContent = XmlSchemaContentProcessing.NONE;

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public XmlSchemaContentProcessing getProcessContent() {
        return this.processContent;
    }

    public void setProcessContent(XmlSchemaContentProcessing processContent) {
        this.processContent = processContent;
    }
}

