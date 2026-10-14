/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaAnyAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaContent;

public class XmlSchemaSimpleContentExtension
extends XmlSchemaContent {
    private XmlSchemaAnyAttribute anyAttribute;
    private List<XmlSchemaAttributeOrGroupRef> attributes = Collections.synchronizedList(new ArrayList());
    private QName baseTypeName;

    public XmlSchemaAnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public List<XmlSchemaAttributeOrGroupRef> getAttributes() {
        return this.attributes;
    }

    public QName getBaseTypeName() {
        return this.baseTypeName;
    }

    public void setAnyAttribute(XmlSchemaAnyAttribute anyAttribute) {
        this.anyAttribute = anyAttribute;
    }

    public void setBaseTypeName(QName baseTypeName) {
        this.baseTypeName = baseTypeName;
    }

    public void setAttributes(List<XmlSchemaAttributeOrGroupRef> attributes) {
        this.attributes = attributes;
    }
}

