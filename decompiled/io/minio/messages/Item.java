/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.DeleteMarker;
import io.minio.messages.Metadata;
import io.minio.messages.Owner;
import io.minio.messages.ResponseDate;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;
import org.simpleframework.xml.Element;

public abstract class Item {
    private static final String UTF_8 = StandardCharsets.UTF_8.toString();
    @Element(name="ETag", required=false)
    private String etag;
    @Element(name="Key")
    private String objectName;
    @Element(name="LastModified")
    private ResponseDate lastModified;
    @Element(name="Owner", required=false)
    private Owner owner;
    @Element(name="Size", required=false)
    private long size;
    @Element(name="StorageClass", required=false)
    private String storageClass;
    @Element(name="IsLatest", required=false)
    private boolean isLatest;
    @Element(name="VersionId", required=false)
    private String versionId;
    @Element(name="UserMetadata", required=false)
    private Metadata userMetadata;
    @Element(name="UserTags", required=false)
    private String userTags;
    private boolean isDir = false;
    private String encodingType = null;

    public Item() {
    }

    public Item(String prefix) {
        this.objectName = prefix;
        this.isDir = true;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public String objectName() {
        try {
            return "url".equals(this.encodingType) ? URLDecoder.decode(this.objectName, UTF_8) : this.objectName;
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public ZonedDateTime lastModified() {
        return this.lastModified.zonedDateTime();
    }

    public String etag() {
        return this.etag;
    }

    public long size() {
        return this.size;
    }

    public String storageClass() {
        return this.storageClass;
    }

    public Owner owner() {
        return this.owner;
    }

    public Map<String, String> userMetadata() {
        return this.userMetadata == null ? null : this.userMetadata.get();
    }

    public String userTags() {
        return this.userTags;
    }

    public boolean isLatest() {
        return this.isLatest;
    }

    public String versionId() {
        return this.versionId;
    }

    public boolean isDir() {
        return this.isDir;
    }

    public boolean isDeleteMarker() {
        return this instanceof DeleteMarker;
    }
}

