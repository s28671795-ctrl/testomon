/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RenderFormatting", propOrder={})
public class RenderFormatting
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31800L;
    @XmlElement(defaultValue="\n")
    protected String newline = "\n";
    @XmlElement(defaultValue="  ")
    protected String indentation = "  ";
    @XmlElement(defaultValue="80")
    protected Integer printMargin = 80;

    public String getNewline() {
        return this.newline;
    }

    public void setNewline(String value) {
        this.newline = value;
    }

    public String getIndentation() {
        return this.indentation;
    }

    public void setIndentation(String value) {
        this.indentation = value;
    }

    public Integer getPrintMargin() {
        return this.printMargin;
    }

    public void setPrintMargin(Integer value) {
        this.printMargin = value;
    }

    public RenderFormatting withNewline(String value) {
        this.setNewline(value);
        return this;
    }

    public RenderFormatting withIndentation(String value) {
        this.setIndentation(value);
        return this;
    }

    public RenderFormatting withPrintMargin(Integer value) {
        this.setPrintMargin(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("newline", this.newline);
        builder.append("indentation", this.indentation);
        builder.append("printMargin", this.printMargin);
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
        RenderFormatting other = (RenderFormatting)that;
        if (this.newline == null ? other.newline != null : !this.newline.equals(other.newline)) {
            return false;
        }
        if (this.indentation == null ? other.indentation != null : !this.indentation.equals(other.indentation)) {
            return false;
        }
        return !(this.printMargin == null ? other.printMargin != null : !this.printMargin.equals(other.printMargin));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.newline == null ? 0 : this.newline.hashCode());
        result = 31 * result + (this.indentation == null ? 0 : this.indentation.hashCode());
        result = 31 * result + (this.printMargin == null ? 0 : this.printMargin.hashCode());
        return result;
    }
}

