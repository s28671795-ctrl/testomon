/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.binding.artifact;

import java.util.Arrays;
import org.opensaml.saml.common.binding.artifact.SAMLSourceIDArtifact;
import org.opensaml.saml.saml1.binding.artifact.AbstractSAML1Artifact;

public class SAML1ArtifactType0001
extends AbstractSAML1Artifact
implements SAMLSourceIDArtifact {
    public static final byte[] TYPE_CODE = new byte[]{0, 1};
    private byte[] sourceID;
    private byte[] assertionHandle;

    public SAML1ArtifactType0001() {
        super(TYPE_CODE);
    }

    public SAML1ArtifactType0001(byte[] source2, byte[] handle2) {
        super(TYPE_CODE);
        this.setSourceID(source2);
        this.setAssertionHandle(handle2);
    }

    public static SAML1ArtifactType0001 parseArtifact(byte[] artifact) {
        if (artifact.length != 42) {
            throw new IllegalArgumentException("Artifact length must be 42 bytes it was " + artifact.length + "bytes");
        }
        byte[] typeCode = new byte[]{artifact[0], artifact[1]};
        if (!Arrays.equals(typeCode, TYPE_CODE)) {
            throw new IllegalArgumentException("Artifact is not of appropriate type.");
        }
        byte[] sourceID = new byte[20];
        System.arraycopy(artifact, 2, sourceID, 0, 20);
        byte[] assertionHandle = new byte[20];
        System.arraycopy(artifact, 22, assertionHandle, 0, 20);
        return new SAML1ArtifactType0001(sourceID, assertionHandle);
    }

    @Override
    public byte[] getSourceID() {
        return this.sourceID;
    }

    protected void setSourceID(byte[] newSourceID) {
        if (newSourceID.length != 20) {
            throw new IllegalArgumentException("Artifact source ID must be 20 bytes long");
        }
        this.sourceID = newSourceID;
    }

    public byte[] getAssertionHandle() {
        return this.assertionHandle;
    }

    public void setAssertionHandle(byte[] handle2) {
        if (handle2.length != 20) {
            throw new IllegalArgumentException("Artifact assertion handle must be 20 bytes long");
        }
        this.assertionHandle = handle2;
    }

    @Override
    public byte[] getRemainingArtifact() {
        byte[] remainingArtifact = new byte[40];
        System.arraycopy(this.getSourceID(), 0, remainingArtifact, 0, 20);
        System.arraycopy(this.getAssertionHandle(), 0, remainingArtifact, 20, 20);
        return remainingArtifact;
    }
}

