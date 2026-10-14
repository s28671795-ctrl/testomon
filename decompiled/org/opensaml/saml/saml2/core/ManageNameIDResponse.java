/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface ManageNameIDResponse
extends StatusResponseType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ManageNameIDResponse";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDResponse", "saml2p");
    public static final String TYPE_LOCAL_NAME = "ManageNameIDResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDResponseType", "saml2p");
}

