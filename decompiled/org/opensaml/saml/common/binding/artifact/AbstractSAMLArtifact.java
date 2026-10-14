/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding.artifact;

import java.util.Arrays;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;

public abstract class AbstractSAMLArtifact
implements SAMLArtifact {
    private byte[] typeCode;

    protected AbstractSAMLArtifact(@Nonnull byte[] code) {
        if (code.length != 2) {
            throw new IllegalArgumentException("Type code was not 2-bytes in size");
        }
        this.typeCode = code;
    }

    @Override
    @Nonnull
    public byte[] getArtifactBytes() {
        byte[] remainingArtifact = this.getRemainingArtifact();
        byte[] artifact = new byte[2 + remainingArtifact.length];
        System.arraycopy(this.getTypeCode(), 0, artifact, 0, 2);
        System.arraycopy(remainingArtifact, 0, artifact, 2, remainingArtifact.length);
        return artifact;
    }

    @Override
    @Nonnull
    public byte[] getTypeCode() {
        return this.typeCode;
    }

    protected void setTypeCode(@Nonnull byte[] newTypeCode) {
        this.typeCode = Constraint.isNotNull(newTypeCode, "Type code cannot be null");
    }

    @Nonnull
    public abstract byte[] getRemainingArtifact();

    @Nonnull
    @NotEmpty
    public String base64Encode() {
        return Base64Support.encode(this.getArtifactBytes(), false);
    }

    @Nonnull
    @NotEmpty
    public String hexEncode() {
        return Hex.encodeHexString(this.getArtifactBytes());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractSAMLArtifact) {
            AbstractSAMLArtifact otherArtifact = (AbstractSAMLArtifact)o;
            return Arrays.equals(this.getArtifactBytes(), otherArtifact.getArtifactBytes());
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.getArtifactBytes());
    }

    public String toString() {
        return this.base64Encode();
    }
}

