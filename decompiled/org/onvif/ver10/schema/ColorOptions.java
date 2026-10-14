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
import org.onvif.ver10.schema.ColorspaceRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ColorOptions", propOrder={"colorList", "colorspaceRange"})
public class ColorOptions {
    @XmlElement(name="ColorList")
    protected List<Color> colorList;
    @XmlElement(name="ColorspaceRange")
    protected List<ColorspaceRange> colorspaceRange;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Color> getColorList() {
        if (this.colorList == null) {
            this.colorList = new ArrayList<Color>();
        }
        return this.colorList;
    }

    public List<ColorspaceRange> getColorspaceRange() {
        if (this.colorspaceRange == null) {
            this.colorspaceRange = new ArrayList<ColorspaceRange>();
        }
        return this.colorspaceRange;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

