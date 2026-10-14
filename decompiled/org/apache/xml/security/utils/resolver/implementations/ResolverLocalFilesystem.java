/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils.resolver.implementations;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolverLocalFilesystem
extends ResourceResolverSpi {
    private static final int FILE_URI_LENGTH = "file:/".length();
    private static final Logger LOG = LoggerFactory.getLogger(ResolverLocalFilesystem.class);

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context) throws ResourceResolverException {
        try {
            URI uriNew = ResolverLocalFilesystem.getNewURI(context.uriToResolve, context.baseUri);
            String fileName = ResolverLocalFilesystem.translateUriToFilename(uriNew.toString());
            InputStream inputStream2 = Files.newInputStream(Paths.get(fileName, new String[0]), new OpenOption[0]);
            XMLSignatureInput result = new XMLSignatureInput(inputStream2);
            result.setSecureValidation(context.secureValidation);
            result.setSourceURI(uriNew.toString());
            return result;
        }
        catch (Exception e) {
            throw new ResourceResolverException(e, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
        }
    }

    private static String translateUriToFilename(String uri) {
        String subStr = uri.substring(FILE_URI_LENGTH);
        if (subStr.indexOf("%20") > -1) {
            int offset = 0;
            int index = 0;
            StringBuilder temp = new StringBuilder(subStr.length());
            do {
                if ((index = subStr.indexOf("%20", offset)) == -1) {
                    temp.append(subStr.substring(offset));
                    continue;
                }
                temp.append(subStr.substring(offset, index));
                temp.append(' ');
                offset = index + 3;
            } while (index != -1);
            subStr = temp.toString();
        }
        if (subStr.charAt(1) == ':') {
            return subStr;
        }
        return "/" + subStr;
    }

    @Override
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            return false;
        }
        if (context.uriToResolve.equals("") || context.uriToResolve.charAt(0) == '#' || context.uriToResolve.startsWith("http:")) {
            return false;
        }
        try {
            LOG.debug("I was asked whether I can resolve {}", (Object)context.uriToResolve);
            if (context.uriToResolve.startsWith("file:") || context.baseUri.startsWith("file:")) {
                LOG.debug("I state that I can resolve {}", (Object)context.uriToResolve);
                return true;
            }
        }
        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
        LOG.debug("But I can't");
        return false;
    }

    private static URI getNewURI(String uri, String baseURI) throws URISyntaxException {
        URI newUri = null;
        newUri = baseURI == null || "".equals(baseURI) ? new URI(uri) : new URI(baseURI).resolve(uri);
        if (newUri.getFragment() != null) {
            URI uriNewNoFrag = new URI(newUri.getScheme(), newUri.getSchemeSpecificPart(), null);
            return uriNewNoFrag;
        }
        return newUri;
    }
}

