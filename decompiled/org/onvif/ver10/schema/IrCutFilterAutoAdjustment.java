/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustmentExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IrCutFilterAutoAdjustment", propOrder={"boundaryType", "boundaryOffset", "responseTime", "extension"})
public class IrCutFilterAutoAdjustment {
    @XmlElement(name="BoundaryType", required=true)
    protected String boundaryType;
    @XmlElement(name="BoundaryOffset")
    protected Float boundaryOffset;
    @XmlElement(name="ResponseTime")
    protected Duration responseTime;
    @XmlElement(name="Extension")
    protected IrCutFilterAutoAdjustmentExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getBoundaryType() {
        return this.boundaryType;
    }

    public void setBoundaryType(String value) {
        this.boundaryType = value;
    }

    public Float getBoundaryOffset() {
        return this.boundaryOffset;
    }

    public void setBoundaryOffset(Float value) {
        this.boundaryOffset = value;
    }

    public Duration getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(Duration value) {
        this.responseTime = value;
    }

    public IrCutFilterAutoAdjustmentExtension getExtension() {
        return this.extension;
    }

    public void setExtension(IrCutFilterAutoAdjustmentExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

