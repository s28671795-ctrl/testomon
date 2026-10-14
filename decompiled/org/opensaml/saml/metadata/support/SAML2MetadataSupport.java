/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.support;

import java.util.List;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAML2MetadataSupport {
    private SAML2MetadataSupport() {
    }

    public static <T extends IndexedEndpoint> T getDefaultIndexedEndpoint(List<T> candidates) {
        Logger log = SAML2MetadataSupport.getLogger();
        log.debug("Selecting default IndexedEndpoint");
        if (candidates == null || candidates.isEmpty()) {
            log.debug("IndexedEndpoint list was null or empty, returning null");
            return null;
        }
        IndexedEndpoint firstNoDefault = null;
        for (IndexedEndpoint endpoint : candidates) {
            if (endpoint.isDefault().booleanValue()) {
                log.debug("Selected IndexedEndpoint with explicit isDefault of true");
                return (T)endpoint;
            }
            if (firstNoDefault != null || endpoint.isDefaultXSBoolean() != null) continue;
            firstNoDefault = endpoint;
        }
        if (firstNoDefault != null) {
            log.debug("Selected first IndexedEndpoint with no explicit isDefault");
            return (T)firstNoDefault;
        }
        log.debug("Selected first IndexedEndpoint with explicit isDefault of false");
        return (T)((IndexedEndpoint)candidates.get(0));
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(SAML2MetadataSupport.class);
    }
}

