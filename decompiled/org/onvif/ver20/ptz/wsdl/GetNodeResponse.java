/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZNode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ptzNode"})
@XmlRootElement(name="GetNodeResponse")
public class GetNodeResponse {
    @XmlElement(name="PTZNode", required=true)
    protected PTZNode ptzNode;

    public PTZNode getPTZNode() {
        return this.ptzNode;
    }

    public void setPTZNode(PTZNode value) {
        this.ptzNode = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

