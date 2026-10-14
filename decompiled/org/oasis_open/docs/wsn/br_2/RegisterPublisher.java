/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.br_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"publisherReference", "topic", "demand", "initialTerminationTime", "any"})
@XmlRootElement(name="RegisterPublisher")
public class RegisterPublisher {
    @XmlElement(name="PublisherReference")
    protected W3CEndpointReference publisherReference;
    @XmlElement(name="Topic")
    protected List<TopicExpressionType> topic;
    @XmlElement(name="Demand", defaultValue="false")
    protected Boolean demand;
    @XmlElement(name="InitialTerminationTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar initialTerminationTime;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public W3CEndpointReference getPublisherReference() {
        return this.publisherReference;
    }

    public void setPublisherReference(W3CEndpointReference value) {
        this.publisherReference = value;
    }

    public List<TopicExpressionType> getTopic() {
        if (this.topic == null) {
            this.topic = new ArrayList<TopicExpressionType>();
        }
        return this.topic;
    }

    public void setDemand(Boolean value) {
        this.demand = value;
    }

    public XMLGregorianCalendar getInitialTerminationTime() {
        return this.initialTerminationTime;
    }

    public void setInitialTerminationTime(XMLGregorianCalendar value) {
        this.initialTerminationTime = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean isDemand() {
        if (null == this.demand) {
            return Boolean.FALSE;
        }
        return this.demand;
    }
}

