/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLLooseConfig$;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]c\u0001\u0002\u0015*\u0005IB\u0001\"\u000f\u0001\u0003\u0006\u0004%\tA\u000f\u0005\t}\u0001\u0011\t\u0011)A\u0005w!Aq\b\u0001BC\u0002\u0013\u0005\u0001\t\u0003\u0005E\u0001\t\u0005\t\u0015!\u0003B\u0011!)\u0005A!b\u0001\n\u0003\u0001\u0005\u0002\u0003$\u0001\u0005\u0003\u0005\u000b\u0011B!\t\u0011\u001d\u0003!Q1A\u0005\u0002iB\u0001B\u0015\u0001\u0003\u0002\u0003\u0006Ia\u000f\u0005\t)\u0002\u0011)\u0019!C\u0001u!Aa\u000b\u0001B\u0001B\u0003%1\b\u0003\u0005Y\u0001\t\u0015\r\u0011\"\u0001;\u0011!I\u0006A!A!\u0002\u0013Y\u0004\u0002\u0003.\u0001\u0005\u000b\u0007I\u0011\u0001\u001e\t\u0011m\u0003!\u0011!Q\u0001\nmBa\u0001\u0018\u0001\u0005\u0002-j\u0006\"B4\u0001\t\u0003A\u0007\"B6\u0001\t\u0003a\u0007\"\u00028\u0001\t\u0003y\u0007\"B9\u0001\t\u0003\u0011\b\"\u0002;\u0001\t\u0003)\b\"B<\u0001\t\u0013A\bb\u0002@\u0001#\u0003%Ia \u0005\n\u0003+\u0001\u0011\u0013!C\u0005\u0003/A\u0011\"a\u0007\u0001#\u0003%I!a\u0006\t\u0011\u0005u\u0001!%A\u0005\n}D\u0001\"a\b\u0001#\u0003%Ia \u0005\b\u0003C\u0001A\u0011IA\u0012\u000f\u001d\tY$\u000bE\u0001\u0003{1a\u0001K\u0015\t\u0002\u0005}\u0002B\u0002/\u001e\t\u0003\t\t\u0005C\u0004\u0002Du!\t!!\u0012\t\u000f\u0005\u001dS\u0004\"\u0001\u0002F!I\u0011\u0011J\u000f\u0012\u0002\u0013\u00051f \u0005\u000b\u0003\u0017j\u0012\u0013!C\u0001W\u0005]\u0001BCA';E\u0005I\u0011A\u0016\u0002\u0018!I\u0011qJ\u000f\u0012\u0002\u0013\u00051f \u0005\n\u0003#j\u0012\u0013!C\u0001W}D\u0011\"a\u0015\u001e#\u0003%\taK@\t\u0013\u0005US$%A\u0005\u0002-z(AD*T\u00192{wn]3D_:4\u0017n\u001a\u0006\u0003U-\n1a]:m\u0015\taS&A\u0005tg2\u001cwN\u001c4jO*\u0011afL\u0001\tif\u0004Xm]1gK*\t\u0001'A\u0002d_6\u001c\u0001a\u0005\u0002\u0001gA\u0011AgN\u0007\u0002k)\ta'A\u0003tG\u0006d\u0017-\u0003\u00029k\t1\u0011I\\=SK\u001a\fA#Y2dKB$\u0018I\\=DKJ$\u0018NZ5dCR,W#A\u001e\u0011\u0005Qb\u0014BA\u001f6\u0005\u001d\u0011un\u001c7fC:\fQ#Y2dKB$\u0018I\\=DKJ$\u0018NZ5dCR,\u0007%\u0001\rbY2|w\u000fT3hC\u000eL\b*\u001a7m_6+7o]1hKN,\u0012!\u0011\t\u0004i\t[\u0014BA\"6\u0005\u0019y\u0005\u000f^5p]\u0006I\u0012\r\u001c7po2+w-Y2z\u0011\u0016dGn\\'fgN\fw-Z:!\u0003a\tG\u000e\\8x+:\u001c\u0018MZ3SK:,wm\u001c;jCRLwN\\\u0001\u001aC2dwn^+og\u00064WMU3oK\u001e|G/[1uS>t\u0007%\u0001\tbY2|woV3bW\u000eK\u0007\u000f[3sg\"2q!\u0013'N\u001fB\u0003\"\u0001\u000e&\n\u0005-+$A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%\u0001(\u0002\u001b9|G\u000fI8qKJ\fG/\u001b<f\u0003\u0015\u0019\u0018N\\2fC\u0005\t\u0016!\u0002\u0019/k9\u0002\u0014!E1mY><x+Z1l\u0007&\u0004\b.\u001a:tA!2\u0001\"\u0013'N\u001fB\u000b!#\u00197m_^<V-Y6Qe>$xnY8mg\"2\u0011\"\u0013'N\u001fB\u000b1#\u00197m_^<V-Y6Qe>$xnY8mg\u0002BcAC%M\u001b>\u0003\u0016a\u00073jg\u0006\u0014G.\u001a%pgRt\u0017-\\3WKJLg-[2bi&|g.\u0001\u000feSN\f'\r\\3I_N$h.Y7f-\u0016\u0014\u0018NZ5dCRLwN\u001c\u0011\u0002\u0015\u0011L7/\u00192mKNs\u0015*A\u0006eSN\f'\r\\3T\u001d&\u0003\u0013A\u0002\u001fj]&$h\b\u0006\u0005_A\u0006\u00147\rZ3g!\ty\u0006!D\u0001*\u0011\u001dIt\u0002%AA\u0002mBqaP\b\u0011\u0002\u0003\u0007\u0011\tC\u0004F\u001fA\u0005\t\u0019A!\t\u000f\u001d{\u0001\u0013!a\u0001w!9Ak\u0004I\u0001\u0002\u0004Y\u0004b\u0002-\u0010!\u0003\u0005\ra\u000f\u0005\b5>\u0001\n\u00111\u0001<\u0003a9\u0018\u000e\u001e5BG\u000e,\u0007\u000f^!os\u000e+'\u000f^5gS\u000e\fG/\u001a\u000b\u0003=&DQA\u001b\tA\u0002m\nQA^1mk\u0016\fAd^5uQ\u0006cGn\\<MK\u001e\f7-\u001f%fY2|W*Z:tC\u001e,7\u000f\u0006\u0002_[\")!.\u0005a\u0001\u0003\u0006ar/\u001b;i\u00032dwn^+og\u00064WMU3oK\u001e|G/[1uS>tGC\u00010q\u0011\u0015Q'\u00031\u0001B\u0003}9\u0018\u000e\u001e5ESN\f'\r\\3I_N$h.Y7f-\u0016\u0014\u0018NZ5dCRLwN\u001c\u000b\u0003=NDQA[\nA\u0002m\nab^5uQ\u0012K7/\u00192mKNs\u0015\n\u0006\u0002_m\")!\u000e\u0006a\u0001w\u0005!1m\u001c9z)\u0019q\u0016P_>}{\"9\u0011(\u0006I\u0001\u0002\u0004Y\u0004bB \u0016!\u0003\u0005\r!\u0011\u0005\b\u000bV\u0001\n\u00111\u0001B\u0011\u001dAV\u0003%AA\u0002mBqAW\u000b\u0011\u0002\u0003\u00071(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005\u0005!fA\u001e\u0002\u0004-\u0012\u0011Q\u0001\t\u0005\u0003\u000f\t\t\"\u0004\u0002\u0002\n)!\u00111BA\u0007\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0010U\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019\"!\u0003\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005e!fA!\u0002\u0004\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001a\u0014AD2paf$C-\u001a4bk2$H\u0005N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136\u0003!!xn\u0015;sS:<GCAA\u0013!\u0011\t9#!\u000e\u000f\t\u0005%\u0012\u0011\u0007\t\u0004\u0003W)TBAA\u0017\u0015\r\ty#M\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005MR'\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003o\tID\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003g)\u0014AD*T\u00192{wn]3D_:4\u0017n\u001a\t\u0003?v\u0019\"!H\u001a\u0015\u0005\u0005u\u0012!B1qa2LH#\u00010\u0002\u0017\u001d,G/\u00138ti\u0006t7-Z\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%g\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIQ\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012*\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$c'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$He\u000e")
public final class SSLLooseConfig {
    private final boolean acceptAnyCertificate;
    private final Option<Object> allowLegacyHelloMessages;
    private final Option<Object> allowUnsafeRenegotiation;
    private final boolean allowWeakCiphers;
    private final boolean allowWeakProtocols;
    private final boolean disableHostnameVerification;
    private final boolean disableSNI;

