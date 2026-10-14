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
import org.onvif.ver10.schema.LayoutExtension;
import org.onvif.ver10.schema.PaneLayout;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Layout", propOrder={"paneLayout", "extension"})
public class Layout {
    @XmlElement(name="PaneLayout", required=true)
    protected List<PaneLayout> paneLayout;
    @XmlElement(name="Extension")
    protected LayoutExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<PaneLayout> getPaneLayout() {
        if (this.paneLayout == null) {
            this.paneLayout = new ArrayList<PaneLayout>();
        }
        return this.paneLayout;
    }

    public LayoutExtension getExtension() {
        return this.extension;
    }

    public void setExtension(LayoutExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

