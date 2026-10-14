/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value={"UwF"}, justification="Everything in this class is initialized by JSON unmarshalling.")
public class Identity {
    @JsonProperty
    private String principalId;

    public String principalId() {
        return this.principalId;
    }
}

