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
@XmlType(name="OnvifVersion", propOrder={"major", "minor"})
public class OnvifVersion {
    @XmlElement(name="Major")
    protected int major;
    @XmlElement(name="Minor")
    protected int minor;

    public int getMajor() {
        return this.major;
    }

    public void setMajor(int value) {
        this.major = value;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int value) {
        this.minor = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

