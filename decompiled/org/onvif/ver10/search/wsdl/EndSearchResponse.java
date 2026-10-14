/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"endpoint"})
@XmlRootElement(name="EndSearchResponse")
public class EndSearchResponse {
    @XmlElement(name="Endpoint", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar endpoint;

    public XMLGregorianCalendar getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(XMLGregorianCalendar value) {
        this.endpoint = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

