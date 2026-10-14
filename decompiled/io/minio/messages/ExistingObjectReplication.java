/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Status;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="ExistingObjectReplication")
public class ExistingObjectReplication {
    @Element(name="Status")
    private Status status;

    public ExistingObjectReplication(@Nonnull @Element(name="Status") Status status) {
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }

    public Status status() {
        return this.status;
    }
}

