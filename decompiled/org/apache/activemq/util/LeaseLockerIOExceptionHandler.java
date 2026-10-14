/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import org.apache.activemq.broker.LockableServiceSupport;
import org.apache.activemq.broker.Locker;
import org.apache.activemq.broker.SuppressReplyException;
import org.apache.activemq.util.DefaultIOExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaseLockerIOExceptionHandler
extends DefaultIOExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(LeaseLockerIOExceptionHandler.class);

    public LeaseLockerIOExceptionHandler() {
        this.setIgnoreSQLExceptions(false);
        this.setStopStartConnectors(true);
    }

    @Override
    protected boolean hasLockOwnership() throws IOException {
        Locker locker;
        boolean hasLock = true;
        if (this.broker.getPersistenceAdapter() instanceof LockableServiceSupport && (locker = ((LockableServiceSupport)((Object)this.broker.getPersistenceAdapter())).getLocker()) != null) {
            try {
                if (!locker.keepAlive()) {
                    hasLock = false;
                }
            }
            catch (SuppressReplyException suppressReplyException) {
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (!hasLock) {
                LOG.warn("Lock keepAlive failed, no longer lock owner with: {}", (Object)locker);
                throw new IOException("Lock keepAlive failed, no longer lock owner with: " + locker);
            }
        }
        return hasLock;
    }
}

