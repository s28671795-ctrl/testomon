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
import org.onvif.ver10.schema.MetadataConfigurationOptionsExtension;
import org.onvif.ver10.schema.PTZStatusFilterOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataConfigurationOptions", propOrder={"ptzStatusFilterOptions", "any", "extension"})
public class MetadataConfigurationOptions {
    @XmlElement(name="PTZStatusFilterOptions", required=true)
    protected PTZStatusFilterOptions ptzStatusFilterOptions;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Extension")
    protected MetadataConfigurationOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZStatusFilterOptions getPTZStatusFilterOptions() {
        return this.ptzStatusFilterOptions;
    }

    public void setPTZStatusFilterOptions(PTZStatusFilterOptions value) {
        this.ptzStatusFilterOptions = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public MetadataConfigurationOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(MetadataConfigurationOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

