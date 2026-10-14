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
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RegexAdapter;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MappedCatalog", propOrder={})
public class MappedCatalog
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
    @XmlElementWrapper(name="schemata")
    @XmlElement(name="schema")
    protected List<MappedSchema> schemata;

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

    public List<MappedSchema> getSchemata() {
        if (this.schemata == null) {
            this.schemata = new ArrayList<MappedSchema>();
        }
        return this.schemata;
    }

    public void setSchemata(List<MappedSchema> schemata) {
        this.schemata = schemata;
    }

    public MappedCatalog withInput(String value) {
        this.setInput(value);
        return this;
    }

    public MappedCatalog withInputExpression(Pattern value) {
        this.setInputExpression(value);
        return this;
    }

    public MappedCatalog withOutput(String value) {
        this.setOutput(value);
        return this;
    }

    public MappedCatalog withSchemata(MappedSchema ... values2) {
        if (values2 != null) {
            for (MappedSchema value : values2) {
                this.getSchemata().add(value);
            }
        }
        return this;
    }

    public MappedCatalog withSchemata(Collection<MappedSchema> values2) {
        if (values2 != null) {
            this.getSchemata().addAll(values2);
        }
        return this;
    }

    public MappedCatalog withSchemata(List<MappedSchema> schemata) {
        this.setSchemata(schemata);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("input", this.input);
        builder.append("inputExpression", this.inputExpression);
        builder.append("output", this.output);
        builder.append("schemata", "schema", this.schemata);
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
        MappedCatalog other = (MappedCatalog)that;
        if (this.input == null ? other.input != null : !this.input.equals(other.input)) {
            return false;
        }
        if (this.inputExpression == null ? other.inputExpression != null : !this.inputExpression.pattern().equals(other.inputExpression.pattern())) {
            return false;
        }
        if (this.output == null ? other.output != null : !this.output.equals(other.output)) {
            return false;
        }
        return !(this.schemata == null ? other.schemata != null : !this.schemata.equals(other.schemata));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.input == null ? 0 : this.input.hashCode());
        result = 31 * result + (this.inputExpression == null ? 0 : this.inputExpression.pattern().hashCode());
        result = 31 * result + (this.output == null ? 0 : this.output.hashCode());
        result = 31 * result + (this.schemata == null ? 0 : this.schemata.hashCode());
        return result;
    }
}

