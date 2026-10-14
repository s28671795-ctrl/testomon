/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.saml2.binding.artifact;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.binding.artifact.AbstractSAML2Artifact;

public interface SAML2ArtifactBuilder<ArtifactType extends AbstractSAML2Artifact> {
    @Nullable
    public ArtifactType buildArtifact(@Nonnull MessageContext<SAMLObject> var1);

    @Nullable
    public ArtifactType buildArtifact(@Nonnull @NotEmpty byte[] var1);
}

