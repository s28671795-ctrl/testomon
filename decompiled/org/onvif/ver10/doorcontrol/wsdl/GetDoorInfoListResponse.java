/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.DoorInfo;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nextStartReference", "doorInfo"})
@XmlRootElement(name="GetDoorInfoListResponse")
public class GetDoorInfoListResponse {
    @XmlElement(name="NextStartReference")
    protected String nextStartReference;
    @XmlElement(name="DoorInfo")
    protected List<DoorInfo> doorInfo;

    public String getNextStartReference() {
        return this.nextStartReference;
    }

    public void setNextStartReference(String value) {
        this.nextStartReference = value;
    }

    public List<DoorInfo> getDoorInfo() {
        if (this.doorInfo == null) {
            this.doorInfo = new ArrayList<DoorInfo>();
        }
        return this.doorInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

