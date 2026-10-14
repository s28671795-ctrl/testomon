/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;

interface CombinableX509KeyManager
extends X509KeyManager {
    public List<X509ExtendedKeyManager> getInnerKeyManagers();

    default public <T> T extractInnerField(Function<X509ExtendedKeyManager, T> keyManagerMapper, Predicate<T> predicate) {
        return this.getInnerKeyManagers().stream().map(keyManagerMapper).filter(predicate).findFirst().orElse(null);
    }

    default public String[] getAliases(Function<X509ExtendedKeyManager, String[]> aliasExtractor) {
        List<String> aliases = this.getInnerKeyManagers().stream().map(aliasExtractor).filter(Objects::nonNull).flatMap(Arrays::stream).collect(Collectors.toList());
        return aliases.isEmpty() ? null : aliases.toArray(new String[0]);
    }
}

