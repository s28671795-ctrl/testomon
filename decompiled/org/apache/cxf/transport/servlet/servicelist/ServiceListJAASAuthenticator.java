/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.servlet.servicelist;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.transport.http.blueprint.HttpDestinationBPBeanDefinitionParser;

public class ServiceListJAASAuthenticator {
    private static final Logger LOG = LogUtils.getL7dLogger(HttpDestinationBPBeanDefinitionParser.class);
    private static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private String realm;

    public String getRealm() {
        return this.realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Object authenticate(String username, String password) {
        return this.doAuthenticate(username, password);
    }

    public Subject doAuthenticate(final String username, final String password) {
        try {
            Subject subject = new Subject();
            LoginContext loginContext = new LoginContext(this.realm, subject, new CallbackHandler(){

                @Override
                public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                    for (int i = 0; i < callbacks.length; ++i) {
                        if (callbacks[i] instanceof NameCallback) {
                            ((NameCallback)callbacks[i]).setName(username);
                            continue;
                        }
                        if (callbacks[i] instanceof PasswordCallback) {
                            ((PasswordCallback)callbacks[i]).setPassword(password.toCharArray());
                            continue;
                        }
                        throw new UnsupportedCallbackException(callbacks[i]);
                    }
                }
            });
            loginContext.login();
            return subject;
        }
        catch (FailedLoginException e) {
            LOG.log(Level.FINE, "Login failed ", e);
            return null;
        }
        catch (AccountException e) {
            LOG.log(Level.WARNING, "Account failure ", e);
            return null;
        }
        catch (GeneralSecurityException e) {
            LOG.log(Level.SEVERE, "General Security Exception ", e);
            return null;
        }
    }

    public boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        int blank;
        String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (authHeader != null && authHeader.length() > 0 && (blank = (authHeader = authHeader.trim()).indexOf(32)) > 0) {
            String authType = authHeader.substring(0, blank);
            String authInfo = authHeader.substring(blank).trim();
            if (authType.equalsIgnoreCase(AUTHENTICATION_SCHEME_BASIC)) {
                try {
                    String srcString = ServiceListJAASAuthenticator.base64Decode(authInfo);
                    int i = srcString.indexOf(58);
                    String username = srcString.substring(0, i);
                    String password = srcString.substring(i + 1);
                    Subject subject = this.doAuthenticate(username, password);
                    if (subject != null) {
                        return true;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        try {
            response.setHeader(HEADER_WWW_AUTHENTICATE, "Basic realm=\"" + this.realm + "\"");
            response.setStatus(401);
            response.setContentLength(0);
            response.flushBuffer();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return false;
    }

    private static String base64Decode(String srcString) {
        byte[] transformed = null;
        try {
            transformed = Base64Utility.decode(srcString);
            return new String(transformed, "ISO-8859-1");
        }
        catch (UnsupportedEncodingException | Base64Exception e) {
            return srcString;
        }
    }
}

