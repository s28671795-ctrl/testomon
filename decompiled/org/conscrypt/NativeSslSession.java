/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.ClientSessionContext;
import org.conscrypt.ConscryptSession;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.NativeSsl;
import org.conscrypt.OpenSSLX509Certificate;
import org.conscrypt.SSLUtils;

abstract class NativeSslSession {
    private static final Logger logger = Logger.getLogger(NativeSslSession.class.getName());

    NativeSslSession() {
    }

    static NativeSslSession newInstance(NativeRef.SSL_SESSION ref, ConscryptSession session) throws SSLPeerUnverifiedException {
        AbstractSessionContext context = (AbstractSessionContext)session.getSessionContext();
        if (context instanceof ClientSessionContext) {
            return new Impl(context, ref, session.getPeerHost(), session.getPeerPort(), session.getPeerCertificates(), NativeSslSession.getOcspResponse(session), session.getPeerSignedCertificateTimestamp());
        }
        return new Impl(context, ref, null, -1, null, null, null);
    }

    private static byte[] getOcspResponse(ConscryptSession session) {
        List<byte[]> ocspResponseList = session.getStatusResponses();
        if (ocspResponseList.size() >= 1) {
            return ocspResponseList.get(0);
        }
        return null;
    }

    static NativeSslSession newInstance(AbstractSessionContext context, byte[] data, String host2, int port) {
        ByteBuffer buf = ByteBuffer.wrap(data);
        try {
            int type = buf.getInt();
            if (!SSLUtils.SessionType.isSupportedType(type)) {
                throw new IOException("Unexpected type ID: " + type);
            }
            int length = buf.getInt();
            NativeSslSession.checkRemaining(buf, length);
            byte[] sessionData = new byte[length];
            buf.get(sessionData);
            int count = buf.getInt();
            NativeSslSession.checkRemaining(buf, count);
            java.security.cert.X509Certificate[] peerCerts = new java.security.cert.X509Certificate[count];
            for (int i = 0; i < count; ++i) {
                length = buf.getInt();
                NativeSslSession.checkRemaining(buf, length);
                byte[] certData = new byte[length];
                buf.get(certData);
                try {
                    peerCerts[i] = OpenSSLX509Certificate.fromX509Der(certData);
                    continue;
                }
                catch (Exception e) {
                    throw new IOException("Can not read certificate " + i + "/" + count);
                }
            }
            byte[] ocspData = null;
            if (type >= SSLUtils.SessionType.OPEN_SSL_WITH_OCSP.value) {
                int countOcspResponses = buf.getInt();
                NativeSslSession.checkRemaining(buf, countOcspResponses);
                if (countOcspResponses >= 1) {
                    int ocspLength = buf.getInt();
                    NativeSslSession.checkRemaining(buf, ocspLength);
                    ocspData = new byte[ocspLength];
                    buf.get(ocspData);
                    for (int i = 1; i < countOcspResponses; ++i) {
                        ocspLength = buf.getInt();
                        NativeSslSession.checkRemaining(buf, ocspLength);
                        buf.position(buf.position() + ocspLength);
                    }
                }
            }
            byte[] tlsSctData = null;
            if (type == SSLUtils.SessionType.OPEN_SSL_WITH_TLS_SCT.value) {
                int tlsSctDataLength = buf.getInt();
                NativeSslSession.checkRemaining(buf, tlsSctDataLength);
                if (tlsSctDataLength > 0) {
                    tlsSctData = new byte[tlsSctDataLength];
                    buf.get(tlsSctData);
                }
            }
            if (buf.remaining() != 0) {
                NativeSslSession.log((Throwable)((Object)new AssertionError((Object)"Read entire session, but data still remains; rejecting")));
                return null;
            }
            NativeRef.SSL_SESSION ref = new NativeRef.SSL_SESSION(NativeCrypto.d2i_SSL_SESSION(sessionData));
            return new Impl(context, ref, host2, port, peerCerts, ocspData, tlsSctData);
        }
        catch (IOException e) {
            NativeSslSession.log(e);
            return null;
        }
        catch (BufferUnderflowException e) {
            NativeSslSession.log(e);
            return null;
        }
    }

    abstract byte[] getId();

    abstract boolean isValid();

    abstract boolean isSingleUse();

    abstract void offerToResume(NativeSsl var1) throws SSLException;

    abstract String getCipherSuite();

    abstract String getProtocol();

    abstract String getPeerHost();

    abstract int getPeerPort();

    abstract byte[] getPeerOcspStapledResponse();

    abstract byte[] getPeerSignedCertificateTimestamp();

    abstract byte[] toBytes();

    abstract SSLSession toSSLSession();

    private static void log(Throwable t2) {
        logger.log(Level.INFO, "Error inflating SSL session: {0}", t2.getMessage() != null ? t2.getMessage() : t2.getClass().getName());
    }

    private static void checkRemaining(ByteBuffer buf, int length) throws IOException {
        if (length < 0) {
            throw new IOException("Length is negative: " + length);
        }
        if (length > buf.remaining()) {
            throw new IOException("Length of blob is longer than available: " + length + " > " + buf.remaining());
        }
    }

