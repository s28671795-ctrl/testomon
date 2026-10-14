/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.SubjectConfirmation;

public interface Subject
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Subject";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Subject", "saml1");
    public static final String TYPE_LOCAL_NAME = "SubjectType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectType", "saml1");

    public NameIdentifier getNameIdentifier();

    public void setNameIdentifier(NameIdentifier var1);

    public SubjectConfirmation getSubjectConfirmation();

    public void setSubjectConfirmation(SubjectConfirmation var1);
}

