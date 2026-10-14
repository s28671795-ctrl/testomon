/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNameIDPolicyPredicate
extends AbstractInitializableComponent
implements Predicate<ProfileRequestContext> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractNameIDPolicyPredicate.class);
    @Nullable
    private Function<ProfileRequestContext, String> requesterIdLookupStrategy;
    @Nullable
    private Function<ProfileRequestContext, String> responderIdLookupStrategy;
    @NonnullAfterInit
    private Function<ProfileRequestContext, SAMLObject> objectLookupStrategy;
    @Nonnull
    @NonnullElements
    private Set<String> formats = new HashSet<String>(Arrays.asList("urn:oasis:names:tc:SAML:2.0:nameid-format:transient", "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent"));

    public void setRequesterIdLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requesterIdLookupStrategy = strategy;
    }

    public void setResponderIdLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responderIdLookupStrategy = strategy;
    }

    public void setObjectLookupStrategy(@Nullable Function<ProfileRequestContext, SAMLObject> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.objectLookupStrategy = Constraint.isNotNull(strategy, "Object lookup strategy cannot be null");
    }

    public void setFormats(@Nonnull @NonnullElements Collection<String> newFormats) {
        Constraint.isNotNull(this.formats, "Format collection cannot be null");
        for (String s2 : newFormats) {
            String trimmed = StringSupport.trimOrNull(s2);
            if (trimmed == null) continue;
            this.formats.add(trimmed);
        }
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.objectLookupStrategy == null) {
            throw new ComponentInitializationException("Object lookup strategy cannot be null");
        }
    }

    @Override
    public boolean apply(@Nullable ProfileRequestContext input) {
        SAMLObject target = this.objectLookupStrategy.apply(input);
        if (target == null) {
            this.log.debug("No object to operate on, returning true");
            return true;
        }
        if (target instanceof NameIdentifier) {
            return this.doApply(input, (NameIdentifier)target);
        }
        if (target instanceof NameID) {
            return this.doApply(input, (NameID)target);
        }
        if (target instanceof NameIDPolicy) {
            return this.doApply(input, (NameIDPolicy)target);
        }
        this.log.error("Lookup function returned an object of an unsupported type: {}", (Object)target.getElementQName());
        return false;
    }

    private boolean doApply(@Nullable ProfileRequestContext input, @Nonnull NameIdentifier target) {
        String requesterId = this.requesterIdLookupStrategy != null ? this.requesterIdLookupStrategy.apply(input) : null;
        String responderId = this.responderIdLookupStrategy != null ? this.responderIdLookupStrategy.apply(input) : null;
        String format = target.getFormat();
        if (this.formats.contains(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")) {
            this.log.debug("Applying policy to NameIdentifier with Format {}", (Object)(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
            return this.doApply(requesterId, responderId, format, target.getNameQualifier(), null);
        }
        this.log.debug("Policy checking disabled for NameIdentifier Format {}", (Object)(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
        return true;
    }

    private boolean doApply(@Nullable ProfileRequestContext input, @Nonnull NameID target) {
        String requesterId = this.requesterIdLookupStrategy != null ? this.requesterIdLookupStrategy.apply(input) : null;
        String responderId = this.responderIdLookupStrategy != null ? this.responderIdLookupStrategy.apply(input) : null;
        String format = target.getFormat();
        if (this.formats.contains(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")) {
            this.log.debug("Applying policy to NameID with Format {}", (Object)(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
            return this.doApply(requesterId, responderId, format, target.getNameQualifier(), target.getSPNameQualifier());
        }
        this.log.debug("Policy checking disabled for NameID Format {}", (Object)(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
        return true;
    }

    private boolean doApply(@Nullable ProfileRequestContext input, @Nonnull NameIDPolicy target) {
        String requesterId = this.requesterIdLookupStrategy != null ? this.requesterIdLookupStrategy.apply(input) : null;
        String responderId = this.responderIdLookupStrategy != null ? this.responderIdLookupStrategy.apply(input) : null;
        String format = target.getFormat();
        if (this.formats.contains(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")) {
            this.log.debug("Applying policy to NameIDPolicy with Format {}", (Object)(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
            return this.doApply(requesterId, responderId, format, null, target.getSPNameQualifier());
        }
        this.log.debug("Policy checking disabled for NameIDPolicy with Format {}", (Object)(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified"));
        return true;
    }

    protected abstract boolean doApply(@Nullable String var1, @Nullable String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5);
}

