/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.catalog;

import java.io.IOException;
import org.apache.cxf.catalog.OASISCatalogManager;

public class OASISCatalogManagerHelper {
    public String resolve(OASISCatalogManager catalogResolver, String target, String base) throws IOException {
        String resolvedLocation = null;
        if (catalogResolver != null) {
            resolvedLocation = catalogResolver.resolveSystem(target);
            if (resolvedLocation == null) {
                resolvedLocation = catalogResolver.resolveURI(target);
            }
            if (resolvedLocation == null) {
                resolvedLocation = catalogResolver.resolvePublic(target, base);
            }
        }
        return resolvedLocation;
    }
}

