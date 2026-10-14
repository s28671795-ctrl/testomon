/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.NotificationCommonConfiguration;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="QueueConfiguration", strict=false)
public class QueueConfiguration
extends NotificationCommonConfiguration {
    @Element(name="Queue")
    private String queue;

    public String queue() {
        return this.queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}

