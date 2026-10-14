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
import org.onvif.ver10.schema.MaximumNumberOfOSDs;
import org.onvif.ver10.schema.OSDConfigurationOptionsExtension;
import org.onvif.ver10.schema.OSDImgOptions;
import org.onvif.ver10.schema.OSDTextOptions;
import org.onvif.ver10.schema.OSDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDConfigurationOptions", propOrder={"maximumNumberOfOSDs", "type", "positionOption", "textOption", "imageOption", "extension"})
public class OSDConfigurationOptions {
    @XmlElement(name="MaximumNumberOfOSDs", required=true)
    protected MaximumNumberOfOSDs maximumNumberOfOSDs;
    @XmlElement(name="Type", required=true)
    @XmlSchemaType(name="string")
    protected List<OSDType> type;
    @XmlElement(name="PositionOption", required=true)
    protected List<String> positionOption;
    @XmlElement(name="TextOption")
    protected OSDTextOptions textOption;
    @XmlElement(name="ImageOption")
    protected OSDImgOptions imageOption;
    @XmlElement(name="Extension")
    protected OSDConfigurationOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public MaximumNumberOfOSDs getMaximumNumberOfOSDs() {
        return this.maximumNumberOfOSDs;
    }

    public void setMaximumNumberOfOSDs(MaximumNumberOfOSDs value) {
        this.maximumNumberOfOSDs = value;
    }

    public List<OSDType> getType() {
        if (this.type == null) {
            this.type = new ArrayList<OSDType>();
        }
        return this.type;
    }

    public List<String> getPositionOption() {
        if (this.positionOption == null) {
            this.positionOption = new ArrayList<String>();
        }
        return this.positionOption;
    }

    public OSDTextOptions getTextOption() {
        return this.textOption;
    }

    public void setTextOption(OSDTextOptions value) {
        this.textOption = value;
    }

    public OSDImgOptions getImageOption() {
        return this.imageOption;
    }

    public void setImageOption(OSDImgOptions value) {
        this.imageOption = value;
    }

    public OSDConfigurationOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDConfigurationOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

