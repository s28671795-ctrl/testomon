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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourOperation;
import org.onvif.ver10.schema.PTZPresetTourSupportedExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourSupported", propOrder={"maximumNumberOfPresetTours", "ptzPresetTourOperation", "extension"})
public class PTZPresetTourSupported {
    @XmlElement(name="MaximumNumberOfPresetTours")
    protected int maximumNumberOfPresetTours;
    @XmlElement(name="PTZPresetTourOperation")
    @XmlSchemaType(name="string")
    protected List<PTZPresetTourOperation> ptzPresetTourOperation;
    @XmlElement(name="Extension")
    protected PTZPresetTourSupportedExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public int getMaximumNumberOfPresetTours() {
        return this.maximumNumberOfPresetTours;
    }

    public void setMaximumNumberOfPresetTours(int value) {
        this.maximumNumberOfPresetTours = value;
    }

    public List<PTZPresetTourOperation> getPTZPresetTourOperation() {
        if (this.ptzPresetTourOperation == null) {
            this.ptzPresetTourOperation = new ArrayList<PTZPresetTourOperation>();
        }
        return this.ptzPresetTourOperation;
    }

    public PTZPresetTourSupportedExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PTZPresetTourSupportedExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

