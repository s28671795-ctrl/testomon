/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

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
import org.onvif.ver10.schema.ActionEngineEventPayloadExtension;
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2003._05.soap_envelope.Fault;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ActionEngineEventPayload", propOrder={"requestInfo", "responseInfo", "fault", "extension"})
public class ActionEngineEventPayload {
    @XmlElement(name="RequestInfo")
    protected Envelope requestInfo;
    @XmlElement(name="ResponseInfo")
    protected Envelope responseInfo;
    @XmlElement(name="Fault")
    protected Fault fault;
    @XmlElement(name="Extension")
    protected ActionEngineEventPayloadExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Envelope getRequestInfo() {
        return this.requestInfo;
    }

    public void setRequestInfo(Envelope value) {
        this.requestInfo = value;
    }

    public Envelope getResponseInfo() {
        return this.responseInfo;
    }

    public void setResponseInfo(Envelope value) {
        this.responseInfo = value;
    }

    public Fault getFault() {
        return this.fault;
    }

    public void setFault(Fault value) {
        this.fault = value;
    }

    public ActionEngineEventPayloadExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ActionEngineEventPayloadExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

