/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.saml1.binding.artifact;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.binding.artifact.AbstractSAML1Artifact;
import org.opensaml.saml.saml1.core.Assertion;

public interface SAML1ArtifactBuilder<ArtifactType extends AbstractSAML1Artifact> {
    @Nullable
    public ArtifactType buildArtifact(@Nonnull MessageContext<SAMLObject> var1, @Nonnull Assertion var2);

    @Nullable
    public ArtifactType buildArtifact(@Nonnull @NotEmpty byte[] var1);
}

