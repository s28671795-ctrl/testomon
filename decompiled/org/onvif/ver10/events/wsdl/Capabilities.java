/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"any"})
public class Capabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="WSSubscriptionPolicySupport")
    protected Boolean wsSubscriptionPolicySupport;
    @XmlAttribute(name="WSPullPointSupport")
    protected Boolean wsPullPointSupport;
    @XmlAttribute(name="WSPausableSubscriptionManagerInterfaceSupport")
    protected Boolean wsPausableSubscriptionManagerInterfaceSupport;
    @XmlAttribute(name="MaxNotificationProducers")
    protected Integer maxNotificationProducers;
    @XmlAttribute(name="MaxPullPoints")
    protected Integer maxPullPoints;
    @XmlAttribute(name="PersistentNotificationStorage")
    protected Boolean persistentNotificationStorage;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getWSSubscriptionPolicySupport() {
        return this.wsSubscriptionPolicySupport;
    }

    public void setWSSubscriptionPolicySupport(Boolean value) {
        this.wsSubscriptionPolicySupport = value;
    }

    public Boolean getWSPullPointSupport() {
        return this.wsPullPointSupport;
    }

    public void setWSPullPointSupport(Boolean value) {
        this.wsPullPointSupport = value;
    }

    public Boolean getWSPausableSubscriptionManagerInterfaceSupport() {
        return this.wsPausableSubscriptionManagerInterfaceSupport;
    }

    public void setWSPausableSubscriptionManagerInterfaceSupport(Boolean value) {
        this.wsPausableSubscriptionManagerInterfaceSupport = value;
    }

    public Integer getMaxNotificationProducers() {
        return this.maxNotificationProducers;
    }

    public void setMaxNotificationProducers(Integer value) {
        this.maxNotificationProducers = value;
    }

    public Integer getMaxPullPoints() {
        return this.maxPullPoints;
    }

    public void setMaxPullPoints(Integer value) {
        this.maxPullPoints = value;
    }

    public Boolean getPersistentNotificationStorage() {
        return this.persistentNotificationStorage;
    }

    public void setPersistentNotificationStorage(Boolean value) {
        this.persistentNotificationStorage = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

