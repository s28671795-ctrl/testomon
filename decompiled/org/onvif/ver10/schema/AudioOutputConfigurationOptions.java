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
import org.onvif.ver10.schema.IntRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioOutputConfigurationOptions", propOrder={"outputTokensAvailable", "sendPrimacyOptions", "outputLevelRange", "any"})
public class AudioOutputConfigurationOptions {
    @XmlElement(name="OutputTokensAvailable", required=true)
    protected List<String> outputTokensAvailable;
    @XmlElement(name="SendPrimacyOptions")
    @XmlSchemaType(name="anyURI")
    protected List<String> sendPrimacyOptions;
    @XmlElement(name="OutputLevelRange", required=true)
    protected IntRange outputLevelRange;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getOutputTokensAvailable() {
        if (this.outputTokensAvailable == null) {
            this.outputTokensAvailable = new ArrayList<String>();
        }
        return this.outputTokensAvailable;
    }

    public List<String> getSendPrimacyOptions() {
        if (this.sendPrimacyOptions == null) {
            this.sendPrimacyOptions = new ArrayList<String>();
        }
        return this.sendPrimacyOptions;
    }

    public IntRange getOutputLevelRange() {
        return this.outputLevelRange;
    }

    public void setOutputLevelRange(IntRange value) {
        this.outputLevelRange = value;
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

