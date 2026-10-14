/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MaximumNumberOfOSDs")
public class MaximumNumberOfOSDs {
    @XmlAttribute(name="Total", required=true)
    protected int total;
    @XmlAttribute(name="Image")
    protected Integer image;
    @XmlAttribute(name="PlainText")
    protected Integer plainText;
    @XmlAttribute(name="Date")
    protected Integer date;
    @XmlAttribute(name="Time")
    protected Integer time;
    @XmlAttribute(name="DateAndTime")
    protected Integer dateAndTime;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int value) {
        this.total = value;
    }

    public Integer getImage() {
        return this.image;
    }

    public void setImage(Integer value) {
        this.image = value;
    }

    public Integer getPlainText() {
        return this.plainText;
    }

    public void setPlainText(Integer value) {
        this.plainText = value;
    }

    public Integer getDate() {
        return this.date;
    }

    public void setDate(Integer value) {
        this.date = value;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer value) {
        this.time = value;
    }

    public Integer getDateAndTime() {
        return this.dateAndTime;
    }

    public void setDateAndTime(Integer value) {
        this.dateAndTime = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

