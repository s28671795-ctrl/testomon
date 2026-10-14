/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.security.cert.X509Certificate;
import org.apache.activemq.security.SecurityContext;

public interface AuthenticationBroker {
    public SecurityContext authenticate(String var1, String var2, X509Certificate[] var3) throws SecurityException;
}

