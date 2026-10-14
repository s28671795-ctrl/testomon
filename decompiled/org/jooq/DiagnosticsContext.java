/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.QueryPart;
import org.jooq.Scope;

public interface DiagnosticsContext
extends Scope {
    @Nullable
    public QueryPart part();

    @Nullable
    public QueryPart transformedPart();

    @NotNull
    public String message();

    @Nullable
    public ResultSet resultSet();

    public int resultSetConsumedRows();

    public int resultSetFetchedRows();

    public int resultSetConsumedColumnCount();

    public int resultSetFetchedColumnCount();

    @NotNull
    public List<String> resultSetConsumedColumnNames();

    @NotNull
    public List<String> resultSetFetchedColumnNames();

    public boolean resultSetUnnecessaryWasNullCall();

    public boolean resultSetMissingWasNullCall();

    public int resultSetColumnIndex();

    @NotNull
    public String actualStatement();

    @NotNull
    public String normalisedStatement();

    @NotNull
    public Set<String> duplicateStatements();

    @NotNull
    public List<String> repeatedStatements();

    @Nullable
    public Throwable exception();
}

