/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.FilterType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"filter", "initialTerminationTime", "subscriptionPolicy", "any"})
@XmlRootElement(name="CreatePullPointSubscription")
public class CreatePullPointSubscription {
    @XmlElement(name="Filter")
    protected FilterType filter;
    @XmlElementRef(name="InitialTerminationTime", namespace="http://www.onvif.org/ver10/events/wsdl", type=JAXBElement.class, required=false)
    protected JAXBElement<String> initialTerminationTime;
    @XmlElement(name="SubscriptionPolicy")
    protected SubscriptionPolicy subscriptionPolicy;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

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

