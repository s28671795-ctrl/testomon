/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FloatRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Space2DDescription", propOrder={"uri", "xRange", "yRange"})
public class Space2DDescription {
    @XmlElement(name="URI", required=true)
    @XmlSchemaType(name="anyURI")
    protected String uri;
    @XmlElement(name="XRange", required=true)
    protected FloatRange xRange;
    @XmlElement(name="YRange", required=true)
    protected FloatRange yRange;

    public String getURI() {
        return this.uri;
    }

    public void setURI(String value) {
        this.uri = value;
    }

    public FloatRange getXRange() {
        return this.xRange;
    }

    public void setXRange(FloatRange value) {
        this.xRange = value;
    }

    public FloatRange getYRange() {
        return this.yRange;
    }

    public void setYRange(FloatRange value) {
        this.yRange = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

