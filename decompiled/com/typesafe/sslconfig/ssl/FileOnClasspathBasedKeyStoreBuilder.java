/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyStoreBuilder;
import com.typesafe.sslconfig.ssl.KeystoreFormats$;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
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

@ScalaSignature(bytes="\u0006\u0005\t4A\u0001C\u0005\u0001%!AQ\u0004\u0001B\u0001B\u0003%a\u0004\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003\u001f\u0011!Q\u0003A!A!\u0002\u0013Y\u0003\"\u0002\u001b\u0001\t\u0003)\u0004\"\u0002\u001e\u0001\t\u0003Y\u0004\"\u0002#\u0001\t\u0003)\u0005\"\u0002)\u0001\t\u0003\t&a\t$jY\u0016|en\u00117bgN\u0004\u0018\r\u001e5CCN,GmS3z'R|'/\u001a\"vS2$WM\u001d\u0006\u0003\u0015-\t1a]:m\u0015\taQ\"A\u0005tg2\u001cwN\u001c4jO*\u0011abD\u0001\tif\u0004Xm]1gK*\t\u0001#A\u0002d_6\u001c\u0001aE\u0002\u0001'e\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011a!\u00118z%\u00164\u0007C\u0001\u000e\u001c\u001b\u0005I\u0011B\u0001\u000f\n\u0005=YU-_*u_J,')^5mI\u0016\u0014\u0018\u0001D6fsN#xN]3UsB,\u0007CA\u0010'\u001d\t\u0001C\u0005\u0005\u0002\"+5\t!E\u0003\u0002$#\u00051AH]8pizJ!!J\u000b\u0002\rA\u0013X\rZ3g\u0013\t9\u0003F\u0001\u0004TiJLgn\u001a\u0006\u0003KU\t\u0001BZ5mKB\u000bG\u000f[\u0001\ta\u0006\u001c8o^8sIB\u0019A\u0003\f\u0018\n\u00055*\"AB(qi&|g\u000eE\u0002\u0015_EJ!\u0001M\u000b\u0003\u000b\u0005\u0013(/Y=\u0011\u0005Q\u0011\u0014BA\u001a\u0016\u0005\u0011\u0019\u0005.\u0019:\u0002\rqJg.\u001b;?)\u00111t\u0007O\u001d\u0011\u0005i\u0001\u0001\"B\u000f\u0005\u0001\u0004q\u0002\"B\u0015\u0005\u0001\u0004q\u0002\"\u0002\u0016\u0005\u0001\u0004Y\u0013!\u00022vS2$G#\u0001\u001f\u0011\u0005u\u0012U\"\u0001 \u000b\u0005}\u0002\u0015\u0001C:fGV\u0014\u0018\u000e^=\u000b\u0003\u0005\u000bAA[1wC&\u00111I\u0010\u0002\t\u0017\u0016L8\u000b^8sK\u0006)\"-^5mI\u001a\u0013x.\\&fsN$xN]3GS2,Gc\u0001\u001fG\u0011\")qI\u0002a\u0001=\u0005I1\u000f^8sKRK\b/\u001a\u0005\u0006\u0013\u001a\u0001\rAS\u0001\u0003SN\u0004\"a\u0013(\u000e\u00031S!!\u0014!\u0002\u0005%|\u0017BA(M\u0005-Ie\u000e];u'R\u0014X-Y7\u0002!I,\u0017\rZ\"feRLg-[2bi\u0016\u001cHC\u0001*b!\r\u0019\u0006l\u0017\b\u0003)Zs!!I+\n\u0003YI!aV\u000b\u0002\u000fA\f7m[1hK&\u0011\u0011L\u0017\u0002\t\u0013R,'/\u00192mK*\u0011q+\u0006\t\u00039~k\u0011!\u0018\u0006\u0003=z\nAaY3si&\u0011\u0001-\u0018\u0002\f\u0007\u0016\u0014H/\u001b4jG\u0006$X\rC\u0003J\u000f\u0001\u0007!\n")
public class FileOnClasspathBasedKeyStoreBuilder
implements KeyStoreBuilder {
    private final String keyStoreType;
    private final String filePath;
    private final Option<char[]> password;

    @Override
    public KeyStore build() {
        KeyStore keyStore;
        block3: {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(this.filePath);
            Predef$.MODULE$.require(is != null, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(48).append("Key store file ").append($this.filePath).append(" was not found on the class path!").toString());
            String string = this.keyStoreType;
            switch (string == null ? 0 : string.hashCode()) {
                case 79096: {
                    if (!"PEM".equals(string)) break;
                    Iterable<Certificate> certs = this.readCertificates(is);
                    keyStore = KeystoreFormats$.MODULE$.loadCertificates(certs);
                    break block3;
                }
            }
            keyStore = this.buildFromKeystoreFile(string, is);
        }
        return keyStore;
    }

    public KeyStore buildFromKeystoreFile(String storeType, InputStream is) {
        KeyStore keyStore;
        try (BufferedInputStream inputStream2 = new BufferedInputStream(is);){
            String storeType2 = this.keyStoreType;
            KeyStore store = KeyStore.getInstance(storeType2);
            store.load(inputStream2, (char[])this.password.orNull($less$colon$less$.MODULE$.refl()));
            keyStore = store;
        }
        return keyStore;
    }

    public Iterable<Certificate> readCertificates(InputStream is) {
        Iterable<Certificate> iterable;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        try (BufferedInputStream bis = new BufferedInputStream(is);){
            iterable = JavaConverters$.MODULE$.collectionAsScalaIterableConverter(cf.generateCertificates(bis)).asScala();
        }
        return iterable;
    }

    public FileOnClasspathBasedKeyStoreBuilder(String keyStoreType, String filePath, Option<char[]> password) {
        this.keyStoreType = keyStoreType;
        this.filePath = filePath;
        this.password = password;
    }
}

