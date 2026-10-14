/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.AbstractNameIdentifierGenerator;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.profile.SAML2NameIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSAML2NameIDGenerator
extends AbstractNameIdentifierGenerator<NameID>
implements SAML2NameIDGenerator {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractSAML2NameIDGenerator.class);
    @Nonnull
    private final SAMLObjectBuilder<NameID> nameBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameID.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private Function<ProfileRequestContext, AuthnRequest> requestLookupStrategy = Functions.compose(new MessageLookup(AuthnRequest.class), new InboundMessageContextLookup());

    protected AbstractSAML2NameIDGenerator() {
    }

    public void setRequestLookupStrategy(@Nonnull Function<ProfileRequestContext, AuthnRequest> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requestLookupStrategy = Constraint.isNotNull(strategy, "AuthnRequest lookup strategy cannot be null");
    }

    @Override
    @Nullable
    protected NameID doGenerate(@Nonnull ProfileRequestContext profileRequestContext) throws SAMLException {
        String identifier = this.getIdentifier(profileRequestContext);
        if (identifier == null) {
            this.log.debug("No identifier to use");
            return null;
        }
        this.log.debug("Generating NameID {} with Format {}", (Object)identifier, (Object)this.getFormat());
        NameID nameIdentifier = this.nameBuilder.buildObject();
        nameIdentifier.setValue(identifier);
        nameIdentifier.setFormat(this.getFormat());
        nameIdentifier.setNameQualifier(this.getEffectiveIdPNameQualifier(profileRequestContext));
        nameIdentifier.setSPNameQualifier(this.getEffectiveSPNameQualifier(profileRequestContext));
        nameIdentifier.setSPProvidedID(this.getSPProvidedID());
        return nameIdentifier;
    }

    @Override
    @Nullable
    protected String getEffectiveSPNameQualifier(@Nonnull ProfileRequestContext profileRequestContext) {
        String qual;
        AuthnRequest request = this.requestLookupStrategy.apply(profileRequestContext);
        if (request != null && request.getNameIDPolicy() != null && !Strings.isNullOrEmpty(qual = request.getNameIDPolicy().getSPNameQualifier())) {
            return qual;
        }
        return super.getEffectiveSPNameQualifier(profileRequestContext);
    }
}

