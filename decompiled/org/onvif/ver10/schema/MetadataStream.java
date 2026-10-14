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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.EventStream;
import org.onvif.ver10.schema.MetadataStreamExtension;
import org.onvif.ver10.schema.PTZStream;
import org.onvif.ver10.schema.VideoAnalyticsStream;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataStream", propOrder={"videoAnalyticsOrPTZOrEvent"})
public class MetadataStream {
    @XmlElements(value={@XmlElement(name="VideoAnalytics", type=VideoAnalyticsStream.class), @XmlElement(name="PTZ", type=PTZStream.class), @XmlElement(name="Event", type=EventStream.class), @XmlElement(name="Extension", type=MetadataStreamExtension.class)})
    protected List<Object> videoAnalyticsOrPTZOrEvent;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getVideoAnalyticsOrPTZOrEvent() {
        if (this.videoAnalyticsOrPTZOrEvent == null) {
            this.videoAnalyticsOrPTZOrEvent = new ArrayList<Object>();
        }
        return this.videoAnalyticsOrPTZOrEvent;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

