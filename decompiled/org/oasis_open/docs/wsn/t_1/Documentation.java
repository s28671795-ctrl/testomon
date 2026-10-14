/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.t_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Documentation", propOrder={"content"})
public class Documentation {
    @XmlMixed
    @XmlAnyElement(lax=true)
    protected List<Object> content;

    public List<Object> getContent() {
        if (this.content == null) {
            this.content = new ArrayList<Object>();
        }
        return this.content;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

