/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockResult;

public final class Mock {
    public static final MockDataProvider of(int rows) {
        return Mock.of(new MockResult(rows, null));
    }

    public static final MockDataProvider of(Record record) {
        return Mock.of(Mock.result(record));
    }

    public static final MockDataProvider of(Result<?> result) {
        return Mock.of(new MockResult(result.size(), result));
    }

    public static final MockDataProvider of(MockResult ... result) {
        return ctx -> result;
    }

    public static final MockDataProvider of(SQLException exception) {
        return Mock.of(new MockResult(exception));
    }

    static final Result<?> result(Record data) {
        Result<Record> result = DSL.using(data.configuration()).newResult(data.fields());
        result.add(data);
        return result;
    }

    private Mock() {
    }
}

