/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.crypto;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.crypto.Mac;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SigningUtil {
    private SigningUtil() {
    }

    @Nonnull
    public static byte[] sign(@Nonnull Credential signingCredential, @Nonnull String jcaAlgorithmID, boolean isMAC, @Nonnull byte[] input) throws SecurityException {
        Logger log = SigningUtil.getLogger();
        Key signingKey = CredentialSupport.extractSigningKey(signingCredential);
        if (signingKey == null) {
            log.error("No signing key supplied in signing credential for signature computation");
            throw new SecurityException("No signing key supplied in signing credential");
        }
        if (isMAC) {
            return SigningUtil.signMAC(signingKey, jcaAlgorithmID, input);
        }
        if (signingKey instanceof PrivateKey) {
            return SigningUtil.sign((PrivateKey)signingKey, jcaAlgorithmID, input);
        }
        log.error("No PrivateKey present in signing credential for signature computation");
        throw new SecurityException("No PrivateKey supplied for signing");
    }

    @Nonnull
    public static byte[] sign(@Nonnull PrivateKey signingKey, @Nonnull String jcaAlgorithmID, @Nonnull byte[] input) throws SecurityException {
        Constraint.isNotNull(signingKey, "Private key cannot be null");
        Constraint.isNotNull(jcaAlgorithmID, "JCA algorithm ID cannot be null");
        Constraint.isNotNull(input, "Input data to sign cannot be null");
        Logger log = SigningUtil.getLogger();
        log.debug("Computing signature over input using private key of type {} and JCA algorithm ID {}", (Object)signingKey.getAlgorithm(), (Object)jcaAlgorithmID);
        try {
            Signature signature = Signature.getInstance(jcaAlgorithmID);
            signature.initSign(signingKey);
            signature.update(input);
            byte[] rawSignature = signature.sign();
            log.debug("Computed signature: {}", (Object)Hex.encodeHex(rawSignature));
            return rawSignature;
        }
        catch (GeneralSecurityException e) {
            log.error("Error during signature generation", e);
            throw new SecurityException("Error during signature generation", e);
        }
    }

    @Nonnull
    public static byte[] signMAC(@Nonnull Key signingKey, @Nonnull String jcaAlgorithmID, @Nonnull byte[] input) throws SecurityException {
        Constraint.isNotNull(signingKey, "Secret key cannot be null");
        Constraint.isNotNull(jcaAlgorithmID, "JCA algorithm ID cannot be null");
        Constraint.isNotNull(input, "Input data to sign cannot be null");
        Logger log = SigningUtil.getLogger();
        log.debug("Computing MAC over input using key of type {} and JCA algorithm ID {}", (Object)signingKey.getAlgorithm(), (Object)jcaAlgorithmID);
        try {
            Mac mac = Mac.getInstance(jcaAlgorithmID);
            mac.init(signingKey);
            mac.update(input);
            byte[] rawMAC = mac.doFinal();
            log.debug("Computed MAC: {}", (Object)Hex.encodeHexString(rawMAC));
            return rawMAC;
        }
        catch (GeneralSecurityException e) {
            log.error("Error during MAC generation", e);
            throw new SecurityException("Error during MAC generation", e);
        }
    }

    public static boolean verify(@Nonnull Credential verificationCredential, @Nonnull String jcaAlgorithmID, boolean isMAC, @Nonnull byte[] signature, @Nonnull byte[] input) throws SecurityException {
        Logger log = SigningUtil.getLogger();
        Key verificationKey = CredentialSupport.extractVerificationKey(verificationCredential);
        if (verificationKey == null) {
            log.error("No verification key supplied in verification credential for signature verification");
            throw new SecurityException("No verification key supplied in verification credential");
        }
        if (isMAC) {
            return SigningUtil.verifyMAC(verificationKey, jcaAlgorithmID, signature, input);
        }
        if (verificationKey instanceof PublicKey) {
            return SigningUtil.verify((PublicKey)verificationKey, jcaAlgorithmID, signature, input);
        }
        log.error("No PublicKey present in verification credential for signature verification");
        throw new SecurityException("No PublicKey supplied for signature verification");
    }

    public static boolean verify(@Nonnull PublicKey verificationKey, @Nonnull String jcaAlgorithmID, @Nonnull byte[] signature, @Nonnull byte[] input) throws SecurityException {
        Constraint.isNotNull(verificationKey, "Public key cannot be null");
        Constraint.isNotNull(jcaAlgorithmID, "JCA algorithm ID cannot be null");
        Constraint.isNotNull(signature, "Signature data to verify cannot be null");
        Constraint.isNotNull(input, "Input data to verify cannot be null");
        Logger log = SigningUtil.getLogger();
        log.debug("Verifying signature over input using public key of type {} and JCA algorithm ID {}", (Object)verificationKey.getAlgorithm(), (Object)jcaAlgorithmID);
        try {
            Signature sig = Signature.getInstance(jcaAlgorithmID);
            sig.initVerify(verificationKey);
            sig.update(input);
            return sig.verify(signature);
        }
        catch (GeneralSecurityException e) {
            log.error("Error during signature verification", e);
            throw new SecurityException("Error during signature verification", e);
        }
    }

    public static boolean verifyMAC(@Nonnull Key verificationKey, @Nonnull String jcaAlgorithmID, @Nonnull byte[] signature, @Nonnull byte[] input) throws SecurityException {
        Constraint.isNotNull(verificationKey, "Secret key cannot be null");
        Constraint.isNotNull(jcaAlgorithmID, "JCA algorithm ID cannot be null");
        Constraint.isNotNull(signature, "Signature data to verify cannot be null");
        Constraint.isNotNull(input, "Input data to verify cannot be null");
        Logger log = SigningUtil.getLogger();
        log.debug("Verifying MAC over input using key of type {} and JCA algorithm ID {}", (Object)verificationKey.getAlgorithm(), (Object)jcaAlgorithmID);
        byte[] computed = SigningUtil.signMAC(verificationKey, jcaAlgorithmID, input);
        return Arrays.equals(computed, signature);
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(SigningUtil.class);
    }
}