    public static SSLLooseConfig getInstance() {
        return SSLLooseConfig$.MODULE$.getInstance();
    }

    public static SSLLooseConfig apply() {
        return SSLLooseConfig$.MODULE$.apply();
    }

    public boolean acceptAnyCertificate() {
        return this.acceptAnyCertificate;
    }

    public Option<Object> allowLegacyHelloMessages() {
        return this.allowLegacyHelloMessages;
    }

    public Option<Object> allowUnsafeRenegotiation() {
        return this.allowUnsafeRenegotiation;
    }

    public boolean allowWeakCiphers() {
        return this.allowWeakCiphers;
    }

    public boolean allowWeakProtocols() {
        return this.allowWeakProtocols;
    }

    public boolean disableHostnameVerification() {
        return this.disableHostnameVerification;
    }

    public boolean disableSNI() {
        return this.disableSNI;
    }

    public SSLLooseConfig withAcceptAnyCertificate(boolean value) {
        return this.copy(value, this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5());
    }

    public SSLLooseConfig withAllowLegacyHelloMessages(Option<Object> value) {
        Option<Object> x$1 = value;
        boolean x$2 = this.copy$default$1();
        Option<Object> x$3 = this.copy$default$3();
        boolean x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public SSLLooseConfig withAllowUnsafeRenegotiation(Option<Object> value) {
        Option<Object> x$1 = value;
        boolean x$2 = this.copy$default$1();
        Option<Object> x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    public SSLLooseConfig withDisableHostnameVerification(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        Option<Object> x$3 = this.copy$default$2();
        Option<Object> x$4 = this.copy$default$3();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$4, x$1, x$5);
    }

    public SSLLooseConfig withDisableSNI(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        Option<Object> x$3 = this.copy$default$2();
        Option<Object> x$4 = this.copy$default$3();
        boolean x$5 = this.copy$default$4();
        return this.copy(x$2, x$3, x$4, x$5, x$1);
    }

    private SSLLooseConfig copy(boolean acceptAnyCertificate, Option<Object> allowLegacyHelloMessages, Option<Object> allowUnsafeRenegotiation, boolean disableHostnameVerification, boolean disableSNI) {
        boolean x$1 = acceptAnyCertificate;
        Option<Object> x$2 = allowLegacyHelloMessages;
        Option<Object> x$3 = allowUnsafeRenegotiation;
        boolean x$4 = disableHostnameVerification;
        boolean x$5 = disableSNI;
        boolean x$6 = SSLLooseConfig$.MODULE$.$lessinit$greater$default$4();
        boolean x$7 = SSLLooseConfig$.MODULE$.$lessinit$greater$default$5();
        return new SSLLooseConfig(x$1, x$2, x$3, x$6, x$7, x$4, x$5);
    }

    private boolean copy$default$1() {
        return this.acceptAnyCertificate();
    }

    private Option<Object> copy$default$2() {
        return this.allowLegacyHelloMessages();
    }

    private Option<Object> copy$default$3() {
        return this.allowUnsafeRenegotiation();
    }

    private boolean copy$default$4() {
        return this.disableHostnameVerification();
    }

    private boolean copy$default$5() {
        return this.disableSNI();
    }

    public String toString() {
        return new StringBuilder(20).append("SSLLooseConfig(").append(this.acceptAnyCertificate()).append(",").append(this.allowLegacyHelloMessages()).append(",").append(this.allowUnsafeRenegotiation()).append(",").append(this.disableHostnameVerification()).append(",").append(this.disableSNI()).append(")").toString();
    }

    public SSLLooseConfig(boolean acceptAnyCertificate, Option<Object> allowLegacyHelloMessages, Option<Object> allowUnsafeRenegotiation, boolean allowWeakCiphers, boolean allowWeakProtocols, boolean disableHostnameVerification, boolean disableSNI) {
        this.acceptAnyCertificate = acceptAnyCertificate;
        this.allowLegacyHelloMessages = allowLegacyHelloMessages;
        this.allowUnsafeRenegotiation = allowUnsafeRenegotiation;
        this.allowWeakCiphers = allowWeakCiphers;
        this.allowWeakProtocols = allowWeakProtocols;
        this.disableHostnameVerification = disableHostnameVerification;
        this.disableSNI = disableSNI;
    }
}

