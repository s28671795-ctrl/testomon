/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accessrules.wsdl.AccessProfile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"accessProfile"})
@XmlRootElement(name="ModifyAccessProfile")
public class ModifyAccessProfile {
    @XmlElement(name="AccessProfile", required=true)
    protected AccessProfile accessProfile;

    public AccessProfile getAccessProfile() {
        return this.accessProfile;
    }

    public void setAccessProfile(AccessProfile value) {
        this.accessProfile = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

