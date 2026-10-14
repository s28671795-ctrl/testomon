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
import org.onvif.ver10.schema.RotateExtension;
import org.onvif.ver10.schema.RotateMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Rotate", propOrder={"mode", "degree", "extension"})
public class Rotate {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected RotateMode mode;
    @XmlElement(name="Degree")
    protected Integer degree;
    @XmlElement(name="Extension")
    protected RotateExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public RotateMode getMode() {
        return this.mode;
    }

    public void setMode(RotateMode value) {
        this.mode = value;
    }

    public Integer getDegree() {
        return this.degree;
    }

    public void setDegree(Integer value) {
        this.degree = value;
    }

    public RotateExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RotateExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

