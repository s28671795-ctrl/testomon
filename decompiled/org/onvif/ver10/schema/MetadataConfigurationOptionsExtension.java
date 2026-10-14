/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.MetadataConfigurationOptionsExtension2;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataConfigurationOptionsExtension", propOrder={"compressionType", "extension"})
public class MetadataConfigurationOptionsExtension {
    @XmlElement(name="CompressionType")
    protected List<String> compressionType;
    @XmlElement(name="Extension")
    protected MetadataConfigurationOptionsExtension2 extension;

    public List<String> getCompressionType() {
        if (this.compressionType == null) {
            this.compressionType = new ArrayList<String>();
        }
        return this.compressionType;
    }

    public MetadataConfigurationOptionsExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(MetadataConfigurationOptionsExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

