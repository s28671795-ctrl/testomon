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
import org.onvif.ver10.schema.AppearanceExtension;
import org.onvif.ver10.schema.ClassDescriptor;
import org.onvif.ver10.schema.ColorDescriptor;
import org.onvif.ver10.schema.ShapeDescriptor;
import org.onvif.ver10.schema.Transformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Appearance", propOrder={"transformation", "shape", "color", "clazz", "extension"})
public class Appearance {
    @XmlElement(name="Transformation")
    protected Transformation transformation;
    @XmlElement(name="Shape")
    protected ShapeDescriptor shape;
    @XmlElement(name="Color")
    protected ColorDescriptor color;
    @XmlElement(name="Class")
    protected ClassDescriptor clazz;
    @XmlElement(name="Extension")
    protected AppearanceExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Transformation getTransformation() {
        return this.transformation;
    }

    public void setTransformation(Transformation value) {
        this.transformation = value;
    }

    public ShapeDescriptor getShape() {
        return this.shape;
    }

    public void setShape(ShapeDescriptor value) {
        this.shape = value;
    }

    public ColorDescriptor getColor() {
        return this.color;
    }

    public void setColor(ColorDescriptor value) {
        this.color = value;
    }

    public ClassDescriptor getClazz() {
        return this.clazz;
    }

    public void setClazz(ClassDescriptor value) {
        this.clazz = value;
    }

    public AppearanceExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AppearanceExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

