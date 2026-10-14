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
import org.onvif.ver10.schema.IntRange;
import org.onvif.ver10.schema.OSDColorOptions;
import org.onvif.ver10.schema.OSDTextOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDTextOptions", propOrder={"type", "fontSizeRange", "dateFormat", "timeFormat", "fontColor", "backgroundColor", "extension"})
public class OSDTextOptions {
    @XmlElement(name="Type", required=true)
    protected List<String> type;
    @XmlElement(name="FontSizeRange")
    protected IntRange fontSizeRange;
    @XmlElement(name="DateFormat")
    protected List<String> dateFormat;
    @XmlElement(name="TimeFormat")
    protected List<String> timeFormat;
    @XmlElement(name="FontColor")
    protected OSDColorOptions fontColor;
    @XmlElement(name="BackgroundColor")
    protected OSDColorOptions backgroundColor;
    @XmlElement(name="Extension")
    protected OSDTextOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getType() {
        if (this.type == null) {
            this.type = new ArrayList<String>();
        }
        return this.type;
    }

    public IntRange getFontSizeRange() {
        return this.fontSizeRange;
    }

    public void setFontSizeRange(IntRange value) {
        this.fontSizeRange = value;
    }

    public List<String> getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new ArrayList<String>();
        }
        return this.dateFormat;
    }

    public List<String> getTimeFormat() {
        if (this.timeFormat == null) {
            this.timeFormat = new ArrayList<String>();
        }
        return this.timeFormat;
    }

    public OSDColorOptions getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(OSDColorOptions value) {
        this.fontColor = value;
    }

    public OSDColorOptions getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(OSDColorOptions value) {
        this.backgroundColor = value;
    }

    public OSDTextOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDTextOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

