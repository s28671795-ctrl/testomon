/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf.context;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.bnf.context.DbColumn;
import org.h2.bnf.context.DbSchema;
import org.h2.util.Utils;

public class DbProcedure {
    private final DbSchema schema;
    private final String name;
    private final String quotedName;
    private final boolean returnsResult;
    private DbColumn[] parameters;

    public DbProcedure(DbSchema dbSchema, ResultSet resultSet) throws SQLException {
        this.schema = dbSchema;
        this.name = resultSet.getString("PROCEDURE_NAME");
        this.returnsResult = resultSet.getShort("PROCEDURE_TYPE") == 2;
        this.quotedName = dbSchema.getContents().quoteIdentifier(this.name);
    }

    public DbSchema getSchema() {
        return this.schema;
    }

    public DbColumn[] getParameters() {
        return this.parameters;
    }

    public String getName() {
        return this.name;
    }

    public String getQuotedName() {
        return this.quotedName;
    }

    public boolean isReturnsResult() {
        return this.returnsResult;
    }

    void readParameters(DatabaseMetaData databaseMetaData) throws SQLException {
        ResultSet resultSet = databaseMetaData.getProcedureColumns(null, this.schema.name, this.name, null);
        ArrayList arrayList = Utils.newSmallArrayList();
        while (resultSet.next()) {
            DbColumn dbColumn = DbColumn.getProcedureColumn(this.schema.getContents(), resultSet);
            if (dbColumn.getPosition() <= 0) continue;
            arrayList.add(dbColumn);
        }
        resultSet.close();
        this.parameters = new DbColumn[arrayList.size()];
        for (int i = 0; i < this.parameters.length; ++i) {
            DbColumn dbColumn = (DbColumn)arrayList.get(i);
            if (dbColumn.getPosition() <= 0 || dbColumn.getPosition() > this.parameters.length) continue;
            this.parameters[dbColumn.getPosition() - 1] = dbColumn;
        }
    }
}

