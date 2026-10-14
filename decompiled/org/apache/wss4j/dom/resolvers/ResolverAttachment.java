/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.resolvers;

import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;

public class ResolverAttachment
extends ResourceResolverSpi {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context) throws ResourceResolverException {
        XMLSignatureInput xmlSignatureInput = new XMLSignatureInput(EMPTY_BYTE_ARRAY);
        xmlSignatureInput.setSourceURI(context.uriToResolve);
        return xmlSignatureInput;
    }

    @Override
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            return false;
        }
        return context.uriToResolve.startsWith("cid:");
    }

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }
}

