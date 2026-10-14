/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.net;

import java.io.Serializable;
import java.net.URISyntaxException;
import org.apache.hc.core5.annotation.Contract;
import org.apache.hc.core5.annotation.ThreadingBehavior;
import org.apache.hc.core5.net.InetAddressUtils;
import org.apache.hc.core5.net.NamedEndpoint;
import org.apache.hc.core5.net.Ports;
import org.apache.hc.core5.net.URISupport;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.LangUtils;
import org.apache.hc.core5.util.TextUtils;
import org.apache.hc.core5.util.Tokenizer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class Host
implements NamedEndpoint,
Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String lcName;
    private final int port;

    public Host(String name, int port) {
        this.name = Args.notNull(name, "Host name");
        this.port = Ports.checkWithDefault(port);
        this.lcName = TextUtils.toLowerCase(this.name);
    }

    static Host parse(CharSequence s2, Tokenizer.Cursor cursor) throws URISyntaxException {
        int port;
        String hostName;
        boolean ipv6Brackets;
        Tokenizer tokenizer = Tokenizer.INSTANCE;
        boolean bl = ipv6Brackets = !cursor.atEnd() && s2.charAt(cursor.getPos()) == '[';
        if (ipv6Brackets) {
            cursor.updatePos(cursor.getPos() + 1);
            hostName = tokenizer.parseContent(s2, cursor, URISupport.IPV6_HOST_TERMINATORS);
            if (cursor.atEnd() || s2.charAt(cursor.getPos()) != ']') {
                throw URISupport.createException(s2, cursor, "Expected an IPv6 closing bracket ']'");
            }
            cursor.updatePos(cursor.getPos() + 1);
            if (!InetAddressUtils.isIPv6Address(hostName)) {
                throw URISupport.createException(s2, cursor, "Expected an IPv6 address");
            }
        } else {
            hostName = tokenizer.parseContent(s2, cursor, URISupport.PORT_SEPARATORS);
        }
        String portText = null;
        if (!cursor.atEnd() && s2.charAt(cursor.getPos()) == ':') {
            cursor.updatePos(cursor.getPos() + 1);
            portText = tokenizer.parseContent(s2, cursor, URISupport.TERMINATORS);
        }
        if (!TextUtils.isBlank(portText)) {
            if (!ipv6Brackets && portText.contains(":")) {
                throw URISupport.createException(s2, cursor, "Expected IPv6 address to be enclosed in brackets");
            }
            try {
                port = Integer.parseInt(portText);
            }
            catch (NumberFormatException ex) {
                throw URISupport.createException(s2, cursor, "Port is invalid");
            }
        } else {
            port = -1;
        }
        return new Host(hostName, port);
    }

    static Host parse(CharSequence s2) throws URISyntaxException {
        Tokenizer.Cursor cursor = new Tokenizer.Cursor(0, s2.length());
        return Host.parse(s2, cursor);
    }

    static void format(StringBuilder buf, NamedEndpoint endpoint) {
        String hostName = endpoint.getHostName();
        if (InetAddressUtils.isIPv6Address(hostName)) {
            buf.append('[').append(hostName).append(']');
        } else {
            buf.append(hostName);
        }
        if (endpoint.getPort() != -1) {
            buf.append(":");
            buf.append(endpoint.getPort());
        }
    }

    static void format(StringBuilder buf, Host host2) {
        Host.format(buf, (NamedEndpoint)host2);
    }

    static String format(Host host2) {
        StringBuilder buf = new StringBuilder();
        Host.format(buf, host2);
        return buf.toString();
    }

    public static Host create(String s2) throws URISyntaxException {
        Args.notEmpty(s2, "HTTP Host");
        Tokenizer.Cursor cursor = new Tokenizer.Cursor(0, s2.length());
        Host host2 = Host.parse(s2, cursor);
        if (TextUtils.isBlank(host2.getHostName())) {
            throw URISupport.createException(s2, cursor, "Hostname is invalid");
        }
        if (!cursor.atEnd()) {
            throw URISupport.createException(s2, cursor, "Unexpected content");
        }
        return host2;
    }

    @Override
    public String getHostName() {
        return this.name;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Host) {
            Host that = (Host)o;
            return this.lcName.equals(that.lcName) && this.port == that.port;
        }
        return false;
    }

    public int hashCode() {
        int hash = 17;
        hash = LangUtils.hashCode(hash, this.lcName);
        hash = LangUtils.hashCode(hash, this.port);
        return hash;
    }

    public String toString() {
        return Host.format(this);
    }
}

