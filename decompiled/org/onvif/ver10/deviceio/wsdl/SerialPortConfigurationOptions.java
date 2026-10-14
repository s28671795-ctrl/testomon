/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.ParityBitList;
import org.onvif.ver10.schema.FloatList;
import org.onvif.ver10.schema.IntList;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SerialPortConfigurationOptions", propOrder={"baudRateList", "parityBitList", "characterLengthList", "stopBitList", "any"})
public class SerialPortConfigurationOptions {
    @XmlElement(name="BaudRateList", required=true)
    protected IntList baudRateList;
    @XmlElement(name="ParityBitList", required=true)
    protected ParityBitList parityBitList;
    @XmlElement(name="CharacterLengthList", required=true)
    protected IntList characterLengthList;
    @XmlElement(name="StopBitList", required=true)
    protected FloatList stopBitList;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="token", required=true)
    protected String token;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public IntList getBaudRateList() {
        return this.baudRateList;
    }

    public void setBaudRateList(IntList value) {
        this.baudRateList = value;
    }

    public ParityBitList getParityBitList() {
        return this.parityBitList;
    }

    public void setParityBitList(ParityBitList value) {
        this.parityBitList = value;
    }

    public IntList getCharacterLengthList() {
        return this.characterLengthList;
    }

    public void setCharacterLengthList(IntList value) {
        this.characterLengthList = value;
    }

    public FloatList getStopBitList() {
        return this.stopBitList;
    }

    public void setStopBitList(FloatList value) {
        this.stopBitList = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

