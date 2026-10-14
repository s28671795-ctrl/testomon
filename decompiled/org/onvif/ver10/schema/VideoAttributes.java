/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.VideoEncoding;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoAttributes", propOrder={"bitrate", "width", "height", "encoding", "framerate", "any"})
public class VideoAttributes {
    @XmlElement(name="Bitrate")
    protected Integer bitrate;
    @XmlElement(name="Width")
    protected int width;
    @XmlElement(name="Height")
    protected int height;
    @XmlElement(name="Encoding", required=true)
    @XmlSchemaType(name="string")
    protected VideoEncoding encoding;
    @XmlElement(name="Framerate")
    protected float framerate;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Integer getBitrate() {
        return this.bitrate;
    }

    public void setBitrate(Integer value) {
        this.bitrate = value;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int value) {
        this.width = value;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public VideoEncoding getEncoding() {
        return this.encoding;
    }

    public void setEncoding(VideoEncoding value) {
        this.encoding = value;
    }

    public float getFramerate() {
        return this.framerate;
    }

    public void setFramerate(float value) {
        this.framerate = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

