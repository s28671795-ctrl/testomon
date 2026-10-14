/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.SystemLogUri;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SystemLogUriList", propOrder={"systemLog"})
public class SystemLogUriList {
    @XmlElement(name="SystemLog")
    protected List<SystemLogUri> systemLog;

    public List<SystemLogUri> getSystemLog() {
        if (this.systemLog == null) {
            this.systemLog = new ArrayList<SystemLogUri>();
        }
        return this.systemLog;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

