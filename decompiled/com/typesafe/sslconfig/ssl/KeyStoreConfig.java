/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyStoreConfig$;
import java.io.Serializable;
import java.util.Optional;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eb\u0001\u0002\u0011\"\u0005)B\u0001\"\r\u0001\u0003\u0006\u0004%\tA\r\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005g!A!\t\u0001BC\u0002\u0013\u0005!\u0007\u0003\u0005D\u0001\t\u0005\t\u0015!\u00034\u0011!!\u0005A!b\u0001\n\u0003)\u0005\u0002C%\u0001\u0005\u0003\u0005\u000b\u0011\u0002$\t\u0011)\u0003!Q1A\u0005\u0002IB\u0001b\u0013\u0001\u0003\u0002\u0003\u0006Ia\r\u0005\t\u0019\u0002\u0011)\u0019!C\u0001\u001b\"Aa\n\u0001B\u0001B\u0003%a\u0007\u0003\u0004P\u0001\u0011\u00051\u0005\u0015\u0005\u00061\u0002!\t!\u0017\u0005\u00067\u0002!\t\u0001\u0018\u0005\u0006=\u0002!\ta\u0018\u0005\u0006C\u0002!\tA\u0019\u0005\u0006K\u0002!\tA\u001a\u0005\u0006Q\u0002!I!\u001b\u0005\b_\u0002\t\n\u0011\"\u0003q\u0011\u001dY\b!%A\u0005\nADq\u0001 \u0001\u0012\u0002\u0013%Q\u0010C\u0004\u0000\u0001E\u0005I\u0011\u00029\t\u0013\u0005\u0005\u0001!%A\u0005\n\u0005\r\u0001bBA\u0004\u0001\u0011\u0005\u0013\u0011B\u0004\b\u0003\u0017\t\u0003\u0012AA\u0007\r\u0019\u0001\u0013\u0005#\u0001\u0002\u0010!1q*\u0007C\u0001\u0003#Aq!a\u0005\u001a\t\u0003\t)\u0002C\u0004\u0002\u001ce!\t!!\b\t\u0013\u0005M\u0012$%A\u0005\u0002\rj\b\"CA\u001b3E\u0005I\u0011A\u0012q\u0011)\t9$GI\u0001\n\u0003\u0019\u00131\u0001\u0002\u000f\u0017\u0016L8\u000b^8sK\u000e{gNZ5h\u0015\t\u00113%A\u0002tg2T!\u0001J\u0013\u0002\u0013M\u001cHnY8oM&<'B\u0001\u0014(\u0003!!\u0018\u0010]3tC\u001a,'\"\u0001\u0015\u0002\u0007\r|Wn\u0001\u0001\u0014\u0005\u0001Y\u0003C\u0001\u00170\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#AB!osJ+g-\u0001\u0003eCR\fW#A\u001a\u0011\u00071\"d'\u0003\u00026[\t1q\n\u001d;j_:\u0004\"a\u000e \u000f\u0005ab\u0004CA\u001d.\u001b\u0005Q$BA\u001e*\u0003\u0019a$o\\8u}%\u0011Q(L\u0001\u0007!J,G-\u001a4\n\u0005}\u0002%AB*ue&twM\u0003\u0002>[\u0005)A-\u0019;bA\u0005Aa-\u001b7f!\u0006$\b.A\u0005gS2,\u0007+\u0019;iA\u0005\t\u0012n\u001d$jY\u0016|en\u00117bgN\u0004\u0018\r\u001e5\u0016\u0003\u0019\u0003\"\u0001L$\n\u0005!k#a\u0002\"p_2,\u0017M\\\u0001\u0013SN4\u0015\u000e\\3P]\u000ec\u0017m]:qCRD\u0007%\u0001\u0005qCN\u001cxo\u001c:e\u0003%\u0001\u0018m]:x_J$\u0007%A\u0005ti>\u0014X\rV=qKV\ta'\u0001\u0006ti>\u0014X\rV=qK\u0002\na\u0001P5oSRtDCB)T)V3v\u000b\u0005\u0002S\u00015\t\u0011\u0005C\u00032\u0017\u0001\u00071\u0007C\u0003C\u0017\u0001\u00071\u0007C\u0004E\u0017A\u0005\t\u0019\u0001$\t\u000f)[\u0001\u0013!a\u0001g!9Aj\u0003I\u0001\u0002\u00041\u0014\u0001C<ji\"$\u0015\r^1\u0015\u0005ES\u0006\"B\u0019\r\u0001\u0004\u0019\u0014\u0001D<ji\"4\u0015\u000e\\3QCRDGCA)^\u0011\u0015\u0011U\u00021\u00014\u0003M9\u0018\u000e\u001e5GS2,wJ\\\"mCN\u001c\b+\u0019;i)\t\t\u0006\rC\u0003E\u001d\u0001\u0007a)\u0001\u0007xSRD\u0007+Y:to>\u0014H\r\u0006\u0002RG\")Am\u0004a\u0001g\u0005)a/\u00197vK\u0006iq/\u001b;i'R|'/\u001a+za\u0016$\"!U4\t\u000b\u0011\u0004\u0002\u0019\u0001\u001c\u0002\t\r|\u0007/\u001f\u000b\u0007#*\\G.\u001c8\t\u000fE\n\u0002\u0013!a\u0001g!9!)\u0005I\u0001\u0002\u0004\u0019\u0004b\u0002#\u0012!\u0003\u0005\rA\u0012\u0005\b\u0015F\u0001\n\u00111\u00014\u0011\u001da\u0015\u0003%AA\u0002Y\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001rU\t\u0019$oK\u0001t!\t!\u00180D\u0001v\u0015\t1x/A\u0005v]\u000eDWmY6fI*\u0011\u00010L\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001>v\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0012A \u0016\u0003\rJ\fabY8qs\u0012\"WMZ1vYR$C'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\u0005\u0015!F\u0001\u001cs\u0003!!xn\u0015;sS:<G#\u0001\u001c\u0002\u001d-+\u0017p\u0015;pe\u0016\u001cuN\u001c4jOB\u0011!+G\n\u00033-\"\"!!\u0004\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000bE\u000b9\"!\u0007\t\u000bEZ\u0002\u0019A\u001a\t\u000b\t[\u0002\u0019A\u001a\u0002\u0017\u001d,G/\u00138ti\u0006t7-\u001a\u000b\u0006#\u0006}\u0011\u0011\u0007\u0005\u0007cq\u0001\r!!\t\u0011\u000b\u0005\r\u0012Q\u0006\u001c\u000e\u0005\u0005\u0015\"\u0002BA\u0014\u0003S\tA!\u001e;jY*\u0011\u00111F\u0001\u0005U\u00064\u0018-\u0003\u0003\u00020\u0005\u0015\"\u0001C(qi&|g.\u00197\t\r\tc\u0002\u0019AA\u0011\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%g\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIQ\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012*\u0004")
public final class KeyStoreConfig {
    private final Option<String> data;
    private final Option<String> filePath;
    private final boolean isFileOnClasspath;
    private final Option<String> password;
    private final String storeType;

