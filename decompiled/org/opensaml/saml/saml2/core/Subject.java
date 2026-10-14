/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

public interface Subject
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Subject";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Subject", "saml2");
    public static final String TYPE_LOCAL_NAME = "SubjectType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectType", "saml2");

    public BaseID getBaseID();

    public void setBaseID(BaseID var1);

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);

    public List<SubjectConfirmation> getSubjectConfirmations();
}

