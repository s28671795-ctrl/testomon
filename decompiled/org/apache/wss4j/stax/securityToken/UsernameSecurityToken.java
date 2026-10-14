/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityToken;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityToken.SubjectAndPrincipalSecurityToken;

public interface UsernameSecurityToken
extends SubjectAndPrincipalSecurityToken {
    public byte[] generateDerivedKey() throws WSSecurityException;

    public WSSConstants.UsernameTokenPasswordType getUsernameTokenPasswordType();

    public String getCreatedTime();

    public String getPassword();

    public String getUsername();

    public byte[] getNonce();

    public byte[] getSalt();

    public Long getIteration();
}

