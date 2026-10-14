/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

public interface SqlStatement {
    public static final SqlStatement[] EMPTY_SQL_STATEMENT = new SqlStatement[0];

    public boolean skipOnUnsupported();

    public boolean continueOnError();
}

