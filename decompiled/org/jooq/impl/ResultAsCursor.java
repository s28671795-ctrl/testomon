/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import org.jooq.Attachable;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.AbstractCursor;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.ResultImpl;

final class ResultAsCursor<R extends Record>
extends AbstractCursor<R> {
    private final Result<R> result;
    private int index;

    ResultAsCursor(Result<R> result) {
        super(result.configuration(), (AbstractRow)result.fieldsRow());
        this.result = result;
    }

    @Override
    final List<? extends Attachable> getAttachables() {
        return this.result;
    }

    @Override
    public final Iterator<R> iterator() {
        return this.result.iterator();
    }

    @Override
    public final Result<R> fetchNext(int number) {
        ResultImpl r = new ResultImpl(this.configuration, this.fields);
        for (int i = 0; i < number && i + this.index < this.result.size(); ++i) {
            r.add((Record)this.result.get(i + this.index));
        }
        this.index += number;
        return r;
    }

    @Override
    public void close() {
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public final ResultSet resultSet() {
        return this.result.intoResultSet();
    }
}

