/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import liquibase.ExtensibleObject;
import liquibase.plugin.Plugin;
import liquibase.ui.InputHandler;

public interface UIService
extends ExtensibleObject,
Plugin {
    public int getPriority();

    public void sendMessage(String var1);

    public void sendErrorMessage(String var1);

    public void sendErrorMessage(String var1, Throwable var2);

    public <T> T prompt(String var1, T var2, InputHandler<T> var3, Class<T> var4);

    public void setAllowPrompt(boolean var1) throws IllegalArgumentException;

    public boolean getAllowPrompt();
}

