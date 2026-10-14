/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.saml.saml2.ecp.RelayState;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public class RelayStateImpl
extends XSStringImpl
implements RelayState {
    private String soap11Actor;
    private XSBooleanValue soap11MustUnderstand;

    protected RelayStateImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
        return null;
    }
}

