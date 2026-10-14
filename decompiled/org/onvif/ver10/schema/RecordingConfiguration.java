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
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.RecordingSourceInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingConfiguration", propOrder={"source", "content", "maximumRetentionTime", "any"})
public class RecordingConfiguration {
    @XmlElement(name="Source", required=true)
    protected RecordingSourceInformation source;
    @XmlElement(name="Content", required=true)
    protected String content;
    @XmlElement(name="MaximumRetentionTime", required=true)
    protected Duration maximumRetentionTime;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public RecordingSourceInformation getSource() {
        return this.source;
    }

    public void setSource(RecordingSourceInformation value) {
        this.source = value;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public Duration getMaximumRetentionTime() {
        return this.maximumRetentionTime;
    }

    public void setMaximumRetentionTime(Duration value) {
        this.maximumRetentionTime = value;
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

