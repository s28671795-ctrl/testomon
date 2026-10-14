/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.io.dns.internal.ResolvConf$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005d!B\r\u001b\u0001r\u0011\u0003\u0002C\u001d\u0001\u0005+\u0007I\u0011\u0001\u001e\t\u0011\u0019\u0003!\u0011#Q\u0001\nmB\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t\u0019\u0002\u0011\t\u0012)A\u0005\u0013\")Q\n\u0001C\u0001\u001d\"91\u000bAA\u0001\n\u0003!\u0006bB,\u0001#\u0003%\t\u0001\u0017\u0005\bG\u0002\t\n\u0011\"\u0001e\u0011\u001d1\u0007!!A\u0005B\u001dDqa\u001c\u0001\u0002\u0002\u0013\u0005\u0001\nC\u0004q\u0001\u0005\u0005I\u0011A9\t\u000f]\u0004\u0011\u0011!C!q\"Aq\u0010AA\u0001\n\u0003\t\t\u0001C\u0005\u0002\f\u0001\t\t\u0011\"\u0011\u0002\u000e!I\u0011\u0011\u0003\u0001\u0002\u0002\u0013\u0005\u00131\u0003\u0005\n\u0003+\u0001\u0011\u0011!C!\u0003/A\u0011\"!\u0007\u0001\u0003\u0003%\t%a\u0007\b\u0015\u0005}!$!A\t\u0002q\t\tCB\u0005\u001a5\u0005\u0005\t\u0012\u0001\u000f\u0002$!1Qj\u0005C\u0001\u0003sA\u0011\"!\u0006\u0014\u0003\u0003%)%a\u0006\t\u0013\u0005m2#!A\u0005\u0002\u0006u\u0002\"CA\"'\u0005\u0005I\u0011QA#\u0011%\t9fEA\u0001\n\u0013\tIF\u0001\u0006SKN|GN^\"p]\u001aT!a\u0007\u000f\u0002\u0011%tG/\u001a:oC2T!!\b\u0010\u0002\u0007\u0011t7O\u0003\u0002 A\u0005\u0011\u0011n\u001c\u0006\u0002C\u0005!\u0011m[6b'\u0011\u00011%\u000b\u0017\u0011\u0005\u0011:S\"A\u0013\u000b\u0003\u0019\nQa]2bY\u0006L!\u0001K\u0013\u0003\r\u0005s\u0017PU3g!\t!#&\u0003\u0002,K\t9\u0001K]8ek\u000e$\bCA\u00177\u001d\tqCG\u0004\u00020g5\t\u0001G\u0003\u00022e\u00051AH]8piz\u001a\u0001!C\u0001'\u0013\t)T%A\u0004qC\u000e\\\u0017mZ3\n\u0005]B$\u0001D*fe&\fG.\u001b>bE2,'BA\u001b&\u0003\u0019\u0019X-\u0019:dQV\t1\bE\u0002.yyJ!!\u0010\u001d\u0003\t1K7\u000f\u001e\t\u0003\u007f\rs!\u0001Q!\u0011\u0005=*\u0013B\u0001\"&\u0003\u0019\u0001&/\u001a3fM&\u0011A)\u0012\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\t+\u0013aB:fCJ\u001c\u0007\u000eI\u0001\u0006]\u0012|Go]\u000b\u0002\u0013B\u0011AES\u0005\u0003\u0017\u0016\u00121!\u00138u\u0003\u0019qGm\u001c;tA\u00051A(\u001b8jiz\"2aT)S!\t\u0001\u0006!D\u0001\u001b\u0011\u0015IT\u00011\u0001<\u0011\u00159U\u00011\u0001J\u0003\u0011\u0019w\u000e]=\u0015\u0007=+f\u000bC\u0004:\rA\u0005\t\u0019A\u001e\t\u000f\u001d3\u0001\u0013!a\u0001\u0013\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A-+\u0005mR6&A.\u0011\u0005q\u000bW\"A/\u000b\u0005y{\u0016!C;oG\",7m[3e\u0015\t\u0001W%\u0001\u0006b]:|G/\u0019;j_:L!AY/\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003\u0015T#!\u0013.\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005A\u0007CA5o\u001b\u0005Q'BA6m\u0003\u0011a\u0017M\\4\u000b\u00035\fAA[1wC&\u0011AI[\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t\u0011X\u000f\u0005\u0002%g&\u0011A/\n\u0002\u0004\u0003:L\bb\u0002<\f\u0003\u0003\u0005\r!S\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003e\u00042A_?s\u001b\u0005Y(B\u0001?&\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003}n\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u00111AA\u0005!\r!\u0013QA\u0005\u0004\u0003\u000f)#a\u0002\"p_2,\u0017M\u001c\u0005\bm6\t\t\u00111\u0001s\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007!\fy\u0001C\u0004w\u001d\u0005\u0005\t\u0019A%\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!S\u0001\ti>\u001cFO]5oOR\t\u0001.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u0007\ti\u0002C\u0004w#\u0005\u0005\t\u0019\u0001:\u0002\u0015I+7o\u001c7w\u0007>tg\r\u0005\u0002Q'M)1#!\n\u00022A9\u0011qEA\u0017w%{UBAA\u0015\u0015\r\tY#J\u0001\beVtG/[7f\u0013\u0011\ty#!\u000b\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u00024\u0005]RBAA\u001b\u0015\tyB.C\u00028\u0003k!\"!!\t\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000b=\u000by$!\u0011\t\u000be2\u0002\u0019A\u001e\t\u000b\u001d3\u0002\u0019A%\u0002\u000fUt\u0017\r\u001d9msR!\u0011qIA*!\u0015!\u0013\u0011JA'\u0013\r\tY%\n\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000b\u0011\nyeO%\n\u0007\u0005ESE\u0001\u0004UkBdWM\r\u0005\t\u0003+:\u0012\u0011!a\u0001\u001f\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005m\u0003cA5\u0002^%\u0019\u0011q\f6\u0003\r=\u0013'.Z2u\u0001")
public class ResolvConf
implements Product,
Serializable {
    private final List<String> search;
    private final int ndots;

    public static Option<Tuple2<List<String>, Object>> unapply(ResolvConf x$0) {
        return ResolvConf$.MODULE$.unapply(x$0);
    }

    public static ResolvConf apply(List<String> search, int ndots) {
        return ResolvConf$.MODULE$.apply(search, ndots);
    }

    public static Function1<Tuple2<List<String>, Object>, ResolvConf> tupled() {
        return ResolvConf$.MODULE$.tupled();
    }

    public static Function1<List<String>, Function1<Object, ResolvConf>> curried() {
        return ResolvConf$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public List<String> search() {
        return this.search;
    }

    public int ndots() {
        return this.ndots;
    }

    public ResolvConf copy(List<String> search, int ndots) {
        return new ResolvConf(search, ndots);
    }

    public List<String> copy$default$1() {
        return this.search();
    }

    public int copy$default$2() {
        return this.ndots();
    }

    @Override
    public String productPrefix() {
        return "ResolvConf";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.search();
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.ndots());
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof ResolvConf;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "search";
            }
            case 1: {
                return "ndots";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.search()));
        n = Statics.mix(n, this.ndots());
        return Statics.finalizeHash(n, 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ResolvConf)) return false;
        boolean bl = true;
        if (!bl) return false;
        ResolvConf resolvConf = (ResolvConf)x$1;
        if (this.ndots() != resolvConf.ndots()) return false;
        List<String> list = this.search();
        List<String> list2 = resolvConf.search();
        if (list == null) {
            if (list2 != null) {
                return false;
            }
        } else if (!((Object)list).equals(list2)) return false;
        if (!resolvConf.canEqual(this)) return false;
        return true;
    }

    public ResolvConf(List<String> search, int ndots) {
        this.search = search;
        this.ndots = ndots;
        Product.$init$(this);
    }
}

