/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;
import org.apache.cxf.security.LoginSecurityContext;

public class RolePrefixSecurityContextImpl
implements LoginSecurityContext {
    private Principal p;
    private Set<Principal> roles;
    private Subject theSubject;

    public RolePrefixSecurityContextImpl(Subject subject, String rolePrefix) {
        this(subject, rolePrefix, "prefix");
    }

    public RolePrefixSecurityContextImpl(Subject subject, String roleClassifier, String roleClassifierType) {
        this.p = RolePrefixSecurityContextImpl.findPrincipal(subject, roleClassifier, roleClassifierType);
        this.roles = RolePrefixSecurityContextImpl.findRoles(subject, roleClassifier, roleClassifierType);
        this.theSubject = subject;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.p;
    }

    @Override
    public boolean isUserInRole(String role) {
        for (Principal principal : this.roles) {
            if (!principal.getName().equals(role)) continue;
            return true;
        }
        return false;
    }

    private static Principal findPrincipal(Subject subject, String roleClassifier, String roleClassifierType) {
        for (Principal p : subject.getPrincipals()) {
            if (RolePrefixSecurityContextImpl.isRole(p, roleClassifier, roleClassifierType)) continue;
            return p;
        }
        return null;
    }

    private static Set<Principal> findRoles(Subject subject, String roleClassifier, String roleClassifierType) {
        HashSet<Principal> set = new HashSet<Principal>();
        for (Principal p : subject.getPrincipals()) {
            if (!RolePrefixSecurityContextImpl.isRole(p, roleClassifier, roleClassifierType)) continue;
            set.add(p);
        }
        return Collections.unmodifiableSet(set);
    }

    private static boolean isRole(Principal p, String roleClassifier, String roleClassifierType) {
        if ("prefix".equals(roleClassifierType)) {
            return p.getName().startsWith(roleClassifier);
        }
        return p.getClass().getName().endsWith(roleClassifier);
    }

    @Override
    public Subject getSubject() {
        return this.theSubject;
    }

    @Override
    public Set<Principal> getUserRoles() {
        return this.roles;
    }
}

