/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.Scoping;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public class AuthnRequestImpl
extends RequestAbstractTypeImpl
implements AuthnRequest {
    private Subject subject;
    private NameIDPolicy nameIDPolicy;
    private Conditions conditions;
    private RequestedAuthnContext requestedAuthnContext;
    private Scoping scoping;
    private XSBooleanValue forceAuthn;
    private XSBooleanValue isPassive;
    private String protocolBinding;
    private Integer assertionConsumerServiceIndex;
    private String assertionConsumerServiceURL;
    private Integer attributeConsumingServiceIndex;
    private String providerName;

    protected AuthnRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Boolean isForceAuthn() {
        if (this.forceAuthn != null) {
            return this.forceAuthn.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue isForceAuthnXSBoolean() {
        return this.forceAuthn;
    }

    @Override
    public void setForceAuthn(Boolean newForceAuth) {
        this.forceAuthn = newForceAuth != null ? this.prepareForAssignment(this.forceAuthn, new XSBooleanValue(newForceAuth, false)) : (XSBooleanValue)this.prepareForAssignment(this.forceAuthn, null);
    }

    @Override
    public void setForceAuthn(XSBooleanValue newForceAuthn) {
        this.forceAuthn = this.prepareForAssignment(this.forceAuthn, newForceAuthn);
    }

    @Override
    public Boolean isPassive() {
        if (this.isPassive != null) {
            return this.isPassive.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue isPassiveXSBoolean() {
        return this.isPassive;
    }

    @Override
    public void setIsPassive(Boolean newIsPassive) {
        this.isPassive = newIsPassive != null ? this.prepareForAssignment(this.isPassive, new XSBooleanValue(newIsPassive, false)) : (XSBooleanValue)this.prepareForAssignment(this.isPassive, null);
    }

    @Override
    public void setIsPassive(XSBooleanValue newIsPassive) {
        this.isPassive = this.prepareForAssignment(this.isPassive, newIsPassive);
    }

    @Override
    public String getProtocolBinding() {
        return this.protocolBinding;
    }

    @Override
    public void setProtocolBinding(String newProtocolBinding) {
        this.protocolBinding = this.prepareForAssignment(this.protocolBinding, newProtocolBinding);
    }

    @Override
    public Integer getAssertionConsumerServiceIndex() {
        return this.assertionConsumerServiceIndex;
    }

    @Override
    public void setAssertionConsumerServiceIndex(Integer newAssertionConsumerServiceIndex) {
        this.assertionConsumerServiceIndex = this.prepareForAssignment(this.assertionConsumerServiceIndex, newAssertionConsumerServiceIndex);
    }

    @Override
    public String getAssertionConsumerServiceURL() {
        return this.assertionConsumerServiceURL;
    }

    @Override
    public void setAssertionConsumerServiceURL(String newAssertionConsumerServiceURL) {
        this.assertionConsumerServiceURL = this.prepareForAssignment(this.assertionConsumerServiceURL, newAssertionConsumerServiceURL);
    }

    @Override
    public Integer getAttributeConsumingServiceIndex() {
        return this.attributeConsumingServiceIndex;
    }

    @Override
    public void setAttributeConsumingServiceIndex(Integer newAttributeConsumingServiceIndex) {
        this.attributeConsumingServiceIndex = this.prepareForAssignment(this.attributeConsumingServiceIndex, newAttributeConsumingServiceIndex);
    }

    @Override
    public String getProviderName() {
        return this.providerName;
    }

    @Override
    public void setProviderName(String newProviderName) {
        this.providerName = this.prepareForAssignment(this.providerName, newProviderName);
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(Subject newSubject) {
        this.subject = this.prepareForAssignment(this.subject, newSubject);
    }

    @Override
    public NameIDPolicy getNameIDPolicy() {
        return this.nameIDPolicy;
    }

    @Override
    public void setNameIDPolicy(NameIDPolicy newNameIDPolicy) {
        this.nameIDPolicy = this.prepareForAssignment(this.nameIDPolicy, newNameIDPolicy);
    }

    @Override
    public Conditions getConditions() {
        return this.conditions;
    }

    @Override
    public void setConditions(Conditions newConditions) {
        this.conditions = this.prepareForAssignment(this.conditions, newConditions);
    }

    @Override
    public RequestedAuthnContext getRequestedAuthnContext() {
        return this.requestedAuthnContext;
    }

    @Override
    public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext) {
        this.requestedAuthnContext = this.prepareForAssignment(this.requestedAuthnContext, newRequestedAuthnContext);
    }

    @Override
    public Scoping getScoping() {
        return this.scoping;
    }

    @Override
    public void setScoping(Scoping newScoping) {
        this.scoping = this.prepareForAssignment(this.scoping, newScoping);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.subject != null) {
            children.add(this.subject);
        }
        if (this.nameIDPolicy != null) {
            children.add(this.nameIDPolicy);
        }
        if (this.conditions != null) {
            children.add(this.conditions);
        }
        if (this.requestedAuthnContext != null) {
            children.add(this.requestedAuthnContext);
        }
        if (this.scoping != null) {
            children.add(this.scoping);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

