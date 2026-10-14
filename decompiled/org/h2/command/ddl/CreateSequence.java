/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.command.ddl.SequenceOptions;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;

public class CreateSequence
extends SchemaOwnerCommand {
    private String sequenceName;
    private boolean ifNotExists;
    private SequenceOptions options;
    private boolean belongsToTable;

    public CreateSequence(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
        this.transactional = true;
    }

    public void setSequenceName(String string) {
        this.sequenceName = string;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setOptions(SequenceOptions sequenceOptions) {
        this.options = sequenceOptions;
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        if (schema.findSequence(this.sequenceName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(90035, this.sequenceName);
        }
        int n = this.getObjectId();
        Sequence sequence2 = new Sequence(this.session, schema, n, this.sequenceName, this.options, this.belongsToTable);
        database.addSchemaObject(this.session, sequence2);
        return 0L;
    }

    public void setBelongsToTable(boolean bl) {
        this.belongsToTable = bl;
    }

    @Override
    public int getType() {
        return 29;
    }
}

