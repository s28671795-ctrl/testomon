/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.binding.artifact;

import org.opensaml.saml.common.binding.artifact.AbstractSAMLArtifact;
import org.opensaml.saml.saml1.binding.artifact.SAML1Artifact;

public abstract class AbstractSAML1Artifact
extends AbstractSAMLArtifact
implements SAML1Artifact {
    protected AbstractSAML1Artifact(byte[] typeCode) {
        super(typeCode);
    }
}

