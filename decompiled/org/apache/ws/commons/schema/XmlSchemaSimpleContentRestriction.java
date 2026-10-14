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
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;

public class XmlSchemaSimpleContentRestriction
extends XmlSchemaContent {
    XmlSchemaAnyAttribute anyAttribute;
    private List<XmlSchemaAttributeOrGroupRef> attributes;
    private XmlSchemaSimpleType baseType;
    private QName baseTypeName;
    private List<XmlSchemaFacet> facets = Collections.synchronizedList(new ArrayList());

    public XmlSchemaSimpleContentRestriction() {
        this.attributes = Collections.synchronizedList(new ArrayList());
    }

    public void setAnyAttribute(XmlSchemaAnyAttribute anyAttribute) {
        this.anyAttribute = anyAttribute;
    }

    public XmlSchemaAnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public List<XmlSchemaAttributeOrGroupRef> getAttributes() {
        return this.attributes;
    }

    public void setBaseType(XmlSchemaSimpleType baseType) {
        this.baseType = baseType;
    }

    public XmlSchemaSimpleType getBaseType() {
        return this.baseType;
    }

    public void setBaseTypeName(QName baseTypeName) {
        this.baseTypeName = baseTypeName;
    }

    public QName getBaseTypeName() {
        return this.baseTypeName;
    }

    public List<XmlSchemaFacet> getFacets() {
        return this.facets;
    }
}

