/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.TrustStoreConfig$;
import java.io.Serializable;
import java.util.Optional;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}b\u0001\u0002\u0011\"\u0005)B\u0001\"\r\u0001\u0003\u0006\u0004%\tA\r\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005g!A!\t\u0001BC\u0002\u0013\u0005!\u0007\u0003\u0005D\u0001\t\u0005\t\u0015!\u00034\u0011!!\u0005A!b\u0001\n\u0003\u0011\u0004\u0002C#\u0001\u0005\u0003\u0005\u000b\u0011B\u001a\t\u0011\u0019\u0003!Q1A\u0005\u0002\u001dC\u0001b\u0013\u0001\u0003\u0002\u0003\u0006I\u0001\u0013\u0005\t\u0019\u0002\u0011)\u0019!C\u0001\u001b\"Aa\n\u0001B\u0001B\u0003%a\u0007\u0003\u0004P\u0001\u0011\u00051\u0005\u0015\u0005\u00061\u0002!\t!\u0017\u0005\u00067\u0002!\t\u0001\u0018\u0005\u0006=\u0002!\ta\u0018\u0005\u0006C\u0002!\tA\u0019\u0005\u0006I\u0002!\t!\u001a\u0005\u0006Q\u0002!I!\u001b\u0005\b_\u0002\t\n\u0011\"\u0003q\u0011\u001dY\b!%A\u0005\nADq\u0001 \u0001\u0012\u0002\u0013%\u0001\u000fC\u0004~\u0001E\u0005I\u0011\u0002@\t\u0013\u0005\u0005\u0001!%A\u0005\n\u0005\r\u0001bBA\u0004\u0001\u0011\u0005\u0013\u0011B\u0004\b\u0003\u0017\t\u0003\u0012AA\u0007\r\u0019\u0001\u0013\u0005#\u0001\u0002\u0010!1q*\u0007C\u0001\u0003#Aq!a\u0005\u001a\t\u0003\t)\u0002C\u0004\u0002\u0014e!\t!a\u0007\t\u000f\u0005\r\u0012\u0004\"\u0001\u0002&!I\u00111H\r\u0012\u0002\u0013\u00051E \u0005\u000b\u0003{I\u0012\u0013!C\u0001G\u0005\r!\u0001\u0005+skN$8\u000b^8sK\u000e{gNZ5h\u0015\t\u00113%A\u0002tg2T!\u0001J\u0013\u0002\u0013M\u001cHnY8oM&<'B\u0001\u0014(\u0003!!\u0018\u0010]3tC\u001a,'\"\u0001\u0015\u0002\u0007\r|Wn\u0001\u0001\u0014\u0005\u0001Y\u0003C\u0001\u00170\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#AB!osJ+g-\u0001\u0003eCR\fW#A\u001a\u0011\u00071\"d'\u0003\u00026[\t1q\n\u001d;j_:\u0004\"a\u000e \u000f\u0005ab\u0004CA\u001d.\u001b\u0005Q$BA\u001e*\u0003\u0019a$o\\8u}%\u0011Q(L\u0001\u0007!J,G-\u001a4\n\u0005}\u0002%AB*ue&twM\u0003\u0002>[\u0005)A-\u0019;bA\u0005Aa-\u001b7f!\u0006$\b.A\u0005gS2,\u0007+\u0019;iA\u0005A\u0001/Y:to>\u0014H-A\u0005qCN\u001cxo\u001c:eA\u0005\t\u0012n\u001d$jY\u0016|en\u00117bgN\u0004\u0018\r\u001e5\u0016\u0003!\u0003\"\u0001L%\n\u0005)k#a\u0002\"p_2,\u0017M\\\u0001\u0013SN4\u0015\u000e\\3P]\u000ec\u0017m]:qCRD\u0007%A\u0005ti>\u0014X\rV=qKV\ta'\u0001\u0006ti>\u0014X\rV=qK\u0002\na\u0001P5oSRtDCB)T)V3v\u000b\u0005\u0002S\u00015\t\u0011\u0005C\u00032\u0017\u0001\u00071\u0007C\u0003C\u0017\u0001\u00071\u0007C\u0003E\u0017\u0001\u00071\u0007C\u0004G\u0017A\u0005\t\u0019\u0001%\t\u000f1[\u0001\u0013!a\u0001m\u0005Aq/\u001b;i\t\u0006$\u0018\r\u0006\u0002R5\")\u0011\u0007\u0004a\u0001g\u0005aq/\u001b;i\r&dW\rU1uQR\u0011\u0011+\u0018\u0005\u0006\u00056\u0001\raM\u0001\ro&$\b\u000eU1tg^|'\u000f\u001a\u000b\u0003#\u0002DQ\u0001\u0012\bA\u0002M\n1c^5uQ\u001aKG.Z(o\u00072\f7o\u001d9bi\"$\"!U2\t\u000b\u0019{\u0001\u0019\u0001%\u0002\u001b]LG\u000f[*u_J,G+\u001f9f)\t\tf\rC\u0003h!\u0001\u0007a'A\u0003wC2,X-\u0001\u0003d_BLHCB)kW2lg\u000eC\u00042#A\u0005\t\u0019A\u001a\t\u000f\t\u000b\u0002\u0013!a\u0001g!9A)\u0005I\u0001\u0002\u0004\u0019\u0004b\u0002$\u0012!\u0003\u0005\r\u0001\u0013\u0005\b\u0019F\u0001\n\u00111\u00017\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012!\u001d\u0016\u0003gI\\\u0013a\u001d\t\u0003ifl\u0011!\u001e\u0006\u0003m^\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005al\u0013AC1o]>$\u0018\r^5p]&\u0011!0\u001e\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\u0012a \u0016\u0003\u0011J\fabY8qs\u0012\"WMZ1vYR$S'\u0006\u0002\u0002\u0006)\u0012aG]\u0001\ti>\u001cFO]5oOR\ta'\u0001\tUeV\u001cHo\u0015;pe\u0016\u001cuN\u001c4jOB\u0011!+G\n\u00033-\"\"!!\u0004\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000bE\u000b9\"!\u0007\t\u000bEZ\u0002\u0019A\u001a\t\u000b\t[\u0002\u0019A\u001a\u0015\u000fE\u000bi\"a\b\u0002\"!)\u0011\u0007\ba\u0001g!)!\t\ba\u0001g!)A\t\ba\u0001g\u0005Yq-\u001a;J]N$\u0018M\\2f)\u0015\t\u0016qEA\u001d\u0011\u0019\tT\u00041\u0001\u0002*A)\u00111FA\u001bm5\u0011\u0011Q\u0006\u0006\u0005\u0003_\t\t$\u0001\u0003vi&d'BAA\u001a\u0003\u0011Q\u0017M^1\n\t\u0005]\u0012Q\u0006\u0002\t\u001fB$\u0018n\u001c8bY\"1!)\ba\u0001\u0003S\t1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\"\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$S\u0007")
public final class TrustStoreConfig {
    private final Option<String> data;
    private final Option<String> filePath;
    private final Option<String> password;
    private final boolean isFileOnClasspath;
    private final String storeType;

