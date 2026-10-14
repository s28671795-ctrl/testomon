/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="ReplicationTimeValue")
public class ReplicationTimeValue {
    @Element(name="Minutes", required=false)
    private Integer minutes = 15;

    public ReplicationTimeValue(@Nullable @Element(name="Minutes", required=false) Integer minutes) {
        this.minutes = minutes;
    }

    public Integer minutes() {
        return this.minutes;
    }
}

