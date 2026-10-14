/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.encoder.MessageEncodingException
 *  org.opensaml.messaging.encoder.servlet.BaseHttpServletResponseXMLMessageEncoder
 */
package org.opensaml.saml.saml1.binding.encoding.impl;

import java.net.URI;
import javax.annotation.Nonnull;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.messaging.encoder.servlet.BaseHttpServletResponseXMLMessageEncoder;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.encoding.SAMLMessageEncoder;

public abstract class BaseSAML1MessageEncoder
extends BaseHttpServletResponseXMLMessageEncoder<SAMLObject>
implements SAMLMessageEncoder {
    @Nonnull
    protected URI getEndpointURL(@Nonnull MessageContext<SAMLObject> messageContext) throws MessageEncodingException {
        try {
            return SAMLBindingSupport.getEndpointURL(messageContext);
        }
        catch (BindingException e) {
            throw new MessageEncodingException("Could not obtain message endpoint URL", (Exception)e);
        }
    }
}

