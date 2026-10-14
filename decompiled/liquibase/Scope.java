/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.Beta;
import liquibase.ChecksumVersion;
import liquibase.ScopeManager;
import liquibase.SingletonObject;
import liquibase.SingletonScopeManager;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.listener.LiquibaseListener;
import liquibase.logging.LogService;
import liquibase.logging.Logger;
import liquibase.logging.core.JavaLogService;
import liquibase.logging.core.LogServiceFactory;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.logging.mdc.MdcManager;
import liquibase.logging.mdc.MdcManagerFactory;
import liquibase.logging.mdc.MdcObject;
import liquibase.osgi.ContainerChecker;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.servicelocator.ServiceLocator;
import liquibase.servicelocator.StandardServiceLocator;
import liquibase.ui.ConsoleUIService;
import liquibase.ui.UIService;
import liquibase.util.CollectionUtil;
import liquibase.util.SmartMap;
import liquibase.util.StringUtil;

public class Scope {
    public static final String JAVA_PROPERTIES = "javaProperties";
    private static final ThreadLocal<ScopeManager> scopeManager = new ThreadLocal();
    private final Scope parent;
    private final SmartMap values = new SmartMap();
    private final String scopeId;
    private static final Map<String, List<MdcObject>> addedMdcEntries = new ConcurrentHashMap<String, List<MdcObject>>();
    private LiquibaseListener listener;

    public static Scope getCurrentScope() {
        if (scopeManager.get() == null) {
            scopeManager.set(new SingletonScopeManager());
        }
        if (scopeManager.get().getCurrentScope() == null) {
            Scope rootScope = new Scope();
            scopeManager.get().setCurrentScope(rootScope);
            rootScope.values.put(Attr.logService.name(), (Object)new JavaLogService());
            rootScope.values.put(Attr.serviceLocator.name(), (Object)new StandardServiceLocator());
            rootScope.values.put(Attr.resourceAccessor.name(), (Object)new ClassLoaderResourceAccessor());
            rootScope.values.put(Attr.latestChecksumVersion.name(), (Object)ChecksumVersion.V9);
            rootScope.values.put(Attr.checksumVersion.name(), (Object)ChecksumVersion.latest());
            rootScope.values.put(Attr.ui.name(), (Object)new ConsoleUIService());
            rootScope.getSingleton(LiquibaseConfiguration.class).init(rootScope);
            LogService overrideLogService = rootScope.getSingleton(LogServiceFactory.class).getDefaultLogService();
            if (overrideLogService == null) {
                throw new UnexpectedLiquibaseException("Cannot find default log service");
            }
            rootScope.values.put(Attr.logService.name(), (Object)overrideLogService);
            ServiceLocator serviceLocator = rootScope.getServiceLocator();
            for (ServiceLocator possibleLocator : serviceLocator.findInstances(ServiceLocator.class)) {
                if (possibleLocator.getPriority() <= serviceLocator.getPriority()) continue;
                serviceLocator = possibleLocator;
            }
            rootScope.values.put(Attr.serviceLocator.name(), (Object)serviceLocator);
            rootScope.values.put(Attr.osgiPlatform.name(), (Object)ContainerChecker.isOsgiPlatform());
        }
        return scopeManager.get().getCurrentScope();
    }

    public static void setScopeManager(ScopeManager scopeManager) {
        Scope.scopeManager.set(scopeManager);
    }

    private Scope() {
        this.scopeId = "root";
        this.parent = null;
    }

    protected Scope(Scope parent, Map<String, Object> scopeValues) {
        if (parent == null) {
            throw new UnexpectedLiquibaseException("Cannot pass a null parent to a new Scope. Use Scope.child to correctly create a nested scope");
        }
        this.parent = parent;
        this.scopeId = this.generateScopeId();
        if (scopeValues != null) {
            this.values.putAll((Map<? extends String, ?>)scopeValues);
        }
    }

    private String generateScopeId() {
        return StringUtil.randomIdentifier(10).toLowerCase();
    }

    public Scope getParent() {
        return this.parent;
    }

    public static void child(Map<String, Object> scopeValues, ScopedRunner runner) throws Exception {
        Scope.child((LiquibaseListener)null, scopeValues, runner);
    }

    public static <ReturnType> ReturnType child(Map<String, Object> scopeValues, ScopedRunnerWithReturn<ReturnType> runner) throws Exception {
        return Scope.child(null, scopeValues, runner);
    }

    public static void child(LiquibaseListener listener, ScopedRunner runner) throws Exception {
        Scope.child(listener, null, runner);
    }

