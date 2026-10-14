/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import org.apache.activemq.broker.Locker;

public interface Lockable {
    public void setUseLock(boolean var1);

    public void setStopOnError(boolean var1);

    public Locker createDefaultLocker() throws IOException;

    public void setLocker(Locker var1) throws IOException;

    public void setLockKeepAlivePeriod(long var1);

    public long getLockKeepAlivePeriod();
}

