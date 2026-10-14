/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.MoveOptions20;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"moveOptions"})
@XmlRootElement(name="GetMoveOptionsResponse")
public class GetMoveOptionsResponse {
    @XmlElement(name="MoveOptions", required=true)
    protected MoveOptions20 moveOptions;

    public MoveOptions20 getMoveOptions() {
        return this.moveOptions;
    }

    public void setMoveOptions(MoveOptions20 value) {
        this.moveOptions = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

