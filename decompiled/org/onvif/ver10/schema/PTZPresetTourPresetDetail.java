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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourTypeExtension;
import org.onvif.ver10.schema.PTZVector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPresetTourPresetDetail", propOrder={"presetToken", "home", "ptzPosition", "typeExtension", "any"})
public class PTZPresetTourPresetDetail {
    @XmlElement(name="PresetToken")
    protected String presetToken;
    @XmlElement(name="Home")
    protected Boolean home;
    @XmlElement(name="PTZPosition")
    protected PTZVector ptzPosition;
    @XmlElement(name="TypeExtension")
    protected PTZPresetTourTypeExtension typeExtension;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getPresetToken() {
        return this.presetToken;
    }

    public void setPresetToken(String value) {
        this.presetToken = value;
    }

    public Boolean getHome() {
        return this.home;
    }

    public void setHome(Boolean value) {
        this.home = value;
    }

    public PTZVector getPTZPosition() {
        return this.ptzPosition;
    }

    public void setPTZPosition(PTZVector value) {
        this.ptzPosition = value;
    }

    public PTZPresetTourTypeExtension getTypeExtension() {
        return this.typeExtension;
    }

    public void setTypeExtension(PTZPresetTourTypeExtension value) {
        this.typeExtension = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

