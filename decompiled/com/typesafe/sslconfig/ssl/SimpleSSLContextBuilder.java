/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLContextBuilder;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import scala.$less$colon$less$;
import scala.Option;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514A\u0001C\u0005\u0001%!AQ\u0004\u0001B\u0001B\u0003%a\u0004\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003+\u0011!a\u0004A!A!\u0002\u0013i\u0004\u0002C!\u0001\u0005\u0003\u0005\u000b\u0011\u0002\"\t\u000b5\u0003A\u0011\u0001(\t\u000bQ\u0003A\u0011A+\t\u000b\u001d\u0004A\u0011\u00015\u0003/MKW\u000e\u001d7f'Nc5i\u001c8uKb$()^5mI\u0016\u0014(B\u0001\u0006\f\u0003\r\u00198\u000f\u001c\u0006\u0003\u00195\t\u0011b]:mG>tg-[4\u000b\u00059y\u0011\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003A\t1aY8n\u0007\u0001\u00192\u0001A\n\u001a!\t!r#D\u0001\u0016\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0005\u0019\te.\u001f*fMB\u0011!dG\u0007\u0002\u0013%\u0011A$\u0003\u0002\u0012'Nc5i\u001c8uKb$()^5mI\u0016\u0014\u0018\u0001\u00039s_R|7m\u001c7\u0011\u0005}1cB\u0001\u0011%!\t\tS#D\u0001#\u0015\t\u0019\u0013#\u0001\u0004=e>|GOP\u0005\u0003KU\ta\u0001\u0015:fI\u00164\u0017BA\u0014)\u0005\u0019\u0019FO]5oO*\u0011Q%F\u0001\fW\u0016LX*\u00198bO\u0016\u00148\u000fE\u0002,aMr!\u0001\f\u0018\u000f\u0005\u0005j\u0013\"\u0001\f\n\u0005=*\u0012a\u00029bG.\fw-Z\u0005\u0003cI\u00121aU3r\u0015\tyS\u0003\u0005\u00025u5\tQG\u0003\u0002\u000bm)\u0011q\u0007O\u0001\u0004]\u0016$(\"A\u001d\u0002\u000b)\fg/\u0019=\n\u0005m*$AC&fs6\u000bg.Y4fe\u0006iAO];ti6\u000bg.Y4feN\u00042a\u000b\u0019?!\t!t(\u0003\u0002Ak\taAK];ti6\u000bg.Y4fe\u0006a1/Z2ve\u0016\u0014\u0016M\u001c3p[B\u0019AcQ#\n\u0005\u0011+\"AB(qi&|g\u000e\u0005\u0002G\u00176\tqI\u0003\u0002I\u0013\u0006A1/Z2ve&$\u0018PC\u0001K\u0003\u0011Q\u0017M^1\n\u00051;%\u0001D*fGV\u0014XMU1oI>l\u0017A\u0002\u001fj]&$h\bF\u0003P!F\u00136\u000b\u0005\u0002\u001b\u0001!)Q$\u0002a\u0001=!)\u0011&\u0002a\u0001U!)A(\u0002a\u0001{!)\u0011)\u0002a\u0001\u0005\u0006Ya.\u001e7m\u0013\u001a,U\u000e\u001d;z+\t1F\f\u0006\u0002XKB\u0019A\u0003\u0017.\n\u0005e+\"!B!se\u0006L\bCA.]\u0019\u0001!Q!\u0018\u0004C\u0002y\u0013\u0011\u0001V\t\u0003?\n\u0004\"\u0001\u00061\n\u0005\u0005,\"a\u0002(pi\"Lgn\u001a\t\u0003)\rL!\u0001Z\u000b\u0003\u0007\u0005s\u0017\u0010C\u0003g\r\u0001\u0007q+A\u0003beJ\f\u00170A\u0003ck&dG\rF\u0001j!\t!$.\u0003\u0002lk\tQ1k\u0015'D_:$X\r\u001f;")
public class SimpleSSLContextBuilder
implements SSLContextBuilder {
    private final String protocol;
    private final Seq<KeyManager> keyManagers;
    private final Seq<TrustManager> trustManagers;
    private final Option<SecureRandom> secureRandom;

    public <T> Object nullIfEmpty(Object array) {
        return ArrayOps$.MODULE$.isEmpty$extension(Predef$.MODULE$.genericArrayOps(array)) ? null : array;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public SSLContext build() {
        void var1_1;
        SSLContext sslContext = SSLContext.getInstance(this.protocol);
        sslContext.init((KeyManager[])this.nullIfEmpty(this.keyManagers.toArray(ClassTag$.MODULE$.apply(KeyManager.class))), (TrustManager[])this.nullIfEmpty(this.trustManagers.toArray(ClassTag$.MODULE$.apply(TrustManager.class))), (SecureRandom)this.secureRandom.orNull($less$colon$less$.MODULE$.refl()));
        return var1_1;
    }

    public SimpleSSLContextBuilder(String protocol, Seq<KeyManager> keyManagers, Seq<TrustManager> trustManagers, Option<SecureRandom> secureRandom) {
        this.protocol = protocol;
        this.keyManagers = keyManagers;
        this.trustManagers = trustManagers;
        this.secureRandom = secureRandom;
    }
}

