/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.binding.artifact;

import java.util.HashMap;
import java.util.Map;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import org.opensaml.saml.saml1.binding.artifact.AbstractSAML1Artifact;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilder;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactType0001;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactType0001Builder;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactType0002;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactType0002Builder;

public class SAML1ArtifactBuilderFactory {
    private Map<String, SAML1ArtifactBuilder> artifactBuilders = new HashMap<String, SAML1ArtifactBuilder>(2);

    public SAML1ArtifactBuilderFactory() {
        this.artifactBuilders.put(new String(SAML1ArtifactType0001.TYPE_CODE), new SAML1ArtifactType0001Builder());
        this.artifactBuilders.put(new String(SAML1ArtifactType0002.TYPE_CODE), new SAML1ArtifactType0002Builder());
    }

    public Map<String, SAML1ArtifactBuilder> getArtifactBuilders() {
        return this.artifactBuilders;
    }

    public SAML1ArtifactBuilder getArtifactBuilder(byte[] type) {
        return this.artifactBuilders.get(new String(type));
    }

    public AbstractSAML1Artifact buildArtifact(String base64Artifact) {
        return this.buildArtifact(Base64Support.decode(base64Artifact));
    }

    public AbstractSAML1Artifact buildArtifact(byte[] artifact) {
        if (artifact == null) {
            return null;
        }
        byte[] type = new byte[]{artifact[0], artifact[1]};
        SAML1ArtifactBuilder artifactBuilder = this.getArtifactBuilder(type);
        if (artifactBuilder == null) {
            throw new IllegalArgumentException("Saw unsupported artifact type: " + new String(type));
        }
        return artifactBuilder.buildArtifact(artifact);
    }
}

