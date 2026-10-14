/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Select;
import org.jooq.impl.QOM;

public final class TableOptions
implements Serializable {
    private static final TableOptions C_EXPRESSION = new TableOptions(TableType.EXPRESSION);
    private static final TableOptions C_FUNCTION = new TableOptions(TableType.FUNCTION);
    private static final TableOptions C_MATERIALIZED_VIEW = TableOptions.materializedView(null);
    private static final TableOptions C_TABLE = new TableOptions(TableType.TABLE);
    private static final TableOptions C_TEMPORARY = new TableOptions(TableType.TEMPORARY);
    private static final TableOptions C_VIEW = TableOptions.view((String)null);
    private final TableType type;
    private final OnCommit onCommit;
    private final Select<?> select;
    private final String source;

    private TableOptions(TableType type) {
        this.type = type;
        this.onCommit = null;
        this.select = null;
        this.source = null;
    }

    private TableOptions(OnCommit onCommit) {
        this.type = TableType.TEMPORARY;
        this.onCommit = onCommit;
        this.select = null;
        this.source = null;
    }

    private TableOptions(TableType type, Select<?> select) {
        this.type = type;
        this.onCommit = null;
        this.select = select;
        this.source = select == null ? null : select.toString();
    }

    private TableOptions(TableType type, String source2) {
        this.type = type;
        this.onCommit = null;
        this.select = null;
        this.source = source2;
    }

    @NotNull
    public static final TableOptions of(TableType tableType) {
        switch (tableType) {
            case EXPRESSION: {
                return TableOptions.expression();
            }
            case FUNCTION: {
                return TableOptions.function();
            }
            case MATERIALIZED_VIEW: {
                return TableOptions.materializedView();
            }
            case TEMPORARY: {
                return TableOptions.temporaryTable();
            }
            case VIEW: {
                return TableOptions.view();
            }
        }
        return TableOptions.table();
    }

    @NotNull
    public static final TableOptions table() {
        return C_TABLE;
    }

    @NotNull
    public static final TableOptions temporaryTable() {
        return C_TEMPORARY;
    }

    @NotNull
    public static final TableOptions temporaryTable(OnCommit onCommit) {
        return new TableOptions(onCommit);
    }

    @NotNull
    public static final TableOptions temporaryTable(QOM.TableCommitAction onCommit) {
        if (onCommit == null) {
            return new TableOptions((OnCommit)null);
        }
        switch (onCommit) {
            case DELETE_ROWS: {
                return TableOptions.temporaryTable(OnCommit.DELETE_ROWS);
            }
            case PRESERVE_ROWS: {
                return TableOptions.temporaryTable(OnCommit.PRESERVE_ROWS);
            }
            case DROP: {
                return TableOptions.temporaryTable(OnCommit.DROP);
            }
        }
        throw new IllegalArgumentException("TableCommitAction not supported: " + String.valueOf((Object)onCommit));
    }

    @NotNull
    public static final TableOptions view() {
        return C_VIEW;
    }

    @NotNull
    public static final TableOptions view(Select<?> select) {
        return new TableOptions(TableType.VIEW, select);
    }

    @NotNull
    public static final TableOptions view(String source2) {
        return new TableOptions(TableType.VIEW, source2);
    }

    @NotNull
    public static final TableOptions materializedView() {
        return C_MATERIALIZED_VIEW;
    }

    @NotNull
    public static final TableOptions materializedView(Select<?> select) {
        return new TableOptions(TableType.MATERIALIZED_VIEW, select);
    }

    @NotNull
    public static final TableOptions expression() {
        return C_EXPRESSION;
    }

    @NotNull
    public static final TableOptions function() {
        return C_FUNCTION;
    }

    @NotNull
    public static final TableOptions function(String source2) {
        return new TableOptions(TableType.FUNCTION, source2);
    }

    @NotNull
    public final TableType type() {
        return this.type;
    }

    @Nullable
    public final OnCommit onCommit() {
        return this.onCommit;
    }

    @Nullable
    public final Select<?> select() {
        return this.select;
    }

    @Nullable
    public final String source() {
        return this.source;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.onCommit == null ? 0 : this.onCommit.hashCode());
        result = 31 * result + (this.select == null ? 0 : this.select.hashCode());
        result = 31 * result + (this.source == null ? 0 : this.source.hashCode());
        result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TableOptions other = (TableOptions)obj;
        if (this.onCommit != other.onCommit) {
            return false;
        }
        if (this.select == null ? other.select != null : !this.select.equals(other.select)) {
            return false;
        }
        if (this.source == null ? other.source != null : !this.source.equals(other.source)) {
            return false;
        }
        return this.type == other.type;
    }

    public String toString() {
        return "TableOptions[" + String.valueOf((Object)this.type) + "]";
    }

    public static enum TableType {
        TABLE,
        TEMPORARY,
        VIEW,
        MATERIALIZED_VIEW,
        FUNCTION,
        EXPRESSION,
        UNKNOWN;


        public final boolean isView() {
            return this == VIEW || this == MATERIALIZED_VIEW;
        }

        public final boolean isFunction() {
            return this == FUNCTION;
        }

        public final boolean isTable() {
            return this == TABLE || this == TEMPORARY;
        }
    }

    public static enum OnCommit {
        DELETE_ROWS,
        PRESERVE_ROWS,
        DROP;

    }
}

