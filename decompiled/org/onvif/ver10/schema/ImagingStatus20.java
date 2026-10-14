/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FocusStatus20;
import org.onvif.ver10.schema.ImagingStatus20Extension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImagingStatus20", propOrder={"focusStatus20", "extension"})
public class ImagingStatus20 {
    @XmlElement(name="FocusStatus20")
    protected FocusStatus20 focusStatus20;
    @XmlElement(name="Extension")
    protected ImagingStatus20Extension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public FocusStatus20 getFocusStatus20() {
        return this.focusStatus20;
    }

    public void setFocusStatus20(FocusStatus20 value) {
        this.focusStatus20 = value;
    }

    public ImagingStatus20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(ImagingStatus20Extension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

