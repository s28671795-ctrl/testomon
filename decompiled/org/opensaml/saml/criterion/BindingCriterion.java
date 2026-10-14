/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.criterion;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class BindingCriterion
implements Criterion {
    @Nonnull
    @NonnullElements
    private final List<String> bindings;

    public BindingCriterion(@Nonnull @NonnullElements List<String> bindingURIs) {
        Constraint.isNotNull(bindingURIs, "Binding list cannot be null");
        this.bindings = new ArrayList<String>(bindingURIs.size());
        for (String binding : bindingURIs) {
            String trimmed = StringSupport.trimOrNull(binding);
            if (trimmed == null) continue;
            this.bindings.add(trimmed);
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getBindings() {
        return ImmutableList.copyOf(this.bindings);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BindingCriterion [bindings=");
        builder.append(this.bindings);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.bindings.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof BindingCriterion) {
            return this.bindings.equals(((BindingCriterion)obj).bindings);
        }
        return false;
    }
}

