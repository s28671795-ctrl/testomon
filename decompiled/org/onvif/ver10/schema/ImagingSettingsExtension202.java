/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ImagingSettingsExtension203;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustment;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingSettingsExtension202", propOrder={"irCutFilterAutoAdjustment", "extension"})
public class ImagingSettingsExtension202 {
    @XmlElement(name="IrCutFilterAutoAdjustment")
    protected List<IrCutFilterAutoAdjustment> irCutFilterAutoAdjustment;
    @XmlElement(name="Extension")
    protected ImagingSettingsExtension203 extension;

    public List<IrCutFilterAutoAdjustment> getIrCutFilterAutoAdjustment() {
        if (this.irCutFilterAutoAdjustment == null) {
            this.irCutFilterAutoAdjustment = new ArrayList<IrCutFilterAutoAdjustment>();
        }
        return this.irCutFilterAutoAdjustment;
    }

    public ImagingSettingsExtension203 getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingSettingsExtension203 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

