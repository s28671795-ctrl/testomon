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
import org.onvif.ver10.schema.FloatRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ColorspaceRange", propOrder={"x", "y", "z", "colorspace"})
public class ColorspaceRange {
    @XmlElement(name="X", required=true)
    protected FloatRange x;
    @XmlElement(name="Y", required=true)
    protected FloatRange y;
    @XmlElement(name="Z", required=true)
    protected FloatRange z;
    @XmlElement(name="Colorspace", required=true)
    @XmlSchemaType(name="anyURI")
    protected String colorspace;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public FloatRange getX() {
        return this.x;
    }

    public void setX(FloatRange value) {
        this.x = value;
    }

    public FloatRange getY() {
        return this.y;
    }

    public void setY(FloatRange value) {
        this.y = value;
    }

    public FloatRange getZ() {
        return this.z;
    }

    public void setZ(FloatRange value) {
        this.z = value;
    }

    public String getColorspace() {
        return this.colorspace;
    }

    public void setColorspace(String value) {
        this.colorspace = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

