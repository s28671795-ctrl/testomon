/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.security.Principal;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.security.SecurityToken;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.SecurityContext;

public abstract class AbstractSecurityContextInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractSecurityContextInInterceptor.class);

    public AbstractSecurityContextInInterceptor() {
        super("pre-invoke");
    }

    @Override
    public void handleMessage(Message message) {
        SecurityContext context;
        SecurityToken token = message.get(SecurityToken.class);
        if (token == null) {
            this.reportSecurityException("Security Token is not available on the current message");
        }
        if ((context = message.get(SecurityContext.class)) == null || context.getUserPrincipal() == null) {
            this.reportSecurityException("User Principal is not available on the current message");
        }
        Subject subject = null;
        try {
            subject = this.createSubject(token);
        }
        catch (Exception ex) {
            this.reportSecurityException("Failed Authentication : Subject has not been created, " + ex.getMessage());
        }
        if (subject == null || subject.getPrincipals().isEmpty()) {
            this.reportSecurityException("Failed Authentication : Invalid Subject");
        }
        Principal principal = this.getPrincipal(context.getUserPrincipal(), subject);
        SecurityContext sc = this.createSecurityContext(principal, subject);
        message.put(SecurityContext.class, sc);
    }

    protected Principal getPrincipal(Principal originalPrincipal, Subject subject) {
        Principal[] ps = subject.getPrincipals().toArray(new Principal[subject.getPrincipals().size()]);
        if (ps != null && ps.length > 0 && !DefaultSecurityContext.isGroupPrincipal(ps[0])) {
            return ps[0];
        }
        return originalPrincipal;
    }

    protected SecurityContext createSecurityContext(Principal p, Subject subject) {
        return new DefaultSecurityContext(p, subject);
    }

    protected abstract Subject createSubject(SecurityToken var1);

    protected void reportSecurityException(String errorMessage) {
        LOG.severe(errorMessage);
        throw new SecurityException(errorMessage);
    }
}

