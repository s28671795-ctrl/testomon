/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.hpke;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import org.bouncycastle.crypto.agreement.XDHBasicAgreement;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.generators.X448KeyPairGenerator;
import org.bouncycastle.crypto.hpke.HKDF;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.crypto.params.X448KeyGenerationParameters;
import org.bouncycastle.crypto.params.X448PrivateKeyParameters;
import org.bouncycastle.crypto.params.X448PublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.math.ec.custom.sec.SecP256R1Curve;
import org.bouncycastle.math.ec.custom.sec.SecP384R1Curve;
import org.bouncycastle.math.ec.custom.sec.SecP521R1Curve;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

class DHKEM {
    private AsymmetricCipherKeyPairGenerator kpGen;
    private BasicAgreement agreement;
    private final short kemId;
    private HKDF hkdf;
    private byte bitmask;
    private int Nsk;
    private int Nsecret;
    ECDomainParameters domainParams;

    protected DHKEM(short s2) {
        this.kemId = s2;
        switch (s2) {
            case 16: {
                this.hkdf = new HKDF(1);
                SecP256R1Curve secP256R1Curve = new SecP256R1Curve();
                this.domainParams = new ECDomainParameters(secP256R1Curve, secP256R1Curve.createPoint(new BigInteger(1, Hex.decode("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296")), new BigInteger(1, Hex.decode("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5"))), secP256R1Curve.getOrder(), secP256R1Curve.getCofactor(), Hex.decode("c49d360886e704936a6678e1139d26b7819f7e90"));
                this.agreement = new ECDHCBasicAgreement();
                this.bitmask = (byte)-1;
                this.Nsk = 32;
                this.Nsecret = 32;
                this.kpGen = new ECKeyPairGenerator();
                this.kpGen.init(new ECKeyGenerationParameters(this.domainParams, new SecureRandom()));
                break;
            }
            case 17: {
                this.hkdf = new HKDF(2);
                SecP384R1Curve secP384R1Curve = new SecP384R1Curve();
                this.domainParams = new ECDomainParameters(secP384R1Curve, secP384R1Curve.createPoint(new BigInteger(1, Hex.decode("aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7")), new BigInteger(1, Hex.decode("3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f"))), secP384R1Curve.getOrder(), secP384R1Curve.getCofactor(), Hex.decode("a335926aa319a27a1d00896a6773a4827acdac73"));
                this.agreement = new ECDHCBasicAgreement();
                this.bitmask = (byte)-1;
                this.Nsk = 48;
                this.Nsecret = 48;
                this.kpGen = new ECKeyPairGenerator();
                this.kpGen.init(new ECKeyGenerationParameters(this.domainParams, new SecureRandom()));
                break;
            }
            case 18: {
                this.hkdf = new HKDF(3);
                SecP521R1Curve secP521R1Curve = new SecP521R1Curve();
                this.domainParams = new ECDomainParameters(secP521R1Curve, secP521R1Curve.createPoint(new BigInteger("c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66", 16), new BigInteger("11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650", 16)), secP521R1Curve.getOrder(), secP521R1Curve.getCofactor(), Hex.decode("d09e8800291cb85396cc6717393284aaa0da64ba"));
                this.agreement = new ECDHCBasicAgreement();
                this.bitmask = 1;
                this.Nsk = 66;
                this.Nsecret = 64;
                this.kpGen = new ECKeyPairGenerator();
                this.kpGen.init(new ECKeyGenerationParameters(this.domainParams, new SecureRandom()));
                break;
            }
            case 32: {
                this.hkdf = new HKDF(1);
                this.agreement = new XDHBasicAgreement();
                this.Nsecret = 32;
                this.Nsk = 32;
                this.kpGen = new X25519KeyPairGenerator();
                this.kpGen.init(new X25519KeyGenerationParameters(new SecureRandom()));
                break;
            }
            case 33: {
                this.hkdf = new HKDF(3);
                this.agreement = new XDHBasicAgreement();
                this.Nsecret = 64;
                this.Nsk = 56;
                this.kpGen = new X448KeyPairGenerator();
                this.kpGen.init(new X448KeyGenerationParameters(new SecureRandom()));
                break;
            }
            default: {
                throw new IllegalArgumentException("invalid kem id");
            }
        }
    }

