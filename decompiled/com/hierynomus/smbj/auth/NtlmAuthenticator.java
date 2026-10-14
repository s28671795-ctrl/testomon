/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.auth;

import com.hierynomus.asn1.types.primitive.ASN1ObjectIdentifier;
import com.hierynomus.ntlm.functions.NtlmFunctions;
import com.hierynomus.ntlm.messages.AvId;
import com.hierynomus.ntlm.messages.NtlmAuthenticate;
import com.hierynomus.ntlm.messages.NtlmChallenge;
import com.hierynomus.ntlm.messages.NtlmNegotiate;
import com.hierynomus.ntlm.messages.NtlmNegotiateFlag;
import com.hierynomus.ntlm.messages.TargetInfo;
import com.hierynomus.protocol.commons.ByteArrayUtils;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.Factory;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.commons.buffer.Endian;
import com.hierynomus.security.SecurityProvider;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticateResponse;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.auth.Authenticator;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.connection.ConnectionContext;
import com.hierynomus.spnego.NegTokenInit;
import com.hierynomus.spnego.NegTokenTarg;
import com.hierynomus.spnego.SpnegoException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NtlmAuthenticator
implements Authenticator {
    private static final Logger logger = LoggerFactory.getLogger(NtlmAuthenticator.class);
    private static final ASN1ObjectIdentifier NTLMSSP = new ASN1ObjectIdentifier("1.3.6.1.4.1.311.2.2.10");
    private SecurityProvider securityProvider;
    private Random random;
    private String workStationName;
    private boolean initialized = false;
    private boolean completed = false;

    @Override
    public AuthenticateResponse authenticate(AuthenticationContext context, byte[] gssToken, ConnectionContext connectionContext) throws IOException {
        try {
            byte[] sessionkey;
            AuthenticateResponse response = new AuthenticateResponse();
            if (this.completed) {
                return null;
            }
            if (!this.initialized) {
                logger.debug("Initialized Authentication of {} using NTLM", (Object)context.getUsername());
                NtlmNegotiate ntlmNegotiate = new NtlmNegotiate();
                this.initialized = true;
                response.setNegToken(this.negTokenInit(ntlmNegotiate));
                return response;
            }
            logger.debug("Received token: {}", (Object)ByteArrayUtils.printHex(gssToken));
            NtlmFunctions ntlmFunctions = new NtlmFunctions(this.random, this.securityProvider);
            NegTokenTarg negTokenTarg = new NegTokenTarg().read(gssToken);
            BigInteger negotiationResult = negTokenTarg.getNegotiationResult();
            NtlmChallenge serverNtlmChallenge = new NtlmChallenge();
            try {
                serverNtlmChallenge.read(new Buffer.PlainBuffer(negTokenTarg.getResponseToken(), Endian.LE));
            }
            catch (Buffer.BufferException e) {
                throw new IOException(e);
            }
            logger.debug("Received NTLM challenge from: {}", (Object)serverNtlmChallenge.getTargetName());
            response.setWindowsVersion(serverNtlmChallenge.getVersion());
            response.setNetBiosName(serverNtlmChallenge.getTargetInfo().getAvPairString(AvId.MsvAvNbComputerName));
            byte[] serverChallenge = serverNtlmChallenge.getServerChallenge();
            byte[] responseKeyNT = ntlmFunctions.NTOWFv2(String.valueOf(context.getPassword()), context.getUsername(), context.getDomain());
            TargetInfo clientTargetInfo = serverNtlmChallenge.getTargetInfo().copy();
            EnumSet<NtlmNegotiateFlag> negotiateFlags = serverNtlmChallenge.getNegotiateFlags();
            if (negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_REQUEST_TARGET)) {
                clientTargetInfo.putAvPairString(AvId.MsvAvTargetName, String.format("cifs/%s", clientTargetInfo.getAvPairString(AvId.MsvAvDnsComputerName)));
            }
            byte[] ntlmv2ClientChallenge = ntlmFunctions.getNTLMv2ClientChallenge(clientTargetInfo);
            byte[] ntlmv2Response = ntlmFunctions.getNTLMv2Response(responseKeyNT, serverChallenge, ntlmv2ClientChallenge);
            byte[] userSessionKey = ntlmFunctions.hmac_md5(responseKeyNT, new byte[][]{Arrays.copyOfRange(ntlmv2Response, 0, 16)});
            if (negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_NEGOTIATE_KEY_EXCH) && (negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_NEGOTIATE_SIGN) || negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_NEGOTIATE_SEAL) || negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_NEGOTIATE_ALWAYS_SIGN))) {
                byte[] masterKey = new byte[16];
                this.random.nextBytes(masterKey);
                sessionkey = ntlmFunctions.encryptRc4(userSessionKey, masterKey);
                response.setSessionKey(masterKey);
            } else {
                sessionkey = userSessionKey;
                response.setSessionKey(sessionkey);
            }
            this.completed = true;
            Object msAvTimestamp = serverNtlmChallenge.getTargetInfo().getAvPairObject(AvId.MsvAvTimestamp);
            if (msAvTimestamp != null) {
                NtlmAuthenticate resp = new NtlmAuthenticate(new byte[0], ntlmv2Response, context.getUsername(), context.getDomain(), this.workStationName, sessionkey, EnumWithValue.EnumUtils.toLong(negotiateFlags), true);
                Buffer.PlainBuffer concatenatedBuffer = new Buffer.PlainBuffer(Endian.LE);
                concatenatedBuffer.putRawBytes(negTokenTarg.getResponseToken());
                concatenatedBuffer.putRawBytes(serverNtlmChallenge.getServerChallenge());
                resp.writeAutentificateMessage(concatenatedBuffer);
                byte[] mic = ntlmFunctions.hmac_md5(userSessionKey, new byte[][]{concatenatedBuffer.getCompactData()});
                resp.setMic(mic);
                response.setNegToken(this.negTokenTarg(resp));
                return response;
            }
            NtlmAuthenticate resp = new NtlmAuthenticate(new byte[0], ntlmv2Response, context.getUsername(), context.getDomain(), this.workStationName, sessionkey, EnumWithValue.EnumUtils.toLong(negotiateFlags), false);
            response.setNegToken(this.negTokenTarg(resp));
            return response;
        }
        catch (SpnegoException spne) {
            throw new SMBRuntimeException(spne);
        }
    }

    private byte[] negTokenInit(NtlmNegotiate ntlmNegotiate) throws SpnegoException {
        NegTokenInit negTokenInit = new NegTokenInit();
        negTokenInit.addSupportedMech(NTLMSSP);
        Buffer.PlainBuffer ntlmBuffer = new Buffer.PlainBuffer(Endian.LE);
        ntlmNegotiate.write(ntlmBuffer);
        negTokenInit.setMechToken(ntlmBuffer.getCompactData());
        Buffer.PlainBuffer negTokenBuffer = new Buffer.PlainBuffer(Endian.LE);
        negTokenInit.write(negTokenBuffer);
        return negTokenBuffer.getCompactData();
    }

    private byte[] negTokenTarg(NtlmAuthenticate resp) throws SpnegoException {
        NegTokenTarg targ = new NegTokenTarg();
        Buffer.PlainBuffer ntlmBuffer = new Buffer.PlainBuffer(Endian.LE);
        resp.write(ntlmBuffer);
        targ.setResponseToken(ntlmBuffer.getCompactData());
        Buffer.PlainBuffer negTokenBuffer = new Buffer.PlainBuffer(Endian.LE);
        targ.write(negTokenBuffer);
        return negTokenBuffer.getCompactData();
    }

    @Override
    public void init(SmbConfig config) {
        this.securityProvider = config.getSecurityProvider();
        this.random = config.getRandomProvider();
        this.workStationName = config.getWorkStationName();
    }

    @Override
    public boolean supports(AuthenticationContext context) {
        return context.getClass().equals(AuthenticationContext.class);
    }

    public static class Factory
    implements Factory.Named<Authenticator> {
        @Override
        public String getName() {
            return NTLMSSP.getValue();
        }

        @Override
        public NtlmAuthenticator create() {
            return new NtlmAuthenticator();
        }
    }
}

