/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.crypto.password.PasswordEncoder
 */
package org.pac4j.core.credentials.password;

import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.util.CommonHelper;

public class SpringSecurityPasswordEncoder
implements PasswordEncoder {
    private final org.springframework.security.crypto.password.PasswordEncoder delegate;

    public SpringSecurityPasswordEncoder(org.springframework.security.crypto.password.PasswordEncoder delegate) {
        CommonHelper.assertNotNull("delegate", delegate);
        this.delegate = delegate;
    }

    @Override
    public String encode(String password) {
        return this.delegate.encode((CharSequence)password);
    }

    @Override
    public boolean matches(String plainPassword, String encodedPassword) {
        return this.delegate.matches((CharSequence)plainPassword, encodedPassword);
    }

    public org.springframework.security.crypto.password.PasswordEncoder getDelegate() {
        return this.delegate;
    }
}