    public static TrustStoreConfig getInstance(Optional<String> data, Optional<String> filePath) {
        return TrustStoreConfig$.MODULE$.getInstance(data, filePath);
    }

    public static TrustStoreConfig apply(Option<String> data, Option<String> filePath, Option<String> password) {
        return TrustStoreConfig$.MODULE$.apply(data, filePath, password);
    }

    public static TrustStoreConfig apply(Option<String> data, Option<String> filePath) {
        return TrustStoreConfig$.MODULE$.apply(data, filePath);
    }

    public Option<String> data() {
        return this.data;
    }

    public Option<String> filePath() {
        return this.filePath;
    }

    public Option<String> password() {
        return this.password;
    }

    public boolean isFileOnClasspath() {
        return this.isFileOnClasspath;
    }

    public String storeType() {
        return this.storeType;
    }

    public TrustStoreConfig withData(Option<String> data) {
        return this.copy(data, None$.MODULE$, this.copy$default$3(), this.copy$default$4(), this.copy$default$5());
    }

    public TrustStoreConfig withFilePath(Option<String> filePath) {
        Option<String> x$1 = filePath;
        None$ x$2 = None$.MODULE$;
        Option<String> x$3 = this.copy$default$3();
        boolean x$4 = this.copy$default$4();
        String x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public TrustStoreConfig withPassword(Option<String> password) {
        Option<String> x$1 = password;
        Option<String> x$2 = this.copy$default$1();
        Option<String> x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$4();
        String x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    public TrustStoreConfig withFileOnClasspath(boolean isFileOnClasspath) {
        boolean x$1 = isFileOnClasspath;
        None$ x$2 = None$.MODULE$;
        Option<String> x$3 = this.copy$default$2();
        Option<String> x$4 = this.copy$default$3();
        String x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$4, x$1, x$5);
    }

    public TrustStoreConfig withStoreType(String value) {
        String x$1 = value;
        Option<String> x$2 = this.copy$default$1();
        Option<String> x$3 = this.copy$default$2();
        Option<String> x$4 = this.copy$default$3();
        boolean x$5 = this.copy$default$4();
        return this.copy(x$2, x$3, x$4, x$5, x$1);
    }

    private TrustStoreConfig copy(Option<String> data, Option<String> filePath, Option<String> password, boolean isFileOnClasspath, String storeType) {
        return new TrustStoreConfig(data, filePath, password, isFileOnClasspath, storeType);
    }

    private Option<String> copy$default$1() {
        return this.data();
    }

    private Option<String> copy$default$2() {
        return this.filePath();
    }

    private Option<String> copy$default$3() {
        return this.password();
    }

    private boolean copy$default$4() {
        return this.isFileOnClasspath();
    }

    private String copy$default$5() {
        return this.storeType();
    }

    public String toString() {
        return new StringBuilder(21).append("TrustStoreConfig(").append(this.data()).append(",").append(this.filePath()).append(",").append(this.isFileOnClasspath()).append(",").append(this.storeType()).append(")").toString();
    }

    public TrustStoreConfig(Option<String> data, Option<String> filePath, Option<String> password, boolean isFileOnClasspath, String storeType) {
        this.data = data;
        this.filePath = filePath;
        this.password = password;
        this.isFileOnClasspath = isFileOnClasspath;
        this.storeType = storeType;
        Predef$.MODULE$.assert(filePath.isDefined() ^ data.isDefined(), (Function0<Object>)(Function0<String> & Serializable)() -> "Either trust store path or data must be defined, but not both.");
    }
}

