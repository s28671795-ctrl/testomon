/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public final class Right
extends DbObject {
    public static final int SELECT = 1;
    public static final int DELETE = 2;
    public static final int INSERT = 4;
    public static final int UPDATE = 8;
    public static final int ALTER_ANY_SCHEMA = 16;
    public static final int SCHEMA_OWNER = 32;
    public static final int ALL = 15;
    private RightOwner grantee;
    private Role grantedRole;
    private int grantedRight;
    private DbObject grantedObject;

    public Right(Database database, int n, RightOwner rightOwner, Role role) {
        super(database, n, "RIGHT_" + n, 13);
        this.grantee = rightOwner;
        this.grantedRole = role;
    }

    public Right(Database database, int n, RightOwner rightOwner, int n2, DbObject dbObject) {
        super(database, n, Integer.toString(n), 13);
        this.grantee = rightOwner;
        this.grantedRight = n2;
        this.grantedObject = dbObject;
    }

    private static boolean appendRight(StringBuilder stringBuilder, int n, int n2, String string, boolean bl) {
        if ((n & n2) != 0) {
            if (bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string);
            return true;
        }
        return bl;
    }

    public String getRights() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.grantedRight == 15) {
            stringBuilder.append("ALL");
        } else {
            boolean bl = false;
            bl = Right.appendRight(stringBuilder, this.grantedRight, 1, "SELECT", bl);
            bl = Right.appendRight(stringBuilder, this.grantedRight, 2, "DELETE", bl);
            bl = Right.appendRight(stringBuilder, this.grantedRight, 4, "INSERT", bl);
            bl = Right.appendRight(stringBuilder, this.grantedRight, 8, "UPDATE", bl);
            Right.appendRight(stringBuilder, this.grantedRight, 16, "ALTER ANY SCHEMA", bl);
        }
        return stringBuilder.toString();
    }

    public Role getGrantedRole() {
        return this.grantedRole;
    }

    public DbObject getGrantedObject() {
        return this.grantedObject;
    }

    public DbObject getGrantee() {
        return this.grantee;
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        return this.getCreateSQLForCopy(table);
    }

    private String getCreateSQLForCopy(DbObject dbObject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GRANT ");
        if (this.grantedRole != null) {
            this.grantedRole.getSQL(stringBuilder, 0);
        } else {
            stringBuilder.append(this.getRights());
            if (dbObject != null) {
                if (dbObject instanceof Schema) {
                    stringBuilder.append(" ON SCHEMA ");
                    dbObject.getSQL(stringBuilder, 0);
                } else if (dbObject instanceof Table) {
                    stringBuilder.append(" ON ");
                    dbObject.getSQL(stringBuilder, 0);
                }
            }
        }
        stringBuilder.append(" TO ");
        this.grantee.getSQL(stringBuilder, 0);
        return stringBuilder.toString();
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQLForCopy(this.grantedObject);
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        if (this.grantedRole != null) {
            this.grantee.revokeRole(this.grantedRole);
        } else {
            this.grantee.revokeRight(this.grantedObject);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        this.grantedRole = null;
        this.grantedObject = null;
        this.grantee = null;
        this.invalidate();
    }

    @Override
    public void checkRename() {
        throw DbException.getInternalError();
    }

    public void setRightMask(int n) {
        this.grantedRight = n;
    }

    public int getRightMask() {
        return this.grantedRight;
    }
}

