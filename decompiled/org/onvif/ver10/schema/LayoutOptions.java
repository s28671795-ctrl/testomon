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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.LayoutOptionsExtension;
import org.onvif.ver10.schema.PaneLayoutOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LayoutOptions", propOrder={"paneLayoutOptions", "extension"})
public class LayoutOptions {
    @XmlElement(name="PaneLayoutOptions", required=true)
    protected List<PaneLayoutOptions> paneLayoutOptions;
    @XmlElement(name="Extension")
    protected LayoutOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<PaneLayoutOptions> getPaneLayoutOptions() {
        if (this.paneLayoutOptions == null) {
            this.paneLayoutOptions = new ArrayList<PaneLayoutOptions>();
        }
        return this.paneLayoutOptions;
    }

    public LayoutOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(LayoutOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

