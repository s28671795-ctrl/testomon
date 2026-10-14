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
import org.onvif.ver10.schema.ReceiverMode;
import org.onvif.ver10.schema.StreamSetup;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ReceiverConfiguration", propOrder={"mode", "mediaUri", "streamSetup", "any"})
public class ReceiverConfiguration {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected ReceiverMode mode;
    @XmlElement(name="MediaUri", required=true)
    @XmlSchemaType(name="anyURI")
    protected String mediaUri;
    @XmlElement(name="StreamSetup", required=true)
    protected StreamSetup streamSetup;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ReceiverMode getMode() {
        return this.mode;
    }

    public void setMode(ReceiverMode value) {
        this.mode = value;
    }

    public String getMediaUri() {
        return this.mediaUri;
    }

    public void setMediaUri(String value) {
        this.mediaUri = value;
    }

    public StreamSetup getStreamSetup() {
        return this.streamSetup;
    }

    public void setStreamSetup(StreamSetup value) {
        this.streamSetup = value;
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

