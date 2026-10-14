/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.bnf.context.DbContents;

public class DbColumn {
    private final String name;
    private final String quotedName;
    private final String dataType;
    private final int position;

    private DbColumn(DbContents dbContents, ResultSet resultSet, boolean bl) throws SQLException {
        String string;
        String string2;
        this.name = resultSet.getString("COLUMN_NAME");
        this.quotedName = dbContents.quoteIdentifier(this.name);
        this.position = resultSet.getInt("ORDINAL_POSITION");
        if (dbContents.isH2() && !bl) {
            this.dataType = resultSet.getString("COLUMN_TYPE");
            return;
        }
        String string3 = resultSet.getString("TYPE_NAME");
        if (bl) {
            string2 = "PRECISION";
            string = "SCALE";
        } else {
            string2 = "COLUMN_SIZE";
            string = "DECIMAL_DIGITS";
        }
        int n = resultSet.getInt(string2);
        if (n > 0 && !dbContents.isSQLite()) {
            int n2 = resultSet.getInt(string);
            string3 = n2 > 0 ? string3 + '(' + n + ", " + n2 + ')' : string3 + '(' + n + ')';
        }
        if (resultSet.getInt("NULLABLE") == 0) {
            string3 = string3 + " NOT NULL";
        }
        this.dataType = string3;
    }

    public static DbColumn getProcedureColumn(DbContents dbContents, ResultSet resultSet) throws SQLException {
        return new DbColumn(dbContents, resultSet, true);
    }

    public static DbColumn getColumn(DbContents dbContents, ResultSet resultSet) throws SQLException {
        return new DbColumn(dbContents, resultSet, false);
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getName() {
        return this.name;
    }

    public String getQuotedName() {
        return this.quotedName;
    }

    public int getPosition() {
        return this.position;
    }
}

