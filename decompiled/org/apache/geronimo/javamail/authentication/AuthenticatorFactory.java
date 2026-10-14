/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.javamail.authentication.CramMD5Authenticator;
import org.apache.geronimo.javamail.authentication.DigestMD5Authenticator;
import org.apache.geronimo.javamail.authentication.LoginAuthenticator;
import org.apache.geronimo.javamail.authentication.PlainAuthenticator;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class AuthenticatorFactory {
    public static final String AUTHENTICATION_PLAIN = "PLAIN";
    public static final String AUTHENTICATION_LOGIN = "LOGIN";
    public static final String AUTHENTICATION_CRAMMD5 = "CRAM-MD5";
    public static final String AUTHENTICATION_DIGESTMD5 = "DIGEST-MD5";

    public static ClientAuthenticator getAuthenticator(ProtocolProperties props, List mechanisms, String host2, String username, String password, String authId, String realm) {
        if (authId == null) {
            authId = username;
        }
        if (props.getBooleanProperty("sasl.enable", false)) {
            String[] mechs = mechanisms.toArray(new String[mechanisms.size()]);
            try {
                Class<?> authenticatorClass = Class.forName("org.apache.geronimo.javamail.authentication.SASLAuthenticator");
                Constructor<?> c = authenticatorClass.getConstructor(new String[0].getClass(), Properties.class, String.class, String.class, String.class, String.class, String.class, String.class);
                Object[] args2 = new Object[]{mechs, props.getProperties(), props.getProtocol(), host2, realm, authId, username, password};
                return (ClientAuthenticator)c.newInstance(args2);
            }
            catch (Throwable e) {
                // empty catch block
            }
        }
        if (mechanisms.contains(AUTHENTICATION_DIGESTMD5)) {
            return new DigestMD5Authenticator(host2, username, password, realm);
        }
        if (mechanisms.contains(AUTHENTICATION_CRAMMD5)) {
            return new CramMD5Authenticator(username, password);
        }
        if (mechanisms.contains(AUTHENTICATION_LOGIN)) {
            return new LoginAuthenticator(username, password);
        }
        if (mechanisms.contains(AUTHENTICATION_PLAIN)) {
            return new PlainAuthenticator(username, password);
        }
        return null;
    }
}

