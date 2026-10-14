/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.FilterType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"consumerReference", "filter", "initialTerminationTime", "subscriptionPolicy", "any"})
@XmlRootElement(name="Subscribe")
public class Subscribe {
    @XmlElement(name="ConsumerReference", required=true)
    protected W3CEndpointReference consumerReference;
    @XmlElement(name="Filter")
    protected FilterType filter;
    @XmlElementRef(name="InitialTerminationTime", namespace="http://docs.oasis-open.org/wsn/b-2", type=JAXBElement.class, required=false)
    protected JAXBElement<String> initialTerminationTime;
    @XmlElement(name="SubscriptionPolicy")
    protected SubscriptionPolicy subscriptionPolicy;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

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

    public JAXBElement<String> getInitialTerminationTime() {
        return this.initialTerminationTime;
    }

    public void setInitialTerminationTime(JAXBElement<String> value) {
        this.initialTerminationTime = value;
    }

    public SubscriptionPolicy getSubscriptionPolicy() {
        return this.subscriptionPolicy;
    }

    public void setSubscriptionPolicy(SubscriptionPolicy value) {
        this.subscriptionPolicy = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class SubscriptionPolicy {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

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
}

