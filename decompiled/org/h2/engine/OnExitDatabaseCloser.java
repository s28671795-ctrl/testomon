/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.WeakHashMap;
import org.h2.engine.Database;

class OnExitDatabaseCloser
extends Thread {
    private static final WeakHashMap<Database, Void> DATABASES = new WeakHashMap();
    private static final Thread INSTANCE = new OnExitDatabaseCloser();
    private static boolean registered;
    private static boolean terminated;

    static synchronized void register(Database database) {
        if (terminated) {
            return;
        }
        DATABASES.put(database, null);
        if (!registered) {
            registered = true;
            try {
                Runtime.getRuntime().addShutdownHook(INSTANCE);
            }
            catch (IllegalStateException illegalStateException) {
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
        }
    }

    static synchronized void unregister(Database database) {
        if (terminated) {
            return;
        }
        DATABASES.remove(database);
        if (DATABASES.isEmpty() && registered) {
            try {
                Runtime.getRuntime().removeShutdownHook(INSTANCE);
            }
            catch (IllegalStateException illegalStateException) {
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
            registered = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void onShutdown() {
        Object object = OnExitDatabaseCloser.class;
        synchronized (OnExitDatabaseCloser.class) {
            terminated = true;
            // ** MonitorExit[var0] (shouldn't be in output)
            object = null;
            for (Database database : DATABASES.keySet()) {
                try {
                    database.onShutdown();
                }
                catch (RuntimeException runtimeException) {
                    try {
                        database.getTrace(2).error(runtimeException, "could not close the database");
                    }
                    catch (Throwable throwable) {
                        runtimeException.addSuppressed(throwable);
                        if (object == null) {
                            object = runtimeException;
                            continue;
                        }
                        ((Throwable)object).addSuppressed(runtimeException);
                    }
                }
            }
            if (object != null) {
                throw object;
            }
            return;
        }
    }

    private OnExitDatabaseCloser() {
    }

    @Override
    public void run() {
        OnExitDatabaseCloser.onShutdown();
    }
}

