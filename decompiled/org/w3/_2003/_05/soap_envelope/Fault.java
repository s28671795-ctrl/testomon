/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.w3._2003._05.soap_envelope.Detail;
import org.w3._2003._05.soap_envelope.Faultcode;
import org.w3._2003._05.soap_envelope.Faultreason;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Fault", propOrder={"code", "reason", "node", "role", "detail"})
public class Fault {
    @XmlElement(name="Code", required=true)
    protected Faultcode code;
    @XmlElement(name="Reason", required=true)
    protected Faultreason reason;
    @XmlElement(name="Node")
    @XmlSchemaType(name="anyURI")
    protected String node;
    @XmlElement(name="Role")
    @XmlSchemaType(name="anyURI")
    protected String role;
    @XmlElement(name="Detail")
    protected Detail detail;

    public Faultcode getCode() {
        return this.code;
    }

    public void setCode(Faultcode value) {
        this.code = value;
    }

    public Faultreason getReason() {
        return this.reason;
    }

    public void setReason(Faultreason value) {
        this.reason = value;
    }

    public String getNode() {
        return this.node;
    }

    public void setNode(String value) {
        this.node = value;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String value) {
        this.role = value;
    }

    public Detail getDetail() {
        return this.detail;
    }

    public void setDetail(Detail value) {
        this.detail = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

