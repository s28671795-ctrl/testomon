/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.criterion;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.saml.saml2.metadata.Endpoint;

public final class EndpointCriterion<EndpointType extends Endpoint>
implements Criterion {
    private final boolean trusted;
    @Nonnull
    private final EndpointType endpoint;

    public EndpointCriterion(@Nonnull EndpointType ep) {
        this(ep, false);
    }

    public EndpointCriterion(@Nonnull EndpointType ep, boolean trust) {
        this.endpoint = (Endpoint)Constraint.isNotNull(ep, "Endpoint cannot be null");
        this.trusted = trust;
    }

    @Nonnull
    public EndpointType getEndpoint() {
        return this.endpoint;
    }

    public boolean isTrusted() {
        return this.trusted;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EndpointCriterion [type=").append(this.endpoint.getElementQName());
        if (this.endpoint.getBinding() != null) {
            builder.append(", Binding=").append(this.endpoint.getBinding());
        }
        if (this.endpoint.getLocation() != null) {
            builder.append(", Location=").append(this.endpoint.getLocation());
        }
        if (this.endpoint.getResponseLocation() != null) {
            builder.append(", ResponseLocation=").append(this.endpoint.getResponseLocation());
        }
        builder.append(", trusted=").append(this.trusted).append(']');
        return builder.toString();
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EndpointCriterion) {
            EndpointType endpoint2 = ((EndpointCriterion)obj).getEndpoint();
            if (!Objects.equals(this.endpoint.getElementQName(), endpoint2.getElementQName())) {
                return false;
            }
            if (!Objects.equals(this.endpoint.getBinding(), endpoint2.getBinding())) {
                return false;
            }
            if (!Objects.equals(this.endpoint.getLocation(), endpoint2.getLocation())) {
                return false;
            }
            return Objects.equals(this.endpoint.getResponseLocation(), endpoint2.getResponseLocation());
        }
        return false;
    }
}

