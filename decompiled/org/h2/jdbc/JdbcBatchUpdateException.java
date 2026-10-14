/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.BatchUpdateException;
import java.sql.SQLException;

public final class JdbcBatchUpdateException
extends BatchUpdateException {
    private static final long serialVersionUID = 1L;

    JdbcBatchUpdateException(SQLException sQLException, int[] nArray) {
        super(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), nArray);
        this.setNextException(sQLException);
    }

    JdbcBatchUpdateException(SQLException sQLException, long[] lArray) {
        super(sQLException.getMessage(), sQLException.getSQLState(), sQLException.getErrorCode(), lArray, null);
        this.setNextException(sQLException);
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        if (printWriter != null) {
            super.printStackTrace(printWriter);
            if (this.getNextException() != null) {
                this.getNextException().printStackTrace(printWriter);
            }
        }
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        if (printStream != null) {
            super.printStackTrace(printStream);
            if (this.getNextException() != null) {
                this.getNextException().printStackTrace(printStream);
            }
        }
    }
}

