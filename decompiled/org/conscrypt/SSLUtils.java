/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import org.conscrypt.EmptyArray;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLX509Certificate;

final class SSLUtils {
    static final boolean USE_ENGINE_SOCKET_BY_DEFAULT = Boolean.parseBoolean(System.getProperty("org.conscrypt.useEngineSocketByDefault", "true"));
    private static final int MAX_PROTOCOL_LENGTH = 255;
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private static final int MAX_ENCRYPTION_OVERHEAD_LENGTH = 86;
    private static final int MAX_ENCRYPTION_OVERHEAD_DIFF = 2147483561;
    private static final String KEY_TYPE_RSA = "RSA";
    private static final String KEY_TYPE_EC = "EC";

    static java.security.cert.X509Certificate[] decodeX509CertificateChain(byte[][] certChain) throws java.security.cert.CertificateException {
        CertificateFactory certificateFactory = SSLUtils.getCertificateFactory();
        int numCerts = certChain.length;
        java.security.cert.X509Certificate[] decodedCerts = new java.security.cert.X509Certificate[numCerts];
        for (int i = 0; i < numCerts; ++i) {
            decodedCerts[i] = SSLUtils.decodeX509Certificate(certificateFactory, certChain[i]);
        }
        return decodedCerts;
    }

    private static CertificateFactory getCertificateFactory() {
        try {
            return CertificateFactory.getInstance("X.509");
        }
        catch (java.security.cert.CertificateException e) {
            return null;
        }
    }

    private static java.security.cert.X509Certificate decodeX509Certificate(CertificateFactory certificateFactory, byte[] bytes) throws java.security.cert.CertificateException {
        if (certificateFactory != null) {
            return (java.security.cert.X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(bytes));
        }
        return OpenSSLX509Certificate.fromX509Der(bytes);
    }

    static String getServerX509KeyType(long sslCipherNative) {
        String kx_name = NativeCrypto.SSL_CIPHER_get_kx_name(sslCipherNative);
        if (kx_name.equals(KEY_TYPE_RSA) || kx_name.equals("DHE_RSA") || kx_name.equals("ECDHE_RSA")) {
            return KEY_TYPE_RSA;
        }
        if (kx_name.equals("ECDHE_ECDSA")) {
            return KEY_TYPE_EC;
        }
        return null;
    }

    static String getClientKeyType(byte clientCertificateType) {
        switch (clientCertificateType) {
            case 1: {
                return KEY_TYPE_RSA;
            }
            case 64: {
                return KEY_TYPE_EC;
            }
        }
        return null;
    }

    static String getClientKeyTypeFromSignatureAlg(int signatureAlg) {
        switch (NativeCrypto.SSL_get_signature_algorithm_key_type(signatureAlg)) {
            case 6: {
                return KEY_TYPE_RSA;
            }
            case 408: {
                return KEY_TYPE_EC;
            }
        }
        return null;
    }

    static Set<String> getSupportedClientKeyTypes(byte[] clientCertificateTypes, int[] signatureAlgs) {
        HashSet<String> fromClientCerts = new HashSet<String>(clientCertificateTypes.length);
        for (byte keyTypeCode : clientCertificateTypes) {
            String keyType = SSLUtils.getClientKeyType(keyTypeCode);
            if (keyType == null) continue;
            fromClientCerts.add(keyType);
        }
        LinkedHashSet<String> fromSigAlgs = new LinkedHashSet<String>(signatureAlgs.length);
        for (int signatureAlg : signatureAlgs) {
            String keyType = SSLUtils.getClientKeyTypeFromSignatureAlg(signatureAlg);
            if (keyType == null) continue;
            fromSigAlgs.add(keyType);
        }
        if (clientCertificateTypes.length > 0 && signatureAlgs.length > 0) {
            fromSigAlgs.retainAll(fromClientCerts);
            return fromSigAlgs;
        }
        if (signatureAlgs.length > 0) {
            return fromSigAlgs;
        }
        return fromClientCerts;
    }

    static byte[][] encodeSubjectX509Principals(java.security.cert.X509Certificate[] certificates) throws CertificateEncodingException {
        byte[][] principalBytes = new byte[certificates.length][];
        for (int i = 0; i < certificates.length; ++i) {
            principalBytes[i] = certificates[i].getSubjectX500Principal().getEncoded();
        }
        return principalBytes;
    }

    static X509Certificate[] toCertificateChain(java.security.cert.X509Certificate[] certificates) throws SSLPeerUnverifiedException {
        try {
            X509Certificate[] chain = new X509Certificate[certificates.length];
            for (int i = 0; i < certificates.length; ++i) {
                byte[] encoded = certificates[i].getEncoded();
                chain[i] = X509Certificate.getInstance(encoded);
            }
            return chain;
        }
        catch (CertificateEncodingException e) {
            SSLPeerUnverifiedException exception = new SSLPeerUnverifiedException(e.getMessage());
            exception.initCause(exception);
            throw exception;
        }
        catch (CertificateException e) {
            SSLPeerUnverifiedException exception = new SSLPeerUnverifiedException(e.getMessage());
            exception.initCause(exception);
            throw exception;
        }
    }

    static int calculateOutNetBufSize(int pendingBytes) {
        return Math.min(16709, 86 + Math.min(2147483561, pendingBytes));
    }

    static SSLHandshakeException toSSLHandshakeException(Throwable e) {
        if (e instanceof SSLHandshakeException) {
            return (SSLHandshakeException)e;
        }
        return (SSLHandshakeException)new SSLHandshakeException(e.getMessage()).initCause(e);
    }

