/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

public interface ExtensionConfig {
    private static Parser getParser() {
        return ServiceLoader.load(Parser.class).iterator().next();
    }

    public static ExtensionConfig parse(String parameterizedName) {
        return ExtensionConfig.getParser().parse(parameterizedName);
    }

    public String getName();

    public int getParameter(String var1, int var2);

    public String getParameter(String var1, String var2);

    public String getParameterizedName();

    public Set<String> getParameterKeys();

    public Map<String, String> getParameters();

    public void setParameter(String var1);

    public void setParameter(String var1, int var2);

    public void setParameter(String var1, String var2);

    public static interface Parser {
        public ExtensionConfig parse(String var1);
    }
}

