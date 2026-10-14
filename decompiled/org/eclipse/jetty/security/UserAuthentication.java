/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import org.eclipse.jetty.security.AbstractUserAuthentication;
import org.eclipse.jetty.server.UserIdentity;

public class UserAuthentication
extends AbstractUserAuthentication {
    public UserAuthentication(String method2, UserIdentity userIdentity) {
        super(method2, userIdentity);
    }

    public String toString() {
        return "{User," + this.getAuthMethod() + "," + this._userIdentity + "}";
    }
}

