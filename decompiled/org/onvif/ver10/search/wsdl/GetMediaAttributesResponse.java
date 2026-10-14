/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.MediaAttributes;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"mediaAttributes"})
@XmlRootElement(name="GetMediaAttributesResponse")
public class GetMediaAttributesResponse {
    @XmlElement(name="MediaAttributes")
    protected List<MediaAttributes> mediaAttributes;

    public List<MediaAttributes> getMediaAttributes() {
        if (this.mediaAttributes == null) {
            this.mediaAttributes = new ArrayList<MediaAttributes>();
        }
        return this.mediaAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

