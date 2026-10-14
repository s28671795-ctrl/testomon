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
import org.onvif.ver10.schema.MediaCapabilitiesExtension;
import org.onvif.ver10.schema.RealTimeStreamingCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MediaCapabilities", propOrder={"xAddr", "streamingCapabilities", "any", "extension"})
public class MediaCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="StreamingCapabilities", required=true)
    protected RealTimeStreamingCapabilities streamingCapabilities;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Extension")
    protected MediaCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public RealTimeStreamingCapabilities getStreamingCapabilities() {
        return this.streamingCapabilities;
    }

    public void setStreamingCapabilities(RealTimeStreamingCapabilities value) {
        this.streamingCapabilities = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public MediaCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(MediaCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

