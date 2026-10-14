/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.common.messaging.logic;

import com.google.common.base.Predicate;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;

public class SignatureCapableBindingPredicate
implements Predicate<MessageContext<SAMLObject>> {
    @Override
    public boolean apply(MessageContext<SAMLObject> input) {
        return input != null && SAMLBindingSupport.isSigningCapableBinding(input);
    }
}

