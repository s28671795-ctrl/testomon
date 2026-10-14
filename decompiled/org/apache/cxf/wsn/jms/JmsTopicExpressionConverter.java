/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jms;

import java.util.Iterator;
import java.util.List;
import javax.jms.Topic;
import javax.xml.namespace.QName;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.cxf.wsn.jms.InvalidTopicException;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;

public class JmsTopicExpressionConverter {
    public static final String SIMPLE_DIALECT = "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple";

    public TopicExpressionType toTopicExpression(Topic topic) {
        return this.toTopicExpression(topic.toString());
    }

    public TopicExpressionType toTopicExpression(ActiveMQTopic topic) {
        return this.toTopicExpression(topic.getPhysicalName());
    }

    public TopicExpressionType toTopicExpression(String name) {
        TopicExpressionType answer = new TopicExpressionType();
        answer.getContent().add(name);
        answer.setDialect(SIMPLE_DIALECT);
        return answer;
    }

    public ActiveMQTopic toActiveMQTopic(List<TopicExpressionType> topics) throws InvalidTopicException {
        if (topics == null || topics.isEmpty()) {
            return null;
        }
        int size = topics.size();
        ActiveMQDestination[] childrenDestinations = new ActiveMQTopic[size];
        for (int i = 0; i < size; ++i) {
            childrenDestinations[i] = this.toActiveMQTopic(topics.get(i));
        }
        ActiveMQTopic topic = new ActiveMQTopic();
        topic.setCompositeDestinations(childrenDestinations);
        return topic;
    }

    public ActiveMQTopic toActiveMQTopic(TopicExpressionType topic) throws InvalidTopicException {
        String dialect = topic.getDialect();
        if (dialect == null || SIMPLE_DIALECT.equals(dialect)) {
            Iterator<Object> iter = topic.getContent().iterator();
            while (iter.hasNext()) {
                ActiveMQTopic answer = this.createActiveMQTopicFromContent(iter.next());
                if (answer == null) continue;
                return answer;
            }
            throw new InvalidTopicException("No topic name available topic: " + topic);
        }
        throw new InvalidTopicException("Topic dialect: " + dialect + " not supported");
    }

    protected ActiveMQTopic createActiveMQTopicFromContent(Object contentItem) {
        if (contentItem instanceof String) {
            return new ActiveMQTopic(((String)contentItem).trim());
        }
        if (contentItem instanceof QName) {
            return this.createActiveMQTopicFromQName((QName)contentItem);
        }
        return null;
    }

    protected ActiveMQTopic createActiveMQTopicFromQName(QName qName) {
        return new ActiveMQTopic(qName.toString());
    }
}

