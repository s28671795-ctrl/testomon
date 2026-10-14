/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;

public interface EncryptedElementType
extends SAMLObject {
    public static final String TYPE_LOCAL_NAME = "EncryptedElementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedElementType", "saml2");

    public EncryptedData getEncryptedData();

    public void setEncryptedData(EncryptedData var1);

    public List<EncryptedKey> getEncryptedKeys();
}

