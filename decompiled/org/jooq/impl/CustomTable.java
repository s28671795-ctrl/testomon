/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.impl.QOM;
import org.jooq.impl.TableImpl;

public abstract class CustomTable<R extends TableRecord<R>>
extends TableImpl<R>
implements QOM.UOpaque {
    protected CustomTable(Name name) {
        super(name);
    }

    protected CustomTable(Name name, Schema schema) {
        super(name, schema);
    }

    @Deprecated
    protected CustomTable(String name) {
        super(name);
    }

    @Deprecated
    protected CustomTable(String name, Schema schema) {
        super(name, schema);
    }

    @Override
    public abstract Class<? extends R> getRecordType();

    @Override
    public Identity<R, ?> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<R> getPrimaryKey() {
        return super.getPrimaryKey();
    }

    @Override
    public List<UniqueKey<R>> getUniqueKeys() {
        return super.getUniqueKeys();
    }

    @Override
    public List<ForeignKey<R, ?>> getReferences() {
        return super.getReferences();
    }

    @Override
    public final boolean declaresFields() {
        return super.declaresFields();
    }

    @Override
    public final boolean declaresTables() {
        return super.declaresTables();
    }
}

