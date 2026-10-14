/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Status;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="DeleteMarkerReplication")
public class DeleteMarkerReplication {
    @Element(name="Status", required=false)
    private Status status;

    public DeleteMarkerReplication(@Nullable @Element(name="Status", required=false) Status status) {
        this.status = status == null ? Status.DISABLED : status;
    }

    public Status status() {
        return this.status;
    }
}

