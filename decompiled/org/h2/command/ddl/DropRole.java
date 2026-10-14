/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;

public class DropRole
extends DefineCommand {
    private String roleName;
    private boolean ifExists;

    public DropRole(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setRoleName(String string) {
        this.roleName = string;
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        Role role = database.findRole(this.roleName);
        if (role == null) {
            if (!this.ifExists) {
                throw DbException.get(90070, this.roleName);
            }
        } else {
            if (role == database.getPublicRole()) {
                throw DbException.get(90091, this.roleName);
            }
            role.checkOwnsNoSchemas();
            database.removeDatabaseObject(this.session, role);
        }
        return 0L;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    @Override
    public int getType() {
        return 41;
    }
}

