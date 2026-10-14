/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.message.DbException;
import org.h2.message.TraceSystem;
import org.h2.store.FileLock;
import org.h2.store.FileLockMethod;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FileUtils;

public class FileLister {
    private FileLister() {
    }

    public static void tryUnlockDatabase(List<String> list, String string) throws SQLException {
        for (String string2 : list) {
            Object object;
            if (string2.endsWith(".lock.db")) {
                object = new FileLock(new TraceSystem(null), string2, 1000);
                try {
                    ((FileLock)object).lock(FileLockMethod.FILE);
                    ((FileLock)object).unlock();
                    continue;
                }
                catch (DbException dbException) {
                    throw DbException.getJdbcSQLException(90133, string);
                }
            }
            if (!string2.endsWith(".mv.db")) continue;
            try {
                object = FilePath.get(string2).open("r");
                Throwable throwable = null;
                try {
                    java.nio.channels.FileLock fileLock = ((FileChannel)object).tryLock(0L, Long.MAX_VALUE, true);
                    fileLock.release();
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (object == null) continue;
                    if (throwable != null) {
                        try {
                            ((AbstractInterruptibleChannel)object).close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    ((AbstractInterruptibleChannel)object).close();
                }
            }
            catch (Exception exception) {
                throw DbException.getJdbcSQLException(90133, exception, string);
            }
        }
    }

    public static String getDir(String string) {
        if (string == null || string.equals("")) {
            return ".";
        }
        return FileUtils.toRealPath(string);
    }

    public static ArrayList<String> getDatabaseFiles(String string, String string2, boolean bl) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String string3 = string2 == null ? null : string2 + '.';
        for (FilePath filePath : FilePath.get(string).newDirectoryStream()) {
            boolean bl2 = false;
            String string4 = filePath.toString();
            if (string4.endsWith(".mv.db")) {
                bl2 = true;
            } else if (bl) {
                if (string4.endsWith(".lock.db")) {
                    bl2 = true;
                } else if (string4.endsWith(".temp.db")) {
                    bl2 = true;
                } else if (string4.endsWith(".trace.db")) {
                    bl2 = true;
                }
            }
            if (!bl2 || string2 != null && !filePath.getName().startsWith(string3)) continue;
            arrayList.add(string4);
        }
        return arrayList;
    }
}

