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
import org.onvif.ver10.schema.Color;
import org.onvif.ver10.schema.ColorCovariance;
import org.onvif.ver10.schema.ColorDescriptorExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ColorDescriptor", propOrder={"colorCluster", "extension"})
public class ColorDescriptor {
    @XmlElement(name="ColorCluster")
    protected List<ColorCluster> colorCluster;
    @XmlElement(name="Extension")
    protected ColorDescriptorExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<ColorCluster> getColorCluster() {
        if (this.colorCluster == null) {
            this.colorCluster = new ArrayList<ColorCluster>();
        }
        return this.colorCluster;
    }

    public ColorDescriptorExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ColorDescriptorExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"color", "weight", "covariance"})
    public static class ColorCluster {
        @XmlElement(name="Color", required=true)
        protected Color color;
        @XmlElement(name="Weight")
        protected Float weight;
        @XmlElement(name="Covariance")
        protected ColorCovariance covariance;

        public Color getColor() {
            return this.color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Float getWeight() {
            return this.weight;
        }

        public void setWeight(Float value) {
            this.weight = value;
        }

        public ColorCovariance getCovariance() {
            return this.covariance;
        }

        public void setCovariance(ColorCovariance value) {
            this.covariance = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

