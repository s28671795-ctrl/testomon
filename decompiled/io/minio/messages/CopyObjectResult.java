/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ResponseDate;
import java.time.ZonedDateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="CopyObjectResult", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class CopyObjectResult {
    @Element(name="ETag")
    private String etag;
    @Element(name="LastModified")
    private ResponseDate lastModified;

    public String etag() {
        return this.etag;
    }

    public ZonedDateTime lastModified() {
        return this.lastModified.zonedDateTime();
    }
}

