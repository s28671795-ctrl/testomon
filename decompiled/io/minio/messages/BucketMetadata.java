/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.Identity;

@SuppressFBWarnings(value={"UwF"}, justification="Everything in this class is initialized by JSON unmarshalling.")
public class BucketMetadata {
    @JsonProperty
    private String name;
    @JsonProperty
    private Identity ownerIdentity;
    @JsonProperty
    private String arn;

    public String name() {
        return this.name;
    }

    public String owner() {
        if (this.ownerIdentity == null) {
            return null;
        }
        return this.ownerIdentity.principalId();
    }

    public String arn() {
        return this.arn;
    }
}

