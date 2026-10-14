/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="EncryptionConfiguration")
public class EncryptionConfiguration {
    @Element(name="ReplicaKmsKeyID", required=false)
    private String replicaKmsKeyID;

    public EncryptionConfiguration(@Nullable @Element(name="ReplicaKmsKeyID", required=false) String replicaKmsKeyID) {
        this.replicaKmsKeyID = replicaKmsKeyID;
    }

    public String replicaKmsKeyID() {
        return this.replicaKmsKeyID;
    }
}

