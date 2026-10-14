/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import org.h2.message.DbException;
import org.h2.store.FileStore;
import org.h2.store.LobStorageInterface;
import org.h2.util.SmallLRUCache;
import org.h2.util.TempFileDeleter;
import org.h2.value.CompareMode;

public interface DataHandler {
    public String getDatabasePath();

    public FileStore openFile(String var1, String var2, boolean var3);

    public void checkPowerOff() throws DbException;

    public void checkWritingAllowed() throws DbException;

    public int getMaxLengthInplaceLob();

    public TempFileDeleter getTempFileDeleter();

    public Object getLobSyncObject();

    public SmallLRUCache<String, String[]> getLobFileListCache();

    public LobStorageInterface getLobStorage();

    public int readLob(long var1, byte[] var3, long var4, byte[] var6, int var7, int var8);

    public CompareMode getCompareMode();
}

