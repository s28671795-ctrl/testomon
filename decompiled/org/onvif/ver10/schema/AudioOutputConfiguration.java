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
import org.onvif.ver10.schema.ConfigurationEntity;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioOutputConfiguration", propOrder={"outputToken", "sendPrimacy", "outputLevel", "any"})
public class AudioOutputConfiguration
extends ConfigurationEntity {
    @XmlElement(name="OutputToken", required=true)
    protected String outputToken;
    @XmlElement(name="SendPrimacy")
    @XmlSchemaType(name="anyURI")
    protected String sendPrimacy;
    @XmlElement(name="OutputLevel")
    protected int outputLevel;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getOutputToken() {
        return this.outputToken;
    }

    public void setOutputToken(String value) {
        this.outputToken = value;
    }

    public String getSendPrimacy() {
        return this.sendPrimacy;
    }

    public void setSendPrimacy(String value) {
        this.sendPrimacy = value;
    }

    public int getOutputLevel() {
        return this.outputLevel;
    }

    public void setOutputLevel(int value) {
        this.outputLevel = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

