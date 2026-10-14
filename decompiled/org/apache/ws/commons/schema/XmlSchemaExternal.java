/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.utils.CollectionFactory;

public abstract class XmlSchemaExternal
extends XmlSchemaAnnotated {
    XmlSchema schema;
    String schemaLocation;

    protected XmlSchemaExternal(XmlSchema parent) {
        final XmlSchema fParent = parent;
        CollectionFactory.withSchemaModifiable(new Runnable(){

            @Override
            public void run() {
                fParent.getExternals().add(XmlSchemaExternal.this);
                fParent.getItems().add(XmlSchemaExternal.this);
            }
        });
    }

    public XmlSchema getSchema() {
        return this.schema;
    }

    public void setSchema(XmlSchema sc) {
        this.schema = sc;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
}

