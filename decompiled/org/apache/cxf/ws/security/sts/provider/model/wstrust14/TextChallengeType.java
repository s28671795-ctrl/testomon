/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ImageType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TextChallengeType", propOrder={"image"})
public class TextChallengeType {
    @XmlElement(name="Image")
    protected ImageType image;
    @XmlAttribute(name="RefID", required=true)
    @XmlSchemaType(name="anyURI")
    protected String refID;
    @XmlAttribute(name="Label")
    protected String label;
    @XmlAttribute(name="MaxLen")
    protected Integer maxLen;
    @XmlAttribute(name="HideText")
    protected Boolean hideText;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ImageType getImage() {
        return this.image;
    }

    public void setImage(ImageType value) {
        this.image = value;
    }

    public String getRefID() {
        return this.refID;
    }

    public void setRefID(String value) {
        this.refID = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    public Integer getMaxLen() {
        return this.maxLen;
    }

    public void setMaxLen(Integer value) {
        this.maxLen = value;
    }

    public Boolean isHideText() {
        return this.hideText;
    }

    public void setHideText(Boolean value) {
        this.hideText = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

