/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.ecp.Request;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public class RequestImpl
extends AbstractSAMLObject
implements Request {
    private IDPList idpList;
    private Issuer issuer;
    private String providerName;
    private XSBooleanValue isPassive;
    private String soap11Actor;
    private XSBooleanValue soap11MustUnderstand;

    protected RequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public IDPList getIDPList() {
        return this.idpList;
    }

    @Override
    public void setIDPList(IDPList newIDPList) {
        this.idpList = this.prepareForAssignment(this.idpList, newIDPList);
    }

    @Override
    public Issuer getIssuer() {
        return this.issuer;
    }

    @Override
    public void setIssuer(Issuer newIssuer) {
        this.issuer = this.prepareForAssignment(this.issuer, newIssuer);
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
    public void setPassive(Boolean newIsPassive) {
        this.isPassive = newIsPassive != null ? this.prepareForAssignment(this.isPassive, new XSBooleanValue(newIsPassive, false)) : (XSBooleanValue)this.prepareForAssignment(this.isPassive, null);
    }

    @Override
    public void setPassive(XSBooleanValue newIsPassive) {
        this.isPassive = this.prepareForAssignment(this.isPassive, newIsPassive);
    }

    @Override
    public Boolean isSOAP11MustUnderstand() {
        if (this.soap11MustUnderstand != null) {
            return this.soap11MustUnderstand.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue isSOAP11MustUnderstandXSBoolean() {
        return this.soap11MustUnderstand;
    }

    @Override
    public void setSOAP11MustUnderstand(Boolean newMustUnderstand) {
        this.soap11MustUnderstand = newMustUnderstand != null ? this.prepareForAssignment(this.soap11MustUnderstand, new XSBooleanValue(newMustUnderstand, true)) : (XSBooleanValue)this.prepareForAssignment(this.soap11MustUnderstand, null);
        this.manageQualifiedAttributeNamespace(MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME, this.soap11MustUnderstand != null);
    }

    @Override
    public void setSOAP11MustUnderstand(XSBooleanValue newMustUnderstand) {
        this.soap11MustUnderstand = this.prepareForAssignment(this.soap11MustUnderstand, newMustUnderstand);
        this.manageQualifiedAttributeNamespace(MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME, this.soap11MustUnderstand != null);
    }

    @Override
    public String getSOAP11Actor() {
        return this.soap11Actor;
    }

    @Override
    public void setSOAP11Actor(String newActor) {
        this.soap11Actor = this.prepareForAssignment(this.soap11Actor, newActor);
        this.manageQualifiedAttributeNamespace(ActorBearing.SOAP11_ACTOR_ATTR_NAME, this.soap11Actor != null);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        if (this.issuer != null) {
            children.add(this.issuer);
        }
        if (this.idpList != null) {
            children.add(this.idpList);
        }
        return Collections.unmodifiableList(children);
    }
}

