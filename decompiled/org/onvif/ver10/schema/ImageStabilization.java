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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ImageStabilizationExtension;
import org.onvif.ver10.schema.ImageStabilizationMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImageStabilization", propOrder={"mode", "level", "extension"})
public class ImageStabilization {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected ImageStabilizationMode mode;
    @XmlElement(name="Level")
    protected Float level;
    @XmlElement(name="Extension")
    protected ImageStabilizationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ImageStabilizationMode getMode() {
        return this.mode;
    }

    public void setMode(ImageStabilizationMode value) {
        this.mode = value;
    }

    public Float getLevel() {
        return this.level;
    }

    public void setLevel(Float value) {
        this.level = value;
    }

    public ImageStabilizationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ImageStabilizationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

