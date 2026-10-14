/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.CloudFunctionConfiguration;
import io.minio.messages.QueueConfiguration;
import io.minio.messages.TopicConfiguration;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="NotificationConfiguration", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class NotificationConfiguration {
    @ElementList(name="CloudFunctionConfiguration", inline=true, required=false)
    private List<CloudFunctionConfiguration> cloudFunctionConfigurationList;
    @ElementList(name="QueueConfiguration", inline=true, required=false)
    private List<QueueConfiguration> queueConfigurationList;
    @ElementList(name="TopicConfiguration", inline=true, required=false)
    private List<TopicConfiguration> topicConfigurationList;

    public List<CloudFunctionConfiguration> cloudFunctionConfigurationList() {
        return Collections.unmodifiableList(this.cloudFunctionConfigurationList == null ? new LinkedList() : this.cloudFunctionConfigurationList);
    }

    public void setCloudFunctionConfigurationList(List<CloudFunctionConfiguration> cloudFunctionConfigurationList) {
        this.cloudFunctionConfigurationList = Collections.unmodifiableList(cloudFunctionConfigurationList);
    }

    public List<QueueConfiguration> queueConfigurationList() {
        return Collections.unmodifiableList(this.queueConfigurationList == null ? new LinkedList() : this.queueConfigurationList);
    }

    public void setQueueConfigurationList(List<QueueConfiguration> queueConfigurationList) {
        this.queueConfigurationList = Collections.unmodifiableList(queueConfigurationList);
    }

    public List<TopicConfiguration> topicConfigurationList() {
        return Collections.unmodifiableList(this.topicConfigurationList == null ? new LinkedList() : this.topicConfigurationList);
    }

    public void setTopicConfigurationList(List<TopicConfiguration> topicConfigurationList) {
        this.topicConfigurationList = Collections.unmodifiableList(topicConfigurationList);
    }
}

