/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ResponseDate;
import io.minio.messages.RetentionMode;
import java.time.ZonedDateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="Retention", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class Retention {
    @Element(name="Mode", required=false)
    private RetentionMode mode;
    @Element(name="RetainUntilDate", required=false)
    private ResponseDate retainUntilDate;

    public Retention() {
    }

    public Retention(RetentionMode mode, ZonedDateTime retainUntilDate) {
        if (mode == null) {
            throw new IllegalArgumentException("null mode is not allowed");
        }
        if (retainUntilDate == null) {
            throw new IllegalArgumentException("null retainUntilDate is not allowed");
        }
        this.mode = mode;
        this.retainUntilDate = new ResponseDate(retainUntilDate);
    }

    public RetentionMode mode() {
        return this.mode;
    }

    public ZonedDateTime retainUntilDate() {
        if (this.retainUntilDate != null) {
            return this.retainUntilDate.zonedDateTime();
        }
        return null;
    }
}

