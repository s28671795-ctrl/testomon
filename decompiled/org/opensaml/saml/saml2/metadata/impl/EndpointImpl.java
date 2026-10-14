/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Endpoint;

public abstract class EndpointImpl
extends AbstractSAMLObject
implements Endpoint {
    private String bindingId;
    private String location;
    private String responseLocation;
    private final AttributeMap unknownAttributes = new AttributeMap(this);
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);

    protected EndpointImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getBinding() {
        return this.bindingId;
    }

    @Override
    public void setBinding(String binding) {
        this.bindingId = this.prepareForAssignment(this.bindingId, binding);
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(String theLocation) {
        this.location = this.prepareForAssignment(this.location, theLocation);
    }

    @Override
    public String getResponseLocation() {
        return this.responseLocation;
    }

    @Override
    public void setResponseLocation(String theLocation) {
        this.responseLocation = this.prepareForAssignment(this.responseLocation, theLocation);
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.unknownChildren);
    }
}

