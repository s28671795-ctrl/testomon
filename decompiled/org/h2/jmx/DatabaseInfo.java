/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jmx;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Constants;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.jmx.DatabaseInfoMBean;
import org.h2.jmx.DocumentedMBean;
import org.h2.table.Table;
import org.h2.util.NetworkConnectionInfo;

public class DatabaseInfo
implements DatabaseInfoMBean {
    private static final Map<String, ObjectName> MBEANS = new HashMap<String, ObjectName>();
    private final Database database;

    private DatabaseInfo(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' must not be null");
        }
        this.database = database;
    }

    private static ObjectName getObjectName(String string, String string2) throws JMException {
        string = string.replace(':', '_');
        string2 = string2.replace(':', '_');
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("name", string);
        hashtable.put("path", string2);
        return new ObjectName("org.h2", hashtable);
    }

    public static void registerMBean(ConnectionInfo connectionInfo, Database database) throws JMException {
        String string = connectionInfo.getName();
        if (!MBEANS.containsKey(string)) {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            String string2 = database.getShortName();
            ObjectName objectName = DatabaseInfo.getObjectName(string2, string);
            MBEANS.put(string, objectName);
            DatabaseInfo databaseInfo = new DatabaseInfo(database);
            DocumentedMBean documentedMBean = new DocumentedMBean(databaseInfo, DatabaseInfoMBean.class);
            mBeanServer.registerMBean(documentedMBean, objectName);
        }
    }

    public static void unregisterMBean(String string) throws Exception {
        ObjectName objectName = MBEANS.remove(string);
        if (objectName != null) {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.unregisterMBean(objectName);
        }
    }

    @Override
    public boolean isExclusive() {
        return this.database.getExclusiveSession() != null;
    }

    @Override
    public boolean isReadOnly() {
        return this.database.isReadOnly();
    }

    @Override
    public String getMode() {
        return this.database.getMode().getName();
    }

    @Override
    public int getTraceLevel() {
        return this.database.getTraceSystem().getLevelFile();
    }

    @Override
    public void setTraceLevel(int n) {
        this.database.getTraceSystem().setLevelFile(n);
    }

    @Override
    public long getFileWriteCount() {
        if (this.database.isPersistent()) {
            return this.database.getStore().getMvStore().getFileStore().getWriteCount();
        }
        return 0L;
    }

    @Override
    public long getFileReadCount() {
        if (this.database.isPersistent()) {
            return this.database.getStore().getMvStore().getFileStore().getReadCount();
        }
        return 0L;
    }

    @Override
    public long getFileSize() {
        long l = 0L;
        if (this.database.isPersistent()) {
            l = this.database.getStore().getMvStore().getFileStore().size();
        }
        return l / 1024L;
    }

    @Override
    public int getCacheSizeMax() {
        if (this.database.isPersistent()) {
            return this.database.getStore().getMvStore().getCacheSize() * 1024;
        }
        return 0;
    }

    @Override
    public void setCacheSizeMax(int n) {
        if (this.database.isPersistent()) {
            this.database.setCacheSize(n);
        }
    }

    @Override
    public int getCacheSize() {
        if (this.database.isPersistent()) {
            return this.database.getStore().getMvStore().getCacheSizeUsed() * 1024;
        }
        return 0;
    }

    @Override
    public String getVersion() {
        return Constants.FULL_VERSION;
    }

    @Override
    public String listSettings() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : this.database.getSettings().getSortedSettings()) {
            stringBuilder.append(entry.getKey()).append(" = ").append(entry.getValue()).append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public String listSessions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SessionLocal sessionLocal : this.database.getSessions(false)) {
            Object object;
            stringBuilder.append("session id: ").append(sessionLocal.getId());
            stringBuilder.append(" user: ").append(sessionLocal.getUser().getName()).append('\n');
            NetworkConnectionInfo networkConnectionInfo = sessionLocal.getNetworkConnectionInfo();
            if (networkConnectionInfo != null) {
                stringBuilder.append("server: ").append(networkConnectionInfo.getServer()).append('\n').append("clientAddr: ").append(networkConnectionInfo.getClient()).append('\n');
                object = networkConnectionInfo.getClientInfo();
                if (object != null) {
                    stringBuilder.append("clientInfo: ").append((String)object).append('\n');
                }
            }
            stringBuilder.append("connected: ").append(sessionLocal.getSessionStart().getString()).append('\n');
            object = sessionLocal.getCurrentCommand();
            if (object != null) {
                stringBuilder.append("statement: ").append(object).append('\n').append("started: ").append(sessionLocal.getCommandStartOrEnd().getString()).append('\n');
            }
            for (Table table : sessionLocal.getLocks()) {
                if (table.isLockedExclusivelyBy(sessionLocal)) {
                    stringBuilder.append("write lock on ");
                } else {
                    stringBuilder.append("read lock on ");
                }
                stringBuilder.append(table.getSchema().getName()).append('.').append(table.getName()).append('\n');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}

