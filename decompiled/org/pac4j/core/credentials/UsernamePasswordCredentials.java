/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials;

import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.util.CommonHelper;

public class UsernamePasswordCredentials
extends Credentials {
    private static final long serialVersionUID = -7229878989627796565L;
    private String username;
    private String password;

    public UsernamePasswordCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UsernamePasswordCredentials that = (UsernamePasswordCredentials)o;
        if (this.username != null ? !this.username.equals(that.username) : that.username != null) {
            return false;
        }
        return !(this.password == null ? that.password != null : !this.password.equals(that.password));
    }

    @Override
    public int hashCode() {
        int result = this.username != null ? this.username.hashCode() : 0;
        result = 31 * result + (this.password != null ? this.password.hashCode() : 0);
        return result;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "username", this.username, "password", "[PROTECTED]");
    }
}

