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
public class Source {
    @JsonProperty
    private String host;
    @JsonProperty
    private String port;
    @JsonProperty
    private String userAgent;

    public String host() {
        return this.host;
    }

    public String port() {
        return this.port;
    }

    public String userAgent() {
        return this.userAgent;
    }
}

