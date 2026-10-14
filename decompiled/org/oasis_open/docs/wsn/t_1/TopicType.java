/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.t_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.t_1.ExtensibleDocumented;
import org.oasis_open.docs.wsn.t_1.QueryExpressionType;
import org.oasis_open.docs.wsn.t_1.TopicNamespaceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TopicType", propOrder={"messagePattern", "topic", "any"})
@XmlSeeAlso(value={TopicNamespaceType.Topic.class})
public class TopicType
extends ExtensibleDocumented {
    @XmlElement(name="MessagePattern")
    protected QueryExpressionType messagePattern;
    @XmlElement(name="Topic")
    protected List<TopicType> topic;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="name", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String name;
    @XmlAttribute(name="messageTypes")
    protected List<QName> messageTypes;
    @XmlAttribute(name="final")
    protected Boolean _final;

    public QueryExpressionType getMessagePattern() {
        return this.messagePattern;
    }

    public void setMessagePattern(QueryExpressionType value) {
        this.messagePattern = value;
    }

    public List<TopicType> getTopic() {
        if (this.topic == null) {
            this.topic = new ArrayList<TopicType>();
        }
        return this.topic;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public List<QName> getMessageTypes() {
        if (this.messageTypes == null) {
            this.messageTypes = new ArrayList<QName>();
        }
        return this.messageTypes;
    }

    public boolean getFinal() {
        if (this._final == null) {
            return false;
        }
        return this._final;
    }

    public void setFinal(Boolean value) {
        this._final = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

