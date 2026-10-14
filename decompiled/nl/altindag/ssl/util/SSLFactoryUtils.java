/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.util.KeyManagerUtils;
import nl.altindag.ssl.util.SSLSessionUtils;
import nl.altindag.ssl.util.TrustManagerUtils;

public final class SSLFactoryUtils {
    private SSLFactoryUtils() {
    }

    public static void reload(SSLFactory baseSslFactory, SSLFactory updatedSslFactory) {
        SSLFactoryUtils.reload(baseSslFactory, updatedSslFactory, true);
    }

    public static void reload(SSLFactory baseSslFactory, SSLFactory updatedSslFactory, boolean shouldInvalidateCaches) {
        SSLFactoryUtils.reload(baseSslFactory, updatedSslFactory, SSLFactory::getKeyManager, KeyManagerUtils::swapKeyManager);
        SSLFactoryUtils.reload(baseSslFactory, updatedSslFactory, SSLFactory::getTrustManager, TrustManagerUtils::swapTrustManager);
        if (shouldInvalidateCaches) {
            SSLSessionUtils.invalidateCaches(baseSslFactory);
        }
    }

    private static <T> void reload(SSLFactory baseSslFactory, SSLFactory updatedSslFactory, Function<SSLFactory, Optional<T>> mapper2, BiConsumer<T, T> consumer) {
        Optional<T> baseManager = mapper2.apply(baseSslFactory);
        Optional<T> updatedManager = mapper2.apply(updatedSslFactory);
        if (baseManager.isPresent() && updatedManager.isPresent()) {
            consumer.accept(baseManager.get(), updatedManager.get());
        }
    }
}

