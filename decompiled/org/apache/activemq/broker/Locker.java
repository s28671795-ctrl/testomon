/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import org.apache.activemq.Service;
import org.apache.activemq.broker.LockableServiceSupport;
import org.apache.activemq.store.PersistenceAdapter;

public interface Locker
extends Service {
    public boolean keepAlive() throws IOException;

    public void setLockAcquireSleepInterval(long var1);

    public void setName(String var1);

    public void setFailIfLocked(boolean var1);

    public void setLockable(LockableServiceSupport var1);

    public void configure(PersistenceAdapter var1) throws IOException;
}

