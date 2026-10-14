/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.criterion;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class ProtocolCriterion
implements Criterion {
    @Nonnull
    @NotEmpty
    private final String protocol;

    public ProtocolCriterion(@Nonnull @NotEmpty String protocolUri) {
        this.protocol = Constraint.isNotNull(StringSupport.trimOrNull(protocolUri), "SAML protocol URI cannot be null or empty");
    }

    @Nonnull
    @NotEmpty
    public String getProtocol() {
        return this.protocol;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProtocolCriterion [protocol=");
        builder.append(this.protocol);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.protocol.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof ProtocolCriterion) {
            return this.protocol.equals(((ProtocolCriterion)obj).protocol);
        }
        return false;
    }
}

