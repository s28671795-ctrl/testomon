/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;

public abstract class GenericServlet
implements Servlet,
ServletConfig,
Serializable {
    private static final long serialVersionUID = -8592279577370996712L;
    private static final String LSTRING_FILE = "jakarta.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle("jakarta.servlet.LocalStrings");
    private transient ServletConfig config;

    @Override
    public void destroy() {
    }

    @Override
    public String getInitParameter(String name) {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getInitParameterNames();
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public ServletContext getServletContext() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getServletContext();
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    public void init() throws ServletException {
    }

    public void log(String msg) {
        this.getServletContext().log(this.getServletName() + ": " + msg);
    }

    public void log(String message, Throwable t2) {
        this.getServletContext().log(this.getServletName() + ": " + message, t2);
    }

    @Override
    public abstract void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    @Override
    public String getServletName() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getServletName();
    }
}

