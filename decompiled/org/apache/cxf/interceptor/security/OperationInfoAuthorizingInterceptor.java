/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.interceptor.security.SimpleAuthorizingInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;

public class OperationInfoAuthorizingInterceptor
extends SimpleAuthorizingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(OperationInfoAuthorizingInterceptor.class);

    @Override
    public void handleMessage(Message message) {
        OperationInfo opinfo = this.getTargetOperationInfo(message);
        SecurityContext sc = message.get(SecurityContext.class);
        if (sc != null && sc.getUserPrincipal() != null ? opinfo.getName() != null && this.authorize(sc, opinfo.getName().getLocalPart()) : !this.isMethodProtected(opinfo.getName().getLocalPart()) && this.isAllowAnonymousUsers()) {
            return;
        }
        throw new AccessDeniedException("Unauthorized");
    }

    protected boolean authorize(SecurityContext sc, String key) {
        List<String> expectedRoles = this.getExpectedRoles(key);
        if (expectedRoles.isEmpty()) {
            List<String> denyRoles = this.getDenyRoles(key);
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

    protected OperationInfo getTargetOperationInfo(Message message) {
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop != null) {
            return bop.getOperationInfo();
        }
        throw new AccessDeniedException("OperationInfo is not available : Unauthorized");
    }

    protected List<String> getExpectedRoles(String key) {
        List roles = (List)this.methodRolesMap.get(key);
        if (roles != null) {
            return roles;
        }
        return this.globalRoles;
    }

    protected List<String> getDenyRoles(String key) {
        return Collections.emptyList();
    }

    protected boolean isMethodProtected(String key) {
        return !this.getExpectedRoles(key).isEmpty() || !this.getDenyRoles(key).isEmpty();
    }
}

