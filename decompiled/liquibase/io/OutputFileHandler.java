/*
 * Decompiled with CFR 0.152.
 */
package liquibase.io;

import java.io.IOException;
import liquibase.command.CommandScope;
import liquibase.plugin.Plugin;

public interface OutputFileHandler
extends Plugin {
    public int getPriority();

    public void create(String var1, CommandScope var2) throws IOException;

    public void close() throws IOException;
}

