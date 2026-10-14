/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context;

import org.pac4j.core.exception.TechnicalException;

public final class Cookie {
    private String name;
    private String value;
    private String domain;
    private int maxAge = -1;
    private String path;
    private boolean secure;
    private boolean isHttpOnly = false;
    private String sameSitePolicy;
    private String comment;

    public Cookie(String name, String value) {
        if (name == null || name.length() == 0) {
            throw new TechnicalException("cookie name and value cannot be empty");
        }
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return this.isHttpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.isHttpOnly = httpOnly;
    }

    public String getSameSitePolicy() {
        return this.sameSitePolicy;
    }

    public void setSameSitePolicy(String sameSitePolicy) {
        this.sameSitePolicy = sameSitePolicy;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

