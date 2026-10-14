/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ReplicationTimeValue;
import io.minio.messages.Status;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="ReplicationTime")
public class ReplicationTime {
    @Element(name="Time")
    private ReplicationTimeValue time;
    @Element(name="Status")
    private Status status;

    public ReplicationTime(@Nonnull @Element(name="Time") ReplicationTimeValue time, @Nonnull @Element(name="Status") Status status) {
        this.time = Objects.requireNonNull(time, "Time must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }

    public ReplicationTimeValue time() {
        return this.time;
    }

    public Status status() {
        return this.status;
    }
}

