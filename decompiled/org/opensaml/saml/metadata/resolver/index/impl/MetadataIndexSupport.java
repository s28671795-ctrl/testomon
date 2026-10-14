/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index.impl;

import java.net.MalformedURLException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.net.SimpleURLCanonicalizer;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class MetadataIndexSupport {
    private MetadataIndexSupport() {
    }

    @Nonnull
    public static String canonicalizeLocationURI(@Nonnull String input) throws MalformedURLException {
        String uri = StringSupport.trimOrNull(input);
        if (uri == null) {
            throw new MalformedURLException("URL input was null or empty");
        }
        URLBuilder urlBuilder = new URLBuilder(uri);
        urlBuilder.setUsername(null);
        urlBuilder.setPassword(null);
        urlBuilder.getQueryParams().clear();
        urlBuilder.setFragment(null);
        return SimpleURLCanonicalizer.canonicalize(urlBuilder.buildURL());
    }

    @Nullable
    public static String trimURLPathSegment(@Nullable String input) {
        String path = StringSupport.trimOrNull(input);
        if (path == null || "/".equals(path)) {
            return null;
        }
        int idx = path.lastIndexOf("/");
        if (idx > 0) {
            if (path.endsWith("/")) {
                return path.substring(0, idx);
            }
            return path.substring(0, idx + 1);
        }
        if (idx == 0) {
            return "/";
        }
        return null;
    }
}

