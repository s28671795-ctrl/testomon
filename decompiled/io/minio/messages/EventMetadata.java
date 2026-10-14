/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.BucketMetadata;
import io.minio.messages.ObjectMetadata;
import java.util.Map;

@SuppressFBWarnings(value={"UwF", "UuF"}, justification="Everything in this class is initialized by JSON unmarshalling and s3SchemaVersion/configurationId are available for completeness.")
public class EventMetadata {
    @JsonProperty
    private String s3SchemaVersion;
    @JsonProperty
    private String configurationId;
    @JsonProperty
    private BucketMetadata bucket;
    @JsonProperty
    private ObjectMetadata object;

    public String bucketName() {
        if (this.bucket == null) {
            return null;
        }
        return this.bucket.name();
    }

    public String bucketOwner() {
        if (this.bucket == null) {
            return null;
        }
        return this.bucket.owner();
    }

    public String bucketArn() {
        if (this.bucket == null) {
            return null;
        }
        return this.bucket.arn();
    }

    public String objectName() {
        if (this.object == null) {
            return null;
        }
        return this.object.key();
    }

    public long objectSize() {
        if (this.object == null) {
            return -1L;
        }
        return this.object.size();
    }

    public String etag() {
        if (this.object == null) {
            return null;
        }
        return this.object.etag();
    }

    public String objectVersionId() {
        if (this.object == null) {
            return null;
        }
        return this.object.versionId();
    }

    public String sequencer() {
        if (this.object == null) {
            return null;
        }
        return this.object.sequencer();
    }

    public Map<String, String> userMetadata() {
        if (this.object == null) {
            return null;
        }
        return this.object.userMetadata();
    }
}

