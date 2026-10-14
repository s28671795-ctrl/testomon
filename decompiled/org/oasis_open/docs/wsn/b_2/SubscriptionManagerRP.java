/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.SubscriptionPolicyType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"consumerReference", "filter", "subscriptionPolicy", "creationTime"})
@XmlRootElement(name="SubscriptionManagerRP")
public class SubscriptionManagerRP {
    @XmlElement(name="ConsumerReference", required=true)
    protected W3CEndpointReference consumerReference;
    @XmlElement(name="Filter")
    protected FilterType filter;
    @XmlElement(name="SubscriptionPolicy")
    protected SubscriptionPolicyType subscriptionPolicy;
    @XmlElement(name="CreationTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar creationTime;

    public W3CEndpointReference getConsumerReference() {
        return this.consumerReference;
    }

    public void setConsumerReference(W3CEndpointReference value) {
        this.consumerReference = value;
    }

    public FilterType getFilter() {
        return this.filter;
    }

    public void setFilter(FilterType value) {
        this.filter = value;
    }

    public SubscriptionPolicyType getSubscriptionPolicy() {
        return this.subscriptionPolicy;
    }

    public void setSubscriptionPolicy(SubscriptionPolicyType value) {
        this.subscriptionPolicy = value;
    }

    public XMLGregorianCalendar getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(XMLGregorianCalendar value) {
        this.creationTime = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

