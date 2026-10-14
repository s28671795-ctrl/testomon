/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.password;

import org.mindrot.jbcrypt.BCrypt;
import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.util.CommonHelper;

public class JBCryptPasswordEncoder
implements PasswordEncoder {
    private String salt;

    public JBCryptPasswordEncoder(String salt) {
        this.salt = salt;
    }

    public JBCryptPasswordEncoder() {
        this.salt = BCrypt.gensalt();
    }

    @Override
    public String encode(String password) {
        CommonHelper.assertNotBlank("salt", this.salt);
        return BCrypt.hashpw(password, this.salt);
    }

    @Override
    public boolean matches(String plainPassword, String encodedPassword) {
        CommonHelper.assertNotBlank("salt", this.salt);
        return BCrypt.checkpw(plainPassword, encodedPassword);
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

