/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.saml.common.profile.logic.DefaultNameIDPolicyPredicate;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.saml2.metadata.AffiliateMember;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AffiliationNameIDPolicyPredicate
extends DefaultNameIDPolicyPredicate {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AffiliationNameIDPolicyPredicate.class);
    @NonnullAfterInit
    private MetadataResolver metadataResolver;

    public void setMetadataResolver(@Nonnull MetadataResolver resolver2) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.metadataResolver = Constraint.isNotNull(resolver2, "MetadataResolver cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.metadataResolver == null) {
            throw new ComponentInitializationException("MetadataResolver cannot be null");
        }
    }

    @Override
    protected boolean doApply(@Nullable String requesterId, @Nullable String responderId, @Nullable String format, @Nullable String nameQualifier, @Nullable String spNameQualifier) {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        if (super.doApply(requesterId, responderId, format, nameQualifier, spNameQualifier)) {
            return true;
        }
        if (spNameQualifier == null) {
            return true;
        }
        try {
            EntityDescriptor affiliation = (EntityDescriptor)this.metadataResolver.resolveSingle(new CriteriaSet(new EntityIdCriterion(spNameQualifier)));
            if (affiliation != null) {
                AffiliationDescriptor descriptor = affiliation.getAffiliationDescriptor();
                if (descriptor != null) {
                    for (AffiliateMember member : descriptor.getMembers()) {
                        if (!Objects.equals(member.getID(), requesterId)) continue;
                        this.log.debug("Entity {} is authorized as a member of Affiliation {}", (Object)requesterId, (Object)spNameQualifier);
                        return true;
                    }
                    this.log.warn("Entity {} was not a member of Affiliation {}", (Object)requesterId, (Object)spNameQualifier);
                } else {
                    this.log.warn("Affiliation entity {} found, but did not contain an AffiliationDescriptor", (Object)spNameQualifier);
                }
            } else {
                this.log.warn("No metadata found for affiliation {}", (Object)spNameQualifier);
            }
        }
        catch (ResolverException e) {
            this.log.error("Error resolving metadata for affiliation {}", (Object)spNameQualifier, (Object)e);
        }
        return false;
    }
}

