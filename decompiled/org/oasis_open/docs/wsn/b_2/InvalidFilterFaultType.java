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
@XmlType(name="InvalidFilterFaultType", propOrder={"unknownFilter"})
public class InvalidFilterFaultType
extends BaseFaultType {
    @XmlElement(name="UnknownFilter", required=true)
    protected List<QName> unknownFilter;

    public List<QName> getUnknownFilter() {
        if (this.unknownFilter == null) {
            this.unknownFilter = new ArrayList<QName>();
        }
        return this.unknownFilter;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

