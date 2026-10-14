/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.h2.jdbc.JdbcException;
import org.h2.message.DbException;

public final class JdbcSQLIntegrityConstraintViolationException
extends SQLIntegrityConstraintViolationException
implements JdbcException {
    private static final long serialVersionUID = 1L;
    private final String originalMessage;
    private final String stackTrace;
    private String message;
    private String sql;

    public JdbcSQLIntegrityConstraintViolationException(String string, String string2, String string3, int n, Throwable throwable, String string4) {
        super(string, string3, n);
        this.originalMessage = string;
        this.stackTrace = string4;
        this.setSQL(string2);
        this.initCause(throwable);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getOriginalMessage() {
        return this.originalMessage;
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        DbException.printNextExceptions((SQLException)this, printWriter);
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        DbException.printNextExceptions((SQLException)this, printStream);
    }

    @Override
    public String getSQL() {
        return this.sql;
    }

    @Override
    public void setSQL(String string) {
        this.sql = string;
        this.message = DbException.buildMessageForException(this);
    }

    @Override
    public String toString() {
        if (this.stackTrace == null) {
            return super.toString();
        }
        return this.stackTrace;
    }
}

