/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.saml.saml2.profile.impl.AbstractEncryptAction;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptAttributes
extends AbstractEncryptAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(EncryptAttributes.class);
    @Nonnull
    private Function<ProfileRequestContext, StatusResponseType> responseLookupStrategy = Functions.compose(new MessageLookup(StatusResponseType.class), new OutboundMessageContextLookup());
    @Nullable
    private Response response;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, StatusResponseType> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    @Override
    @Nullable
    protected EncryptionParameters getApplicableParameters(@Nullable EncryptionContext ctx) {
        if (ctx != null) {
            return ctx.getAttributeEncryptionParameters();
        }
        return null;
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        StatusResponseType message = this.responseLookupStrategy.apply(profileRequestContext);
        if (message != null) {
            if (message instanceof Response) {
                this.response = (Response)message;
            } else if (message instanceof ArtifactResponse && ((ArtifactResponse)message).getMessage() instanceof Response) {
                this.response = (Response)((ArtifactResponse)message).getMessage();
            }
        }
        if (this.response == null || this.response.getAssertions().isEmpty()) {
            this.log.debug("{} Response was not present or contained no assertions, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        for (Assertion assertion : this.response.getAssertions()) {
            for (AttributeStatement statement : assertion.getAttributeStatements()) {
                ArrayList<EncryptedAttribute> accumulator = new ArrayList<EncryptedAttribute>(statement.getAttributes().size());
                for (Attribute attribute : statement.getAttributes()) {
                    try {
                        if (this.log.isDebugEnabled()) {
                            try {
                                Element dom = XMLObjectSupport.marshall(attribute);
                                this.log.debug("{} Attribute before encryption:\n{}", (Object)this.getLogPrefix(), (Object)SerializeSupport.prettyPrintXML(dom));
                            }
                            catch (MarshallingException e) {
                                this.log.error("{} Unable to marshall Attribute for logging purposes", (Object)this.getLogPrefix(), (Object)e);
                            }
                        }
                        accumulator.add(this.getEncrypter().encrypt(attribute));
                    }
                    catch (EncryptionException e) {
                        this.log.warn("{} Error encrypting attribute", (Object)this.getLogPrefix(), (Object)e);
                        ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
                        return;
                    }
                }
                statement.getEncryptedAttributes().addAll(accumulator);
                statement.getAttributes().clear();
            }
        }
    }
}

