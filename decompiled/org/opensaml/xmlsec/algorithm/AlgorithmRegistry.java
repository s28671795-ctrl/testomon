/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm;

import com.google.common.base.MoreObjects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.DigestAlgorithm;
import org.opensaml.xmlsec.algorithm.KeyLengthSpecifiedAlgorithm;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorithmRegistry {
    private Logger log = LoggerFactory.getLogger(AlgorithmRegistry.class);
    private Map<String, AlgorithmDescriptor> descriptors = new HashMap<String, AlgorithmDescriptor>();
    private Set<String> runtimeSupported = new HashSet<String>();
    private Map<String, DigestAlgorithm> digestAlgorithms = new HashMap<String, DigestAlgorithm>();
    private Map<SignatureAlgorithmIndex, SignatureAlgorithm> signatureAlgorithms = new HashMap<SignatureAlgorithmIndex, SignatureAlgorithm>();

    @Nullable
    public AlgorithmDescriptor get(@Nullable String algorithmURI) {
        String trimmed = StringSupport.trimOrNull(algorithmURI);
        if (trimmed == null) {
            return null;
        }
        return this.descriptors.get(trimmed);
    }

    public boolean isRuntimeSupported(@Nullable String algorithmURI) {
        String trimmed = StringSupport.trimOrNull(algorithmURI);
        if (trimmed == null) {
            this.log.debug("Runtime support failed, algorithm URI was null or empty");
            return false;
        }
        boolean supported = this.runtimeSupported.contains(trimmed);
        this.log.debug("Runtime support eval for algorithm URI '{}': {}", (Object)trimmed, (Object)(supported ? "supported" : "unsupported"));
        return supported;
    }

    public void clear() {
        this.descriptors.clear();
        this.runtimeSupported.clear();
        this.digestAlgorithms.clear();
        this.signatureAlgorithms.clear();
    }

    public void register(@Nonnull AlgorithmDescriptor descriptor) {
        Constraint.isNotNull(descriptor, "AlgorithmDescriptor was null");
        this.log.debug("Registering algorithm descriptor with URI: {}", (Object)descriptor.getURI());
        AlgorithmDescriptor old = this.descriptors.get(descriptor.getURI());
        if (old != null) {
            this.log.debug("Registry contained existing descriptor with URI, removing old instance and re-registering: {}", (Object)descriptor.getURI());
            this.deindex(old);
            this.deregister(old);
        }
        this.descriptors.put(descriptor.getURI(), descriptor);
        this.index(descriptor);
    }

    public void deregister(@Nonnull AlgorithmDescriptor descriptor) {
        Constraint.isNotNull(descriptor, "AlgorithmDescriptor was null");
        if (this.descriptors.containsKey(descriptor.getURI())) {
            this.deindex(descriptor);
            this.descriptors.remove(descriptor.getURI());
        } else {
            this.log.debug("Registry did not contain descriptor with URI, nothing to do: {}", (Object)descriptor.getURI());
        }
    }

    public void deregister(@Nonnull String uri) {
        Constraint.isNotNull(uri, "AlgorithmDescriptor URI was null");
        AlgorithmDescriptor descriptor = this.get(uri);
        if (descriptor != null) {
            this.deregister(descriptor);
        }
    }

    @Nullable
    public DigestAlgorithm getDigestAlgorithm(@Nonnull String digestMethod) {
        Constraint.isNotNull(digestMethod, "Digest method was null");
        return this.digestAlgorithms.get(digestMethod);
    }

    @Nullable
    public SignatureAlgorithm getSignatureAlgorithm(@Nonnull String keyType, @Nonnull String digestMethod) {
        Constraint.isNotNull(keyType, "Key type was null");
        Constraint.isNotNull(digestMethod, "Digest type was null");
        return this.signatureAlgorithms.get(new SignatureAlgorithmIndex(keyType, digestMethod));
    }

    private void index(AlgorithmDescriptor descriptor) {
        if (this.checkRuntimeSupports(descriptor)) {
            this.runtimeSupported.add(descriptor.getURI());
        } else {
            this.log.info("Algorithm failed runtime support check, will not be usable: {}", (Object)descriptor.getURI());
            this.runtimeSupported.remove(descriptor.getURI());
        }
        if (descriptor instanceof DigestAlgorithm) {
            DigestAlgorithm digestAlgorithm = (DigestAlgorithm)descriptor;
            this.digestAlgorithms.put(digestAlgorithm.getJCAAlgorithmID(), digestAlgorithm);
        }
        if (descriptor instanceof SignatureAlgorithm) {
            SignatureAlgorithm sigAlg = (SignatureAlgorithm)descriptor;
            this.signatureAlgorithms.put(new SignatureAlgorithmIndex(sigAlg.getKey(), sigAlg.getDigest()), sigAlg);
        }
    }

    private void deindex(AlgorithmDescriptor descriptor) {
        this.runtimeSupported.remove(descriptor.getURI());
        if (descriptor instanceof DigestAlgorithm) {
            DigestAlgorithm digestAlgorithm = (DigestAlgorithm)descriptor;
            this.digestAlgorithms.remove(digestAlgorithm.getJCAAlgorithmID());
        }
        if (descriptor instanceof SignatureAlgorithm) {
            SignatureAlgorithm sigAlg = (SignatureAlgorithm)descriptor;
            this.signatureAlgorithms.remove(new SignatureAlgorithmIndex(sigAlg.getKey(), sigAlg.getDigest()));
        }
    }

    private boolean checkRuntimeSupports(AlgorithmDescriptor descriptor) {
        try {
            switch (descriptor.getType()) {
                case BlockEncryption: 
                case KeyTransport: 
                case SymmetricKeyWrap: {
                    Cipher.getInstance(descriptor.getJCAAlgorithmID());
                    if (!this.checkCipherSupportedKeyLength(descriptor)) {
                        return false;
                    }
                    break;
                }
                case Signature: {
                    Signature.getInstance(descriptor.getJCAAlgorithmID());
                    break;
                }
                case Mac: {
                    Mac.getInstance(descriptor.getJCAAlgorithmID());
                    break;
                }
                case MessageDigest: {
                    MessageDigest.getInstance(descriptor.getJCAAlgorithmID());
                    break;
                }
                default: {
                    this.log.info("Saw unknown AlgorithmDescriptor type, failing runtime support check: {}", (Object)descriptor.getClass().getName());
                    break;
                }
            }
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            if (!this.checkSpecialCasesRuntimeSupport(descriptor)) {
                this.log.debug(String.format("AlgorithmDescriptor failed runtime support check: %s", descriptor.getURI()), e);
                return false;
            }
        }
        catch (Throwable t2) {
            this.log.error("Fatal error evaluating algorithm runtime support", t2);
            return false;
        }
        return true;
    }

    private boolean checkCipherSupportedKeyLength(AlgorithmDescriptor descriptor) throws NoSuchAlgorithmException {
        int cipherMaxLength;
        int algoLength;
        if (descriptor instanceof KeyLengthSpecifiedAlgorithm && (algoLength = ((KeyLengthSpecifiedAlgorithm)descriptor).getKeyLength().intValue()) > (cipherMaxLength = Cipher.getMaxAllowedKeyLength(descriptor.getJCAAlgorithmID()))) {
            this.log.info("Cipher algorithm '{}' is not supported, its key length {} exceeds Cipher max key length {}", descriptor.getURI(), algoLength, cipherMaxLength);
            return false;
        }
        return true;
    }

    private boolean checkSpecialCasesRuntimeSupport(AlgorithmDescriptor descriptor) {
        this.log.trace("Checking runtime support failure for special cases: {}", (Object)descriptor.getURI());
        try {
            if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(descriptor.getURI())) {
                Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
                this.log.trace("RSA OAEP algorithm passed as special case with OAEPWithSHA1AndMGF1Padding");
                return true;
            }
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            this.log.trace("Special case eval for algorithm failed with exception", e);
        }
        this.log.trace("Algorithm was not supported by any special cases: {}", (Object)descriptor.getURI());
        return false;
    }

    protected class SignatureAlgorithmIndex {
        private String key;
        private String digest;

        public SignatureAlgorithmIndex(@Nonnull String keyType, String digestType) {
            this.key = StringSupport.trim(keyType);
            this.digest = StringSupport.trim(digestType);
        }

        public int hashCode() {
            int result = 17;
            result = 37 * result + this.key.hashCode();
            result = 37 * result + this.digest.hashCode();
            return result;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof SignatureAlgorithmIndex) {
                SignatureAlgorithmIndex other = (SignatureAlgorithmIndex)obj;
                return Objects.equals(this.key, other.key) && Objects.equals(this.digest, other.digest);
            }
            return false;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("Key", this.key).add("Digest", this.digest).toString();
        }
    }
}

