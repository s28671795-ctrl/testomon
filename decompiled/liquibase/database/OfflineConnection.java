/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.OfflineChangeLogHistoryService;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.SnapshotParser;
import liquibase.parser.SnapshotParserFactory;
import liquibase.resource.ResourceAccessor;
import liquibase.servicelocator.LiquibaseService;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.EmptyDatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

@LiquibaseService(skip=true)
public class OfflineConnection
implements DatabaseConnection {
    private String url = null;
    private String databaseShortName = null;
    private final Map<String, String> databaseParams = new HashMap<String, String>();
    private DatabaseSnapshot snapshot;
    private OutputLiquibaseSql outputLiquibaseSql = OutputLiquibaseSql.NONE;
    private String changeLogFile = "databasechangelog.csv";
    private boolean caseSensitive;
    private String productName;
    private String productVersion;
    private int databaseMajorVersion = 999;
    private int databaseMinorVersion = 999;
    private String catalog;
    private boolean sendsStringParametersAsUnicode = true;
    private String connectionUserName;
    private static final String OFFLINE_COMMAND_REGEX = "offline:(\\w+)\\??(.*)";
    private static final Pattern OFFLINE_COMMAND_PATTERN = Pattern.compile("offline:(\\w+)\\??(.*)");

    public OfflineConnection() {
    }

    public OfflineConnection(String url2, ResourceAccessor resourceAccessor) {
        this.url = url2;
        Matcher matcher = OFFLINE_COMMAND_PATTERN.matcher(url2);
        if (!matcher.matches()) {
            throw new UnexpectedLiquibaseException("Could not parse offline url " + url2);
        }
        this.databaseShortName = matcher.group(1).toLowerCase();
        String params = StringUtil.trimToNull(matcher.group(2));
        try {
            HashMap<String, String> params1 = new HashMap<String, String>();
            if (params != null) {
                String[] keyValues;
                for (String param2 : keyValues = params.split("&")) {
                    String[] split = param2.split("=");
                    params1.put(URLDecoder.decode(split[0], StandardCharsets.UTF_8.name()), URLDecoder.decode(split[1], StandardCharsets.UTF_8.name()));
                }
            }
            this.productName = "Offline " + this.databaseShortName;
            for (Map.Entry paramEntry : params1.entrySet()) {
                if ("version".equals(paramEntry.getKey())) {
                    this.productVersion = (String)paramEntry.getValue();
                    String[] versionParts = this.productVersion.split("\\.");
                    try {
                        this.databaseMajorVersion = Integer.parseInt(versionParts[0]);
                        if (versionParts.length <= 1) continue;
                        this.databaseMinorVersion = Integer.parseInt(versionParts[1]);
                    }
                    catch (NumberFormatException e) {
                        Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot parse database version " + this.productVersion);
                    }
                    continue;
                }
                if ("productName".equals(paramEntry.getKey())) {
                    this.productName = (String)paramEntry.getValue();
                    continue;
                }
                if ("catalog".equals(paramEntry.getKey())) {
                    this.catalog = (String)params1.get("catalog");
                    continue;
                }
                if ("caseSensitive".equals(paramEntry.getKey())) {
                    this.caseSensitive = Boolean.parseBoolean((String)paramEntry.getValue());
                    continue;
                }
                if ("changeLogFile".equals(paramEntry.getKey())) {
                    this.changeLogFile = (String)paramEntry.getValue();
                    continue;
                }
                if ("outputLiquibaseSql".equals(paramEntry.getKey())) {
                    this.outputLiquibaseSql = OutputLiquibaseSql.fromString((String)paramEntry.getValue());
                    continue;
                }
                if ("snapshot".equals(paramEntry.getKey())) {
                    String snapshotFile = (String)paramEntry.getValue();
                    try {
                        SnapshotParser parser = SnapshotParserFactory.getInstance().getParser(snapshotFile, resourceAccessor);
                        this.snapshot = parser.parse(snapshotFile, resourceAccessor);
                        this.productVersion = this.snapshot.getDatabase().getDatabaseProductVersion();
                        this.snapshot.getDatabase().setConnection(this);
                        for (Catalog catalog : this.snapshot.get(Catalog.class)) {
                            if (!catalog.isDefault()) continue;
                            this.catalog = catalog.getName();
                        }
                        continue;
                    }
                    catch (LiquibaseException e) {
                        throw new UnexpectedLiquibaseException("Cannot parse snapshot " + url2, e);
                    }
                }
                if ("sendsStringParametersAsUnicode".equals(paramEntry.getKey())) {
                    this.sendsStringParametersAsUnicode = Boolean.parseBoolean((String)paramEntry.getValue());
                    continue;
                }
                this.databaseParams.put((String)paramEntry.getKey(), (String)paramEntry.getValue());
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public boolean isCorrectDatabaseImplementation(Database database) {
        return database.getShortName().equalsIgnoreCase(this.databaseShortName);
    }

    @Override
    public void open(String url2, Driver driverObject, Properties driverProperties) throws DatabaseException {
    }

    @Override
    public void attached(Database database) {
        for (Map.Entry<String, String> param2 : this.databaseParams.entrySet()) {
            try {
                ObjectUtil.setProperty((Object)database, param2.getKey(), param2.getValue());
            }
            catch (Exception e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Error setting database parameter " + param2.getKey() + ": " + e.getMessage(), e);
            }
        }
        if (database instanceof AbstractJdbcDatabase) {
            ((AbstractJdbcDatabase)database).setCaseSensitive(this.caseSensitive);
        }
        if (this.snapshot == null) {
            try {
                this.snapshot = new EmptyDatabaseSnapshot(database);
            }
            catch (DatabaseException | InvalidExampleException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).register(this.createChangeLogHistoryService(database));
    }

    protected ChangeLogHistoryService createChangeLogHistoryService(Database database) {
        return new OfflineChangeLogHistoryService(database, new File(this.changeLogFile), this.outputLiquibaseSql != OutputLiquibaseSql.NONE, this.outputLiquibaseSql == OutputLiquibaseSql.ALL);
    }

    public DatabaseSnapshot getSnapshot(DatabaseObject[] examples) {
        return this.snapshot.clone(examples);
    }

    public void setSnapshot(DatabaseSnapshot snapshot) {
        this.snapshot = snapshot;
        this.snapshot.getDatabase().setConnection(this);
        for (Catalog catalog : this.snapshot.get(Catalog.class)) {
            if (!catalog.isDefault()) continue;
            this.catalog = catalog.getName();
        }
    }

    @Override
    public void close() throws DatabaseException {
    }

    @Override
    public void commit() throws DatabaseException {
    }

    @Override
    public boolean getAutoCommit() throws DatabaseException {
        return false;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws DatabaseException {
    }

    @Override
    public String getCatalog() throws DatabaseException {
        return this.catalog;
    }

    public String getSchema() {
        if (this.snapshot == null) {
            return null;
        }
        for (Schema schema : this.snapshot.get(Schema.class)) {
            if (!schema.isDefault()) continue;
            return schema.getName();
        }
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws DatabaseException {
        return sql;
    }

    @Override
    public void rollback() throws DatabaseException {
    }

    @Override
    public String getDatabaseProductName() throws DatabaseException {
        return this.productName;
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        return this.productVersion;
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        return this.databaseMajorVersion;
    }

    public void setDatabaseMajorVersion(int databaseMajorVersion) {
        this.databaseMajorVersion = databaseMajorVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        return this.databaseMinorVersion;
    }

    public void setDatabaseMinorVersion(int databaseMinorVersion) {
        this.databaseMinorVersion = databaseMinorVersion;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public String getConnectionUserName() {
        return this.connectionUserName;
    }

    public void setConnectionUserName(String connectionUserName) {
        this.connectionUserName = StringUtil.isEmpty(connectionUserName) ? null : connectionUserName;
    }

    @Override
    public boolean isClosed() throws DatabaseException {
        return false;
    }

    public boolean getSendsStringParametersAsUnicode() {
        return this.sendsStringParametersAsUnicode;
    }

    public void setSendsStringParametersAsUnicode(boolean sendsStringParametersAsUnicode) {
        this.sendsStringParametersAsUnicode = sendsStringParametersAsUnicode;
    }

    public boolean isCaseSensitive() {
        return this.caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    private static enum OutputLiquibaseSql {
        NONE,
        DATA_ONLY,
        ALL;


        public static OutputLiquibaseSql fromString(String s2) {
            if (s2 == null) {
                return null;
            }
            switch (s2 = s2.toUpperCase()) {
                case "TRUE": {
                    return ALL;
                }
                case "FALSE": {
                    return NONE;
                }
            }
            return OutputLiquibaseSql.valueOf(s2);
        }
    }
}

