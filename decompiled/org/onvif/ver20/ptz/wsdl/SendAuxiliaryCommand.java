/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"profileToken", "auxiliaryData"})
@XmlRootElement(name="SendAuxiliaryCommand")
public class SendAuxiliaryCommand {
    @XmlElement(name="ProfileToken", required=true)
    protected String profileToken;
    @XmlElement(name="AuxiliaryData", required=true)
    protected String auxiliaryData;

    public String getProfileToken() {
        return this.profileToken;
    }

    public void setProfileToken(String value) {
        this.profileToken = value;
    }

    public String getAuxiliaryData() {
        return this.auxiliaryData;
    }

    public void setAuxiliaryData(String value) {
        this.auxiliaryData = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

