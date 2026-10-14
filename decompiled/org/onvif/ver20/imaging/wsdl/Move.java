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
import org.onvif.ver10.schema.FocusMove;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"videoSourceToken", "focus"})
@XmlRootElement(name="Move")
public class Move {
    @XmlElement(name="VideoSourceToken", required=true)
    protected String videoSourceToken;
    @XmlElement(name="Focus", required=true)
    protected FocusMove focus;

    public String getVideoSourceToken() {
        return this.videoSourceToken;
    }

    public void setVideoSourceToken(String value) {
        this.videoSourceToken = value;
    }

    public FocusMove getFocus() {
        return this.focus;
    }

    public void setFocus(FocusMove value) {
        this.focus = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

