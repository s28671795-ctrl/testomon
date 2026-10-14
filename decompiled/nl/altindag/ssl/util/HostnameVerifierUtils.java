/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.util.function.Predicate;
import javax.net.ssl.HostnameVerifier;
import nl.altindag.ssl.hostnameverifier.BasicHostnameVerifier;
import nl.altindag.ssl.hostnameverifier.EnhanceableHostnameVerifier;
import nl.altindag.ssl.hostnameverifier.FenixHostnameVerifier;
import nl.altindag.ssl.hostnameverifier.UnsafeHostnameVerifier;
import nl.altindag.ssl.model.HostnameVerifierParameters;

public final class HostnameVerifierUtils {
    private HostnameVerifierUtils() {
    }

    public static HostnameVerifier createBasic() {
        return BasicHostnameVerifier.getInstance();
    }

    public static HostnameVerifier createUnsafe() {
        return UnsafeHostnameVerifier.getInstance();
    }

    @Deprecated
    public static HostnameVerifier createFenix() {
        return HostnameVerifierUtils.createDefault();
    }

    public static HostnameVerifier createDefault() {
        return FenixHostnameVerifier.getInstance();
    }

    public static HostnameVerifier createEnhanceable(HostnameVerifier baseHostnameVerifier, Predicate<HostnameVerifierParameters> hostnameVerifierParametersValidator) {
        return new EnhanceableHostnameVerifier(baseHostnameVerifier, hostnameVerifierParametersValidator);
    }
}

