/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.Set;

public interface ServletContainerInitializer {
    public void onStartup(Set<Class<?>> var1, ServletContext var2) throws ServletException;
}