    public static KeyStoreConfig getInstance(Optional<String> data, Optional<String> filePath) {
        return KeyStoreConfig$.MODULE$.getInstance(data, filePath);
    }

    public static KeyStoreConfig apply(Option<String> data, Option<String> filePath) {
        return KeyStoreConfig$.MODULE$.apply(data, filePath);
    }

    public Option<String> data() {
        return this.data;
    }

    public Option<String> filePath() {
        return this.filePath;
    }

    public boolean isFileOnClasspath() {
        return this.isFileOnClasspath;
    }

    public Option<String> password() {
        return this.password;
    }

    public String storeType() {
        return this.storeType;
    }

    public KeyStoreConfig withData(Option<String> data) {
        return this.copy(data, None$.MODULE$, this.copy$default$3(), this.copy$default$4(), this.copy$default$5());
    }

    public KeyStoreConfig withFilePath(Option<String> filePath) {
        Option<String> x$1 = filePath;
        None$ x$2 = None$.MODULE$;
        boolean x$3 = this.copy$default$3();
        Option<String> x$4 = this.copy$default$4();
        String x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public KeyStoreConfig withFileOnClassPath(boolean isFileOnClasspath) {
        boolean x$1 = isFileOnClasspath;
        None$ x$2 = None$.MODULE$;
        Option<String> x$3 = this.copy$default$2();
        Option<String> x$4 = this.copy$default$4();
        String x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    public KeyStoreConfig withPassword(Option<String> value) {
        Option<String> x$1 = value;
        Option<String> x$2 = this.copy$default$1();
        Option<String> x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        String x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$4, x$1, x$5);
    }

    public KeyStoreConfig withStoreType(String value) {
        String x$1 = value;
        Option<String> x$2 = this.copy$default$1();
        Option<String> x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<String> x$5 = this.copy$default$4();
        return this.copy(x$2, x$3, x$4, x$5, x$1);
    }

    private KeyStoreConfig copy(Option<String> data, Option<String> filePath, boolean isFileOnClasspath, Option<String> password, String storeType) {
        return new KeyStoreConfig(data, filePath, isFileOnClasspath, password, storeType);
    }

    private Option<String> copy$default$1() {
        return this.data();
    }

    private Option<String> copy$default$2() {
        return this.filePath();
    }

    private boolean copy$default$3() {
        return this.isFileOnClasspath();
    }

    private Option<String> copy$default$4() {
        return this.password();
    }

    private String copy$default$5() {
        return this.storeType();
    }

    public String toString() {
        return new StringBuilder(19).append("KeyStoreConfig(").append(this.data()).append(",").append(this.filePath()).append(",").append(this.isFileOnClasspath()).append(",").append(this.storeType()).append(")").toString();
    }

    public KeyStoreConfig(Option<String> data, Option<String> filePath, boolean isFileOnClasspath, Option<String> password, String storeType) {
        this.data = data;
        this.filePath = filePath;
        this.isFileOnClasspath = isFileOnClasspath;
        this.password = password;
        this.storeType = storeType;
        Predef$.MODULE$.assert(filePath.isDefined() ^ data.isDefined(), (Function0<Object>)(Function0<String> & Serializable)() -> "Either key store path or data must be defined, but not both.");
    }
}

