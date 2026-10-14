/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ResponseDate;
import java.time.ZonedDateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Bucket", strict=false)
public class Bucket {
    @Element(name="Name")
    private String name;
    @Element(name="CreationDate")
    private ResponseDate creationDate;

    public String name() {
        return this.name;
    }

    public ZonedDateTime creationDate() {
        return this.creationDate.zonedDateTime();
    }
}

