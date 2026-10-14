/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.ArrayList;
import java.util.Arrays;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.security.SHA256;
import org.h2.table.DualTable;
import org.h2.table.MetaTable;
import org.h2.table.RangeTable;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public final class User
extends RightOwner {
    private final boolean systemUser;
    private byte[] salt;
    private byte[] passwordHash;
    private boolean admin;

    public User(Database database, int n, String string, boolean bl) {
        super(database, n, string, 13);
        this.systemUser = bl;
    }

    public void setAdmin(boolean bl) {
        this.admin = bl;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setSaltAndHash(byte[] byArray, byte[] byArray2) {
        this.salt = byArray;
        this.passwordHash = byArray2;
    }

    public void setUserPasswordHash(byte[] byArray) {
        if (byArray != null) {
            if (byArray.length == 0) {
                this.passwordHash = byArray;
                this.salt = byArray;
            } else {
                this.salt = new byte[8];
                MathUtils.randomBytes(this.salt);
                this.passwordHash = SHA256.getHashWithSalt(byArray, this.salt);
            }
        }
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQL(true);
    }

    public String getCreateSQL(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("CREATE USER IF NOT EXISTS ");
        this.getSQL(stringBuilder, 0);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        if (bl) {
            stringBuilder.append(" SALT '");
            StringUtils.convertBytesToHex(stringBuilder, this.salt).append("' HASH '");
            StringUtils.convertBytesToHex(stringBuilder, this.passwordHash).append('\'');
        } else {
            stringBuilder.append(" PASSWORD ''");
        }
        if (this.admin) {
            stringBuilder.append(" ADMIN");
        }
        return stringBuilder.toString();
    }

    boolean validateUserPasswordHash(byte[] byArray) {
        if (byArray.length == 0 && this.passwordHash.length == 0) {
            return true;
        }
        if (byArray.length == 0) {
            byArray = SHA256.getKeyPasswordHash(this.getName(), new char[0]);
        }
        byte[] byArray2 = SHA256.getHashWithSalt(byArray, this.salt);
        return Utils.compareSecure(byArray2, this.passwordHash);
    }

    public void checkAdmin() {
        if (!this.admin) {
            throw DbException.get(90040);
        }
    }

    public void checkSchemaAdmin() {
        if (!this.hasSchemaRight(null)) {
            throw DbException.get(90040);
        }
    }

    public void checkSchemaOwner(Schema schema) {
        if (!this.hasSchemaRight(schema)) {
            throw DbException.get(90096, schema.getTraceSQL());
        }
    }

    private boolean hasSchemaRight(Schema schema) {
        if (this.admin) {
            return true;
        }
        Role role = this.database.getPublicRole();
        if (role.isSchemaRightGrantedRecursive(schema)) {
            return true;
        }
        return this.isSchemaRightGrantedRecursive(schema);
    }

    public void checkTableRight(Table table, int n) {
        if (!this.hasTableRight(table, n)) {
            throw DbException.get(90096, table.getTraceSQL());
        }
    }

    public boolean hasTableRight(Table table, int n) {
        if (n != 1 && !this.systemUser) {
            table.checkWritingAllowed();
        }
        if (this.admin) {
            return true;
        }
        Role role = this.database.getPublicRole();
        if (role.isTableRightGrantedRecursive(table, n)) {
            return true;
        }
        if (table instanceof MetaTable || table instanceof DualTable || table instanceof RangeTable) {
            return true;
        }
        TableType tableType = table.getTableType();
        if (tableType == null) {
            return true;
        }
        if (table.isTemporary() && !table.isGlobalTemporary()) {
            return true;
        }
        return this.isTableRightGrantedRecursive(table, n);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public ArrayList<DbObject> getChildren() {
        ArrayList<DbObject> arrayList = new ArrayList<DbObject>();
        for (Right dbObject : this.database.getAllRights()) {
            if (dbObject.getGrantee() != this) continue;
            arrayList.add(dbObject);
        }
        for (Schema schema : this.database.getAllSchemas()) {
            if (schema.getOwner() != this) continue;
            arrayList.add(schema);
        }
        return arrayList;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        for (Right right : this.database.getAllRights()) {
            if (right.getGrantee() != this) continue;
            this.database.removeDatabaseObject(sessionLocal, right);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        this.salt = null;
        Arrays.fill(this.passwordHash, (byte)0);
        this.passwordHash = null;
        this.invalidate();
    }
}

