/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"topic", "any"})
@XmlRootElement(name="GetCurrentMessage")
public class GetCurrentMessage {
    @XmlElement(name="Topic", required=true)
    protected TopicExpressionType topic;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public TopicExpressionType getTopic() {
        return this.topic;
    }

    public void setTopic(TopicExpressionType value) {
        this.topic = value;
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

