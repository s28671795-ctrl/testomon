/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Artifact;

public class ArtifactImpl
extends AbstractSAMLObject
implements Artifact {
    private String artifact;

    protected ArtifactImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getArtifact() {
        return this.artifact;
    }

    @Override
    public void setArtifact(String newArtifact) {
        this.artifact = this.prepareForAssignment(this.artifact, newArtifact);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

