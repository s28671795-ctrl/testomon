/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.tools.jdbc.Mock;

public class MockResult {
    public final int rows;
    public final Result<?> data;
    public final SQLException exception;

    public MockResult() {
        this(-1, null);
    }

    public MockResult(int rows) {
        this(rows, null);
    }

    public MockResult(Record data) {
        this(1, Mock.result(data));
    }

    public MockResult(int rows, Result<?> data) {
        this.rows = rows;
        this.data = data;
        this.exception = null;
    }

    public MockResult(SQLException exception) {
        this.rows = -1;
        this.data = null;
        this.exception = exception;
    }

    public String toString() {
        return this.exception != null ? "Exception : " + this.exception.getMessage() : (this.data != null ? this.data.toString() : "" + this.rows);
    }
}

