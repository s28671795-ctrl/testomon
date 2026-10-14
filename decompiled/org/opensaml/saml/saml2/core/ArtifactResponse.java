/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface ArtifactResponse
extends StatusResponseType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ArtifactResponse";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResponse", "saml2p");
    public static final String TYPE_LOCAL_NAME = "ArtifactResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResponseType", "saml2p");

    public SAMLObject getMessage();

    public void setMessage(SAMLObject var1);
}

