/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.List;
import org.apache.ws.commons.schema.XmlSchemaAnnotationItem;
import org.apache.ws.commons.schema.XmlSchemaObject;

public class XmlSchemaAnnotation
extends XmlSchemaObject {
    private List<XmlSchemaAnnotationItem> items = new ArrayList<XmlSchemaAnnotationItem>();

    public List<XmlSchemaAnnotationItem> getItems() {
        return this.items;
    }
}

