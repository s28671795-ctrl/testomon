/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Set;
import javax.security.auth.Subject;
import org.apache.activemq.security.SecurityContext;

public class JaasCertificateSecurityContext
extends SecurityContext {
    private Subject subject;
    private X509Certificate[] certs;

    public JaasCertificateSecurityContext(String userName, Subject subject, X509Certificate[] certs) {
        super(userName);
        this.subject = subject;
        this.certs = certs;
    }

    @Override
    public Set<Principal> getPrincipals() {
        return this.subject.getPrincipals();
    }

    @Override
    public String getUserName() {
        if (this.certs != null && this.certs.length > 0) {
            return this.certs[0].getSubjectDN().getName();
        }
        return super.getUserName();
    }
}

