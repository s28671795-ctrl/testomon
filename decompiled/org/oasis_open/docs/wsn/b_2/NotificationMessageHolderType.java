/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NotificationMessageHolderType", propOrder={"subscriptionReference", "topic", "producerReference", "message"})
public class NotificationMessageHolderType {
    @XmlElement(name="SubscriptionReference")
    protected W3CEndpointReference subscriptionReference;
    @XmlElement(name="Topic")
    protected TopicExpressionType topic;
    @XmlElement(name="ProducerReference")
    protected W3CEndpointReference producerReference;
    @XmlElement(name="Message", required=true)
    protected Message message;

    public W3CEndpointReference getSubscriptionReference() {
        return this.subscriptionReference;
    }

    public void setSubscriptionReference(W3CEndpointReference value) {
        this.subscriptionReference = value;
    }

    public TopicExpressionType getTopic() {
        return this.topic;
    }

    public void setTopic(TopicExpressionType value) {
        this.topic = value;
    }

    public W3CEndpointReference getProducerReference() {
        return this.producerReference;
    }

    public void setProducerReference(W3CEndpointReference value) {
        this.producerReference = value;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message value) {
        this.message = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class Message {
        @XmlAnyElement(lax=true)
        protected Object any;

        public Object getAny() {
            return this.any;
        }

        public void setAny(Object value) {
            this.any = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

