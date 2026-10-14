/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.lang.reflect.Method;
import javax.net.ssl.SSLParameters;
import org.conscrypt.AbstractConscryptSocket;
import org.conscrypt.ConscryptEngine;
import org.conscrypt.EmptyArray;
import org.conscrypt.Java8PlatformUtil;
import org.conscrypt.SSLParametersImpl;

final class Java9PlatformUtil {
    private static final Method SSL_PARAMETERS_GET_APPLICATION_PROTOCOLS_METHOD;
    private static final Method SSL_PARAMETERS_SET_APPLICATION_PROTOCOLS_METHOD;

    static void setSSLParameters(SSLParameters src, SSLParametersImpl dest, AbstractConscryptSocket socket) {
        Java8PlatformUtil.setSSLParameters(src, dest, socket);
        dest.setApplicationProtocols(Java9PlatformUtil.getApplicationProtocols(src));
    }

    static void getSSLParameters(SSLParameters dest, SSLParametersImpl src, AbstractConscryptSocket socket) {
        Java8PlatformUtil.getSSLParameters(dest, src, socket);
        Java9PlatformUtil.setApplicationProtocols(dest, src.getApplicationProtocols());
    }

    static void setSSLParameters(SSLParameters src, SSLParametersImpl dest, ConscryptEngine engine) {
        Java8PlatformUtil.setSSLParameters(src, dest, engine);
        dest.setApplicationProtocols(Java9PlatformUtil.getApplicationProtocols(src));
    }

    static void getSSLParameters(SSLParameters dest, SSLParametersImpl src, ConscryptEngine engine) {
        Java8PlatformUtil.getSSLParameters(dest, src, engine);
        Java9PlatformUtil.setApplicationProtocols(dest, src.getApplicationProtocols());
    }

    private static String[] getApplicationProtocols(SSLParameters params) {
        if (SSL_PARAMETERS_GET_APPLICATION_PROTOCOLS_METHOD != null) {
            try {
                return (String[])SSL_PARAMETERS_GET_APPLICATION_PROTOCOLS_METHOD.invoke((Object)params, new Object[0]);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                // empty catch block
            }
        }
        return EmptyArray.STRING;
    }

    private static void setApplicationProtocols(SSLParameters params, String[] protocols) {
        if (SSL_PARAMETERS_SET_APPLICATION_PROTOCOLS_METHOD != null) {
            try {
                SSL_PARAMETERS_SET_APPLICATION_PROTOCOLS_METHOD.invoke((Object)params, new Object[]{protocols});
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                // empty catch block
            }
        }
    }

    private Java9PlatformUtil() {
    }

    static {
        Method setApplicationProtocolsMethod;
        Method getApplicationProtocolsMethod;
        Class<SSLParameters> sslParameters = SSLParameters.class;
        try {
            getApplicationProtocolsMethod = sslParameters.getMethod("getApplicationProtocols", new Class[0]);
            setApplicationProtocolsMethod = sslParameters.getMethod("setApplicationProtocols", String[].class);
        }
        catch (NoSuchMethodException e) {
            getApplicationProtocolsMethod = null;
            setApplicationProtocolsMethod = null;
        }
        SSL_PARAMETERS_GET_APPLICATION_PROTOCOLS_METHOD = getApplicationProtocolsMethod;
        SSL_PARAMETERS_SET_APPLICATION_PROTOCOLS_METHOD = setApplicationProtocolsMethod;
    }
}

