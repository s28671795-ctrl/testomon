/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Upload;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ListMultipartUploadsResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ListMultipartUploadsResult {
    @Element(name="Bucket")
    private String bucketName;
    @Element(name="EncodingType", required=false)
    private String encodingType;
    @Element(name="KeyMarker", required=false)
    private String keyMarker;
    @Element(name="UploadIdMarker", required=false)
    private String uploadIdMarker;
    @Element(name="NextKeyMarker", required=false)
    private String nextKeyMarker;
    @Element(name="NextUploadIdMarker", required=false)
    private String nextUploadIdMarker;
    @Element(name="MaxUploads")
    private int maxUploads;
    @Element(name="IsTruncated", required=false)
    private boolean isTruncated;
    @ElementList(name="Upload", inline=true, required=false)
    List<Upload> uploads;

    private String decodeIfNeeded(String value) {
        try {
            return value != null && "url".equals(this.encodingType) ? URLDecoder.decode(value, StandardCharsets.UTF_8.name()) : value;
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public String bucketName() {
        return this.bucketName;
    }

    public String keyMarker() {
        return this.decodeIfNeeded(this.keyMarker);
    }

    public String uploadIdMarker() {
        return this.uploadIdMarker;
    }

    public String nextKeyMarker() {
        return this.decodeIfNeeded(this.nextKeyMarker);
    }

    public String nextUploadIdMarker() {
        return this.nextUploadIdMarker;
    }

    public int maxUploads() {
        return this.maxUploads;
    }

    public String encodingType() {
        return this.encodingType;
    }

    public List<Upload> uploads() {
        if (this.uploads == null) {
            return Collections.unmodifiableList(new LinkedList());
        }
        return Collections.unmodifiableList(this.uploads);
    }
}

