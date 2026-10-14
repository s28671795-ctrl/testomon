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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioEncoderConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PaneConfiguration", propOrder={"paneName", "audioOutputToken", "audioSourceToken", "audioEncoderConfiguration", "receiverToken", "token", "any"})
public class PaneConfiguration {
    @XmlElement(name="PaneName")
    protected String paneName;
    @XmlElement(name="AudioOutputToken")
    protected String audioOutputToken;
    @XmlElement(name="AudioSourceToken")
    protected String audioSourceToken;
    @XmlElement(name="AudioEncoderConfiguration")
    protected AudioEncoderConfiguration audioEncoderConfiguration;
    @XmlElement(name="ReceiverToken")
    protected String receiverToken;
    @XmlElement(name="Token", required=true)
    protected String token;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getPaneName() {
        return this.paneName;
    }

    public void setPaneName(String value) {
        this.paneName = value;
    }

    public String getAudioOutputToken() {
        return this.audioOutputToken;
    }

    public void setAudioOutputToken(String value) {
        this.audioOutputToken = value;
    }

    public String getAudioSourceToken() {
        return this.audioSourceToken;
    }

    public void setAudioSourceToken(String value) {
        this.audioSourceToken = value;
    }

    public AudioEncoderConfiguration getAudioEncoderConfiguration() {
        return this.audioEncoderConfiguration;
    }

    public void setAudioEncoderConfiguration(AudioEncoderConfiguration value) {
        this.audioEncoderConfiguration = value;
    }

    public String getReceiverToken() {
        return this.receiverToken;
    }

    public void setReceiverToken(String value) {
        this.receiverToken = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
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

