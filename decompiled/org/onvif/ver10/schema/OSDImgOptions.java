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
import org.onvif.ver10.schema.OSDImgOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDImgOptions", propOrder={"imagePath", "extension"})
public class OSDImgOptions {
    @XmlElement(name="ImagePath", required=true)
    @XmlSchemaType(name="anyURI")
    protected List<String> imagePath;
    @XmlElement(name="Extension")
    protected OSDImgOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getImagePath() {
        if (this.imagePath == null) {
            this.imagePath = new ArrayList<String>();
        }
        return this.imagePath;
    }

    public OSDImgOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDImgOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

