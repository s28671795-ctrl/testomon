/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.JAXBElement
 *  jakarta.xml.bind.annotation.XmlElementDecl
 *  jakarta.xml.bind.annotation.XmlRegistry
 */
package org.jooq.conf;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.jooq.conf.InterpreterSearchSchema;
import org.jooq.conf.MappedCatalog;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.MigrationSchema;
import org.jooq.conf.ParseSearchSchema;
import org.jooq.conf.RenderFormatting;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Settings_QNAME = new QName("http://www.jooq.org/xsd/jooq-runtime-3.18.0.xsd", "settings");

    public Settings createSettings() {
        return new Settings();
    }

    public ParseSearchSchema createParseSearchSchema() {
        return new ParseSearchSchema();
    }

    public InterpreterSearchSchema createInterpreterSearchSchema() {
        return new InterpreterSearchSchema();
    }

    public MigrationSchema createMigrationSchema() {
        return new MigrationSchema();
    }

    public RenderMapping createRenderMapping() {
        return new RenderMapping();
    }

    public MappedCatalog createMappedCatalog() {
        return new MappedCatalog();
    }

    public MappedSchema createMappedSchema() {
        return new MappedSchema();
    }

    public MappedTable createMappedTable() {
        return new MappedTable();
    }

    public RenderFormatting createRenderFormatting() {
        return new RenderFormatting();
    }

    @XmlElementDecl(namespace="http://www.jooq.org/xsd/jooq-runtime-3.18.0.xsd", name="settings")
    public JAXBElement<Settings> createSettings(Settings value) {
        return new JAXBElement(_Settings_QNAME, Settings.class, null, (Object)value);
    }
}

