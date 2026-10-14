/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.DigitalInput;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"digitalInputs"})
@XmlRootElement(name="GetDigitalInputsResponse")
public class GetDigitalInputsResponse {
    @XmlElement(name="DigitalInputs")
    protected List<DigitalInput> digitalInputs;

    public List<DigitalInput> getDigitalInputs() {
        if (this.digitalInputs == null) {
            this.digitalInputs = new ArrayList<DigitalInput>();
        }
        return this.digitalInputs;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

