/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Vector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Polyline", propOrder={"point"})
public class Polyline {
    @XmlElement(name="Point", required=true)
    protected List<Vector> point;

    public List<Vector> getPoint() {
        if (this.point == null) {
            this.point = new ArrayList<Vector>();
        }
        return this.point;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

