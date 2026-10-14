/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletContext;
import java.util.Enumeration;

public interface FilterConfig {
    public String getFilterName();

    public ServletContext getServletContext();

    public String getInitParameter(String var1);

    public Enumeration<String> getInitParameterNames();
}

