/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.keys.storage.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.keys.storage.StorageResolverException;
import org.apache.xml.security.keys.storage.StorageResolverSpi;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertsInFilesystemDirectoryResolver
extends StorageResolverSpi {
    private static final Logger LOG = LoggerFactory.getLogger(CertsInFilesystemDirectoryResolver.class);
    private String merlinsCertificatesDir;
    private List<X509Certificate> certs = new ArrayList<X509Certificate>();

    public CertsInFilesystemDirectoryResolver(String directoryName) throws StorageResolverException {
        this.merlinsCertificatesDir = directoryName;
        this.readCertsFromHarddrive();
    }

    private void readCertsFromHarddrive() throws StorageResolverException {
        File certDir = new File(this.merlinsCertificatesDir);
        ArrayList<String> al = new ArrayList<String>();
        String[] names = certDir.list();
        if (names != null) {
            for (int i = 0; i < names.length; ++i) {
                String currentFileName = names[i];
                if (!currentFileName.endsWith(".crt")) continue;
                al.add(names[i]);
            }
        }
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        }
        catch (CertificateException ex) {
            throw new StorageResolverException(ex);
        }
        for (int i = 0; i < al.size(); ++i) {
            String dn;
            boolean added;
            block27: {
                String filename = certDir.getAbsolutePath() + File.separator + (String)al.get(i);
                added = false;
                dn = null;
                try (InputStream inputStream2 = Files.newInputStream(Paths.get(filename, new String[0]), new OpenOption[0]);){
                    X509Certificate cert = (X509Certificate)cf.generateCertificate(inputStream2);
                    cert.checkValidity();
                    this.certs.add(cert);
                    dn = cert.getSubjectX500Principal().getName();
                    added = true;
                }
                catch (FileNotFoundException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Could not add certificate from file " + filename, ex);
                    }
                }
                catch (CertificateNotYetValidException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Could not add certificate from file " + filename, ex);
                    }
                }
                catch (CertificateExpiredException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Could not add certificate from file " + filename, ex);
                    }
                }
                catch (CertificateException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Could not add certificate from file " + filename, ex);
                    }
                }
                catch (IOException ex) {
                    if (!LOG.isDebugEnabled()) break block27;
                    LOG.debug("Could not add certificate from file " + filename, ex);
                }
            }
            if (!added) continue;
            LOG.debug("Added certificate: {}", (Object)dn);
        }
    }

    @Override
    public Iterator<Certificate> getIterator() {
        return new FilesystemIterator(this.certs);
    }

    public static void main(String[] unused2) throws Exception {
        CertsInFilesystemDirectoryResolver krs = new CertsInFilesystemDirectoryResolver("data/ie/baltimore/merlin-examples/merlin-xmldsig-eighteen/certs");
        Iterator<Certificate> i = krs.getIterator();
        while (i.hasNext()) {
            X509Certificate cert = (X509Certificate)i.next();
            byte[] ski = XMLX509SKI.getSKIBytesFromCert(cert);
            System.out.println();
            System.out.println("Base64(SKI())=                 \"" + XMLUtils.encodeToString(ski) + "\"");
            System.out.println("cert.getSerialNumber()=        \"" + cert.getSerialNumber().toString() + "\"");
            System.out.println("cert.getSubjectX500Principal().getName()= \"" + cert.getSubjectX500Principal().getName() + "\"");
            System.out.println("cert.getIssuerX500Principal().getName()=  \"" + cert.getIssuerX500Principal().getName() + "\"");
        }
    }

    private static class FilesystemIterator
    implements Iterator<Certificate> {
        private List<X509Certificate> certs;
        private int i;

        public FilesystemIterator(List<X509Certificate> certs) {
            this.certs = certs;
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            return this.i < this.certs.size();
        }

        @Override
        public Certificate next() {
            return this.certs.get(this.i++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can't remove keys from KeyStore");
        }
    }
}

