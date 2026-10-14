/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security.callback;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.interceptor.security.NamePasswordCallbackHandler;
import org.apache.cxf.interceptor.security.callback.CallbackHandlerProvider;
import org.apache.cxf.interceptor.security.callback.CertificateToNameMapper;
import org.apache.cxf.interceptor.security.callback.NameToPasswordMapper;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.transport.TLSSessionInfo;

public class CallbackHandlerTlsCert
implements CallbackHandlerProvider {
    private CertificateToNameMapper certMapper = new CertificateToNameMapper(){

        @Override
        public String getUserName(Certificate cert) {
            return ((X509Certificate)cert).getSubjectDN().getName();
        }
    };
    private NameToPasswordMapper nameToPasswordMapper = new NameToPasswordMapper(){

        @Override
        public String getPassword(String userName) {
            return CallbackHandlerTlsCert.this.fixedPassword;
        }
    };
    private String fixedPassword;

    @Override
    public CallbackHandler create(Message message) {
        TLSSessionInfo tlsSession = message.get(TLSSessionInfo.class);
        if (tlsSession == null) {
            return null;
        }
        Certificate cert = this.getCertificate(message);
        String name = this.certMapper.getUserName(cert);
        String password = this.nameToPasswordMapper.getPassword(name);
        return new NamePasswordCallbackHandler(name, password);
    }

    private Certificate getCertificate(Message message) {
        TLSSessionInfo tlsSessionInfo = message.get(TLSSessionInfo.class);
        if (tlsSessionInfo == null) {
            throw new SecurityException("Not TLS connection");
        }
        Certificate[] certificates = tlsSessionInfo.getPeerCertificates();
        if (certificates == null || certificates.length == 0) {
            throw new SecurityException("No certificate found");
        }
        return certificates[0];
    }

    public void setCertMapper(CertificateToNameMapper certMapper) {
        this.certMapper = certMapper;
    }

    public void setFixedPassword(String fixedPassword) {
        this.fixedPassword = fixedPassword;
    }

    public void setNameToPasswordMapper(NameToPasswordMapper nameToPasswordMapper) {
        this.nameToPasswordMapper = nameToPasswordMapper;
    }
}

