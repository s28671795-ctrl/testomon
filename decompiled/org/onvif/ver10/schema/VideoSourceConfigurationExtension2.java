/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.LensDescription;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoSourceConfigurationExtension2", propOrder={"lensDescription", "any"})
public class VideoSourceConfigurationExtension2 {
    @XmlElement(name="LensDescription")
    protected List<LensDescription> lensDescription;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public List<LensDescription> getLensDescription() {
        if (this.lensDescription == null) {
            this.lensDescription = new ArrayList<LensDescription>();
        }
        return this.lensDescription;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

