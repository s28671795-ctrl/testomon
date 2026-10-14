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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoRateControl2", propOrder={"frameRateLimit", "bitrateLimit", "any"})
public class VideoRateControl2 {
    @XmlElement(name="FrameRateLimit")
    protected float frameRateLimit;
    @XmlElement(name="BitrateLimit")
    protected int bitrateLimit;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="ConstantBitRate")
    protected Boolean constantBitRate;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public float getFrameRateLimit() {
        return this.frameRateLimit;
    }

    public void setFrameRateLimit(float value) {
        this.frameRateLimit = value;
    }

    public int getBitrateLimit() {
        return this.bitrateLimit;
    }

    public void setBitrateLimit(int value) {
        this.bitrateLimit = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getConstantBitRate() {
        return this.constantBitRate;
    }

    public void setConstantBitRate(Boolean value) {
        this.constantBitRate = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

