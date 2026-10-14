/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.samlec.EncType;
import org.opensaml.saml.ext.samlec.SessionKey;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.xmlsec.signature.KeyInfo;

public class SessionKeyImpl
extends AbstractSAMLObject
implements SessionKey {
    private String soap11Actor;
    private XSBooleanValue soap11MustUnderstand;
    private String algorithm;
    private XMLObjectChildrenList<EncType> encTypes = new XMLObjectChildrenList(this);
    private KeyInfo keyInfo;

    protected SessionKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public void setAlgorithm(String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }

    @Override
    public List<EncType> getEncTypes() {
        return this.encTypes;
    }

    @Override
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }

    @Override
    public void setKeyInfo(KeyInfo newKeyInfo) {
        this.keyInfo = this.prepareForAssignment(this.keyInfo, newKeyInfo);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.encTypes);
        if (this.keyInfo != null) {
            children.add(this.keyInfo);
        }
        return Collections.unmodifiableList(children);
    }
}

