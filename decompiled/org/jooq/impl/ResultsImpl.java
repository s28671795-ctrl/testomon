/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultOrRows;
import org.jooq.Results;
import org.jooq.exception.DataAccessException;

final class ResultsImpl
extends AbstractList<Result<Record>>
implements Results {
    private Configuration configuration;
    final List<ResultOrRows> resultsOrRows;

    ResultsImpl(Configuration configuration) {
        this.configuration = configuration;
        this.resultsOrRows = new ArrayList<ResultOrRows>();
    }

    @Override
    public final List<ResultOrRows> resultsOrRows() {
        return this.resultsOrRows;
    }

    @Override
    public final void attach(Configuration c) {
        this.configuration = c;
        for (Result result : this) {
            if (result == null) continue;
            result.attach(c);
        }
    }

    @Override
    public final void detach() {
        this.attach(null);
    }

    @Override
    public final Configuration configuration() {
        return this.configuration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (ResultOrRows result : this.resultsOrRows) {
            if (result.result() != null) {
                sb.append(separator).append("Result set:\n").append(result.result());
            } else if (result.exception() != null) {
                sb.append(separator).append("Exception: ").append(result.exception().getMessage());
            } else {
                sb.append(separator).append("Update count: ").append(result.rows());
            }
            separator = "\n";
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.resultsOrRows.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ResultsImpl) {
            ResultsImpl r = (ResultsImpl)obj;
            return this.resultsOrRows.equals(r.resultsOrRows);
        }
        return false;
    }

    @Override
    public final int size() {
        return this.list().size();
    }

    @Override
    public final Result<Record> get(int index) {
        return this.list().get(index);
    }

    @Override
    public Result<Record> set(int index, Result<Record> element) {
        return this.resultsOrRows.set(this.translatedIndex(index), new ResultOrRowsImpl(element)).result();
    }

    @Override
    public void add(int index, Result<Record> element) {
        this.resultsOrRows.add(this.translatedIndex(index), new ResultOrRowsImpl(element));
    }

    @Override
    public Result<Record> remove(int index) {
        return this.resultsOrRows.remove(this.translatedIndex(index)).result();
    }

    @Override
    public void clear() {
        this.resultsOrRows.clear();
    }

    private final List<Result<Record>> list() {
        ArrayList<Result<Record>> list = new ArrayList<Result<Record>>();
        for (ResultOrRows result : this.resultsOrRows) {
            if (result.result() == null) continue;
            list.add(result.result());
        }
        return list;
    }

    private final int translatedIndex(int index) {
        int translated = 0;
        for (int i = 0; i < index; ++i) {
            while (this.resultsOrRows.get(translated++).result() == null) {
            }
        }
        return translated;
    }

    record ResultOrRowsImpl(Result<Record> result, int rows, DataAccessException exception) implements ResultOrRows
    {
        ResultOrRowsImpl(Result<Record> result) {
            this(result, result != null ? result.size() : 0, null);
        }

        ResultOrRowsImpl(int rows) {
            this(null, rows, null);
        }

        ResultOrRowsImpl(DataAccessException exception) {
            this(null, 0, exception);
        }

        @Override
        public String toString() {
            if (this.exception != null) {
                return this.exception.toString();
            }
            if (this.result != null) {
                return this.result.toString();
            }
            return "" + this.rows;
        }
    }
}

