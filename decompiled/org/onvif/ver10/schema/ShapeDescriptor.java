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
import org.onvif.ver10.schema.Polygon;
import org.onvif.ver10.schema.Rectangle;
import org.onvif.ver10.schema.ShapeDescriptorExtension;
import org.onvif.ver10.schema.Vector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ShapeDescriptor", propOrder={"boundingBox", "centerOfGravity", "polygon", "extension"})
public class ShapeDescriptor {
    @XmlElement(name="BoundingBox", required=true)
    protected Rectangle boundingBox;
    @XmlElement(name="CenterOfGravity", required=true)
    protected Vector centerOfGravity;
    @XmlElement(name="Polygon")
    protected List<Polygon> polygon;
    @XmlElement(name="Extension")
    protected ShapeDescriptorExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(Rectangle value) {
        this.boundingBox = value;
    }

    public Vector getCenterOfGravity() {
        return this.centerOfGravity;
    }

    public void setCenterOfGravity(Vector value) {
        this.centerOfGravity = value;
    }

    public List<Polygon> getPolygon() {
        if (this.polygon == null) {
            this.polygon = new ArrayList<Polygon>();
        }
        return this.polygon;
    }

    public ShapeDescriptorExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ShapeDescriptorExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

