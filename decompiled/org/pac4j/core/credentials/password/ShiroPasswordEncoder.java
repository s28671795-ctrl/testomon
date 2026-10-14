/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.authc.credential.PasswordService
 */
package org.pac4j.core.credentials.password;

import org.apache.shiro.authc.credential.PasswordService;
import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.util.CommonHelper;

public class ShiroPasswordEncoder
implements PasswordEncoder {
    private PasswordService delegate;

    public ShiroPasswordEncoder(PasswordService delegate) {
        this.setDelegate(delegate);
    }

    @Override
    public String encode(String password) {
        return this.delegate.encryptPassword((Object)password);
    }

    @Override
    public boolean matches(String plainPassword, String encodedPassword) {
        return this.delegate.passwordsMatch((Object)plainPassword, encodedPassword);
    }

    public PasswordService getDelegate() {
        return this.delegate;
    }

    public void setDelegate(PasswordService delegate) {
        CommonHelper.assertNotNull("delegate", delegate);
        this.delegate = delegate;
    }
}

