/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

public final class NSDecl {
    private final String prefix;
    private final String uri;
    private final int hashCode;

    public NSDecl(String pfx, String ur) {
        this.prefix = pfx == null ? "".intern() : pfx.intern();
        this.uri = ur.intern();
        this.hashCode = this.toString().hashCode();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getUri() {
        return this.uri;
    }

    public String toString() {
        return this.prefix + ":" + this.uri;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NSDecl)) {
            return false;
        }
        return this.uri == ((NSDecl)obj).uri && this.prefix == ((NSDecl)obj).prefix;
    }
}

