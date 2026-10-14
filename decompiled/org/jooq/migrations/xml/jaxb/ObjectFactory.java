/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.JAXBElement
 *  jakarta.xml.bind.annotation.XmlElementDecl
 *  jakarta.xml.bind.annotation.XmlRegistry
 */
package org.jooq.migrations.xml.jaxb;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.jooq.migrations.xml.jaxb.CommitType;
import org.jooq.migrations.xml.jaxb.FileType;
import org.jooq.migrations.xml.jaxb.MigrationsType;
import org.jooq.migrations.xml.jaxb.ParentType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Migrations_QNAME = new QName("http://www.jooq.org/xsd/jooq-migrations-3.15.0.xsd", "migrations");

    public MigrationsType createMigrationsType() {
        return new MigrationsType();
    }

    public CommitType createCommitType() {
        return new CommitType();
    }

    public ParentType createParentType() {
        return new ParentType();
    }

    public FileType createFileType() {
        return new FileType();
    }

    @XmlElementDecl(namespace="http://www.jooq.org/xsd/jooq-migrations-3.15.0.xsd", name="migrations")
    public JAXBElement<MigrationsType> createMigrations(MigrationsType value) {
        return new JAXBElement(_Migrations_QNAME, MigrationsType.class, null, (Object)value);
    }
}

