/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Strings;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class SimpleURLCanonicalizer {
    private static Map<String, Integer> schemePortMap = new HashMap<String, Integer>();

    private SimpleURLCanonicalizer() {
    }

    public static void registerSchemePortMapping(@Nonnull @NotEmpty String scheme2, @Nonnull Integer port) {
        String trimmedScheme = Constraint.isNotNull(StringSupport.trimOrNull(scheme2), "Scheme cannot be null");
        Constraint.isNotNull(port, "Port cannot be null");
        schemePortMap.put(trimmedScheme.toLowerCase(), port);
    }

    public static void deregisterSchemePortMapping(@Nonnull String scheme2) {
        String trimmedScheme = Constraint.isNotNull(StringSupport.trimOrNull(scheme2), "Scheme cannot be null");
        schemePortMap.remove(trimmedScheme.toLowerCase());
    }

    @Nullable
    public static Integer getRegisteredPort(@Nonnull @NotEmpty String scheme2) {
        String trimmedScheme = Constraint.isNotNull(StringSupport.trimOrNull(scheme2), "Scheme cannot be null");
        return schemePortMap.get(trimmedScheme.toLowerCase());
    }

    @Nonnull
    @NotEmpty
    public static String canonicalize(@Nonnull @NotEmpty String url2) throws MalformedURLException {
        Constraint.isFalse(Strings.isNullOrEmpty(url2), "URL was null or empty");
        URLBuilder urlBuilder = new URLBuilder(url2);
        SimpleURLCanonicalizer.canonicalize(urlBuilder);
        return urlBuilder.buildURL();
    }

    private static void canonicalize(@Nonnull URLBuilder url2) {
        if (url2.getScheme() != null) {
            url2.setScheme(url2.getScheme().toLowerCase());
            String scheme2 = url2.getScheme();
            Integer port = SimpleURLCanonicalizer.getRegisteredPort(scheme2);
            if (port != null && port.equals(url2.getPort())) {
                url2.setPort(null);
            }
        }
        if (url2.getHost() != null) {
            url2.setHost(url2.getHost().toLowerCase());
        }
    }

    static {
        SimpleURLCanonicalizer.registerSchemePortMapping("ftp", 23);
        SimpleURLCanonicalizer.registerSchemePortMapping("http", 80);
        SimpleURLCanonicalizer.registerSchemePortMapping("https", 443);
        SimpleURLCanonicalizer.registerSchemePortMapping("ldap", 389);
        SimpleURLCanonicalizer.registerSchemePortMapping("ldaps", 636);
    }
}

