/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.Enumeration;
import liquibase.integration.servlet.GenericServletListener;
import liquibase.integration.servlet.GenericServletWrapper;

public class LiquibaseJakartaServletListener
extends GenericServletListener
implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(new JakartaServletContext(sce.getServletContext()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private static class JakartaServletContext
    extends GenericServletWrapper.ServletContext {
        private final ServletContext servletContext;

        private JakartaServletContext(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        @Override
        public void log(String message) {
            this.servletContext.log(message);
        }

        @Override
        public String getInitParameter(String key) {
            return this.servletContext.getInitParameter(key);
        }

        @Override
        public Enumeration<String> getInitParameterNames() {
            return this.servletContext.getInitParameterNames();
        }
    }
}

