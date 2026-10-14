/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class TopicRequestor {
    private TopicSession session;
    private Topic topic;
    private TemporaryTopic temporaryTopic;
    private TopicPublisher publisher;
    private TopicSubscriber subscriber;

    public TopicRequestor(TopicSession session, Topic topic) throws JMSException {
        this.setSession(session);
        this.setTopic(topic);
        this.setTemporaryTopic(session.createTemporaryTopic());
        this.setPublisher(session.createPublisher(topic));
        this.setSubscriber(session.createSubscriber(this.getTemporaryTopic()));
    }

    public Message request(Message message) throws JMSException {
        message.setJMSReplyTo(this.getTemporaryTopic());
        this.getPublisher().publish(message);
        return this.getSubscriber().receive();
    }

    public void close() throws JMSException {
        this.getSession().close();
        this.getTemporaryTopic().delete();
    }

    private void setPublisher(TopicPublisher publisher) {
        this.publisher = publisher;
    }

    private TopicPublisher getPublisher() {
        return this.publisher;
    }

    private void setSession(TopicSession session) {
        this.session = session;
    }

    private TopicSession getSession() {
        return this.session;
    }

    private void setSubscriber(TopicSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    private TopicSubscriber getSubscriber() {
        return this.subscriber;
    }

    private void setTemporaryTopic(TemporaryTopic temporaryTopic) {
        this.temporaryTopic = temporaryTopic;
    }

    private TemporaryTopic getTemporaryTopic() {
        return this.temporaryTopic;
    }

    private void setTopic(Topic topic) {
        this.topic = topic;
    }

    private Topic getTopic() {
        return this.topic;
    }
}

