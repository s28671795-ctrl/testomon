/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.io.Serializable;
import org.apache.wss4j.common.principal.SAMLTokenPrincipal;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;

public class SAMLTokenPrincipalImpl
implements Serializable,
SAMLTokenPrincipal {
    private static final long serialVersionUID = 1L;
    private String name;
    private SamlAssertionWrapper samlAssertion;

    public SAMLTokenPrincipalImpl(SamlAssertionWrapper samlAssertion) {
        this.samlAssertion = samlAssertion;
        this.name = samlAssertion.getSubjectName();
    }

    @Override
    public SamlAssertionWrapper getToken() {
        return this.samlAssertion;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        if (this.samlAssertion != null) {
            return this.samlAssertion.getId();
        }
        return null;
    }
}

