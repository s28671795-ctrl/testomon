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
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.StreamType;
import org.onvif.ver10.schema.Transport;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StreamSetup", propOrder={"stream", "transport", "any"})
public class StreamSetup {
    @XmlElement(name="Stream", required=true)
    @XmlSchemaType(name="string")
    protected StreamType stream;
    @XmlElement(name="Transport", required=true)
    protected Transport transport;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public StreamType getStream() {
        return this.stream;
    }

    public void setStream(StreamType value) {
        this.stream = value;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setTransport(Transport value) {
        this.transport = value;
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

