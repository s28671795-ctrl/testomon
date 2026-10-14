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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ItemListDescriptionExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ItemListDescription", propOrder={"simpleItemDescription", "elementItemDescription", "extension"})
public class ItemListDescription {
    @XmlElement(name="SimpleItemDescription")
    protected List<SimpleItemDescription> simpleItemDescription;
    @XmlElement(name="ElementItemDescription")
    protected List<ElementItemDescription> elementItemDescription;
    @XmlElement(name="Extension")
    protected ItemListDescriptionExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<SimpleItemDescription> getSimpleItemDescription() {
        if (this.simpleItemDescription == null) {
            this.simpleItemDescription = new ArrayList<SimpleItemDescription>();
        }
        return this.simpleItemDescription;
    }

    public List<ElementItemDescription> getElementItemDescription() {
        if (this.elementItemDescription == null) {
            this.elementItemDescription = new ArrayList<ElementItemDescription>();
        }
        return this.elementItemDescription;
    }

    public ItemListDescriptionExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ItemListDescriptionExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SimpleItemDescription {
        @XmlAttribute(name="Name", required=true)
        protected String name;
        @XmlAttribute(name="Type", required=true)
        protected QName type;

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public QName getType() {
            return this.type;
        }

        public void setType(QName value) {
            this.type = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ElementItemDescription {
        @XmlAttribute(name="Name", required=true)
        protected String name;
        @XmlAttribute(name="Type", required=true)
        protected QName type;

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public QName getType() {
            return this.type;
        }

        public void setType(QName value) {
            this.type = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

