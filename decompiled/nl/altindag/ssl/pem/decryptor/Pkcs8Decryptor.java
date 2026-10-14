/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.decryptor;

import nl.altindag.ssl.pem.decryptor.BouncyFunction;
import nl.altindag.ssl.util.internal.ValidationUtils;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.operator.OperatorCreationException;

public final class Pkcs8Decryptor
implements BouncyFunction<char[], InputDecryptorProvider> {
    private static final Pkcs8Decryptor INSTANCE = new Pkcs8Decryptor();
    private static final JceOpenSSLPKCS8DecryptorProviderBuilder PKCS8_DECRYPTOR_PROVIDER_BUILDER = new JceOpenSSLPKCS8DecryptorProviderBuilder();

    private Pkcs8Decryptor() {
    }

    @Override
    public InputDecryptorProvider apply(char[] password) throws OperatorCreationException {
        ValidationUtils.requireNotNull(password, "A password is mandatory with an encrypted key");
        return PKCS8_DECRYPTOR_PROVIDER_BUILDER.build(password);
    }

    public static BouncyFunction<char[], InputDecryptorProvider> getInstance() {
        return INSTANCE;
    }
}

