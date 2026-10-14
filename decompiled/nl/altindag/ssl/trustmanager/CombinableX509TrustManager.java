/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.trustmanager.TrustManagerConsumer;

interface CombinableX509TrustManager
extends X509TrustManager {
    public static final String CERTIFICATE_EXCEPTION_MESSAGE = "None of the TrustManagers trust this certificate chain";

    public List<X509ExtendedTrustManager> getInnerTrustManagers();

    default public void checkTrusted(TrustManagerConsumer callBackConsumer) throws CertificateException {
        ArrayList<CertificateException> certificateExceptions = new ArrayList<CertificateException>();
        for (X509ExtendedTrustManager trustManager : this.getInnerTrustManagers()) {
            try {
                callBackConsumer.checkTrusted(trustManager);
                return;
            }
            catch (CertificateException e) {
                certificateExceptions.add(e);
            }
            catch (RuntimeException e) {
                Throwable cause = e.getCause();
                if (cause instanceof InvalidAlgorithmParameterException) {
                    certificateExceptions.add(new CertificateException(cause));
                    continue;
                }
                throw e;
            }
        }
        CertificateException certificateException = new CertificateException(CERTIFICATE_EXCEPTION_MESSAGE);
        certificateExceptions.forEach(certificateException::addSuppressed);
        throw certificateException;
    }
}

