/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupMember;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaItemWithRef;
import org.apache.ws.commons.schema.utils.XmlSchemaRef;
import org.apache.ws.commons.schema.utils.XmlSchemaRefBase;

public class XmlSchemaAttributeGroupRef
extends XmlSchemaAttributeOrGroupRef
implements XmlSchemaAttributeGroupMember,
XmlSchemaItemWithRef<XmlSchemaAttributeGroup> {
    private XmlSchemaRef<XmlSchemaAttributeGroup> ref;

    public XmlSchemaAttributeGroupRef(XmlSchema parent) {
        this.ref = new XmlSchemaRef<XmlSchemaAttributeGroup>(parent, XmlSchemaAttributeGroup.class);
    }

    @Override
    public XmlSchemaRef<XmlSchemaAttributeGroup> getRef() {
        return this.ref;
    }

    @Override
    public boolean isRef() {
        return this.ref.getTargetQName() != null;
    }

    @Override
    public QName getTargetQName() {
        return this.ref.getTargetQName();
    }

    @Override
    public XmlSchemaRefBase getRefBase() {
        return this.ref;
    }
}

