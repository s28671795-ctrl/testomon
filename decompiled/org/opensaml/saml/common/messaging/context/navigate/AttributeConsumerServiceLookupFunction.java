/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.AttributeConsumingServiceContext;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public class AttributeConsumerServiceLookupFunction
implements ContextDataLookupFunction<AttributeConsumingServiceContext, AttributeConsumingService> {
    @Nullable
    public AttributeConsumingService apply(@Nullable AttributeConsumingServiceContext input) {
        if (null == input) {
            return null;
        }
        return input.getAttributeConsumingService();
    }
}

