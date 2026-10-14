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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AutoFocusMode;
import org.onvif.ver10.schema.FocusConfiguration20Extension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FocusConfiguration20", propOrder={"autoFocusMode", "defaultSpeed", "nearLimit", "farLimit", "extension"})
public class FocusConfiguration20 {
    @XmlElement(name="AutoFocusMode", required=true)
    @XmlSchemaType(name="string")
    protected AutoFocusMode autoFocusMode;
    @XmlElement(name="DefaultSpeed")
    protected Float defaultSpeed;
    @XmlElement(name="NearLimit")
    protected Float nearLimit;
    @XmlElement(name="FarLimit")
    protected Float farLimit;
    @XmlElement(name="Extension")
    protected FocusConfiguration20Extension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AutoFocusMode getAutoFocusMode() {
        return this.autoFocusMode;
    }

    public void setAutoFocusMode(AutoFocusMode value) {
        this.autoFocusMode = value;
    }

    public Float getDefaultSpeed() {
        return this.defaultSpeed;
    }

    public void setDefaultSpeed(Float value) {
        this.defaultSpeed = value;
    }

    public Float getNearLimit() {
        return this.nearLimit;
    }

    public void setNearLimit(Float value) {
        this.nearLimit = value;
    }

    public Float getFarLimit() {
        return this.farLimit;
    }

    public void setFarLimit(Float value) {
        this.farLimit = value;
    }

    public FocusConfiguration20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(FocusConfiguration20Extension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

