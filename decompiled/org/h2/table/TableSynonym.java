/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.command.ddl.CreateSynonymData;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.table.Table;
import org.h2.util.ParserUtil;

public class TableSynonym
extends SchemaObject {
    private CreateSynonymData data;
    private Table synonymFor;

    public TableSynonym(CreateSynonymData createSynonymData) {
        super(createSynonymData.schema, createSynonymData.id, createSynonymData.synonymName, 11);
        this.data = createSynonymData;
    }

    public Table getSynonymFor() {
        return this.synonymFor;
    }

    public void updateData(CreateSynonymData createSynonymData) {
        this.data = createSynonymData;
    }

    @Override
    public int getType() {
        return 15;
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        return this.synonymFor.getCreateSQLForCopy(table, string);
    }

    @Override
    public void rename(String string) {
        throw DbException.getUnsupportedException("SYNONYM");
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.synonymFor.removeSynonym(this);
        this.database.removeMeta(sessionLocal, this.getId());
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("CREATE SYNONYM ");
        this.getSQL(stringBuilder, 0).append(" FOR ");
        ParserUtil.quoteIdentifier(stringBuilder, this.data.synonymForSchema.getName(), 0).append('.');
        ParserUtil.quoteIdentifier(stringBuilder, this.data.synonymFor, 0);
        return stringBuilder.toString();
    }

    @Override
    public String getDropSQL() {
        return this.getSQL(new StringBuilder("DROP SYNONYM "), 0).toString();
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("SYNONYM");
    }

    public String getSynonymForName() {
        return this.data.synonymFor;
    }

    public Schema getSynonymForSchema() {
        return this.data.synonymForSchema;
    }

    public boolean isInvalid() {
        return this.synonymFor.isValid();
    }

    public void updateSynonymFor() {
        if (this.synonymFor != null) {
            this.synonymFor.removeSynonym(this);
        }
        this.synonymFor = this.data.synonymForSchema.getTableOrView(this.data.session, this.data.synonymFor);
        this.synonymFor.addSynonym(this);
    }
}

