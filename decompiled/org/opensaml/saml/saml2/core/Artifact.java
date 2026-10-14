/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Artifact
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Artifact";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Artifact", "saml2p");
    public static final String TYPE_LOCAL_NAME = "ArtifactType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactType", "saml2p");

    public String getArtifact();

    public void setArtifact(String var1);
}

