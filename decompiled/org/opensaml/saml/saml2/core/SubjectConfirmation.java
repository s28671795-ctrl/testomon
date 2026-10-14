/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;

public interface SubjectConfirmation
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmation", "saml2");
    public static final String TYPE_LOCAL_NAME = "SubjectConfirmationType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationType", "saml2");
    public static final String METHOD_ATTRIB_NAME = "Method";
    public static final String METHOD_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
    public static final String METHOD_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
    public static final String METHOD_BEARER = "urn:oasis:names:tc:SAML:2.0:cm:bearer";

    public String getMethod();

    public void setMethod(String var1);

    public BaseID getBaseID();

    public void setBaseID(BaseID var1);

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);

    public SubjectConfirmationData getSubjectConfirmationData();

    public void setSubjectConfirmationData(SubjectConfirmationData var1);
}

