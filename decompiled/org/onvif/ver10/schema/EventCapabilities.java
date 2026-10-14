/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EventCapabilities", propOrder={"xAddr", "wsSubscriptionPolicySupport", "wsPullPointSupport", "wsPausableSubscriptionManagerInterfaceSupport", "any"})
public class EventCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="WSSubscriptionPolicySupport")
    protected boolean wsSubscriptionPolicySupport;
    @XmlElement(name="WSPullPointSupport")
    protected boolean wsPullPointSupport;
    @XmlElement(name="WSPausableSubscriptionManagerInterfaceSupport")
    protected boolean wsPausableSubscriptionManagerInterfaceSupport;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public boolean getWSSubscriptionPolicySupport() {
        return this.wsSubscriptionPolicySupport;
    }

    public void setWSSubscriptionPolicySupport(boolean value) {
        this.wsSubscriptionPolicySupport = value;
    }

    public boolean getWSPullPointSupport() {
        return this.wsPullPointSupport;
    }

    public void setWSPullPointSupport(boolean value) {
        this.wsPullPointSupport = value;
    }

    public boolean getWSPausableSubscriptionManagerInterfaceSupport() {
        return this.wsPausableSubscriptionManagerInterfaceSupport;
    }

    public void setWSPausableSubscriptionManagerInterfaceSupport(boolean value) {
        this.wsPausableSubscriptionManagerInterfaceSupport = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

