/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.ext;

import io.swagger.v3.jaxrs2.DefaultParameterExtension;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAPIExtensions {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAPIExtensions.class);
    private static List<OpenAPIExtension> extensions = null;

    public static List<OpenAPIExtension> getExtensions() {
        return extensions;
    }

    public static void setExtensions(List<OpenAPIExtension> ext) {
        extensions = ext;
    }

    public static Iterator<OpenAPIExtension> chain() {
        return extensions.iterator();
    }

    static {
        extensions = new ArrayList<OpenAPIExtension>();
        ServiceLoader<OpenAPIExtension> loader = ServiceLoader.load(OpenAPIExtension.class);
        for (OpenAPIExtension ext : loader) {
            LOGGER.debug("adding extension {}", (Object)ext);
            extensions.add(ext);
        }
        extensions.add(new DefaultParameterExtension());
    }
}

