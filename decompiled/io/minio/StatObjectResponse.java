/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.GenericResponse;
import io.minio.Time;
import io.minio.messages.LegalHold;
import io.minio.messages.ResponseDate;
import io.minio.messages.RetentionMode;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import okhttp3.Headers;

public class StatObjectResponse
extends GenericResponse {
    private String etag;
    private long size;
    private ZonedDateTime lastModified;
    private RetentionMode retentionMode;
    private ZonedDateTime retentionRetainUntilDate;
    private LegalHold legalHold;
    private boolean deleteMarker;
    private Map<String, String> userMetadata;

    public StatObjectResponse(Headers headers, String bucket, String region, String object) {
        super(headers, bucket, region, object);
        String value = headers.get("ETag");
        this.etag = value != null ? value.replaceAll("\"", "") : "";
        value = headers.get("Content-Length");
        this.size = value != null ? Long.parseLong(value) : -1L;
        this.lastModified = ZonedDateTime.parse(headers.get("Last-Modified"), Time.HTTP_HEADER_DATE_FORMAT);
        value = headers.get("x-amz-object-lock-mode");
        this.retentionMode = value != null ? RetentionMode.valueOf(value) : null;
        value = headers.get("x-amz-object-lock-retain-until-date");
        this.retentionRetainUntilDate = value != null ? ResponseDate.fromString(value).zonedDateTime() : null;
        this.legalHold = new LegalHold("ON".equals(headers.get("x-amz-object-lock-legal-hold")));
        this.deleteMarker = Boolean.parseBoolean(headers.get("x-amz-delete-marker"));
        HashMap<String, String> userMetadata = new HashMap<String, String>();
        for (String key : headers.names()) {
            if (!key.toLowerCase(Locale.US).startsWith("x-amz-meta-")) continue;
            userMetadata.put(key.toLowerCase(Locale.US).substring("x-amz-meta-".length(), key.length()), headers.get(key));
        }
        this.userMetadata = Collections.unmodifiableMap(userMetadata);
    }

    public String etag() {
        return this.etag;
    }

    public long size() {
        return this.size;
    }

    public ZonedDateTime lastModified() {
        return this.lastModified;
    }

    public RetentionMode retentionMode() {
        return this.retentionMode;
    }

    public ZonedDateTime retentionRetainUntilDate() {
        return this.retentionRetainUntilDate;
    }

    public LegalHold legalHold() {
        return this.legalHold;
    }

    public boolean deleteMarker() {
        return this.deleteMarker;
    }

    public String versionId() {
        return this.headers().get("x-amz-version-id");
    }

    public String contentType() {
        return this.headers().get("Content-Type");
    }

    public Map<String, String> userMetadata() {
        return this.userMetadata;
    }

    public String toString() {
        return "ObjectStat{bucket=" + this.bucket() + ", object=" + this.object() + ", last-modified=" + this.lastModified + ", size=" + this.size + "}";
    }
}

