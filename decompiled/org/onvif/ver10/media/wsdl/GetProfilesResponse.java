/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Profile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profiles"})
@XmlRootElement(name="GetProfilesResponse")
public class GetProfilesResponse {
    @XmlElement(name="Profiles")
    protected List<Profile> profiles;

    public List<Profile> getProfiles() {
        if (this.profiles == null) {
            this.profiles = new ArrayList<Profile>();
        }
        return this.profiles;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

