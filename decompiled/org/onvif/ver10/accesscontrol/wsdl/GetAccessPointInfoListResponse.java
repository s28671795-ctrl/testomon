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
import org.onvif.ver10.accesscontrol.wsdl.AccessPointInfo;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nextStartReference", "accessPointInfo"})
@XmlRootElement(name="GetAccessPointInfoListResponse")
public class GetAccessPointInfoListResponse {
    @XmlElement(name="NextStartReference")
    protected String nextStartReference;
    @XmlElement(name="AccessPointInfo")
    protected List<AccessPointInfo> accessPointInfo;

    public String getNextStartReference() {
        return this.nextStartReference;
    }

    public void setNextStartReference(String value) {
        this.nextStartReference = value;
    }

    public List<AccessPointInfo> getAccessPointInfo() {
        if (this.accessPointInfo == null) {
            this.accessPointInfo = new ArrayList<AccessPointInfo>();
        }
        return this.accessPointInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

