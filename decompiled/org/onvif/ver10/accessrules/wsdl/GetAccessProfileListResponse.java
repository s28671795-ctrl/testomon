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
import org.onvif.ver10.accessrules.wsdl.AccessProfile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nextStartReference", "accessProfile"})
@XmlRootElement(name="GetAccessProfileListResponse")
public class GetAccessProfileListResponse {
    @XmlElement(name="NextStartReference")
    protected String nextStartReference;
    @XmlElement(name="AccessProfile")
    protected List<AccessProfile> accessProfile;

    public String getNextStartReference() {
        return this.nextStartReference;
    }

    public void setNextStartReference(String value) {
        this.nextStartReference = value;
    }

    public List<AccessProfile> getAccessProfile() {
        if (this.accessProfile == null) {
            this.accessProfile = new ArrayList<AccessProfile>();
        }
        return this.accessProfile;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

