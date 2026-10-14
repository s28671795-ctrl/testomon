/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterDomain;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;

public class AlterDomainRename
extends AlterDomain {
    private String newDomainName;

    public AlterDomainRename(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setNewDomainName(String string) {
        this.newDomainName = string;
    }

    @Override
    long update(Schema schema, Domain domain) {
        Domain domain2 = schema.findDomain(this.newDomainName);
        if (domain2 != null) {
            if (domain != domain2) {
                throw DbException.get(90119, this.newDomainName);
            }
            if (this.newDomainName.equals(domain.getName())) {
                return 0L;
            }
        }
        this.getDatabase().renameSchemaObject(this.session, domain, this.newDomainName);
        AlterDomainRename.forAllDependencies(this.session, domain, null, null, false);
        return 0L;
    }

    @Override
    public int getType() {
        return 96;
    }
}

