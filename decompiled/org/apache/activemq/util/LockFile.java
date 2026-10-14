/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Date;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockFile {
    private static final boolean DISABLE_FILE_LOCK = Boolean.getBoolean("java.nio.channels.FileLock.broken");
    private final File file;
    private long lastModified;
    private FileLock lock;
    private RandomAccessFile randomAccessLockFile;
    private int lockCounter;
    private final boolean deleteOnUnlock;
    private volatile boolean locked;
    private String lockSystemPropertyName = "";
    private static final Logger LOG = LoggerFactory.getLogger(LockFile.class);

    public LockFile(File file, boolean deleteOnUnlock) {
        this.file = file;
        this.deleteOnUnlock = deleteOnUnlock;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void lock() throws IOException {
        if (DISABLE_FILE_LOCK) {
            return;
        }
        if (this.lockCounter > 0) {
            return;
        }
        IOHelper.mkdirs(this.file.getParentFile());
        Class<LockFile> clazz = LockFile.class;
        synchronized (LockFile.class) {
            block21: {
                this.lockSystemPropertyName = this.getVmLockKey();
                if (System.getProperty(this.lockSystemPropertyName) != null) {
                    throw new IOException("File '" + this.file + "' could not be locked as lock is already held for this jvm. Value: " + System.getProperty(this.lockSystemPropertyName));
                }
                System.setProperty(this.lockSystemPropertyName, new Date().toString());
                // ** MonitorExit[var1_1] (shouldn't be in output)
                try {
                    if (this.lock != null) break block21;
                    this.randomAccessLockFile = new RandomAccessFile(this.file, "rw");
                    IOException reason = null;
                    try {
                        this.lock = this.randomAccessLockFile.getChannel().tryLock(0L, Math.max(1L, this.randomAccessLockFile.getChannel().size()), false);
                    }
                    catch (OverlappingFileLockException e) {
                        reason = IOExceptionSupport.create("File '" + this.file + "' could not be locked.", e);
                    }
                    catch (IOException ioe) {
                        reason = ioe;
                    }
                    if (this.lock != null) {
                        this.randomAccessLockFile.writeLong(System.currentTimeMillis());
                        this.randomAccessLockFile.getChannel().force(true);
                        this.lastModified = this.file.lastModified();
                        ++this.lockCounter;
                        System.setProperty(this.lockSystemPropertyName, new Date().toString());
                        this.locked = true;
                        break block21;
                    }
                    this.closeReadFile();
                    if (reason != null) {
                        throw reason;
                    }
                    throw new IOException("File '" + this.file + "' could not be locked.");
                }
                finally {
                    clazz = LockFile.class;
                    synchronized (LockFile.class) {
                        if (this.lock == null) {
                            System.getProperties().remove(this.lockSystemPropertyName);
                        }
                        // ** MonitorExit[var1_1] (shouldn't be in output)
                    }
                }
            }
            return;
        }
    }

    public synchronized void unlock() {
        if (DISABLE_FILE_LOCK) {
            return;
        }
        --this.lockCounter;
        if (this.lockCounter != 0) {
            return;
        }
        if (this.lock != null) {
            try {
                this.lock.release();
            }
            catch (Throwable throwable) {
            }
            finally {
                if (this.lockSystemPropertyName != null) {
                    System.getProperties().remove(this.lockSystemPropertyName);
                }
                this.lock = null;
            }
        }
        this.closeReadFile();
        if (this.locked && this.deleteOnUnlock) {
            this.file.delete();
        }
    }

    private String getVmLockKey() throws IOException {
        return this.getClass().getName() + ".lock." + this.file.getCanonicalPath();
    }

    private void closeReadFile() {
        if (this.randomAccessLockFile != null) {
            try {
                this.randomAccessLockFile.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.randomAccessLockFile = null;
        }
    }

    private boolean hasBeenModified() {
        boolean modified = false;
        File localFile = new File(this.file.getAbsolutePath());
        if (localFile.exists()) {
            if (localFile.lastModified() != this.lastModified) {
                LOG.info("Lock file " + this.file.getAbsolutePath() + ", locked at " + new Date(this.lastModified) + ", has been modified at " + new Date(localFile.lastModified()));
                modified = true;
            }
        } else {
            LOG.info("Lock file " + this.file.getAbsolutePath() + ", does not exist");
            modified = true;
        }
        return modified;
    }

    public boolean keepAlive() {
        this.locked = this.locked && this.lock != null && this.lock.isValid() && !this.hasBeenModified();
        return this.locked;
    }
}