    private static final class Impl
    extends NativeSslSession {
        private final NativeRef.SSL_SESSION ref;
        private final AbstractSessionContext context;
        private final String host;
        private final int port;
        private final String protocol;
        private final String cipherSuite;
        private final java.security.cert.X509Certificate[] peerCertificates;
        private final byte[] peerOcspStapledResponse;
        private final byte[] peerSignedCertificateTimestamp;

        private Impl(AbstractSessionContext context, NativeRef.SSL_SESSION ref, String host2, int port, java.security.cert.X509Certificate[] peerCertificates2, byte[] peerOcspStapledResponse, byte[] peerSignedCertificateTimestamp) {
            this.context = context;
            this.host = host2;
            this.port = port;
            this.peerCertificates = peerCertificates2;
            this.peerOcspStapledResponse = peerOcspStapledResponse;
            this.peerSignedCertificateTimestamp = peerSignedCertificateTimestamp;
            this.protocol = NativeCrypto.SSL_SESSION_get_version(ref.address);
            this.cipherSuite = NativeCrypto.cipherSuiteToJava(NativeCrypto.SSL_SESSION_cipher(ref.address));
            this.ref = ref;
        }

        @Override
        byte[] getId() {
            return NativeCrypto.SSL_SESSION_session_id(this.ref.address);
        }

        private long getCreationTime() {
            return NativeCrypto.SSL_SESSION_get_time(this.ref.address);
        }

        @Override
        boolean isValid() {
            long creationTimeMillis = this.getCreationTime();
            long timeoutMillis = Math.max(0L, Math.min((long)this.context.getSessionTimeout(), NativeCrypto.SSL_SESSION_get_timeout(this.ref.address))) * 1000L;
            return System.currentTimeMillis() - timeoutMillis < creationTimeMillis;
        }

        @Override
        boolean isSingleUse() {
            return NativeCrypto.SSL_SESSION_should_be_single_use(this.ref.address);
        }

        @Override
        void offerToResume(NativeSsl ssl) throws SSLException {
            ssl.offerToResumeSession(this.ref.address);
        }

        @Override
        String getCipherSuite() {
            return this.cipherSuite;
        }

        @Override
        String getProtocol() {
            return this.protocol;
        }

        @Override
        String getPeerHost() {
            return this.host;
        }

        @Override
        int getPeerPort() {
            return this.port;
        }

        @Override
        byte[] getPeerOcspStapledResponse() {
            return this.peerOcspStapledResponse;
        }

        @Override
        byte[] getPeerSignedCertificateTimestamp() {
            return this.peerSignedCertificateTimestamp;
        }

        @Override
        byte[] toBytes() {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream daos = new DataOutputStream(baos);
                daos.writeInt(SSLUtils.SessionType.OPEN_SSL_WITH_TLS_SCT.value);
                byte[] data = NativeCrypto.i2d_SSL_SESSION(this.ref.address);
                daos.writeInt(data.length);
                daos.write(data);
                daos.writeInt(this.peerCertificates.length);
                for (java.security.cert.X509Certificate cert : this.peerCertificates) {
                    data = cert.getEncoded();
                    daos.writeInt(data.length);
                    daos.write(data);
                }
                if (this.peerOcspStapledResponse != null) {
                    daos.writeInt(1);
                    daos.writeInt(this.peerOcspStapledResponse.length);
                    daos.write(this.peerOcspStapledResponse);
                } else {
                    daos.writeInt(0);
                }
                if (this.peerSignedCertificateTimestamp != null) {
                    daos.writeInt(this.peerSignedCertificateTimestamp.length);
                    daos.write(this.peerSignedCertificateTimestamp);
                } else {
                    daos.writeInt(0);
                }
                return baos.toByteArray();
            }
            catch (IOException e) {
                logger.log(Level.WARNING, "Failed to convert saved SSL Session: ", e);
                return null;
            }
            catch (CertificateEncodingException e) {
                NativeSslSession.log(e);
                return null;
            }
        }

        @Override
        SSLSession toSSLSession() {
            return new SSLSession(){

                @Override
                public byte[] getId() {
                    return Impl.this.getId();
                }

                @Override
                public String getCipherSuite() {
                    return Impl.this.getCipherSuite();
                }

                @Override
                public String getProtocol() {
                    return Impl.this.getProtocol();
                }

                @Override
                public String getPeerHost() {
                    return Impl.this.getPeerHost();
                }

                @Override
                public int getPeerPort() {
                    return Impl.this.getPeerPort();
                }

                @Override
                public long getCreationTime() {
                    return Impl.this.getCreationTime();
                }

                @Override
                public boolean isValid() {
                    return Impl.this.isValid();
                }

                @Override
                public SSLSessionContext getSessionContext() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public long getLastAccessedTime() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void invalidate() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void putValue(String s2, Object o) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Object getValue(String s2) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void removeValue(String s2) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String[] getValueNames() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Certificate[] getLocalCertificates() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Principal getLocalPrincipal() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int getPacketBufferSize() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int getApplicationBufferSize() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
}

