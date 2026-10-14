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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FrameExtension;
import org.onvif.ver10.schema.Object;
import org.onvif.ver10.schema.ObjectTree;
import org.onvif.ver10.schema.PTZStatus;
import org.onvif.ver10.schema.Transformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Frame", propOrder={"ptzStatus", "transformation", "object", "objectTree", "extension"})
public class Frame {
    @XmlElement(name="PTZStatus")
    protected PTZStatus ptzStatus;
    @XmlElement(name="Transformation")
    protected Transformation transformation;
    @XmlElement(name="Object")
    protected List<Object> object;
    @XmlElement(name="ObjectTree")
    protected ObjectTree objectTree;
    @XmlElement(name="Extension")
    protected FrameExtension extension;
    @XmlAttribute(name="UtcTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar utcTime;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZStatus getPTZStatus() {
        return this.ptzStatus;
    }

    public void setPTZStatus(PTZStatus value) {
        this.ptzStatus = value;
    }

    public Transformation getTransformation() {
        return this.transformation;
    }

    public void setTransformation(Transformation value) {
        this.transformation = value;
    }

    public List<Object> getObject() {
        if (this.object == null) {
            this.object = new ArrayList<Object>();
        }
        return this.object;
    }

    public ObjectTree getObjectTree() {
        return this.objectTree;
    }

    public void setObjectTree(ObjectTree value) {
        this.objectTree = value;
    }

    public FrameExtension getExtension() {
        return this.extension;
    }

    public void setExtension(FrameExtension value) {
        this.extension = value;
    }

    public XMLGregorianCalendar getUtcTime() {
        return this.utcTime;
    }

    public void setUtcTime(XMLGregorianCalendar value) {
        this.utcTime = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

