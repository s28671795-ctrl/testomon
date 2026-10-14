/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl.resourceResolvers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.ResourceResolverLookup;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class ResolverHttp
implements ResourceResolver,
ResourceResolverLookup {
    private static Proxy proxy;
    private String uri;
    private String baseURI;
    private Pattern pattern = Pattern.compile("^http[s]?://.*");

    public ResolverHttp() {
    }

    public ResolverHttp(String uri, String baseURI) {
        this.uri = uri;
        this.baseURI = baseURI;
    }

    public static void setProxy(Proxy proxy) {
        ResolverHttp.proxy = proxy;
    }

    @Override
    public ResourceResolverLookup canResolve(String uri, String baseURI) {
        if (uri == null) {
            return null;
        }
        if (this.pattern.matcher(uri).matches() || baseURI != null && this.pattern.matcher(baseURI).matches()) {
            return this;
        }
        return null;
    }

    @Override
    public ResourceResolver newInstance(String uri, String baseURI) {
        return new ResolverHttp(uri, baseURI);
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
        try {
            URI tmp = this.baseURI == null || "".equals(this.baseURI) ? new URI(this.uri) : new URI(this.baseURI).resolve(this.uri);
            if (tmp.getFragment() != null) {
                tmp = new URI(tmp.getScheme(), tmp.getSchemeSpecificPart(), null);
            }
            URL url2 = tmp.toURL();
            HttpURLConnection urlConnection = proxy != null ? (HttpURLConnection)url2.openConnection(proxy) : (HttpURLConnection)url2.openConnection();
            return urlConnection.getInputStream();
        }
        catch (MalformedURLException e) {
            throw new XMLSecurityException(e);
        }
        catch (IOException e) {
            throw new XMLSecurityException(e);
        }
        catch (URISyntaxException e) {
            throw new XMLSecurityException(e);
        }
    }
}

