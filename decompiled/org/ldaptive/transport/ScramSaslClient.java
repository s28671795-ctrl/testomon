/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.ldaptive.BindResponse;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.ResultCode;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.SaslBindRequest;
import org.ldaptive.sasl.SaslClient;
import org.ldaptive.sasl.ScramBindRequest;
import org.ldaptive.transport.TransportConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScramSaslClient
implements SaslClient<ScramBindRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScramSaslClient.class);

    @Override
    public BindResponse bind(TransportConnection conn, ScramBindRequest request) throws LdapException {
        ClientFirstMessage clientFirstMessage = new ClientFirstMessage(request.getUsername(), request.getNonce());
        BindResponse serverFirstResult = conn.operation(new SaslBindRequest(request.getMechanism().mechanism(), LdapUtils.utf8Encode(clientFirstMessage.encode(), false))).execute();
        if (serverFirstResult.getResultCode() != ResultCode.SASL_BIND_IN_PROGRESS) {
            if (serverFirstResult.isSuccess()) {
                throw new IllegalStateException("Unexpected success result from SCRAM SASL bind: " + serverFirstResult.getResultCode());
            }
            LOGGER.warn("Unexpected server result {}", (Object)serverFirstResult);
            return serverFirstResult;
        }
        ClientFinalMessage clientFinalMessage = new ClientFinalMessage(request.getMechanism(), request.getPassword(), clientFirstMessage, new ServerFirstMessage(clientFirstMessage, serverFirstResult));
        BindResponse serverFinalResult = conn.operation(new SaslBindRequest(request.getMechanism().mechanism(), LdapUtils.utf8Encode(clientFinalMessage.encode(), false))).execute();
        ServerFinalMessage serverFinalMessage = new ServerFinalMessage(request.getMechanism(), clientFinalMessage, serverFinalResult);
        if (!serverFinalResult.isSuccess() && serverFinalMessage.isVerified()) {
            throw new IllegalStateException("Verified server message but result was not a success");
        }
        if (serverFinalResult.isSuccess() && !serverFinalMessage.isVerified()) {
            throw new IllegalStateException("Received success from server but message could not be verified");
        }
        return serverFinalResult;
    }

    private static Mac createMac(String algorithm, byte[] key) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key, algorithm));
            return mac;
        }
        catch (Exception e) {
            throw new IllegalStateException("Could not create MAC", e);
        }
    }

    private static byte[] createDigest(String algorithm, byte[] data) {
        try {
            return MessageDigest.getInstance(algorithm).digest(data);
        }
        catch (Exception e) {
            throw new IllegalStateException("Could not create digest", e);
        }
    }

    static class ServerFinalMessage {
        private static final byte[] SERVER_KEY_INIT = LdapUtils.utf8Encode("Server Key");
        private final String message;
        private final boolean verified;

        ServerFinalMessage(Mechanism mech, ClientFinalMessage clientFinalMessage, BindResponse result) {
            if (result.getServerSaslCreds() == null || result.getServerSaslCreds().length == 0) {
                throw new IllegalArgumentException("Bind response missing server SASL credentials");
            }
            this.message = LdapUtils.utf8Encode(result.getServerSaslCreds(), false);
            Map<String, String> attributes = Stream.of(this.message.split(",")).map(s2 -> s2.split("=", 2)).collect(Collectors.toMap(attr -> attr[0], attr -> attr[1]));
            String e = attributes.get("e");
            if (e != null) {
                LOGGER.warn("SASL bind server final message included error: {}", (Object)e);
            }
            if (result.getResultCode() != ResultCode.SUCCESS) {
                this.verified = false;
            } else {
                String serverSignature = attributes.get("v");
                if (serverSignature == null) {
                    throw new IllegalArgumentException("Invalid SASL credentials, missing server verification");
                }
                byte[] serverKey = ScramSaslClient.createMac(mech.properties()[1], clientFinalMessage.getSaltedPassword()).doFinal(SERVER_KEY_INIT);
                String expectedServerSignature = LdapUtils.base64Encode(ScramSaslClient.createMac(mech.properties()[1], serverKey).doFinal(LdapUtils.utf8Encode(clientFinalMessage.getMessage(), false)));
                if (!expectedServerSignature.equals(serverSignature)) {
                    throw new IllegalArgumentException("Invalid SASL credentials, incorrect server verification");
                }
                this.verified = true;
            }
        }

        public boolean isVerified() {
            return this.verified;
        }
    }

    static class ServerFirstMessage {
        private static final int MINIMUM_ITERATION_COUNT = 4096;
        private final String message;
        private final String combinedNonce;
        private final byte[] salt;
        private final int iterations;

        ServerFirstMessage(ClientFirstMessage clientFirstMessage, BindResponse result) {
            if (result.getServerSaslCreds() == null || result.getServerSaslCreds().length == 0) {
                throw new IllegalArgumentException("Bind response missing server SASL credentials");
            }
            this.message = LdapUtils.utf8Encode(result.getServerSaslCreds(), false);
            Map<String, String> attributes = Stream.of(this.message.split(",")).map(s2 -> s2.split("=", 2)).collect(Collectors.toMap(attr -> attr[0], attr -> attr[1]));
            String r = attributes.get("r");
            if (r == null) {
                throw new IllegalArgumentException("Invalid SASL credentials, missing server nonce");
            }
            if (!r.startsWith(clientFirstMessage.getNonce())) {
                throw new IllegalArgumentException("Invalid SASL credentials, missing client nonce");
            }
            this.combinedNonce = r;
            String s3 = attributes.get("s");
            if (s3 == null) {
                throw new IllegalArgumentException("Invalid SASL credentials, missing server salt");
            }
            this.salt = LdapUtils.base64Decode(s3);
            String i = attributes.get("i");
            this.iterations = Integer.parseInt(i);
            if (this.iterations < 4096) {
                throw new IllegalArgumentException("Invalid SASL credentials, iterations minimum value is 4096");
            }
        }

        public String getMessage() {
            return this.message;
        }

        public String getCombinedNonce() {
            return this.combinedNonce;
        }

        public byte[] getSalt() {
            return this.salt;
        }

        public int getIterations() {
            return this.iterations;
        }
    }

    static class ClientFinalMessage {
        private static final String GS2_NO_CHANNEL_BINDING = LdapUtils.base64Encode("n,,");
        private static final byte[] INTEGER_ONE = new byte[]{0, 0, 0, 1};
        private static final byte[] CLIENT_KEY_INIT = LdapUtils.utf8Encode("Client Key");
        private final Mechanism mechanism;
        private final String withoutProof;
        private final String message;
        private final byte[] saltedPassword;

        ClientFinalMessage(Mechanism mech, String password, ClientFirstMessage clientFirstMessage, ServerFirstMessage serverFirstMessage) {
            this.mechanism = mech;
            this.saltedPassword = ClientFinalMessage.createSaltedPassword(this.mechanism.properties()[1], password, serverFirstMessage.getSalt(), serverFirstMessage.getIterations());
            this.withoutProof = "c=".concat(GS2_NO_CHANNEL_BINDING).concat(",").concat("r=").concat(serverFirstMessage.getCombinedNonce());
            this.message = clientFirstMessage.getMessage().concat(",").concat(serverFirstMessage.getMessage()).concat(",").concat(this.withoutProof);
        }

        public byte[] getSaltedPassword() {
            return this.saltedPassword;
        }

        public String getMessage() {
            return this.message;
        }

        public String encode() {
            byte[] clientKey = ScramSaslClient.createMac(this.mechanism.properties()[1], this.saltedPassword).doFinal(CLIENT_KEY_INIT);
            byte[] storedKey = ScramSaslClient.createDigest(this.mechanism.properties()[0], clientKey);
            byte[] clientSignature = ScramSaslClient.createMac(this.mechanism.properties()[1], storedKey).doFinal(LdapUtils.utf8Encode(this.message, false));
            byte[] clientProof = new byte[clientKey.length];
            for (int i = 0; i < clientProof.length; ++i) {
                clientProof[i] = (byte)(clientKey[i] ^ clientSignature[i]);
            }
            return this.withoutProof.concat(",p=").concat(LdapUtils.base64Encode(clientProof));
        }

        private static byte[] createSaltedPassword(String algorithm, String password, byte[] salt, int iterations) {
            Mac mac = ScramSaslClient.createMac(algorithm, LdapUtils.utf8Encode(password, false));
            byte[] bytes = Arrays.copyOf(salt, salt.length + INTEGER_ONE.length);
            System.arraycopy(INTEGER_ONE, 0, bytes, salt.length, INTEGER_ONE.length);
            byte[] xor = bytes = mac.doFinal(bytes);
            for (int i = 1; i < iterations; ++i) {
                byte[] macResult = mac.doFinal(bytes);
                for (int j = 0; j < macResult.length; ++j) {
                    int n = j;
                    xor[n] = (byte)(xor[n] ^ macResult[j]);
                }
                bytes = macResult;
            }
            return xor;
        }
    }

    static class ClientFirstMessage {
        private static final String GS2_NO_CHANNEL_BINDING = "n,,";
        private static final int DEFAULT_NONCE_SIZE = 16;
        private final String clientUsername;
        private final String clientNonce;
        private final String message;

        ClientFirstMessage(String username, byte[] nonce) {
            this.clientUsername = username;
            if (nonce == null) {
                SecureRandom random = new SecureRandom();
                byte[] b = new byte[16];
                random.nextBytes(b);
                this.clientNonce = LdapUtils.base64Encode(b);
            } else {
                this.clientNonce = LdapUtils.base64Encode(nonce);
            }
            this.message = "n=".concat(this.clientUsername).concat(",").concat("r=").concat(this.clientNonce);
        }

        public String getNonce() {
            return this.clientNonce;
        }

        public String getMessage() {
            return this.message;
        }

        public String encode() {
            return GS2_NO_CHANNEL_BINDING.concat(this.message);
        }
    }
}

