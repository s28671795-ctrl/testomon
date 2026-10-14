/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value.lob;

import java.io.BufferedInputStream;
import java.io.InputStream;
import org.h2.engine.SysProperties;
import org.h2.store.DataHandler;
import org.h2.store.FileStore;
import org.h2.store.FileStoreInputStream;
import org.h2.store.fs.FileUtils;
import org.h2.value.ValueLob;
import org.h2.value.lob.LobData;

public final class LobDataFile
extends LobData {
    private DataHandler handler;
    private final String fileName;
    private final FileStore tempFile;

    public LobDataFile(DataHandler dataHandler, String string, FileStore fileStore) {
        this.handler = dataHandler;
        this.fileName = string;
        this.tempFile = fileStore;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void remove(ValueLob valueLob) {
        if (this.fileName != null) {
            if (this.tempFile != null) {
                this.tempFile.stopAutoDelete();
            }
            Object object = this.handler.getLobSyncObject();
            synchronized (object) {
                FileUtils.delete(this.fileName);
            }
        }
    }

    @Override
    public InputStream getInputStream(long l) {
        FileStore fileStore = this.handler.openFile(this.fileName, "r", true);
        boolean bl = SysProperties.lobCloseBetweenReads;
        return new BufferedInputStream(new FileStoreInputStream(fileStore, false, bl), 4096);
    }

    @Override
    public DataHandler getDataHandler() {
        return this.handler;
    }

    public String toString() {
        return "lob-file: " + this.fileName;
    }
}

