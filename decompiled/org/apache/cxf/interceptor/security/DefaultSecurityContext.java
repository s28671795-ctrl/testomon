/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.security.GroupPrincipal;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.security.LoginSecurityContext;

public class DefaultSecurityContext
implements LoginSecurityContext {
    private static final Logger LOG = LogUtils.getL7dLogger(DefaultSecurityContext.class);
    private static Class<?> javaGroup;
    private static Class<?> karafGroup;
    private Principal p;
    private Subject subject;

    public DefaultSecurityContext(Subject subject) {
        this.p = DefaultSecurityContext.findPrincipal(null, subject);
        this.subject = subject;
    }

    public DefaultSecurityContext(String principalName, Subject subject) {
        this.p = DefaultSecurityContext.findPrincipal(principalName, subject);
        this.subject = subject;
    }

    public DefaultSecurityContext(Principal p, Subject subject) {
        this.p = p;
        this.subject = subject;
        if (p == null) {
            this.p = DefaultSecurityContext.findPrincipal(null, subject);
        }
    }

    private static Principal findPrincipal(String principalName, Subject subject) {
        if (subject == null) {
            return null;
        }
        for (Principal principal : subject.getPrincipals()) {
            if (DefaultSecurityContext.isGroupPrincipal(principal) || principalName != null && !principal.getName().equals(principalName)) continue;
            return principal;
        }
        if (principalName != null) {
            for (Principal principal : subject.getPrincipals()) {
                if (DefaultSecurityContext.isGroupPrincipal(principal)) continue;
                return principal;
            }
        }
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.p;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this.subject != null) {
            for (Principal principal : this.subject.getPrincipals()) {
                if (DefaultSecurityContext.isGroupPrincipal(principal) && this.checkGroup(principal, role)) {
                    return true;
                }
                if (this.p == principal || !role.equals(principal.getName())) continue;
                return true;
            }
        }
        return false;
    }

    protected boolean checkGroup(Principal principal, String role) {
        Enumeration members;
        if (principal.getName().equals(role)) {
            return true;
        }
        try {
            Enumeration ms;
            Method m4 = ReflectionUtil.getMethod(principal.getClass(), "members", new Class[0]);
            m4.setAccessible(true);
            members = ms = (Enumeration)m4.invoke((Object)principal, new Object[0]);
        }
        catch (Exception e) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Unable to invoke memebers in " + principal.getName() + ":" + e.getMessage());
            }
            return false;
        }
        while (members.hasMoreElements()) {
            Principal member = (Principal)members.nextElement();
            if (!member.getName().equals(role) && (!DefaultSecurityContext.isGroupPrincipal(member) || !this.checkGroup((GroupPrincipal)member, role))) continue;
            return true;
        }
        return false;
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public Set<Principal> getUserRoles() {
        HashSet<Principal> roles = new HashSet<Principal>();
        if (this.subject != null) {
            for (Principal principal : this.subject.getPrincipals()) {
                if (principal == this.p) continue;
                roles.add(principal);
            }
        }
        return roles;
    }

    private static boolean instanceOfGroup(Object obj) {
        try {
            return javaGroup != null && javaGroup.isInstance(obj) || karafGroup != null && karafGroup.isInstance(obj);
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isGroupPrincipal(Principal principal) {
        return principal instanceof GroupPrincipal || DefaultSecurityContext.instanceOfGroup(principal);
    }

    static {
        try {
            javaGroup = Class.forName("java.security.acl.Group");
        }
        catch (Exception e) {
            javaGroup = null;
        }
        try {
            karafGroup = Class.forName("org.apache.karaf.jaas.boot.principal.Group");
        }
        catch (Exception e) {
            karafGroup = null;
        }
    }
}

