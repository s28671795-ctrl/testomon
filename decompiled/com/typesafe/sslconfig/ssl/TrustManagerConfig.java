/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.TrustManagerConfig$;
import com.typesafe.sslconfig.ssl.TrustStoreConfig;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4Aa\u0005\u000b\u0003;!AA\u0005\u0001BC\u0002\u0013\u0005Q\u0005\u0003\u00052\u0001\t\u0005\t\u0015!\u0003'\u0011!\u0011\u0004A!b\u0001\n\u0003\u0019\u0004\u0002\u0003!\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001b\t\r\u0005\u0003A\u0011\u0001\fC\u0011\u00151\u0005\u0001\"\u0001H\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u0015i\u0005\u0001\"\u0003O\u0011\u001d\t\u0006!%A\u0005\nICq!\u0018\u0001\u0012\u0002\u0013%a\fC\u0003a\u0001\u0011\u0005\u0013mB\u0003c)!\u00051MB\u0003\u0014)!\u0005A\rC\u0003B\u001b\u0011\u0005Q\rC\u0003g\u001b\u0011\u0005q\rC\u0003i\u001b\u0011\u0005q\r\u0003\u0005j\u001bE\u0005I\u0011\u0001\fS\u0011!QW\"%A\u0005\u0002Yq&A\u0005+skN$X*\u00198bO\u0016\u00148i\u001c8gS\u001eT!!\u0006\f\u0002\u0007M\u001cHN\u0003\u0002\u00181\u0005I1o\u001d7d_:4\u0017n\u001a\u0006\u00033i\t\u0001\u0002^=qKN\fg-\u001a\u0006\u00027\u0005\u00191m\\7\u0004\u0001M\u0011\u0001A\b\t\u0003?\tj\u0011\u0001\t\u0006\u0002C\u0005)1oY1mC&\u00111\u0005\t\u0002\u0007\u0003:L(+\u001a4\u0002\u0013\u0005dwm\u001c:ji\"lW#\u0001\u0014\u0011\u0005\u001drcB\u0001\u0015-!\tI\u0003%D\u0001+\u0015\tYC$\u0001\u0004=e>|GOP\u0005\u0003[\u0001\na\u0001\u0015:fI\u00164\u0017BA\u00181\u0005\u0019\u0019FO]5oO*\u0011Q\u0006I\u0001\u000bC2<wN]5uQ6\u0004\u0013!\u0005;skN$8\u000b^8sK\u000e{gNZ5hgV\tA\u0007E\u00026uqj\u0011A\u000e\u0006\u0003oa\n\u0011\"[7nkR\f'\r\\3\u000b\u0005e\u0002\u0013AC2pY2,7\r^5p]&\u00111H\u000e\u0002\u0004'\u0016\f\bCA\u001f?\u001b\u0005!\u0012BA \u0015\u0005A!&/^:u'R|'/Z\"p]\u001aLw-\u0001\nueV\u001cHo\u0015;pe\u0016\u001cuN\u001c4jON\u0004\u0013A\u0002\u001fj]&$h\bF\u0002D\t\u0016\u0003\"!\u0010\u0001\t\u000f\u0011*\u0001\u0013!a\u0001M!9!'\u0002I\u0001\u0002\u0004!\u0014!D<ji\"\fEnZ8sSRDW\u000e\u0006\u0002D\u0011\")\u0011J\u0002a\u0001M\u0005)a/\u00197vK\u0006)r/\u001b;i)J,8\u000f^*u_J,7i\u001c8gS\u001e\u001cHCA\"M\u0011\u0015Iu\u00011\u00015\u0003\u0011\u0019w\u000e]=\u0015\u0007\r{\u0005\u000bC\u0004%\u0011A\u0005\t\u0019\u0001\u0014\t\u000fIB\u0001\u0013!a\u0001i\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A*+\u0005\u0019\"6&A+\u0011\u0005Y[V\"A,\u000b\u0005aK\u0016!C;oG\",7m[3e\u0015\tQ\u0006%\u0001\u0006b]:|G/\u0019;j_:L!\u0001X,\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003}S#\u0001\u000e+\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AJ\u0001\u0013)J,8\u000f^'b]\u0006<WM]\"p]\u001aLw\r\u0005\u0002>\u001bM\u0011QB\b\u000b\u0002G\u0006)\u0011\r\u001d9msR\t1)A\u0006hKRLen\u001d;b]\u000e,\u0017a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HE\r")
public final class TrustManagerConfig {
    private final String algorithm;
    private final Seq<TrustStoreConfig> trustStoreConfigs;

    public static TrustManagerConfig getInstance() {
        return TrustManagerConfig$.MODULE$.getInstance();
    }

    public static TrustManagerConfig apply() {
        return TrustManagerConfig$.MODULE$.apply();
    }

    public String algorithm() {
        return this.algorithm;
    }

    public Seq<TrustStoreConfig> trustStoreConfigs() {
        return this.trustStoreConfigs;
    }

    public TrustManagerConfig withAlgorithm(String value) {
        return this.copy(value, this.copy$default$2());
    }

    public TrustManagerConfig withTrustStoreConfigs(Seq<TrustStoreConfig> value) {
        Seq<TrustStoreConfig> x$1 = value;
        String x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    private TrustManagerConfig copy(String algorithm, Seq<TrustStoreConfig> trustStoreConfigs) {
        return new TrustManagerConfig(algorithm, trustStoreConfigs);
    }

    private String copy$default$1() {
        return this.algorithm();
    }

    private Seq<TrustStoreConfig> copy$default$2() {
        return this.trustStoreConfigs();
    }

    public String toString() {
        return new StringBuilder(21).append("TrustManagerConfig(").append(this.algorithm()).append(",").append(this.trustStoreConfigs()).append(")").toString();
    }

    public TrustManagerConfig(String algorithm, Seq<TrustStoreConfig> trustStoreConfigs) {
        this.algorithm = algorithm;
        this.trustStoreConfigs = trustStoreConfigs;
    }
}

