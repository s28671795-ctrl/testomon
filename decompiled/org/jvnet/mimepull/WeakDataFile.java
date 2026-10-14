/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jvnet.mimepull.CleanUpExecutorFactory;
import org.jvnet.mimepull.DataFile;
import org.jvnet.mimepull.MIMEParsingException;

final class WeakDataFile
extends WeakReference<DataFile> {
    private static final Logger LOGGER;
    private static int TIMEOUT;
    private static ReferenceQueue<DataFile> refQueue;
    private static List<WeakDataFile> refList;
    private final File file;
    private final RandomAccessFile raf;
    private static boolean hasCleanUpExecutor;

    WeakDataFile(DataFile df, File file) {
        super(df, refQueue);
        refList.add(this);
        this.file = file;
        try {
            this.raf = new RandomAccessFile(file, "rw");
        }
        catch (IOException ioe) {
            throw new MIMEParsingException(ioe);
        }
        if (!hasCleanUpExecutor) {
            WeakDataFile.drainRefQueueBounded();
        }
    }

    synchronized void read(long pointer, byte[] buf, int offset, int length) {
        try {
            this.raf.seek(pointer);
            this.raf.readFully(buf, offset, length);
        }
        catch (IOException ioe) {
            throw new MIMEParsingException(ioe);
        }
    }

    synchronized long writeTo(long pointer, byte[] data, int offset, int length) {
        try {
            this.raf.seek(pointer);
            this.raf.write(data, offset, length);
            return this.raf.getFilePointer();
        }
        catch (IOException ioe) {
            throw new MIMEParsingException(ioe);
        }
    }

    void close() {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Deleting file = {0}", this.file.getName());
        }
        refList.remove(this);
        try {
            this.raf.close();
            boolean deleted = this.file.delete();
            if (!deleted && LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "File {0} was not deleted", this.file.getAbsolutePath());
            }
        }
        catch (IOException ioe) {
            throw new MIMEParsingException(ioe);
        }
    }

    void renameTo(File f) {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Moving file={0} to={1}", new Object[]{this.file, f});
        }
        refList.remove(this);
        try {
            this.raf.close();
            Path target = Files.move(this.file.toPath(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
            boolean renamed = f.toPath().equals(target);
            if (!renamed && LOGGER.isLoggable(Level.INFO)) {
                throw new MIMEParsingException("File " + this.file.getAbsolutePath() + " was not moved to " + f.getAbsolutePath());
            }
        }
        catch (IOException ioe) {
            throw new MIMEParsingException(ioe);
        }
    }

    static void drainRefQueueBounded() {
        WeakDataFile weak;
        while ((weak = (WeakDataFile)refQueue.poll()) != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Cleaning file = {0} from reference queue.", weak.file);
            }
            weak.close();
        }
    }

    static {
        int delay;
        block4: {
            LOGGER = Logger.getLogger(WeakDataFile.class.getName());
            TIMEOUT = 10;
            refQueue = new ReferenceQueue();
            refList = new ArrayList<WeakDataFile>();
            hasCleanUpExecutor = false;
            delay = 10;
            try {
                delay = Integer.getInteger("org.jvnet.mimepull.delay", 10);
            }
            catch (SecurityException se) {
                if (!LOGGER.isLoggable(Level.CONFIG)) break block4;
                LOGGER.log(Level.CONFIG, "Cannot read ''{0}'' property, using defaults.", new Object[]{"org.jvnet.mimepull.delay"});
            }
        }
        CleanUpExecutorFactory executorFactory = CleanUpExecutorFactory.newInstance();
        if (executorFactory != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Initializing clean up executor for MIMEPULL: {0}", executorFactory.getClass().getName());
            }
            ScheduledExecutorService scheduler = executorFactory.getScheduledExecutorService();
            scheduler.scheduleWithFixedDelay(new CleanupRunnable(), delay, delay, TimeUnit.SECONDS);
            hasCleanUpExecutor = true;
        }
    }

    private static class CleanupRunnable
    implements Runnable {
        private CleanupRunnable() {
        }

        @Override
        public void run() {
            try {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "Running cleanup task");
                }
                WeakDataFile weak = (WeakDataFile)refQueue.remove(TIMEOUT);
                while (weak != null) {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, "Cleaning file = {0} from reference queue.", weak.file);
                    }
                    weak.close();
                    weak = (WeakDataFile)refQueue.remove(TIMEOUT);
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }
}

