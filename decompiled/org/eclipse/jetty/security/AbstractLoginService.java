/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import jakarta.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.Subject;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLoginService
extends ContainerLifeCycle
implements LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractLoginService.class);
    protected IdentityService _identityService = new DefaultIdentityService();
    protected String _name;
    protected boolean _fullValidate = false;

    protected abstract List<RolePrincipal> loadRoleInfo(UserPrincipal var1);

    protected abstract UserPrincipal loadUserInfo(String var1);

    protected AbstractLoginService() {
        this.addBean(this._identityService);
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public void setIdentityService(IdentityService identityService) {
        if (this.isRunning()) {
            throw new IllegalStateException("Running");
        }
        this.updateBean(this._identityService, identityService);
        this._identityService = identityService;
    }

    public void setName(String name) {
        if (this.isRunning()) {
            throw new IllegalStateException("Running");
        }
        this._name = name;
    }

    @Override
    public String toString() {
        return String.format("%s@%x[%s]", this.getClass().getSimpleName(), this.hashCode(), this._name);
    }

    @Override
    public UserIdentity login(String username, Object credentials, ServletRequest request) {
        if (username == null) {
            return null;
        }
        UserPrincipal userPrincipal = this.loadUserInfo(username);
        if (userPrincipal != null && userPrincipal.authenticate(credentials)) {
            List<RolePrincipal> roles = this.loadRoleInfo(userPrincipal);
            ArrayList roleNames = new ArrayList();
            Subject subject = new Subject();
            userPrincipal.configureSubject(subject);
            if (roles != null) {
                roles.forEach(p -> {
                    p.configureForSubject(subject);
                    roleNames.add(p.getName());
                });
            }
            subject.setReadOnly();
            return this._identityService.newUserIdentity(subject, userPrincipal, roleNames.toArray(new String[0]));
        }
        return null;
    }

    @Override
    public boolean validate(UserIdentity user) {
        if (!this.isFullValidate()) {
            return true;
        }
        UserPrincipal fresh = this.loadUserInfo(user.getUserPrincipal().getName());
        if (fresh == null) {
            return false;
        }
        if (user.getUserPrincipal() instanceof UserPrincipal) {
            return fresh.authenticate((UserPrincipal)user.getUserPrincipal());
        }
        throw new IllegalStateException("UserPrincipal not known");
    }

    @Override
    public IdentityService getIdentityService() {
        return this._identityService;
    }

    @Override
    public void logout(UserIdentity user) {
    }

    public boolean isFullValidate() {
        return this._fullValidate;
    }

    public void setFullValidate(boolean fullValidate) {
        this._fullValidate = fullValidate;
    }
}

