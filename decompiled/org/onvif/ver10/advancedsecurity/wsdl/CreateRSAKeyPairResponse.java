/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"keyID", "estimatedCreationTime"})
@XmlRootElement(name="CreateRSAKeyPairResponse")
public class CreateRSAKeyPairResponse {
    @XmlElement(name="KeyID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String keyID;
    @XmlElement(name="EstimatedCreationTime", required=true)
    protected Duration estimatedCreationTime;

    public String getKeyID() {
        return this.keyID;
    }

    public void setKeyID(String value) {
        this.keyID = value;
    }

    public Duration getEstimatedCreationTime() {
        return this.estimatedCreationTime;
    }

    public void setEstimatedCreationTime(Duration value) {
        this.estimatedCreationTime = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

