/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestAbstractType;

public interface NameIDMappingRequest
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDMappingRequest";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingRequest", "saml2p");
    public static final String TYPE_LOCAL_NAME = "NameIDMappingRequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingRequestType", "saml2p");

    public BaseID getBaseID();

    public void setBaseID(BaseID var1);

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);

    public NameIDPolicy getNameIDPolicy();

    public void setNameIDPolicy(NameIDPolicy var1);
}

