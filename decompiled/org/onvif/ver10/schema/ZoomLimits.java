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
import org.onvif.ver10.schema.Space1DDescription;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ZoomLimits", propOrder={"range"})
public class ZoomLimits {
    @XmlElement(name="Range", required=true)
    protected Space1DDescription range;

    public Space1DDescription getRange() {
        return this.range;
    }

    public void setRange(Space1DDescription value) {
        this.range = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

