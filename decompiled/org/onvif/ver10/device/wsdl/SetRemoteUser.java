/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RemoteUser;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"remoteUser"})
@XmlRootElement(name="SetRemoteUser")
public class SetRemoteUser {
    @XmlElement(name="RemoteUser")
    protected RemoteUser remoteUser;

    public RemoteUser getRemoteUser() {
        return this.remoteUser;
    }

    public void setRemoteUser(RemoteUser value) {
        this.remoteUser = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

