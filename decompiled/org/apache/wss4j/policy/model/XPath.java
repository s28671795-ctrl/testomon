/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Map;

public class XPath {
    private final String xPath;
    private final Version version;
    private final String filter;
    private final Map<String, String> prefixNamespaceMap;

    public XPath(String xPath, Version version, String filter2, Map<String, String> prefixNamespaceMap) {
        this.xPath = xPath;
        this.version = version;
        this.filter = filter2;
        this.prefixNamespaceMap = prefixNamespaceMap;
    }

    public String getXPath() {
        return this.xPath;
    }

    public Version getVersion() {
        return this.version;
    }

    public String getFilter() {
        return this.filter;
    }

    public Map<String, String> getPrefixNamespaceMap() {
        return this.prefixNamespaceMap;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof XPath)) {
            return false;
        }
        XPath that = (XPath)object;
        if (this.xPath != null && !this.xPath.equals(that.xPath) || this.xPath == null && that.xPath != null) {
            return false;
        }
        if (this.version != that.version) {
            return false;
        }
        return (this.filter == null || this.filter.equals(that.filter)) && (this.filter != null || that.filter == null);
    }

    public int hashCode() {
        int result = 17;
        if (this.xPath != null) {
            result = 31 * result + this.xPath.hashCode();
        }
        if (this.version != null) {
            result = 31 * result + this.version.hashCode();
        }
        if (this.filter != null) {
            result = 31 * result + this.filter.hashCode();
        }
        return result;
    }

    public static enum Version {
        V1("1"),
        V2("2");

        private final String version;

        private Version(String version) {
            this.version = version;
        }

        public String getVersion() {
            return this.version;
        }
    }
}

