/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.hostnameverifier;

import java.util.function.Predicate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import nl.altindag.ssl.model.HostnameVerifierParameters;

public final class EnhanceableHostnameVerifier
implements HostnameVerifier {
    private final HostnameVerifier baseHostnameVerifier;
    private final Predicate<HostnameVerifierParameters> hostnameVerifierParametersValidator;

    public EnhanceableHostnameVerifier(HostnameVerifier baseHostnameVerifier, Predicate<HostnameVerifierParameters> hostnameVerifierParametersValidator) {
        this.baseHostnameVerifier = baseHostnameVerifier;
        this.hostnameVerifierParametersValidator = hostnameVerifierParametersValidator;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        HostnameVerifierParameters hostnameVerifierParameters = new HostnameVerifierParameters(hostname, session);
        if (this.hostnameVerifierParametersValidator.test(hostnameVerifierParameters)) {
            return true;
        }
        return this.baseHostnameVerifier.verify(hostname, session);
    }
}

