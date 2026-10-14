/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RegexAdapter;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MappedSchema", propOrder={})
public class MappedSchema
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31800L;
    protected String input;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=RegexAdapter.class)
    protected Pattern inputExpression;
    protected String output;
    @XmlElementWrapper(name="tables")
    @XmlElement(name="table")
    protected List<MappedTable> tables;

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

    public List<MappedTable> getTables() {
        if (this.tables == null) {
            this.tables = new ArrayList<MappedTable>();
        }
        return this.tables;
    }

    public void setTables(List<MappedTable> tables) {
        this.tables = tables;
    }

    public MappedSchema withInput(String value) {
        this.setInput(value);
        return this;
    }

    public MappedSchema withInputExpression(Pattern value) {
        this.setInputExpression(value);
        return this;
    }

    public MappedSchema withOutput(String value) {
        this.setOutput(value);
        return this;
    }

    public MappedSchema withTables(MappedTable ... values2) {
        if (values2 != null) {
            for (MappedTable value : values2) {
                this.getTables().add(value);
            }
        }
        return this;
    }

    public MappedSchema withTables(Collection<MappedTable> values2) {
        if (values2 != null) {
            this.getTables().addAll(values2);
        }
        return this;
    }

    public MappedSchema withTables(List<MappedTable> tables) {
        this.setTables(tables);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("input", this.input);
        builder.append("inputExpression", this.inputExpression);
        builder.append("output", this.output);
        builder.append("tables", "table", this.tables);
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
        MappedSchema other = (MappedSchema)that;
        if (this.input == null ? other.input != null : !this.input.equals(other.input)) {
            return false;
        }
        if (this.inputExpression == null ? other.inputExpression != null : !this.inputExpression.pattern().equals(other.inputExpression.pattern())) {
            return false;
        }
        if (this.output == null ? other.output != null : !this.output.equals(other.output)) {
            return false;
        }
        return !(this.tables == null ? other.tables != null : !this.tables.equals(other.tables));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.input == null ? 0 : this.input.hashCode());
        result = 31 * result + (this.inputExpression == null ? 0 : this.inputExpression.pattern().hashCode());
        result = 31 * result + (this.output == null ? 0 : this.output.hashCode());
        result = 31 * result + (this.tables == null ? 0 : this.tables.hashCode());
        return result;
    }
}

