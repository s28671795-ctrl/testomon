/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.profile.impl;

import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.impl.AbstractDecryptAction;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptAssertions
extends AbstractDecryptAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(DecryptAssertions.class);

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        block3: {
            SAMLObject message = this.getSAMLObject();
            try {
                if (!(message instanceof Response)) {
                    this.log.debug("{} Message was of unrecognized type {}, nothing to do", (Object)this.getLogPrefix(), (Object)message.getClass().getName());
                    return;
                }
                this.processResponse(profileRequestContext, (Response)message);
            }
            catch (DecryptionException e) {
                this.log.warn("{} Failure performing decryption", (Object)this.getLogPrefix(), (Object)e);
                if (!this.isErrorFatal()) break block3;
                ActionSupport.buildEvent(profileRequestContext, "DecryptAssertionFailed");
            }
        }
    }

    @Nullable
    private Assertion processEncryptedAssertion(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull EncryptedAssertion encAssert) throws DecryptionException {
        if (!this.getDecryptionPredicate().apply(new Pair<ProfileRequestContext, EncryptedAssertion>(profileRequestContext, encAssert))) {
            return null;
        }
        if (this.getDecrypter() == null) {
            throw new DecryptionException("No decryption parameters, unable to decrypt EncryptedAssertion");
        }
        return this.getDecrypter().decrypt(encAssert);
    }

    private void processResponse(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Response response) throws DecryptionException {
        ArrayList<Assertion> decrypteds = new ArrayList<Assertion>();
        Iterator<EncryptedAssertion> i = response.getEncryptedAssertions().iterator();
        while (i.hasNext()) {
            this.log.debug("{} Decrypting EncryptedAssertion in Response", (Object)this.getLogPrefix());
            try {
                Assertion decrypted = this.processEncryptedAssertion(profileRequestContext, i.next());
                if (decrypted == null) continue;
                decrypteds.add(decrypted);
                i.remove();
            }
            catch (DecryptionException e) {
                if (this.isErrorFatal()) {
                    throw e;
                }
                this.log.warn("{} Trapped failure decrypting EncryptedAttribute in AttributeStatement", (Object)this.getLogPrefix(), (Object)e);
            }
        }
        response.getAssertions().addAll(decrypteds);
    }
}

