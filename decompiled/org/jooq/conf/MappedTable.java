/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.regex.Pattern;
import org.jooq.conf.RegexAdapter;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MappedTable", propOrder={})
public class MappedTable
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31800L;
    protected String input;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=RegexAdapter.class)
    protected Pattern inputExpression;
    @XmlElement(required=true)
    protected String output;

    public String getInput() {
        return this.input;
    }

    public void setInput(String value) {
        this.input = value;
    }

    public Pattern getInputExpression() {
        return this.inputExpression;
    }

    public void setInputExpression(Pattern value) {
        this.inputExpression = value;
    }

    public String getOutput() {
        return this.output;
    }

    public void setOutput(String value) {
        this.output = value;
    }

    public MappedTable withInput(String value) {
        this.setInput(value);
        return this;
    }

    public MappedTable withInputExpression(Pattern value) {
        this.setInputExpression(value);
        return this;
    }

    public MappedTable withOutput(String value) {
        this.setOutput(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("input", this.input);
        builder.append("inputExpression", this.inputExpression);
        builder.append("output", this.output);
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
        MappedTable other = (MappedTable)that;
        if (this.input == null ? other.input != null : !this.input.equals(other.input)) {
            return false;
        }
        if (this.inputExpression == null ? other.inputExpression != null : !this.inputExpression.pattern().equals(other.inputExpression.pattern())) {
            return false;
        }
        return !(this.output == null ? other.output != null : !this.output.equals(other.output));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.input == null ? 0 : this.input.hashCode());
        result = 31 * result + (this.inputExpression == null ? 0 : this.inputExpression.pattern().hashCode());
        result = 31 * result + (this.output == null ? 0 : this.output.hashCode());
        return result;
    }
}

