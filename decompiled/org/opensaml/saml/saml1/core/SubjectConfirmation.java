/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface SubjectConfirmation
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation", "saml1");
    public static final String TYPE_LOCAL_NAME = "SubjectConfirmationType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationType", "saml1");

    public List<ConfirmationMethod> getConfirmationMethods();

    public void setSubjectConfirmationData(XMLObject var1);

    public XMLObject getSubjectConfirmationData();

    public KeyInfo getKeyInfo();

    public void setKeyInfo(KeyInfo var1);
}

