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
import org.onvif.ver10.schema.OSDColor;
import org.onvif.ver10.schema.OSDTextConfigurationExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDTextConfiguration", propOrder={"type", "dateFormat", "timeFormat", "fontSize", "fontColor", "backgroundColor", "plainText", "extension"})
public class OSDTextConfiguration {
    @XmlElement(name="Type", required=true)
    protected String type;
    @XmlElement(name="DateFormat")
    protected String dateFormat;
    @XmlElement(name="TimeFormat")
    protected String timeFormat;
    @XmlElement(name="FontSize")
    protected Integer fontSize;
    @XmlElement(name="FontColor")
    protected OSDColor fontColor;
    @XmlElement(name="BackgroundColor")
    protected OSDColor backgroundColor;
    @XmlElement(name="PlainText")
    protected String plainText;
    @XmlElement(name="Extension")
    protected OSDTextConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String value) {
        this.dateFormat = value;
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String value) {
        this.timeFormat = value;
    }

    public Integer getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(Integer value) {
        this.fontSize = value;
    }

    public OSDColor getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(OSDColor value) {
        this.fontColor = value;
    }

    public OSDColor getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(OSDColor value) {
        this.backgroundColor = value;
    }

    public String getPlainText() {
        return this.plainText;
    }

    public void setPlainText(String value) {
        this.plainText = value;
    }

    public OSDTextConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDTextConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

