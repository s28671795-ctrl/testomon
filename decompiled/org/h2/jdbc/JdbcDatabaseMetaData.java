/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import org.h2.engine.Constants;
import org.h2.engine.Session;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcDatabaseMetaDataBackwardsCompat;
import org.h2.jdbc.JdbcResultSet;
import org.h2.jdbc.meta.DatabaseMeta;
import org.h2.jdbc.meta.DatabaseMetaLegacy;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceObject;
import org.h2.mode.DefaultNullOrdering;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.value.TypeInfo;
import org.h2.value.ValueInteger;
import org.h2.value.ValueVarchar;

public final class JdbcDatabaseMetaData
extends TraceObject
implements DatabaseMetaData,
JdbcDatabaseMetaDataBackwardsCompat {
    private final JdbcConnection conn;
    private final DatabaseMeta meta;

    JdbcDatabaseMetaData(JdbcConnection jdbcConnection, Trace trace, int n) {
        this.setTrace(trace, 2, n);
        this.conn = jdbcConnection;
        Session session = jdbcConnection.getSession();
        this.meta = session.isOldInformationSchema() ? new DatabaseMetaLegacy(session) : jdbcConnection.getSession().getDatabaseMeta();
    }

    @Override
    public int getDriverMajorVersion() {
        this.debugCodeCall("getDriverMajorVersion");
        return 2;
    }

    @Override
    public int getDriverMinorVersion() {
        this.debugCodeCall("getDriverMinorVersion");
        return 2;
    }

    @Override
    public String getDatabaseProductName() {
        this.debugCodeCall("getDatabaseProductName");
        return "H2";
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        try {
            this.debugCodeCall("getDatabaseProductVersion");
            return this.meta.getDatabaseProductVersion();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getDriverName() {
        this.debugCodeCall("getDriverName");
        return "H2 JDBC Driver";
    }

    @Override
    public String getDriverVersion() {
        this.debugCodeCall("getDriverVersion");
        return Constants.FULL_VERSION;
    }

    @Override
    public ResultSet getTables(String string, String string2, String string3, String[] stringArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getTables(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quoteArray(stringArray) + ')');
            }
            return this.getResultSet(this.meta.getTables(string, string2, string3, stringArray));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getColumns(String string, String string2, String string3, String string4) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getColumns(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ')');
            }
            return this.getResultSet(this.meta.getColumns(string, string2, string3, string4));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getIndexInfo(String string, String string2, String string3, boolean bl, boolean bl2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getIndexInfo(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + bl + ", " + bl2 + ')');
            }
            return this.getResultSet(this.meta.getIndexInfo(string, string2, string3, bl, bl2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getPrimaryKeys(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getPrimaryKeys(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getPrimaryKeys(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean allProceduresAreCallable() {
        this.debugCodeCall("allProceduresAreCallable");
        return true;
    }

    @Override
    public boolean allTablesAreSelectable() {
        this.debugCodeCall("allTablesAreSelectable");
        return true;
    }

    @Override
    public String getURL() throws SQLException {
        try {
            this.debugCodeCall("getURL");
            return this.conn.getURL();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getUserName() throws SQLException {
        try {
            this.debugCodeCall("getUserName");
            return this.conn.getUser();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            this.debugCodeCall("isReadOnly");
            return this.conn.isReadOnly();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        try {
            this.debugCodeCall("nullsAreSortedHigh");
            return this.meta.defaultNullOrdering() == DefaultNullOrdering.HIGH;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        try {
            this.debugCodeCall("nullsAreSortedLow");
            return this.meta.defaultNullOrdering() == DefaultNullOrdering.LOW;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        try {
            this.debugCodeCall("nullsAreSortedAtStart");
            return this.meta.defaultNullOrdering() == DefaultNullOrdering.FIRST;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        try {
            this.debugCodeCall("nullsAreSortedAtEnd");
            return this.meta.defaultNullOrdering() == DefaultNullOrdering.LAST;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Connection getConnection() {
        this.debugCodeCall("getConnection");
        return this.conn;
    }

    @Override
    public ResultSet getProcedures(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getProcedures(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getProcedures(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getProcedureColumns(String string, String string2, String string3, String string4) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getProcedureColumns(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ')');
            }
            this.checkClosed();
            return this.getResultSet(this.meta.getProcedureColumns(string, string2, string3, string4));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        try {
            this.debugCodeCall("getSchemas");
            return this.getResultSet(this.meta.getSchemas());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        try {
            this.debugCodeCall("getCatalogs");
            return this.getResultSet(this.meta.getCatalogs());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        try {
            this.debugCodeCall("getTableTypes");
            return this.getResultSet(this.meta.getTableTypes());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getColumnPrivileges(String string, String string2, String string3, String string4) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getColumnPrivileges(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ')');
            }
            return this.getResultSet(this.meta.getColumnPrivileges(string, string2, string3, string4));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getTablePrivileges(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getTablePrivileges(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            this.checkClosed();
            return this.getResultSet(this.meta.getTablePrivileges(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getBestRowIdentifier(String string, String string2, String string3, int n, boolean bl) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getBestRowIdentifier(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + n + ", " + bl + ')');
            }
            return this.getResultSet(this.meta.getBestRowIdentifier(string, string2, string3, n, bl));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getVersionColumns(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getVersionColumns(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getVersionColumns(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getImportedKeys(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getImportedKeys(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getImportedKeys(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getExportedKeys(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getExportedKeys(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getExportedKeys(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getCrossReference(String string, String string2, String string3, String string4, String string5, String string6) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getCrossReference(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ", " + JdbcDatabaseMetaData.quote(string5) + ", " + JdbcDatabaseMetaData.quote(string6) + ')');
            }
            return this.getResultSet(this.meta.getCrossReference(string, string2, string3, string4, string5, string6));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getUDTs(String string, String string2, String string3, int[] nArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getUDTs(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quoteIntArray(nArray) + ')');
            }
            return this.getResultSet(this.meta.getUDTs(string, string2, string3, nArray));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        try {
            this.debugCodeCall("getTypeInfo");
            return this.getResultSet(this.meta.getTypeInfo());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean usesLocalFiles() {
        this.debugCodeCall("usesLocalFiles");
        return true;
    }

    @Override
    public boolean usesLocalFilePerTable() {
        this.debugCodeCall("usesLocalFilePerTable");
        return false;
    }

    @Override
    public String getIdentifierQuoteString() {
        this.debugCodeCall("getIdentifierQuoteString");
        return "\"";
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        try {
            this.debugCodeCall("getSQLKeywords");
            return this.meta.getSQLKeywords();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        try {
            this.debugCodeCall("getNumericFunctions");
            return this.meta.getNumericFunctions();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getStringFunctions() throws SQLException {
        try {
            this.debugCodeCall("getStringFunctions");
            return this.meta.getStringFunctions();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        try {
            this.debugCodeCall("getSystemFunctions");
            return this.meta.getSystemFunctions();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        try {
            this.debugCodeCall("getTimeDateFunctions");
            return this.meta.getTimeDateFunctions();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        try {
            this.debugCodeCall("getSearchStringEscape");
            return this.meta.getSearchStringEscape();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getExtraNameCharacters() {
        this.debugCodeCall("getExtraNameCharacters");
        return "";
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() {
        this.debugCodeCall("supportsAlterTableWithAddColumn");
        return true;
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() {
        this.debugCodeCall("supportsAlterTableWithDropColumn");
        return true;
    }

    @Override
    public boolean supportsColumnAliasing() {
        this.debugCodeCall("supportsColumnAliasing");
        return true;
    }

    @Override
    public boolean nullPlusNonNullIsNull() {
        this.debugCodeCall("nullPlusNonNullIsNull");
        return true;
    }

    @Override
    public boolean supportsConvert() {
        this.debugCodeCall("supportsConvert");
        return true;
    }

    @Override
    public boolean supportsConvert(int n, int n2) {
        if (this.isDebugEnabled()) {
            this.debugCode("supportsConvert(" + n + ", " + n2 + ')');
        }
        return true;
    }

    @Override
    public boolean supportsTableCorrelationNames() {
        this.debugCodeCall("supportsTableCorrelationNames");
        return true;
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() {
        this.debugCodeCall("supportsDifferentTableCorrelationNames");
        return false;
    }

    @Override
    public boolean supportsExpressionsInOrderBy() {
        this.debugCodeCall("supportsExpressionsInOrderBy");
        return true;
    }

    @Override
    public boolean supportsOrderByUnrelated() {
        this.debugCodeCall("supportsOrderByUnrelated");
        return true;
    }

    @Override
    public boolean supportsGroupBy() {
        this.debugCodeCall("supportsGroupBy");
        return true;
    }

    @Override
    public boolean supportsGroupByUnrelated() {
        this.debugCodeCall("supportsGroupByUnrelated");
        return true;
    }

    @Override
    public boolean supportsGroupByBeyondSelect() {
        this.debugCodeCall("supportsGroupByBeyondSelect");
        return true;
    }

    @Override
    public boolean supportsLikeEscapeClause() {
        this.debugCodeCall("supportsLikeEscapeClause");
        return true;
    }

    @Override
    public boolean supportsMultipleResultSets() {
        this.debugCodeCall("supportsMultipleResultSets");
        return false;
    }

    @Override
    public boolean supportsMultipleTransactions() {
        this.debugCodeCall("supportsMultipleTransactions");
        return true;
    }

    @Override
    public boolean supportsNonNullableColumns() {
        this.debugCodeCall("supportsNonNullableColumns");
        return true;
    }

    @Override
    public boolean supportsMinimumSQLGrammar() {
        this.debugCodeCall("supportsMinimumSQLGrammar");
        return true;
    }

    @Override
    public boolean supportsCoreSQLGrammar() {
        this.debugCodeCall("supportsCoreSQLGrammar");
        return true;
    }

    @Override
    public boolean supportsExtendedSQLGrammar() {
        this.debugCodeCall("supportsExtendedSQLGrammar");
        return false;
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() {
        this.debugCodeCall("supportsANSI92EntryLevelSQL");
        return true;
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() {
        this.debugCodeCall("supportsANSI92IntermediateSQL");
        return false;
    }

    @Override
    public boolean supportsANSI92FullSQL() {
        this.debugCodeCall("supportsANSI92FullSQL");
        return false;
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() {
        this.debugCodeCall("supportsIntegrityEnhancementFacility");
        return true;
    }

    @Override
    public boolean supportsOuterJoins() {
        this.debugCodeCall("supportsOuterJoins");
        return true;
    }

    @Override
    public boolean supportsFullOuterJoins() {
        this.debugCodeCall("supportsFullOuterJoins");
        return false;
    }

    @Override
    public boolean supportsLimitedOuterJoins() {
        this.debugCodeCall("supportsLimitedOuterJoins");
        return true;
    }

    @Override
    public String getSchemaTerm() {
        this.debugCodeCall("getSchemaTerm");
        return "schema";
    }

    @Override
    public String getProcedureTerm() {
        this.debugCodeCall("getProcedureTerm");
        return "procedure";
    }

    @Override
    public String getCatalogTerm() {
        this.debugCodeCall("getCatalogTerm");
        return "catalog";
    }

    @Override
    public boolean isCatalogAtStart() {
        this.debugCodeCall("isCatalogAtStart");
        return true;
    }

    @Override
    public String getCatalogSeparator() {
        this.debugCodeCall("getCatalogSeparator");
        return ".";
    }

    @Override
    public boolean supportsSchemasInDataManipulation() {
        this.debugCodeCall("supportsSchemasInDataManipulation");
        return true;
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() {
        this.debugCodeCall("supportsSchemasInProcedureCalls");
        return true;
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() {
        this.debugCodeCall("supportsSchemasInTableDefinitions");
        return true;
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() {
        this.debugCodeCall("supportsSchemasInIndexDefinitions");
        return true;
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() {
        this.debugCodeCall("supportsSchemasInPrivilegeDefinitions");
        return true;
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() {
        this.debugCodeCall("supportsCatalogsInDataManipulation");
        return true;
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() {
        this.debugCodeCall("supportsCatalogsInProcedureCalls");
        return false;
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() {
        this.debugCodeCall("supportsCatalogsInTableDefinitions");
        return true;
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() {
        this.debugCodeCall("supportsCatalogsInIndexDefinitions");
        return true;
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() {
        this.debugCodeCall("supportsCatalogsInPrivilegeDefinitions");
        return true;
    }

    @Override
    public boolean supportsPositionedDelete() {
        this.debugCodeCall("supportsPositionedDelete");
        return false;
    }

    @Override
    public boolean supportsPositionedUpdate() {
        this.debugCodeCall("supportsPositionedUpdate");
        return false;
    }

    @Override
    public boolean supportsSelectForUpdate() {
        this.debugCodeCall("supportsSelectForUpdate");
        return true;
    }

    @Override
    public boolean supportsStoredProcedures() {
        this.debugCodeCall("supportsStoredProcedures");
        return false;
    }

    @Override
    public boolean supportsSubqueriesInComparisons() {
        this.debugCodeCall("supportsSubqueriesInComparisons");
        return true;
    }

    @Override
    public boolean supportsSubqueriesInExists() {
        this.debugCodeCall("supportsSubqueriesInExists");
        return true;
    }

    @Override
    public boolean supportsSubqueriesInIns() {
        this.debugCodeCall("supportsSubqueriesInIns");
        return true;
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() {
        this.debugCodeCall("supportsSubqueriesInQuantifieds");
        return true;
    }

    @Override
    public boolean supportsCorrelatedSubqueries() {
        this.debugCodeCall("supportsCorrelatedSubqueries");
        return true;
    }

    @Override
    public boolean supportsUnion() {
        this.debugCodeCall("supportsUnion");
        return true;
    }

    @Override
    public boolean supportsUnionAll() {
        this.debugCodeCall("supportsUnionAll");
        return true;
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() {
        this.debugCodeCall("supportsOpenCursorsAcrossCommit");
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() {
        this.debugCodeCall("supportsOpenCursorsAcrossRollback");
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() {
        this.debugCodeCall("supportsOpenStatementsAcrossCommit");
        return true;
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() {
        this.debugCodeCall("supportsOpenStatementsAcrossRollback");
        return true;
    }

    @Override
    public boolean supportsTransactions() {
        this.debugCodeCall("supportsTransactions");
        return true;
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int n) throws SQLException {
        this.debugCodeCall("supportsTransactionIsolationLevel");
        switch (n) {
            case 1: 
            case 2: 
            case 4: 
            case 6: 
            case 8: {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() {
        this.debugCodeCall("supportsDataDefinitionAndDataManipulationTransactions");
        return false;
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() {
        this.debugCodeCall("supportsDataManipulationTransactionsOnly");
        return true;
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() {
        this.debugCodeCall("dataDefinitionCausesTransactionCommit");
        return true;
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() {
        this.debugCodeCall("dataDefinitionIgnoredInTransactions");
        return false;
    }

    @Override
    public boolean supportsResultSetType(int n) {
        this.debugCodeCall("supportsResultSetType", n);
        return n != 1005;
    }

    @Override
    public boolean supportsResultSetConcurrency(int n, int n2) {
        if (this.isDebugEnabled()) {
            this.debugCode("supportsResultSetConcurrency(" + n + ", " + n2 + ')');
        }
        return n != 1005;
    }

    @Override
    public boolean ownUpdatesAreVisible(int n) {
        this.debugCodeCall("ownUpdatesAreVisible", n);
        return true;
    }

    @Override
    public boolean ownDeletesAreVisible(int n) {
        this.debugCodeCall("ownDeletesAreVisible", n);
        return false;
    }

    @Override
    public boolean ownInsertsAreVisible(int n) {
        this.debugCodeCall("ownInsertsAreVisible", n);
        return false;
    }

    @Override
    public boolean othersUpdatesAreVisible(int n) {
        this.debugCodeCall("othersUpdatesAreVisible", n);
        return false;
    }

    @Override
    public boolean othersDeletesAreVisible(int n) {
        this.debugCodeCall("othersDeletesAreVisible", n);
        return false;
    }

    @Override
    public boolean othersInsertsAreVisible(int n) {
        this.debugCodeCall("othersInsertsAreVisible", n);
        return false;
    }

    @Override
    public boolean updatesAreDetected(int n) {
        this.debugCodeCall("updatesAreDetected", n);
        return false;
    }

    @Override
    public boolean deletesAreDetected(int n) {
        this.debugCodeCall("deletesAreDetected", n);
        return false;
    }

    @Override
    public boolean insertsAreDetected(int n) {
        this.debugCodeCall("insertsAreDetected", n);
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() {
        this.debugCodeCall("supportsBatchUpdates");
        return true;
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() {
        this.debugCodeCall("doesMaxRowSizeIncludeBlobs");
        return false;
    }

    @Override
    public int getDefaultTransactionIsolation() {
        this.debugCodeCall("getDefaultTransactionIsolation");
        return 2;
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        this.debugCodeCall("supportsMixedCaseIdentifiers");
        Session.StaticSettings staticSettings = this.conn.getStaticSettings();
        return !staticSettings.databaseToUpper && !staticSettings.databaseToLower && !staticSettings.caseInsensitiveIdentifiers;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        this.debugCodeCall("storesUpperCaseIdentifiers");
        return this.conn.getStaticSettings().databaseToUpper;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        this.debugCodeCall("storesLowerCaseIdentifiers");
        return this.conn.getStaticSettings().databaseToLower;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        this.debugCodeCall("storesMixedCaseIdentifiers");
        Session.StaticSettings staticSettings = this.conn.getStaticSettings();
        return !staticSettings.databaseToUpper && !staticSettings.databaseToLower && staticSettings.caseInsensitiveIdentifiers;
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        this.debugCodeCall("supportsMixedCaseQuotedIdentifiers");
        return !this.conn.getStaticSettings().caseInsensitiveIdentifiers;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        this.debugCodeCall("storesUpperCaseQuotedIdentifiers");
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        this.debugCodeCall("storesLowerCaseQuotedIdentifiers");
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        this.debugCodeCall("storesMixedCaseQuotedIdentifiers");
        return this.conn.getStaticSettings().caseInsensitiveIdentifiers;
    }

    @Override
    public int getMaxBinaryLiteralLength() {
        this.debugCodeCall("getMaxBinaryLiteralLength");
        return 0;
    }

    @Override
    public int getMaxCharLiteralLength() {
        this.debugCodeCall("getMaxCharLiteralLength");
        return 0;
    }

    @Override
    public int getMaxColumnNameLength() {
        this.debugCodeCall("getMaxColumnNameLength");
        return 0;
    }

    @Override
    public int getMaxColumnsInGroupBy() {
        this.debugCodeCall("getMaxColumnsInGroupBy");
        return 0;
    }

    @Override
    public int getMaxColumnsInIndex() {
        this.debugCodeCall("getMaxColumnsInIndex");
        return 0;
    }

    @Override
    public int getMaxColumnsInOrderBy() {
        this.debugCodeCall("getMaxColumnsInOrderBy");
        return 0;
    }

    @Override
    public int getMaxColumnsInSelect() {
        this.debugCodeCall("getMaxColumnsInSelect");
        return 0;
    }

    @Override
    public int getMaxColumnsInTable() {
        this.debugCodeCall("getMaxColumnsInTable");
        return 0;
    }

    @Override
    public int getMaxConnections() {
        this.debugCodeCall("getMaxConnections");
        return 0;
    }

    @Override
    public int getMaxCursorNameLength() {
        this.debugCodeCall("getMaxCursorNameLength");
        return 0;
    }

    @Override
    public int getMaxIndexLength() {
        this.debugCodeCall("getMaxIndexLength");
        return 0;
    }

    @Override
    public int getMaxSchemaNameLength() {
        this.debugCodeCall("getMaxSchemaNameLength");
        return 0;
    }

    @Override
    public int getMaxProcedureNameLength() {
        this.debugCodeCall("getMaxProcedureNameLength");
        return 0;
    }

    @Override
    public int getMaxCatalogNameLength() {
        this.debugCodeCall("getMaxCatalogNameLength");
        return 0;
    }

    @Override
    public int getMaxRowSize() {
        this.debugCodeCall("getMaxRowSize");
        return 0;
    }

    @Override
    public int getMaxStatementLength() {
        this.debugCodeCall("getMaxStatementLength");
        return 0;
    }

    @Override
    public int getMaxStatements() {
        this.debugCodeCall("getMaxStatements");
        return 0;
    }

    @Override
    public int getMaxTableNameLength() {
        this.debugCodeCall("getMaxTableNameLength");
        return 0;
    }

    @Override
    public int getMaxTablesInSelect() {
        this.debugCodeCall("getMaxTablesInSelect");
        return 0;
    }

    @Override
    public int getMaxUserNameLength() {
        this.debugCodeCall("getMaxUserNameLength");
        return 0;
    }

    @Override
    public boolean supportsSavepoints() {
        this.debugCodeCall("supportsSavepoints");
        return true;
    }

    @Override
    public boolean supportsNamedParameters() {
        this.debugCodeCall("supportsNamedParameters");
        return false;
    }

    @Override
    public boolean supportsMultipleOpenResults() {
        this.debugCodeCall("supportsMultipleOpenResults");
        return false;
    }

    @Override
    public boolean supportsGetGeneratedKeys() {
        this.debugCodeCall("supportsGetGeneratedKeys");
        return true;
    }

    @Override
    public ResultSet getSuperTypes(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getSuperTypes(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getSuperTypes(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getSuperTables(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getSuperTables(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getSuperTables(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getAttributes(String string, String string2, String string3, String string4) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getAttributes(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ')');
            }
            return this.getResultSet(this.meta.getAttributes(string, string2, string3, string4));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean supportsResultSetHoldability(int n) {
        this.debugCodeCall("supportsResultSetHoldability", n);
        return n == 2;
    }

    @Override
    public int getResultSetHoldability() {
        this.debugCodeCall("getResultSetHoldability");
        return 2;
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        try {
            this.debugCodeCall("getDatabaseMajorVersion");
            return this.meta.getDatabaseMajorVersion();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        try {
            this.debugCodeCall("getDatabaseMinorVersion");
            return this.meta.getDatabaseMinorVersion();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getJDBCMajorVersion() {
        this.debugCodeCall("getJDBCMajorVersion");
        return 4;
    }

    @Override
    public int getJDBCMinorVersion() {
        this.debugCodeCall("getJDBCMinorVersion");
        return 2;
    }

    @Override
    public int getSQLStateType() {
        this.debugCodeCall("getSQLStateType");
        return 2;
    }

    @Override
    public boolean locatorsUpdateCopy() {
        this.debugCodeCall("locatorsUpdateCopy");
        return false;
    }

    @Override
    public boolean supportsStatementPooling() {
        this.debugCodeCall("supportsStatementPooling");
        return false;
    }

    private void checkClosed() {
        this.conn.checkClosed();
    }

    @Override
    public RowIdLifetime getRowIdLifetime() {
        this.debugCodeCall("getRowIdLifetime");
        return RowIdLifetime.ROWID_UNSUPPORTED;
    }

    @Override
    public ResultSet getSchemas(String string, String string2) throws SQLException {
        try {
            this.debugCodeCall("getSchemas(String,String)");
            return this.getResultSet(this.meta.getSchemas(string, string2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() {
        this.debugCodeCall("supportsStoredFunctionsUsingCallSyntax");
        return true;
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() {
        this.debugCodeCall("autoCommitFailureClosesAllResultSets");
        return false;
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        Properties properties = this.conn.getClientInfo();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("MAX_LEN", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("DEFAULT_VALUE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("DESCRIPTION", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("VALUE", TypeInfo.TYPE_VARCHAR);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            simpleResult.addRow(ValueVarchar.get((String)entry.getKey()), ValueInteger.get(Integer.MAX_VALUE), ValueVarchar.EMPTY, ValueVarchar.EMPTY, ValueVarchar.get((String)entry.getValue()));
        }
        int n = JdbcDatabaseMetaData.getNextId(4);
        this.debugCodeAssign("ResultSet", 4, n, "getClientInfoProperties()");
        return new JdbcResultSet(this.conn, null, null, (ResultInterface)simpleResult, n, true, false, false);
    }

    @Override
    public <T> T unwrap(Class<T> clazz) throws SQLException {
        try {
            if (this.isWrapperFor(clazz)) {
                return (T)this;
            }
            throw DbException.getInvalidValueException("iface", clazz);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return clazz != null && clazz.isAssignableFrom(this.getClass());
    }

    @Override
    public ResultSet getFunctionColumns(String string, String string2, String string3, String string4) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getFunctionColumns(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ')');
            }
            return this.getResultSet(this.meta.getFunctionColumns(string, string2, string3, string4));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getFunctions(String string, String string2, String string3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getFunctions(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ')');
            }
            return this.getResultSet(this.meta.getFunctions(string, string2, string3));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean generatedKeyAlwaysReturned() {
        return true;
    }

    @Override
    public ResultSet getPseudoColumns(String string, String string2, String string3, String string4) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getPseudoColumns(" + JdbcDatabaseMetaData.quote(string) + ", " + JdbcDatabaseMetaData.quote(string2) + ", " + JdbcDatabaseMetaData.quote(string3) + ", " + JdbcDatabaseMetaData.quote(string4) + ')');
            }
            return this.getResultSet(this.meta.getPseudoColumns(string, string2, string3, string4));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    public String toString() {
        return this.getTraceObjectName() + ": " + this.conn;
    }

    private JdbcResultSet getResultSet(ResultInterface resultInterface) {
        return new JdbcResultSet(this.conn, null, null, resultInterface, JdbcDatabaseMetaData.getNextId(4), true, false, false);
    }
}