    static SSLException toSSLException(Throwable e) {
        if (e instanceof SSLException) {
            return (SSLException)e;
        }
        return new SSLException(e);
    }

    static String toProtocolString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, US_ASCII);
    }

    static byte[] toProtocolBytes(String protocol) {
        if (protocol == null) {
            return null;
        }
        return protocol.getBytes(US_ASCII);
    }

    static String[] decodeProtocols(byte[] protocols) {
        byte protocolLength;
        byte protocolLength2;
        if (protocols.length == 0) {
            return EmptyArray.STRING;
        }
        int numProtocols = 0;
        for (int i = 0; i < protocols.length; i += 1 + protocolLength2) {
            protocolLength2 = protocols[i];
            if (protocolLength2 < 0 || protocolLength2 > protocols.length - i) {
                throw new IllegalArgumentException("Protocol has invalid length (" + protocolLength2 + " at position " + i + "): " + (protocols.length < 50 ? Arrays.toString(protocols) : protocols.length + " byte array"));
            }
            ++numProtocols;
        }
        String[] decoded = new String[numProtocols];
        int d = 0;
        for (int i = 0; i < protocols.length; i += 1 + protocolLength) {
            protocolLength = protocols[i];
            decoded[d++] = protocolLength > 0 ? new String(protocols, i + 1, (int)protocolLength, US_ASCII) : "";
        }
        return decoded;
    }

    static byte[] encodeProtocols(String[] protocols) {
        if (protocols == null) {
            throw new IllegalArgumentException("protocols array must be non-null");
        }
        if (protocols.length == 0) {
            return EmptyArray.BYTE;
        }
        int length = 0;
        for (int i = 0; i < protocols.length; ++i) {
            String protocol = protocols[i];
            if (protocol == null) {
                throw new IllegalArgumentException("protocol[" + i + "] is null");
            }
            int protocolLength = protocols[i].length();
            if (protocolLength == 0 || protocolLength > 255) {
                throw new IllegalArgumentException("protocol[" + i + "] has invalid length: " + protocolLength);
            }
            length += 1 + protocolLength;
        }
        byte[] data = new byte[length];
        int dataIndex = 0;
        for (int i = 0; i < protocols.length; ++i) {
            String protocol = protocols[i];
            int protocolLength = protocol.length();
            data[dataIndex++] = (byte)protocolLength;
            for (int ci = 0; ci < protocolLength; ++ci) {
                char c = protocol.charAt(ci);
                if (c > '\u007f') {
                    throw new IllegalArgumentException("Protocol contains invalid character: " + c + "(protocol=" + protocol + ")");
                }
                data[dataIndex++] = (byte)c;
            }
        }
        return data;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int getEncryptedPacketLength(ByteBuffer[] buffers, int offset) {
        ByteBuffer buffer = buffers[offset];
        if (buffer.remaining() >= 5) {
            return SSLUtils.getEncryptedPacketLength(buffer);
        }
        ByteBuffer tmp = ByteBuffer.allocate(5);
        do {
            buffer = buffers[offset++];
            int pos = buffer.position();
            int limit = buffer.limit();
            if (buffer.remaining() > tmp.remaining()) {
                buffer.limit(pos + tmp.remaining());
            }
            try {
                tmp.put(buffer);
            }
            finally {
                buffer.limit(limit);
                buffer.position(pos);
            }
        } while (tmp.hasRemaining());
        tmp.flip();
        return SSLUtils.getEncryptedPacketLength(tmp);
    }

    private static int getEncryptedPacketLength(ByteBuffer buffer) {
        int pos = buffer.position();
        switch (SSLUtils.unsignedByte(buffer.get(pos))) {
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                break;
            }
            default: {
                return -1;
            }
        }
        short majorVersion = SSLUtils.unsignedByte(buffer.get(pos + 1));
        if (majorVersion != 3) {
            return -1;
        }
        int packetLength = SSLUtils.unsignedShort(buffer.getShort(pos + 3)) + 5;
        if (packetLength <= 5) {
            return -1;
        }
        return packetLength;
    }

    private static short unsignedByte(byte b) {
        return (short)(b & 0xFF);
    }

    private static int unsignedShort(short s2) {
        return s2 & 0xFFFF;
    }

    static String[] concat(String[] ... arrays) {
        int resultLength = 0;
        for (String[] array : arrays) {
            resultLength += array.length;
        }
        String[] result = new String[resultLength];
        int resultOffset = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, result, resultOffset, array.length);
            resultOffset += array.length;
        }
        return result;
    }

    private SSLUtils() {
    }

    static final class EngineStates {
        static final int STATE_NEW = 0;
        static final int STATE_MODE_SET = 1;
        static final int STATE_HANDSHAKE_STARTED = 2;
        static final int STATE_HANDSHAKE_COMPLETED = 3;
        static final int STATE_READY_HANDSHAKE_CUT_THROUGH = 4;
        static final int STATE_READY = 5;
        static final int STATE_CLOSED_INBOUND = 6;
        static final int STATE_CLOSED_OUTBOUND = 7;
        static final int STATE_CLOSED = 8;

        private EngineStates() {
        }
    }

    static enum SessionType {
        OPEN_SSL(1),
        OPEN_SSL_WITH_OCSP(2),
        OPEN_SSL_WITH_TLS_SCT(3);

        final int value;

        private SessionType(int value) {
            this.value = value;
        }

        static boolean isSupportedType(int type) {
            return type == SessionType.OPEN_SSL.value || type == SessionType.OPEN_SSL_WITH_OCSP.value || type == SessionType.OPEN_SSL_WITH_TLS_SCT.value;
        }
    }
}

