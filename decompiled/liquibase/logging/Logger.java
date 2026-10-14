/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging;

import java.util.logging.Level;
import liquibase.ExtensibleObject;

public interface Logger
extends ExtensibleObject,
AutoCloseable {
    @Override
    default public void close() throws Exception {
    }

    public void log(Level var1, String var2, Throwable var3);

    public void severe(String var1);

    public void severe(String var1, Throwable var2);

    public void warning(String var1);

    public void warning(String var1, Throwable var2);

    public void info(String var1);

    public void info(String var1, Throwable var2);

    public void config(String var1);

    public void config(String var1, Throwable var2);

    public void fine(String var1);

    public void fine(String var1, Throwable var2);

    public void debug(String var1);

    public void debug(String var1, Throwable var2);
}

