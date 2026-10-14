/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import java.util.ArrayList;
import java.util.List;
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
@XmlType(name="", propOrder={"recordingTokens", "time"})
@XmlRootElement(name="GetMediaAttributes")
public class GetMediaAttributes {
    @XmlElement(name="RecordingTokens")
    protected List<String> recordingTokens;
    @XmlElement(name="Time", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar time;

    public List<String> getRecordingTokens() {
        if (this.recordingTokens == null) {
            this.recordingTokens = new ArrayList<String>();
        }
        return this.recordingTokens;
    }

    public XMLGregorianCalendar getTime() {
        return this.time;
    }

    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

