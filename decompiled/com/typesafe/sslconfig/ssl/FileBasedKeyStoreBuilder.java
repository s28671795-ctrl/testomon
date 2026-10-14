/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyStoreBuilder;
import com.typesafe.sslconfig.ssl.KeystoreFormats$;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Option;
import scala.Predef$;
import scala.collection.Iterable;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t4A\u0001C\u0005\u0001%!AQ\u0004\u0001B\u0001B\u0003%a\u0004\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003\u001f\u0011!Q\u0003A!A!\u0002\u0013Y\u0003\"\u0002\u001b\u0001\t\u0003)\u0004\"\u0002\u001e\u0001\t\u0003Y\u0004\"\u0002#\u0001\t\u0003)\u0005\"\u0002)\u0001\t\u0003\t&\u0001\u0007$jY\u0016\u0014\u0015m]3e\u0017\u0016L8\u000b^8sK\n+\u0018\u000e\u001c3fe*\u0011!bC\u0001\u0004gNd'B\u0001\u0007\u000e\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u000f\u001f\u0005AA/\u001f9fg\u00064WMC\u0001\u0011\u0003\r\u0019w.\\\u0002\u0001'\r\u00011#\u0007\t\u0003)]i\u0011!\u0006\u0006\u0002-\u0005)1oY1mC&\u0011\u0001$\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0005iYR\"A\u0005\n\u0005qI!aD&fsN#xN]3Ck&dG-\u001a:\u0002\u0019-,\u0017p\u0015;pe\u0016$\u0016\u0010]3\u0011\u0005}1cB\u0001\u0011%!\t\tS#D\u0001#\u0015\t\u0019\u0013#\u0001\u0004=e>|GOP\u0005\u0003KU\ta\u0001\u0015:fI\u00164\u0017BA\u0014)\u0005\u0019\u0019FO]5oO*\u0011Q%F\u0001\tM&dW\rU1uQ\u0006A\u0001/Y:to>\u0014H\rE\u0002\u0015Y9J!!L\u000b\u0003\r=\u0003H/[8o!\r!r&M\u0005\u0003aU\u0011Q!\u0011:sCf\u0004\"\u0001\u0006\u001a\n\u0005M*\"\u0001B\"iCJ\fa\u0001P5oSRtD\u0003\u0002\u001c8qe\u0002\"A\u0007\u0001\t\u000bu!\u0001\u0019\u0001\u0010\t\u000b%\"\u0001\u0019\u0001\u0010\t\u000b)\"\u0001\u0019A\u0016\u0002\u000b\t,\u0018\u000e\u001c3\u0015\u0003q\u0002\"!\u0010\"\u000e\u0003yR!a\u0010!\u0002\u0011M,7-\u001e:jifT\u0011!Q\u0001\u0005U\u00064\u0018-\u0003\u0002D}\tA1*Z=Ti>\u0014X-A\u000bck&dGM\u0012:p[.+\u0017p\u001d;pe\u00164\u0015\u000e\\3\u0015\u0007q2\u0005\nC\u0003H\r\u0001\u0007a$A\u0005ti>\u0014X\rV=qK\")\u0011J\u0002a\u0001\u0015\u0006!a-\u001b7f!\tYe*D\u0001M\u0015\ti\u0005)\u0001\u0002j_&\u0011q\n\u0014\u0002\u0005\r&dW-\u0001\tsK\u0006$7)\u001a:uS\u001aL7-\u0019;fgR\u0011!+\u0019\t\u0004'b[fB\u0001+W\u001d\t\tS+C\u0001\u0017\u0013\t9V#A\u0004qC\u000e\\\u0017mZ3\n\u0005eS&\u0001C%uKJ\f'\r\\3\u000b\u0005]+\u0002C\u0001/`\u001b\u0005i&B\u00010?\u0003\u0011\u0019WM\u001d;\n\u0005\u0001l&aC\"feRLg-[2bi\u0016DQ!S\u0004A\u0002)\u0003")
public class FileBasedKeyStoreBuilder
implements KeyStoreBuilder {
    private final String keyStoreType;
    private final String filePath;
    private final Option<char[]> password;

    @Override
    public KeyStore build() {
        KeyStore keyStore;
        block3: {
            File file = new File(this.filePath);
            Predef$.MODULE$.require(file.exists(), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(31).append("Key store file ").append($this.filePath).append(" does not exist!").toString());
            Predef$.MODULE$.require(file.canRead(), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(33).append("Cannot read from key store file ").append($this.filePath).append("!").toString());
            String string = this.keyStoreType;
            switch (string == null ? 0 : string.hashCode()) {
                case 79096: {
                    if (!"PEM".equals(string)) break;
                    Iterable<Certificate> certs = this.readCertificates(file);
                    keyStore = KeystoreFormats$.MODULE$.loadCertificates(certs);
                    break block3;
                }
            }
            keyStore = this.buildFromKeystoreFile(string, file);
        }
        return keyStore;
    }

    public KeyStore buildFromKeystoreFile(String storeType, File file) {
        KeyStore keyStore;
        try (BufferedInputStream inputStream2 = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));){
            String storeType2 = this.keyStoreType;
            KeyStore store = KeyStore.getInstance(storeType2);
            store.load(inputStream2, (char[])this.password.orNull($less$colon$less$.MODULE$.refl()));
            keyStore = store;
        }
        return keyStore;
    }

    public Iterable<Certificate> readCertificates(File file) {
        Iterable<Certificate> iterable;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream fis = Files.newInputStream(file.toPath(), new OpenOption[0]);
        try (BufferedInputStream bis = new BufferedInputStream(fis);){
            iterable = JavaConverters$.MODULE$.collectionAsScalaIterableConverter(cf.generateCertificates(bis)).asScala();
        }
        return iterable;
    }

    public FileBasedKeyStoreBuilder(String keyStoreType, String filePath, Option<char[]> password) {
        this.keyStoreType = keyStoreType;
        this.filePath = filePath;
        this.password = password;
    }
}

