/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.command.ddl.SequenceOptions;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;
import org.h2.table.Column;

public class AlterSequence
extends SchemaOwnerCommand {
    private boolean ifExists;
    private Column column;
    private Boolean always;
    private String sequenceName;
    private Sequence sequence;
    private SequenceOptions options;

    public AlterSequence(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
        this.transactional = true;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setSequenceName(String string) {
        this.sequenceName = string;
    }

    public void setOptions(SequenceOptions sequenceOptions) {
        this.options = sequenceOptions;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    public void setColumn(Column column, Boolean bl) {
        this.column = column;
        this.always = bl;
        this.sequence = column.getSequence();
        if (this.sequence == null && !this.ifExists) {
            throw DbException.get(90036, column.getTraceSQL());
        }
    }

    @Override
    long update(Schema schema) {
        if (this.sequence == null) {
            this.sequence = schema.findSequence(this.sequenceName);
            if (this.sequence == null) {
                if (!this.ifExists) {
                    throw DbException.get(90036, this.sequenceName);
                }
                return 0L;
            }
        }
        if (this.column != null) {
            this.session.getUser().checkTableRight(this.column.getTable(), 32);
        }
        this.options.setDataType(this.sequence.getDataType());
        Long l = this.options.getStartValue(this.session);
        this.sequence.modify(this.options.getRestartValue(this.session, l != null ? l.longValue() : this.sequence.getStartValue()), l, this.options.getMinValue(this.sequence, this.session), this.options.getMaxValue(this.sequence, this.session), this.options.getIncrement(this.session), this.options.getCycle(), this.options.getCacheSize(this.session));
        this.sequence.flush(this.session);
        if (this.column != null && this.always != null) {
            this.column.setSequence(this.sequence, this.always);
            this.getDatabase().updateMeta(this.session, this.column.getTable());
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 54;
    }
}

