/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.decoder.MessageDecoder
 */
package org.opensaml.saml.common.binding.decoding;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.decoder.MessageDecoder;
import org.opensaml.saml.common.SAMLObject;

public interface SAMLMessageDecoder
extends MessageDecoder<SAMLObject> {
    @Nonnull
    @NotEmpty
    public String getBindingURI();
}

