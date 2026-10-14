/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.EventMetadata;
import io.minio.messages.EventType;
import io.minio.messages.Identity;
import io.minio.messages.ResponseDate;
import io.minio.messages.Source;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressFBWarnings(value={"UuF"}, justification="eventVersion and eventSource are available for completeness")
public class Event {
    @JsonProperty
    private String eventVersion;
    @JsonProperty
    private String eventSource;
    @JsonProperty
    private String awsRegion;
    @JsonProperty
    private EventType eventName;
    @JsonProperty
    private Identity userIdentity;
    @JsonProperty
    private Map<String, String> requestParameters;
    @JsonProperty
    private Map<String, String> responseElements;
    @JsonProperty
    private EventMetadata s3;
    @JsonProperty
    private Source source;
    @JsonProperty
    private ResponseDate eventTime;

    public String region() {
        return this.awsRegion;
    }

    public ZonedDateTime eventTime() {
        return this.eventTime.zonedDateTime();
    }

    public EventType eventType() {
        return this.eventName;
    }

    public String userId() {
        if (this.userIdentity == null) {
            return null;
        }
        return this.userIdentity.principalId();
    }

    public Map<String, String> requestParameters() {
        return Collections.unmodifiableMap(this.requestParameters == null ? new HashMap() : this.requestParameters);
    }

    public Map<String, String> responseElements() {
        return Collections.unmodifiableMap(this.responseElements == null ? new HashMap() : this.responseElements);
    }

    public String bucketName() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.bucketName();
    }

    public String bucketOwner() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.bucketOwner();
    }

    public String bucketArn() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.bucketArn();
    }

    public String objectName() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.objectName();
    }

    public long objectSize() {
        if (this.s3 == null) {
            return -1L;
        }
        return this.s3.objectSize();
    }

    public String etag() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.etag();
    }

    public String objectVersionId() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.objectVersionId();
    }

    public String sequencer() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.sequencer();
    }

    public Map<String, String> userMetadata() {
        if (this.s3 == null) {
            return null;
        }
        return this.s3.userMetadata();
    }

    public String host() {
        if (this.source == null) {
            return null;
        }
        return this.source.host();
    }

    public String port() {
        if (this.source == null) {
            return null;
        }
        return this.source.port();
    }

    public String userAgent() {
        if (this.source == null) {
            return null;
        }
        return this.source.userAgent();
    }
}

