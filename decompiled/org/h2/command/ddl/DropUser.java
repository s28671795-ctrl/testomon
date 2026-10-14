/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.message.DbException;

public class DropUser
extends DefineCommand {
    private boolean ifExists;
    private String userName;

    public DropUser(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setUserName(String string) {
        this.userName = string;
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        User user = database.findUser(this.userName);
        if (user == null) {
            if (!this.ifExists) {
                throw DbException.get(90032, this.userName);
            }
        } else {
            if (user == this.session.getUser()) {
                int n = 0;
                for (RightOwner rightOwner : database.getAllUsersAndRoles()) {
                    if (!(rightOwner instanceof User) || !((User)rightOwner).isAdmin()) continue;
                    ++n;
                }
                if (n == 1) {
                    throw DbException.get(90019);
                }
            }
            user.checkOwnsNoSchemas();
            database.removeDatabaseObject(this.session, user);
        }
        return 0L;
    }

    @Override
    public boolean isTransactional() {
        return false;
    }

    @Override
    public int getType() {
        return 46;
    }
}

