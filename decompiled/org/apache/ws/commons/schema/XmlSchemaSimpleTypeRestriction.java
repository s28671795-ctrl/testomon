/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaFacet;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeContent;

public class XmlSchemaSimpleTypeRestriction
extends XmlSchemaSimpleTypeContent {
    private XmlSchemaSimpleType baseType;
    private QName baseTypeName;
    private List<XmlSchemaFacet> facets = Collections.synchronizedList(new ArrayList());

    public XmlSchemaSimpleType getBaseType() {
        return this.baseType;
    }

    public void setBaseType(XmlSchemaSimpleType baseType) {
        this.baseType = baseType;
    }

    public QName getBaseTypeName() {
        return this.baseTypeName;
    }

    public void setBaseTypeName(QName baseTypeName) {
        this.baseTypeName = baseTypeName;
    }

    public List<XmlSchemaFacet> getFacets() {
        return this.facets;
    }
}