    public byte[] SerializePublicKey(AsymmetricKeyParameter asymmetricKeyParameter) {
        switch (this.kemId) {
            case 16: 
            case 17: 
            case 18: {
                return ((ECPublicKeyParameters)asymmetricKeyParameter).getQ().getEncoded(false);
            }
            case 33: {
                return ((X448PublicKeyParameters)asymmetricKeyParameter).getEncoded();
            }
            case 32: {
                return ((X25519PublicKeyParameters)asymmetricKeyParameter).getEncoded();
            }
        }
        throw new IllegalStateException("invalid kem id");
    }

    public byte[] SerializePrivateKey(AsymmetricKeyParameter asymmetricKeyParameter) {
        switch (this.kemId) {
            case 16: 
            case 17: 
            case 18: {
                return this.formatBigIntegerBytes(((ECPrivateKeyParameters)asymmetricKeyParameter).getD().toByteArray(), this.Nsk);
            }
            case 33: {
                return ((X448PrivateKeyParameters)asymmetricKeyParameter).getEncoded();
            }
            case 32: {
                return ((X25519PrivateKeyParameters)asymmetricKeyParameter).getEncoded();
            }
        }
        throw new IllegalStateException("invalid kem id");
    }

    public AsymmetricKeyParameter DeserializePublicKey(byte[] byArray) {
        switch (this.kemId) {
            case 16: 
            case 17: 
            case 18: {
                ECPoint eCPoint = this.domainParams.getCurve().decodePoint(byArray);
                return new ECPublicKeyParameters(eCPoint, this.domainParams);
            }
            case 33: {
                return new X448PublicKeyParameters(byArray);
            }
            case 32: {
                return new X25519PublicKeyParameters(byArray);
            }
        }
        throw new IllegalStateException("invalid kem id");
    }

    public AsymmetricCipherKeyPair DeserializePrivateKey(byte[] byArray, byte[] byArray2) {
        AsymmetricKeyParameter asymmetricKeyParameter = null;
        if (byArray2 != null) {
            asymmetricKeyParameter = this.DeserializePublicKey(byArray2);
        }
        switch (this.kemId) {
            case 16: 
            case 17: 
            case 18: {
                BigInteger bigInteger = new BigInteger(1, byArray);
                ECPrivateKeyParameters eCPrivateKeyParameters = new ECPrivateKeyParameters(bigInteger, this.domainParams);
                if (asymmetricKeyParameter == null) {
                    ECPoint eCPoint = new FixedPointCombMultiplier().multiply(this.domainParams.getG(), eCPrivateKeyParameters.getD());
                    asymmetricKeyParameter = new ECPublicKeyParameters(eCPoint, this.domainParams);
                }
                return new AsymmetricCipherKeyPair(asymmetricKeyParameter, eCPrivateKeyParameters);
            }
            case 33: {
                X448PrivateKeyParameters x448PrivateKeyParameters = new X448PrivateKeyParameters(byArray);
                if (asymmetricKeyParameter == null) {
                    asymmetricKeyParameter = x448PrivateKeyParameters.generatePublicKey();
                }
                return new AsymmetricCipherKeyPair(asymmetricKeyParameter, x448PrivateKeyParameters);
            }
            case 32: {
                X25519PrivateKeyParameters x25519PrivateKeyParameters = new X25519PrivateKeyParameters(byArray);
                if (asymmetricKeyParameter == null) {
                    asymmetricKeyParameter = x25519PrivateKeyParameters.generatePublicKey();
                }
                return new AsymmetricCipherKeyPair(asymmetricKeyParameter, x25519PrivateKeyParameters);
            }
        }
        throw new IllegalStateException("invalid kem id");
    }

    private boolean ValidateSk(BigInteger bigInteger) {
        BigInteger bigInteger2 = this.domainParams.getN();
        int n = bigInteger2.bitLength();
        int n2 = n >>> 2;
        if (bigInteger.compareTo(BigInteger.valueOf(1L)) < 0 || bigInteger.compareTo(bigInteger2) >= 0) {
            return false;
        }
        return WNafUtil.getNafWeight(bigInteger) >= n2;
    }

    public AsymmetricCipherKeyPair GeneratePrivateKey() {
        return this.kpGen.generateKeyPair();
    }

