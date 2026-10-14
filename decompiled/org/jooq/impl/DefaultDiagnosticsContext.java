/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jooq.Configuration;
import org.jooq.DiagnosticsContext;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.DiagnosticsResultSet;
import org.jooq.tools.JooqLogger;

final class DefaultDiagnosticsContext
extends AbstractScope
implements DiagnosticsContext {
    private static final JooqLogger log = JooqLogger.getLogger(DefaultDiagnosticsContext.class);
    final QueryPart part;
    final QueryPart transformedPart;
    final String message;
    ResultSet resultSet;
    DiagnosticsResultSet resultSetWrapper;
    boolean resultSetClosing;
    int resultSetFetchedColumnCount;
    int resultSetConsumedColumnCount;
    int resultSetFetchedRows;
    boolean resultSetFetchedRowsComputed;
    int resultSetConsumedRows;
    final String actualStatement;
    final String normalisedStatement;
    final Set<String> duplicateStatements;
    final List<String> repeatedStatements;
    boolean resultSetUnnecessaryWasNullCall;
    boolean resultSetMissingWasNullCall;
    int resultSetColumnIndex;
    final Throwable exception;

    DefaultDiagnosticsContext(Configuration configuration, String message, String actualStatement) {
        this(configuration, message, actualStatement, null);
    }

    DefaultDiagnosticsContext(Configuration configuration, String message, String actualStatement, Throwable exception) {
        this(configuration, message, actualStatement, actualStatement, Collections.singleton(actualStatement), Collections.singletonList(actualStatement), null, null, exception);
    }

    DefaultDiagnosticsContext(Configuration configuration, String message, String actualStatement, String normalisedStatement, Set<String> duplicateStatements, List<String> repeatedStatements, QueryPart part, QueryPart transformedPart, Throwable exception) {
        super(configuration);
        this.message = message;
        this.actualStatement = actualStatement;
        this.normalisedStatement = normalisedStatement;
        this.duplicateStatements = duplicateStatements == null ? Collections.emptySet() : duplicateStatements;
        this.repeatedStatements = repeatedStatements == null ? Collections.emptyList() : repeatedStatements;
        this.part = part;
        this.transformedPart = transformedPart;
        this.exception = exception;
    }

    @Override
    public final QueryPart part() {
        return this.part;
    }

    @Override
    public final QueryPart transformedPart() {
        return this.transformedPart;
    }

    @Override
    public final String message() {
        return this.message;
    }

    @Override
    public final ResultSet resultSet() {
        return this.resultSet;
    }

    @Override
    public final int resultSetConsumedRows() {
        return this.resultSet == null ? -1 : this.resultSetConsumedRows;
    }

    @Override
    public final int resultSetFetchedRows() {
        if (this.resultSet == null) {
            return -1;
        }
        if (!this.resultSetFetchedRowsComputed) {
            this.resultSetFetchedRowsComputed = true;
            try {
                if (this.resultSetClosing || this.resultSet.getType() != 1003) {
                    while (this.resultSet.next()) {
                        ++this.resultSetFetchedRows;
                    }
                    this.resultSet.absolute(this.resultSetConsumedRows);
                }
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
        return this.resultSetFetchedRows;
    }

    @Override
    public final int resultSetConsumedColumnCount() {
        return this.resultSet == null ? -1 : this.resultSetConsumedColumnCount;
    }

    @Override
    public final int resultSetFetchedColumnCount() {
        return this.resultSet == null ? -1 : this.resultSetFetchedColumnCount;
    }

    @Override
    public final List<String> resultSetConsumedColumnNames() {
        return this.resultSetColumnNames(false);
    }

    @Override
    public final List<String> resultSetFetchedColumnNames() {
        return this.resultSetColumnNames(true);
    }

    private final List<String> resultSetColumnNames(boolean fetched) {
        ArrayList<String> result = new ArrayList<String>();
        if (this.resultSet != null) {
            try {
                if (!this.resultSet.isClosed()) {
                    ResultSetMetaData meta = this.resultSet.getMetaData();
                    for (int i = 1; i <= meta.getColumnCount(); ++i) {
                        if (!fetched && !this.resultSetWrapper.read.get(i - 1)) continue;
                        result.add(meta.getColumnLabel(i));
                    }
                }
            }
            catch (SQLException e) {
                log.info(e);
            }
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public final boolean resultSetUnnecessaryWasNullCall() {
        return this.resultSet == null ? false : this.resultSetUnnecessaryWasNullCall;
    }

    @Override
    public final boolean resultSetMissingWasNullCall() {
        return this.resultSet == null ? false : this.resultSetMissingWasNullCall;
    }

    @Override
    public final int resultSetColumnIndex() {
        return this.resultSet == null ? 0 : this.resultSetColumnIndex;
    }

    @Override
    public final String actualStatement() {
        return this.actualStatement;
    }

    @Override
    public final String normalisedStatement() {
        return this.normalisedStatement;
    }

    @Override
    public final Set<String> duplicateStatements() {
        return Collections.unmodifiableSet(this.duplicateStatements);
    }

    @Override
    public final List<String> repeatedStatements() {
        return Collections.unmodifiableList(this.repeatedStatements);
    }

    @Override
    public final Throwable exception() {
        return this.exception;
    }
}

