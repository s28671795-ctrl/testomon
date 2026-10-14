/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml.saml2.metadata.ServiceDescription;
import org.opensaml.saml.saml2.metadata.ServiceName;

public class AttributeConsumingServiceImpl
extends AbstractSAMLObject
implements AttributeConsumingService {
    private int index;
    private XSBooleanValue isDefault;
    private final XMLObjectChildrenList<ServiceName> serviceNames = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<ServiceDescription> serviceDescriptions = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<RequestedAttribute> requestAttributes = new XMLObjectChildrenList(this);

    protected AttributeConsumingServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public void setIndex(int theIndex) {
        if (this.index != theIndex) {
            this.releaseThisandParentDOM();
            this.index = theIndex;
        }
    }

    @Override
    public Boolean isDefault() {
        if (this.isDefault != null) {
            return this.isDefault.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue isDefaultXSBoolean() {
        return this.isDefault;
    }

    @Override
    public void setIsDefault(Boolean newIsDefault) {
        this.isDefault = newIsDefault != null ? this.prepareForAssignment(this.isDefault, new XSBooleanValue(newIsDefault, false)) : (XSBooleanValue)this.prepareForAssignment(this.isDefault, null);
    }

    @Override
    public void setIsDefault(XSBooleanValue newIsDefault) {
        this.isDefault = this.prepareForAssignment(this.isDefault, newIsDefault);
    }

    @Override
    public List<ServiceName> getNames() {
        return this.serviceNames;
    }

    @Override
    public List<ServiceDescription> getDescriptions() {
        return this.serviceDescriptions;
    }

    @Override
    public List<RequestedAttribute> getRequestAttributes() {
        return this.requestAttributes;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.addAll(this.serviceNames);
        children.addAll(this.serviceDescriptions);
        children.addAll(this.requestAttributes);
        return Collections.unmodifiableList(children);
    }
}

