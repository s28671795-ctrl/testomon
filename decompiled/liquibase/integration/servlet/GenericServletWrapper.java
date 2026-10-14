/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

class GenericServletWrapper {
    GenericServletWrapper() {
    }

    public static abstract class HttpServletResponse {
        public abstract void setStatus(int var1);

        public abstract void setContentType(String var1);

        public abstract PrintWriter getWriter() throws IOException;
    }

    public static abstract class HttpServletRequest {
        public abstract String getParameter(String var1);

        public abstract String getRequestURI();
    }

    public static abstract class ServletContext {
        public abstract void log(String var1);

        public abstract String getInitParameter(String var1);

        public abstract Enumeration<String> getInitParameterNames();
    }
}