    public AsymmetricCipherKeyPair DeriveKeyPair(byte[] byArray) {
        byte[] byArray2 = Arrays.concatenate(Strings.toByteArray("KEM"), Pack.shortToBigEndian(this.kemId));
        switch (this.kemId) {
            case 16: 
            case 17: 
            case 18: {
                byte[] byArray3 = this.hkdf.LabeledExtract(null, byArray2, "dkp_prk", byArray);
                int n = 0;
                byte[] byArray4 = new byte[1];
                while (true) {
                    if (n > 255) {
                        throw new IllegalStateException("DeriveKeyPairError");
                    }
                    byArray4[0] = (byte)n;
                    byte[] byArray5 = this.hkdf.LabeledExpand(byArray3, byArray2, "candidate", byArray4, this.Nsk);
                    byArray5[0] = (byte)(byArray5[0] & this.bitmask);
                    BigInteger bigInteger = new BigInteger(1, byArray5);
                    if (this.ValidateSk(bigInteger)) {
                        ECPoint eCPoint = new FixedPointCombMultiplier().multiply(this.domainParams.getG(), bigInteger);
                        ECPrivateKeyParameters eCPrivateKeyParameters = new ECPrivateKeyParameters(bigInteger, this.domainParams);
                        ECPublicKeyParameters eCPublicKeyParameters = new ECPublicKeyParameters(eCPoint, this.domainParams);
                        return new AsymmetricCipherKeyPair(eCPublicKeyParameters, eCPrivateKeyParameters);
                    }
                    ++n;
                }
            }
            case 33: {
                byte[] byArray6 = this.hkdf.LabeledExtract(null, byArray2, "dkp_prk", byArray);
                byte[] byArray7 = this.hkdf.LabeledExpand(byArray6, byArray2, "sk", null, this.Nsk);
                X448PrivateKeyParameters x448PrivateKeyParameters = new X448PrivateKeyParameters(byArray7);
                return new AsymmetricCipherKeyPair(x448PrivateKeyParameters.generatePublicKey(), x448PrivateKeyParameters);
            }
            case 32: {
                byte[] byArray8 = this.hkdf.LabeledExtract(null, byArray2, "dkp_prk", byArray);
                byte[] byArray9 = this.hkdf.LabeledExpand(byArray8, byArray2, "sk", null, this.Nsk);
                X25519PrivateKeyParameters x25519PrivateKeyParameters = new X25519PrivateKeyParameters(byArray9);
                return new AsymmetricCipherKeyPair(x25519PrivateKeyParameters.generatePublicKey(), x25519PrivateKeyParameters);
            }
        }
        throw new IllegalStateException("invalid kem id");
    }

    protected byte[][] Encap(AsymmetricKeyParameter asymmetricKeyParameter) {
        return this.Encap(asymmetricKeyParameter, this.kpGen.generateKeyPair());
    }

    protected byte[][] Encap(AsymmetricKeyParameter asymmetricKeyParameter, AsymmetricCipherKeyPair asymmetricCipherKeyPair) {
        byte[][] byArrayArray = new byte[2][];
        this.agreement.init(asymmetricCipherKeyPair.getPrivate());
        byte[] byArray = this.agreement.calculateAgreement(asymmetricKeyParameter).toByteArray();
        byte[] byArray2 = this.formatBigIntegerBytes(byArray, this.agreement.getFieldSize());
        byte[] byArray3 = this.SerializePublicKey(asymmetricCipherKeyPair.getPublic());
        byte[] byArray4 = this.SerializePublicKey(asymmetricKeyParameter);
        byte[] byArray5 = Arrays.concatenate(byArray3, byArray4);
        byte[] byArray6 = this.ExtractAndExpand(byArray2, byArray5);
        byArrayArray[0] = byArray6;
        byArrayArray[1] = byArray3;
        return byArrayArray;
    }

