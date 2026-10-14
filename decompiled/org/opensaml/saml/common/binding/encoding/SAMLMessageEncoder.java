/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.encoder.MessageEncoder
 */
package org.opensaml.saml.common.binding.encoding;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.encoder.MessageEncoder;
import org.opensaml.saml.common.SAMLObject;

public interface SAMLMessageEncoder
extends MessageEncoder<SAMLObject> {
    @Nonnull
    @NotEmpty
    public String getBindingURI();
}

