/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;

public class CreateRole
extends DefineCommand {
    private String roleName;
    private boolean ifNotExists;

    public CreateRole(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setRoleName(String string) {
        this.roleName = string;
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        RightOwner rightOwner = database.findUserOrRole(this.roleName);
        if (rightOwner != null) {
            if (rightOwner instanceof Role) {
                if (this.ifNotExists) {
                    return 0L;
                }
                throw DbException.get(90069, this.roleName);
            }
            throw DbException.get(90033, this.roleName);
        }
        int n = this.getObjectId();
        Role role = new Role(database, n, this.roleName, false);
        database.addDatabaseObject(this.session, role);
        return 0L;
    }

    @Override
    public int getType() {
        return 27;
    }
}

