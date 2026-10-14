/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.NotificationCommonConfiguration;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="TopicConfiguration", strict=false)
public class TopicConfiguration
extends NotificationCommonConfiguration {
    @Element(name="Topic")
    private String topic;

    public String topic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

