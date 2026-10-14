/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaExternal;

public class XmlSchemaImport
extends XmlSchemaExternal {
    String namespace;

    public XmlSchemaImport(XmlSchema parent) {
        super(parent);
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}

