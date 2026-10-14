/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlRegistry
 */
package org.jooq.util.xml.jaxb;

import jakarta.xml.bind.annotation.XmlRegistry;
import org.jooq.util.xml.jaxb.Catalog;
import org.jooq.util.xml.jaxb.CheckConstraint;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.Domain;
import org.jooq.util.xml.jaxb.DomainConstraint;
import org.jooq.util.xml.jaxb.ElementType;
import org.jooq.util.xml.jaxb.Index;
import org.jooq.util.xml.jaxb.IndexColumnUsage;
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.Parameter;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.Routine;
import org.jooq.util.xml.jaxb.Schema;
import org.jooq.util.xml.jaxb.Sequence;
import org.jooq.util.xml.jaxb.Table;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.View;

@XmlRegistry
public class ObjectFactory {
    public InformationSchema createInformationSchema() {
        return new InformationSchema();
    }

    public Catalog createCatalog() {
        return new Catalog();
    }

    public CheckConstraint createCheckConstraint() {
        return new CheckConstraint();
    }

    public Column createColumn() {
        return new Column();
    }

    public DomainConstraint createDomainConstraint() {
        return new DomainConstraint();
    }

    public Domain createDomain() {
        return new Domain();
    }

    public ElementType createElementType() {
        return new ElementType();
    }

    public IndexColumnUsage createIndexColumnUsage() {
        return new IndexColumnUsage();
    }

    public Index createIndex() {
        return new Index();
    }

    public KeyColumnUsage createKeyColumnUsage() {
        return new KeyColumnUsage();
    }

    public Parameter createParameter() {
        return new Parameter();
    }

    public ReferentialConstraint createReferentialConstraint() {
        return new ReferentialConstraint();
    }

    public Routine createRoutine() {
        return new Routine();
    }

    public Schema createSchema() {
        return new Schema();
    }

    public Sequence createSequence() {
        return new Sequence();
    }

    public TableConstraint createTableConstraint() {
        return new TableConstraint();
    }

    public Table createTable() {
        return new Table();
    }

    public View createView() {
        return new View();
    }
}

