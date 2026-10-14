/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.Role;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;
import org.h2.util.StringUtils;

public abstract class RightOwner
extends DbObject {
    private HashMap<Role, Right> grantedRoles;
    private HashMap<DbObject, Right> grantedRights;

    protected RightOwner(Database database, int n, String string, int n2) {
        super(database, n, StringUtils.toUpperEnglish(string), n2);
    }

    @Override
    public void rename(String string) {
        super.rename(StringUtils.toUpperEnglish(string));
    }

    public boolean isRoleGranted(Role role) {
        if (role == this) {
            return true;
        }
        if (this.grantedRoles != null) {
            for (Role role2 : this.grantedRoles.keySet()) {
                if (role2 == role) {
                    return true;
                }
                if (!role2.isRoleGranted(role)) continue;
                return true;
            }
        }
        return false;
    }

    final boolean isTableRightGrantedRecursive(Table table, int n) {
        Schema schema = table.getSchema();
        if (schema.getOwner() == this) {
            return true;
        }
        if (this.grantedRights != null) {
            Object object = this.grantedRights.get(null);
            if (object != null && (((Right)object).getRightMask() & 0x10) == 16) {
                return true;
            }
            object = this.grantedRights.get(schema);
            if (object != null && (((Right)object).getRightMask() & n) == n) {
                return true;
            }
            object = this.grantedRights.get(table);
            if (object != null && (((Right)object).getRightMask() & n) == n) {
                return true;
            }
        }
        if (this.grantedRoles != null) {
            for (Role role : this.grantedRoles.keySet()) {
                if (!role.isTableRightGrantedRecursive(table, n)) continue;
                return true;
            }
        }
        return false;
    }

    final boolean isSchemaRightGrantedRecursive(Schema schema) {
        Object object;
        if (schema != null && schema.getOwner() == this) {
            return true;
        }
        if (this.grantedRights != null && (object = this.grantedRights.get(null)) != null && (((Right)object).getRightMask() & 0x10) == 16) {
            return true;
        }
        if (this.grantedRoles != null) {
            for (Role role : this.grantedRoles.keySet()) {
                if (!role.isSchemaRightGrantedRecursive(schema)) continue;
                return true;
            }
        }
        return false;
    }

    public void grantRight(DbObject dbObject, Right right) {
        if (this.grantedRights == null) {
            this.grantedRights = new HashMap();
        }
        this.grantedRights.put(dbObject, right);
    }

    void revokeRight(DbObject dbObject) {
        if (this.grantedRights == null) {
            return;
        }
        this.grantedRights.remove(dbObject);
        if (this.grantedRights.size() == 0) {
            this.grantedRights = null;
        }
    }

    public void grantRole(Role role, Right right) {
        if (this.grantedRoles == null) {
            this.grantedRoles = new HashMap();
        }
        this.grantedRoles.put(role, right);
    }

    void revokeRole(Role role) {
        if (this.grantedRoles == null) {
            return;
        }
        Right right = this.grantedRoles.get(role);
        if (right == null) {
            return;
        }
        this.grantedRoles.remove(role);
        if (this.grantedRoles.size() == 0) {
            this.grantedRoles = null;
        }
    }

    public void revokeTemporaryRightsOnRoles() {
        if (this.grantedRoles == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Role, Right> entry : this.grantedRoles.entrySet()) {
            if (!entry.getValue().isTemporary() && entry.getValue().isValid()) continue;
            arrayList.add(entry.getKey());
        }
        for (Map.Entry<Role, Right> entry : arrayList) {
            this.revokeRole((Role)((Object)entry));
        }
    }

    public Right getRightForObject(DbObject dbObject) {
        if (this.grantedRights == null) {
            return null;
        }
        return this.grantedRights.get(dbObject);
    }

    public Right getRightForRole(Role role) {
        if (this.grantedRoles == null) {
            return null;
        }
        return this.grantedRoles.get(role);
    }

    public final void checkOwnsNoSchemas() {
        for (Schema schema : this.database.getAllSchemas()) {
            if (this != schema.getOwner()) continue;
            throw DbException.get(90107, this.getName(), schema.getName());
        }
    }
}

