/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import javax.security.auth.Subject;
import org.apache.cxf.common.security.SecurityToken;
import org.apache.cxf.common.security.TokenType;
import org.apache.cxf.common.security.UsernameToken;
import org.apache.cxf.interceptor.security.AbstractSecurityContextInInterceptor;

public abstract class AbstractUsernameTokenInInterceptor
extends AbstractSecurityContextInInterceptor {
    @Override
    protected Subject createSubject(SecurityToken token) {
        if (token.getTokenType() != TokenType.UsernameToken) {
            this.reportSecurityException("Unsupported token type " + token.getTokenType().toString());
        }
        UsernameToken ut = (UsernameToken)token;
        return this.createSubject(ut);
    }

    protected abstract Subject createSubject(UsernameToken var1);
}

