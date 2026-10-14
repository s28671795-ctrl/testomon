/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.servicelocator.PrioritizedService;

public class ConnectionServiceFactory {
    private static ConnectionServiceFactory instance;
    private final List<DatabaseConnection> databaseConnections = new ArrayList<DatabaseConnection>();

    public static synchronized void reset() {
        instance = new ConnectionServiceFactory();
    }

    public static synchronized ConnectionServiceFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionServiceFactory();
        }
        return instance;
    }

    public DatabaseConnection create(String url2, Driver driverObject, Properties driverProperties) throws DatabaseException {
        DatabaseConnection databaseConnection = this.getDatabaseConnection(url2);
        try {
            databaseConnection.open(url2, driverObject, driverProperties);
        }
        catch (Exception sqle) {
            throw new DatabaseException(sqle);
        }
        return databaseConnection;
    }

    public DatabaseConnection getDatabaseConnection() {
        return this.getDatabaseConnection(null);
    }

    public DatabaseConnection getDatabaseConnection(String url2) {
        TreeSet sortedConnections = new TreeSet((o1, o2) -> -1 * Integer.compare(o1.getPriority(), o2.getPriority()));
        this.databaseConnections.stream().filter(c -> c.supports(url2)).forEach(sortedConnections::add);
        try {
            DatabaseConnection connection;
            DatabaseConnection exampleService = (DatabaseConnection)sortedConnections.iterator().next();
            Class<?> aClass = exampleService.getClass();
            try {
                aClass.getConstructor(new Class[0]);
                connection = (DatabaseConnection)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (NoSuchMethodException e) {
                connection = exampleService;
            }
            return connection;
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private ConnectionServiceFactory() {
        try {
            List<DatabaseConnection> connections = Scope.getCurrentScope().getServiceLocator().findInstances(DatabaseConnection.class);
            for (DatabaseConnection connection : connections) {
                this.register((DatabaseConnection)connection.getClass().getConstructor(new Class[0]).newInstance(new Object[0]));
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public void register(DatabaseConnection databaseConnection) {
        this.databaseConnections.add(databaseConnection);
        this.databaseConnections.sort(PrioritizedService.COMPARATOR);
    }
}

