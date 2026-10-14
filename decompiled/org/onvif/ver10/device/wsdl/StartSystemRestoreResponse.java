/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"uploadUri", "expectedDownTime"})
@XmlRootElement(name="StartSystemRestoreResponse")
public class StartSystemRestoreResponse {
    @XmlElement(name="UploadUri", required=true)
    @XmlSchemaType(name="anyURI")
    protected String uploadUri;
    @XmlElement(name="ExpectedDownTime", required=true)
    protected Duration expectedDownTime;

    public String getUploadUri() {
        return this.uploadUri;
    }

    public void setUploadUri(String value) {
        this.uploadUri = value;
    }

    public Duration getExpectedDownTime() {
        return this.expectedDownTime;
    }

    public void setExpectedDownTime(Duration value) {
        this.expectedDownTime = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

