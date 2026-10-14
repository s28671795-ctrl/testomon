/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.saml2.binding.artifact;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilder;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactType0004;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML2ArtifactType0004Builder
implements SAML2ArtifactBuilder<SAML2ArtifactType0004> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SAML2ArtifactType0004Builder.class);

    @Override
    @Nullable
    public SAML2ArtifactType0004 buildArtifact(@Nonnull @NotEmpty byte[] artifact) {
        return SAML2ArtifactType0004.parseArtifact(artifact);
    }

    @Override
    @Nullable
    public SAML2ArtifactType0004 buildArtifact(@Nonnull MessageContext<SAMLObject> requestContext) {
        try {
            String sourceId = this.getSourceEntityId(requestContext);
            if (sourceId == null) {
                return null;
            }
            Integer arsIndex = this.getArsEndpointIndex(requestContext);
            if (arsIndex == null) {
                return null;
            }
            byte[] endpointIndex = this.intToByteArray(arsIndex);
            byte[] trimmedIndex = new byte[]{endpointIndex[2], endpointIndex[3]};
            MessageDigest sha1Digester = MessageDigest.getInstance("SHA-1");
            byte[] source2 = sha1Digester.digest(sourceId.getBytes());
            SecureRandom handleGenerator = SecureRandom.getInstance("SHA1PRNG");
            byte[] assertionHandle = new byte[20];
            handleGenerator.nextBytes(assertionHandle);
            return new SAML2ArtifactType0004(trimmedIndex, source2, assertionHandle);
        }
        catch (NoSuchAlgorithmException e) {
            this.log.warn("JVM does not support required cryptography algorithms: SHA-1/SHA1PRNG.", e);
            return null;
        }
    }

    @Nullable
    protected SAMLArtifactContext getArtifactContext(@Nonnull MessageContext<SAMLObject> requestContext) {
        return (SAMLArtifactContext)requestContext.getSubcontext(SAMLArtifactContext.class);
    }

    @Nullable
    protected Integer getArsEndpointIndex(@Nonnull MessageContext<SAMLObject> requestContext) {
        SAMLArtifactContext artifactContext = this.getArtifactContext(requestContext);
        if (artifactContext == null || artifactContext.getSourceArtifactResolutionServiceEndpointIndex() == null) {
            this.log.warn("No artifact resolution service endpoint index is available");
            return null;
        }
        return artifactContext.getSourceArtifactResolutionServiceEndpointIndex();
    }

    @Nullable
    protected String getSourceEntityId(@Nonnull MessageContext<SAMLObject> requestContext) {
        SAMLArtifactContext artifactContext = this.getArtifactContext(requestContext);
        if (artifactContext != null) {
            if (artifactContext.getSourceEntityId() != null) {
                return artifactContext.getSourceEntityId();
            }
            this.log.warn("SAMLArtifactContext did not contain a source entityID");
        } else {
            this.log.warn("Message context did not contain a SAMLArtifactContext");
        }
        return null;
    }

    @Nonnull
    @NotEmpty
    private byte[] intToByteArray(int integer) {
        byte[] intBytes = new byte[]{(byte)((integer & 0xFF000000) >>> 24), (byte)((integer & 0xFF0000) >>> 16), (byte)((integer & 0xFF00) >>> 8), (byte)(integer & 0xFF)};
        return intBytes;
    }
}

