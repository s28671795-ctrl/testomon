/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EntityDescriptorLookupFunction
implements ContextDataLookupFunction<SAMLMetadataContext, EntityDescriptor> {
    @Nullable
    public EntityDescriptor apply(@Nullable SAMLMetadataContext input) {
        if (null == input) {
            return null;
        }
        return input.getEntityDescriptor();
    }
}

