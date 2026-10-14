/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2cb.impl;

import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public class ChannelBindingsImpl
extends XSBase64BinaryImpl
implements ChannelBindings {
    private String type;
    private String soap11Actor;
    private XSBooleanValue soap11MustUnderstand;

    protected ChannelBindingsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String newType) {
        this.type = this.prepareForAssignment(this.type, newType);
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
}

