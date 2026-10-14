/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ResponseDate;
import java.time.ZonedDateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Part", strict=false)
public class Part {
    @Element(name="PartNumber")
    private int partNumber;
    @Element(name="ETag")
    private String etag;
    @Element(name="LastModified", required=false)
    private ResponseDate lastModified;
    @Element(name="Size", required=false)
    private Long size;

    public Part() {
    }

    public Part(int partNumber, String etag) {
        this.partNumber = partNumber;
        this.etag = etag;
    }

    public int partNumber() {
        return this.partNumber;
    }

    public String etag() {
        return this.etag.replaceAll("\"", "");
    }

    public ZonedDateTime lastModified() {
        return this.lastModified.zonedDateTime();
    }

    public long partSize() {
        return this.size;
    }
}

