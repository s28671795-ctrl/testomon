/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZStatus;
import org.onvif.ver10.schema.PTZStreamExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZStream", propOrder={"ptzStatusOrExtension"})
public class PTZStream {
    @XmlElements(value={@XmlElement(name="PTZStatus", type=PTZStatus.class), @XmlElement(name="Extension", type=PTZStreamExtension.class)})
    protected List<Object> ptzStatusOrExtension;

    public List<Object> getPTZStatusOrExtension() {
        if (this.ptzStatusOrExtension == null) {
            this.ptzStatusOrExtension = new ArrayList<Object>();
        }
        return this.ptzStatusOrExtension;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

