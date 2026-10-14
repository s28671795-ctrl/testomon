/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth.impl;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import org.h2.api.CredentialsValidator;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.ConfigProperties;

public class LdapCredentialsValidator
implements CredentialsValidator {
    private String bindDnPattern;
    private String host;
    private int port;
    private boolean secure;
    private String url;

    @Override
    public void configure(ConfigProperties configProperties) {
        this.bindDnPattern = configProperties.getStringValue("bindDnPattern");
        this.host = configProperties.getStringValue("host");
        this.secure = configProperties.getBooleanValue("secure", true);
        this.port = configProperties.getIntValue("port", this.secure ? 636 : 389);
        this.url = "ldap" + (this.secure ? "s" : "") + "://" + this.host + ":" + this.port;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean validateCredentials(AuthenticationInfo authenticationInfo) throws Exception {
        try (Context context = null;){
            String string = this.bindDnPattern.replace("%u", authenticationInfo.getUserName());
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
            hashtable.put("java.naming.provider.url", this.url);
            hashtable.put("java.naming.security.authentication", "simple");
            hashtable.put("java.naming.security.principal", string);
            hashtable.put("java.naming.security.credentials", authenticationInfo.getPassword());
            if (this.secure) {
                hashtable.put("java.naming.security.protocol", "ssl");
            }
            context = new InitialDirContext(hashtable);
            authenticationInfo.setNestedIdentity(string);
            boolean bl = true;
            return bl;
        }
    }
}

