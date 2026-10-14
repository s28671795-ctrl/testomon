/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;

public abstract class XmlSchemaRefBase {
    protected XmlSchema parent;
    protected QName targetQName;
    private XmlSchemaNamed namedTwin;

    protected abstract void forgetTargetObject();

    public void setNamedObject(XmlSchemaNamed named) {
        this.namedTwin = named;
    }

    public QName getTargetQName() {
        return this.targetQName;
    }

    public void setTargetQName(QName targetQName) {
        if (targetQName != null && this.namedTwin != null && !this.namedTwin.isAnonymous()) {
            throw new XmlSchemaException("It is invalid to set the ref= name for an item that has a name.");
        }
        this.targetQName = targetQName;
        this.forgetTargetObject();
    }
}

