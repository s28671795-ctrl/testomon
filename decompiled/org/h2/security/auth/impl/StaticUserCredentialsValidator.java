/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth.impl;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import org.h2.api.CredentialsValidator;
import org.h2.security.SHA256;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.ConfigProperties;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class StaticUserCredentialsValidator
implements CredentialsValidator {
    private Pattern userNamePattern;
    private String password;
    private byte[] salt;
    private byte[] hashWithSalt;

    public StaticUserCredentialsValidator() {
    }

    public StaticUserCredentialsValidator(String string, String string2) {
        if (string != null) {
            this.userNamePattern = Pattern.compile(string.toUpperCase());
        }
        this.salt = MathUtils.secureRandomBytes(256);
        this.hashWithSalt = SHA256.getHashWithSalt(string2.getBytes(StandardCharsets.UTF_8), this.salt);
    }

    @Override
    public boolean validateCredentials(AuthenticationInfo authenticationInfo) throws AuthenticationException {
        if (this.userNamePattern != null && !this.userNamePattern.matcher(authenticationInfo.getUserName()).matches()) {
            return false;
        }
        if (this.password != null) {
            return this.password.equals(authenticationInfo.getPassword());
        }
        return Utils.compareSecure(this.hashWithSalt, SHA256.getHashWithSalt(authenticationInfo.getPassword().getBytes(StandardCharsets.UTF_8), this.salt));
    }

    @Override
    public void configure(ConfigProperties configProperties) {
        String string;
        String string2 = configProperties.getStringValue("userNamePattern", null);
        if (string2 != null) {
            this.userNamePattern = Pattern.compile(string2);
        }
        this.password = configProperties.getStringValue("password", this.password);
        String string3 = configProperties.getStringValue("salt", null);
        if (string3 != null) {
            this.salt = StringUtils.convertHexToBytes(string3);
        }
        if ((string = configProperties.getStringValue("hash", null)) != null) {
            this.hashWithSalt = SHA256.getHashWithSalt(StringUtils.convertHexToBytes(string), this.salt);
        }
    }
}

