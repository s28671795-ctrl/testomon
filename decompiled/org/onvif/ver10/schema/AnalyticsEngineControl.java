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
import org.onvif.ver10.schema.Config;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.ModeOfOperation;
import org.onvif.ver10.schema.MulticastConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsEngineControl", propOrder={"engineToken", "engineConfigToken", "inputToken", "receiverToken", "multicast", "subscription", "mode", "any"})
public class AnalyticsEngineControl
extends ConfigurationEntity {
    @XmlElement(name="EngineToken", required=true)
    protected String engineToken;
    @XmlElement(name="EngineConfigToken", required=true)
    protected String engineConfigToken;
    @XmlElement(name="InputToken", required=true)
    protected List<String> inputToken;
    @XmlElement(name="ReceiverToken", required=true)
    protected List<String> receiverToken;
    @XmlElement(name="Multicast")
    protected MulticastConfiguration multicast;
    @XmlElement(name="Subscription", required=true)
    protected Config subscription;
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected ModeOfOperation mode;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getEngineToken() {
        return this.engineToken;
    }

    public void setEngineToken(String value) {
        this.engineToken = value;
    }

    public String getEngineConfigToken() {
        return this.engineConfigToken;
    }

    public void setEngineConfigToken(String value) {
        this.engineConfigToken = value;
    }

    public List<String> getInputToken() {
        if (this.inputToken == null) {
            this.inputToken = new ArrayList<String>();
        }
        return this.inputToken;
    }

    public List<String> getReceiverToken() {
        if (this.receiverToken == null) {
            this.receiverToken = new ArrayList<String>();
        }
        return this.receiverToken;
    }

    public MulticastConfiguration getMulticast() {
        return this.multicast;
    }

    public void setMulticast(MulticastConfiguration value) {
        this.multicast = value;
    }

    public Config getSubscription() {
        return this.subscription;
    }

    public void setSubscription(Config value) {
        this.subscription = value;
    }

    public ModeOfOperation getMode() {
        return this.mode;
    }

    public void setMode(ModeOfOperation value) {
        this.mode = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

