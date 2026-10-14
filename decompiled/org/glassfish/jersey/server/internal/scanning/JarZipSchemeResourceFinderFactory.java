/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.scanning;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.internal.AbstractResourceFinderAdapter;
import org.glassfish.jersey.server.internal.scanning.JarFileScanner;
import org.glassfish.jersey.server.internal.scanning.ResourceFinderException;
import org.glassfish.jersey.server.internal.scanning.UriSchemeResourceFinderFactory;
import org.glassfish.jersey.uri.UriComponent;

final class JarZipSchemeResourceFinderFactory
implements UriSchemeResourceFinderFactory {
    private static final Set<String> SCHEMES = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("jar", "zip", "wsjar")));

    @Override
    public Set<String> getSchemes() {
        return SCHEMES;
    }

    JarZipSchemeResourceFinderFactory() {
    }

    @Override
    public JarZipSchemeScanner create(URI uri, boolean recursive) {
        String ssp = uri.getRawSchemeSpecificPart();
        String jarUrlString = ssp.substring(0, ssp.lastIndexOf(33));
        String parent = ssp.substring(ssp.lastIndexOf(33) + 2);
        try {
            return new JarZipSchemeScanner(this.getInputStream(jarUrlString), parent, recursive);
        }
        catch (IOException e) {
            throw new ResourceFinderException(e);
        }
    }

    private InputStream getInputStream(String jarUrlString) throws IOException {
        try {
            return new URL(jarUrlString).openStream();
        }
        catch (MalformedURLException e) {
            return new FileInputStream(UriComponent.decode(jarUrlString, UriComponent.Type.PATH));
        }
    }

    private class JarZipSchemeScanner
    extends AbstractResourceFinderAdapter {
        private final InputStream inputStream;
        private final JarFileScanner jarFileScanner;

        private JarZipSchemeScanner(InputStream inputStream2, String parent, boolean recursive) throws IOException {
            this.inputStream = inputStream2;
            this.jarFileScanner = new JarFileScanner(inputStream2, parent, recursive);
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = this.jarFileScanner.hasNext();
            if (!hasNext) {
                try {
                    this.inputStream.close();
                }
                catch (IOException e) {
                    Logger.getLogger(JarZipSchemeScanner.class.getName()).log(Level.FINE, "Unable to close jar file.", e);
                }
                return false;
            }
            return true;
        }

        @Override
        public String next() {
            return this.jarFileScanner.next();
        }

        @Override
        public InputStream open() {
            return this.jarFileScanner.open();
        }

        @Override
        public void close() {
            this.jarFileScanner.close();
        }

        @Override
        public void reset() {
            this.jarFileScanner.reset();
        }
    }
}

