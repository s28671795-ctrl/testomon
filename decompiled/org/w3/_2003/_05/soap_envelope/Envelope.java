/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.w3._2003._05.soap_envelope.Body;
import org.w3._2003._05.soap_envelope.Header;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Envelope", propOrder={"header", "body"})
public class Envelope {
    @XmlElement(name="Header")
    protected Header header;
    @XmlElement(name="Body", required=true)
    protected Body body;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header value) {
        this.header = value;
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body value) {
        this.body = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

