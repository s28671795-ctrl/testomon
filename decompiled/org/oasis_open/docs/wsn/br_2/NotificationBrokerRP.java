/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.br_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.t_1.TopicSetType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"topicExpression", "fixedTopicSet", "topicExpressionDialect", "topicSet", "requiresRegistration"})
@XmlRootElement(name="NotificationBrokerRP")
public class NotificationBrokerRP {
    @XmlElement(name="TopicExpression", namespace="http://docs.oasis-open.org/wsn/b-2")
    protected List<TopicExpressionType> topicExpression;
    @XmlElement(name="FixedTopicSet", namespace="http://docs.oasis-open.org/wsn/b-2", defaultValue="true")
    protected Boolean fixedTopicSet;
    @XmlElement(name="TopicExpressionDialect", namespace="http://docs.oasis-open.org/wsn/b-2")
    @XmlSchemaType(name="anyURI")
    protected List<String> topicExpressionDialect;
    @XmlElement(name="TopicSet", namespace="http://docs.oasis-open.org/wsn/t-1")
    protected TopicSetType topicSet;
    @XmlElement(name="RequiresRegistration")
    protected boolean requiresRegistration;

    public List<TopicExpressionType> getTopicExpression() {
        if (this.topicExpression == null) {
            this.topicExpression = new ArrayList<TopicExpressionType>();
        }
        return this.topicExpression;
    }

    public void setFixedTopicSet(Boolean value) {
        this.fixedTopicSet = value;
    }

    public List<String> getTopicExpressionDialect() {
        if (this.topicExpressionDialect == null) {
            this.topicExpressionDialect = new ArrayList<String>();
        }
        return this.topicExpressionDialect;
    }

    public TopicSetType getTopicSet() {
        return this.topicSet;
    }

    public void setTopicSet(TopicSetType value) {
        this.topicSet = value;
    }

    public boolean isRequiresRegistration() {
        return this.requiresRegistration;
    }

    public void setRequiresRegistration(boolean value) {
        this.requiresRegistration = value;
    }

    public Boolean isFixedTopicSet() {
        if (null == this.fixedTopicSet) {
            return Boolean.TRUE;
        }
        return this.fixedTopicSet;
    }
}

