/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.activemq.broker.AbstractLocker;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.util.LockFile;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedFileLocker
extends AbstractLocker {
    public static final File DEFAULT_DIRECTORY = new File("KahaDB");
    private static final Logger LOG = LoggerFactory.getLogger(SharedFileLocker.class);
    private LockFile lockFile;
    protected File directory = DEFAULT_DIRECTORY;

    @Override
    public void doStart() throws Exception {
        if (this.lockFile == null) {
            File lockFileName = new File(this.directory, "lock");
            this.lockFile = new LockFile(lockFileName, false);
            if (this.failIfLocked) {
                this.lockFile.lock();
            } else {
                boolean warned = false;
                boolean locked = false;
                while (!this.isStopped() && !this.isStopping()) {
                    try {
                        this.lockFile.lock();
                        if (warned) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(this.lockable != null ? this.lockable.getLockKeepAlivePeriod() : 0L);
                            }
                            catch (InterruptedException interruptedException) {
                                // empty catch block
                            }
                        }
                        locked = this.keepAlive();
                        break;
                    }
                    catch (IOException e) {
                        if (!warned) {
                            LOG.info("Database " + lockFileName + " is locked by another server. This broker is now in slave mode waiting a lock to be acquired");
                            warned = true;
                        }
                        LOG.debug("Database " + lockFileName + " is locked... waiting " + this.lockAcquireSleepInterval / 1000L + " seconds for the database to be unlocked. Reason: " + e);
                        try {
                            TimeUnit.MILLISECONDS.sleep(this.lockAcquireSleepInterval);
                        }
                        catch (InterruptedException interruptedException) {}
                    }
                }
                if (!locked) {
                    throw new IOException("attempt to obtain lock aborted due to shutdown");
                }
            }
        }
    }

    @Override
    public boolean keepAlive() {
        boolean result = this.lockFile != null && this.lockFile.keepAlive();
        LOG.trace("keepAlive result: " + result + (this.name != null ? ", name: " + this.name : ""));
        return result;
    }

    @Override
    public void doStop(ServiceStopper stopper) throws Exception {
        if (this.lockFile != null) {
            this.lockFile.unlock();
            this.lockFile = null;
        }
    }

    public File getDirectory() {
        return this.directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    @Override
    public void configure(PersistenceAdapter persistenceAdapter) throws IOException {
        this.setDirectory(persistenceAdapter.getDirectory());
        if (this.name == null) {
            this.name = this.getDirectory().toString();
        }
    }
}

