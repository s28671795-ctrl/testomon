/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public final class UriUtils {
    private UriUtils() {
    }

    public static void validate(URI uri) {
        if (Objects.isNull(uri)) {
            throw new IllegalArgumentException("Host should be present");
        }
        if (Objects.isNull(uri.getHost())) {
            throw new IllegalArgumentException(String.format("Hostname should be defined for the given input: [%s]", uri));
        }
        if (uri.getPort() == -1) {
            throw new IllegalArgumentException(String.format("Port should be defined for the given input: [%s]", uri));
        }
    }

    public static String extractHost(String value) {
        try {
            URI url2 = new URI(value);
            return url2.getHost();
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

