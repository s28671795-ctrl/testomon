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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.ParityBit;
import org.onvif.ver10.deviceio.wsdl.SerialPortType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SerialPortConfiguration", propOrder={"baudRate", "parityBit", "characterLength", "stopBit", "any"})
public class SerialPortConfiguration {
    @XmlElement(name="BaudRate")
    protected int baudRate;
    @XmlElement(name="ParityBit", required=true)
    @XmlSchemaType(name="string")
    protected ParityBit parityBit;
    @XmlElement(name="CharacterLength")
    protected int characterLength;
    @XmlElement(name="StopBit")
    protected float stopBit;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="token", required=true)
    protected String token;
    @XmlAttribute(name="type", required=true)
    protected SerialPortType type;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public int getBaudRate() {
        return this.baudRate;
    }

    public void setBaudRate(int value) {
        this.baudRate = value;
    }

    public ParityBit getParityBit() {
        return this.parityBit;
    }

    public void setParityBit(ParityBit value) {
        this.parityBit = value;
    }

    public int getCharacterLength() {
        return this.characterLength;
    }

    public void setCharacterLength(int value) {
        this.characterLength = value;
    }

    public float getStopBit() {
        return this.stopBit;
    }

    public void setStopBit(float value) {
        this.stopBit = value;
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

    public SerialPortType getType() {
        return this.type;
    }

    public void setType(SerialPortType value) {
        this.type = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

