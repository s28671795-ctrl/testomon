/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.AnnotatedMethod;

public class RolesAllowedDynamicFeature
implements DynamicFeature {
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext configuration) {
        AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
        if (am.isAnnotationPresent(DenyAll.class)) {
            configuration.register(new RolesAllowedRequestFilter());
            return;
        }
        RolesAllowed ra = am.getAnnotation(RolesAllowed.class);
        if (ra != null) {
            configuration.register(new RolesAllowedRequestFilter(ra.value()));
            return;
        }
        if (am.isAnnotationPresent(PermitAll.class)) {
            return;
        }
        ra = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
        if (ra != null) {
            configuration.register(new RolesAllowedRequestFilter(ra.value()));
        }
    }

    @Priority(value=2000)
    private static class RolesAllowedRequestFilter
    implements ContainerRequestFilter {
        private final boolean denyAll;
        private final String[] rolesAllowed;

        RolesAllowedRequestFilter() {
            this.denyAll = true;
            this.rolesAllowed = null;
        }

        RolesAllowedRequestFilter(String[] rolesAllowed) {
            this.denyAll = false;
            this.rolesAllowed = rolesAllowed != null ? rolesAllowed : new String[]{};
        }

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            if (!this.denyAll) {
                if (this.rolesAllowed.length > 0 && !RolesAllowedRequestFilter.isAuthenticated(requestContext)) {
                    throw new ForbiddenException(LocalizationMessages.USER_NOT_AUTHORIZED());
                }
                for (String role : this.rolesAllowed) {
                    if (!requestContext.getSecurityContext().isUserInRole(role)) continue;
                    return;
                }
            }
            throw new ForbiddenException(LocalizationMessages.USER_NOT_AUTHORIZED());
        }

        private static boolean isAuthenticated(ContainerRequestContext requestContext) {
            return requestContext.getSecurityContext().getUserPrincipal() != null;
        }
    }
}

