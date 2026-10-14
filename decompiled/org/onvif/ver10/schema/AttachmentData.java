/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.w3._2004._08.xop.include.Include;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AttachmentData", propOrder={"include"})
public class AttachmentData {
    @XmlElement(name="Include", namespace="http://www.w3.org/2004/08/xop/include", required=true)
    protected Include include;
    @XmlAttribute(name="contentType", namespace="http://www.w3.org/2005/05/xmlmime")
    protected String contentType;

    public Include getInclude() {
        return this.include;
    }

    public void setInclude(Include value) {
        this.include = value;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String value) {
        this.contentType = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

