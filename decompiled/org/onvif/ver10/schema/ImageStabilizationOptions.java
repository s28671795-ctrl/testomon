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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FloatRange;
import org.onvif.ver10.schema.ImageStabilizationMode;
import org.onvif.ver10.schema.ImageStabilizationOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ImageStabilizationOptions", propOrder={"mode", "level", "extension"})
public class ImageStabilizationOptions {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<ImageStabilizationMode> mode;
    @XmlElement(name="Level")
    protected FloatRange level;
    @XmlElement(name="Extension")
    protected ImageStabilizationOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<ImageStabilizationMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<ImageStabilizationMode>();
        }
        return this.mode;
    }

    public FloatRange getLevel() {
        return this.level;
    }

    public void setLevel(FloatRange value) {
        this.level = value;
    }

    public ImageStabilizationOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ImageStabilizationOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

