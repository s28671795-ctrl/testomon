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
import org.onvif.ver10.schema.ConfigurationEntity;
import org.onvif.ver10.schema.MetadataInput;
import org.onvif.ver10.schema.SourceIdentification;
import org.onvif.ver10.schema.VideoEncoderConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AnalyticsEngineInput", propOrder={"sourceIdentification", "videoInput", "metadataInput", "any"})
public class AnalyticsEngineInput
extends ConfigurationEntity {
    @XmlElement(name="SourceIdentification", required=true)
    protected SourceIdentification sourceIdentification;
    @XmlElement(name="VideoInput", required=true)
    protected VideoEncoderConfiguration videoInput;
    @XmlElement(name="MetadataInput", required=true)
    protected MetadataInput metadataInput;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public SourceIdentification getSourceIdentification() {
        return this.sourceIdentification;
    }

    public void setSourceIdentification(SourceIdentification value) {
        this.sourceIdentification = value;
    }

    public VideoEncoderConfiguration getVideoInput() {
        return this.videoInput;
    }

    public void setVideoInput(VideoEncoderConfiguration value) {
        this.videoInput = value;
    }

    public MetadataInput getMetadataInput() {
        return this.metadataInput;
    }

    public void setMetadataInput(MetadataInput value) {
        this.metadataInput = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

