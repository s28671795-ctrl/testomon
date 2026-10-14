/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPresetTourOperation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "presetTourToken", "operation"})
@XmlRootElement(name="OperatePresetTour")
public class OperatePresetTour {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="PresetTourToken", required=true)
    protected String presetTourToken;
    @XmlElement(name="Operation", required=true)
    @XmlSchemaType(name="string")
    protected PTZPresetTourOperation operation;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public String getPresetTourToken() {
        return this.presetTourToken;
    }

    public void setPresetTourToken(String value) {
        this.presetTourToken = value;
    }

    public PTZPresetTourOperation getOperation() {
        return this.operation;
    }

    public void setOperation(PTZPresetTourOperation value) {
        this.operation = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

