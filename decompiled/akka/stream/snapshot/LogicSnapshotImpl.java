/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.snapshot.HideImpl;
import akka.stream.snapshot.LogicSnapshot;
import akka.stream.snapshot.LogicSnapshotImpl$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Me!\u0002\u000f\u001e\u0005\u0006\u001a\u0003\u0002C!\u0001\u0005+\u0007I\u0011\u0001\"\t\u0011\u0019\u0003!\u0011#Q\u0001\n\rC\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t#\u0002\u0011\t\u0012)A\u0005\u0013\"A!\u000b\u0001BK\u0002\u0013\u00051\u000b\u0003\u0005Y\u0001\tE\t\u0015!\u0003U\u0011\u0015I\u0006\u0001\"\u0001[\u0011\u0015y\u0006\u0001\"\u0011a\u0011\u001d\t\u0007!!A\u0005\u0002\tDqA\u001a\u0001\u0012\u0002\u0013\u0005q\rC\u0004s\u0001E\u0005I\u0011A:\t\u000fU\u0004\u0011\u0013!C\u0001m\"9\u0001\u0010AA\u0001\n\u0003J\b\u0002CA\u0002\u0001\u0005\u0005I\u0011\u0001\"\t\u0013\u0005\u0015\u0001!!A\u0005\u0002\u0005\u001d\u0001\"CA\n\u0001\u0005\u0005I\u0011IA\u000b\u0011%\t\u0019\u0003AA\u0001\n\u0003\t)\u0003C\u0005\u00020\u0001\t\t\u0011\"\u0011\u00022!I\u0011Q\u0007\u0001\u0002\u0002\u0013\u0005\u0013q\u0007\u0005\n\u0003s\u0001\u0011\u0011!C!\u0003w9!\"a\u0013\u001e\u0003\u0003E\t!IA'\r%aR$!A\t\u0002\u0005\ny\u0005\u0003\u0004Z-\u0011\u0005\u0011q\r\u0005\t?Z\t\t\u0011\"\u0012\u0002j!I\u00111\u000e\f\u0002\u0002\u0013\u0005\u0015Q\u000e\u0005\n\u0003k2\u0012\u0011!CA\u0003oB\u0011\"!#\u0017\u0003\u0003%I!a#\u0003#1{w-[2T]\u0006\u00048\u000f[8u\u00136\u0004HN\u0003\u0002\u001f?\u0005A1O\\1qg\"|GO\u0003\u0002!C\u000511\u000f\u001e:fC6T\u0011AI\u0001\u0005C.\\\u0017m\u0005\u0004\u0001I)r\u0013\u0007\u000e\t\u0003K!j\u0011A\n\u0006\u0002O\u0005)1oY1mC&\u0011\u0011F\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005-bS\"A\u000f\n\u00055j\"!\u0004'pO&\u001c7K\\1qg\"|G\u000f\u0005\u0002,_%\u0011\u0001'\b\u0002\t\u0011&$W-S7qYB\u0011QEM\u0005\u0003g\u0019\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00026}9\u0011a\u0007\u0010\b\u0003omj\u0011\u0001\u000f\u0006\u0003si\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002O%\u0011QHJ\u0001\ba\u0006\u001c7.Y4f\u0013\ty\u0004I\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002>M\u0005)\u0011N\u001c3fqV\t1\t\u0005\u0002&\t&\u0011QI\n\u0002\u0004\u0013:$\u0018AB5oI\u0016D\b%A\u0003mC\n,G.F\u0001J!\tQeJ\u0004\u0002L\u0019B\u0011qGJ\u0005\u0003\u001b\u001a\na\u0001\u0015:fI\u00164\u0017BA(Q\u0005\u0019\u0019FO]5oO*\u0011QJJ\u0001\u0007Y\u0006\u0014W\r\u001c\u0011\u0002\u0015\u0005$HO]5ckR,7/F\u0001U!\t)f+D\u0001 \u0013\t9vD\u0001\u0006BiR\u0014\u0018NY;uKN\f1\"\u0019;ue&\u0014W\u000f^3tA\u00051A(\u001b8jiz\"Ba\u0017/^=B\u00111\u0006\u0001\u0005\u0006\u0003\u001e\u0001\ra\u0011\u0005\u0006\u000f\u001e\u0001\r!\u0013\u0005\u0006%\u001e\u0001\r\u0001V\u0001\ti>\u001cFO]5oOR\t\u0011*\u0001\u0003d_BLH\u0003B.dI\u0016Dq!Q\u0005\u0011\u0002\u0003\u00071\tC\u0004H\u0013A\u0005\t\u0019A%\t\u000fIK\u0001\u0013!a\u0001)\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u00015+\u0005\rK7&\u00016\u0011\u0005-\u0004X\"\u00017\u000b\u00055t\u0017!C;oG\",7m[3e\u0015\tyg%\u0001\u0006b]:|G/\u0019;j_:L!!\u001d7\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003QT#!S5\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\tqO\u000b\u0002US\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012A\u001f\t\u0004w\u0006\u0005Q\"\u0001?\u000b\u0005ut\u0018\u0001\u00027b]\u001eT\u0011a`\u0001\u0005U\u00064\u0018-\u0003\u0002Py\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA\u0005\u0003\u001f\u00012!JA\u0006\u0013\r\tiA\n\u0002\u0004\u0003:L\b\u0002CA\t\u001f\u0005\u0005\t\u0019A\"\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t9\u0002\u0005\u0004\u0002\u001a\u0005}\u0011\u0011B\u0007\u0003\u00037Q1!!\b'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\tYB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0014\u0003[\u00012!JA\u0015\u0013\r\tYC\n\u0002\b\u0005>|G.Z1o\u0011%\t\t\"EA\u0001\u0002\u0004\tI!\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,Gc\u0001>\u00024!A\u0011\u0011\u0003\n\u0002\u0002\u0003\u00071)\u0001\u0005iCND7i\u001c3f)\u0005\u0019\u0015AB3rk\u0006d7\u000f\u0006\u0003\u0002(\u0005u\u0002\"CA\t)\u0005\u0005\t\u0019AA\u0005Q\r\u0001\u0011\u0011\t\t\u0005\u0003\u0007\n9%\u0004\u0002\u0002F)\u0011q.I\u0005\u0005\u0003\u0013\n)EA\u0006J]R,'O\\1m\u0003BL\u0017!\u0005'pO&\u001c7K\\1qg\"|G/S7qYB\u00111FF\n\u0006-\u0005E\u0013Q\f\t\t\u0003'\nIfQ%U76\u0011\u0011Q\u000b\u0006\u0004\u0003/2\u0013a\u0002:v]RLW.Z\u0005\u0005\u00037\n)FA\tBEN$(/Y2u\rVt7\r^5p]N\u0002B!a\u0018\u0002f5\u0011\u0011\u0011\r\u0006\u0004\u0003Gr\u0018AA5p\u0013\ry\u0014\u0011\r\u000b\u0003\u0003\u001b\"\u0012A_\u0001\u0006CB\u0004H.\u001f\u000b\b7\u0006=\u0014\u0011OA:\u0011\u0015\t\u0015\u00041\u0001D\u0011\u00159\u0015\u00041\u0001J\u0011\u0015\u0011\u0016\u00041\u0001U\u0003\u001d)h.\u00199qYf$B!!\u001f\u0002\u0006B)Q%a\u001f\u0002\u0000%\u0019\u0011Q\u0010\u0014\u0003\r=\u0003H/[8o!\u0019)\u0013\u0011Q\"J)&\u0019\u00111\u0011\u0014\u0003\rQ+\b\u000f\\34\u0011!\t9IGA\u0001\u0002\u0004Y\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u0012\t\u0004w\u0006=\u0015bAAIy\n1qJ\u00196fGR\u0004")
@InternalApi
public final class LogicSnapshotImpl
implements LogicSnapshot,
HideImpl,
Product,
Serializable {
    private final int index;
    private final String label;
    private final Attributes attributes;

    public static Option<Tuple3<Object, String, Attributes>> unapply(LogicSnapshotImpl x$0) {
        return LogicSnapshotImpl$.MODULE$.unapply(x$0);
    }

    public static LogicSnapshotImpl apply(int index, String label, Attributes attributes) {
        return LogicSnapshotImpl$.MODULE$.apply(index, label, attributes);
    }

    public static Function1<Tuple3<Object, String, Attributes>, LogicSnapshotImpl> tupled() {
        return LogicSnapshotImpl$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<String, Function1<Attributes, LogicSnapshotImpl>>> curried() {
        return LogicSnapshotImpl$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public /* synthetic */ String akka$stream$snapshot$HideImpl$$super$toString() {
        return super.toString();
    }

    public int index() {
        return this.index;
    }

    @Override
    public String label() {
        return this.label;
    }

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public String toString() {
        return new StringBuilder(7).append("Logic(").append(this.label()).append(")").toString();
    }

    public LogicSnapshotImpl copy(int index, String label, Attributes attributes) {
        return new LogicSnapshotImpl(index, label, attributes);
    }

    public int copy$default$1() {
        return this.index();
    }

    public String copy$default$2() {
        return this.label();
    }

    public Attributes copy$default$3() {
        return this.attributes();
    }

    @Override
    public String productPrefix() {
        return "LogicSnapshotImpl";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.index());
            }
            case 1: {
                return this.label();
            }
            case 2: {
                return this.attributes();
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
        return x$1 instanceof LogicSnapshotImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "index";
            }
            case 1: {
                return "label";
            }
            case 2: {
                return "attributes";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.index());
        n = Statics.mix(n, Statics.anyHash(this.label()));
        n = Statics.mix(n, Statics.anyHash(this.attributes()));
        return Statics.finalizeHash(n, 3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof LogicSnapshotImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        LogicSnapshotImpl logicSnapshotImpl = (LogicSnapshotImpl)x$1;
        if (this.index() != logicSnapshotImpl.index()) return false;
        String string = this.label();
        String string2 = logicSnapshotImpl.label();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = logicSnapshotImpl.attributes();
        if (attributes == null) {
            if (attributes2 == null) return true;
            return false;
        } else {
            if (!((Object)attributes).equals(attributes2)) return false;
            return true;
        }
    }

    public LogicSnapshotImpl(int index, String label, Attributes attributes) {
        this.index = index;
        this.label = label;
        this.attributes = attributes;
        HideImpl.$init$(this);
        Product.$init$(this);
    }
}

