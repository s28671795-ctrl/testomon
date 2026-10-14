/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;

public class SAMLMessageInfoContextIssueInstantFunction
implements ContextDataLookupFunction<SAMLMessageInfoContext, DateTime> {
    @Nullable
    public DateTime apply(@Nullable SAMLMessageInfoContext input) {
        if (input != null) {
            return input.getMessageIssueInstant();
        }
        return null;
    }
}

