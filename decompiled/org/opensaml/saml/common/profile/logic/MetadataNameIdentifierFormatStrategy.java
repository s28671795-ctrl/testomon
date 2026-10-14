/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataNameIdentifierFormatStrategy
implements Function<ProfileRequestContext, List<String>> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(MetadataNameIdentifierFormatStrategy.class);
    @Nonnull
    private Function<ProfileRequestContext, SSODescriptor> ssoDescriptorLookupStrategy = new MetadataLookupStrategy();

    public void setSSODescriptorLookupStrategy(@Nonnull Function<ProfileRequestContext, SSODescriptor> strategy) {
        this.ssoDescriptorLookupStrategy = Constraint.isNotNull(strategy, "SSODescriptor lookup strategy cannot be null");
    }

    @Override
    @Nullable
    public List<String> apply(@Nullable ProfileRequestContext input) {
        SSODescriptor role = this.ssoDescriptorLookupStrategy.apply(input);
        if (role != null) {
            ArrayList<String> strings = new ArrayList<String>();
            for (NameIDFormat nif : role.getNameIDFormats()) {
                if (nif.getFormat() == null) continue;
                if ("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified".equals(nif.getFormat())) {
                    this.log.warn("Ignoring NameIDFormat metadata that includes the 'unspecified' format");
                    return Collections.emptyList();
                }
                strings.add(nif.getFormat());
            }
            this.log.debug("Metadata specifies the following formats: {}", (Object)strings);
            return strings;
        }
        return Collections.emptyList();
    }

    private class MetadataLookupStrategy
    implements Function<ProfileRequestContext, SSODescriptor> {
        private MetadataLookupStrategy() {
        }

        @Override
        @Nullable
        public SSODescriptor apply(@Nullable ProfileRequestContext input) {
            if (input != null && input.getInboundMessageContext() != null) {
                SAMLPeerEntityContext peerCtx = (SAMLPeerEntityContext)input.getInboundMessageContext().getSubcontext(SAMLPeerEntityContext.class);
                if (peerCtx != null) {
                    SAMLMetadataContext mdCtx = (SAMLMetadataContext)peerCtx.getSubcontext(SAMLMetadataContext.class);
                    if (mdCtx != null && mdCtx.getRoleDescriptor() != null && mdCtx.getRoleDescriptor() instanceof SSODescriptor) {
                        return (SSODescriptor)mdCtx.getRoleDescriptor();
                    }
                    MetadataNameIdentifierFormatStrategy.this.log.debug("No SAMLMetadataContext or SSODescriptor role available");
                } else {
                    MetadataNameIdentifierFormatStrategy.this.log.debug("No SAMLPeerEntityContext available");
                }
            } else {
                MetadataNameIdentifierFormatStrategy.this.log.debug("No inbound message context available");
            }
            return null;
        }
    }
}

