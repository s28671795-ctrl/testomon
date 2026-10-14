/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Space2DDescription;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PanTiltLimits", propOrder={"range"})
public class PanTiltLimits {
    @XmlElement(name="Range", required=true)
    protected Space2DDescription range;

    public Space2DDescription getRange() {
        return this.range;
    }

    public void setRange(Space2DDescription value) {
        this.range = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

