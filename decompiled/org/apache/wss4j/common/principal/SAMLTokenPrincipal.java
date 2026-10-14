/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.security.Principal;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;

public interface SAMLTokenPrincipal
extends Principal {
    public SamlAssertionWrapper getToken();

    @Override
    public String getName();

    public String getId();
}

