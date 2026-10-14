/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf.context;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.bnf.context.DbSchema;
import org.h2.engine.Session;
import org.h2.jdbc.JdbcConnection;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class DbContents {
    private DbSchema[] schemas;
    private DbSchema defaultSchema;
    private boolean isOracle;
    private boolean isH2;
    private boolean isPostgreSQL;
    private boolean isDerby;
    private boolean isSQLite;
    private boolean isMySQL;
    private boolean isFirebird;
    private boolean isMSSQLServer;
    private boolean isDB2;
    private boolean databaseToUpper;
    private boolean databaseToLower;
    private boolean mayHaveStandardViews = true;

    public DbSchema getDefaultSchema() {
        return this.defaultSchema;
    }

    public boolean isDerby() {
        return this.isDerby;
    }

    public boolean isFirebird() {
        return this.isFirebird;
    }

    public boolean isH2() {
        return this.isH2;
    }

    public boolean isMSSQLServer() {
        return this.isMSSQLServer;
    }

    public boolean isMySQL() {
        return this.isMySQL;
    }

    public boolean isOracle() {
        return this.isOracle;
    }

    public boolean isPostgreSQL() {
        return this.isPostgreSQL;
    }

    public boolean isSQLite() {
        return this.isSQLite;
    }

    public boolean isDB2() {
        return this.isDB2;
    }

    public DbSchema[] getSchemas() {
        return this.schemas;
    }

    public boolean mayHaveStandardViews() {
        return this.mayHaveStandardViews;
    }

    public void setMayHaveStandardViews(boolean bl) {
        this.mayHaveStandardViews = bl;
    }

    public synchronized void readContents(String string, Connection connection) throws SQLException {
        Object object;
        this.isH2 = string.startsWith("jdbc:h2:");
        this.isDB2 = string.startsWith("jdbc:db2:");
        this.isSQLite = string.startsWith("jdbc:sqlite:");
        this.isOracle = string.startsWith("jdbc:oracle:");
        this.isPostgreSQL = string.startsWith("jdbc:postgresql:") || string.startsWith("jdbc:vertica:");
        this.isMySQL = string.startsWith("jdbc:mysql:");
        this.isDerby = string.startsWith("jdbc:derby:");
        this.isFirebird = string.startsWith("jdbc:firebirdsql:");
        this.isMSSQLServer = string.startsWith("jdbc:sqlserver:");
        if (this.isH2) {
            object = ((JdbcConnection)connection).getStaticSettings();
            this.databaseToUpper = ((Session.StaticSettings)object).databaseToUpper;
            this.databaseToLower = ((Session.StaticSettings)object).databaseToLower;
        } else if (this.isMySQL || this.isPostgreSQL) {
            this.databaseToUpper = false;
            this.databaseToLower = true;
        } else {
            this.databaseToUpper = true;
            this.databaseToLower = false;
        }
        object = connection.getMetaData();
        String string2 = this.getDefaultSchemaName((DatabaseMetaData)object);
        String[] stringArray = this.getSchemaNames((DatabaseMetaData)object);
        this.schemas = new DbSchema[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            DbSchema[] dbSchemaArray = stringArray[i];
            int n = string2 == null || string2.equals(dbSchemaArray) ? 1 : 0;
            DbSchema dbSchema = new DbSchema(this, (String)dbSchemaArray, n != 0);
            if (n != 0) {
                this.defaultSchema = dbSchema;
            }
            this.schemas[i] = dbSchema;
            String[] stringArray2 = new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "SYSTEM VIEW", "TABLE LINK", "SYNONYM", "EXTERNAL"};
            dbSchema.readTables((DatabaseMetaData)object, stringArray2);
            if (this.isPostgreSQL || this.isDB2) continue;
            dbSchema.readProcedures((DatabaseMetaData)object);
        }
        if (this.defaultSchema == null) {
            String string3 = null;
            for (DbSchema dbSchema : this.schemas) {
                if ("dbo".equals(dbSchema.name)) {
                    this.defaultSchema = dbSchema;
                    break;
                }
                if (this.defaultSchema != null && string3 != null && dbSchema.name.length() >= string3.length()) continue;
                string3 = dbSchema.name;
                this.defaultSchema = dbSchema;
            }
        }
    }

    private String[] getSchemaNames(DatabaseMetaData databaseMetaData) throws SQLException {
        if (this.isMySQL || this.isSQLite) {
            return new String[]{""};
        }
        if (this.isFirebird) {
            return new String[]{null};
        }
        ResultSet resultSet = databaseMetaData.getSchemas();
        ArrayList<String> arrayList = Utils.newSmallArrayList();
        while (resultSet.next()) {
            String string = resultSet.getString("TABLE_SCHEM");
            String[] stringArray = null;
            if (this.isOracle) {
                stringArray = new String[]{"CTXSYS", "DIP", "DBSNMP", "DMSYS", "EXFSYS", "FLOWS_020100", "FLOWS_FILES", "MDDATA", "MDSYS", "MGMT_VIEW", "OLAPSYS", "ORDSYS", "ORDPLUGINS", "OUTLN", "SI_INFORMTN_SCHEMA", "SYS", "SYSMAN", "SYSTEM", "TSMSYS", "WMSYS", "XDB"};
            } else if (this.isMSSQLServer) {
                stringArray = new String[]{"sys", "db_accessadmin", "db_backupoperator", "db_datareader", "db_datawriter", "db_ddladmin", "db_denydatareader", "db_denydatawriter", "db_owner", "db_securityadmin"};
            } else if (this.isDB2) {
                stringArray = new String[]{"NULLID", "SYSFUN", "SYSIBMINTERNAL", "SYSIBMTS", "SYSPROC", "SYSPUBLIC", "SYSCAT", "SYSIBM", "SYSIBMADM", "SYSSTAT", "SYSTOOLS"};
            }
            if (stringArray != null) {
                for (void var9_9 : stringArray) {
                    if (!var9_9.equals(string)) continue;
                    string = null;
                    break;
                }
            }
            if (string == null) continue;
            arrayList.add(string);
        }
        resultSet.close();
        return arrayList.toArray(new String[0]);
    }

    private String getDefaultSchemaName(DatabaseMetaData databaseMetaData) {
        String string = "";
        try {
            if (this.isH2) {
                return databaseMetaData.storesLowerCaseIdentifiers() ? "public" : "PUBLIC";
            }
            if (this.isOracle) {
                return databaseMetaData.getUserName();
            }
            if (this.isPostgreSQL) {
                return "public";
            }
            if (this.isMySQL) {
                return "";
            }
            if (this.isDerby) {
                return StringUtils.toUpperEnglish(databaseMetaData.getUserName());
            }
            if (this.isFirebird) {
                return null;
            }
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        return string;
    }

    public String quoteIdentifier(String string) {
        if (string == null) {
            return null;
        }
        if (ParserUtil.isSimpleIdentifier(string, this.databaseToUpper, this.databaseToLower)) {
            return string;
        }
        return StringUtils.quoteIdentifier(string);
    }
}

