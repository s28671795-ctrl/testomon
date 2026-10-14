/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth.impl;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import org.h2.api.CredentialsValidator;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.ConfigProperties;

public class JaasCredentialsValidator
implements CredentialsValidator {
    public static final String DEFAULT_APPNAME = "h2";
    private String appName;

    public JaasCredentialsValidator() {
        this(DEFAULT_APPNAME);
    }

    public JaasCredentialsValidator(String string) {
        this.appName = string;
    }

    @Override
    public void configure(ConfigProperties configProperties) {
        this.appName = configProperties.getStringValue("appName", this.appName);
    }

    @Override
    public boolean validateCredentials(AuthenticationInfo authenticationInfo) throws Exception {
        LoginContext loginContext = new LoginContext(this.appName, new AuthenticationInfoCallbackHandler(authenticationInfo));
        loginContext.login();
        authenticationInfo.setNestedIdentity(loginContext.getSubject());
        return true;
    }

    static class AuthenticationInfoCallbackHandler
    implements CallbackHandler {
        AuthenticationInfo authenticationInfo;

        AuthenticationInfoCallbackHandler(AuthenticationInfo authenticationInfo) {
            this.authenticationInfo = authenticationInfo;
        }

        @Override
        public void handle(Callback[] callbackArray) throws IOException, UnsupportedCallbackException {
            for (int i = 0; i < callbackArray.length; ++i) {
                if (callbackArray[i] instanceof NameCallback) {
                    ((NameCallback)callbackArray[i]).setName(this.authenticationInfo.getUserName());
                    continue;
                }
                if (!(callbackArray[i] instanceof PasswordCallback)) continue;
                ((PasswordCallback)callbackArray[i]).setPassword(this.authenticationInfo.getPassword().toCharArray());
            }
        }
    }
}

