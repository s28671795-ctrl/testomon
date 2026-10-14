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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.IntRectangle;
import org.onvif.ver10.schema.VideoSourceConfigurationExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceConfiguration", propOrder={"sourceToken", "bounds", "any", "extension"})
public class VideoSourceConfiguration
extends ConfigurationEntity {
    @XmlElement(name="SourceToken", required=true)
    protected String sourceToken;
    @XmlElement(name="Bounds", required=true)
    protected IntRectangle bounds;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Extension")
    protected VideoSourceConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getSourceToken() {
        return this.sourceToken;
    }

    public void setSourceToken(String value) {
        this.sourceToken = value;
    }

    public IntRectangle getBounds() {
        return this.bounds;
    }

    public void setBounds(IntRectangle value) {
        this.bounds = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public VideoSourceConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(VideoSourceConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

