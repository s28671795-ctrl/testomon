/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.migrations.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jooq.migrations.xml.jaxb.FileType;
import org.jooq.migrations.xml.jaxb.ParentType;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CommitType", propOrder={})
public class CommitType
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31500L;
    @XmlElement(required=true)
    protected String id;
    protected String message;
    @XmlElementWrapper(name="parents")
    @XmlElement(name="parent")
    protected List<ParentType> parents;
    @XmlElementWrapper(name="files")
    @XmlElement(name="file")
    protected List<FileType> files;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public List<ParentType> getParents() {
        if (this.parents == null) {
            this.parents = new ArrayList<ParentType>();
        }
        return this.parents;
    }

    public void setParents(List<ParentType> parents) {
        this.parents = parents;
    }

    public List<FileType> getFiles() {
        if (this.files == null) {
            this.files = new ArrayList<FileType>();
        }
        return this.files;
    }

    public void setFiles(List<FileType> files) {
        this.files = files;
    }

    public CommitType withId(String value) {
        this.setId(value);
        return this;
    }

    public CommitType withMessage(String value) {
        this.setMessage(value);
        return this;
    }

    public CommitType withParents(ParentType ... values2) {
        if (values2 != null) {
            for (ParentType value : values2) {
                this.getParents().add(value);
            }
        }
        return this;
    }

    public CommitType withParents(Collection<ParentType> values2) {
        if (values2 != null) {
            this.getParents().addAll(values2);
        }
        return this;
    }

    public CommitType withParents(List<ParentType> parents) {
        this.setParents(parents);
        return this;
    }

    public CommitType withFiles(FileType ... values2) {
        if (values2 != null) {
            for (FileType value : values2) {
                this.getFiles().add(value);
            }
        }
        return this;
    }

    public CommitType withFiles(Collection<FileType> values2) {
        if (values2 != null) {
            this.getFiles().addAll(values2);
        }
        return this;
    }

    public CommitType withFiles(List<FileType> files) {
        this.setFiles(files);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("id", this.id);
        builder.append("message", this.message);
        builder.append("parents", "parent", this.parents);
        builder.append("files", "file", this.files);
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
        CommitType other = (CommitType)that;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        if (this.message == null ? other.message != null : !this.message.equals(other.message)) {
            return false;
        }
        if (this.parents == null ? other.parents != null : !this.parents.equals(other.parents)) {
            return false;
        }
        return !(this.files == null ? other.files != null : !this.files.equals(other.files));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.message == null ? 0 : this.message.hashCode());
        result = 31 * result + (this.parents == null ? 0 : this.parents.hashCode());
        result = 31 * result + (this.files == null ? 0 : this.files.hashCode());
        return result;
    }
}

