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
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingJobStateTrack", propOrder={"sourceTag", "destination", "error", "state", "any"})
public class RecordingJobStateTrack {
    @XmlElement(name="SourceTag", required=true)
    protected String sourceTag;
    @XmlElement(name="Destination", required=true)
    protected String destination;
    @XmlElement(name="Error")
    protected String error;
    @XmlElement(name="State", required=true)
    protected String state;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getSourceTag() {
        return this.sourceTag;
    }

    public void setSourceTag(String value) {
        this.sourceTag = value;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String value) {
        this.destination = value;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String value) {
        this.error = value;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String value) {
        this.state = value;
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

