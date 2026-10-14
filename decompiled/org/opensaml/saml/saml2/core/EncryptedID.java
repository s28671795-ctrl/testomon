/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.EncryptedElementType;

public interface EncryptedID
extends EncryptedElementType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedID";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedID", "saml2");
}

