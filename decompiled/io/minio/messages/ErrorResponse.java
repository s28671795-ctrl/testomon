/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import java.io.Serializable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ErrorResponse", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ErrorResponse
implements Serializable {
    private static final long serialVersionUID = 1905162041950251407L;
    @Element(name="Code")
    protected String code;
    @Element(name="Message", required=false)
    protected String message;
    @Element(name="BucketName", required=false)
    protected String bucketName;
    @Element(name="Key", required=false)
    protected String objectName;
    @Element(name="Resource", required=false)
    protected String resource;
    @Element(name="RequestId", required=false)
    protected String requestId;
    @Element(name="HostId", required=false)
    protected String hostId;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String message, String bucketName, String objectName, String resource, String requestId, String hostId) {
        this.code = code;
        this.message = message;
        this.bucketName = bucketName;
        this.objectName = objectName;
        this.resource = resource;
        this.requestId = requestId;
        this.hostId = hostId;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public String bucketName() {
        return this.bucketName;
    }

    public String objectName() {
        return this.objectName;
    }

    public String hostId() {
        return this.hostId;
    }

    public String requestId() {
        return this.requestId;
    }

    public String resource() {
        return this.resource;
    }

    public String toString() {
        return "ErrorResponse(code = " + this.code + ", message = " + this.message + ", bucketName = " + this.bucketName + ", objectName = " + this.objectName + ", resource = " + this.resource + ", requestId = " + this.requestId + ", hostId = " + this.hostId + ")";
    }
}

