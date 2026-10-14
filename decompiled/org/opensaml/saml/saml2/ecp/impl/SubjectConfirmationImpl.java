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
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml.saml2.ecp.SubjectConfirmation;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public class SubjectConfirmationImpl
extends AbstractSAMLObject
implements SubjectConfirmation {
    private String soap11Actor;
    private XSBooleanValue soap11MustUnderstand;
    private String method;
    private SubjectConfirmationData subjectConfirmationData;

    protected SubjectConfirmationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String newMethod) {
        this.method = this.prepareForAssignment(this.method, newMethod);
    }

    @Override
    public SubjectConfirmationData getSubjectConfirmationData() {
        return this.subjectConfirmationData;
    }

    @Override
    public void setSubjectConfirmationData(SubjectConfirmationData newSubjectConfirmationData) {
        this.subjectConfirmationData = this.prepareForAssignment(this.subjectConfirmationData, newSubjectConfirmationData);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SubjectConfirmationData> children = new ArrayList<SubjectConfirmationData>();
        if (this.subjectConfirmationData != null) {
            children.add(this.subjectConfirmationData);
        }
        return Collections.unmodifiableList(children);
    }
}

