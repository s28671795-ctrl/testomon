/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.LdapURLRetryMetadata;
import org.ldaptive.LdapUtils;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchScope;

public class LdapURL {
    protected static final Pattern URL_PATTERN = Pattern.compile("([lL][dD][aA][pP][sSiI]?)://(\\[[0-9A-Fa-f:]+\\]|[^:/]+)?(?::(\\d+))?(?:/(?:([^?]+))?(?:\\?([^?]*))?(?:\\?([^?]*))?(?:\\?(.*))?)?");
    protected static final int DEFAULT_LDAP_PORT = 389;
    protected static final int DEFAULT_LDAPS_PORT = 636;
    protected static final String DEFAULT_BASE_DN = "";
    protected static final String DEFAULT_FILTER = "(objectClass=*)";
    protected static final SearchScope DEFAULT_SCOPE = SearchScope.OBJECT;
    protected static final String[] DEFAULT_ATTRIBUTES = ReturnAttributes.ALL_USER.value();
    private static final int HASH_CODE_SEED = 10333;
    private String scheme;
    private String hostname;
    private int port;
    private String baseDn;
    private String[] attributes;
    private SearchScope scope;
    private String filter;
    private LdapURLRetryMetadata retryMetadata;
    private boolean active = true;
    private InetAddress inetAddress;

    private LdapURL() {
    }

    public LdapURL(String hostname, int port) {
        this("ldap://" + hostname + ":" + port);
    }

    public LdapURL(String url2) {
        this.parseURL(url2);
    }

    protected LdapURL(String scheme2, String hostname, int port, String baseDn, String[] attributes, SearchScope scope, String filter2) {
        if (scheme2 == null) {
            throw new IllegalArgumentException("Scheme cannot be null");
        }
        this.scheme = scheme2;
        this.hostname = hostname;
        this.port = port;
        this.baseDn = baseDn;
        this.attributes = attributes;
        this.scope = scope;
        this.filter = filter2;
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        if (this.port == -1) {
            return "ldaps".equals(this.scheme) ? 636 : 389;
        }
        return this.port;
    }

    public boolean isDefaultPort() {
        return this.port == -1;
    }

    public String getBaseDn() {
        return this.baseDn == null ? DEFAULT_BASE_DN : this.baseDn;
    }

    public boolean isDefaultBaseDn() {
        return this.baseDn == null;
    }

    public String[] getAttributes() {
        return this.attributes == null ? DEFAULT_ATTRIBUTES : this.attributes;
    }

    public boolean isDefaultAttributes() {
        return this.attributes == null;
    }

    public SearchScope getScope() {
        return this.scope == null ? DEFAULT_SCOPE : this.scope;
    }

    public boolean isDefaultScope() {
        return this.scope == null;
    }

    public String getFilter() {
        return this.filter == null ? DEFAULT_FILTER : this.filter;
    }

    public boolean isDefaultFilter() {
        return this.filter == null;
    }

    public String getUrl() {
        StringBuilder sb = new StringBuilder(this.scheme).append("://");
        String hostname = this.getHostname();
        if (hostname != null) {
            if (hostname.contains(":")) {
                sb.append("[").append(hostname).append("]");
            } else {
                sb.append(hostname);
            }
        }
        sb.append(":").append(this.getPort());
        sb.append("/").append(LdapUtils.percentEncode(this.getBaseDn()));
        sb.append("?");
        String[] attrs = this.getAttributes();
        for (int i = 0; i < attrs.length; ++i) {
            sb.append(attrs[i]);
            if (i + 1 >= attrs.length) continue;
            sb.append(",");
        }
        sb.append("?");
        SearchScope scope = this.getScope();
        if (SearchScope.OBJECT == scope) {
            sb.append("base");
        } else if (SearchScope.ONELEVEL == scope) {
            sb.append("one");
        } else if (SearchScope.SUBTREE == scope) {
            sb.append("sub");
        }
        sb.append("?").append(LdapUtils.percentEncode(this.getFilter()));
        return sb.toString();
    }

