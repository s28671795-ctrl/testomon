/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.security;

import org.apache.cxf.common.security.SecurityToken;
import org.apache.cxf.common.security.TokenType;

public class UsernameToken
implements SecurityToken {
    private String name;
    private String password;
    private String passwordType;
    private boolean isHashed;
    private String nonce;
    private String createdTime;

    public UsernameToken(String name, String password, String passwordType, boolean isHashed, String nonce, String createdTime) {
        this.name = name;
        this.password = password;
        this.passwordType = passwordType;
        this.isHashed = isHashed;
        this.nonce = nonce;
        this.createdTime = createdTime;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPasswordType() {
        return this.passwordType;
    }

    public boolean isHashed() {
        return this.isHashed;
    }

    public String getNonce() {
        return this.nonce;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.UsernameToken;
    }
}

