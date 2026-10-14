/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLStateSubclass;

public final class ParserException
extends DataAccessException {
    private final String sql;
    private int position;
    private int line;
    private int column;

    public ParserException(String sql) {
        this(sql, (String)null);
    }

    public ParserException(String sql, String message) {
        this(sql, message, null);
    }

    public ParserException(String sql, String message, SQLStateSubclass state) {
        this(sql, message, state, null);
    }

    public ParserException(String sql, String message, SQLStateSubclass state, Throwable cause) {
        super((String)(state == null ? "" : String.valueOf((Object)state) + ": ") + (String)(message == null ? "" : message + ": ") + sql, cause);
        this.sql = sql;
    }

    public final int position() {
        return this.position;
    }

    public final ParserException position(int p) {
        this.position = p;
        return this;
    }

    public final int line() {
        return this.line;
    }

    public final ParserException line(int l) {
        this.line = l;
        return this;
    }

    public final int column() {
        return this.column;
    }

    public final ParserException column(int c) {
        this.column = c;
        return this;
    }

    public final String sql() {
        return this.sql;
    }
}

