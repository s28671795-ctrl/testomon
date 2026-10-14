/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.function.BiPredicate;
import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;

public abstract class AlterDomain
extends SchemaOwnerCommand {
    String domainName;
    boolean ifDomainExists;

    public static void forAllDependencies(SessionLocal sessionLocal, Domain domain, BiPredicate<Domain, Column> biPredicate, BiPredicate<Domain, Domain> biPredicate2, boolean bl) {
        Database database = sessionLocal.getDatabase();
        for (Schema dbObject : database.getAllSchemasNoMeta()) {
            for (Domain domain2 : dbObject.getAllDomains()) {
                if (domain2.getDomain() != domain || biPredicate2 != null && !biPredicate2.test(domain, domain2)) continue;
                if (bl) {
                    domain.prepareExpressions(sessionLocal);
                }
                database.updateMeta(sessionLocal, domain2);
            }
            for (Table table : dbObject.getAllTablesAndViews(null)) {
                if (!AlterDomain.forTable(sessionLocal, domain, biPredicate, bl, table)) continue;
                database.updateMeta(sessionLocal, table);
            }
        }
        for (Table table : sessionLocal.getLocalTempTables()) {
            AlterDomain.forTable(sessionLocal, domain, biPredicate, bl, table);
        }
    }

    private static boolean forTable(SessionLocal sessionLocal, Domain domain, BiPredicate<Domain, Column> biPredicate, boolean bl, Table table) {
        boolean bl2 = false;
        for (Column column : table.getColumns()) {
            boolean bl3;
            if (column.getDomain() != domain) continue;
            boolean bl4 = bl3 = biPredicate == null || biPredicate.test(domain, column);
            if (!bl3) continue;
            if (bl) {
                column.prepareExpressions(sessionLocal);
            }
            bl2 = true;
        }
        return bl2;
    }

    AlterDomain(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public final void setDomainName(String string) {
        this.domainName = string;
    }

    public final void setIfDomainExists(boolean bl) {
        this.ifDomainExists = bl;
    }

    @Override
    final long update(Schema schema) {
        Domain domain = this.getSchema().findDomain(this.domainName);
        if (domain == null) {
            if (this.ifDomainExists) {
                return 0L;
            }
            throw DbException.get(90120, this.domainName);
        }
        return this.update(schema, domain);
    }

    abstract long update(Schema var1, Domain var2);
}

