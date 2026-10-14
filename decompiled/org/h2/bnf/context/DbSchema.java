/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf.context;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import org.h2.bnf.context.DbContents;
import org.h2.bnf.context.DbProcedure;
import org.h2.bnf.context.DbTableOrView;
import org.h2.engine.SysProperties;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class DbSchema {
    private static final String COLUMNS_QUERY_H2_197 = "SELECT COLUMN_NAME, ORDINAL_POSITION, COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ?1 AND TABLE_NAME = ?2";
    private static final String COLUMNS_QUERY_H2_202 = "SELECT COLUMN_NAME, ORDINAL_POSITION, DATA_TYPE_SQL(?1, ?2, 'TABLE', ORDINAL_POSITION) COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ?1 AND TABLE_NAME = ?2";
    public final String name;
    public final boolean isDefault;
    public final boolean isSystem;
    public final String quotedName;
    private final DbContents contents;
    private DbTableOrView[] tables;
    private DbProcedure[] procedures;

    DbSchema(DbContents dbContents, String string, boolean bl) {
        this.contents = dbContents;
        this.name = string;
        this.quotedName = dbContents.quoteIdentifier(string);
        this.isDefault = bl;
        this.isSystem = string == null ? true : ("INFORMATION_SCHEMA".equalsIgnoreCase(string) ? true : (!dbContents.isH2() && StringUtils.toUpperEnglish(string).startsWith("INFO") ? true : (dbContents.isPostgreSQL() && StringUtils.toUpperEnglish(string).startsWith("PG_") ? true : dbContents.isDerby() && string.startsWith("SYS"))));
    }

    public DbContents getContents() {
        return this.contents;
    }

    public DbTableOrView[] getTables() {
        return this.tables;
    }

    public DbProcedure[] getProcedures() {
        return this.procedures;
    }

    public void readTables(DatabaseMetaData databaseMetaData, String[] stringArray) throws SQLException {
        DbTableOrView dbTableOrView;
        ResultSet resultSet = databaseMetaData.getTables(null, this.name, null, stringArray);
        ArrayList<DbTableOrView> arrayList = new ArrayList<DbTableOrView>();
        while (resultSet.next()) {
            dbTableOrView = new DbTableOrView(this, resultSet);
            if (this.contents.isOracle() && dbTableOrView.getName().indexOf(36) > 0) continue;
            arrayList.add(dbTableOrView);
        }
        resultSet.close();
        this.tables = arrayList.toArray(new DbTableOrView[0]);
        if (this.tables.length < SysProperties.CONSOLE_MAX_TABLES_LIST_COLUMNS) {
            dbTableOrView = this.contents.isH2() ? DbSchema.prepareColumnsQueryH2(databaseMetaData.getConnection()) : null;
            Throwable throwable = null;
            try {
                for (DbTableOrView dbTableOrView2 : this.tables) {
                    try {
                        dbTableOrView2.readColumns(databaseMetaData, (PreparedStatement)((Object)dbTableOrView));
                    }
                    catch (SQLException sQLException) {
                        // empty catch block
                    }
                }
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (dbTableOrView != null) {
                    if (throwable != null) {
                        try {
                            dbTableOrView.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        dbTableOrView.close();
                    }
                }
            }
        }
    }

    private static PreparedStatement prepareColumnsQueryH2(Connection connection) throws SQLException {
        try {
            return connection.prepareStatement(COLUMNS_QUERY_H2_202);
        }
        catch (SQLSyntaxErrorException sQLSyntaxErrorException) {
            return connection.prepareStatement(COLUMNS_QUERY_H2_197);
        }
    }

    public void readProcedures(DatabaseMetaData databaseMetaData) throws SQLException {
        ResultSet resultSet = databaseMetaData.getProcedures(null, this.name, null);
        ArrayList<DbProcedure> arrayList = Utils.newSmallArrayList();
        while (resultSet.next()) {
            arrayList.add(new DbProcedure(this, resultSet));
        }
        resultSet.close();
        this.procedures = arrayList.toArray(new DbProcedure[0]);
        if (this.procedures.length < SysProperties.CONSOLE_MAX_PROCEDURES_LIST_COLUMNS) {
            for (DbProcedure dbProcedure : this.procedures) {
                dbProcedure.readParameters(databaseMetaData);
            }
        }
    }
}

