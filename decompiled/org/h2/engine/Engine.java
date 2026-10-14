/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.h2.command.Command;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Database;
import org.h2.engine.DbSettings;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.engine.User;
import org.h2.message.DbException;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.store.fs.FileUtils;
import org.h2.util.MathUtils;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.ThreadDeadlockDetector;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;

public final class Engine {
    private static final Map<String, DatabaseHolder> DATABASES = new HashMap<String, DatabaseHolder>();
    private static volatile long WRONG_PASSWORD_DELAY = SysProperties.DELAY_WRONG_PASSWORD_MIN;
    private static boolean JMX;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static SessionLocal openSession(ConnectionInfo connectionInfo, boolean bl, boolean bl2, String string2) {
        Database database;
        DatabaseHolder databaseHolder;
        Object object;
        String string3 = connectionInfo.getName();
        connectionInfo.removeProperty("NO_UPGRADE", false);
        boolean bl3 = connectionInfo.getProperty("OPEN_NEW", false);
        boolean bl4 = false;
        User user = null;
        if (!connectionInfo.isUnnamedInMemory()) {
            object = DATABASES;
            synchronized (object) {
                databaseHolder = DATABASES.computeIfAbsent(string3, string -> new DatabaseHolder());
            }
        } else {
            databaseHolder = new DatabaseHolder();
        }
        object = databaseHolder;
        synchronized (object) {
            database = databaseHolder.database;
            if (database == null || bl3) {
                if (connectionInfo.isPersistent()) {
                    Object object2;
                    String string4 = connectionInfo.getProperty("MV_STORE");
                    if (string4 == null) {
                        object2 = string3 + ".mv.db";
                        if (!FileUtils.exists((String)object2)) {
                            Engine.throwNotFound(bl, bl2, string3);
                            object2 = string3 + ".data.db";
                            if (FileUtils.exists((String)object2)) {
                                throw DbException.getFileVersionError((String)object2);
                            }
                            object2 = null;
                        }
                    } else {
                        object2 = string3 + ".mv.db";
                        if (!FileUtils.exists((String)object2)) {
                            Engine.throwNotFound(bl, bl2, string3);
                            object2 = null;
                        }
                    }
                    if (object2 != null && !FileUtils.canWrite((String)object2)) {
                        connectionInfo.setProperty("ACCESS_MODE_DATA", "r");
                    }
                } else {
                    Engine.throwNotFound(bl, bl2, string3);
                }
                database = new Database(connectionInfo, string2);
                bl4 = true;
                boolean bl5 = false;
                for (RightOwner rightOwner : database.getAllUsersAndRoles()) {
                    if (!(rightOwner instanceof User)) continue;
                    bl5 = true;
                    break;
                }
                if (!bl5) {
                    user = new User(database, database.allocateObjectId(), connectionInfo.getUserName(), false);
                    user.setAdmin(true);
                    user.setUserPasswordHash(connectionInfo.getUserPasswordHash());
                    database.setMasterUser(user);
                }
                databaseHolder.database = database;
            }
        }
        if (bl4) {
            database.opened();
        }
        if (database.isClosing()) {
            return null;
        }
        if (user == null) {
            if (database.validateFilePasswordHash(string2, connectionInfo.getFilePasswordHash())) {
                if (connectionInfo.getProperty("AUTHREALM") == null) {
                    user = database.findUser(connectionInfo.getUserName());
                    if (user != null && !user.validateUserPasswordHash(connectionInfo.getUserPasswordHash())) {
                        user = null;
                    }
                } else {
                    object = database.getAuthenticator();
                    if (object == null) {
                        throw DbException.get(90144, string3);
                    }
                    try {
                        AuthenticationInfo authenticationInfo = new AuthenticationInfo(connectionInfo);
                        user = database.getAuthenticator().authenticate(authenticationInfo, database);
                    }
                    catch (AuthenticationException authenticationException) {
                        database.getTrace(2).error(authenticationException, "an error occurred during authentication; user: \"" + connectionInfo.getUserName() + "\"");
                    }
                }
            }
            if (bl4 && (user == null || !user.isAdmin())) {
                database.setEventListener(null);
            }
        }
        if (user == null) {
            object = DbException.get(28000);
            database.getTrace(2).error((Throwable)object, "wrong user or password; user: \"" + connectionInfo.getUserName() + "\"");
            database.removeSession(null);
            throw object;
        }
        connectionInfo.cleanAuthenticationInfo();
        Engine.checkClustering(connectionInfo, database);
        object = database.createSession(user, connectionInfo.getNetworkConnectionInfo());
        if (object == null) {
            return null;
        }
        if (connectionInfo.getProperty("OLD_INFORMATION_SCHEMA", false)) {
            ((SessionLocal)object).setOldInformationSchema(true);
        }
        if (connectionInfo.getProperty("JMX", false)) {
            try {
                Utils.callStaticMethod("org.h2.jmx.DatabaseInfo.registerMBean", connectionInfo, database);
            }
            catch (Exception exception) {
                database.removeSession((SessionLocal)object);
                throw DbException.get(50100, exception, "JMX");
            }
            JMX = true;
        }
        return object;
    }

    private static void throwNotFound(boolean bl, boolean bl2, String string) {
        if (bl) {
            throw DbException.get(90146, string);
        }
        if (bl2) {
            throw DbException.get(90149, string);
        }
    }

