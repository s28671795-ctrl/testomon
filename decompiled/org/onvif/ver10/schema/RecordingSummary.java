/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingSummary", propOrder={"dataFrom", "dataUntil", "numberRecordings", "any"})
public class RecordingSummary {
    @XmlElement(name="DataFrom", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar dataFrom;
    @XmlElement(name="DataUntil", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar dataUntil;
    @XmlElement(name="NumberRecordings")
    protected int numberRecordings;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public XMLGregorianCalendar getDataFrom() {
        return this.dataFrom;
    }

    public void setDataFrom(XMLGregorianCalendar value) {
        this.dataFrom = value;
    }

    public XMLGregorianCalendar getDataUntil() {
        return this.dataUntil;
    }

    public void setDataUntil(XMLGregorianCalendar value) {
        this.dataUntil = value;
    }

    public int getNumberRecordings() {
        return this.numberRecordings;
    }

    public void setNumberRecordings(int value) {
        this.numberRecordings = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

