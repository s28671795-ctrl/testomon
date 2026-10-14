/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.profile.FormatSpecificNameIdentifierGenerator;

public abstract class AbstractNameIdentifierGenerator<NameIdType extends SAMLObject>
extends AbstractIdentifiableInitializableComponent
implements FormatSpecificNameIdentifierGenerator<NameIdType>,
Predicate<ProfileRequestContext> {
    @Nonnull
    private Predicate<ProfileRequestContext> activationCondition = Predicates.alwaysTrue();
    @Nullable
    private Function<ProfileRequestContext, String> defaultIdPNameQualifierLookupStrategy;
    @Nullable
    private Function<ProfileRequestContext, String> defaultSPNameQualifierLookupStrategy;
    private boolean omitQualifiers;
    @NonnullAfterInit
    @NotEmpty
    private String format;
    @Nullable
    private String idpNameQualifier;
    @Nullable
    private String spNameQualifier;
    @Nullable
    private String spProvidedId;

    protected AbstractNameIdentifierGenerator() {
    }

    public void setActivationCondition(@Nonnull Predicate<ProfileRequestContext> condition) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.activationCondition = Constraint.isNotNull(condition, "Predicate cannot be null");
    }

    @Nullable
    public Function<ProfileRequestContext, String> getDefaultIdPNameQualifierLookupStrategy() {
        return this.defaultIdPNameQualifierLookupStrategy;
    }

    public void setDefaultIdPNameQualifierLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.defaultIdPNameQualifierLookupStrategy = strategy;
    }

    @Nullable
    public Function<ProfileRequestContext, String> getDefaultSPNameQualifierLookupStrategy() {
        return this.defaultSPNameQualifierLookupStrategy;
    }

    public void setDefaultSPNameQualifierLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.defaultSPNameQualifierLookupStrategy = strategy;
    }

    public boolean isOmitQualifiers() {
        return this.omitQualifiers;
    }

    public void setOmitQualifiers(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.omitQualifiers = flag;
    }

    @Override
    @NonnullAfterInit
    @NotEmpty
    public String getFormat() {
        return this.format;
    }

    public void setFormat(@Nonnull @NotEmpty String f) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.format = Constraint.isNotNull(StringSupport.trimOrNull(f), "Format cannot be null or empty");
    }

    @Nullable
    public String getIdPNameQualifier() {
        return this.idpNameQualifier;
    }

    public void setIdPNameQualifier(@Nullable String qualifier) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.idpNameQualifier = StringSupport.trimOrNull(qualifier);
    }

    @Nullable
    public String getSPNameQualifier() {
        return this.spNameQualifier;
    }

    public void setSPNameQualifier(@Nullable String qualifier) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.spNameQualifier = StringSupport.trimOrNull(qualifier);
    }

    @Nullable
    public String getSPProvidedID() {
        return this.spProvidedId;
    }

    public void setSPProvidedId(@Nullable String id) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.spProvidedId = id;
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.format == null) {
            throw new ComponentInitializationException("Format value cannot be null or empty");
        }
    }

    @Override
    public boolean apply(@Nullable ProfileRequestContext input) {
        return this.activationCondition.apply(input);
    }

    @Override
    @Nullable
    public NameIdType generate(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull @NotEmpty String theFormat) throws SAMLException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        if (!Objects.equals(this.format, theFormat)) {
            throw new SAMLException("The format to generate does not match the value configured");
        }
        if (!this.apply(profileRequestContext)) {
            return null;
        }
        return this.doGenerate(profileRequestContext);
    }

    @Nullable
    protected abstract NameIdType doGenerate(@Nonnull ProfileRequestContext var1) throws SAMLException;

    @Nullable
    protected String getIdentifier(@Nonnull ProfileRequestContext profileRequestContext) throws SAMLException {
        return null;
    }

    @Nullable
    protected String getEffectiveIdPNameQualifier(@Nonnull ProfileRequestContext profileRequestContext) {
        if (this.idpNameQualifier != null) {
            if (this.omitQualifiers) {
                if (this.defaultIdPNameQualifierLookupStrategy == null || !Objects.equals(this.idpNameQualifier, this.defaultIdPNameQualifierLookupStrategy.apply(profileRequestContext))) {
                    return this.idpNameQualifier;
                }
                return null;
            }
            return this.idpNameQualifier;
        }
        if (!this.omitQualifiers && this.defaultIdPNameQualifierLookupStrategy != null) {
            return this.defaultIdPNameQualifierLookupStrategy.apply(profileRequestContext);
        }
        return null;
    }

    @Nullable
    protected String getEffectiveSPNameQualifier(@Nonnull ProfileRequestContext profileRequestContext) {
        if (this.spNameQualifier != null) {
            if (this.omitQualifiers) {
                if (this.defaultSPNameQualifierLookupStrategy == null || !Objects.equals(this.spNameQualifier, this.defaultSPNameQualifierLookupStrategy.apply(profileRequestContext))) {
                    return this.spNameQualifier;
                }
                return null;
            }
            return this.spNameQualifier;
        }
        if (!this.omitQualifiers && this.defaultSPNameQualifierLookupStrategy != null) {
            return this.defaultSPNameQualifierLookupStrategy.apply(profileRequestContext);
        }
        return null;
    }
}

