/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;
import org.h2.util.Utils;

public class GrantRevoke
extends DefineCommand {
    private ArrayList<String> roleNames;
    private int operationType;
    private int rightMask;
    private final ArrayList<Table> tables = Utils.newSmallArrayList();
    private Schema schema;
    private RightOwner grantee;

    public GrantRevoke(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setOperationType(int n) {
        this.operationType = n;
    }

    public void addRight(int n) {
        this.rightMask |= n;
    }

    public void addRoleName(String string) {
        if (this.roleNames == null) {
            this.roleNames = Utils.newSmallArrayList();
        }
        this.roleNames.add(string);
    }

    public void setGranteeName(String string) {
        Database database = this.getDatabase();
        this.grantee = database.findUserOrRole(string);
        if (this.grantee == null) {
            throw DbException.get(90071, string);
        }
    }

    @Override
    public long update() {
        Database database = this.getDatabase();
        User user = this.session.getUser();
        if (this.roleNames != null) {
            user.checkAdmin();
            for (String string : this.roleNames) {
                Role role = database.findRole(string);
                if (role == null) {
                    throw DbException.get(90070, string);
                }
                if (this.operationType == 49) {
                    this.grantRole(role);
                    continue;
                }
                if (this.operationType == 50) {
                    this.revokeRole(role);
                    continue;
                }
                throw DbException.getInternalError("type=" + this.operationType);
            }
        } else {
            if ((this.rightMask & 0x10) != 0) {
                user.checkAdmin();
            } else {
                if (this.schema != null) {
                    user.checkSchemaOwner(this.schema);
                }
                for (Table table : this.tables) {
                    user.checkSchemaOwner(table.getSchema());
                }
            }
            if (this.operationType == 49) {
                this.grantRight();
            } else if (this.operationType == 50) {
                this.revokeRight();
            } else {
                throw DbException.getInternalError("type=" + this.operationType);
            }
        }
        return 0L;
    }

    private void grantRight() {
        if (this.schema != null) {
            this.grantRight(this.schema);
        }
        for (Table table : this.tables) {
            this.grantRight(table);
        }
    }

    private void grantRight(DbObject dbObject) {
        Database database = this.getDatabase();
        Right right = this.grantee.getRightForObject(dbObject);
        if (right == null) {
            int n = this.getPersistedObjectId();
            if (n == 0) {
                n = this.getDatabase().allocateObjectId();
            }
            right = new Right(database, n, this.grantee, this.rightMask, dbObject);
            this.grantee.grantRight(dbObject, right);
            database.addDatabaseObject(this.session, right);
        } else {
            right.setRightMask(right.getRightMask() | this.rightMask);
            database.updateMeta(this.session, right);
        }
    }

    private void grantRole(Role role) {
        Object object;
        if (role != this.grantee && this.grantee.isRoleGranted(role)) {
            return;
        }
        if (this.grantee instanceof Role && role.isRoleGranted((Role)(object = (Role)this.grantee))) {
            throw DbException.get(90074, role.getTraceSQL());
        }
        object = this.getDatabase();
        int n = this.getObjectId();
        Right right = new Right((Database)object, n, this.grantee, role);
        ((Database)object).addDatabaseObject(this.session, right);
        this.grantee.grantRole(role, right);
    }

    private void revokeRight() {
        if (this.schema != null) {
            this.revokeRight(this.schema);
        }
        for (Table table : this.tables) {
            this.revokeRight(table);
        }
    }

    private void revokeRight(DbObject dbObject) {
        Right right = this.grantee.getRightForObject(dbObject);
        if (right == null) {
            return;
        }
        int n = right.getRightMask();
        int n2 = n & ~this.rightMask;
        Database database = this.getDatabase();
        if (n2 == 0) {
            database.removeDatabaseObject(this.session, right);
        } else {
            right.setRightMask(n2);
            database.updateMeta(this.session, right);
        }
    }

    private void revokeRole(Role role) {
        Right right = this.grantee.getRightForRole(role);
        if (right == null) {
            return;
        }
        Database database = this.getDatabase();
        database.removeDatabaseObject(this.session, right);
    }

    @Override
    public boolean isTransactional() {
        return false;
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public int getType() {
        return this.operationType;
    }
}

