/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaIdentityConstraint;

public class XmlSchemaKeyref
extends XmlSchemaIdentityConstraint {
    QName refer;

    public QName getRefer() {
        return this.refer;
    }

    public void setRefer(QName refer) {
        this.refer = refer;
    }
}

