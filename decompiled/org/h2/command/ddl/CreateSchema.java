/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;

public class CreateSchema
extends DefineCommand {
    private String schemaName;
    private String authorization;
    private boolean ifNotExists;
    private ArrayList<String> tableEngineParams;

    public CreateSchema(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    @Override
    public long update() {
        this.session.getUser().checkSchemaAdmin();
        Database database = this.getDatabase();
        RightOwner rightOwner = database.findUserOrRole(this.authorization);
        if (rightOwner == null) {
            throw DbException.get(90071, this.authorization);
        }
        if (database.findSchema(this.schemaName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(90078, this.schemaName);
        }
        int n = this.getObjectId();
        Schema schema = new Schema(database, n, this.schemaName, rightOwner, false);
        schema.setTableEngineParams(this.tableEngineParams);
        database.addDatabaseObject(this.session, schema);
        return 0L;
    }

    public void setSchemaName(String string) {
        this.schemaName = string;
    }

    public void setAuthorization(String string) {
        this.authorization = string;
    }

    public void setTableEngineParams(ArrayList<String> arrayList) {
        this.tableEngineParams = arrayList;
    }

    @Override
    public int getType() {
        return 28;
    }
}

