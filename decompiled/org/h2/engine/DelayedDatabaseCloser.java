/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.h2.engine.Database;
import org.h2.message.Trace;

class DelayedDatabaseCloser
extends Thread {
    private final Trace trace;
    private volatile WeakReference<Database> databaseRef;
    private int delayInMillis;

    DelayedDatabaseCloser(Database database, int n) {
        this.databaseRef = new WeakReference<Database>(database);
        this.delayInMillis = n;
        this.trace = database.getTrace(2);
        this.setName("H2 Close Delay " + database.getShortName());
        this.setDaemon(true);
        this.start();
    }

    void reset() {
        this.databaseRef = null;
    }

    @Override
    public void run() {
        Object object;
        while (this.delayInMillis > 0) {
            try {
                int n = 100;
                Thread.sleep(n);
                this.delayInMillis -= n;
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ((object = this.databaseRef) != null && ((Reference)object).get() != null) continue;
            return;
        }
        WeakReference<Database> weakReference = this.databaseRef;
        if (weakReference != null && (object = (Database)weakReference.get()) != null) {
            try {
                ((Database)object).close();
            }
            catch (RuntimeException runtimeException) {
                try {
                    this.trace.error(runtimeException, "could not close the database");
                }
                catch (Throwable throwable) {
                    runtimeException.addSuppressed(throwable);
                    throw runtimeException;
                }
            }
        }
    }
}

