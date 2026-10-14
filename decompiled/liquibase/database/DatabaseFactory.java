/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.database.ConnectionServiceFactory;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.InternalDatabase;
import liquibase.database.LiquibaseExtDriver;
import liquibase.database.OfflineConnection;
import liquibase.database.core.UnsupportedDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.logging.Logger;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StringUtil;
import liquibase.util.SystemUtil;

public class DatabaseFactory
implements SingletonObject {
    private static final Logger LOG = Scope.getCurrentScope().getLog(DatabaseFactory.class);
    private static DatabaseFactory instance;
    private final Map<String, SortedSet<Database>> implementedDatabases = new HashMap<String, SortedSet<Database>>();
    private final Map<String, SortedSet<Database>> internalDatabases = new HashMap<String, SortedSet<Database>>();
    private Database specifiedDbClass;

    private DatabaseFactory() {
        try {
            for (Database database : Scope.getCurrentScope().getServiceLocator().findInstances(Database.class)) {
                this.register(database);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseFactory();
        }
        return instance;
    }

    public static synchronized void setInstance(DatabaseFactory databaseFactory) {
        instance = databaseFactory;
    }

    public static synchronized void reset() {
        instance = new DatabaseFactory();
    }

    public List<Database> getImplementedDatabases() {
        ArrayList<Database> returnList = new ArrayList<Database>();
        for (SortedSet<Database> set : this.implementedDatabases.values()) {
            returnList.add((Database)set.iterator().next());
        }
        return returnList;
    }

    public List<Database> getInternalDatabases() {
        ArrayList<Database> returnList = new ArrayList<Database>();
        for (SortedSet<Database> set : this.internalDatabases.values()) {
            returnList.add((Database)set.iterator().next());
        }
        return returnList;
    }

    public void register(Database database) {
        Map<String, SortedSet<Database>> map = null;
        map = database instanceof InternalDatabase ? this.internalDatabases : this.implementedDatabases;
        if (!map.containsKey(database.getShortName())) {
            map.put(database.getShortName(), new TreeSet<Database>((SortedSet<Database>)new TreeSet<Database>(new DatabaseComparator())));
        }
        map.get(database.getShortName()).add(database);
    }

    public Database findCorrectDatabaseImplementation(DatabaseConnection connection) throws DatabaseException {
        Database returnDatabase;
        TreeSet<Database> foundDatabases = new TreeSet<Database>(new DatabaseComparator());
        for (Database implementedDatabase : this.getImplementedDatabases()) {
            if (connection instanceof OfflineConnection) {
                if (!((OfflineConnection)connection).isCorrectDatabaseImplementation(implementedDatabase)) continue;
                foundDatabases.add(implementedDatabase);
                continue;
            }
            if (!implementedDatabase.isCorrectDatabaseImplementation(connection)) continue;
            foundDatabases.add(implementedDatabase);
        }
        if (foundDatabases.isEmpty()) {
            LOG.warning("Unknown database: " + connection.getDatabaseProductName());
            UnsupportedDatabase unsupportedDB = new UnsupportedDatabase();
            unsupportedDB.setConnection(connection);
            return unsupportedDB;
        }
        try {
            returnDatabase = (Database)((Database)foundDatabases.iterator().next()).getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
        returnDatabase.setConnection(connection);
        return returnDatabase;
    }

    public Database openDatabase(String url2, String username, String password, String propertyProviderClass, ResourceAccessor resourceAccessor) throws DatabaseException {
        return this.openDatabase(url2, username, password, null, null, null, propertyProviderClass, resourceAccessor);
    }

    public Database openDatabase(String url2, String username, String password, String driver, String databaseClass, String driverPropertiesFile, String propertyProviderClass, ResourceAccessor resourceAccessor) throws DatabaseException {
        return this.findCorrectDatabaseImplementation(this.openConnection(url2, username, password, driver, databaseClass, driverPropertiesFile, propertyProviderClass, resourceAccessor));
    }

    public Database openDatabase(String url2, String username, String driver, String databaseClass, Properties driverProperties, ResourceAccessor resourceAccessor) throws DatabaseException {
        return this.findCorrectDatabaseImplementation(this.openConnection(url2, username, driver, databaseClass, driverProperties, resourceAccessor));
    }

    public DatabaseConnection openConnection(String url2, String username, String password, String propertyProvider, ResourceAccessor resourceAccessor) throws DatabaseException {
        return this.openConnection(url2, username, password, null, null, null, propertyProvider, resourceAccessor);
    }

    public DatabaseConnection openConnection(String url2, String username, String password, String driver, String databaseClass, String driverPropertiesFile, String propertyProviderClass, ResourceAccessor resourceAccessor) throws DatabaseException {
        Properties driverProperties;
        try {
            driverProperties = this.buildDriverProperties(username, password, driverPropertiesFile, propertyProviderClass);
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
        return this.openConnection(url2, username, driver, databaseClass, driverProperties, resourceAccessor);
    }

    public DatabaseConnection openConnection(String url2, String username, String driver, String databaseClass, Properties driverProperties, ResourceAccessor resourceAccessor) throws DatabaseException {
        DatabaseConnection databaseConnection;
        if (url2.startsWith("offline:")) {
            OfflineConnection offlineConnection = new OfflineConnection(url2, resourceAccessor);
            offlineConnection.setConnectionUserName(username);
            return offlineConnection;
        }
        try {
            String selectedDriverClass;
            Driver driverObject;
            DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
            if (databaseClass != null) {
                this.specifiedDbClass = (Database)Class.forName(databaseClass, true, Scope.getCurrentScope().getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
                boolean registered = false;
                if (this.implementedDatabases.containsKey(this.specifiedDbClass.getShortName())) {
                    for (Database database : this.implementedDatabases.get(this.specifiedDbClass.getShortName())) {
                        if (!database.getClass().getName().equals(this.specifiedDbClass.getClass().getName())) continue;
                        registered = true;
                    }
                }
                if (!registered) {
                    databaseFactory.register(this.specifiedDbClass);
                }
            }
            if ((driverObject = this.loadDriver(selectedDriverClass = this.findDriverClass(url2, driver, databaseFactory))) instanceof LiquibaseExtDriver) {
                ((LiquibaseExtDriver)((Object)driverObject)).setResourceAccessor(resourceAccessor);
            }
            if (selectedDriverClass.contains("oracle")) {
                driverProperties.put("remarksReporting", "true");
            } else if (selectedDriverClass.contains("mysql")) {
                driverProperties.put("useInformationSchema", "true");
            }
            LOG.fine("Connecting to the URL:'" + JdbcConnection.sanitizeUrl(url2) + "' using driver:'" + driverObject.getClass().getName() + "'");
            databaseConnection = ConnectionServiceFactory.getInstance().create(url2, driverObject, driverProperties);
            LOG.fine("Connection has been created");
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
        return databaseConnection;
    }

    public String findDefaultDriver(String url2) {
        for (Database database : this.getImplementedDatabases()) {
            String defaultDriver = database.getDefaultDriver(url2);
            if (defaultDriver == null) continue;
            return defaultDriver;
        }
        return null;
    }

    public void clearRegistry() {
        this.implementedDatabases.clear();
    }

    public Database getDatabase(String shortName) {
        if (!this.implementedDatabases.containsKey(shortName)) {
            return null;
        }
        if (this.specifiedDbClass != null && this.specifiedDbClass.getShortName().equals(shortName)) {
            return this.specifiedDbClass;
        }
        return (Database)this.implementedDatabases.get(shortName).iterator().next();
    }

    private String findDriverClass(String url2, String driver, DatabaseFactory databaseFactory) {
        String selectedDriverClass = StringUtil.trimToNull(driver);
        if (selectedDriverClass == null) {
            selectedDriverClass = databaseFactory.findDefaultDriver(url2);
        }
        if (selectedDriverClass == null) {
            throw new RuntimeException("Driver class was not specified and could not be determined from the url (" + url2 + ")");
        }
        return selectedDriverClass;
    }

    private Driver loadDriver(String driverClass) {
        Driver driverObject;
        try {
            driverObject = (Driver)Class.forName(driverClass, true, Scope.getCurrentScope().getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (UnsupportedClassVersionError e) {
            throw new UnexpectedLiquibaseException(String.format("Your database driver %s is not compatible with Java version %s. You will need to either upgrade your Java version or install a different driver jar file.", driverClass, SystemUtil.getJavaVersion()), e);
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot find database driver: " + e.getMessage());
        }
        return driverObject;
    }

    private Properties buildDriverProperties(String username, String password, String driverPropertiesFile, String propertyProviderClass) {
        Properties driverProperties;
        block13: {
            try {
                driverProperties = propertyProviderClass == null ? new Properties() : (Properties)Class.forName(propertyProviderClass, true, Scope.getCurrentScope().getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
                if (username != null) {
                    driverProperties.put("user", username);
                }
                if (password != null) {
                    driverProperties.put("password", password);
                }
                if (null == driverPropertiesFile) break block13;
                PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
                Resource driverProperty = pathHandlerFactory.getResource(driverPropertiesFile);
                if (driverProperty.exists()) {
                    try (InputStream stream = driverProperty.openInputStream();){
                        LOG.fine("Loading properties from the file:'" + driverPropertiesFile + "'");
                        driverProperties.load(stream);
                        break block13;
                    }
                }
                throw new RuntimeException("Can't open JDBC Driver specific properties from the file: '" + driverPropertiesFile + "'");
            }
            catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("Exception opening JDBC Driver specific properties from the file: '" + driverPropertiesFile + "'", e);
            }
        }
        LOG.fine("Properties:");
        for (Map.Entry<Object, Object> entry : driverProperties.entrySet()) {
            if (entry.getKey().toString().toLowerCase().contains("password")) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Key:'" + entry.getKey().toString() + "' Value:'**********'");
                continue;
            }
            LOG.fine("Key:'" + entry.getKey().toString() + "' Value:'" + entry.getValue().toString() + "'");
        }
        return driverProperties;
    }

    private static class DatabaseComparator
    implements Comparator<Database> {
        private DatabaseComparator() {
        }

        @Override
        public int compare(Database o1, Database o2) {
            return -1 * Integer.compare(o1.getPriority(), o2.getPriority());
        }
    }
}

