/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.util.Properties;
import javax.mail.Session;

public class ProtocolProperties {
    protected String protocol;
    protected String protocolPrefix;
    protected Session session;
    protected boolean sslConnection;
    protected int defaultPort;

    public ProtocolProperties(Session session, String protocol, boolean sslConnection, int defaultPort) {
        this.session = session;
        this.protocol = protocol;
        this.sslConnection = sslConnection;
        this.defaultPort = defaultPort;
        this.protocolPrefix = "mail." + protocol + ".";
    }

    public Session getSession() {
        return this.session;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public boolean getSSLConnection() {
        return this.sslConnection;
    }

    public int getDefaultPort() {
        return this.defaultPort;
    }

    public String getProperty(String name) {
        String fullName = this.protocolPrefix + name;
        return this.session.getProperty(fullName);
    }

    public String getProperty(String name, String defaultValue) {
        String fullName = this.protocolPrefix + name;
        String value = this.session.getProperty(fullName);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public int getIntProperty(String name, int defaultValue) {
        String value = this.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public boolean getBooleanProperty(String name, boolean defaultValue) {
        String value = this.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        return "true".equals(value);
    }

    public String getSessionProperty(String name) {
        String fullName = "mail." + name;
        return this.session.getProperty(fullName);
    }

    public String getSessionProperty(String name, String defaultValue) {
        String fullName = "mail." + name;
        String value = this.session.getProperty(fullName);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public int getIntSessionProperty(String name, int defaultValue) {
        String value = this.getSessionProperty(name);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public boolean getBooleanSessionProperty(String name, boolean defaultValue) {
        String value = this.getSessionProperty(name);
        if (value == null) {
            return defaultValue;
        }
        return "true".equals(value);
    }

    public Properties getProperties() {
        return this.session.getProperties();
    }
}

