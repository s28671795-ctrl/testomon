/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.t_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.t_1.ExtensibleDocumented;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TopicSetType", propOrder={"any"})
public class TopicSetType
extends ExtensibleDocumented {
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

