/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.saml1.profile.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.security.SecureRandomIdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Status;
import org.opensaml.saml.saml1.core.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddResponseShell
extends AbstractProfileAction {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(AddResponseShell.class);
    @Nonnull
    private Function<ProfileRequestContext, IdentifierGenerationStrategy> idGeneratorLookupStrategy = new Function<ProfileRequestContext, IdentifierGenerationStrategy>(){

        @Override
        public IdentifierGenerationStrategy apply(ProfileRequestContext input) {
            return new SecureRandomIdentifierGenerationStrategy();
        }
    };
    private boolean overwriteExisting;
    @Nullable
    private IdentifierGenerationStrategy idGenerator;

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setIdentifierGeneratorLookupStrategy(@Nonnull Function<ProfileRequestContext, IdentifierGenerationStrategy> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.idGeneratorLookupStrategy = Constraint.isNotNull(strategy, "IdentifierGenerationStrategy lookup strategy cannot be null");
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        MessageContext outboundMessageCtx = profileRequestContext.getOutboundMessageContext();
        if (outboundMessageCtx == null) {
            this.log.debug("{} No outbound message context", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (!this.overwriteExisting && outboundMessageCtx.getMessage() != null) {
            this.log.debug("{} Outbound message context already contains a Response", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        this.idGenerator = this.idGeneratorLookupStrategy.apply(profileRequestContext);
        if (this.idGenerator == null) {
            this.log.debug("{} No identifier generation strategy", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
            return false;
        }
        outboundMessageCtx.setMessage(null);
        return super.doPreExecute(profileRequestContext);
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        XMLObjectBuilderFactory bf = XMLObjectProviderRegistrySupport.getBuilderFactory();
        SAMLObjectBuilder statusCodeBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(StatusCode.DEFAULT_ELEMENT_NAME);
        SAMLObjectBuilder statusBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(Status.DEFAULT_ELEMENT_NAME);
        SAMLObjectBuilder responseBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(Response.DEFAULT_ELEMENT_NAME);
        StatusCode statusCode = (StatusCode)statusCodeBuilder.buildObject();
        statusCode.setValue(StatusCode.SUCCESS);
        Status status = (Status)statusBuilder.buildObject();
        status.setStatusCode(statusCode);
        Response response = (Response)responseBuilder.buildObject();
        response.setID(this.idGenerator.generateIdentifier());
        response.setIssueInstant(new DateTime(ISOChronology.getInstanceUTC()));
        response.setStatus(status);
        response.setVersion(SAMLVersion.VERSION_11);
        profileRequestContext.getOutboundMessageContext().setMessage((Object)response);
    }
}

