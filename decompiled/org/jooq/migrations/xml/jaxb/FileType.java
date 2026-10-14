/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlSchemaType
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.migrations.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import org.jooq.ContentType;
import org.jooq.migrations.xml.ContentTypeAdapter;
import org.jooq.migrations.xml.jaxb.ChangeType;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FileType", propOrder={"path", "content", "contentType", "change"})
public class FileType
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31500L;
    @XmlElement(required=true)
    protected String path;
    protected String content;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=ContentTypeAdapter.class)
    protected ContentType contentType;
    @XmlElement(required=true)
    @XmlSchemaType(name="string")
    protected ChangeType change;

    public String getPath() {
        return this.path;
    }

    public void setPath(String value) {
        this.path = value;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public void setContentType(ContentType value) {
        this.contentType = value;
    }

    public ChangeType getChange() {
        return this.change;
    }

    public void setChange(ChangeType value) {
        this.change = value;
    }

    public FileType withPath(String value) {
        this.setPath(value);
        return this;
    }

    public FileType withContent(String value) {
        this.setContent(value);
        return this;
    }

    public FileType withContentType(ContentType value) {
        this.setContentType(value);
        return this;
    }

    public FileType withChange(ChangeType value) {
        this.setChange(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("path", this.path);
        builder.append("content", this.content);
        builder.append("contentType", (Object)this.contentType);
        builder.append("change", (Object)this.change);
    }

    public String toString() {
        XMLBuilder builder = XMLBuilder.nonFormatting();
        this.appendTo(builder);
        return builder.toString();
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        FileType other = (FileType)that;
        if (this.path == null ? other.path != null : !this.path.equals(other.path)) {
            return false;
        }
        if (this.content == null ? other.content != null : !this.content.equals(other.content)) {
            return false;
        }
        if (this.contentType == null ? other.contentType != null : !this.contentType.equals((Object)other.contentType)) {
            return false;
        }
        return !(this.change == null ? other.change != null : !this.change.equals((Object)other.change));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.path == null ? 0 : this.path.hashCode());
        result = 31 * result + (this.content == null ? 0 : this.content.hashCode());
        result = 31 * result + (this.contentType == null ? 0 : this.contentType.hashCode());
        result = 31 * result + (this.change == null ? 0 : this.change.hashCode());
        return result;
    }
}

