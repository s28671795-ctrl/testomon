/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.criterion;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;

public final class ArtifactCriterion
implements Criterion {
    @Nonnull
    private final SAMLArtifact artifact;

    public ArtifactCriterion(@Nonnull SAMLArtifact newArtifact) {
        this.artifact = Constraint.isNotNull(newArtifact, "SAMLArtifact cannot be null");
    }

    @Nonnull
    public SAMLArtifact getArtifact() {
        return this.artifact;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ArtifactCriterion [artifact=");
        builder.append(Hex.encodeHex(this.artifact.getArtifactBytes(), true));
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.artifact.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof ArtifactCriterion) {
            return Objects.equals(this.artifact, ((ArtifactCriterion)obj).artifact);
        }
        return false;
    }
}

