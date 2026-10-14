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
import org.onvif.ver10.schema.StreamSetup;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"streamSetup", "profileToken"})
@XmlRootElement(name="GetStreamUri")
public class GetStreamUri {
    @XmlElement(name="StreamSetup", required=true)
    protected StreamSetup streamSetup;
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;

    public StreamSetup getStreamSetup() {
        return this.streamSetup;
    }

    public void setStreamSetup(StreamSetup value) {
        this.streamSetup = value;
    }

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

