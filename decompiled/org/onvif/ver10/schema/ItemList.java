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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ItemListExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ItemList", propOrder={"simpleItem", "elementItem", "extension"})
public class ItemList {
    @XmlElement(name="SimpleItem")
    protected List<SimpleItem> simpleItem;
    @XmlElement(name="ElementItem")
    protected List<ElementItem> elementItem;
    @XmlElement(name="Extension")
    protected ItemListExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<SimpleItem> getSimpleItem() {
        if (this.simpleItem == null) {
            this.simpleItem = new ArrayList<SimpleItem>();
        }
        return this.simpleItem;
    }

    public List<ElementItem> getElementItem() {
        if (this.elementItem == null) {
            this.elementItem = new ArrayList<ElementItem>();
        }
        return this.elementItem;
    }

    public ItemListExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ItemListExtension value) {
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
    public static class SimpleItem {
        @XmlAttribute(name="Name", required=true)
        protected String name;
        @XmlAttribute(name="Value", required=true)
        @XmlSchemaType(name="anySimpleType")
        protected String value;

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class ElementItem {
        @XmlAnyElement(lax=true)
        protected Object any;
        @XmlAttribute(name="Name", required=true)
        protected String name;

        public Object getAny() {
            return this.any;
        }

        public void setAny(Object value) {
            this.any = value;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

