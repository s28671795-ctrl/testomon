/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf.context;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.bnf.context.DbColumn;
import org.h2.bnf.context.DbSchema;

public class DbTableOrView {
    private final DbSchema schema;
    private final String name;
    private final String quotedName;
    private final boolean isView;
    private DbColumn[] columns;

    public DbTableOrView(DbSchema dbSchema, ResultSet resultSet) throws SQLException {
        this.schema = dbSchema;
        this.name = resultSet.getString("TABLE_NAME");
        String string = resultSet.getString("TABLE_TYPE");
        this.isView = "VIEW".equals(string);
        this.quotedName = dbSchema.getContents().quoteIdentifier(this.name);
    }

    public DbSchema getSchema() {
        return this.schema;
    }

    public DbColumn[] getColumns() {
        return this.columns;
    }

    public String getName() {
        return this.name;
    }

    public boolean isView() {
        return this.isView;
    }

    public String getQuotedName() {
        return this.quotedName;
    }

    public void readColumns(DatabaseMetaData databaseMetaData, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet;
        if (this.schema.getContents().isH2()) {
            preparedStatement.setString(1, this.schema.name);
            preparedStatement.setString(2, this.name);
            resultSet = preparedStatement.executeQuery();
        } else {
            resultSet = databaseMetaData.getColumns(null, this.schema.name, this.name, null);
        }
        ArrayList<DbColumn> arrayList = new ArrayList<DbColumn>();
        while (resultSet.next()) {
            DbColumn dbColumn = DbColumn.getColumn(this.schema.getContents(), resultSet);
            arrayList.add(dbColumn);
        }
        resultSet.close();
        this.columns = arrayList.toArray(new DbColumn[0]);
    }
}

