/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="LegalHold", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class LegalHold {
    @Element(name="Status", required=false)
    private String status;

    public LegalHold() {
    }

    public LegalHold(boolean status) {
        this.status = status ? "ON" : "OFF";
    }

    public boolean status() {
        return this.status != null && this.status.equals("ON");
    }
}

