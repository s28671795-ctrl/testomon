/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.asn1.types.primitive.ASN1ObjectIdentifier;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.mssmb2.messages.SMB2SessionSetup;
import com.hierynomus.protocol.commons.Factory;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.security.DerivationFunction;
import com.hierynomus.security.MessageDigest;
import com.hierynomus.security.SecurityException;
import com.hierynomus.security.jce.derivationfunction.CounterDerivationParameters;
import com.hierynomus.smb.Packets;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticateResponse;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.auth.Authenticator;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.connection.ConnectionContext;
import com.hierynomus.smbj.connection.SessionTable;
import com.hierynomus.smbj.session.SMB2GuestSigningRequiredException;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.session.SessionContext;
import com.hierynomus.smbj.utils.DigestUtil;
import com.hierynomus.spnego.NegTokenInit;
import com.hierynomus.spnego.NegTokenInit2;
import com.hierynomus.spnego.SpnegoException;
import com.hierynomus.utils.Strings;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMBSessionBuilder {
    static final byte[] KDF_ENC_LABEL_SMB311 = Strings.nullTerminatedBytes("SMBC2SCipherKey");
    static final byte[] KDF_DEC_LABEL_SMB311 = Strings.nullTerminatedBytes("SMBS2CCipherKey");
    static final byte[] KDF_ENCDEC_LABEL = Strings.nullTerminatedBytes("SMB2AESCCM");
    static final byte[] KDF_ENC_CONTEXT = Strings.nullTerminatedBytes("ServerIn ");
    static final byte[] KDF_DEC_CONTEXT = Strings.nullTerminatedBytes("ServerOut");
    static final byte[] KDF_SIGN_CONTEXT = Strings.nullTerminatedBytes("SmbSign");
    static final byte[] KDF_SIGN_LABEL = Strings.nullTerminatedBytes("SMB2AESCMAC");
    static final byte[] KDF_SIGN_LABEL_SMB311 = Strings.nullTerminatedBytes("SMBSigningKey");
    static final byte[] KDF_APP_CONTEXT = Strings.nullTerminatedBytes("SmbRpc");
    static final byte[] KDF_APP_LABEL = Strings.nullTerminatedBytes("SMB2APP");
    static final byte[] KDF_APP_LABEL_SMB311 = Strings.nullTerminatedBytes("SMBAppKey");
    static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    static final String AES_128_CMAC_ALGORITHM = "AesCmac";
    private static final Logger logger = LoggerFactory.getLogger(SMBSessionBuilder.class);
    private final SmbConfig config;
    private final ConnectionContext connectionContext;
    private final SessionFactory sessionFactory;
    private final SessionTable sessionTable;
    private final SessionTable preauthSessionTable;
    private final Connection connection;

    public SMBSessionBuilder(Connection connection, SmbConfig config, SessionFactory sessionFactory) {
        this.connection = connection;
        this.config = config;
        this.connectionContext = connection.getConnectionContext();
        this.sessionTable = connection.getSessionTable();
        this.preauthSessionTable = connection.getPreauthSessionTable();
        this.sessionFactory = sessionFactory;
    }

    public Session establish(AuthenticationContext authContext) {
        try {
            Authenticator authenticator = this.getAuthenticator(authContext);
            BuilderContext ctx = this.newContext(authContext, authenticator);
            authenticator.init(this.config);
            this.processAuthenticationToken(ctx, this.connectionContext.getGssNegotiateToken());
            Session session = this.setupSession(ctx);
            logger.info("Successfully authenticated {} on {}, session is {}", authContext.getUsername(), this.connection.getRemoteHostname(), session.getSessionId());
            this.sessionTable.registerSession(session.getSessionId(), session);
            return session;
        }
        catch (SpnegoException | IOException e) {
            throw new SMBRuntimeException(e);
        }
    }

    private BuilderContext newContext(AuthenticationContext authContext, Authenticator authenticator) {
        BuilderContext ctx = new BuilderContext();
        ctx.authenticator = authenticator;
        ctx.authContext = authContext;
        return ctx;
    }

    private Session setupSession(BuilderContext ctx) throws IOException {
        this.initiateSessionSetup(ctx, ctx.securityContext);
        SMB2SessionSetup response = ctx.response;
        ctx.sessionId = ((SMB2PacketHeader)response.getHeader()).getSessionId();
        SMB2Dialect dialect = this.connectionContext.getNegotiatedProtocol().getDialect();
        if (((SMB2PacketHeader)response.getHeader()).getStatusCode() == NtStatus.STATUS_MORE_PROCESSING_REQUIRED.getValue()) {
            if (dialect == SMB2Dialect.SMB_3_1_1) {
                Session preauthSession = this.preauthSessionTable.find(ctx.sessionId);
                if (preauthSession == null) {
                    preauthSession = this.newSession(ctx);
                    this.preauthSessionTable.registerSession(ctx.sessionId, preauthSession);
                }
                this.updatePreauthIntegrityValue(ctx, preauthSession.getSessionContext(), ctx.request);
                this.updatePreauthIntegrityValue(ctx, preauthSession.getSessionContext(), ctx.response);
            }
            logger.debug("More processing required for authentication of {} using {}", (Object)ctx.authContext.getUsername(), (Object)ctx.authenticator);
            this.processAuthenticationToken(ctx, response.getSecurityBuffer());
            return this.setupSession(ctx);
        }
        if (((SMB2PacketHeader)response.getHeader()).getStatusCode() != NtStatus.STATUS_SUCCESS.getValue()) {
            throw new SMBApiException((SMB2PacketHeader)response.getHeader(), String.format("Authentication failed for '%s' using %s", ctx.authContext.getUsername(), ctx.authenticator));
        }
        Session session = this.preauthSessionTable.find(ctx.sessionId);
        if (dialect == SMB2Dialect.SMB_3_1_1 && session != null) {
            this.preauthSessionTable.removeSession(session.getSessionId());
        } else {
            session = this.newSession(ctx);
        }
        SessionContext context = session.getSessionContext();
        this.processAuthenticationToken(ctx, response.getSecurityBuffer());
        context.setSessionKey(new SecretKeySpec(ctx.sessionKey, HMAC_SHA256_ALGORITHM));
        if (dialect == SMB2Dialect.SMB_3_1_1) {
            this.updatePreauthIntegrityValue(ctx, context, ctx.request);
        }
        this.validateAndSetSigning(ctx, context);
        this.deriveKeys(response, dialect, context);
        context.established(response);
        return session;
    }

    private Session newSession(BuilderContext ctx) {
        Session preauthSession = this.sessionFactory.createSession(ctx.authContext);
        preauthSession.setSessionId(ctx.sessionId);
        preauthSession.getSessionContext().setPreauthIntegrityHashValue(this.connectionContext.getPreauthIntegrityHashValue());
        return preauthSession;
    }

    private void processAuthenticationToken(BuilderContext ctx, byte[] inputToken) throws IOException {
        AuthenticateResponse resp = ctx.authenticator.authenticate(ctx.authContext, inputToken, this.connectionContext);
        if (resp == null) {
            return;
        }
        this.connectionContext.setWindowsVersion(resp.getWindowsVersion());
        this.connectionContext.setNetBiosName(resp.getNetBiosName());
        BuilderContext.access$602(ctx, resp.getSessionKey());
        BuilderContext.access$202(ctx, resp.getNegToken());
    }

    private BuilderContext initiateSessionSetup(BuilderContext ctx, byte[] securityContext) throws TransportException {
        SMB2SessionSetup req = new SMB2SessionSetup(this.connectionContext.getNegotiatedProtocol().getDialect(), this.connectionContext.isServerRequiresSigning() ? EnumSet.of(SMB2SessionSetup.SMB2SecurityMode.SMB2_NEGOTIATE_SIGNING_REQUIRED) : EnumSet.of(SMB2SessionSetup.SMB2SecurityMode.SMB2_NEGOTIATE_SIGNING_ENABLED), this.connectionContext.getClientCapabilities());
        req.setSecurityBuffer(securityContext);
        ((SMB2PacketHeader)req.getHeader()).setSessionId(ctx.sessionId);
        ctx.request = req;
        ctx.response = (SMB2SessionSetup)this.connection.sendAndReceive(req);
        return ctx;
    }

    private Authenticator getAuthenticator(AuthenticationContext context) throws SpnegoException {
        ArrayList<Factory.Named<Authenticator>> supportedAuthenticators = new ArrayList<Factory.Named<Authenticator>>(this.config.getSupportedAuthenticators());
        List<Object> mechTypes = new ArrayList();
        if (this.connectionContext.getGssNegotiateToken().length > 0) {
            NegTokenInit negTokenInit = new NegTokenInit2().read(this.connectionContext.getGssNegotiateToken());
            mechTypes = negTokenInit.getSupportedMechTypes();
        }
        for (Factory.Named<Authenticator> factory2 : new ArrayList<Factory.Named<Authenticator>>(supportedAuthenticators)) {
            Authenticator authenticator;
            if (!mechTypes.isEmpty() && !mechTypes.contains(new ASN1ObjectIdentifier(factory2.getName())) || !(authenticator = (Authenticator)factory2.create()).supports(context)) continue;
            return authenticator;
        }
        throw new SMBRuntimeException("Could not find a configured authenticator for mechtypes: " + mechTypes + " and authentication context: " + context);
    }

    private void validateAndSetSigning(BuilderContext ctx, SessionContext context) {
        boolean guest;
        boolean requireMessageSigning = this.config.isSigningRequired();
        boolean connectionSigningRequired = this.connection.getConnectionContext().isServerRequiresSigning();
        context.setSigningRequired(requireMessageSigning || connectionSigningRequired);
        if (ctx.response.getSessionFlags().contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_IS_NULL)) {
            context.setSigningRequired(false);
        }
        if ((guest = ctx.response.getSessionFlags().contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_IS_GUEST)) && context.isSigningRequired()) {
            throw new SMB2GuestSigningRequiredException();
        }
        if (guest && !requireMessageSigning) {
            context.setSigningRequired(false);
        }
        if (this.connection.getNegotiatedProtocol().getDialect().isSmb3x() && this.connection.getConnectionContext().supportsEncryption() && ctx.response.getSessionFlags().contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_ENCRYPT_DATA)) {
            context.setEncryptData(true);
            context.setSigningRequired(false);
        }
    }

    private void updatePreauthIntegrityValue(BuilderContext ctx, SessionContext sessionContext, SMB2Packet packet) {
        if (ctx.digest == null) {
            String algorithmName = this.connection.getConnectionContext().getPreauthIntegrityHashId().getAlgorithmName();
            try {
                ctx.digest = this.config.getSecurityProvider().getDigest(algorithmName);
            }
            catch (SecurityException se) {
                throw new SMBRuntimeException("Cannot get the message digest for " + algorithmName, se);
            }
        }
        sessionContext.setPreauthIntegrityHashValue(DigestUtil.digest(ctx.digest, sessionContext.getPreauthIntegrityHashValue(), Packets.getPacketBytes(packet)));
    }

    private void deriveKeys(SMB2SessionSetup response, SMB2Dialect dialect, SessionContext context) {
        if (dialect.isSmb3x() && !response.getSessionFlags().contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_IS_NULL) && !response.getSessionFlags().contains(SMB2SessionSetup.SMB2SessionFlags.SMB2_SESSION_FLAG_IS_GUEST)) {
            if (dialect == SMB2Dialect.SMB_3_1_1) {
                context.setSigningKey(this.deriveKey(context.getSessionKey(), KDF_SIGN_LABEL_SMB311, context.getPreauthIntegrityHashValue(), AES_128_CMAC_ALGORITHM));
            } else {
                context.setSigningKey(this.deriveKey(context.getSessionKey(), KDF_SIGN_LABEL, KDF_SIGN_CONTEXT, AES_128_CMAC_ALGORITHM));
            }
            if (this.connectionContext.supportsEncryption()) {
                String alg = this.connectionContext.getCipherId().getAlgorithmName();
                if (dialect == SMB2Dialect.SMB_3_1_1) {
                    context.setEncryptionKey(this.deriveKey(context.getSessionKey(), KDF_ENC_LABEL_SMB311, context.getPreauthIntegrityHashValue(), alg));
                    context.setDecryptionKey(this.deriveKey(context.getSessionKey(), KDF_DEC_LABEL_SMB311, context.getPreauthIntegrityHashValue(), alg));
                    context.setApplicationKey(this.deriveKey(context.getSessionKey(), KDF_APP_LABEL_SMB311, context.getPreauthIntegrityHashValue(), alg));
                } else {
                    context.setEncryptionKey(this.deriveKey(context.getSessionKey(), KDF_ENCDEC_LABEL, KDF_ENC_CONTEXT, alg));
                    context.setDecryptionKey(this.deriveKey(context.getSessionKey(), KDF_ENCDEC_LABEL, KDF_DEC_CONTEXT, alg));
                    context.setApplicationKey(this.deriveKey(context.getSessionKey(), KDF_APP_LABEL, KDF_APP_CONTEXT, alg));
                }
            }
        }
    }

    private SecretKey deriveKey(SecretKey derivationKey, byte[] label, byte[] context, String algorithm) {
        ByteArrayOutputStream fixedSuffixTemp = new ByteArrayOutputStream(25);
        try {
            fixedSuffixTemp.write(label);
            fixedSuffixTemp.write(0);
            fixedSuffixTemp.write(context);
            fixedSuffixTemp.write(new byte[]{0, 0, 0, -128});
        }
        catch (IOException e) {
            logger.error("Unable to format suffix, error occur : ", e);
            return null;
        }
        try {
            DerivationFunction kdf = this.config.getSecurityProvider().getDerivationFunction("KDF/Counter/HMACSHA256");
            byte[] fixedSuffix = fixedSuffixTemp.toByteArray();
            kdf.init(new CounterDerivationParameters(derivationKey.getEncoded(), fixedSuffix, 32));
            byte[] derived = new byte[16];
            kdf.generateBytes(derived, 0, derived.length);
            return new SecretKeySpec(derived, algorithm);
        }
        catch (SecurityException se) {
            throw new SMBRuntimeException(se);
        }
    }

    public static class BuilderContext {
        private Authenticator authenticator;
        private long sessionId;
        private byte[] sessionKey;
        private AuthenticationContext authContext;
        private byte[] securityContext;
        private SMB2SessionSetup request;
        private SMB2SessionSetup response;
        private MessageDigest digest;

        static /* synthetic */ byte[] access$602(BuilderContext x0, byte[] x1) {
            x0.sessionKey = x1;
            return x1;
        }

        static /* synthetic */ byte[] access$202(BuilderContext x0, byte[] x1) {
            x0.securityContext = x1;
            return x1;
        }
    }

    public static interface SessionFactory {
        public Session createSession(AuthenticationContext var1);
    }
}

