/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.File;
import org.apache.activemq.Service;
import org.apache.activemq.store.PList;

public interface PListStore
extends Service {
    public File getDirectory();

    public void setDirectory(File var1);

    public PList getPList(String var1) throws Exception;

    public boolean removePList(String var1) throws Exception;

    public long size();
}

