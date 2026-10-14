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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoResolution", propOrder={"width", "height"})
public class VideoResolution {
    @XmlElement(name="Width")
    protected int width;
    @XmlElement(name="Height")
    protected int height;

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int value) {
        this.width = value;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

