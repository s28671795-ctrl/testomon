/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingSignaturePrevalidator
implements SignaturePrevalidator {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(ChainingSignaturePrevalidator.class);
    @Nonnull
    @NonnullElements
    private List<SignaturePrevalidator> validators;

    public ChainingSignaturePrevalidator(@Nonnull @ParameterName(name="validatorChain") @NonnullElements List<SignaturePrevalidator> validatorChain) {
        Constraint.isNotNull(validatorChain, "SignaturePrevalidator list cannot be null");
        this.validators = new ArrayList<SignaturePrevalidator>(Collections2.filter(validatorChain, Predicates.notNull()));
    }

    @Override
    public void validate(@Nonnull Signature signature) throws SignatureException {
        for (SignaturePrevalidator validator : this.validators) {
            this.log.debug("Validating signature using prevalidator: {}", (Object)validator.getClass().getName());
            validator.validate(signature);
        }
    }
}

