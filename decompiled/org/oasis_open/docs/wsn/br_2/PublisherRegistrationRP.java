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
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"publisherReference", "topic", "demand", "creationTime"})
@XmlRootElement(name="PublisherRegistrationRP")
public class PublisherRegistrationRP {
    @XmlElement(name="PublisherReference")
    protected W3CEndpointReference publisherReference;
    @XmlElement(name="Topic")
    protected List<TopicExpressionType> topic;
    @XmlElement(name="Demand")
    protected boolean demand;
    @XmlElement(name="CreationTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar creationTime;

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

    public boolean isDemand() {
        return this.demand;
    }

    public void setDemand(boolean value) {
        this.demand = value;
    }

    public XMLGregorianCalendar getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(XMLGregorianCalendar value) {
        this.creationTime = value;
    }
}

