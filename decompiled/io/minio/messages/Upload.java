/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Initiator;
import io.minio.messages.Owner;
import io.minio.messages.ResponseDate;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="Upload", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class Upload {
    @Element(name="Key")
    private String objectName;
    @Element(name="UploadId")
    private String uploadId;
    @Element(name="Initiator")
    private Initiator initiator;
    @Element(name="Owner")
    private Owner owner;
    @Element(name="StorageClass")
    private String storageClass;
    @Element(name="Initiated")
    private ResponseDate initiated;
    private long aggregatedPartSize;
    private String encodingType = null;

    public String objectName() {
        try {
            return "url".equals(this.encodingType) ? URLDecoder.decode(this.objectName, StandardCharsets.UTF_8.name()) : this.objectName;
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadId() {
        return this.uploadId;
    }

    public Initiator initiator() {
        return this.initiator;
    }

    public Owner owner() {
        return this.owner;
    }

    public String storageClass() {
        return this.storageClass;
    }

    public ZonedDateTime initiated() {
        return this.initiated.zonedDateTime();
    }

    public long aggregatedPartSize() {
        return this.aggregatedPartSize;
    }

    public void setAggregatedPartSize(long size) {
        this.aggregatedPartSize = size;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }
}

