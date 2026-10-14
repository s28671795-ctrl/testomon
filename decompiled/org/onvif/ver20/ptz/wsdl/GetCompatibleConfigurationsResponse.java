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
import org.onvif.ver10.schema.PTZConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ptzConfiguration"})
@XmlRootElement(name="GetCompatibleConfigurationsResponse")
public class GetCompatibleConfigurationsResponse {
    @XmlElement(name="PTZConfiguration")
    protected List<PTZConfiguration> ptzConfiguration;

    public List<PTZConfiguration> getPTZConfiguration() {
        if (this.ptzConfiguration == null) {
            this.ptzConfiguration = new ArrayList<PTZConfiguration>();
        }
        return this.ptzConfiguration;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

