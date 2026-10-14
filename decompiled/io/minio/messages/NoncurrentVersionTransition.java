/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.NoncurrentVersionExpiration;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="NoncurrentVersionTransition")
public class NoncurrentVersionTransition
extends NoncurrentVersionExpiration {
    @Element(name="StorageClass")
    private String storageClass;

    public NoncurrentVersionTransition(@Element(name="NoncurrentDays", required=false) int noncurrentDays, @Nonnull @Element(name="StorageClass", required=false) String storageClass) {
        super(noncurrentDays);
        if (storageClass == null || storageClass.isEmpty()) {
            throw new IllegalArgumentException("StorageClass must be provided");
        }
        this.storageClass = storageClass;
    }

    public String storageClass() {
        return this.storageClass;
    }
}

