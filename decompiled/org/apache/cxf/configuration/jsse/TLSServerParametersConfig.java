/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.jsse.TLSParameterJaxBUtils;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.TLSServerParametersType;

@NoJSR250Annotations
public class TLSServerParametersConfig
extends TLSServerParameters {
    public TLSServerParametersConfig(TLSServerParametersType params) throws GeneralSecurityException, IOException {
        TLSServerParametersTypeInternal iparams = null;
        if (params instanceof TLSServerParametersTypeInternal) {
            iparams = (TLSServerParametersTypeInternal)params;
        }
        if (params.isSetSecureSocketProtocol()) {
            this.setSecureSocketProtocol(params.getSecureSocketProtocol());
        }
        if (params.isSetCipherSuitesFilter()) {
            this.setCipherSuitesFilter(params.getCipherSuitesFilter());
        }
        if (params.isSetCipherSuites()) {
            this.setCipherSuites(params.getCipherSuites().getCipherSuite());
        }
        if (params.isSetExcludeProtocols()) {
            this.setExcludeProtocols(params.getExcludeProtocols().getExcludeProtocol());
        }
        if (params.isSetIncludeProtocols()) {
            this.setIncludeProtocols(params.getIncludeProtocols().getIncludeProtocol());
        }
        if (params.isSetJsseProvider()) {
            this.setJsseProvider(params.getJsseProvider());
        }
        if (params.isSetSecureRandomParameters()) {
            this.setSecureRandom(TLSParameterJaxBUtils.getSecureRandom(params.getSecureRandomParameters()));
        }
        if (params.isSetClientAuthentication()) {
            this.setClientAuthentication(params.getClientAuthentication());
        }
        if (params.isSetKeyManagers()) {
            if (!params.isSetCertAlias()) {
                this.setKeyManagers(TLSParameterJaxBUtils.getKeyManagers(params.getKeyManagers()));
            } else {
                this.setKeyManagers(TLSParameterJaxBUtils.getKeyManagers(params.getKeyManagers(), params.getCertAlias()));
            }
        }
        if (params.isSetTrustManagers()) {
            this.setTrustManagers(TLSParameterJaxBUtils.getTrustManagers(params.getTrustManagers(), params.isEnableRevocation()));
        }
        if (params.isSetCertConstraints()) {
            this.setCertConstraints(params.getCertConstraints());
        }
        if (params.isSetCertAlias()) {
            this.setCertAlias(params.getCertAlias());
        }
        if (iparams != null && iparams.isSetKeyManagersRef()) {
            this.setKeyManagers(iparams.getKeyManagersRef());
        }
        if (iparams != null && iparams.isSetTrustManagersRef()) {
            this.setTrustManagers(iparams.getTrustManagersRef());
        }
    }

    public static class TLSServerParametersTypeInternal
    extends TLSServerParametersType {
        private KeyManager[] keyManagersRef;
        private TrustManager[] trustManagersRef;

        public KeyManager[] getKeyManagersRef() {
            return this.keyManagersRef;
        }

        public void setKeyManagersRef(KeyManager[] keyManagersRef) {
            this.keyManagersRef = keyManagersRef;
        }

        public boolean isSetKeyManagersRef() {
            return this.keyManagersRef != null;
        }

        public TrustManager[] getTrustManagersRef() {
            return this.trustManagersRef;
        }

        public void setTrustManagersRef(TrustManager[] trustManagersRef) {
            this.trustManagersRef = trustManagersRef;
        }

        public boolean isSetTrustManagersRef() {
            return this.trustManagersRef != null;
        }
    }
}

