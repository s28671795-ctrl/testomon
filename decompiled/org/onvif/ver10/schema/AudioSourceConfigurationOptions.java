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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AudioSourceOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioSourceConfigurationOptions", propOrder={"inputTokensAvailable", "extension"})
public class AudioSourceConfigurationOptions {
    @XmlElement(name="InputTokensAvailable", required=true)
    protected List<String> inputTokensAvailable;
    @XmlElement(name="Extension")
    protected AudioSourceOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getInputTokensAvailable() {
        if (this.inputTokensAvailable == null) {
            this.inputTokensAvailable = new ArrayList<String>();
        }
        return this.inputTokensAvailable;
    }

    public AudioSourceOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AudioSourceOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

