/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import org.conscrypt.AbstractConscryptSocket;
import org.conscrypt.AddressUtils;
import org.conscrypt.ConscryptEngine;
import org.conscrypt.ExternalSession;
import org.conscrypt.Java8EngineWrapper;
import org.conscrypt.Java8ExtendedSSLSession;
import org.conscrypt.SSLParametersImpl;

final class Java8PlatformUtil {
    static void setSSLParameters(SSLParameters params, SSLParametersImpl impl, AbstractConscryptSocket socket) {
        Java8PlatformUtil.setSSLParameters(params, impl);
        String sniHost = Java8PlatformUtil.getSniHostName(params);
        if (sniHost != null) {
            socket.setHostname(sniHost);
        }
    }

    static void setSSLParameters(SSLParameters params, SSLParametersImpl impl, ConscryptEngine engine) {
        Java8PlatformUtil.setSSLParameters(params, impl);
        String sniHost = Java8PlatformUtil.getSniHostName(params);
        if (sniHost != null) {
            engine.setHostname(sniHost);
        }
    }

    static void getSSLParameters(SSLParameters params, SSLParametersImpl impl, AbstractConscryptSocket socket) {
        Java8PlatformUtil.getSSLParameters(params, impl);
        if (impl.getUseSni() && AddressUtils.isValidSniHostname(socket.getHostname())) {
            params.setServerNames(Collections.singletonList(new SNIHostName(socket.getHostname())));
        }
    }

    static void getSSLParameters(SSLParameters params, SSLParametersImpl impl, ConscryptEngine engine) {
        Java8PlatformUtil.getSSLParameters(params, impl);
        if (impl.getUseSni() && AddressUtils.isValidSniHostname(engine.getHostname())) {
            params.setServerNames(Collections.singletonList(new SNIHostName(engine.getHostname())));
        }
    }

    private static String getSniHostName(SSLParameters params) {
        List<SNIServerName> serverNames = params.getServerNames();
        if (serverNames != null) {
            for (SNIServerName serverName : serverNames) {
                if (serverName.getType() != 0) continue;
                return ((SNIHostName)serverName).getAsciiName();
            }
        }
        return null;
    }

    private static void setSSLParameters(SSLParameters params, SSLParametersImpl impl) {
        impl.setEndpointIdentificationAlgorithm(params.getEndpointIdentificationAlgorithm());
        impl.setUseCipherSuitesOrder(params.getUseCipherSuitesOrder());
        impl.setSNIMatchers(params.getSNIMatchers());
        impl.setAlgorithmConstraints(params.getAlgorithmConstraints());
    }

    private static void getSSLParameters(SSLParameters params, SSLParametersImpl impl) {
        params.setEndpointIdentificationAlgorithm(impl.getEndpointIdentificationAlgorithm());
        params.setUseCipherSuitesOrder(impl.getUseCipherSuitesOrder());
        params.setSNIMatchers(impl.getSNIMatchers());
        params.setAlgorithmConstraints(impl.getAlgorithmConstraints());
    }

    static boolean serverNamePermitted(SSLParametersImpl parameters, String serverName) {
        Collection<SNIMatcher> sniMatchers = parameters.getSNIMatchers();
        if (sniMatchers == null || sniMatchers.isEmpty()) {
            return true;
        }
        for (SNIMatcher m4 : sniMatchers) {
            boolean match = m4.matches(new SNIHostName(serverName));
            if (!match) continue;
            return true;
        }
        return false;
    }

    static SSLEngine wrapEngine(ConscryptEngine engine) {
        return new Java8EngineWrapper(engine);
    }

    static SSLEngine unwrapEngine(SSLEngine engine) {
        return Java8EngineWrapper.getDelegate(engine);
    }

    static SSLSession wrapSSLSession(ExternalSession sslSession) {
        return new Java8ExtendedSSLSession(sslSession);
    }

    private Java8PlatformUtil() {
    }
}

