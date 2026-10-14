/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"dot1XConfigurationToken"})
@XmlRootElement(name="DeleteDot1XConfiguration")
public class DeleteDot1XConfiguration {
    @XmlElement(name="Dot1XConfigurationToken")
    protected List<String> dot1XConfigurationToken;

    public List<String> getDot1XConfigurationToken() {
        if (this.dot1XConfigurationToken == null) {
            this.dot1XConfigurationToken = new ArrayList<String>();
        }
        return this.dot1XConfigurationToken;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

