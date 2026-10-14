/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityToken;

import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.stax.securityToken.SubjectAndPrincipalSecurityToken;

public interface SamlSecurityToken
extends SubjectAndPrincipalSecurityToken {
    public SamlAssertionWrapper getSamlAssertionWrapper();
}

