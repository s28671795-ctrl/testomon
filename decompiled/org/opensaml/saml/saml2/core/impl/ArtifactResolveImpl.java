/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.Artifact;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public class ArtifactResolveImpl
extends RequestAbstractTypeImpl
implements ArtifactResolve {
    private Artifact artifact;

    protected ArtifactResolveImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Artifact getArtifact() {
        return this.artifact;
    }

    @Override
    public void setArtifact(Artifact newArtifact) {
        this.artifact = this.prepareForAssignment(this.artifact, newArtifact);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.artifact != null) {
            children.add(this.artifact);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

