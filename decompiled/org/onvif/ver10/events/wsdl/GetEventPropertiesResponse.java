/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.t_1.TopicSetType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"topicNamespaceLocation", "fixedTopicSet", "topicSet", "topicExpressionDialect", "messageContentFilterDialect", "producerPropertiesFilterDialect", "messageContentSchemaLocation", "any"})
@XmlRootElement(name="GetEventPropertiesResponse")
public class GetEventPropertiesResponse {
    @XmlElement(name="TopicNamespaceLocation", required=true)
    @XmlSchemaType(name="anyURI")
    protected List<String> topicNamespaceLocation;
    @XmlElement(name="FixedTopicSet", namespace="http://docs.oasis-open.org/wsn/b-2", defaultValue="true")
    protected boolean fixedTopicSet;
    @XmlElement(name="TopicSet", namespace="http://docs.oasis-open.org/wsn/t-1", required=true)
    protected TopicSetType topicSet;
    @XmlElement(name="TopicExpressionDialect", namespace="http://docs.oasis-open.org/wsn/b-2", required=true)
    @XmlSchemaType(name="anyURI")
    protected List<String> topicExpressionDialect;
    @XmlElement(name="MessageContentFilterDialect", required=true)
    @XmlSchemaType(name="anyURI")
    protected List<String> messageContentFilterDialect;
    @XmlElement(name="ProducerPropertiesFilterDialect")
    @XmlSchemaType(name="anyURI")
    protected List<String> producerPropertiesFilterDialect;
    @XmlElement(name="MessageContentSchemaLocation", required=true)
    @XmlSchemaType(name="anyURI")
    protected List<String> messageContentSchemaLocation;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public List<String> getTopicNamespaceLocation() {
        if (this.topicNamespaceLocation == null) {
            this.topicNamespaceLocation = new ArrayList<String>();
        }
        return this.topicNamespaceLocation;
    }

    public boolean getFixedTopicSet() {
        return this.fixedTopicSet;
    }

    public void setFixedTopicSet(boolean value) {
        this.fixedTopicSet = value;
    }

    public TopicSetType getTopicSet() {
        return this.topicSet;
    }

    public void setTopicSet(TopicSetType value) {
        this.topicSet = value;
    }

    public List<String> getTopicExpressionDialect() {
        if (this.topicExpressionDialect == null) {
            this.topicExpressionDialect = new ArrayList<String>();
        }
        return this.topicExpressionDialect;
    }

    public List<String> getMessageContentFilterDialect() {
        if (this.messageContentFilterDialect == null) {
            this.messageContentFilterDialect = new ArrayList<String>();
        }
        return this.messageContentFilterDialect;
    }

    public List<String> getProducerPropertiesFilterDialect() {
        if (this.producerPropertiesFilterDialect == null) {
            this.producerPropertiesFilterDialect = new ArrayList<String>();
        }
        return this.producerPropertiesFilterDialect;
    }

    public List<String> getMessageContentSchemaLocation() {
        if (this.messageContentSchemaLocation == null) {
            this.messageContentSchemaLocation = new ArrayList<String>();
        }
        return this.messageContentSchemaLocation;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

