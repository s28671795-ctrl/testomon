/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityToken;

import java.security.Principal;
import javax.security.auth.Subject;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public interface SubjectAndPrincipalSecurityToken
extends SecurityToken {
    public Subject getSubject() throws WSSecurityException;

    public Principal getPrincipal() throws WSSecurityException;
}

