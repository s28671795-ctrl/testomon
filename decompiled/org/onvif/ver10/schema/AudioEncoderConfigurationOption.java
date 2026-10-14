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
import org.onvif.ver10.schema.AudioEncoding;
import org.onvif.ver10.schema.IntList;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioEncoderConfigurationOption", propOrder={"encoding", "bitrateList", "sampleRateList", "any"})
public class AudioEncoderConfigurationOption {
    @XmlElement(name="Encoding", required=true)
    @XmlSchemaType(name="string")
    protected AudioEncoding encoding;
    @XmlElement(name="BitrateList", required=true)
    protected IntList bitrateList;
    @XmlElement(name="SampleRateList", required=true)
    protected IntList sampleRateList;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AudioEncoding getEncoding() {
        return this.encoding;
    }

    public void setEncoding(AudioEncoding value) {
        this.encoding = value;
    }

    public IntList getBitrateList() {
        return this.bitrateList;
    }

    public void setBitrateList(IntList value) {
        this.bitrateList = value;
    }

    public IntList getSampleRateList() {
        return this.sampleRateList;
    }

    public void setSampleRateList(IntList value) {
        this.sampleRateList = value;
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

