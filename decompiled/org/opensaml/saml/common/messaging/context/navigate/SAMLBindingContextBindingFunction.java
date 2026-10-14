/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.binding.BindingDescriptor;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;

public class SAMLBindingContextBindingFunction
implements ContextDataLookupFunction<SAMLBindingContext, String> {
    private boolean useShortName;

    public void setUseShortName(boolean flag) {
        this.useShortName = flag;
    }

    @Nullable
    public String apply(@Nullable SAMLBindingContext input) {
        if (input != null) {
            BindingDescriptor descriptor;
            if (this.useShortName && (descriptor = input.getBindingDescriptor()) != null && descriptor.getShortName() != null) {
                return descriptor.getShortName();
            }
            return input.getBindingUri();
        }
        return null;
    }
}

