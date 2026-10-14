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
import org.onvif.ver10.schema.TransformationExtension;
import org.onvif.ver10.schema.Vector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Transformation", propOrder={"translate", "scale", "extension"})
public class Transformation {
    @XmlElement(name="Translate")
    protected Vector translate;
    @XmlElement(name="Scale")
    protected Vector scale;
    @XmlElement(name="Extension")
    protected TransformationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Vector getTranslate() {
        return this.translate;
    }

    public void setTranslate(Vector value) {
        this.translate = value;
    }

    public Vector getScale() {
        return this.scale;
    }

    public void setScale(Vector value) {
        this.scale = value;
    }

    public TransformationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(TransformationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

