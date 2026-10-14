/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface NameIDMappingResponse
extends StatusResponseType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDMappingResponse";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingResponse", "saml2p");
    public static final String TYPE_LOCAL_NAME = "NameIDMappingResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingResponseType", "saml2p");

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);
}

