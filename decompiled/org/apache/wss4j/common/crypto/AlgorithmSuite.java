/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AlgorithmSuite {
    private Set<String> signatureMethods = Collections.emptySet();
    private Set<String> c14nAlgorithms = Collections.emptySet();
    private Set<String> digestAlgorithms = Collections.emptySet();
    private Set<String> transformAlgorithms = Collections.emptySet();
    private Set<String> encryptionMethods = Collections.emptySet();
    private Set<String> keyWrapAlgorithms = Collections.emptySet();
    private Set<String> derivedKeyAlgorithms = Collections.emptySet();
    private int maximumSymmetricKeyLength = 256;
    private int minimumSymmetricKeyLength = 128;
    private int maximumAsymmetricKeyLength = 4096;
    private int minimumAsymmetricKeyLength = 1024;
    private int maximumEllipticCurveKeyLength = 512;
    private int minimumEllipticCurveKeyLength = 160;
    private int signatureDerivedKeyLength;
    private int encryptionDerivedKeyLength;

    public void addSignatureMethod(String signatureMethod) {
        if (this.signatureMethods.isEmpty()) {
            this.signatureMethods = new HashSet<String>();
        }
        this.signatureMethods.add(signatureMethod);
    }

    public Set<String> getSignatureMethods() {
        return this.signatureMethods;
    }

    public void addC14nAlgorithm(String c14nAlgorithm) {
        if (this.c14nAlgorithms.isEmpty()) {
            this.c14nAlgorithms = new HashSet<String>();
        }
        this.c14nAlgorithms.add(c14nAlgorithm);
    }

    public Set<String> getC14nAlgorithms() {
        return this.c14nAlgorithms;
    }

    public void addDigestAlgorithm(String digestAlgorithm) {
        if (this.digestAlgorithms.isEmpty()) {
            this.digestAlgorithms = new HashSet<String>();
        }
        this.digestAlgorithms.add(digestAlgorithm);
    }

    public Set<String> getDigestAlgorithms() {
        return this.digestAlgorithms;
    }

    public void addTransformAlgorithm(String transformAlgorithm) {
        if (this.transformAlgorithms.isEmpty()) {
            this.transformAlgorithms = new HashSet<String>();
        }
        this.transformAlgorithms.add(transformAlgorithm);
    }

    public Set<String> getTransformAlgorithms() {
        return this.transformAlgorithms;
    }

    public void addEncryptionMethod(String encryptionMethod) {
        if (this.encryptionMethods.isEmpty()) {
            this.encryptionMethods = new HashSet<String>();
        }
        this.encryptionMethods.add(encryptionMethod);
    }

    public Set<String> getEncryptionMethods() {
        return this.encryptionMethods;
    }

    public void addKeyWrapAlgorithm(String keyWrapAlgorithm) {
        if (this.keyWrapAlgorithms.isEmpty()) {
            this.keyWrapAlgorithms = new HashSet<String>();
        }
        this.keyWrapAlgorithms.add(keyWrapAlgorithm);
    }

    public Set<String> getKeyWrapAlgorithms() {
        return this.keyWrapAlgorithms;
    }

    public void addDerivedKeyAlgorithm(String derivedKeyAlgorithm) {
        if (this.derivedKeyAlgorithms.isEmpty()) {
            this.derivedKeyAlgorithms = new HashSet<String>();
        }
        this.derivedKeyAlgorithms.add(derivedKeyAlgorithm);
    }

    public Set<String> getDerivedKeyAlgorithms() {
        return this.derivedKeyAlgorithms;
    }

    public int getMaximumSymmetricKeyLength() {
        return this.maximumSymmetricKeyLength;
    }

    public void setMaximumSymmetricKeyLength(int maximumSymmetricKeyLength) {
        this.maximumSymmetricKeyLength = maximumSymmetricKeyLength;
    }

    public int getMinimumAsymmetricKeyLength() {
        return this.minimumAsymmetricKeyLength;
    }

    public void setMinimumAsymmetricKeyLength(int minimumAsymmetricKeyLength) {
        this.minimumAsymmetricKeyLength = minimumAsymmetricKeyLength;
    }

    public int getMaximumAsymmetricKeyLength() {
        return this.maximumAsymmetricKeyLength;
    }

    public void setMaximumAsymmetricKeyLength(int maximumAsymmetricKeyLength) {
        this.maximumAsymmetricKeyLength = maximumAsymmetricKeyLength;
    }

    public int getEncryptionDerivedKeyLength() {
        return this.encryptionDerivedKeyLength;
    }

    public void setEncryptionDerivedKeyLength(int encryptionDerivedKeyLength) {
        this.encryptionDerivedKeyLength = encryptionDerivedKeyLength;
    }

    public int getSignatureDerivedKeyLength() {
        return this.signatureDerivedKeyLength;
    }

    public void setSignatureDerivedKeyLength(int signatureDerivedKeyLength) {
        this.signatureDerivedKeyLength = signatureDerivedKeyLength;
    }

    public int getMinimumSymmetricKeyLength() {
        return this.minimumSymmetricKeyLength;
    }

    public void setMinimumSymmetricKeyLength(int minimumSymmetricKeyLength) {
        this.minimumSymmetricKeyLength = minimumSymmetricKeyLength;
    }

    public int getMaximumEllipticCurveKeyLength() {
        return this.maximumEllipticCurveKeyLength;
    }

    public void setMaximumEllipticCurveKeyLength(int maximumEllipticCurveKeyLength) {
        this.maximumEllipticCurveKeyLength = maximumEllipticCurveKeyLength;
    }

    public int getMinimumEllipticCurveKeyLength() {
        return this.minimumEllipticCurveKeyLength;
    }

    public void setMinimumEllipticCurveKeyLength(int minimumEllipticCurveKeyLength) {
        this.minimumEllipticCurveKeyLength = minimumEllipticCurveKeyLength;
    }
}

