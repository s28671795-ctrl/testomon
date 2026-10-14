/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;

public class SAMLProtocolContextProtocolFunction
implements ContextDataLookupFunction<SAMLProtocolContext, String> {
    @Nullable
    public String apply(@Nullable SAMLProtocolContext input) {
        if (input != null) {
            return input.getProtocol();
        }
        return null;
    }
}

