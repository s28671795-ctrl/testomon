/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.SerialData;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"serialData", "timeOut", "dataLength", "delimiter"})
@XmlRootElement(name="SendReceiveSerialCommand")
public class SendReceiveSerialCommand {
    @XmlElement(name="SerialData")
    protected SerialData serialData;
    @XmlElement(name="TimeOut")
    protected Duration timeOut;
    @XmlElement(name="DataLength")
    protected BigInteger dataLength;
    @XmlElement(name="Delimiter")
    protected String delimiter;

    public SerialData getSerialData() {
        return this.serialData;
    }

    public void setSerialData(SerialData value) {
        this.serialData = value;
    }

    public Duration getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(Duration value) {
        this.timeOut = value;
    }

    public BigInteger getDataLength() {
        return this.dataLength;
    }

    public void setDataLength(BigInteger value) {
        this.dataLength = value;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String value) {
        this.delimiter = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

