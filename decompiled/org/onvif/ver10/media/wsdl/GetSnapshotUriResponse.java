/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.MediaUri;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"mediaUri"})
@XmlRootElement(name="GetSnapshotUriResponse")
public class GetSnapshotUriResponse {
    @XmlElement(name="MediaUri", required=true)
    protected MediaUri mediaUri;

    public MediaUri getMediaUri() {
        return this.mediaUri;
    }

    public void setMediaUri(MediaUri value) {
        this.mediaUri = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