    public static void child(LiquibaseListener listener, Map<String, Object> scopeValues, ScopedRunner runner) throws Exception {
        Scope.child(listener, scopeValues, () -> {
            runner.run();
            return null;
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <T> T child(LiquibaseListener listener, Map<String, Object> scopeValues, ScopedRunnerWithReturn<T> runner) throws Exception {
        String scopeId = Scope.enter(listener, scopeValues);
        try {
            T t2 = runner.run();
            return t2;
        }
        finally {
            Scope.exit(scopeId);
        }
    }

    public static String enter(Map<String, Object> scopeValues) throws Exception {
        return Scope.enter(null, scopeValues);
    }

    public static String enter(LiquibaseListener listener, Map<String, Object> scopeValues) throws Exception {
        Scope originalScope = Scope.getCurrentScope();
        Scope child = new Scope(originalScope, scopeValues);
        child.listener = listener;
        scopeManager.get().setCurrentScope(child);
        return child.scopeId;
    }

    public static void exit(String scopeId) throws Exception {
        Scope currentScope = Scope.getCurrentScope();
        if (!currentScope.scopeId.equals(scopeId)) {
            throw new RuntimeException("Cannot end scope " + scopeId + " when currently at scope " + currentScope.scopeId);
        }
        List<MdcObject> mdcObjects = addedMdcEntries.remove(currentScope.scopeId);
        for (MdcObject mdcObject : CollectionUtil.createIfNull(mdcObjects)) {
            mdcObject.close();
        }
        scopeManager.get().setCurrentScope(currentScope.getParent());
    }

    public static void child(String newValueKey, Object newValue, ScopedRunner runner) throws Exception {
        HashMap<String, Object> scopeValues = new HashMap<String, Object>();
        scopeValues.put(newValueKey, newValue);
        Scope.child(scopeValues, runner);
    }

    public static void child(Enum newValueKey, Object newValue, ScopedRunner runner) throws Exception {
        Scope.child(newValueKey.name(), newValue, runner);
    }

    public boolean has(String key) {
        return this.get(key, (Object)Object.class) != null;
    }

    public boolean has(Enum key) {
        return this.has(key.name());
    }

    public synchronized <T> T get(Enum key, Class<T> type) {
        return (T)this.get(key.name(), (T)type);
    }

    public <T> T get(Enum key, T defaultValue) {
        return this.get(key.name(), defaultValue);
    }

    public <T> T get(String key, Class<T> type) {
        Class<Object> value = this.values.get(key, type);
        if (value == null && this.values.containsKey(JAVA_PROPERTIES)) {
            Map javaProperties = (Map)((Object)this.values.get(JAVA_PROPERTIES, Map.class));
            value = javaProperties.get(key);
        }
        if (value == null && this.parent != null) {
            value = this.parent.get(key, (T)type);
        }
        return (T)value;
    }

    public synchronized <T> T get(String key, T defaultValue) {
        Class type = defaultValue == null ? Object.class : defaultValue.getClass();
        Class<Object> value = this.get(key, (T)type);
        if (value == null) {
            return defaultValue;
        }
        return (T)value;
    }

    public synchronized <T extends SingletonObject> T getSingleton(Class<T> type) {
        if (this.getParent() != null) {
            return this.getParent().getSingleton(type);
        }
        String key = type.getName();
        SingletonObject singleton = (SingletonObject)((Object)this.get(key, (T)type));
        if (singleton == null) {
            try {
                try {
                    Constructor<T> constructor = type.getDeclaredConstructor(Scope.class);
                    constructor.setAccessible(true);
                    singleton = (SingletonObject)constructor.newInstance(this);
                }
                catch (NoSuchMethodException e) {
                    Constructor<T> constructor = type.getDeclaredConstructor(new Class[0]);
                    constructor.setAccessible(true);
                    singleton = (SingletonObject)constructor.newInstance(new Object[0]);
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
            this.values.put(key, (Object)singleton);
        }
        return (T)singleton;
    }

    public Logger getLog(Class clazz) {
        return ((LogService)((Object)this.get((Enum)Attr.logService, (Object)LogService.class))).getLog(clazz);
    }

    public UIService getUI() {
        return (UIService)((Object)this.get((Enum)Attr.ui, (Object)UIService.class));
    }

    public Database getDatabase() {
        return (Database)((Object)this.get((Enum)Attr.database, (Object)Database.class));
    }

    public ClassLoader getClassLoader() {
        return this.get((Enum)Attr.classLoader, Thread.currentThread().getContextClassLoader());
    }

    public ClassLoader getClassLoader(boolean fallbackToContextClassLoader) {
        ClassLoader classLoader = this.getClassLoader();
        if (classLoader == null && fallbackToContextClassLoader) {
            return Thread.currentThread().getContextClassLoader();
        }
        return classLoader;
    }

    public ServiceLocator getServiceLocator() {
        return (ServiceLocator)((Object)this.get((Enum)Attr.serviceLocator, (Object)ServiceLocator.class));
    }

    public ResourceAccessor getResourceAccessor() {
        return (ResourceAccessor)((Object)this.get((Enum)Attr.resourceAccessor, (Object)ResourceAccessor.class));
    }

    public ChecksumVersion getChecksumVersion() {
        return (ChecksumVersion)((Object)this.get((Enum)Attr.checksumVersion, (Object)ChecksumVersion.class));
    }

    public String getLineSeparator() {
        return this.get((Enum)Attr.lineSeparator, System.lineSeparator());
    }

    @Deprecated
    public Charset getFileEncoding() {
        return this.get((Enum)Attr.fileEncoding, Charset.defaultCharset());
    }

    public MdcManager getMdcManager() {
        MdcManagerFactory mdcManagerFactory = this.getSingleton(MdcManagerFactory.class);
        return mdcManagerFactory.getMdcManager();
    }

    public MdcObject addMdcValue(String key, String value) {
        return this.addMdcValue(key, value, true);
    }

    public MdcObject addMdcValue(String key, String value, boolean removeWhenScopeExits) {
        MdcObject mdcObject = this.getMdcManager().put(key, value, removeWhenScopeExits);
        this.removeMdcObjectWhenScopeExits(removeWhenScopeExits, mdcObject);
        return mdcObject;
    }

    private void removeMdcObjectWhenScopeExits(boolean removeWhenScopeExits, MdcObject mdcObject) {
        if (removeWhenScopeExits) {
            Scope currentScope = Scope.getCurrentScope();
            addedMdcEntries.computeIfAbsent(currentScope.scopeId, k -> new ArrayList()).add(mdcObject);
        }
    }

    public MdcObject addMdcValue(String key, Map<String, Object> value) {
        return this.addMdcValue(key, value, true);
    }

    public MdcObject addMdcValue(String key, Map<String, Object> value, boolean removeWhenScopeExits) {
        MdcObject mdcObject = this.getMdcManager().put(key, value, removeWhenScopeExits);
        this.removeMdcObjectWhenScopeExits(removeWhenScopeExits, mdcObject);
        return mdcObject;
    }

    public MdcObject addMdcValue(String key, CustomMdcObject customMdcObject) {
        return this.addMdcValue(key, customMdcObject, true);
    }

    public MdcObject addMdcValue(String key, CustomMdcObject customMdcObject, boolean removeWhenScopeExits) {
        MdcObject mdcObject = this.getMdcManager().put(key, customMdcObject, removeWhenScopeExits);
        this.removeMdcObjectWhenScopeExits(removeWhenScopeExits, mdcObject);
        return mdcObject;
    }

    @Beta
    public boolean isMdcKeyPresent(String key) {
        Object mdc = this.getMdcManager().getAll().get(key);
        return mdc != null;
    }

    public <T extends LiquibaseListener> Collection<T> getListeners(Class<T> type) {
        ArrayList<LiquibaseListener> returnList = new ArrayList<LiquibaseListener>();
        for (Scope scopeToCheck = this; scopeToCheck != null; scopeToCheck = scopeToCheck.getParent()) {
            if (scopeToCheck.listener == null || !type.isAssignableFrom(scopeToCheck.listener.getClass())) continue;
            returnList.add(scopeToCheck.listener);
        }
        return returnList;
    }

    public String toString() {
        return this.describe();
    }

    public String describe() {
        String databaseName = null;
        Database database = this.getDatabase();
        if (database != null) {
            databaseName = database.getShortName();
            DatabaseConnection connection = database.getConnection();
            if (connection == null) {
                databaseName = "unconnected " + databaseName;
            } else if (connection instanceof OfflineConnection) {
                databaseName = "offline " + databaseName;
            } else if (connection instanceof JdbcConnection) {
                databaseName = "jdbc " + databaseName;
            }
        }
        return "scope(database=" + databaseName + ")";
    }

    public String getScopeId() {
        return this.scopeId;
    }

    public static enum Attr {
        logService,
        ui,
        resourceAccessor,
        classLoader,
        database,
        quotingStrategy,
        changeLogHistoryService,
        lockService,
        executeMode,
        lineSeparator,
        serviceLocator,
        fileEncoding,
        databaseChangeLog,
        changeSet,
        osgiPlatform,
        checksumVersion,
        latestChecksumVersion,
        mavenConfigurationProperties;

    }

    public static interface ScopedRunner<T> {
        public void run() throws Exception;
    }

    public static interface ScopedRunnerWithReturn<T> {
        public T run() throws Exception;
    }
}

