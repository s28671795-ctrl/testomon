/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import jakarta.servlet.ServletRequest;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;

public class EmptyLoginService
implements LoginService {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public UserIdentity login(String username, Object credentials, ServletRequest request) {
        return null;
    }

    @Override
    public boolean validate(UserIdentity user) {
        return false;
    }

    @Override
    public IdentityService getIdentityService() {
        return null;
    }

    @Override
    public void setIdentityService(IdentityService service2) {
    }

    @Override
    public void logout(UserIdentity user) {
    }
}

