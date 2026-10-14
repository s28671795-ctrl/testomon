/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Profile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profile"})
@XmlRootElement(name="GetProfileResponse")
public class GetProfileResponse {
    @XmlElement(name="Profile", required=true)
    protected Profile profile;

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile value) {
        this.profile = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

