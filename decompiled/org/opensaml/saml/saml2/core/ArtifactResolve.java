/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Artifact;
import org.opensaml.saml.saml2.core.RequestAbstractType;

public interface ArtifactResolve
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ArtifactResolve";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResolve", "saml2p");
    public static final String TYPE_LOCAL_NAME = "ArtifactResolveType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResolveType", "saml2p");

    public Artifact getArtifact();

    public void setArtifact(Artifact var1);
}

