/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.h2.Driver;
import org.h2.tools.Server;
import org.h2.util.StringUtils;

public class JakartaDbStarter
implements ServletContextListener {
    private Connection conn;
    private Server server;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Driver.load();
            ServletContext servletContext = servletContextEvent.getServletContext();
            String string = JakartaDbStarter.getParameter(servletContext, "db.url", "jdbc:h2:~/test");
            String string2 = JakartaDbStarter.getParameter(servletContext, "db.user", "sa");
            String string3 = JakartaDbStarter.getParameter(servletContext, "db.password", "sa");
            String string4 = JakartaDbStarter.getParameter(servletContext, "db.tcpServer", null);
            if (string4 != null) {
                String[] stringArray = StringUtils.arraySplit(string4, ' ', true);
                this.server = Server.createTcpServer(stringArray);
                this.server.start();
            }
            this.conn = DriverManager.getConnection(string, string2, string3);
            servletContext.setAttribute("connection", this.conn);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static String getParameter(ServletContext servletContext, String string, String string2) {
        String string3 = servletContext.getInitParameter(string);
        return string3 == null ? string2 : string3;
    }

    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            Statement statement = this.conn.createStatement();
            statement.execute("SHUTDOWN");
            statement.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            this.conn.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (this.server != null) {
            this.server.stop();
            this.server = null;
        }
    }
}

