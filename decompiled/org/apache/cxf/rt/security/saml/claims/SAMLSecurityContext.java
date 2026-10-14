/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.claims;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.claims.ClaimsSecurityContext;
import org.w3c.dom.Element;

public class SAMLSecurityContext
implements ClaimsSecurityContext {
    private final Principal principal;
    private Set<Principal> roles;
    private Element assertionElement;
    private String issuer;
    private ClaimCollection claims;

    public SAMLSecurityContext(Principal principal) {
        this(principal, null);
    }

    public SAMLSecurityContext(Principal principal, Set<Principal> roles) {
        this(principal, roles, null);
    }

    public SAMLSecurityContext(Principal principal, Set<Principal> roles, ClaimCollection claims) {
        this.principal = principal;
        this.roles = roles;
        this.claims = claims;
    }

    @Override
    public ClaimCollection getClaims() {
        return this.claims;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this.roles == null) {
            return false;
        }
        for (Principal principalRole : this.roles) {
            if (principalRole == this.principal || !principalRole.getName().equals(role)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Subject getSubject() {
        return null;
    }

    public void setUserRoles(Set<Principal> userRoles) {
        this.roles = userRoles;
    }

    @Override
    public Set<Principal> getUserRoles() {
        if (this.roles == null) {
            return Collections.emptySet();
        }
        HashSet<Principal> retRoles = new HashSet<Principal>(this.roles);
        if (this.principal != null && retRoles.contains(this.principal)) {
            retRoles.remove(this.principal);
        }
        return retRoles;
    }

    public void setAssertionElement(Element assertionElement) {
        this.assertionElement = assertionElement;
    }

    public Element getAssertionElement() {
        return this.assertionElement;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuer() {
        return this.issuer;
    }
}

