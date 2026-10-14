/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Appearance;
import org.onvif.ver10.schema.Behaviour;
import org.onvif.ver10.schema.ObjectExtension;
import org.onvif.ver10.schema.ObjectId;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Object", propOrder={"appearance", "behaviour", "extension"})
public class Object
extends ObjectId {
    @XmlElement(name="Appearance")
    protected Appearance appearance;
    @XmlElement(name="Behaviour")
    protected Behaviour behaviour;
    @XmlElement(name="Extension")
    protected ObjectExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Appearance getAppearance() {
        return this.appearance;
    }

    public void setAppearance(Appearance value) {
        this.appearance = value;
    }

    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    public void setBehaviour(Behaviour value) {
        this.behaviour = value;
    }

    public ObjectExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ObjectExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

