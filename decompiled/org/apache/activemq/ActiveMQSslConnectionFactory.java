/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.jms.JMSException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.JMSExceptionSupport;

public class ActiveMQSslConnectionFactory
extends ActiveMQConnectionFactory {
    protected KeyManager[] keyManager;
    protected TrustManager[] trustManager;
    protected SecureRandom secureRandom;
    protected String trustStoreType = KeyStore.getDefaultType();
    protected String trustStore;
    protected String trustStorePassword;
    protected String keyStoreType = KeyStore.getDefaultType();
    protected String keyStore;
    protected String keyStorePassword;
    protected String keyStoreKeyPassword;

    public ActiveMQSslConnectionFactory() {
    }

    public ActiveMQSslConnectionFactory(String brokerURL) {
        super(brokerURL);
    }

    public ActiveMQSslConnectionFactory(URI brokerURL) {
        super(brokerURL);
    }

    public void setKeyAndTrustManagers(KeyManager[] km4, TrustManager[] tm, SecureRandom random) {
        this.keyManager = km4;
        this.trustManager = tm;
        this.secureRandom = random;
    }

    @Override
    protected Transport createTransport() throws JMSException {
        SslContext existing = SslContext.getCurrentSslContext();
        try {
            if (this.keyStore != null || this.trustStore != null) {
                this.keyManager = this.createKeyManager();
                this.trustManager = this.createTrustManager();
            }
            if (this.keyManager != null || this.trustManager != null) {
                SslContext.setCurrentSslContext(new SslContext(this.keyManager, this.trustManager, this.secureRandom));
            }
            Transport transport = super.createTransport();
            return transport;
        }
        catch (Exception e) {
            throw JMSExceptionSupport.create("Could not create Transport. Reason: " + e, e);
        }
        finally {
            SslContext.setCurrentSslContext(existing);
        }
    }

    protected TrustManager[] createTrustManager() throws Exception {
        TrustManager[] trustStoreManagers = null;
        KeyStore trustedCertStore = KeyStore.getInstance(this.getTrustStoreType());
        if (this.trustStore != null) {
            try (InputStream tsStream = this.getInputStream(this.trustStore);){
                trustedCertStore.load(tsStream, this.trustStorePassword.toCharArray());
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(trustedCertStore);
                trustStoreManagers = tmf.getTrustManagers();
            }
        }
        return trustStoreManagers;
    }

    protected KeyManager[] createKeyManager() throws Exception {
        byte[] sslCert;
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance(this.getKeyStoreType());
        KeyManager[] keystoreManagers = null;
        if (this.keyStore != null && (sslCert = this.loadClientCredential(this.keyStore)) != null && sslCert.length > 0) {
            try (ByteArrayInputStream bin = new ByteArrayInputStream(sslCert);){
                ks.load(bin, this.keyStorePassword.toCharArray());
                kmf.init(ks, this.keyStoreKeyPassword != null ? this.keyStoreKeyPassword.toCharArray() : this.keyStorePassword.toCharArray());
                keystoreManagers = kmf.getKeyManagers();
            }
        }
        return keystoreManagers;
    }

    /*
     * Exception decompiling
     */
    protected byte[] loadClientCredential(String fileName) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected InputStream getInputStream(String urlOrResource) throws IOException {
        try {
            File ifile = new File(urlOrResource);
            if (ifile.exists()) {
                return new FileInputStream(ifile);
            }
        }
        catch (Exception ifile) {
            // empty catch block
        }
        InputStream ins = null;
        try {
            URL url2 = new URL(urlOrResource);
            ins = url2.openStream();
            if (ins != null) {
                return ins;
            }
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        if (ins == null) {
            ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(urlOrResource);
        }
        if (ins == null) {
            throw new IOException("Could not load resource: " + urlOrResource);
        }
        return ins;
    }

    public String getTrustStoreType() {
        return this.trustStoreType;
    }

    public void setTrustStoreType(String type) {
        this.trustStoreType = type;
    }

    public String getTrustStore() {
        return this.trustStore;
    }

    public void setTrustStore(String trustStore) throws Exception {
        this.trustStore = trustStore;
        this.trustManager = null;
    }

    public String getTrustStorePassword() {
        return this.trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public String getKeyStoreType() {
        return this.keyStoreType;
    }

    public void setKeyStoreType(String type) {
        this.keyStoreType = type;
    }

    public String getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(String keyStore) throws Exception {
        this.keyStore = keyStore;
        this.keyManager = null;
    }

    public String getKeyStorePassword() {
        return this.keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreKeyPassword() {
        return this.keyStoreKeyPassword;
    }

    public void setKeyStoreKeyPassword(String keyStoreKeyPassword) {
        this.keyStoreKeyPassword = keyStoreKeyPassword;
    }
}

