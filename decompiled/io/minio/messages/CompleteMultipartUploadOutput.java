/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="CompleteMultipartUploadOutput")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class CompleteMultipartUploadOutput {
    @Element(name="Location")
    private String location;
    @Element(name="Bucket")
    private String bucket;
    @Element(name="Key")
    private String object;
    @Element(name="ETag")
    private String etag;
    @Element(name="ChecksumCRC32", required=false)
    private String checksumCRC32;
    @Element(name="ChecksumCRC32C", required=false)
    private String checksumCRC32C;
    @Element(name="ChecksumSHA1", required=false)
    private String checksumSHA1;
    @Element(name="ChecksumSHA256", required=false)
    private String checksumSHA256;

    public String location() {
        return this.location;
    }

    public String bucket() {
        return this.bucket;
    }

    public String object() {
        return this.object;
    }

    public String etag() {
        return this.etag;
    }

    public String checksumCRC32() {
        return this.checksumCRC32;
    }

    public String checksumCRC32C() {
        return this.checksumCRC32C;
    }

    public String checksumSHA1() {
        return this.checksumSHA1;
    }

    public String checksumSHA256() {
        return this.checksumSHA256;
    }
}

