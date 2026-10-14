/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotated;

public class XmlSchemaXPath
extends XmlSchemaAnnotated {
    String xpath;

    public String getXPath() {
        return this.xpath;
    }

    public void setXPath(String xpathString) {
        this.xpath = xpathString;
    }
}

