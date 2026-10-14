/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyStoreBuilder;
import com.typesafe.sslconfig.ssl.KeystoreFormats$;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ListBuffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d3A!\u0002\u0004\u0001\u001f!A!\u0004\u0001B\u0001B\u0003%1\u0004C\u0003'\u0001\u0011\u0005q\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00035\u0001\u0011\u0005QG\u0001\u000eTiJLgn\u001a\"bg\u0016$7*Z=Ti>\u0014XMQ;jY\u0012,'O\u0003\u0002\b\u0011\u0005\u00191o\u001d7\u000b\u0005%Q\u0011!C:tY\u000e|gNZ5h\u0015\tYA\"\u0001\u0005usB,7/\u00194f\u0015\u0005i\u0011aA2p[\u000e\u00011c\u0001\u0001\u0011-A\u0011\u0011\u0003F\u0007\u0002%)\t1#A\u0003tG\u0006d\u0017-\u0003\u0002\u0016%\t1\u0011I\\=SK\u001a\u0004\"a\u0006\r\u000e\u0003\u0019I!!\u0007\u0004\u0003\u001f-+\u0017p\u0015;pe\u0016\u0014U/\u001b7eKJ\fA\u0001Z1uCB\u0011Ad\t\b\u0003;\u0005\u0002\"A\b\n\u000e\u0003}Q!\u0001\t\b\u0002\rq\u0012xn\u001c;?\u0013\t\u0011##\u0001\u0004Qe\u0016$WMZ\u0005\u0003I\u0015\u0012aa\u0015;sS:<'B\u0001\u0012\u0013\u0003\u0019a\u0014N\\5u}Q\u0011\u0001&\u000b\t\u0003/\u0001AQA\u0007\u0002A\u0002m\tQAY;jY\u0012$\u0012\u0001\f\t\u0003[Ij\u0011A\f\u0006\u0003_A\n\u0001b]3dkJLG/\u001f\u0006\u0002c\u0005!!.\u0019<b\u0013\t\u0019dF\u0001\u0005LKf\u001cFo\u001c:f\u0003A\u0011X-\u00193DKJ$\u0018NZ5dCR,7\u000f\u0006\u00027\u000bB\u0019q\u0007P \u000f\u0005aRdB\u0001\u0010:\u0013\u0005\u0019\u0012BA\u001e\u0013\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u0010 \u0003\u0007M+\u0017O\u0003\u0002<%A\u0011\u0001iQ\u0007\u0002\u0003*\u0011!IL\u0001\u0005G\u0016\u0014H/\u0003\u0002E\u0003\nY1)\u001a:uS\u001aL7-\u0019;f\u0011\u00151E\u00011\u0001\u001c\u0003E\u0019WM\u001d;jM&\u001c\u0017\r^3TiJLgn\u001a")
public class StringBasedKeyStoreBuilder
implements KeyStoreBuilder {
    private final String data;

    /*
     * WARNING - void declaration
     */
    @Override
    public KeyStore build() {
        void var2_2;
        Seq<Certificate> certs = this.readCertificates(this.data);
        KeyStore store = KeystoreFormats$.MODULE$.loadCertificates(certs);
        return var2_2;
    }

    public Seq<Certificate> readCertificates(String certificateString) {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        String trimmedString = certificateString.trim();
        ByteArrayInputStream is = new ByteArrayInputStream(trimmedString.getBytes("UTF-8"));
        BufferedInputStream bis = new BufferedInputStream(is);
        ListBuffer<Certificate> buffer = new ListBuffer<Certificate>();
        while (bis.available() > 0) {
            Certificate cert = cf.generateCertificate(bis);
            buffer.append(cert);
        }
        return buffer.toList();
    }

    public StringBasedKeyStoreBuilder(String data) {
        this.data = data;
    }
}

