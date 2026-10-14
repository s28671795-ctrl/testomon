/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.jvm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.ConnectionPatterns;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;

public class JdbcConnection
implements DatabaseConnection {
    private Connection con;
    private static final Pattern PROXY_USER = Pattern.compile(".*(?:thin|oci)\\:(.+)/@.*");
    private static final List<ConnectionPatterns> JDBC_CONNECTION_PATTERNS = Scope.getCurrentScope().getServiceLocator().findInstances(ConnectionPatterns.class);

    public JdbcConnection() {
    }

    public JdbcConnection(Connection connection) {
        this.con = connection;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void open(String url2, Driver driverObject, Properties driverProperties) throws DatabaseException {
        String driverClassName = driverObject.getClass().getName();
        String errorMessage = "Connection could not be created to " + JdbcConnection.sanitizeUrl(url2) + " with driver " + driverClassName;
        try {
            this.con = driverObject.connect(url2, driverProperties);
            if (this.con == null) {
                throw new DatabaseException(errorMessage + ".  Possibly the wrong driver for the given database URL");
            }
        }
        catch (SQLException sqle) {
            if (driverClassName.equals("org.h2.Driver")) {
                errorMessage = errorMessage + ". Make sure your H2 database is active and accessible by opening a new terminal window, run \"liquibase init start-h2\", and then return to this terminal window to run commands";
            }
            throw new DatabaseException(errorMessage + ".  " + sqle.getMessage(), sqle);
        }
    }

    @Override
    public void attached(Database database) {
        try {
            database.addReservedWords(Arrays.asList(this.getWrappedConnection().getMetaData().getSQLKeywords().toUpperCase().split(",\\s*")));
        }
        catch (SQLException e) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error fetching reserved words list from JDBC driver", e);
        }
    }

    @Override
    public String getDatabaseProductName() throws DatabaseException {
        try {
            return this.con.getMetaData().getDatabaseProductName();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        try {
            return this.con.getMetaData().getDatabaseProductVersion();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        try {
            return this.con.getMetaData().getDatabaseMajorVersion();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        try {
            return this.con.getMetaData().getDatabaseMinorVersion();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String getURL() {
        try {
            String url2 = this.getConnectionUrl();
            url2 = JdbcConnection.stripPasswordPropFromJdbcUrl(url2);
            return url2;
        }
        catch (SQLException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static String sanitizeUrl(String url2) {
        return JdbcConnection.obfuscateCredentialsPropFromJdbcUrl(url2);
    }

    private static String obfuscateCredentialsPropFromJdbcUrl(String jdbcUrl) {
        Pattern pattern;
        Matcher actualMatcher;
        Matcher matcher;
        Pattern jdbcUrlPattern;
        if (jdbcUrl == null || jdbcUrl != null && jdbcUrl.equals("")) {
            return jdbcUrl;
        }
        Matcher m4 = PROXY_USER.matcher(jdbcUrl);
        if (m4.matches()) {
            return jdbcUrl;
        }
        if (!JDBC_CONNECTION_PATTERNS.isEmpty()) {
            for (ConnectionPatterns jdbcConnectionPattern : JDBC_CONNECTION_PATTERNS) {
                for (Map.Entry<Pattern, Pattern> entry : jdbcConnectionPattern.getJdbcBlankToObfuscatePatterns()) {
                    jdbcUrlPattern = entry.getKey();
                    matcher = jdbcUrlPattern.matcher(jdbcUrl);
                    if (!matcher.matches() || !(actualMatcher = (pattern = entry.getValue()).matcher(jdbcUrl)).find()) continue;
                    jdbcUrl = jdbcUrl.replace(actualMatcher.group(1) + actualMatcher.group(2) + actualMatcher.group(3), "*****" + actualMatcher.group(2) + "*****");
                }
            }
        }
        if (!JDBC_CONNECTION_PATTERNS.isEmpty()) {
            for (ConnectionPatterns jdbcConnectionPattern : JDBC_CONNECTION_PATTERNS) {
                for (Map.Entry<Pattern, Pattern> entry : jdbcConnectionPattern.getJdbcObfuscatePatterns()) {
                    jdbcUrlPattern = entry.getKey();
                    matcher = jdbcUrlPattern.matcher(jdbcUrl);
                    if (!matcher.matches() || !(actualMatcher = (pattern = entry.getValue()).matcher(jdbcUrl)).find()) continue;
                    jdbcUrl = jdbcUrl.replace("=" + actualMatcher.group(2), "=*****");
                    jdbcUrl = jdbcUrl.replace(":" + actualMatcher.group(2), ":*****");
                }
            }
        }
        return jdbcUrl;
    }

    private static String stripPasswordPropFromJdbcUrl(String jdbcUrl) {
        if (jdbcUrl == null || jdbcUrl != null && jdbcUrl.equals("")) {
            return jdbcUrl;
        }
        Matcher m4 = PROXY_USER.matcher(jdbcUrl);
        if (m4.matches()) {
            return jdbcUrl;
        }
        if (!JDBC_CONNECTION_PATTERNS.isEmpty()) {
            for (ConnectionPatterns jdbcConnectionPattern : JDBC_CONNECTION_PATTERNS) {
                for (Map.Entry<Pattern, Pattern> entry : jdbcConnectionPattern.getJdbcBlankPatterns()) {
                    Pattern jdbcUrlPattern = entry.getKey();
                    Matcher matcher = jdbcUrlPattern.matcher(jdbcUrl);
                    if (!matcher.matches()) continue;
                    Pattern pattern = entry.getValue();
                    jdbcUrl = pattern.matcher(jdbcUrl).replaceAll("");
                }
            }
        }
        return jdbcUrl;
    }

    protected String getConnectionUrl() throws SQLException {
        return this.con.getMetaData().getURL();
    }

    @Override
    public String getConnectionUserName() {
        try {
            return this.con.getMetaData().getUserName();
        }
        catch (SQLException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public Connection getWrappedConnection() {
        return this.con;
    }

    public void clearWarnings() throws DatabaseException {
        try {
            this.con.clearWarnings();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void close() throws DatabaseException {
        this.rollback();
        try {
            this.con.close();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void commit() throws DatabaseException {
        try {
            if (!this.con.getAutoCommit()) {
                this.con.commit();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Statement createStatement() throws DatabaseException {
        try {
            return this.con.createStatement();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws DatabaseException {
        try {
            return this.con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws DatabaseException {
        try {
            return this.con.createStatement(resultSetType, resultSetConcurrency);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean getAutoCommit() throws DatabaseException {
        try {
            return this.con.getAutoCommit();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String getCatalog() throws DatabaseException {
        try {
            return this.con.getCatalog();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public int getHoldability() throws DatabaseException {
        try {
            return this.con.getHoldability();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public DatabaseMetaData getMetaData() throws DatabaseException {
        try {
            return this.con.getMetaData();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public int getTransactionIsolation() throws DatabaseException {
        try {
            return this.con.getTransactionIsolation();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Map<String, Class<?>> getTypeMap() throws DatabaseException {
        try {
            return this.con.getTypeMap();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public SQLWarning getWarnings() throws DatabaseException {
        try {
            return this.con.getWarnings();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean isClosed() throws DatabaseException {
        try {
            return this.con.isClosed();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public boolean isReadOnly() throws DatabaseException {
        try {
            return this.con.isReadOnly();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String nativeSQL(String sql) throws DatabaseException {
        try {
            return this.con.nativeSQL(sql);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws DatabaseException {
        try {
            return this.con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws DatabaseException {
        try {
            return this.con.prepareCall(sql, resultSetType, resultSetConcurrency);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public CallableStatement prepareCall(String sql) throws DatabaseException {
        try {
            return this.con.prepareCall(sql);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws DatabaseException {
        try {
            return this.con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws DatabaseException {
        try {
            return this.con.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws DatabaseException {
        try {
            return this.con.prepareStatement(sql, autoGeneratedKeys);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws DatabaseException {
        try {
            return this.con.prepareStatement(sql, columnIndexes);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws DatabaseException {
        try {
            return this.con.prepareStatement(sql, columnNames);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws DatabaseException {
        try {
            return this.con.prepareStatement(sql);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void releaseSavepoint(Savepoint savepoint) throws DatabaseException {
        try {
            this.con.releaseSavepoint(savepoint);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void rollback() throws DatabaseException {
        try {
            if (!this.con.isClosed() && !this.con.getAutoCommit()) {
                this.con.rollback();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void rollback(Savepoint savepoint) throws DatabaseException {
        try {
            if (!this.con.getAutoCommit()) {
                this.con.rollback(savepoint);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {
        try {
            this.con.setAutoCommit(autoCommit);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void setCatalog(String catalog) throws DatabaseException {
        try {
            this.con.setCatalog(catalog);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void setHoldability(int holdability) throws DatabaseException {
        try {
            this.con.setHoldability(holdability);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void setReadOnly(boolean readOnly) throws DatabaseException {
        try {
            this.con.setReadOnly(readOnly);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Savepoint setSavepoint() throws DatabaseException {
        try {
            return this.con.setSavepoint();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Savepoint setSavepoint(String name) throws DatabaseException {
        try {
            return this.con.setSavepoint(name);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void setTransactionIsolation(int level) throws DatabaseException {
        try {
            this.con.setTransactionIsolation(level);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void setTypeMap(Map<String, Class<?>> map) throws DatabaseException {
        try {
            this.con.setTypeMap(map);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Connection getUnderlyingConnection() {
        return this.con;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JdbcConnection)) {
            return false;
        }
        Connection underlyingConnection = this.getUnderlyingConnection();
        if (underlyingConnection == null) {
            return ((JdbcConnection)obj).getUnderlyingConnection() == null;
        }
        return underlyingConnection.equals(((JdbcConnection)obj).getUnderlyingConnection());
    }

    public int hashCode() {
        Connection underlyingConnection = this.getUnderlyingConnection();
        try {
            if (underlyingConnection == null || underlyingConnection.isClosed()) {
                return super.hashCode();
            }
        }
        catch (SQLException e) {
            return super.hashCode();
        }
        return underlyingConnection.hashCode();
    }

    public boolean supportsBatchUpdates() throws DatabaseException {
        try {
            return this.getUnderlyingConnection().getMetaData().supportsBatchUpdates();
        }
        catch (SQLException e) {
            throw new DatabaseException("Asking the JDBC driver if it supports batched updates has failed.", e);
        }
    }
}

