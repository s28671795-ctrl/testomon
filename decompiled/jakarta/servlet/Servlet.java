/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public interface Servlet {
    public void init(ServletConfig var1) throws ServletException;

    public ServletConfig getServletConfig();

    public void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    public String getServletInfo();

    public void destroy();
}