    public static SessionLocal createSession(ConnectionInfo connectionInfo) {
        try {
            SessionLocal sessionLocal = Engine.openSession(connectionInfo);
            Engine.validateUserAndPassword(true);
            return sessionLocal;
        }
        catch (DbException dbException) {
            if (dbException.getErrorCode() == 28000) {
                Engine.validateUserAndPassword(false);
            }
            throw dbException;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static SessionLocal openSession(ConnectionInfo connectionInfo) {
        SessionLocal sessionLocal;
        boolean bl = connectionInfo.removeProperty("IFEXISTS", false);
        boolean bl2 = connectionInfo.removeProperty("FORBID_CREATION", false);
        boolean bl3 = connectionInfo.removeProperty("IGNORE_UNKNOWN_SETTINGS", false);
        String string = connectionInfo.removeProperty("CIPHER", null);
        String string2 = connectionInfo.removeProperty("INIT", null);
        long l = System.nanoTime();
        while ((sessionLocal = Engine.openSession(connectionInfo, bl, bl2, string)) == null) {
            if (System.nanoTime() - l > 60000000000L) {
                throw DbException.get(90020, "Waited for database closing longer than 1 minute");
            }
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {
                throw DbException.get(90121);
            }
        }
        sessionLocal.lock();
        try {
            block19: {
                sessionLocal.setAllowLiterals(true);
                DbSettings dbSettings = DbSettings.DEFAULT;
                for (String string3 : connectionInfo.getKeys()) {
                    if (dbSettings.containsKey(string3)) continue;
                    String string4 = connectionInfo.getProperty(string3);
                    StringBuilder stringBuilder = new StringBuilder("SET ").append(string3).append(' ');
                    if (!ParserUtil.isSimpleIdentifier(string3, false, false)) {
                        if (!string3.equalsIgnoreCase("TIME ZONE")) {
                            throw DbException.get(90113, string3);
                        }
                        StringUtils.quoteStringSQL(stringBuilder, string4);
                    } else {
                        stringBuilder.append(string4);
                    }
                    try {
                        Command dbException = sessionLocal.prepareLocal(stringBuilder.toString());
                        dbException.executeUpdate(null);
                    }
                    catch (DbException dbException) {
                        if (dbException.getErrorCode() == 90040) {
                            sessionLocal.getTrace().error(dbException, "admin rights required; user: \"" + connectionInfo.getUserName() + "\"");
                        } else {
                            sessionLocal.getTrace().error(dbException, "");
                        }
                        if (bl3) continue;
                        sessionLocal.close();
                        throw dbException;
                    }
                }
                TimeZoneProvider timeZoneProvider = connectionInfo.getTimeZone();
                if (timeZoneProvider != null) {
                    sessionLocal.setTimeZone(timeZoneProvider);
                }
                if (string2 != null) {
                    try {
                        Command dbException = sessionLocal.prepareLocal(string2);
                        dbException.executeUpdate(null);
                    }
                    catch (DbException dbException) {
                        if (bl3) break block19;
                        sessionLocal.close();
                        throw dbException;
                    }
                }
            }
            sessionLocal.setAllowLiterals(false);
            sessionLocal.commit(true);
        }
        finally {
            sessionLocal.unlock();
        }
        return sessionLocal;
    }

    private static void checkClustering(ConnectionInfo connectionInfo, Database database) {
        String string = connectionInfo.getProperty(12, null);
        if ("''".equals(string)) {
            return;
        }
        String string2 = database.getCluster();
        if (!("''".equals(string2) || "TRUE".equals(string) || Objects.equals(string, string2))) {
            if (string2.equals("''")) {
                throw DbException.get(90093);
            }
            throw DbException.get(90094, string2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void close(String string) {
        if (JMX) {
            try {
                Utils.callStaticMethod("org.h2.jmx.DatabaseInfo.unregisterMBean", string);
            }
            catch (Exception exception) {
                throw DbException.get(50100, exception, "JMX");
            }
        }
        Map<String, DatabaseHolder> map = DATABASES;
        synchronized (map) {
            DATABASES.remove(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void validateUserAndPassword(boolean bl) {
        int n = SysProperties.DELAY_WRONG_PASSWORD_MIN;
        if (bl) {
            long l = WRONG_PASSWORD_DELAY;
            if (l > (long)n && l > 0L) {
                Class<Engine> clazz = Engine.class;
                synchronized (Engine.class) {
                    l = MathUtils.secureRandomInt((int)l);
                    try {
                        Thread.sleep(l);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    WRONG_PASSWORD_DELAY = n;
                    // ** MonitorExit[var4_4] (shouldn't be in output)
                }
            }
        } else {
            Class<Engine> clazz = Engine.class;
            synchronized (Engine.class) {
                long l = WRONG_PASSWORD_DELAY;
                int n2 = SysProperties.DELAY_WRONG_PASSWORD_MAX;
                if (n2 <= 0) {
                    n2 = Integer.MAX_VALUE;
                }
                if ((WRONG_PASSWORD_DELAY += WRONG_PASSWORD_DELAY) > (long)n2 || WRONG_PASSWORD_DELAY < 0L) {
                    WRONG_PASSWORD_DELAY = n2;
                }
                if (n > 0) {
                    l += Math.abs(MathUtils.secureRandomLong() % 100L);
                    try {
                        Thread.sleep(l);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                throw DbException.get(28000);
            }
        }
    }

    private Engine() {
    }

    static {
        if (SysProperties.THREAD_DEADLOCK_DETECTOR) {
            ThreadDeadlockDetector.init();
        }
    }

    private static final class DatabaseHolder {
        volatile Database database;

        DatabaseHolder() {
        }
    }
}

