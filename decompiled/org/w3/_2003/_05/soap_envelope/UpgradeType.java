/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.w3._2003._05.soap_envelope.SupportedEnvType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="UpgradeType", propOrder={"supportedEnvelope"})
public class UpgradeType {
    @XmlElement(name="SupportedEnvelope", required=true)
    protected List<SupportedEnvType> supportedEnvelope;

    public List<SupportedEnvType> getSupportedEnvelope() {
        if (this.supportedEnvelope == null) {
            this.supportedEnvelope = new ArrayList<SupportedEnvType>();
        }
        return this.supportedEnvelope;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

