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
import org.onvif.ver10.schema.ImagingOptions20Extension3;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustmentOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingOptions20Extension2", propOrder={"irCutFilterAutoAdjustment", "extension"})
public class ImagingOptions20Extension2 {
    @XmlElement(name="IrCutFilterAutoAdjustment")
    protected IrCutFilterAutoAdjustmentOptions irCutFilterAutoAdjustment;
    @XmlElement(name="Extension")
    protected ImagingOptions20Extension3 extension;

    public IrCutFilterAutoAdjustmentOptions getIrCutFilterAutoAdjustment() {
        return this.irCutFilterAutoAdjustment;
    }

    public void setIrCutFilterAutoAdjustment(IrCutFilterAutoAdjustmentOptions value) {
        this.irCutFilterAutoAdjustment = value;
    }

    public ImagingOptions20Extension3 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingOptions20Extension3 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

