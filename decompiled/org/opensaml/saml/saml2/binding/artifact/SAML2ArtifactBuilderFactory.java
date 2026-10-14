/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.binding.artifact;

import java.util.Map;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import org.opensaml.saml.saml2.binding.artifact.AbstractSAML2Artifact;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilder;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactType0004;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactType0004Builder;

public class SAML2ArtifactBuilderFactory {
    private Map<String, SAML2ArtifactBuilder> artifactBuilders = new LazyMap<String, SAML2ArtifactBuilder>();

    public SAML2ArtifactBuilderFactory() {
        this.artifactBuilders.put(new String(SAML2ArtifactType0004.TYPE_CODE), new SAML2ArtifactType0004Builder());
    }

    public Map<String, SAML2ArtifactBuilder> getArtifactBuilders() {
        return this.artifactBuilders;
    }

    public SAML2ArtifactBuilder getArtifactBuilder(byte[] type) {
        return this.artifactBuilders.get(new String(type));
    }

    public AbstractSAML2Artifact buildArtifact(String base64Artifact) {
        return this.buildArtifact(Base64Support.decode(base64Artifact));
    }

    public AbstractSAML2Artifact buildArtifact(byte[] artifact) {
        if (artifact == null) {
            return null;
        }
        byte[] type = new byte[]{artifact[0], artifact[1]};
        SAML2ArtifactBuilder artifactBuilder = this.getArtifactBuilder(type);
        if (artifactBuilder == null) {
            throw new IllegalArgumentException("Saw unsupported artifact type: " + new String(type));
        }
        return artifactBuilder.buildArtifact(artifact);
    }
}

