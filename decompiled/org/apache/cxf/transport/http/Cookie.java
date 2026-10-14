/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

public class Cookie {
    public static final String DISCARD_ATTRIBUTE = "discard";
    public static final String MAX_AGE_ATTRIBUTE = "max-age";
    public static final String PATH_ATTRIBUTE = "path";
    private String name;
    private String value;
    private String path;
    private int maxAge = -1;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public int hashCode() {
        return 17 * this.name.hashCode() + (this.path != null ? 11 * this.path.hashCode() : 0);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Cookie)) {
            return false;
        }
        Cookie c = (Cookie)o;
        return this.name.equals(c.name) && (this.path == null && c.path == null || this.path != null && this.path.equals(c.path));
    }

    public String requestCookieHeader() {
        StringBuilder b = new StringBuilder(64);
        b.append("$Version=\"1\"");
        b.append("; ").append(this.getName()).append('=').append(this.getValue());
        if (this.getPath() != null && this.getPath().length() > 0) {
            b.append("; $Path=").append(this.getPath());
        }
        return b.toString();
    }
}

