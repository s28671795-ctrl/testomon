/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;

public class SAMLMessageInfoContextIDFunction
implements ContextDataLookupFunction<SAMLMessageInfoContext, String> {
    @Nullable
    public String apply(@Nullable SAMLMessageInfoContext input) {
        if (input != null) {
            return input.getMessageId();
        }
        return null;
    }
}

