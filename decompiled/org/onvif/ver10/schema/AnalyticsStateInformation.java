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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AnalyticsState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsStateInformation", propOrder={"analyticsEngineControlToken", "state", "any"})
public class AnalyticsStateInformation {
    @XmlElement(name="AnalyticsEngineControlToken", required=true)
    protected String analyticsEngineControlToken;
    @XmlElement(name="State", required=true)
    protected AnalyticsState state;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getAnalyticsEngineControlToken() {
        return this.analyticsEngineControlToken;
    }

    public void setAnalyticsEngineControlToken(String value) {
        this.analyticsEngineControlToken = value;
    }

    public AnalyticsState getState() {
        return this.state;
    }

    public void setState(AnalyticsState value) {
        this.state = value;
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

