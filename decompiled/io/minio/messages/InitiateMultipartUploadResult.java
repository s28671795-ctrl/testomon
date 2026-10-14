/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="InitiateMultipartUploadResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class InitiateMultipartUploadResult {
    @Element(name="Bucket")
    private String bucketName;
    @Element(name="Key")
    private String objectName;
    @Element(name="UploadId")
    private String uploadId;

    public String bucketName() {
        return this.bucketName;
    }

    public String objectName() {
        return this.objectName;
    }

    public String uploadId() {
        return this.uploadId;
    }
}

