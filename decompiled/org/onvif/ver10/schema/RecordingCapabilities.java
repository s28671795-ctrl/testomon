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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingCapabilities", propOrder={"xAddr", "receiverSource", "mediaProfileSource", "dynamicRecordings", "dynamicTracks", "maxStringLength", "any"})
public class RecordingCapabilities {
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="ReceiverSource")
    protected boolean receiverSource;
    @XmlElement(name="MediaProfileSource")
    protected boolean mediaProfileSource;
    @XmlElement(name="DynamicRecordings")
    protected boolean dynamicRecordings;
    @XmlElement(name="DynamicTracks")
    protected boolean dynamicTracks;
    @XmlElement(name="MaxStringLength")
    protected int maxStringLength;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public boolean getReceiverSource() {
        return this.receiverSource;
    }

    public void setReceiverSource(boolean value) {
        this.receiverSource = value;
    }

    public boolean getMediaProfileSource() {
        return this.mediaProfileSource;
    }

    public void setMediaProfileSource(boolean value) {
        this.mediaProfileSource = value;
    }

    public boolean getDynamicRecordings() {
        return this.dynamicRecordings;
    }

    public void setDynamicRecordings(boolean value) {
        this.dynamicRecordings = value;
    }

    public boolean getDynamicTracks() {
        return this.dynamicTracks;
    }

    public void setDynamicTracks(boolean value) {
        this.dynamicTracks = value;
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public void setMaxStringLength(int value) {
        this.maxStringLength = value;
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

