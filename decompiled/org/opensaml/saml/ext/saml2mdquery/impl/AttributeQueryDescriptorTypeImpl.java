/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;
import org.opensaml.saml.ext.saml2mdquery.impl.QueryDescriptorTypeImpl;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.Endpoint;

public class AttributeQueryDescriptorTypeImpl
extends QueryDescriptorTypeImpl
implements AttributeQueryDescriptorType {
    private XMLObjectChildrenList<AttributeConsumingService> attributeConsumingServices = new XMLObjectChildrenList(this);

    protected AttributeQueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AttributeConsumingService> getAttributeConsumingServices() {
        return this.attributeConsumingServices;
    }

    @Override
    public List<Endpoint> getEndpoints() {
        return new ArrayList<Endpoint>();
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        return null;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.addAll(this.attributeConsumingServices);
        return Collections.unmodifiableList(children);
    }
}

