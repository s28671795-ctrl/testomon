/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"limit", "startReference"})
@XmlRootElement(name="GetAccessProfileList")
public class GetAccessProfileList {
    @XmlElement(name="Limit")
    protected Integer limit;
    @XmlElement(name="StartReference")
    protected String startReference;

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer value) {
        this.limit = value;
    }

    public String getStartReference() {
        return this.startReference;
    }

    public void setStartReference(String value) {
        this.startReference = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

