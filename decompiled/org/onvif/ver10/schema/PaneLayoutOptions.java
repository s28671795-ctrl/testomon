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
import org.onvif.ver10.schema.PaneOptionExtension;
import org.onvif.ver10.schema.Rectangle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PaneLayoutOptions", propOrder={"area", "extension"})
public class PaneLayoutOptions {
    @XmlElement(name="Area", required=true)
    protected List<Rectangle> area;
    @XmlElement(name="Extension")
    protected PaneOptionExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Rectangle> getArea() {
        if (this.area == null) {
            this.area = new ArrayList<Rectangle>();
        }
        return this.area;
    }

    public PaneOptionExtension getExtension() {
        return this.extension;
    }

    public void setExtension(PaneOptionExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

