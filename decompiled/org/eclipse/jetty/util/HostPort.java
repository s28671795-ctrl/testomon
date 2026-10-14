/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util;

import java.net.InetAddress;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostPort {
    private static final Logger LOG = LoggerFactory.getLogger(HostPort.class);
    private static final int BAD_PORT = -1;
    private final String _host;
    private final int _port;

    public static HostPort unsafe(String authority) {
        return new HostPort(authority, true);
    }

    public HostPort(String host2, int port) {
        this._host = HostPort.normalizeHost(host2);
        this._port = port;
    }

    public HostPort(String authority) throws IllegalArgumentException {
        this(authority, false);
    }

    private HostPort(String authority, boolean unsafe) {
        Object host2;
        int port = 0;
        if (authority == null) {
            LOG.warn("Bad Authority [<null>]");
            if (!unsafe) {
                throw new IllegalArgumentException("No Authority");
            }
            this._host = "";
            this._port = 0;
            return;
        }
        if (authority.isEmpty()) {
            this._host = authority;
            this._port = 0;
            return;
        }
        try {
            if (authority.charAt(0) == '[') {
                int close = authority.lastIndexOf(93);
                if (close < 0) {
                    LOG.warn("Bad IPv6 host: [{}]", (Object)authority);
                    if (!unsafe) {
                        throw new IllegalArgumentException("Bad IPv6 host");
                    }
                    host2 = authority;
                } else {
                    host2 = authority.substring(0, close + 1);
                }
                if (!this.isValidIpAddress((String)host2)) {
                    LOG.warn("Bad IPv6 host: [{}]", host2);
                    if (!unsafe) {
                        throw new IllegalArgumentException("Bad IPv6 host");
                    }
                }
                if (authority.length() > close + 1) {
                    if (authority.charAt(close + 1) != ':') {
                        LOG.warn("Bad IPv6 port: [{}]", (Object)authority);
                        if (!unsafe) {
                            throw new IllegalArgumentException("Bad IPv6 port");
                        }
                        host2 = authority;
                        port = 0;
                    } else {
                        port = this.parsePort(authority.substring(close + 2), unsafe);
                        if (unsafe && port == -1) {
                            host2 = authority;
                            port = 0;
                        }
                    }
                } else {
                    port = 0;
                }
            } else {
                int c = authority.lastIndexOf(58);
                if (c >= 0) {
                    if (c != authority.indexOf(58)) {
                        port = 0;
                        host2 = "[" + authority + "]";
                        if (!this.isValidIpAddress((String)host2)) {
                            LOG.warn("Bad IPv6Address: [{}]", host2);
                            if (!unsafe) {
                                throw new IllegalArgumentException("Bad IPv6 host");
                            }
                            host2 = authority;
                        }
                    } else {
                        host2 = authority.substring(0, c);
                        if (StringUtil.isBlank((String)host2)) {
                            LOG.warn("Bad Authority: [{}]", host2);
                            if (!unsafe) {
                                throw new IllegalArgumentException("Bad Authority");
                            }
                            host2 = "";
                        } else if (!this.isValidHostName((String)host2)) {
                            LOG.warn("Bad Authority: [{}]", host2);
                            if (!unsafe) {
                                throw new IllegalArgumentException("Bad Authority");
                            }
                            host2 = authority;
                        }
                        port = this.parsePort(authority.substring(c + 1), unsafe);
                        if (unsafe && port == -1) {
                            host2 = authority;
                            port = 0;
                        }
                    }
                } else {
                    host2 = authority;
                    if (StringUtil.isBlank((String)host2) || !this.isValidHostName((String)host2)) {
                        LOG.warn("Bad Authority: [{}]", host2);
                        if (!unsafe) {
                            throw new IllegalArgumentException("Bad Authority");
                        }
                    }
                    port = 0;
                }
            }
        }
        catch (IllegalArgumentException iae) {
            if (!unsafe) {
                throw iae;
            }
            host2 = authority;
            port = 0;
        }
        catch (Exception ex) {
            if (!unsafe) {
                throw new IllegalArgumentException("Bad HostPort", ex);
            }
            host2 = authority;
            port = 0;
        }
        this._host = host2;
        this._port = port;
    }

    protected boolean isValidIpAddress(String ip2) {
        try {
            InetAddress.getByName(ip2);
            return true;
        }
        catch (Throwable ignore) {
            return false;
        }
    }

    protected boolean isValidHostName(String name) {
        return URIUtil.isValidHostRegisteredName(name);
    }

    @ManagedAttribute(value="host")
    public String getHost() {
        return this._host;
    }

    @ManagedAttribute(value="port")
    public int getPort() {
        return this._port;
    }

    public int getPort(int defaultPort) {
        return this._port > 0 ? this._port : defaultPort;
    }

    public boolean hasHost() {
        return StringUtil.isNotBlank(this._host);
    }

    public boolean hasPort() {
        return this._port > 0;
    }

    public String toString() {
        if (this._port > 0) {
            return this._host + ":" + this._port;
        }
        return this._host;
    }

    public static String normalizeHost(String host2) {
        if (host2 == null || host2.isEmpty() || host2.charAt(0) == '[' || host2.indexOf(58) < 0) {
            return host2;
        }
        return "[" + host2 + "]";
    }

    public static int parsePort(String rawPort) throws IllegalArgumentException {
        if (StringUtil.isEmpty(rawPort)) {
            throw new IllegalArgumentException("Bad port");
        }
        int port = Integer.parseInt(rawPort);
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Bad port");
        }
        return port;
    }

    private int parsePort(String rawPort, boolean unsafe) {
        if (StringUtil.isEmpty(rawPort)) {
            if (!unsafe) {
                throw new IllegalArgumentException("Bad port [" + rawPort + "]");
            }
            return 0;
        }
        try {
            int port = Integer.parseInt(rawPort);
            if (port <= 0 || port > 65535) {
                LOG.warn("Bad port [{}]", (Object)port);
                if (!unsafe) {
                    throw new IllegalArgumentException("Bad port");
                }
                return -1;
            }
            return port;
        }
        catch (NumberFormatException e) {
            LOG.warn("Bad port [{}]", (Object)rawPort);
            if (!unsafe) {
                throw new IllegalArgumentException("Bad Port");
            }
            return -1;
        }
    }
}

