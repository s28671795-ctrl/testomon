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
import org.onvif.ver10.schema.AudioEncoderConfigurationOption;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AudioEncoderConfigurationOptions", propOrder={"options"})
public class AudioEncoderConfigurationOptions {
    @XmlElement(name="Options")
    protected List<AudioEncoderConfigurationOption> options;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<AudioEncoderConfigurationOption> getOptions() {
        if (this.options == null) {
            this.options = new ArrayList<AudioEncoderConfigurationOption>();
        }
        return this.options;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

