/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.ArrayList;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.schema.Schema;

public final class Role
extends RightOwner {
    private final boolean system;

    public Role(Database database, int n, String string, boolean bl) {
        super(database, n, string, 13);
        this.system = bl;
    }

    public String getCreateSQL(boolean bl) {
        if (this.system) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("CREATE ROLE ");
        if (bl) {
            stringBuilder.append("IF NOT EXISTS ");
        }
        return this.getSQL(stringBuilder, 0).toString();
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQL(false);
    }

    @Override
    public int getType() {
        return 7;
    }

    @Override
    public ArrayList<DbObject> getChildren() {
        ArrayList<DbObject> arrayList = new ArrayList<DbObject>();
        for (Schema schema : this.database.getAllSchemas()) {
            if (schema.getOwner() != this) continue;
            arrayList.add(schema);
        }
        return arrayList;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        for (RightOwner dbObject : this.database.getAllUsersAndRoles()) {
            Right right = dbObject.getRightForRole(this);
            if (right == null) continue;
            this.database.removeDatabaseObject(sessionLocal, right);
        }
        for (Right right : this.database.getAllRights()) {
            if (right.getGrantee() != this) continue;
            this.database.removeDatabaseObject(sessionLocal, right);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        this.invalidate();
    }
}

