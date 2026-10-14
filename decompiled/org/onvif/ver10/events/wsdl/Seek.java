/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"utcTime", "reverse", "any"})
@XmlRootElement(name="Seek")
public class Seek {
    @XmlElement(name="UtcTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar utcTime;
    @XmlElement(name="Reverse")
    protected Boolean reverse;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public XMLGregorianCalendar getUtcTime() {
        return this.utcTime;
    }

    public void setUtcTime(XMLGregorianCalendar value) {
        this.utcTime = value;
    }

    public Boolean getReverse() {
        return this.reverse;
    }

    public void setReverse(Boolean value) {
        this.reverse = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

