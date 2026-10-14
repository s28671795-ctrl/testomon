/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsrf.bf_2.BaseFaultType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="UnrecognizedPolicyRequestFaultType", propOrder={"unrecognizedPolicy"})
public class UnrecognizedPolicyRequestFaultType
extends BaseFaultType {
    @XmlElement(name="UnrecognizedPolicy")
    protected List<QName> unrecognizedPolicy;

    public List<QName> getUnrecognizedPolicy() {
        if (this.unrecognizedPolicy == null) {
            this.unrecognizedPolicy = new ArrayList<QName>();
        }
        return this.unrecognizedPolicy;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

