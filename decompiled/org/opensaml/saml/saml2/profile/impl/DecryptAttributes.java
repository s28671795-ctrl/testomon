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
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.impl.AbstractDecryptAction;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptAttributes
extends AbstractDecryptAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(DecryptAttributes.class);

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        block5: {
            SAMLObject message = this.getSAMLObject();
            try {
                if (message instanceof Response) {
                    for (Assertion a : ((Response)message).getAssertions()) {
                        this.processAssertion(profileRequestContext, a);
                    }
                    break block5;
                }
                if (message instanceof Assertion) {
                    this.processAssertion(profileRequestContext, (Assertion)message);
                    break block5;
                }
                this.log.debug("{} Message was of unrecognized type {}, nothing to do", (Object)this.getLogPrefix(), (Object)message.getClass().getName());
                return;
            }
            catch (DecryptionException e) {
                this.log.warn("{} Failure performing decryption", (Object)this.getLogPrefix(), (Object)e);
                if (!this.isErrorFatal()) break block5;
                ActionSupport.buildEvent(profileRequestContext, "DecryptAttributeFailed");
            }
        }
    }

    @Nullable
    private Attribute processEncryptedAttribute(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull EncryptedAttribute encAttr) throws DecryptionException {
        if (!this.getDecryptionPredicate().apply(new Pair<ProfileRequestContext, EncryptedAttribute>(profileRequestContext, encAttr))) {
            return null;
        }
        if (this.getDecrypter() == null) {
            throw new DecryptionException("No decryption parameters, unable to decrypt EncryptedAttribute");
        }
        return this.getDecrypter().decrypt(encAttr);
    }

    private void processAssertion(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Assertion assertion) throws DecryptionException {
        ArrayList<Attribute> decrypteds = new ArrayList<Attribute>();
        for (AttributeStatement s2 : assertion.getAttributeStatements()) {
            Iterator<EncryptedAttribute> i = s2.getEncryptedAttributes().iterator();
            while (i.hasNext()) {
                this.log.debug("{} Decrypting EncryptedAttribute in AttributeStatement", (Object)this.getLogPrefix());
                try {
                    Attribute decrypted = this.processEncryptedAttribute(profileRequestContext, i.next());
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
            s2.getAttributes().addAll(decrypteds);
        }
    }
}

