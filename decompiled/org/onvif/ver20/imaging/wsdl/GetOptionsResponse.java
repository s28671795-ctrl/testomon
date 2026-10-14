/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ImagingOptions20;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"imagingOptions"})
@XmlRootElement(name="GetOptionsResponse")
public class GetOptionsResponse {
    @XmlElement(name="ImagingOptions", required=true)
    protected ImagingOptions20 imagingOptions;

    public ImagingOptions20 getImagingOptions() {
        return this.imagingOptions;
    }

    public void setImagingOptions(ImagingOptions20 value) {
        this.imagingOptions = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

