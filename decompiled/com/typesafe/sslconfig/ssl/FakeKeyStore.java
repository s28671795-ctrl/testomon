/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.FakeKeyStore$;
import com.typesafe.sslconfig.ssl.FakeKeyStore$KeystoreSettings$;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import javax.net.ssl.KeyManagerFactory;
import scala.Function1;
import scala.Option$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import sun.security.x509.X509CertImpl;

@ScalaSignature(bytes="\u0006\u0005\u0005\rx!\u0002\u001a4\u0011\u0003ad!\u0002 4\u0011\u0003y\u0004\"\u0002$\u0002\t\u00039\u0005b\u0002%\u0002\u0005\u0004%I!\u0013\u0005\u0007!\u0006\u0001\u000b\u0011\u0002&\b\u000bE\u000b\u0001\u0012\u0001*\u0007\u000bQ\u000b\u0001\u0012A+\t\u000b\u00193A\u0011\u0001,\b\u000b]3\u0001\u0012\u0001-\u0007\u000bi3\u0001\u0012A.\t\u000b\u0019KA\u0011\u0001/\t\u000fuK!\u0019!C\u0001=\"1q-\u0003Q\u0001\n}Cq\u0001[\u0005C\u0002\u0013\u0005a\f\u0003\u0004j\u0013\u0001\u0006Ia\u0018\u0005\bU\u001a\u0011\r\u0011\"\u0001_\u0011\u0019Yg\u0001)A\u0005?\"9AN\u0002b\u0001\n\u0003I\u0005BB7\u0007A\u0003%!jB\u0003o\u0003!\u0005qNB\u0003q\u0003!\u0005\u0011\u000fC\u0003G)\u0011\u0005!\u000fC\u0004t)\t\u0007I\u0011\u0001;\t\r}$\u0002\u0015!\u0003v\u0011!\t\t\u0001\u0006b\u0001\n\u0003q\u0006bBA\u0002)\u0001\u0006Ia\u0018\u0005\t\u0003\u000b!\"\u0019!C\u0001=\"9\u0011q\u0001\u000b!\u0002\u0013y\u0006\"CA\u0005)\t\u0007I\u0011AA\u0006\u0011!\t\u0019\u0002\u0006Q\u0001\n\u00055\u0001\u0002CA\u000b)\t\u0007I\u0011\u00010\t\u000f\u0005]A\u0003)A\u0005?\"A\u0011\u0011\u0004\u000bC\u0002\u0013\u0005\u0011\nC\u0004\u0002\u001cQ\u0001\u000b\u0011\u0002&\t\u000f\u0005u\u0011\u0001\"\u0003\u0002 !9\u0011QE\u0001\u0005\u0002\u0005\u001d\u0002bBA\u001b\u0003\u0011\u0005\u0011q\u0007\u0004\u0006}M\u0012\u0011q\n\u0005\u000b\u0003#*#\u0011!Q\u0001\n\u0005M\u0003B\u0002$&\t\u0003\ty\u0006C\u0005\u0002f\u0015\u0012\r\u0011\"\u0003\u0002h!A\u0011qN\u0013!\u0002\u0013\tI\u0007C\u0004\u0002r\u0015\"\t!a\u001d\t\u0011\u0005\u0015U\u0005\"\u00014\u0003\u000fCq!a%&\t\u0013\t)\n\u0003\u0005\u0002\u001c\u0016\"\taMAO\u0011\u001d\tI+\nC\u0001\u0003WCq!a,&\t\u0013\t\t\fC\u0004\u0002>\u0016\"\t!a0\t\u000f\u0005UW\u0005\"\u0001\u0002X\u0006aa)Y6f\u0017\u0016L8\u000b^8sK*\u0011A'N\u0001\u0004gNd'B\u0001\u001c8\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u00029s\u0005AA/\u001f9fg\u00064WMC\u0001;\u0003\r\u0019w.\\\u0002\u0001!\ti\u0014!D\u00014\u000511\u0015m[3LKf\u001cFo\u001c:f'\t\t\u0001\t\u0005\u0002B\t6\t!IC\u0001D\u0003\u0015\u00198-\u00197b\u0013\t)%I\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003q\na\"R'Q)f{\u0006+Q*T/>\u0013F)F\u0001K!\r\t5*T\u0005\u0003\u0019\n\u0013Q!\u0011:sCf\u0004\"!\u0011(\n\u0005=\u0013%\u0001B\"iCJ\fq\"R'Q)f{\u0006+Q*T/>\u0013F\tI\u0001\u000b'\u0016dgmU5h]\u0016$\u0007CA*\u0007\u001b\u0005\t!AC*fY\u001a\u001c\u0016n\u001a8fIN\u0011a\u0001\u0011\u000b\u0002%\u0006)\u0011\t\\5bgB\u0011\u0011,C\u0007\u0002\r\t)\u0011\t\\5bgN\u0011\u0011\u0002\u0011\u000b\u00021\u0006\u0001BO];ti\u0016$7)\u001a:u\u000b:$(/_\u000b\u0002?B\u0011\u0001-Z\u0007\u0002C*\u0011!mY\u0001\u0005Y\u0006twMC\u0001e\u0003\u0011Q\u0017M^1\n\u0005\u0019\f'AB*ue&tw-A\tueV\u001cH/\u001a3DKJ$XI\u001c;ss\u0002\nq\u0002\u0015:jm\u0006$XmS3z\u000b:$(/_\u0001\u0011!JLg/\u0019;f\u0017\u0016LXI\u001c;ss\u0002\n\u0011\u0003R5ti&tw-^5tQ\u0016$g*Y7f\u0003I!\u0015n\u001d;j]\u001e,\u0018n\u001d5fI:\u000bW.\u001a\u0011\u0002\u0017-,\u0017\u0010U1tg^|'\u000fZ\u0001\rW\u0016L\b+Y:to>\u0014H\rI\u0001\u0011\u0017\u0016L8\u000f^8sKN+G\u000f^5oON\u0004\"a\u0015\u000b\u0003!-+\u0017p\u001d;pe\u0016\u001cV\r\u001e;j]\u001e\u001c8C\u0001\u000bA)\u0005y\u0017!E$f]\u0016\u0014\u0018\r^3e\u0017\u0016L8\u000b^8sKV\tQ\u000f\u0005\u0002w{:\u0011qo\u001f\t\u0003q\nk\u0011!\u001f\u0006\u0003un\na\u0001\u0010:p_Rt\u0014B\u0001?C\u0003\u0019\u0001&/\u001a3fM&\u0011aM \u0006\u0003y\n\u000b!cR3oKJ\fG/\u001a3LKf\u001cFo\u001c:fA\u000512+[4oCR,(/Z!mO>\u0014\u0018\u000e\u001e5n\u001d\u0006lW-A\fTS\u001et\u0017\r^;sK\u0006cwm\u001c:ji\"lg*Y7fA\u0005!2*Z=QC&\u0014\u0018\t\\4pe&$\b.\u001c(b[\u0016\fQcS3z!\u0006L'/\u00117h_JLG\u000f[7OC6,\u0007%\u0001\tLKf\u0004\u0016-\u001b:LKfdUM\\4uQV\u0011\u0011Q\u0002\t\u0004\u0003\u0006=\u0011bAA\t\u0005\n\u0019\u0011J\u001c;\u0002#-+\u0017\u0010U1je.+\u0017\u0010T3oORD\u0007%\u0001\u0007LKf\u001cHo\u001c:f)f\u0004X-A\u0007LKf\u001cHo\u001c:f)f\u0004X\rI\u0001\u0011W\u0016L8\u000f^8sKB\u000b7o]<pe\u0012\f\u0011c[3zgR|'/\u001a)bgN<xN\u001d3!\u0003A1\u0017\u000e\\3J]\u0012+g/T8eK\u0012K'\u000fF\u0002v\u0003CAa!a\t#\u0001\u0004)\u0018\u0001\u00034jY\u0016t\u0017-\\3\u0002!\u001d,g.\u001a:bi\u0016\\U-_*u_J,WCAA\u0015!\u0011\tY#!\r\u000e\u0005\u00055\"bAA\u0018G\u0006A1/Z2ve&$\u00180\u0003\u0003\u00024\u00055\"\u0001C&fsN#xN]3\u00027\r\u0014X-\u0019;f'\u0016dgmU5h]\u0016$7)\u001a:uS\u001aL7-\u0019;f)\u0011\tI$!\u0012\u0011\t\u0005m\u0012\u0011I\u0007\u0003\u0003{QA!a\u0010\u0002.\u0005!1-\u001a:u\u0013\u0011\t\u0019%!\u0010\u0003\u001fa+\u0004'O\"feRLg-[2bi\u0016Dq!a\u0012%\u0001\u0004\tI%A\u0004lKf\u0004\u0016-\u001b:\u0011\t\u0005-\u00121J\u0005\u0005\u0003\u001b\niCA\u0004LKf\u0004\u0016-\u001b:\u0014\u0005\u0015\u0002\u0015\u0001C7l\u0019><w-\u001a:\u0011\t\u0005U\u00131L\u0007\u0003\u0003/R1!!\u00176\u0003\u0011)H/\u001b7\n\t\u0005u\u0013q\u000b\u0002\u000e\u0019><w-\u001a:GC\u000e$xN]=\u0015\t\u0005\u0005\u00141\r\t\u0003{\u0015Bq!!\u0015(\u0001\u0004\t\u0019&\u0001\u0004m_\u001e<WM]\u000b\u0003\u0003S\u0002B!!\u0016\u0002l%!\u0011QNA,\u00051qu\u000eR3qg2{wmZ3s\u0003\u001dawnZ4fe\u0002\n1cZ3u\u0017\u0016L8\u000b^8sK\u001aKG.\u001a)bi\"$B!!\u001e\u0002\u0002B!\u0011qOA?\u001b\t\tIHC\u0002\u0002|\r\f!![8\n\t\u0005}\u0014\u0011\u0010\u0002\u0005\r&dW\rC\u0004\u0002\u0004*\u0002\r!!\u001e\u0002\u000f\u0005\u0004\b\u000fU1uQ\u0006q1\u000f[8vY\u0012<UM\\3sCR,G\u0003BAE\u0003\u001f\u00032!QAF\u0013\r\tiI\u0011\u0002\b\u0005>|G.Z1o\u0011\u001d\t\tj\u000ba\u0001\u0003k\nAb[3z'R|'/\u001a$jY\u0016\fA\u0002\\8bI.+\u0017p\u0015;pe\u0016$B!!\u000b\u0002\u0018\"9\u0011\u0011\u0014\u0017A\u0002\u0005U\u0014\u0001\u00024jY\u0016\f!cY3si&4\u0017nY1uKR{wnV3bWR!\u0011\u0011RAP\u0011\u001d\t\t+\fa\u0001\u0003G\u000b\u0011a\u0019\t\u0005\u0003w\t)+\u0003\u0003\u0002(\u0006u\"aC\"feRLg-[2bi\u0016\fab\u0019:fCR,7*Z=Ti>\u0014X\r\u0006\u0003\u0002*\u00055\u0006bBAB]\u0001\u0007\u0011QO\u0001\u001eGJ,\u0017\r^3LKf\u001cHo\u001c:f!\u0006\u0014XM\u001c;ESJ,7\r^8ssR!\u00111WA]!\r\t\u0015QW\u0005\u0004\u0003o\u0013%\u0001B+oSRDq!a/0\u0001\u0004\t)(A\u0006lKf\u001cFo\u001c:f\t&\u0014\u0018!E6fs6\u000bg.Y4fe\u001a\u000b7\r^8ssR!\u0011\u0011YAj!\u0011\t\u0019-a4\u000e\u0005\u0005\u0015'b\u0001\u001b\u0002H*!\u0011\u0011ZAf\u0003\rqW\r\u001e\u0006\u0003\u0003\u001b\fQA[1wCbLA!!5\u0002F\n\t2*Z=NC:\fw-\u001a:GC\u000e$xN]=\t\u000f\u0005\r\u0005\u00071\u0001\u0002v\u0005a1\r\\8tKF+\u0018.\u001a;msR!\u00111WAm\u0011\u001d\tY.\ra\u0001\u0003;\f\u0011b\u00197pg\u0016\f'\r\\3\u0011\t\u0005]\u0014q\\\u0005\u0005\u0003C\fIHA\u0005DY>\u001cX-\u00192mK\u0002")
public final class FakeKeyStore {
    private final NoDepsLogger logger;

