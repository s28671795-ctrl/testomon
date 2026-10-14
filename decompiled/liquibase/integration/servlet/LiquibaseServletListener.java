/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletContextEvent
 *  javax.servlet.ServletContextListener
 */
package liquibase.integration.servlet;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import liquibase.integration.servlet.GenericServletListener;
import liquibase.integration.servlet.GenericServletWrapper;

public class LiquibaseServletListener
extends GenericServletListener
implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(new JavaxServletContext(sce.getServletContext()));
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    private static class JavaxServletContext
    extends GenericServletWrapper.ServletContext {
        private final ServletContext servletContext;

        private JavaxServletContext(ServletContext servletContext) {
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

