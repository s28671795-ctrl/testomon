/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.storage;

import com.incoresoft.middleware.connector.Connector;
import java.io.File;
import java.io.InputStream;
import org.pf4j.ExtensionPoint;

public interface Storage
extends ExtensionPoint,
Connector {
    public File get(String var1);

    public File save(String var1, File var2);

    public File save(String var1, InputStream var2);

    public void delete(String var1);
}

