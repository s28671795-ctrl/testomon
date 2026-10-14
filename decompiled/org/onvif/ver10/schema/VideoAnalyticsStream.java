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
import org.onvif.ver10.schema.Frame;
import org.onvif.ver10.schema.VideoAnalyticsStreamExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VideoAnalyticsStream", propOrder={"frameOrExtension"})
public class VideoAnalyticsStream {
    @XmlElements(value={@XmlElement(name="Frame", type=Frame.class), @XmlElement(name="Extension", type=VideoAnalyticsStreamExtension.class)})
    protected List<Object> frameOrExtension;

    public List<Object> getFrameOrExtension() {
        if (this.frameOrExtension == null) {
            this.frameOrExtension = new ArrayList<Object>();
        }
        return this.frameOrExtension;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

