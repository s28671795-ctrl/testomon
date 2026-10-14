/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accesscontrol.wsdl.AreaInfo;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nextStartReference", "areaInfo"})
@XmlRootElement(name="GetAreaInfoListResponse")
public class GetAreaInfoListResponse {
    @XmlElement(name="NextStartReference")
    protected String nextStartReference;
    @XmlElement(name="AreaInfo")
    protected List<AreaInfo> areaInfo;

    public String getNextStartReference() {
        return this.nextStartReference;
    }

    public void setNextStartReference(String value) {
        this.nextStartReference = value;
    }

    public List<AreaInfo> getAreaInfo() {
        if (this.areaInfo == null) {
            this.areaInfo = new ArrayList<AreaInfo>();
        }
        return this.areaInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

