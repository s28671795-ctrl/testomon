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
import org.apache.ws.commons.schema.XmlSchemaParticle;

public class XmlSchemaComplexContentExtension
extends XmlSchemaContent {
    private XmlSchemaAnyAttribute anyAttribute;
    private List<XmlSchemaAttributeOrGroupRef> attributes = Collections.synchronizedList(new ArrayList());
    private QName baseTypeName;
    private XmlSchemaParticle particle;

    public void setAnyAttribute(XmlSchemaAnyAttribute anyAttribute) {
        this.anyAttribute = anyAttribute;
    }

    public XmlSchemaAnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public List<XmlSchemaAttributeOrGroupRef> getAttributes() {
        return this.attributes;
    }

    public void setBaseTypeName(QName baseTypeName) {
        this.baseTypeName = baseTypeName;
    }

    public QName getBaseTypeName() {
        return this.baseTypeName;
    }

    public XmlSchemaParticle getParticle() {
        return this.particle;
    }

    public void setParticle(XmlSchemaParticle particle) {
        this.particle = particle;
    }

    void setAttributes(List<XmlSchemaAttributeOrGroupRef> attributes) {
        this.attributes = attributes;
    }
}

