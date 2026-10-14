/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.CreateSynonymData;
import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.TableSynonym;

public class CreateSynonym
extends SchemaOwnerCommand {
    private final CreateSynonymData data = new CreateSynonymData();
    private boolean ifNotExists;
    private boolean orReplace;
    private String comment;

    public CreateSynonym(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setName(String string) {
        this.data.synonymName = string;
    }

    public void setSynonymFor(String string) {
        this.data.synonymFor = string;
    }

    public void setSynonymForSchema(Schema schema) {
        this.data.synonymForSchema = schema;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setOrReplace(boolean bl) {
        this.orReplace = bl;
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        this.data.session = this.session;
        database.lockMeta(this.session);
        if (schema.findTableOrView(this.session, this.data.synonymName) != null) {
            throw DbException.get(42101, this.data.synonymName);
        }
        if (this.data.synonymForSchema.findTableOrView(this.session, this.data.synonymFor) != null) {
            return this.createTableSynonym(database);
        }
        throw DbException.get(42102, this.data.synonymForSchema.getName() + "." + this.data.synonymFor);
    }

    private int createTableSynonym(Database database) {
        TableSynonym tableSynonym;
        TableSynonym tableSynonym2 = this.getSchema().getSynonym(this.data.synonymName);
        if (tableSynonym2 != null && !this.orReplace) {
            if (this.ifNotExists) {
                return 0;
            }
            throw DbException.get(42101, this.data.synonymName);
        }
        if (tableSynonym2 != null) {
            tableSynonym = tableSynonym2;
            this.data.schema = tableSynonym.getSchema();
            tableSynonym.updateData(this.data);
            tableSynonym.setComment(this.comment);
            tableSynonym.setModified();
            database.updateMeta(this.session, tableSynonym);
        } else {
            this.data.id = this.getObjectId();
            tableSynonym = this.getSchema().createSynonym(this.data);
            tableSynonym.setComment(this.comment);
            database.addSchemaObject(this.session, tableSynonym);
        }
        tableSynonym.updateSynonymFor();
        return 0;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    @Override
    public int getType() {
        return 88;
    }
}

