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
import org.onvif.ver10.schema.OSDImgConfigurationExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OSDImgConfiguration", propOrder={"imgPath", "extension"})
public class OSDImgConfiguration {
    @XmlElement(name="ImgPath", required=true)
    @XmlSchemaType(name="anyURI")
    protected String imgPath;
    @XmlElement(name="Extension")
    protected OSDImgConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String value) {
        this.imgPath = value;
    }

    public OSDImgConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(OSDImgConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

