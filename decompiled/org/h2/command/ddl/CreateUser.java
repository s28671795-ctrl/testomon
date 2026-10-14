/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.security.SHA256;
import org.h2.util.StringUtils;
import org.h2.value.DataType;
import org.h2.value.Value;

public class CreateUser
extends DefineCommand {
    private String userName;
    private boolean admin;
    private Expression password;
    private Expression salt;
    private Expression hash;
    private boolean ifNotExists;
    private String comment;

    public CreateUser(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setUserName(String string) {
        this.userName = string;
    }

    public void setPassword(Expression expression) {
        this.password = expression;
    }

    static void setSaltAndHash(User user, SessionLocal sessionLocal, Expression expression, Expression expression2) {
        user.setSaltAndHash(CreateUser.getByteArray(sessionLocal, expression), CreateUser.getByteArray(sessionLocal, expression2));
    }

    private static byte[] getByteArray(SessionLocal sessionLocal, Expression expression) {
        Value value = expression.optimize(sessionLocal).getValue(sessionLocal);
        if (DataType.isBinaryStringType(value.getValueType())) {
            byte[] byArray = value.getBytes();
            return byArray == null ? new byte[]{} : byArray;
        }
        String string = value.getString();
        return string == null ? new byte[]{} : StringUtils.convertHexToBytes(string);
    }

    static void setPassword(User user, SessionLocal sessionLocal, Expression expression) {
        String string = expression.optimize(sessionLocal).getValue(sessionLocal).getString();
        char[] cArray = string == null ? new char[]{} : string.toCharArray();
        String string2 = user.getName();
        byte[] byArray = string2.isEmpty() && cArray.length == 0 ? new byte[]{} : SHA256.getKeyPasswordHash(string2, cArray);
        user.setUserPasswordHash(byArray);
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        RightOwner rightOwner = database.findUserOrRole(this.userName);
        if (rightOwner != null) {
            if (rightOwner instanceof User) {
                if (this.ifNotExists) {
                    return 0L;
                }
                throw DbException.get(90033, this.userName);
            }
            throw DbException.get(90069, this.userName);
        }
        int n = this.getObjectId();
        User user = new User(database, n, this.userName, false);
        user.setAdmin(this.admin);
        user.setComment(this.comment);
        if (this.hash != null && this.salt != null) {
            CreateUser.setSaltAndHash(user, this.session, this.salt, this.hash);
        } else if (this.password != null) {
            CreateUser.setPassword(user, this.session, this.password);
        } else {
            throw DbException.getInternalError();
        }
        database.addDatabaseObject(this.session, user);
        return 0L;
    }

    public void setSalt(Expression expression) {
        this.salt = expression;
    }

    public void setHash(Expression expression) {
        this.hash = expression;
    }

    public void setAdmin(boolean bl) {
        this.admin = bl;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    @Override
    public int getType() {
        return 32;
    }
}

