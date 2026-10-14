/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.common.messaging.context;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.messaging.context.AbstractAuthenticatableSAMLEntityContext;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLPeerEntityContext
extends AbstractAuthenticatableSAMLEntityContext {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(SAMLPeerEntityContext.class);
    private boolean useSAML1QueryResourceAsEntityId = true;

    @Override
    @Nullable
    @NotEmpty
    public String getEntityId() {
        if (super.getEntityId() == null) {
            this.setEntityId(this.resolveEntityId());
        }
        return super.getEntityId();
    }

    public boolean getUseSAML1QueryResourceAsEntityId() {
        return this.useSAML1QueryResourceAsEntityId;
    }

    public void setUseSAML1QueryResourceAsEntityId(boolean useResource) {
        this.useSAML1QueryResourceAsEntityId = useResource;
    }

    @Nullable
    protected String resolveEntityId() {
        SAMLObject samlMessage = this.resolveSAMLMessage();
        if (samlMessage instanceof RequestAbstractType) {
            RequestAbstractType request = (RequestAbstractType)samlMessage;
            return this.processSaml2Request(request);
        }
        if (samlMessage instanceof StatusResponseType) {
            StatusResponseType response = (StatusResponseType)samlMessage;
            return this.processSaml2Response(response);
        }
        if (samlMessage instanceof Response) {
            Response response = (Response)samlMessage;
            return this.processSaml1Response(response);
        }
        if (samlMessage instanceof Request) {
            Request request = (Request)samlMessage;
            return this.processSaml1Request(request);
        }
        return null;
    }

    @Nullable
    protected String processSaml2Request(@Nonnull RequestAbstractType request) {
        if (request.getIssuer() != null) {
            return this.processSaml2Issuer(request.getIssuer());
        }
        return null;
    }

    @Nullable
    protected String processSaml2Response(@Nonnull StatusResponseType response) {
        if (response.getIssuer() != null) {
            return this.processSaml2Issuer(response.getIssuer());
        }
        return null;
    }

    @Nullable
    protected String processSaml2Issuer(@Nonnull Issuer issuer) {
        if (issuer.getFormat() == null || issuer.getFormat().equals("urn:oasis:names:tc:SAML:2.0:nameid-format:entity")) {
            return issuer.getValue();
        }
        this.log.warn("Couldn't dynamically resolve SAML 2 peer entity ID due to unsupported NameID format: {}", (Object)issuer.getFormat());
        return null;
    }

    @Nullable
    protected String processSaml1Response(@Nonnull Response response) {
        String issuer = null;
        List<Assertion> assertions = response.getAssertions();
        if (assertions != null && assertions.size() > 0) {
            this.log.info("Attempting to extract issuer from enclosed SAML 1.x Assertion(s)");
            for (Assertion assertion : assertions) {
                if (assertion == null || assertion.getIssuer() == null) continue;
                if (issuer != null && !issuer.equals(assertion.getIssuer())) {
                    this.log.warn("SAML 1.x assertions, within response '{}' contain different issuer IDs, can not dynamically resolve SAML peer entity ID", (Object)response.getID());
                    return null;
                }
                issuer = assertion.getIssuer();
            }
        }
        if (issuer == null) {
            this.log.warn("Issuer could not be extracted from standard SAML 1.x response message");
        }
        return issuer;
    }

    @Nullable
    protected String processSaml1Request(@Nonnull Request request) {
        String entityId = null;
        if (request.getAttributeQuery() != null && (entityId = this.processSaml1AttributeQuery(request.getAttributeQuery())) != null) {
            return entityId;
        }
        if (request.getAuthorizationDecisionQuery() != null && (entityId = this.processSaml1AuthorizationDecisionQuery(request.getAuthorizationDecisionQuery())) != null) {
            return entityId;
        }
        return null;
    }

    @Nullable
    protected String processSaml1AttributeQuery(@Nonnull AttributeQuery query) {
        if (this.getUseSAML1QueryResourceAsEntityId()) {
            this.log.debug("Attempting to extract entity ID from SAML 1 AttributeQuery Resource attribute");
            String resource = StringSupport.trimOrNull(query.getResource());
            if (resource != null) {
                this.log.debug("Extracted entity ID from SAML 1.x AttributeQuery: {}", (Object)resource);
                return resource;
            }
        }
        return null;
    }

    @Nullable
    protected String processSaml1AuthorizationDecisionQuery(@Nonnull AuthorizationDecisionQuery query) {
        if (this.getUseSAML1QueryResourceAsEntityId()) {
            this.log.debug("Attempting to extract entity ID from SAML 1 AuthorizationDecisionQuery Resource attribute");
            String resource = StringSupport.trimOrNull(query.getResource());
            if (resource != null) {
                this.log.debug("Extracted entity ID from SAML 1.x AuthorizationDecisionQuery: {}", (Object)resource);
                return resource;
            }
        }
        return null;
    }

    @Nullable
    protected SAMLObject resolveSAMLMessage() {
        MessageContext parent;
        if (this.getParent() instanceof MessageContext && (parent = (MessageContext)this.getParent()).getMessage() instanceof SAMLObject) {
            return (SAMLObject)parent.getMessage();
        }
        return null;
    }
}

