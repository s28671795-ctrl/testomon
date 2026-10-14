/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.decryptor;

import nl.altindag.ssl.pem.decryptor.BouncyFunction;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

public final class PemDecryptor
implements BouncyFunction<char[], PEMDecryptorProvider> {
    private static final PemDecryptor INSTANCE = new PemDecryptor();
    private static final JcePEMDecryptorProviderBuilder PEM_DECRYPTOR_PROVIDER_BUILDER = new JcePEMDecryptorProviderBuilder();

    private PemDecryptor() {
    }

    @Override
    public PEMDecryptorProvider apply(char[] password) {
        ValidationUtils.requireNotNull(password, "A password is mandatory with an encrypted key");
        return PEM_DECRYPTOR_PROVIDER_BUILDER.build(password);
    }

    public static BouncyFunction<char[], PEMDecryptorProvider> getInstance() {
        return INSTANCE;
    }
}

