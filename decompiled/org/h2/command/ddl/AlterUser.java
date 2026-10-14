/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.CreateUser;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.expression.Expression;
import org.h2.message.DbException;

public class AlterUser
extends DefineCommand {
    private int type;
    private User user;
    private String newName;
    private Expression password;
    private Expression salt;
    private Expression hash;
    private boolean admin;

    public AlterUser(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setType(int n) {
        this.type = n;
    }

    public void setNewName(String string) {
        this.newName = string;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAdmin(boolean bl) {
        this.admin = bl;
    }

    public void setSalt(Expression expression) {
        this.salt = expression;
    }

    public void setHash(Expression expression) {
        this.hash = expression;
    }

    public void setPassword(Expression expression) {
        this.password = expression;
    }

    @Override
    public long update() {
        Database database = this.getDatabase();
        switch (this.type) {
            case 19: {
                if (this.user != this.session.getUser()) {
                    this.session.getUser().checkAdmin();
                }
                if (this.hash != null && this.salt != null) {
                    CreateUser.setSaltAndHash(this.user, this.session, this.salt, this.hash);
                    break;
                }
                CreateUser.setPassword(this.user, this.session, this.password);
                break;
            }
            case 18: {
                this.session.getUser().checkAdmin();
                if (database.findUser(this.newName) != null || this.newName.equals(this.user.getName())) {
                    throw DbException.get(90033, this.newName);
                }
                database.renameDatabaseObject(this.session, this.user, this.newName);
                break;
            }
            case 17: {
                this.session.getUser().checkAdmin();
                this.user.setAdmin(this.admin);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        database.updateMeta(this.session, this.user);
        return 0L;
    }

    @Override
    public int getType() {
        return this.type;
    }
}

