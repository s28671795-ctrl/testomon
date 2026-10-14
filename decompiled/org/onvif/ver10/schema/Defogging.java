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
import org.onvif.ver10.schema.DefoggingExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Defogging", propOrder={"mode", "level", "extension"})
public class Defogging {
    @XmlElement(name="Mode", required=true)
    protected String mode;
    @XmlElement(name="Level")
    protected Float level;
    @XmlElement(name="Extension")
    protected DefoggingExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getMode() {
        return this.mode;
    }

    public void setMode(String value) {
        this.mode = value;
    }

    public Float getLevel() {
        return this.level;
    }

    public void setLevel(Float value) {
        this.level = value;
    }

    public DefoggingExtension getExtension() {
        return this.extension;
    }

    public void setExtension(DefoggingExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

