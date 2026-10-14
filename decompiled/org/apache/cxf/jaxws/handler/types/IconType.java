/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.cxf.jaxws.handler.types.PathType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="iconType", propOrder={"smallIcon", "largeIcon"})
public class IconType {
    @XmlElement(name="small-icon")
    protected PathType smallIcon;
    @XmlElement(name="large-icon")
    protected PathType largeIcon;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute(namespace="http://www.w3.org/XML/1998/namespace")
    protected String lang;

    public PathType getSmallIcon() {
        return this.smallIcon;
    }

    public void setSmallIcon(PathType value) {
        this.smallIcon = value;
    }

    public PathType getLargeIcon() {
        return this.largeIcon;
    }

    public void setLargeIcon(PathType value) {
        this.largeIcon = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String value) {
        this.lang = value;
    }
}

