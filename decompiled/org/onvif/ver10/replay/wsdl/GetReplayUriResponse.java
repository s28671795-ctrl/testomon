/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.replay.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"uri"})
@XmlRootElement(name="GetReplayUriResponse")
public class GetReplayUriResponse {
    @XmlElement(name="Uri", required=true)
    @XmlSchemaType(name="anyURI")
    protected String uri;

    public String getUri() {
        return this.uri;
    }

    public void setUri(String value) {
        this.uri = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

