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
import org.onvif.ver10.schema.Config;
import org.onvif.ver10.schema.MetadataInputExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataInput", propOrder={"metadataConfig", "extension"})
public class MetadataInput {
    @XmlElement(name="MetadataConfig")
    protected List<Config> metadataConfig;
    @XmlElement(name="Extension")
    protected MetadataInputExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Config> getMetadataConfig() {
        if (this.metadataConfig == null) {
            this.metadataConfig = new ArrayList<Config>();
        }
        return this.metadataConfig;
    }

    public MetadataInputExtension getExtension() {
        return this.extension;
    }

    public void setExtension(MetadataInputExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

