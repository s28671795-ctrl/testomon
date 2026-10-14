/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import java.security.Principal;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;

class SecurityContextInjectee
implements SecurityContext {
    private final ContainerRequestContext requestContext;

    @Inject
    public SecurityContextInjectee(ContainerRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    public Principal getUserPrincipal() {
        this.checkState();
        return this.requestContext.getSecurityContext().getUserPrincipal();
    }

    @Override
    public boolean isUserInRole(String role) {
        this.checkState();
        return this.requestContext.getSecurityContext().isUserInRole(role);
    }

    @Override
    public boolean isSecure() {
        this.checkState();
        return this.requestContext.getSecurityContext().isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        this.checkState();
        return this.requestContext.getSecurityContext().getAuthenticationScheme();
    }

    public int hashCode() {
        this.checkState();
        return 7 * this.requestContext.getSecurityContext().hashCode();
    }

    public boolean equals(Object that) {
        this.checkState();
        return that instanceof SecurityContext && that.equals(this.requestContext.getSecurityContext());
    }

    private void checkState() {
        if (this.requestContext == null) {
            throw new IllegalStateException(LocalizationMessages.SECURITY_CONTEXT_WAS_NOT_SET());
        }
    }
}

