/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import org.h2.command.CommandInterface;
import org.h2.engine.CastDataProvider;
import org.h2.engine.IsolationLevel;
import org.h2.engine.Mode;
import org.h2.jdbc.meta.DatabaseMeta;
import org.h2.message.Trace;
import org.h2.result.ResultInterface;
import org.h2.store.DataHandler;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.value.ValueLob;

public abstract class Session
implements CastDataProvider,
AutoCloseable {
    private final ReentrantLock lock = new ReentrantLock();
    private ArrayList<String> sessionState;
    boolean sessionStateChanged;
    private boolean sessionStateUpdating;
    volatile StaticSettings staticSettings;

    Session() {
    }

    public final void lock() {
        this.lock.lock();
    }

    public final void unlock() {
        this.lock.unlock();
    }

    public final boolean isLockedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public abstract ArrayList<String> getClusterServers();

    public abstract CommandInterface prepareCommand(String var1, int var2);

    @Override
    public abstract void close();

    public abstract Trace getTrace();

    public abstract boolean isClosed();

    public abstract DataHandler getDataHandler();

    public abstract boolean hasPendingTransaction();

    public abstract void cancel();

    public abstract boolean getAutoCommit();

    public abstract void setAutoCommit(boolean var1);

    public abstract ValueLob addTemporaryLob(ValueLob var1);

    public abstract boolean isRemote();

    public abstract void setCurrentSchemaName(String var1);

    public abstract String getCurrentSchemaName();

    public abstract void setNetworkConnectionInfo(NetworkConnectionInfo var1);

    public abstract IsolationLevel getIsolationLevel();

    public abstract void setIsolationLevel(IsolationLevel var1);

    public abstract StaticSettings getStaticSettings();

    public abstract DynamicSettings getDynamicSettings();

    public abstract DatabaseMeta getDatabaseMeta();

    public abstract boolean isOldInformationSchema();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void recreateSessionState() {
        if (this.sessionState != null && !this.sessionState.isEmpty()) {
            this.sessionStateUpdating = true;
            try {
                for (String string : this.sessionState) {
                    CommandInterface commandInterface = this.prepareCommand(string, Integer.MAX_VALUE);
                    commandInterface.executeUpdate(null);
                }
            }
            finally {
                this.sessionStateUpdating = false;
                this.sessionStateChanged = false;
            }
        }
    }

    public void readSessionState() {
        if (!this.sessionStateChanged || this.sessionStateUpdating) {
            return;
        }
        this.sessionStateChanged = false;
        this.sessionState = Utils.newSmallArrayList();
        CommandInterface commandInterface = this.prepareCommand(!this.isOldInformationSchema() ? "SELECT STATE_COMMAND FROM INFORMATION_SCHEMA.SESSION_STATE" : "SELECT SQL FROM INFORMATION_SCHEMA.SESSION_STATE", Integer.MAX_VALUE);
        ResultInterface resultInterface = commandInterface.executeQuery(0L, false);
        while (resultInterface.next()) {
            this.sessionState.add(resultInterface.currentRow()[0].getString());
        }
    }

    public Session setThreadLocalSession() {
        return null;
    }

    public void resetThreadLocalSession(Session session) {
    }

    public static final class DynamicSettings {
        public final Mode mode;
        public final TimeZoneProvider timeZone;

        public DynamicSettings(Mode mode, TimeZoneProvider timeZoneProvider) {
            this.mode = mode;
            this.timeZone = timeZoneProvider;
        }
    }

    public static final class StaticSettings {
        public final boolean databaseToUpper;
        public final boolean databaseToLower;
        public final boolean caseInsensitiveIdentifiers;

        public StaticSettings(boolean bl, boolean bl2, boolean bl3) {
            this.databaseToUpper = bl;
            this.databaseToLower = bl2;
            this.caseInsensitiveIdentifiers = bl3;
        }
    }
}

