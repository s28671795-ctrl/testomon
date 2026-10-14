/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils.resolver.implementations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;

public class ResolverAnonymous
extends ResourceResolverSpi {
    private InputStream inStream;

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    public ResolverAnonymous(String filename) throws FileNotFoundException, IOException {
        this.inStream = Files.newInputStream(Paths.get(filename, new String[0]), new OpenOption[0]);
    }

    public ResolverAnonymous(InputStream is) {
        this.inStream = is;
    }

    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context) {
        XMLSignatureInput input = new XMLSignatureInput(this.inStream);
        input.setSecureValidation(context.secureValidation);
        return input;
    }

    @Override
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        return context.uriToResolve == null;
    }

    @Override
    public String[] engineGetPropertyKeys() {
        return new String[0];
    }
}

