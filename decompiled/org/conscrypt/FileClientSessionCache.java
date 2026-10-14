/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;
import org.conscrypt.SSLClientSessionCache;
import org.conscrypt.io.IoUtils;

public final class FileClientSessionCache {
    private static final Logger logger = Logger.getLogger(FileClientSessionCache.class.getName());
    public static final int MAX_SIZE = 12;
    static final Map<File, Impl> caches = new HashMap<File, Impl>();

    private FileClientSessionCache() {
    }

    public static synchronized SSLClientSessionCache usingDirectory(File directory) throws IOException {
        Impl cache = caches.get(directory);
        if (cache == null) {
            cache = new Impl(directory);
            caches.put(directory, cache);
        }
        return cache;
    }

    static synchronized void reset() {
        caches.clear();
    }

    static class CacheFile
    extends File {
        final String name;
        long lastModified = -1L;

        CacheFile(File dir, String name) {
            super(dir, name);
            this.name = name;
        }

        @Override
        public long lastModified() {
            long lastModified = this.lastModified;
            if (lastModified == -1L) {
                lastModified = this.lastModified = super.lastModified();
            }
            return lastModified;
        }

        @Override
        public int compareTo(File another) {
            long result = this.lastModified() - another.lastModified();
            if (result == 0L) {
                return super.compareTo(another);
            }
            return result < 0L ? -1 : 1;
        }
    }

    static class Impl
    implements SSLClientSessionCache {
        final File directory;
        Map<String, File> accessOrder = Impl.newAccessOrder();
        int size;
        String[] initialFiles;

        Impl(File directory) throws IOException {
            boolean exists = directory.exists();
            if (exists && !directory.isDirectory()) {
                throw new IOException(directory + " exists but is not a directory.");
            }
            if (exists) {
                this.initialFiles = directory.list();
                if (this.initialFiles == null) {
                    throw new IOException(directory + " exists but cannot list contents.");
                }
                Arrays.sort(this.initialFiles);
                this.size = this.initialFiles.length;
            } else {
                if (!directory.mkdirs()) {
                    throw new IOException("Creation of " + directory + " directory failed.");
                }
                this.size = 0;
            }
            this.directory = directory;
        }

        private static Map<String, File> newAccessOrder() {
            return new LinkedHashMap<String, File>(12, 0.75f, true);
        }

        private static String fileName(String host2, int port) {
            if (host2 == null) {
                throw new NullPointerException("host == null");
            }
            return host2 + "." + port;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public synchronized byte[] getSessionData(String host2, int port) {
            FileInputStream in;
            String name = Impl.fileName(host2, port);
            File file = this.accessOrder.get(name);
            if (file == null) {
                if (this.initialFiles == null) {
                    return null;
                }
                if (Arrays.binarySearch(this.initialFiles, name) < 0) {
                    return null;
                }
                file = new File(this.directory, name);
                this.accessOrder.put(name, file);
            }
            try {
                in = new FileInputStream(file);
            }
            catch (FileNotFoundException e) {
                Impl.logReadError(host2, file, e);
                return null;
            }
            try {
                int size = (int)file.length();
                byte[] data = new byte[size];
                new DataInputStream(in).readFully(data);
                byte[] byArray = data;
                return byArray;
            }
            catch (IOException e) {
                Impl.logReadError(host2, file, e);
                byte[] byArray = null;
                return byArray;
            }
            finally {
                IoUtils.closeQuietly(in);
            }
        }

        static void logReadError(String host2, File file, Throwable t2) {
            logger.log(Level.WARNING, "FileClientSessionCache: Error reading session data for " + host2 + " from " + file + ".", t2);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public synchronized void putSessionData(SSLSession session, byte[] sessionData) {
            FileOutputStream out;
            String host2 = session.getPeerHost();
            if (sessionData == null) {
                throw new NullPointerException("sessionData == null");
            }
            String name = Impl.fileName(host2, session.getPeerPort());
            File file = new File(this.directory, name);
            boolean existedBefore = file.exists();
            try {
                out = new FileOutputStream(file);
            }
            catch (FileNotFoundException e) {
                Impl.logWriteError(host2, file, e);
                return;
            }
            if (!existedBefore) {
                ++this.size;
                this.makeRoom();
            }
            boolean writeSuccessful = false;
            try {
                out.write(sessionData);
                writeSuccessful = true;
                return;
            }
            catch (IOException e) {
                Impl.logWriteError(host2, file, e);
                return;
            }
            finally {
                boolean closeSuccessful = false;
                try {
                    out.close();
                    closeSuccessful = true;
                }
                catch (IOException e) {
                    Impl.logWriteError(host2, file, e);
                }
                finally {
                    if (!writeSuccessful || !closeSuccessful) {
                        this.delete(file);
                    } else {
                        this.accessOrder.put(name, file);
                    }
                }
            }
        }

        private void makeRoom() {
            if (this.size <= 12) {
                return;
            }
            this.indexFiles();
            int removals = this.size - 12;
            Iterator<File> i = this.accessOrder.values().iterator();
            do {
                this.delete(i.next());
                i.remove();
            } while (--removals > 0);
        }

        private void indexFiles() {
            String[] initialFiles = this.initialFiles;
            if (initialFiles != null) {
                this.initialFiles = null;
                TreeSet<CacheFile> diskOnly = new TreeSet<CacheFile>();
                for (String name : initialFiles) {
                    if (this.accessOrder.containsKey(name)) continue;
                    diskOnly.add(new CacheFile(this.directory, name));
                }
                if (!diskOnly.isEmpty()) {
                    Map<String, File> newOrder = Impl.newAccessOrder();
                    for (CacheFile cacheFile : diskOnly) {
                        newOrder.put(cacheFile.name, cacheFile);
                    }
                    newOrder.putAll(this.accessOrder);
                    this.accessOrder = newOrder;
                }
            }
        }

        private void delete(File file) {
            if (!file.delete()) {
                IOException e = new IOException("FileClientSessionCache: Failed to delete " + file + ".");
                logger.log(Level.WARNING, e.getMessage(), e);
            }
            --this.size;
        }

        static void logWriteError(String host2, File file, Throwable t2) {
            logger.log(Level.WARNING, "FileClientSessionCache: Error writing session data for " + host2 + " to " + file + ".", t2);
        }
    }
}

