/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.resourceResolvers;

import java.io.InputStream;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.ResourceResolverLookup;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class ResolverAttachment
implements ResourceResolver,
ResourceResolverLookup {
    @Override
    public ResourceResolverLookup canResolve(String uri, String baseURI) {
        if (uri == null) {
            return null;
        }
        if (uri.startsWith("cid:")) {
            return this;
        }
        return null;
    }

    @Override
    public ResourceResolver newInstance(String uri, String baseURI) {
        return new ResolverAttachment();
    }

    @Override
    public boolean isSameDocumentReference() {
        return false;
    }

    @Override
    public boolean matches(XMLSecStartElement xmlSecStartElement) {
        return false;
    }

    @Override
    public InputStream getInputStreamFromExternalReference() throws XMLSecurityException {
        return null;
    }
}