    public String getHostnameWithPort() {
        return (this.getHostname() != null ? this.getHostname() : "null") + ":" + this.getPort();
    }

    public String getHostnameWithSchemeAndPort() {
        return this.getScheme() + "://" + (this.getHostname() != null ? this.getHostname() : "null") + ":" + this.getPort();
    }

    LdapURLRetryMetadata getRetryMetadata() {
        return this.retryMetadata;
    }

    void setRetryMetadata(LdapURLRetryMetadata metadata) {
        this.retryMetadata = metadata;
    }

    boolean isActive() {
        return this.active;
    }

    void activate() {
        this.active = true;
    }

    void deactivate() {
        this.active = false;
    }

    public InetAddress getInetAddress() {
        return this.inetAddress;
    }

    void setInetAddress(InetAddress address) {
        this.inetAddress = address;
    }

    public static LdapURL copy(LdapURL ldapURL) {
        LdapURL url2 = new LdapURL();
        url2.scheme = ldapURL.scheme;
        url2.hostname = ldapURL.hostname;
        url2.port = ldapURL.port;
        url2.baseDn = ldapURL.baseDn;
        url2.attributes = ldapURL.attributes;
        url2.scope = ldapURL.scope;
        url2.filter = ldapURL.filter;
        url2.retryMetadata = ldapURL.retryMetadata;
        url2.active = ldapURL.active;
        url2.inetAddress = ldapURL.inetAddress;
        return url2;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapURL) {
            LdapURL v = (LdapURL)o;
            return LdapUtils.areEqual(this.scheme, v.scheme) && LdapUtils.areEqual(this.hostname, v.hostname) && LdapUtils.areEqual(this.port, v.port) && LdapUtils.areEqual(this.baseDn, v.baseDn) && LdapUtils.areEqual(this.attributes, v.attributes) && LdapUtils.areEqual((Object)this.scope, (Object)v.scope) && LdapUtils.areEqual(this.filter, v.filter) && LdapUtils.areEqual(this.inetAddress, v.inetAddress);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10333, new Object[]{this.scheme, this.hostname, this.port, this.baseDn, this.attributes, this.scope, this.filter, this.inetAddress});
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "scheme=" + this.scheme + ", " + "hostname=" + this.hostname + ", " + "port=" + this.port + ", " + "baseDn=" + this.baseDn + ", " + "attributes=" + Arrays.toString(this.attributes) + ", " + "scope=" + (Object)((Object)this.scope) + ", " + "filter=" + this.filter + ", " + "inetAddress=" + this.inetAddress + "]";
    }

    protected void parseURL(String url2) {
        Matcher m4 = URL_PATTERN.matcher(url2);
        if (!m4.matches()) {
            throw new IllegalArgumentException("Invalid LDAP URL: " + url2);
        }
        this.scheme = m4.group(1).toLowerCase();
        this.hostname = m4.group(2);
        if (this.hostname != null && this.hostname.startsWith("[") && this.hostname.endsWith("]")) {
            this.hostname = this.hostname.substring(1, this.hostname.length() - 1).trim();
        }
        this.port = m4.group(3) != null ? Integer.parseInt(m4.group(3)) : -1;
        String string = this.baseDn = m4.group(4) != null ? LdapUtils.percentDecode(m4.group(4)) : null;
        this.attributes = m4.group(5) != null ? (m4.group(5).length() > 0 ? m4.group(5).split(",") : null) : null;
        String scope = m4.group(6);
        if (scope != null && scope.length() > 0) {
            if ("base".equalsIgnoreCase(scope)) {
                this.scope = SearchScope.OBJECT;
            } else if ("one".equalsIgnoreCase(scope)) {
                this.scope = SearchScope.ONELEVEL;
            } else if ("sub".equalsIgnoreCase(scope)) {
                this.scope = SearchScope.SUBTREE;
            } else {
                throw new IllegalArgumentException("Invalid scope: " + scope);
            }
        }
        this.filter = m4.group(7) != null ? (m4.group(7).length() > 0 ? LdapUtils.percentDecode(m4.group(7)) : null) : null;
    }
}

