/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.saml.saml2.core.Evidentiary;

public interface EncryptedAssertion
extends EncryptedElementType,
Evidentiary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedAssertion";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion", "saml2");
}

