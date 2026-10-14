/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.r_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsrf.bf_2.BaseFaultType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ResourceUnavailableFaultType")
public class ResourceUnavailableFaultType
extends BaseFaultType {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

