/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"totalNumber", "jpeg", "h264", "mpeg4"})
@XmlRootElement(name="GetGuaranteedNumberOfVideoEncoderInstancesResponse")
public class GetGuaranteedNumberOfVideoEncoderInstancesResponse {
    @XmlElement(name="TotalNumber")
    protected int totalNumber;
    @XmlElement(name="JPEG")
    protected Integer jpeg;
    @XmlElement(name="H264")
    protected Integer h264;
    @XmlElement(name="MPEG4")
    protected Integer mpeg4;

    public int getTotalNumber() {
        return this.totalNumber;
    }

    public void setTotalNumber(int value) {
        this.totalNumber = value;
    }

    public Integer getJPEG() {
        return this.jpeg;
    }

    public void setJPEG(Integer value) {
        this.jpeg = value;
    }

    public Integer getH264() {
        return this.h264;
    }

    public void setH264(Integer value) {
        this.h264 = value;
    }

    public Integer getMPEG4() {
        return this.mpeg4;
    }

    public void setMPEG4(Integer value) {
        this.mpeg4 = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

