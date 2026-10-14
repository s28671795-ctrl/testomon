/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import org.h2.engine.Database;
import org.h2.engine.User;
import org.h2.security.auth.AuthConfigException;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;

public interface Authenticator {
    public User authenticate(AuthenticationInfo var1, Database var2) throws AuthenticationException;

    public void init(Database var1) throws AuthConfigException;
}

