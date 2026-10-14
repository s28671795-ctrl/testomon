/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.InternalApi;
import akka.stream.snapshot.ConnectionSnapshot;
import akka.stream.snapshot.HideImpl;
import akka.stream.snapshot.LogicSnapshot;
import akka.stream.snapshot.RunningInterpreter;
import akka.stream.snapshot.RunningInterpreterImpl$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005-g!B\u0011#\u0005\u001aB\u0003\u0002\u0003$\u0001\u0005+\u0007I\u0011A$\t\u0011M\u0003!\u0011#Q\u0001\n!C\u0001\u0002\u0016\u0001\u0003\u0016\u0004%\t!\u0016\u0005\t5\u0002\u0011\t\u0012)A\u0005-\"A1\f\u0001BK\u0002\u0013\u0005A\f\u0003\u0005f\u0001\tE\t\u0015!\u0003^\u0011!1\u0007A!f\u0001\n\u00039\u0007\u0002C6\u0001\u0005#\u0005\u000b\u0011\u00025\t\u00111\u0004!Q3A\u0005\u0002\u001dC\u0001\"\u001c\u0001\u0003\u0012\u0003\u0006I\u0001\u0013\u0005\u0006]\u0002!\ta\u001c\u0005\bm\u0002\t\t\u0011\"\u0001x\u0011\u001di\b!%A\u0005\u0002yD\u0011\"a\u0005\u0001#\u0003%\t!!\u0006\t\u0013\u0005e\u0001!%A\u0005\u0002\u0005m\u0001\"CA\u0010\u0001E\u0005I\u0011AA\u0011\u0011!\t)\u0003AI\u0001\n\u0003q\b\"CA\u0014\u0001\u0005\u0005I\u0011IA\u0015\u0011!\tI\u0004AA\u0001\n\u00039\u0007\"CA\u001e\u0001\u0005\u0005I\u0011AA\u001f\u0011%\tI\u0005AA\u0001\n\u0003\nY\u0005C\u0005\u0002V\u0001\t\t\u0011\"\u0001\u0002X!I\u0011\u0011\r\u0001\u0002\u0002\u0013\u0005\u00131\r\u0005\n\u0003O\u0002\u0011\u0011!C!\u0003SB\u0011\"a\u001b\u0001\u0003\u0003%\t%!\u001c\b\u0015\u0005u$%!A\t\u0002\u0019\nyHB\u0005\"E\u0005\u0005\t\u0012\u0001\u0014\u0002\u0002\"1an\u0007C\u0001\u00033C\u0011\"a'\u001c\u0003\u0003%)%!(\t\u0013\u0005}5$!A\u0005\u0002\u0006\u0005\u0006\"CAW7\u0005\u0005I\u0011QAX\u0011%\t\tmGA\u0001\n\u0013\t\u0019M\u0001\fSk:t\u0017N\\4J]R,'\u000f\u001d:fi\u0016\u0014\u0018*\u001c9m\u0015\t\u0019C%\u0001\u0005t]\u0006\u00048\u000f[8u\u0015\t)c%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002O\u0005!\u0011m[6b'\u0019\u0001\u0011fL\u001a7sA\u0011!&L\u0007\u0002W)\tA&A\u0003tG\u0006d\u0017-\u0003\u0002/W\t1\u0011I\\=SK\u001a\u0004\"\u0001M\u0019\u000e\u0003\tJ!A\r\u0012\u0003%I+hN\\5oO&sG/\u001a:qe\u0016$XM\u001d\t\u0003aQJ!!\u000e\u0012\u0003\u0011!KG-Z%na2\u0004\"AK\u001c\n\u0005aZ#a\u0002)s_\u0012,8\r\u001e\t\u0003u\rs!aO!\u000f\u0005q\u0002U\"A\u001f\u000b\u0005yz\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u00031J!AQ\u0016\u0002\u000fA\f7m[1hK&\u0011A)\u0012\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u0005.\na\u0001\\8hS\u000e\u001cX#\u0001%\u0011\u0007%s\u0005+D\u0001K\u0015\tYE*A\u0005j[6,H/\u00192mK*\u0011QjK\u0001\u000bG>dG.Z2uS>t\u0017BA(K\u0005\r\u0019V-\u001d\t\u0003aEK!A\u0015\u0012\u0003\u001b1{w-[2T]\u0006\u00048\u000f[8u\u0003\u001dawnZ5dg\u0002\n1bY8o]\u0016\u001cG/[8ogV\ta\u000bE\u0002J\u001d^\u0003\"\u0001\r-\n\u0005e\u0013#AE\"p]:,7\r^5p]Ns\u0017\r]:i_R\fAbY8o]\u0016\u001cG/[8og\u0002\n1\"];fk\u0016\u001cF/\u0019;vgV\tQ\f\u0005\u0002_E:\u0011q\f\u0019\t\u0003y-J!!Y\u0016\u0002\rA\u0013X\rZ3g\u0013\t\u0019GM\u0001\u0004TiJLgn\u001a\u0006\u0003C.\nA\"];fk\u0016\u001cF/\u0019;vg\u0002\n!C];o]&tw\rT8hS\u000e\u001c8i\\;oiV\t\u0001\u000e\u0005\u0002+S&\u0011!n\u000b\u0002\u0004\u0013:$\u0018a\u0005:v]:Lgn\u001a'pO&\u001c7oQ8v]R\u0004\u0013!D:u_B\u0004X\r\u001a'pO&\u001c7/\u0001\bti>\u0004\b/\u001a3M_\u001eL7m\u001d\u0011\u0002\rqJg.\u001b;?)\u0019\u0001\u0018O]:ukB\u0011\u0001\u0007\u0001\u0005\u0006\r.\u0001\r\u0001\u0013\u0005\u0006).\u0001\rA\u0016\u0005\u00067.\u0001\r!\u0018\u0005\u0006M.\u0001\r\u0001\u001b\u0005\u0006Y.\u0001\r\u0001S\u0001\u0005G>\u0004\u0018\u0010\u0006\u0004qqfT8\u0010 \u0005\b\r2\u0001\n\u00111\u0001I\u0011\u001d!F\u0002%AA\u0002YCqa\u0017\u0007\u0011\u0002\u0003\u0007Q\fC\u0004g\u0019A\u0005\t\u0019\u00015\t\u000f1d\u0001\u0013!a\u0001\u0011\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A@+\u0007!\u000b\ta\u000b\u0002\u0002\u0004A!\u0011QAA\b\u001b\t\t9A\u0003\u0003\u0002\n\u0005-\u0011!C;oG\",7m[3e\u0015\r\tiaK\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\t\u0003\u000f\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!a\u0006+\u0007Y\u000b\t!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005u!fA/\u0002\u0002\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAA\u0012U\rA\u0017\u0011A\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u0006\t\u0005\u0003[\t9$\u0004\u0002\u00020)!\u0011\u0011GA\u001a\u0003\u0011a\u0017M\\4\u000b\u0005\u0005U\u0012\u0001\u00026bm\u0006L1aYA\u0018\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a\u0010\u0002FA\u0019!&!\u0011\n\u0007\u0005\r3FA\u0002B]fD\u0001\"a\u0012\u0015\u0003\u0003\u0005\r\u0001[\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u00055\u0003CBA(\u0003#\ny$D\u0001M\u0013\r\t\u0019\u0006\u0014\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002Z\u0005}\u0003c\u0001\u0016\u0002\\%\u0019\u0011QL\u0016\u0003\u000f\t{w\u000e\\3b]\"I\u0011q\t\f\u0002\u0002\u0003\u0007\u0011qH\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002,\u0005\u0015\u0004\u0002CA$/\u0005\u0005\t\u0019\u00015\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001[\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005e\u0013q\u000e\u0005\n\u0003\u000fJ\u0012\u0011!a\u0001\u0003\u007fA3\u0001AA:!\u0011\t)(!\u001f\u000e\u0005\u0005]$bAA\u0007M%!\u00111PA<\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002-I+hN\\5oO&sG/\u001a:qe\u0016$XM]%na2\u0004\"\u0001M\u000e\u0014\u000bm\t\u0019)a$\u0011\u0015\u0005\u0015\u00151\u0012%W;\"D\u0005/\u0004\u0002\u0002\b*\u0019\u0011\u0011R\u0016\u0002\u000fI,h\u000e^5nK&!\u0011QRAD\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\u000e\t\u0005\u0003#\u000b9*\u0004\u0002\u0002\u0014*!\u0011QSA\u001a\u0003\tIw.C\u0002E\u0003'#\"!a \u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u000b\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0017A\f\u0019+!*\u0002(\u0006%\u00161\u0016\u0005\u0006\rz\u0001\r\u0001\u0013\u0005\u0006)z\u0001\rA\u0016\u0005\u00067z\u0001\r!\u0018\u0005\u0006Mz\u0001\r\u0001\u001b\u0005\u0006Yz\u0001\r\u0001S\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t,!0\u0011\u000b)\n\u0019,a.\n\u0007\u0005U6F\u0001\u0004PaRLwN\u001c\t\tU\u0005e\u0006JV/i\u0011&\u0019\u00111X\u0016\u0003\rQ+\b\u000f\\36\u0011!\tylHA\u0001\u0002\u0004\u0001\u0018a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u0019\t\u0005\u0003[\t9-\u0003\u0003\u0002J\u0006=\"AB(cU\u0016\u001cG\u000f")
@InternalApi
public final class RunningInterpreterImpl
implements RunningInterpreter,
HideImpl,
Product,
Serializable {
    private final Seq<LogicSnapshot> logics;
    private final Seq<ConnectionSnapshot> connections;
    private final String queueStatus;
    private final int runningLogicsCount;
    private final Seq<LogicSnapshot> stoppedLogics;

    public static Option<Tuple5<Seq<LogicSnapshot>, Seq<ConnectionSnapshot>, String, Object, Seq<LogicSnapshot>>> unapply(RunningInterpreterImpl x$0) {
        return RunningInterpreterImpl$.MODULE$.unapply(x$0);
    }

    public static RunningInterpreterImpl apply(Seq<LogicSnapshot> logics, Seq<ConnectionSnapshot> connections, String queueStatus, int runningLogicsCount, Seq<LogicSnapshot> stoppedLogics) {
        return RunningInterpreterImpl$.MODULE$.apply(logics, connections, queueStatus, runningLogicsCount, stoppedLogics);
    }

    public static Function1<Tuple5<Seq<LogicSnapshot>, Seq<ConnectionSnapshot>, String, Object, Seq<LogicSnapshot>>, RunningInterpreterImpl> tupled() {
        return RunningInterpreterImpl$.MODULE$.tupled();
    }

    public static Function1<Seq<LogicSnapshot>, Function1<Seq<ConnectionSnapshot>, Function1<String, Function1<Object, Function1<Seq<LogicSnapshot>, RunningInterpreterImpl>>>>> curried() {
        return RunningInterpreterImpl$.MODULE$.curried();
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

    @Override
    public Seq<LogicSnapshot> logics() {
        return this.logics;
    }

    @Override
    public Seq<ConnectionSnapshot> connections() {
        return this.connections;
    }

    public String queueStatus() {
        return this.queueStatus;
    }

    @Override
    public int runningLogicsCount() {
        return this.runningLogicsCount;
    }

    @Override
    public Seq<LogicSnapshot> stoppedLogics() {
        return this.stoppedLogics;
    }

    public RunningInterpreterImpl copy(Seq<LogicSnapshot> logics, Seq<ConnectionSnapshot> connections, String queueStatus, int runningLogicsCount, Seq<LogicSnapshot> stoppedLogics) {
        return new RunningInterpreterImpl(logics, connections, queueStatus, runningLogicsCount, stoppedLogics);
    }

    public Seq<LogicSnapshot> copy$default$1() {
        return this.logics();
    }

    public Seq<ConnectionSnapshot> copy$default$2() {
        return this.connections();
    }

    public String copy$default$3() {
        return this.queueStatus();
    }

    public int copy$default$4() {
        return this.runningLogicsCount();
    }

    public Seq<LogicSnapshot> copy$default$5() {
        return this.stoppedLogics();
    }

    @Override
    public String productPrefix() {
        return "RunningInterpreterImpl";
    }

    @Override
    public int productArity() {
        return 5;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.logics();
            }
            case 1: {
                return this.connections();
            }
            case 2: {
                return this.queueStatus();
            }
            case 3: {
                return BoxesRunTime.boxToInteger(this.runningLogicsCount());
            }
            case 4: {
                return this.stoppedLogics();
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
        return x$1 instanceof RunningInterpreterImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "logics";
            }
            case 1: {
                return "connections";
            }
            case 2: {
                return "queueStatus";
            }
            case 3: {
                return "runningLogicsCount";
            }
            case 4: {
                return "stoppedLogics";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.logics()));
        n = Statics.mix(n, Statics.anyHash(this.connections()));
        n = Statics.mix(n, Statics.anyHash(this.queueStatus()));
        n = Statics.mix(n, this.runningLogicsCount());
        n = Statics.mix(n, Statics.anyHash(this.stoppedLogics()));
        return Statics.finalizeHash(n, 5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof RunningInterpreterImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        RunningInterpreterImpl runningInterpreterImpl = (RunningInterpreterImpl)x$1;
        if (this.runningLogicsCount() != runningInterpreterImpl.runningLogicsCount()) return false;
        Seq<LogicSnapshot> seq = this.logics();
        Seq<LogicSnapshot> seq2 = runningInterpreterImpl.logics();
        if (seq == null) {
            if (seq2 != null) {
                return false;
            }
        } else if (!seq.equals(seq2)) return false;
        Seq<ConnectionSnapshot> seq3 = this.connections();
        Seq<ConnectionSnapshot> seq4 = runningInterpreterImpl.connections();
        if (seq3 == null) {
            if (seq4 != null) {
                return false;
            }
        } else if (!seq3.equals(seq4)) return false;
        String string = this.queueStatus();
        String string2 = runningInterpreterImpl.queueStatus();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Seq<LogicSnapshot> seq5 = this.stoppedLogics();
        Seq<LogicSnapshot> seq6 = runningInterpreterImpl.stoppedLogics();
        if (seq5 == null) {
            if (seq6 == null) return true;
            return false;
        } else {
            if (!seq5.equals(seq6)) return false;
            return true;
        }
    }

    public RunningInterpreterImpl(Seq<LogicSnapshot> logics, Seq<ConnectionSnapshot> connections, String queueStatus, int runningLogicsCount, Seq<LogicSnapshot> stoppedLogics) {
        this.logics = logics;
        this.connections = connections;
        this.queueStatus = queueStatus;
        this.runningLogicsCount = runningLogicsCount;
        this.stoppedLogics = stoppedLogics;
        HideImpl.$init$(this);
        Product.$init$(this);
    }
}

