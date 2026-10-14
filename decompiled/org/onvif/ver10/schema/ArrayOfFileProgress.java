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
import org.onvif.ver10.schema.ArrayOfFileProgressExtension;
import org.onvif.ver10.schema.FileProgress;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ArrayOfFileProgress", propOrder={"fileProgress", "extension"})
public class ArrayOfFileProgress {
    @XmlElement(name="FileProgress")
    protected List<FileProgress> fileProgress;
    @XmlElement(name="Extension")
    protected ArrayOfFileProgressExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<FileProgress> getFileProgress() {
        if (this.fileProgress == null) {
            this.fileProgress = new ArrayList<FileProgress>();
        }
        return this.fileProgress;
    }

    public ArrayOfFileProgressExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ArrayOfFileProgressExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