    protected byte[] Decap(byte[] byArray, AsymmetricCipherKeyPair asymmetricCipherKeyPair) {
        AsymmetricKeyParameter asymmetricKeyParameter = this.DeserializePublicKey(byArray);
        this.agreement.init(asymmetricCipherKeyPair.getPrivate());
        byte[] byArray2 = this.agreement.calculateAgreement(asymmetricKeyParameter).toByteArray();
        byte[] byArray3 = this.formatBigIntegerBytes(byArray2, this.agreement.getFieldSize());
        byte[] byArray4 = this.SerializePublicKey(asymmetricCipherKeyPair.getPublic());
        byte[] byArray5 = Arrays.concatenate(byArray, byArray4);
        byte[] byArray6 = this.ExtractAndExpand(byArray3, byArray5);
        return byArray6;
    }

    protected byte[][] AuthEncap(AsymmetricKeyParameter asymmetricKeyParameter, AsymmetricCipherKeyPair asymmetricCipherKeyPair) {
        byte[][] byArrayArray = new byte[2][];
        AsymmetricCipherKeyPair asymmetricCipherKeyPair2 = this.kpGen.generateKeyPair();
        this.agreement.init(asymmetricCipherKeyPair2.getPrivate());
        byte[] byArray = this.agreement.calculateAgreement(asymmetricKeyParameter).toByteArray();
        byte[] byArray2 = this.formatBigIntegerBytes(byArray, this.agreement.getFieldSize());
        this.agreement.init(asymmetricCipherKeyPair.getPrivate());
        byArray = this.agreement.calculateAgreement(asymmetricKeyParameter).toByteArray();
        byte[] byArray3 = this.formatBigIntegerBytes(byArray, this.agreement.getFieldSize());
        byte[] byArray4 = Arrays.concatenate(byArray2, byArray3);
        byte[] byArray5 = this.SerializePublicKey(asymmetricCipherKeyPair2.getPublic());
        byte[] byArray6 = this.SerializePublicKey(asymmetricKeyParameter);
        byte[] byArray7 = this.SerializePublicKey(asymmetricCipherKeyPair.getPublic());
        byte[] byArray8 = Arrays.concatenate(byArray5, byArray6, byArray7);
        byte[] byArray9 = this.ExtractAndExpand(byArray4, byArray8);
        byArrayArray[0] = byArray9;
        byArrayArray[1] = byArray5;
        return byArrayArray;
    }

    protected byte[] AuthDecap(byte[] byArray, AsymmetricCipherKeyPair asymmetricCipherKeyPair, AsymmetricKeyParameter asymmetricKeyParameter) {
        AsymmetricKeyParameter asymmetricKeyParameter2 = this.DeserializePublicKey(byArray);
        this.agreement.init(asymmetricCipherKeyPair.getPrivate());
        byte[] byArray2 = this.agreement.calculateAgreement(asymmetricKeyParameter2).toByteArray();
        byte[] byArray3 = this.formatBigIntegerBytes(byArray2, this.agreement.getFieldSize());
        this.agreement.init(asymmetricCipherKeyPair.getPrivate());
        byArray2 = this.agreement.calculateAgreement(asymmetricKeyParameter).toByteArray();
        byte[] byArray4 = this.formatBigIntegerBytes(byArray2, this.agreement.getFieldSize());
        byte[] byArray5 = Arrays.concatenate(byArray3, byArray4);
        byte[] byArray6 = this.SerializePublicKey(asymmetricCipherKeyPair.getPublic());
        byte[] byArray7 = this.SerializePublicKey(asymmetricKeyParameter);
        byte[] byArray8 = Arrays.concatenate(byArray, byArray6, byArray7);
        byte[] byArray9 = this.ExtractAndExpand(byArray5, byArray8);
        return byArray9;
    }

    private byte[] ExtractAndExpand(byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = Arrays.concatenate(Strings.toByteArray("KEM"), Pack.shortToBigEndian(this.kemId));
        byte[] byArray4 = this.hkdf.LabeledExtract(null, byArray3, "eae_prk", byArray);
        byte[] byArray5 = this.hkdf.LabeledExpand(byArray4, byArray3, "shared_secret", byArray2, this.Nsecret);
        return byArray5;
    }

    private byte[] formatBigIntegerBytes(byte[] byArray, int n) {
        byte[] byArray2 = new byte[n];
        if (byArray.length <= n) {
            System.arraycopy(byArray, 0, byArray2, n - byArray.length, byArray.length);
        } else {
            System.arraycopy(byArray, byArray.length - n, byArray2, 0, n);
        }
        return byArray2;
    }
}

