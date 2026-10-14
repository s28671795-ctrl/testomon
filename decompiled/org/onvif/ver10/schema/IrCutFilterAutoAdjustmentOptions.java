/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DurationRange;
import org.onvif.ver10.schema.IrCutFilterAutoAdjustmentOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IrCutFilterAutoAdjustmentOptions", propOrder={"boundaryType", "boundaryOffset", "responseTimeRange", "extension"})
public class IrCutFilterAutoAdjustmentOptions {
    @XmlElement(name="BoundaryType", required=true)
    protected List<String> boundaryType;
    @XmlElement(name="BoundaryOffset")
    protected Boolean boundaryOffset;
    @XmlElement(name="ResponseTimeRange")
    protected DurationRange responseTimeRange;
    @XmlElement(name="Extension")
    protected IrCutFilterAutoAdjustmentOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getBoundaryType() {
        if (this.boundaryType == null) {
            this.boundaryType = new ArrayList<String>();
        }
        return this.boundaryType;
    }

    public Boolean getBoundaryOffset() {
        return this.boundaryOffset;
    }

    public void setBoundaryOffset(Boolean value) {
        this.boundaryOffset = value;
    }

    public DurationRange getResponseTimeRange() {
        return this.responseTimeRange;
    }

    public void setResponseTimeRange(DurationRange value) {
        this.responseTimeRange = value;
    }

    public IrCutFilterAutoAdjustmentOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(IrCutFilterAutoAdjustmentOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

