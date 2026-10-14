/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotationItem;
import org.w3c.dom.NodeList;

public class XmlSchemaAppInfo
extends XmlSchemaAnnotationItem {
    String source;
    NodeList markup;

    public String getSource() {
        return this.source;
    }

    public void setSource(String source2) {
        this.source = source2;
    }

    public NodeList getMarkup() {
        return this.markup;
    }

    public void setMarkup(NodeList markup) {
        this.markup = markup;
    }
}

