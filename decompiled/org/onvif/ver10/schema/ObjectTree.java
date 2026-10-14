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
import org.onvif.ver10.schema.Merge;
import org.onvif.ver10.schema.ObjectId;
import org.onvif.ver10.schema.ObjectTreeExtension;
import org.onvif.ver10.schema.Rename;
import org.onvif.ver10.schema.Split;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ObjectTree", propOrder={"rename", "split", "merge", "delete", "extension"})
public class ObjectTree {
    @XmlElement(name="Rename")
    protected List<Rename> rename;
    @XmlElement(name="Split")
    protected List<Split> split;
    @XmlElement(name="Merge")
    protected List<Merge> merge;
    @XmlElement(name="Delete")
    protected List<ObjectId> delete;
    @XmlElement(name="Extension")
    protected ObjectTreeExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Rename> getRename() {
        if (this.rename == null) {
            this.rename = new ArrayList<Rename>();
        }
        return this.rename;
    }

    public List<Split> getSplit() {
        if (this.split == null) {
            this.split = new ArrayList<Split>();
        }
        return this.split;
    }

    public List<Merge> getMerge() {
        if (this.merge == null) {
            this.merge = new ArrayList<Merge>();
        }
        return this.merge;
    }

    public List<ObjectId> getDelete() {
        if (this.delete == null) {
            this.delete = new ArrayList<ObjectId>();
        }
        return this.delete;
    }

    public ObjectTreeExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ObjectTreeExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

