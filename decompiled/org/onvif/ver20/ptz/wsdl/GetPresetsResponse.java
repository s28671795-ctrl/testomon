/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZPreset;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"preset"})
@XmlRootElement(name="GetPresetsResponse")
public class GetPresetsResponse {
    @XmlElement(name="Preset")
    protected List<PTZPreset> preset;

    public List<PTZPreset> getPreset() {
        if (this.preset == null) {
            this.preset = new ArrayList<PTZPreset>();
        }
        return this.preset;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

