/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$;
import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$KeystoreSettings$;
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
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;
import javax.net.ssl.KeyManagerFactory;
import scala.Array$;
import scala.Function1;
import scala.Option$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import sun.security.x509.X509CertImpl;

@ScalaSignature(bytes="\u0006\u0005\t}q!B!C\u0011\u0003Ye!B'C\u0011\u0003q\u0005\"B+\u0002\t\u00031\u0006bB,\u0002\u0005\u0004%I\u0001\u0017\u0005\u0007?\u0006\u0001\u000b\u0011B-\b\u000b\u0001\f\u0001\u0012A1\u0007\u000b\r\f\u0001\u0012\u00013\t\u000bU3A\u0011A3\b\u000b\u00194\u0001\u0012A4\u0007\u000b%4\u0001\u0012\u00016\t\u000bUKA\u0011A6\t\u000f1L!\u0019!C\u0001[\"1a/\u0003Q\u0001\n9Dqa^\u0005C\u0002\u0013\u0005Q\u000e\u0003\u0004y\u0013\u0001\u0006IA\u001c\u0005\bs\u001a\u0011\r\u0011\"\u0001n\u0011\u0019Qh\u0001)A\u0005]\"91P\u0002b\u0001\n\u0003A\u0006B\u0002?\u0007A\u0003%\u0011lB\u0003~\u0003!\u0005aP\u0002\u0004\u0000\u0003!\u0005\u0011\u0011\u0001\u0005\u0007+R!\t!a\u0001\b\r\u0019$\u0002\u0012AA\u0003\r\u0019IG\u0003#\u0001\u0002\n!1Qk\u0006C\u0001\u0003\u0017Aq\u0001\\\fC\u0002\u0013\u0005Q\u000e\u0003\u0004w/\u0001\u0006IA\u001c\u0005\bo^\u0011\r\u0011\"\u0001n\u0011\u0019Ax\u0003)A\u0005]\"9\u0011\u0010\u0006b\u0001\n\u0003i\u0007B\u0002>\u0015A\u0003%a\u000eC\u0004|)\t\u0007I\u0011\u0001-\t\rq$\u0002\u0015!\u0003Z\u000f\u001d\ti!\u0001E\u0001\u0003\u001f1q!!\u0005\u0002\u0011\u0003\t\u0019\u0002\u0003\u0004VE\u0011\u0005\u0011Q\u0003\u0005\n\u0003/\u0011#\u0019!C\u0001\u00033A\u0001\"a\f#A\u0003%\u00111\u0004\u0005\t\u0003c\u0011#\u0019!C\u0001[\"9\u00111\u0007\u0012!\u0002\u0013q\u0007\u0002CA\u001bE\t\u0007I\u0011A7\t\u000f\u0005]\"\u0005)A\u0005]\"I\u0011\u0011\b\u0012C\u0002\u0013\u0005\u00111\b\u0005\t\u0003\u0007\u0012\u0003\u0015!\u0003\u0002>!A\u0011Q\t\u0012C\u0002\u0013\u0005Q\u000eC\u0004\u0002H\t\u0002\u000b\u0011\u00028\t\u0011\u0005%#E1A\u0005\u0002aCq!a\u0013#A\u0003%\u0011\fC\u0004\u0002N\u0005!I!a\u0014\t\u000f\u0005U\u0013\u0001\"\u0001\u0002X!A\u0011QM\u0001\u0005\u0002\t\u000b9\u0007C\u0004\u0002\u0004\u0006!I!!\"\u0007\u000b5\u0013%!a#\t\u0015\u00055EG!A!\u0002\u0013\ty\t\u0003\u0004Vi\u0011\u0005\u00111\u0014\u0005\n\u0003C#$\u0019!C\u0005\u0003GC\u0001\"a+5A\u0003%\u0011Q\u0015\u0005\b\u0003[#D\u0011AAX\u0011!\t\t\r\u000eC\u0001\u0005\u0006\r\u0007bBAhi\u0011%\u0011\u0011\u001b\u0005\t\u0003/$D\u0011\u0001\"\u0002Z\"9\u0011Q\u001d\u001b\u0005\u0002\u0005\u001d\bbBAvi\u0011%\u0011Q\u001e\u0005\b\u0003s$D\u0011AA~\u0011\u001d\u0011\t\u0002\u000eC\u0001\u0005'\t1CR1lK\u000eC\u0017-\u001b8fI.+\u0017p\u0015;pe\u0016T!a\u0011#\u0002\u0007M\u001cHN\u0003\u0002F\r\u0006I1o\u001d7d_:4\u0017n\u001a\u0006\u0003\u000f\"\u000b\u0001\u0002^=qKN\fg-\u001a\u0006\u0002\u0013\u0006\u00191m\\7\u0004\u0001A\u0011A*A\u0007\u0002\u0005\n\u0019b)Y6f\u0007\"\f\u0017N\\3e\u0017\u0016L8\u000b^8sKN\u0011\u0011a\u0014\t\u0003!Nk\u0011!\u0015\u0006\u0002%\u0006)1oY1mC&\u0011A+\u0015\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005Y\u0015AD#N!RKv\fU!T'^{%\u000bR\u000b\u00023B\u0019\u0001K\u0017/\n\u0005m\u000b&!B!se\u0006L\bC\u0001)^\u0013\tq\u0016K\u0001\u0003DQ\u0006\u0014\u0018aD#N!RKv\fU!T'^{%\u000b\u0012\u0011\u0002\u0005\r\u000b\u0005C\u00012\u0007\u001b\u0005\t!AA\"B'\t1q\nF\u0001b\u0003\u0015\tE.[1t!\tA\u0017\"D\u0001\u0007\u0005\u0015\tE.[1t'\tIq\nF\u0001h\u0003A!(/^:uK\u0012\u001cUM\u001d;F]R\u0014\u00180F\u0001o!\tyG/D\u0001q\u0015\t\t(/\u0001\u0003mC:<'\"A:\u0002\t)\fg/Y\u0005\u0003kB\u0014aa\u0015;sS:<\u0017!\u0005;skN$X\rZ\"feR,e\u000e\u001e:zA\u0005y\u0001K]5wCR,7*Z=F]R\u0014\u00180\u0001\tQe&4\u0018\r^3LKf,e\u000e\u001e:zA\u0005\tB)[:uS:<W/[:iK\u0012t\u0015-\\3\u0002%\u0011K7\u000f^5oOVL7\u000f[3e\u001d\u0006lW\rI\u0001\fW\u0016L\b+Y:to>\u0014H-\u0001\u0007lKf\u0004\u0016m]:x_J$\u0007%\u0001\u0003Vg\u0016\u0014\bC\u00012\u0015\u0005\u0011)6/\u001a:\u0014\u0005QyE#\u0001@\u0011\u0007\u0005\u001dq#D\u0001\u0015'\t9r\n\u0006\u0002\u0002\u0006\u0005\u00012*Z=ti>\u0014XmU3ui&twm\u001d\t\u0003E\n\u0012\u0001cS3zgR|'/Z*fiRLgnZ:\u0014\u0005\tzECAA\b\u0003E9UM\\3sCR,GmS3z'R|'/Z\u000b\u0003\u00037\u0001B!!\b\u0002,9!\u0011qDA\u0014!\r\t\t#U\u0007\u0003\u0003GQ1!!\nK\u0003\u0019a$o\\8u}%\u0019\u0011\u0011F)\u0002\rA\u0013X\rZ3g\u0013\r)\u0018Q\u0006\u0006\u0004\u0003S\t\u0016AE$f]\u0016\u0014\u0018\r^3e\u0017\u0016L8\u000b^8sK\u0002\nacU5h]\u0006$XO]3BY\u001e|'/\u001b;i[:\u000bW.Z\u0001\u0018'&<g.\u0019;ve\u0016\fEnZ8sSRDWNT1nK\u0002\nAcS3z!\u0006L'/\u00117h_JLG\u000f[7OC6,\u0017!F&fsB\u000b\u0017N]!mO>\u0014\u0018\u000e\u001e5n\u001d\u0006lW\rI\u0001\u0011\u0017\u0016L\b+Y5s\u0017\u0016LH*\u001a8hi\",\"!!\u0010\u0011\u0007A\u000by$C\u0002\u0002BE\u00131!\u00138u\u0003EYU-\u001f)bSJ\\U-\u001f'f]\u001e$\b\u000eI\u0001\r\u0017\u0016L8\u000f^8sKRK\b/Z\u0001\u000e\u0017\u0016L8\u000f^8sKRK\b/\u001a\u0011\u0002!-,\u0017p\u001d;pe\u0016\u0004\u0016m]:x_J$\u0017!E6fsN$xN]3QCN\u001cxo\u001c:eA\u0005\u0001b-\u001b7f\u0013:$UM^'pI\u0016$\u0015N\u001d\u000b\u0005\u00037\t\t\u0006C\u0004\u0002TA\u0002\r!a\u0007\u0002\u0011\u0019LG.\u001a8b[\u0016\f\u0001cZ3oKJ\fG/Z&fsN#xN]3\u0016\u0005\u0005e\u0003\u0003BA.\u0003Cj!!!\u0018\u000b\u0007\u0005}#/\u0001\u0005tK\u000e,(/\u001b;z\u0013\u0011\t\u0019'!\u0018\u0003\u0011-+\u0017p\u0015;pe\u0016\fQc\u0019:fCR,Wk]3s\u0007\u0016\u0014H/\u001b4jG\u0006$X\r\u0006\u0004\u0002j\u0005U\u0014q\u0010\t\u0005\u0003W\n\t(\u0004\u0002\u0002n)!\u0011qNA/\u0003\u0011\u0019WM\u001d;\n\t\u0005M\u0014Q\u000e\u0002\u00101V\u0002\u0014hQ3si&4\u0017nY1uK\"9\u0011q\u000f\u001aA\u0002\u0005e\u0014aC;tKJ\\U-\u001f)bSJ\u0004B!a\u0017\u0002|%!\u0011QPA/\u0005\u001dYU-\u001f)bSJDq!!!3\u0001\u0004\tI(A\u000edKJ$\u0018NZ5dCR,\u0017)\u001e;i_JLG/_&fsB\u000b\u0017N]\u0001\u001bGJ,\u0017\r^3DKJ$\u0018NZ5dCR,\u0017)\u001e;i_JLG/\u001f\u000b\u0005\u0003S\n9\tC\u0004\u0002\nN\u0002\r!!\u001f\u0002\u000f-,\u0017\u0010U1jeN\u0011AgT\u0001\t[.dunZ4feB!\u0011\u0011SAL\u001b\t\t\u0019JC\u0002\u0002\u0016\u0012\u000bA!\u001e;jY&!\u0011\u0011TAJ\u00055aunZ4fe\u001a\u000b7\r^8ssR!\u0011QTAP!\taE\u0007C\u0004\u0002\u000eZ\u0002\r!a$\u0002\r1|wmZ3s+\t\t)\u000b\u0005\u0003\u0002\u0012\u0006\u001d\u0016\u0002BAU\u0003'\u0013ABT8EKB\u001cHj\\4hKJ\fq\u0001\\8hO\u0016\u0014\b%A\nhKR\\U-_*u_J,g)\u001b7f!\u0006$\b\u000e\u0006\u0003\u00022\u0006u\u0006\u0003BAZ\u0003sk!!!.\u000b\u0007\u0005]&/\u0001\u0002j_&!\u00111XA[\u0005\u00111\u0015\u000e\\3\t\u000f\u0005}\u0016\b1\u0001\u00022\u00069\u0011\r\u001d9QCRD\u0017AD:i_VdGmR3oKJ\fG/\u001a\u000b\u0005\u0003\u000b\fY\rE\u0002Q\u0003\u000fL1!!3R\u0005\u001d\u0011un\u001c7fC:Dq!!4;\u0001\u0004\t\t,\u0001\u0007lKf\u001cFo\u001c:f\r&dW-\u0001\u0007m_\u0006$7*Z=Ti>\u0014X\r\u0006\u0003\u0002Z\u0005M\u0007bBAkw\u0001\u0007\u0011\u0011W\u0001\u0005M&dW-\u0001\ndKJ$\u0018NZ5dCR,Gk\\8XK\u0006\\G\u0003BAc\u00037Dq!!8=\u0001\u0004\ty.A\u0001d!\u0011\tY'!9\n\t\u0005\r\u0018Q\u000e\u0002\f\u0007\u0016\u0014H/\u001b4jG\u0006$X-\u0001\bde\u0016\fG/Z&fsN#xN]3\u0015\t\u0005e\u0013\u0011\u001e\u0005\b\u0003\u007fk\u0004\u0019AAY\u0003u\u0019'/Z1uK.+\u0017p\u001d;pe\u0016\u0004\u0016M]3oi\u0012K'/Z2u_JLH\u0003BAx\u0003k\u00042\u0001UAy\u0013\r\t\u00190\u0015\u0002\u0005+:LG\u000fC\u0004\u0002xz\u0002\r!!-\u0002\u0017-,\u0017p\u0015;pe\u0016$\u0015N]\u0001\u0012W\u0016LX*\u00198bO\u0016\u0014h)Y2u_JLH\u0003BA\u007f\u0005\u001f\u0001B!a@\u0003\f5\u0011!\u0011\u0001\u0006\u0004\u0007\n\r!\u0002\u0002B\u0003\u0005\u000f\t1A\\3u\u0015\t\u0011I!A\u0003kCZ\f\u00070\u0003\u0003\u0003\u000e\t\u0005!!E&fs6\u000bg.Y4fe\u001a\u000b7\r^8ss\"9\u0011qX A\u0002\u0005E\u0016\u0001D2m_N,\u0017+^5fi2LH\u0003BAx\u0005+AqAa\u0006A\u0001\u0004\u0011I\"A\u0005dY>\u001cX-\u00192mKB!\u00111\u0017B\u000e\u0013\u0011\u0011i\"!.\u0003\u0013\rcwn]3bE2,\u0007")
public final class FakeChainedKeyStore {
    private final NoDepsLogger logger;

