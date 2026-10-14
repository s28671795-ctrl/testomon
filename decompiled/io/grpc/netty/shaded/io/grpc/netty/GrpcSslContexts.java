/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.grpc.ExperimentalApi;
import io.grpc.internal.ConscryptLoader;
import io.grpc.netty.shaded.io.grpc.netty.JettyTlsUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2SecurityUtil;
import io.grpc.netty.shaded.io.netty.handler.ssl.ApplicationProtocolConfig;
import io.grpc.netty.shaded.io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.grpc.netty.shaded.io.netty.handler.ssl.OpenSsl;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslProvider;
import io.grpc.netty.shaded.io.netty.handler.ssl.SupportedCipherSuiteFilter;
import java.io.File;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1784")
public class GrpcSslContexts {
    private static final Logger logger = Logger.getLogger(GrpcSslContexts.class.getName());
    private static final String HTTP2_VERSION = "h2";
    private static final List<String> NEXT_PROTOCOL_VERSIONS = Collections.unmodifiableList(Arrays.asList("h2"));
    private static final ApplicationProtocolConfig ALPN = new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, (Iterable<String>)NEXT_PROTOCOL_VERSIONS);
    private static final ApplicationProtocolConfig NPN = new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN, ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, (Iterable<String>)NEXT_PROTOCOL_VERSIONS);
    private static final ApplicationProtocolConfig NPN_AND_ALPN = new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN_AND_ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, (Iterable<String>)NEXT_PROTOCOL_VERSIONS);
    private static final String SUN_PROVIDER_NAME = "SunJSSE";
    private static final String IBM_PROVIDER_NAME = "IBMJSSE2";
    private static final String OPENJSSE_PROVIDER_NAME = "OpenJSSE";

    private GrpcSslContexts() {
    }

    public static SslContextBuilder forClient() {
        return GrpcSslContexts.configure(SslContextBuilder.forClient());
    }

    public static SslContextBuilder forServer(File keyCertChainFile, File keyFile) {
        return GrpcSslContexts.configure(SslContextBuilder.forServer(keyCertChainFile, keyFile));
    }

    public static SslContextBuilder forServer(File keyCertChainFile, File keyFile, String keyPassword) {
        return GrpcSslContexts.configure(SslContextBuilder.forServer(keyCertChainFile, keyFile, keyPassword));
    }

    public static SslContextBuilder forServer(InputStream keyCertChain, InputStream key) {
        return GrpcSslContexts.configure(SslContextBuilder.forServer(keyCertChain, key));
    }

    public static SslContextBuilder forServer(InputStream keyCertChain, InputStream key, String keyPassword) {
        return GrpcSslContexts.configure(SslContextBuilder.forServer(keyCertChain, key, keyPassword));
    }

    @CanIgnoreReturnValue
    public static SslContextBuilder configure(SslContextBuilder builder) {
        return GrpcSslContexts.configure(builder, GrpcSslContexts.defaultSslProvider());
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1784")
    @CanIgnoreReturnValue
    public static SslContextBuilder configure(SslContextBuilder builder, SslProvider provider) {
        switch (provider) {
            case JDK: {
                Provider jdkProvider = GrpcSslContexts.findJdkProvider();
                if (jdkProvider == null) {
                    throw new IllegalArgumentException("Could not find Jetty NPN/ALPN or Conscrypt as installed JDK providers");
                }
                return GrpcSslContexts.configure(builder, jdkProvider);
            }
            case OPENSSL: {
                ApplicationProtocolConfig apc = OpenSsl.isAlpnSupported() ? NPN_AND_ALPN : NPN;
                return builder.sslProvider(SslProvider.OPENSSL).ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE).applicationProtocolConfig(apc);
            }
        }
        throw new IllegalArgumentException("Unsupported provider: " + (Object)((Object)provider));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @CanIgnoreReturnValue
    public static SslContextBuilder configure(SslContextBuilder builder, Provider jdkProvider) {
        ApplicationProtocolConfig apc;
        if (SUN_PROVIDER_NAME.equals(jdkProvider.getName())) {
            if (JettyTlsUtil.isJettyAlpnConfigured()) {
                apc = ALPN;
                return builder.sslProvider(SslProvider.JDK).ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE).applicationProtocolConfig(apc).sslContextProvider(jdkProvider);
            } else if (JettyTlsUtil.isJettyNpnConfigured()) {
                apc = NPN;
                return builder.sslProvider(SslProvider.JDK).ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE).applicationProtocolConfig(apc).sslContextProvider(jdkProvider);
            } else {
                if (!JettyTlsUtil.isJava9AlpnAvailable()) throw new IllegalArgumentException(jdkProvider.getName() + " selected, but Java 9+ and Jetty NPN/ALPN unavailable");
                apc = ALPN;
            }
            return builder.sslProvider(SslProvider.JDK).ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE).applicationProtocolConfig(apc).sslContextProvider(jdkProvider);
        } else if (IBM_PROVIDER_NAME.equals(jdkProvider.getName()) || OPENJSSE_PROVIDER_NAME.equals(jdkProvider.getName())) {
            if (!JettyTlsUtil.isJava9AlpnAvailable()) throw new IllegalArgumentException(jdkProvider.getName() + " selected, but Java 9+ ALPN unavailable");
            apc = ALPN;
            return builder.sslProvider(SslProvider.JDK).ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE).applicationProtocolConfig(apc).sslContextProvider(jdkProvider);
        } else {
            if (!ConscryptLoader.isConscrypt(jdkProvider)) throw new IllegalArgumentException("Unknown provider; can't configure: " + jdkProvider);
            apc = ALPN;
            builder.protocols("TLSv1.2");
        }
        return builder.sslProvider(SslProvider.JDK).ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE).applicationProtocolConfig(apc).sslContextProvider(jdkProvider);
    }

    private static SslProvider defaultSslProvider() {
        if (OpenSsl.isAvailable()) {
            logger.log(Level.FINE, "Selecting OPENSSL");
            return SslProvider.OPENSSL;
        }
        Provider provider = GrpcSslContexts.findJdkProvider();
        if (provider != null) {
            logger.log(Level.FINE, "Selecting JDK with provider {0}", provider);
            return SslProvider.JDK;
        }
        logger.log(Level.INFO, "Java 9 ALPN API unavailable (this may be normal)");
        logger.log(Level.INFO, "netty-tcnative unavailable (this may be normal)", OpenSsl.unavailabilityCause());
        logger.log(Level.INFO, "Conscrypt not found (this may be normal)", ConscryptHolder.UNAVAILABILITY_CAUSE);
        logger.log(Level.INFO, "Jetty ALPN unavailable (this may be normal)", JettyTlsUtil.getJettyAlpnUnavailabilityCause());
        throw new IllegalStateException("Could not find TLS ALPN provider; no working netty-tcnative, Conscrypt, or Jetty NPN/ALPN available");
    }

    private static Provider findJdkProvider() {
        for (Provider provider : Security.getProviders("SSLContext.TLS")) {
            if (!(SUN_PROVIDER_NAME.equals(provider.getName()) ? JettyTlsUtil.isJettyAlpnConfigured() || JettyTlsUtil.isJettyNpnConfigured() || JettyTlsUtil.isJava9AlpnAvailable() : (IBM_PROVIDER_NAME.equals(provider.getName()) || OPENJSSE_PROVIDER_NAME.equals(provider.getName()) ? JettyTlsUtil.isJava9AlpnAvailable() : ConscryptLoader.isConscrypt(provider)))) continue;
            return provider;
        }
        if (ConscryptHolder.PROVIDER != null) {
            return ConscryptHolder.PROVIDER;
        }
        return null;
    }

    static void ensureAlpnAndH2Enabled(ApplicationProtocolNegotiator alpnNegotiator) {
        Preconditions.checkArgument(alpnNegotiator != null, "ALPN must be configured");
        Preconditions.checkArgument(alpnNegotiator.protocols() != null && !alpnNegotiator.protocols().isEmpty(), "ALPN must be enabled and list HTTP/2 as a supported protocol.");
        Preconditions.checkArgument(alpnNegotiator.protocols().contains(HTTP2_VERSION), "This ALPN config does not support HTTP/2. Expected %s, but got %s'.", (Object)HTTP2_VERSION, alpnNegotiator.protocols());
    }

    private static class ConscryptHolder {
        static final Provider PROVIDER;
        static final Throwable UNAVAILABILITY_CAUSE;

        private ConscryptHolder() {
        }

        static {
            Throwable cause;
            Provider provider;
            try {
                provider = ConscryptLoader.newProvider();
                cause = null;
            }
            catch (Throwable t2) {
                provider = null;
                cause = t2;
            }
            PROVIDER = provider;
            UNAVAILABILITY_CAUSE = cause;
        }
    }
}