    public static X509Certificate createSelfSignedCertificate(KeyPair keyPair) {
        return FakeKeyStore$.MODULE$.createSelfSignedCertificate(keyPair);
    }

    public static KeyStore generateKeyStore() {
        return FakeKeyStore$.MODULE$.generateKeyStore();
    }

    private NoDepsLogger logger() {
        return this.logger;
    }

    public File getKeyStoreFilePath(File appPath) {
        return new File(appPath, FakeKeyStore$KeystoreSettings$.MODULE$.GeneratedKeyStore());
    }

    public boolean shouldGenerate(File keyStoreFile) {
        if (!keyStoreFile.exists()) {
            return true;
        }
        KeyStore store = this.loadKeyStore(keyStoreFile);
        return ((IterableOnceOps)JavaConverters$.MODULE$.enumerationAsScalaIteratorConverter(store.aliases()).asScala()).exists((Function1<String, Object> & Serializable)alias -> BoxesRunTime.boxToBoolean(FakeKeyStore.$anonfun$shouldGenerate$1(this, store, alias)));
    }

    /*
     * WARNING - void declaration
     */
    private KeyStore loadKeyStore(File file) {
        void var2_2;
        KeyStore keyStore = KeyStore.getInstance(FakeKeyStore$KeystoreSettings$.MODULE$.KeystoreType());
        InputStream in = Files.newInputStream(file.toPath(), new OpenOption[0]);
        try {
            keyStore.load(in, "".toCharArray());
        }
        finally {
            this.closeQuietly(in);
        }
        return var2_2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean certificateTooWeak(Certificate c) {
        RSAPublicKey key = (RSAPublicKey)c.getPublicKey();
        if (key.getModulus().bitLength() < FakeKeyStore$KeystoreSettings$.MODULE$.KeyPairKeyLength()) return true;
        String string = ((X509CertImpl)c).getSigAlgName();
        String string2 = FakeKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName();
        if (string == null) {
            if (string2 == null) return false;
            return true;
        } else if (string.equals(string2)) return false;
        return true;
    }

    public KeyStore createKeyStore(File appPath) {
        KeyStore keyStore;
        File keyStoreFile = this.getKeyStoreFilePath(appPath);
        File keyStoreDir = keyStoreFile.getParentFile();
        this.createKeystoreParentDirectory(keyStoreDir);
        FakeKeyStore fakeKeyStore = this;
        synchronized (fakeKeyStore) {
            KeyStore keyStore2;
            if (this.shouldGenerate(keyStoreFile)) {
                this.logger().info(new StringBuilder(146).append("Generating HTTPS key pair in ").append(keyStoreFile.getAbsolutePath()).append(" - this may take some time. If nothing happens, try moving the mouse/typing on the keyboard to generate some entropy.").toString());
                KeyStore freshKeyStore = FakeKeyStore$.MODULE$.generateKeyStore();
                OutputStream out = Files.newOutputStream(keyStoreFile.toPath(), new OpenOption[0]);
                try {
                    freshKeyStore.store(out, FakeKeyStore$KeystoreSettings$.MODULE$.keystorePassword());
                }
                finally {
                    this.closeQuietly(out);
                }
                keyStore2 = freshKeyStore;
            } else {
                KeyStore loadedKeyStore = this.loadKeyStore(keyStoreFile);
                this.logger().info(new StringBuilder(29).append("HTTPS key pair generated in ").append(keyStoreFile.getAbsolutePath()).append(".").toString());
                keyStore2 = loadedKeyStore;
            }
            keyStore = keyStore2;
        }
        KeyStore keyStore3 = keyStore;
        return keyStore3;
    }

    private void createKeystoreParentDirectory(File keyStoreDir) {
        if (keyStoreDir.mkdirs()) {
            this.logger().debug(new StringBuilder(54).append("Parent directory for keystore successfully created at ").append(keyStoreDir.getAbsolutePath()).toString());
        } else if (keyStoreDir.exists() && keyStoreDir.isDirectory()) {
            this.logger().debug(new StringBuilder(43).append("No need to create ").append(keyStoreDir).append(" since it already exists.").toString());
        } else {
            if (keyStoreDir.exists() && keyStoreDir.isFile()) {
                throw new IllegalStateException(new StringBuilder(88).append(keyStoreDir).append(" exists, but it is NOT a directory, making it not possible to generate a key store file.").toString());
            }
            throw new IllegalStateException(new StringBuilder(70).append("Failed to create ").append(keyStoreDir).append(". Check if there is permission to create such folder.").toString());
        }
    }

    /*
     * WARNING - void declaration
     */
    public KeyManagerFactory keyManagerFactory(File appPath) {
        void var3_3;
        KeyStore keyStore = this.createKeyStore(appPath);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, FakeKeyStore$KeystoreSettings$.MODULE$.keystorePassword());
        return var3_3;
    }

    public void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (IOException e) {
            this.logger().warn(new StringBuilder(29).append("Error closing stream. Cause: ").append(e).toString());
        }
    }

    public static final /* synthetic */ boolean $anonfun$shouldGenerate$1(FakeKeyStore $this, KeyStore store$1, String alias) {
        return Option$.MODULE$.apply(store$1.getCertificate(alias)).exists((Function1<Certificate, Object> & Serializable)c -> BoxesRunTime.boxToBoolean($this.certificateTooWeak(c)));
    }

    public FakeKeyStore(LoggerFactory mkLogger) {
        this.logger = mkLogger.apply(this.getClass());
    }
}

