/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accessrules.wsdl.AccessProfileInfo;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nextStartReference", "accessProfileInfo"})
@XmlRootElement(name="GetAccessProfileInfoListResponse")
public class GetAccessProfileInfoListResponse {
    @XmlElement(name="NextStartReference")
    protected String nextStartReference;
    @XmlElement(name="AccessProfileInfo")
    protected List<AccessProfileInfo> accessProfileInfo;

    public String getNextStartReference() {
        return this.nextStartReference;
    }

    public void setNextStartReference(String value) {
        this.nextStartReference = value;
    }

    public List<AccessProfileInfo> getAccessProfileInfo() {
        if (this.accessProfileInfo == null) {
            this.accessProfileInfo = new ArrayList<AccessProfileInfo>();
        }
        return this.accessProfileInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

