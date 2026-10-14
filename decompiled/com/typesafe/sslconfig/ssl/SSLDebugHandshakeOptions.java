/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugHandshakeOptions$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000554Aa\u0005\u000b\u0003;!AA\u0005\u0001BC\u0002\u0013\u0005Q\u0005\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003'\u0011!Q\u0003A!b\u0001\n\u0003)\u0003\u0002C\u0016\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0014\t\r1\u0002A\u0011\u0001\f.\u0011\u0015\u0011\u0004\u0001\"\u00014\u0011\u00151\u0004\u0001\"\u00018\u0011\u0015I\u0004\u0001\"\u0003;\u0011\u001di\u0004!%A\u0005\nyBq!\u0013\u0001\u0012\u0002\u0013%a\bC\u0003K\u0001\u0011\u00053jB\u0003b)!\u0005!MB\u0003\u0014)!\u00051\rC\u0003-\u001b\u0011\u0005A\rC\u0003f\u001b\u0011\u0005a\rC\u0003h\u001b\u0011\u0005a\r\u0003\u0005i\u001bE\u0005I\u0011\u0001\f?\u0011!IW\"%A\u0005\u0002Yq$\u0001G*T\u0019\u0012+'-^4IC:$7\u000f[1lK>\u0003H/[8og*\u0011QCF\u0001\u0004gNd'BA\f\u0019\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u001a5\u0005AA/\u001f9fg\u00064WMC\u0001\u001c\u0003\r\u0019w.\\\u0002\u0001'\t\u0001a\u0004\u0005\u0002 E5\t\u0001EC\u0001\"\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0003E\u0001\u0004B]f\u0014VMZ\u0001\u0005I\u0006$\u0018-F\u0001'!\tyr%\u0003\u0002)A\t9!i\\8mK\u0006t\u0017!\u00023bi\u0006\u0004\u0013a\u0002<fe\n|7/Z\u0001\tm\u0016\u0014(m\\:fA\u00051A(\u001b8jiz\"2A\f\u00192!\ty\u0003!D\u0001\u0015\u0011\u001d!S\u0001%AA\u0002\u0019BqAK\u0003\u0011\u0002\u0003\u0007a%\u0001\u0005xSRDG)\u0019;b)\tqC\u0007C\u00036\r\u0001\u0007a%A\u0003wC2,X-A\u0006xSRDg+\u001a:c_N,GC\u0001\u00189\u0011\u0015)t\u00011\u0001'\u0003\u0011\u0019w\u000e]=\u0015\u00079ZD\bC\u0004%\u0011A\u0005\t\u0019\u0001\u0014\t\u000f)B\u0001\u0013!a\u0001M\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A +\u0005\u0019\u00025&A!\u0011\u0005\t;U\"A\"\u000b\u0005\u0011+\u0015!C;oG\",7m[3e\u0015\t1\u0005%\u0001\u0006b]:|G/\u0019;j_:L!\u0001S\"\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001\u0014\t\u0003\u001bRs!A\u0014*\u0011\u0005=\u0003S\"\u0001)\u000b\u0005Ec\u0012A\u0002\u001fs_>$h(\u0003\u0002TA\u00051\u0001K]3eK\u001aL!!\u0016,\u0003\rM#(/\u001b8h\u0015\t\u0019\u0006\u0005\u000b\u0004\u00011ncfl\u0018\t\u0003?eK!A\u0017\u0011\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003u\u000bQB\\8uA=\u0004XM]1uSZ,\u0017!B:j]\u000e,\u0017%\u00011\u0002\u000bArCG\f\u0019\u00021M\u001bF\nR3ck\u001eD\u0015M\u001c3tQ\u0006\\Wm\u00149uS>t7\u000f\u0005\u00020\u001bM\u0011QB\b\u000b\u0002E\u0006)\u0011\r\u001d9msR\ta&A\u0006hKRLen\u001d;b]\u000e,\u0017a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HE\r\u0015\u0007\u001ba[FLX0)\u00055A\u0006F\u0002\u0007Y7rsv\f")
public final class SSLDebugHandshakeOptions {
    private final boolean data;
    private final boolean verbose;

    public static SSLDebugHandshakeOptions getInstance() {
        return SSLDebugHandshakeOptions$.MODULE$.getInstance();
    }

    public static SSLDebugHandshakeOptions apply() {
        return SSLDebugHandshakeOptions$.MODULE$.apply();
    }

    public boolean data() {
        return this.data;
    }

    public boolean verbose() {
        return this.verbose;
    }

    public SSLDebugHandshakeOptions withData(boolean value) {
        return this.copy(value, this.copy$default$2());
    }

    public SSLDebugHandshakeOptions withVerbose(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    private SSLDebugHandshakeOptions copy(boolean data, boolean verbose) {
        return new SSLDebugHandshakeOptions(data, verbose);
    }

    private boolean copy$default$1() {
        return this.data();
    }

    private boolean copy$default$2() {
        return this.verbose();
    }

    public String toString() {
        return new StringBuilder(27).append("SSLDebugHandshakeOptions(").append(this.data()).append(",").append(this.verbose()).append(")").toString();
    }

    public SSLDebugHandshakeOptions(boolean data, boolean verbose) {
        this.data = data;
        this.verbose = verbose;
    }
}

