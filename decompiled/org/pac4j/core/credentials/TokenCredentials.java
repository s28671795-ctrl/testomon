/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials;

import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.util.CommonHelper;

public class TokenCredentials
extends Credentials {
    private static final long serialVersionUID = -4270718634364817595L;
    private String token;

    public TokenCredentials(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TokenCredentials that = (TokenCredentials)o;
        return !(this.token == null ? that.token != null : !this.token.equals(that.token));
    }

    @Override
    public int hashCode() {
        return this.token != null ? this.token.hashCode() : 0;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "token", this.token);
    }
}

