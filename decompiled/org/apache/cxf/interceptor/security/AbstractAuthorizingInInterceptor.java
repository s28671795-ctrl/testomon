/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.SecurityContext;

public abstract class AbstractAuthorizingInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractAuthorizingInInterceptor.class);
    private static final String ALL_ROLES = "*";
    private boolean allowAnonymousUsers = true;

    public AbstractAuthorizingInInterceptor() {
        this(true);
    }

    public AbstractAuthorizingInInterceptor(boolean uniqueId) {
        super(null, "pre-invoke", uniqueId);
    }

    @Override
    public void handleMessage(Message message) {
        Method method2 = this.getTargetMethod(message);
        SecurityContext sc = message.get(SecurityContext.class);
        if (sc != null && sc.getUserPrincipal() != null ? this.authorize(sc, method2) : !this.isMethodProtected(method2) && this.isAllowAnonymousUsers()) {
            return;
        }
        throw new AccessDeniedException("Unauthorized");
    }

    protected Method getTargetMethod(Message m4) {
        return MessageUtils.getTargetMethod(m4).orElseThrow(() -> new AccessDeniedException("Method is not available : Unauthorized"));
    }

    protected boolean authorize(SecurityContext sc, Method method2) {
        List<String> expectedRoles = this.getExpectedRoles(method2);
        if (expectedRoles.isEmpty()) {
            List<String> denyRoles = this.getDenyRoles(method2);
            return denyRoles.isEmpty() || this.isUserInRole(sc, denyRoles, true);
        }
        if (this.isUserInRole(sc, expectedRoles, false)) {
            return true;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine(sc.getUserPrincipal().getName() + " is not authorized");
        }
        return false;
    }

    protected boolean isMethodProtected(Method method2) {
        return !this.getExpectedRoles(method2).isEmpty() || !this.getDenyRoles(method2).isEmpty();
    }

    protected boolean isUserInRole(SecurityContext sc, List<String> roles, boolean deny) {
        if (roles.size() == 1 && ALL_ROLES.equals(roles.get(0))) {
            return !deny;
        }
        for (String role : roles) {
            if (!sc.isUserInRole(role)) continue;
            return !deny;
        }
        return deny;
    }

    protected abstract List<String> getExpectedRoles(Method var1);

    protected List<String> getDenyRoles(Method method2) {
        return Collections.emptyList();
    }

    public boolean isAllowAnonymousUsers() {
        return this.allowAnonymousUsers;
    }

    public void setAllowAnonymousUsers(boolean allowAnonymousUsers) {
        this.allowAnonymousUsers = allowAnonymousUsers;
    }
}

