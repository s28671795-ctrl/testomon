/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.InternalApi;
import akka.stream.snapshot.ConnectionSnapshot;
import akka.stream.snapshot.ConnectionSnapshotImpl$;
import akka.stream.snapshot.HideImpl;
import akka.stream.snapshot.LogicSnapshot;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005}e!\u0002\u0010 \u0005\u000e*\u0003\u0002C\"\u0001\u0005+\u0007I\u0011\u0001#\t\u0011!\u0003!\u0011#Q\u0001\n\u0015C\u0001\"\u0013\u0001\u0003\u0016\u0004%\tA\u0013\u0005\t\u001d\u0002\u0011\t\u0012)A\u0005\u0017\"Aq\n\u0001BK\u0002\u0013\u0005!\n\u0003\u0005Q\u0001\tE\t\u0015!\u0003L\u0011!\t\u0006A!f\u0001\n\u0003\u0011\u0006\u0002\u0003.\u0001\u0005#\u0005\u000b\u0011B*\t\u000bm\u0003A\u0011\u0001/\t\u000f\t\u0004\u0011\u0011!C\u0001G\"9\u0001\u000eAI\u0001\n\u0003I\u0007b\u0002;\u0001#\u0003%\t!\u001e\u0005\bo\u0002\t\n\u0011\"\u0001v\u0011\u001dA\b!%A\u0005\u0002eDqa\u001f\u0001\u0002\u0002\u0013\u0005C\u0010\u0003\u0005\u0002\f\u0001\t\t\u0011\"\u0001E\u0011%\ti\u0001AA\u0001\n\u0003\ty\u0001C\u0005\u0002\u001c\u0001\t\t\u0011\"\u0011\u0002\u001e!I\u00111\u0006\u0001\u0002\u0002\u0013\u0005\u0011Q\u0006\u0005\n\u0003o\u0001\u0011\u0011!C!\u0003sA\u0011\"!\u0010\u0001\u0003\u0003%\t%a\u0010\t\u0013\u0005\u0005\u0003!!A\u0005B\u0005\rsACA*?\u0005\u0005\t\u0012A\u0012\u0002V\u0019IadHA\u0001\u0012\u0003\u0019\u0013q\u000b\u0005\u00077b!\t!a\u001c\t\u0013\u0005E\u0004$!A\u0005F\u0005M\u0004\"CA;1\u0005\u0005I\u0011QA<\u0011%\t\t\tGA\u0001\n\u0003\u000b\u0019\tC\u0005\u0002\u0016b\t\t\u0011\"\u0003\u0002\u0018\n12i\u001c8oK\u000e$\u0018n\u001c8T]\u0006\u00048\u000f[8u\u00136\u0004HN\u0003\u0002!C\u0005A1O\\1qg\"|GO\u0003\u0002#G\u000511\u000f\u001e:fC6T\u0011\u0001J\u0001\u0005C.\\\u0017m\u0005\u0004\u0001M1\u00024G\u000e\t\u0003O)j\u0011\u0001\u000b\u0006\u0002S\u0005)1oY1mC&\u00111\u0006\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\u00055rS\"A\u0010\n\u0005=z\"AE\"p]:,7\r^5p]Ns\u0017\r]:i_R\u0004\"!L\u0019\n\u0005Iz\"\u0001\u0003%jI\u0016LU\u000e\u001d7\u0011\u0005\u001d\"\u0014BA\u001b)\u0005\u001d\u0001&o\u001c3vGR\u0004\"a\u000e!\u000f\u0005ardBA\u001d>\u001b\u0005Q$BA\u001e=\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0015\n\u0005}B\u0013a\u00029bG.\fw-Z\u0005\u0003\u0003\n\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!a\u0010\u0015\u0002\u0005%$W#A#\u0011\u0005\u001d2\u0015BA$)\u0005\rIe\u000e^\u0001\u0004S\u0012\u0004\u0013AA5o+\u0005Y\u0005CA\u0017M\u0013\tiuDA\u0007M_\u001eL7m\u00158baNDw\u000e^\u0001\u0004S:\u0004\u0013aA8vi\u0006!q.\u001e;!\u0003\u0015\u0019H/\u0019;f+\u0005\u0019\u0006C\u0001+X\u001d\tiS+\u0003\u0002W?\u0005\u00112i\u001c8oK\u000e$\u0018n\u001c8T]\u0006\u00048\u000f[8u\u0013\tA\u0016LA\bD_:tWm\u0019;j_:\u001cF/\u0019;f\u0015\t1v$\u0001\u0004ti\u0006$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u000busv\fY1\u0011\u00055\u0002\u0001\"B\"\n\u0001\u0004)\u0005\"B%\n\u0001\u0004Y\u0005\"B(\n\u0001\u0004Y\u0005\"B)\n\u0001\u0004\u0019\u0016\u0001B2paf$R!\u00183fM\u001eDqa\u0011\u0006\u0011\u0002\u0003\u0007Q\tC\u0004J\u0015A\u0005\t\u0019A&\t\u000f=S\u0001\u0013!a\u0001\u0017\"9\u0011K\u0003I\u0001\u0002\u0004\u0019\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0002U*\u0012Qi[\u0016\u0002YB\u0011QN]\u0007\u0002]*\u0011q\u000e]\u0001\nk:\u001c\u0007.Z2lK\u0012T!!\u001d\u0015\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002t]\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\taO\u000b\u0002LW\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001a\u0014AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0002u*\u00121k[\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003u\u00042A`A\u0004\u001b\u0005y(\u0002BA\u0001\u0003\u0007\tA\u0001\\1oO*\u0011\u0011QA\u0001\u0005U\u00064\u0018-C\u0002\u0002\n}\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003#\t9\u0002E\u0002(\u0003'I1!!\u0006)\u0005\r\te.\u001f\u0005\t\u00033\t\u0012\u0011!a\u0001\u000b\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\b\u0011\r\u0005\u0005\u0012qEA\t\u001b\t\t\u0019CC\u0002\u0002&!\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI#a\t\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003_\t)\u0004E\u0002(\u0003cI1!a\r)\u0005\u001d\u0011un\u001c7fC:D\u0011\"!\u0007\u0014\u0003\u0003\u0005\r!!\u0005\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004{\u0006m\u0002\u0002CA\r)\u0005\u0005\t\u0019A#\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!R\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005=\u0012Q\t\u0005\n\u000331\u0012\u0011!a\u0001\u0003#A3\u0001AA%!\u0011\tY%a\u0014\u000e\u0005\u00055#BA9$\u0013\u0011\t\t&!\u0014\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0017\u0007>tg.Z2uS>t7K\\1qg\"|G/S7qYB\u0011Q\u0006G\n\u00061\u0005e\u0013Q\r\t\n\u00037\n\t'R&L'vk!!!\u0018\u000b\u0007\u0005}\u0003&A\u0004sk:$\u0018.\\3\n\t\u0005\r\u0014Q\f\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:$\u0004\u0003BA4\u0003[j!!!\u001b\u000b\t\u0005-\u00141A\u0001\u0003S>L1!QA5)\t\t)&\u0001\u0005u_N#(/\u001b8h)\u0005i\u0018!B1qa2LH#C/\u0002z\u0005m\u0014QPA@\u0011\u0015\u00195\u00041\u0001F\u0011\u0015I5\u00041\u0001L\u0011\u0015y5\u00041\u0001L\u0011\u0015\t6\u00041\u0001T\u0003\u001d)h.\u00199qYf$B!!\"\u0002\u0012B)q%a\"\u0002\f&\u0019\u0011\u0011\u0012\u0015\u0003\r=\u0003H/[8o!\u001d9\u0013QR#L\u0017NK1!a$)\u0005\u0019!V\u000f\u001d7fi!A\u00111\u0013\u000f\u0002\u0002\u0003\u0007Q,A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!'\u0011\u0007y\fY*C\u0002\u0002\u001e~\u0014aa\u00142kK\u000e$\b")
@InternalApi
public final class ConnectionSnapshotImpl
implements ConnectionSnapshot,
HideImpl,
Product,
Serializable {
    private final int id;
    private final LogicSnapshot in;
    private final LogicSnapshot out;
    private final ConnectionSnapshot.ConnectionState state;

    public static Option<Tuple4<Object, LogicSnapshot, LogicSnapshot, ConnectionSnapshot.ConnectionState>> unapply(ConnectionSnapshotImpl x$0) {
        return ConnectionSnapshotImpl$.MODULE$.unapply(x$0);
    }

    public static ConnectionSnapshotImpl apply(int id, LogicSnapshot in, LogicSnapshot out, ConnectionSnapshot.ConnectionState state) {
        return ConnectionSnapshotImpl$.MODULE$.apply(id, in, out, state);
    }

    public static Function1<Tuple4<Object, LogicSnapshot, LogicSnapshot, ConnectionSnapshot.ConnectionState>, ConnectionSnapshotImpl> tupled() {
        return ConnectionSnapshotImpl$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<LogicSnapshot, Function1<LogicSnapshot, Function1<ConnectionSnapshot.ConnectionState, ConnectionSnapshotImpl>>>> curried() {
        return ConnectionSnapshotImpl$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public /* synthetic */ String akka$stream$snapshot$HideImpl$$super$toString() {
        return super.toString();
    }

    @Override
    public String toString() {
        return HideImpl.toString$(this);
    }

    public int id() {
        return this.id;
    }

    @Override
    public LogicSnapshot in() {
        return this.in;
    }

    @Override
    public LogicSnapshot out() {
        return this.out;
    }

    @Override
    public ConnectionSnapshot.ConnectionState state() {
        return this.state;
    }

    public ConnectionSnapshotImpl copy(int id, LogicSnapshot in, LogicSnapshot out, ConnectionSnapshot.ConnectionState state) {
        return new ConnectionSnapshotImpl(id, in, out, state);
    }

    public int copy$default$1() {
        return this.id();
    }

    public LogicSnapshot copy$default$2() {
        return this.in();
    }

    public LogicSnapshot copy$default$3() {
        return this.out();
    }

    public ConnectionSnapshot.ConnectionState copy$default$4() {
        return this.state();
    }

    @Override
    public String productPrefix() {
        return "ConnectionSnapshotImpl";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.id());
            }
            case 1: {
                return this.in();
            }
            case 2: {
                return this.out();
            }
            case 3: {
                return this.state();
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
        return x$1 instanceof ConnectionSnapshotImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "id";
            }
            case 1: {
                return "in";
            }
            case 2: {
                return "out";
            }
            case 3: {
                return "state";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.id());
        n = Statics.mix(n, Statics.anyHash(this.in()));
        n = Statics.mix(n, Statics.anyHash(this.out()));
        n = Statics.mix(n, Statics.anyHash(this.state()));
        return Statics.finalizeHash(n, 4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ConnectionSnapshotImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        ConnectionSnapshotImpl connectionSnapshotImpl = (ConnectionSnapshotImpl)x$1;
        if (this.id() != connectionSnapshotImpl.id()) return false;
        LogicSnapshot logicSnapshot = this.in();
        LogicSnapshot logicSnapshot2 = connectionSnapshotImpl.in();
        if (logicSnapshot == null) {
            if (logicSnapshot2 != null) {
                return false;
            }
        } else if (!logicSnapshot.equals(logicSnapshot2)) return false;
        LogicSnapshot logicSnapshot3 = this.out();
        LogicSnapshot logicSnapshot4 = connectionSnapshotImpl.out();
        if (logicSnapshot3 == null) {
            if (logicSnapshot4 != null) {
                return false;
            }
        } else if (!logicSnapshot3.equals(logicSnapshot4)) return false;
        ConnectionSnapshot.ConnectionState connectionState = this.state();
        ConnectionSnapshot.ConnectionState connectionState2 = connectionSnapshotImpl.state();
        if (connectionState == null) {
            if (connectionState2 == null) return true;
            return false;
        } else {
            if (!connectionState.equals(connectionState2)) return false;
            return true;
        }
    }

    public ConnectionSnapshotImpl(int id, LogicSnapshot in, LogicSnapshot out, ConnectionSnapshot.ConnectionState state) {
        this.id = id;
        this.in = in;
        this.out = out;
        this.state = state;
        HideImpl.$init$(this);
        Product.$init$(this);
    }
}