    public static KeyStore generateKeyStore() {
        return FakeChainedKeyStore$.MODULE$.generateKeyStore();
    }

    private NoDepsLogger logger() {
        return this.logger;
    }

    public File getKeyStoreFilePath(File appPath) {
        return new File(appPath, FakeChainedKeyStore$KeystoreSettings$.MODULE$.GeneratedKeyStore());
    }

    public boolean shouldGenerate(File keyStoreFile) {
        if (!keyStoreFile.exists()) {
            return true;
        }
        KeyStore store = this.loadKeyStore(keyStoreFile);
        return ((IterableOnceOps)JavaConverters$.MODULE$.enumerationAsScalaIteratorConverter(store.aliases()).asScala()).exists((Function1<String, Object> & Serializable)alias -> BoxesRunTime.boxToBoolean(FakeChainedKeyStore.$anonfun$shouldGenerate$1(this, store, alias)));
    }

    /*
     * WARNING - void declaration
     */
    private KeyStore loadKeyStore(File file) {
        void var2_2;
        KeyStore keyStore = KeyStore.getInstance(FakeChainedKeyStore$KeystoreSettings$.MODULE$.KeystoreType());
        InputStream in = Files.newInputStream(file.toPath(), new OpenOption[0]);
        try {
            keyStore.load(in, FakeChainedKeyStore$KeystoreSettings$.MODULE$.keystorePassword());
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
        if (key.getModulus().bitLength() < 2048) return true;
        String string = ((X509CertImpl)c).getSigAlgName();
        String string2 = FakeChainedKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName();
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
        FakeChainedKeyStore fakeChainedKeyStore = this;
        synchronized (fakeChainedKeyStore) {
            KeyStore keyStore2;
            if (this.shouldGenerate(keyStoreFile)) {
                this.logger().info(new StringBuilder(146).append("Generating HTTPS key pair in ").append(keyStoreFile.getAbsolutePath()).append(" - this may take some time. If nothing happens, try moving the mouse/typing on the keyboard to generate some entropy.").toString());
                KeyStore freshKeyStore = FakeChainedKeyStore$.MODULE$.generateKeyStore();
                OutputStream out = Files.newOutputStream(keyStoreFile.toPath(), new OpenOption[0]);
                try {
                    freshKeyStore.store(out, Array$.MODULE$.emptyCharArray());
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
        kmf.init(keyStore, Array$.MODULE$.emptyCharArray());
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

    public static final /* synthetic */ boolean $anonfun$shouldGenerate$1(FakeChainedKeyStore $this, KeyStore store$1, String alias) {
        return Option$.MODULE$.apply(store$1.getCertificate(alias)).exists((Function1<Certificate, Object> & Serializable)c -> BoxesRunTime.boxToBoolean($this.certificateTooWeak(c)));
    }

    public FakeChainedKeyStore(LoggerFactory mkLogger) {
        this.logger = mkLogger.apply(this.getClass());
    }
}

