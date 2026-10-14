/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.common;

import com.google.common.base.Predicate;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.common.SAML2Support;

public class IsTimeboundSAMLObjectValidPredicate
implements Predicate<XMLObject> {
    @Override
    public boolean apply(XMLObject input) {
        if (input == null) {
            return false;
        }
        return SAML2Support.isValid(input);
    }
}

