/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.Arrays;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.ArrayOps$;
import scala.collection.BitSet;
import scala.collection.BitSetOps;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedOps;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSetOps;
import scala.collection.StrictOptimizedSortedSetOps;
import scala.collection.View;
import scala.collection.immutable.Range;
import scala.collection.mutable.AbstractSet;
import scala.collection.mutable.BitSet$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.SetOps;
import scala.collection.mutable.Shrinkable;
import scala.collection.mutable.SortedSet;
import scala.math.Ordering;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\t]h\u0001B\u001c9\u0001}B\u0011B\u001a\u0001\u0003\u0002\u0004%)BO4\t\u00139\u0004!\u00111A\u0005\u0016iz\u0007\u0002C;\u0001\u0005\u0003\u0005\u000bU\u00025\t\u000bY\u0004A\u0011A<\t\u000bY\u0004A\u0011A=\t\u000bY\u0004A\u0011\u0001?\t\u000bu\u0004A\u0011\u000b@\t\u000f\u0005%\u0001\u0001\"\u0015\u0002\f!9\u00111\u0003\u0001\u0005B\u0005U\u0001bBA\f\u0001\u0011\u0005\u0011\u0011\u0004\u0005\b\u0003?\u0003A\u0011IAQ\u0011!\t)\u000b\u0001C\u000bu\u0005\u001d\u0006\u0002CAU\u0001\u0011U!(a+\t\u0011\u0005%\u0003\u0001\"\u0005;\u0003cCq!!.\u0001\t\u0003\t9\fC\u0004\u0002@\u0002!\t!!1\t\u000f\u0005\u0015\u0007\u0001\"\u0001\u0002H\"9\u0011\u0011\u001a\u0001\u0005\u0016\u0005-\u0007bBAj\u0001\u0011U\u0011Q\u001b\u0005\b\u00033\u0004A\u0011AAn\u0011\u001d\t\t\u000f\u0001C\u0001\u0003GDq!!;\u0001\t\u0003\tY\u000fC\u0004\u0002p\u0002!\t!!=\t\u000f\u0005U\b\u0001\"\u0001\u0002x\"1\u00111 \u0001\u0005BqDq!!@\u0001\t\u0003\ty\u0010C\u0004\u0003\f\u0001!\tE!\u0004\t\u000f\t-\u0001\u0001\"\u0011\u0003\u001a!9!1\u000b\u0001\u0005B\tU\u0003b\u0002B*\u0001\u0011\u0005#1\f\u0005\b\u0005g\u0002A\u0011\tB;\u0011\u001d\u0011\u0019\b\u0001C!\u0005\u0003CqAa&\u0001\t\u0003\u0012I\nC\u0004\u0003<\u0002!\tE!0\t\u000f\t\r\u0007\u0001\"\u0011\u0003F\"9!q\u001a\u0001\u0005B\tE\u0007\u0002CAG\u0001\u0001&\tB!6\t\u000f\t]\u0007\u0001\"\u0011\u0003Z\"9!Q\u001c\u0001\u0005B\t}\u0007b\u0002Bv\u0001\u0011\u0005#Q\u001e\u0005\u0007\u0005g\u0004A\u0011I4\b\u000f\u0005\u001d\u0004\b#\u0001\u0002\u001c\u00191q\u0007\u000fE\u0001\u0003;AaA^\u0016\u0005\u0002\u0005e\u0002BB?,\t\u0003\tY\u0004C\u0004\u0002\u0014-\"\t!!\u0006\t\u000f\u0005\u00053\u0006\"\u0001\u0002\f!9\u00111I\u0016\u0005\u0002\u0005\u0015\u0003bBA%W\u0011\u0005\u00111\n\u0004\u0007\u0003\u001fZc!!\u0015\t\u0017\u0005\u0005!G!A!\u0002\u0013y\u0015Q\u000e\u0005\u0007mJ\"\t!a\u001c\t\u0011\u0005]$\u0007)C\t\u0003sB\u0011\"!$,\u0003\u0003%I!a$\u0003\r\tKGoU3u\u0015\tI$(A\u0004nkR\f'\r\\3\u000b\u0005mb\u0014AC2pY2,7\r^5p]*\tQ(A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0013\u0001\u0001\u0005j\u0013)X5r{\u0006cA!C\t6\t\u0001(\u0003\u0002Dq\tY\u0011IY:ue\u0006\u001cGoU3u!\t)e)D\u0001=\u0013\t9EHA\u0002J]R\u00042!Q%E\u0013\tQ\u0005HA\u0005T_J$X\rZ*fiB)\u0011\t\u0014#O\u001f&\u0011Q\n\u000f\u0002\r'>\u0014H/\u001a3TKR|\u0005o\u001d\t\u0003\u0003&\u0003\"!\u0011\u0001\u0011\u000bE\u0013F\tV(\u000e\u0003iJ!a\u0015\u001e\u00035M#(/[2u\u001fB$\u0018.\\5{K\u0012LE/\u001a:bE2,w\n]:\u0011\u0005\u0005+\u0016B\u0001,9\u0005\r\u0019V\r\u001e\t\u0006#b#ejT\u0005\u00033j\u00121d\u0015;sS\u000e$x\n\u001d;j[&TX\rZ*peR,GmU3u\u001fB\u001c\bCA)\\\u0013\t9$\bE\u0002R;>K!A\u0018\u001e\u0003\u0013\tKGoU3u\u001fB\u001c\bC\u00011d\u001d\t)\u0015-\u0003\u0002cy\u00059\u0001/Y2lC\u001e,\u0017B\u00013f\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u0011G(A\u0003fY\u0016l7/F\u0001i!\r)\u0015n[\u0005\u0003Ur\u0012Q!\u0011:sCf\u0004\"!\u00127\n\u00055d$\u0001\u0002'p]\u001e\f\u0011\"\u001a7f[N|F%Z9\u0015\u0005A\u001c\bCA#r\u0013\t\u0011HH\u0001\u0003V]&$\bb\u0002;\u0003\u0003\u0003\u0005\r\u0001[\u0001\u0004q\u0012\n\u0014AB3mK6\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0003\u001fbDQA\u001a\u0003A\u0002!$\"a\u0014>\t\u000bm,\u0001\u0019\u0001#\u0002\u0011%t\u0017\u000e^*ju\u0016$\u0012aT\u0001\rMJ|Wn\u00159fG&4\u0017n\u0019\u000b\u0003\u001f~Dq!!\u0001\b\u0001\u0004\t\u0019!\u0001\u0003d_2d\u0007\u0003B)\u0002\u0006\u0011K1!a\u0002;\u00051IE/\u001a:bE2,wJ\\2f\u0003IqWm^*qK\u000eLg-[2Ck&dG-\u001a:\u0016\u0005\u00055\u0001#B!\u0002\u0010\u0011{\u0015bAA\tq\t9!)^5mI\u0016\u0014\u0018!B3naRLX#A(\u0002\u001b\tLGoU3u\r\u0006\u001cGo\u001c:z+\t\tY\u0002\u0005\u0002BWM91&a\b\u0002&\u0005-\u0002cA#\u0002\"%\u0019\u00111\u0005\u001f\u0003\r\u0005s\u0017PU3g!\u0015\t\u0016q\u0005#P\u0013\r\tIC\u000f\u0002\u0018'B,7-\u001b4jG&#XM]1cY\u00164\u0015m\u0019;pef\u0004B!!\f\u000285\u0011\u0011q\u0006\u0006\u0005\u0003c\t\u0019$\u0001\u0002j_*\u0011\u0011QG\u0001\u0005U\u00064\u0018-C\u0002e\u0003_!\"!a\u0007\u0015\u0007=\u000bi\u0004C\u0004\u0002@5\u0002\r!a\u0001\u0002\u0005%$\u0018A\u00038fo\n+\u0018\u000e\u001c3fe\u0006YaM]8n\u0005&$X*Y:l)\ry\u0015q\t\u0005\u0006MB\u0002\r\u0001[\u0001\u0012MJ|WNQ5u\u001b\u0006\u001c8NT8D_BLHcA(\u0002N!)a-\ra\u0001Q\n\u00112+\u001a:jC2L'0\u0019;j_:\u0004&o\u001c=z'\r\u0011\u00141\u000b\t\u0005\u0003+\nIG\u0004\u0003\u0002X\u0005\u0015d\u0002BA-\u0003GrA!a\u0017\u0002b5\u0011\u0011Q\f\u0006\u0004\u0003?r\u0014A\u0002\u001fs_>$h(C\u0001>\u0013\tYD(C\u0002\u0002hi\naAQ5u'\u0016$\u0018\u0002BA(\u0003WR1!a\u001a;\u0013\u0011\t\t!!\u001b\u0015\t\u0005E\u0014Q\u000f\t\u0004\u0003g\u0012T\"A\u0016\t\r\u0005\u0005A\u00071\u0001P\u0003-\u0011X-\u00193SKN|GN^3\u0015\u0005\u0005m\u0004cA#\u0002~%\u0019\u0011q\u0010\u001f\u0003\u0007\u0005s\u0017\u0010K\u00043\u0003\u0007\u000bI)a#\u0011\u0007\u0015\u000b))C\u0002\u0002\br\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\r\tAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!%\u0011\t\u0005M\u0015\u0011T\u0007\u0003\u0003+SA!a&\u00024\u0005!A.\u00198h\u0013\u0011\tY*!&\u0003\r=\u0013'.Z2uQ\u001dY\u00131QAE\u0003\u0017\u000b\u0001\"\u001e8t_J$X\rZ\u000b\u0003\u0003G\u00032!Q+E\u0003\u0019qwo\u001c:egV\tA)\u0001\u0003x_J$GcA6\u0002.\"1\u0011qV\u0007A\u0002\u0011\u000b1!\u001b3y)\ry\u00151\u0017\u0005\u0006M:\u0001\r\u0001[\u0001\u0007C\u0012$wJ\\3\u0015\t\u0005e\u00161X\u0007\u0002\u0001!1\u0011QX\bA\u0002\u0011\u000bA!\u001a7f[\u0006Y1/\u001e2ue\u0006\u001cGo\u00148f)\u0011\tI,a1\t\r\u0005u\u0006\u00031\u0001E\u0003\u0015\u0019G.Z1s)\u0005\u0001\u0018AC;qI\u0006$XmV8sIR)\u0001/!4\u0002P\"1\u0011q\u0016\nA\u0002\u0011Ca!!5\u0013\u0001\u0004Y\u0017!A<\u0002\u001d\u0015t7/\u001e:f\u0007\u0006\u0004\u0018mY5usR\u0019\u0001/a6\t\r\u0005=6\u00031\u0001E\u00035)hnY8ogR\u0014\u0018-\u001b8fIV\u0011\u0011Q\u001c\t\u0005#\u0006}G)\u0003\u0002Wu\u00059AEY1sI\u0015\fH\u0003BA]\u0003KDa!a:\u0016\u0001\u0004Q\u0016!B8uQ\u0016\u0014\u0018a\u0002\u0013b[B$S-\u001d\u000b\u0005\u0003s\u000bi\u000f\u0003\u0004\u0002hZ\u0001\rAW\u0001\u0007IU\u0004H%Z9\u0015\t\u0005e\u00161\u001f\u0005\u0007\u0003O<\u0002\u0019\u0001.\u0002\u001b\u0011\nW\u000e\u001d\u0013uS2$W\rJ3r)\u0011\tI,!?\t\r\u0005\u001d\b\u00041\u0001[\u0003\u0015\u0019Gn\u001c8f\u0003-!x.S7nkR\f'\r\\3\u0016\u0005\t\u0005\u0001\u0003\u0002B\u0002\u0005\u0013i!A!\u0002\u000b\u0007\t\u001d!(A\u0005j[6,H/\u00192mK&\u0019qG!\u0002\u0002\u00075\f\u0007\u000fF\u0002P\u0005\u001fAqA!\u0005\u001c\u0001\u0004\u0011\u0019\"A\u0001g!\u0015)%Q\u0003#E\u0013\r\u00119\u0002\u0010\u0002\n\rVt7\r^5p]F*BAa\u0007\u0003&Q!!Q\u0004B()\u0011\u0011yB!\r\u0011\t\u0005K%\u0011\u0005\t\u0005\u0005G\u0011)\u0003\u0004\u0001\u0005\u000f\t\u001dBD1\u0001\u0003*\t\t!)\u0005\u0003\u0003,\u0005m\u0004cA#\u0003.%\u0019!q\u0006\u001f\u0003\u000f9{G\u000f[5oO\"9!1\u0007\u000fA\u0004\tU\u0012AA3w!\u0015\u0001'q\u0007B\u0011\u0013\r\u0011I$\u001a\u0002\t\u001fJ$WM]5oO\"B!\u0011\u0007B\u001f\u0005\u0013\u0012Y\u0005\u0005\u0003\u0003@\t\u0015SB\u0001B!\u0015\r\u0011\u0019\u0005P\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002\u0002B$\u0005\u0003\u0012\u0001#[7qY&\u001c\u0017\u000e\u001e(pi\u001a{WO\u001c3\u0002\u00075\u001cx-\t\u0002\u0003N\u0005qhj\u001c\u0011j[Bd\u0017nY5uA=\u0013H-\u001a:j]\u001e\\Fe\u001f\"~;\u00022w.\u001e8eAQ|\u0007EY;jY\u0012\u0004\u0013\rI*peR,GmU3u7\u0012Z()`//Ae{W\u000fI7bs\u0002:\u0018M\u001c;!i>\u0004S\u000f]2bgR\u0004Co\u001c\u0011bAM+GoW%oiv\u0003c-\u001b:ti\u0002\u0012\u0017\u0010I2bY2Lgn\u001a\u0011ak:\u001cxN\u001d;fI\u0002t\u0003b\u0002B\t9\u0001\u0007!\u0011\u000b\t\u0007\u000b\nUAI!\t\u0002\u000f\u0019d\u0017\r^'baR\u0019qJa\u0016\t\u000f\tEQ\u00041\u0001\u0003ZA1QI!\u0006E\u0003\u0007)BA!\u0018\u0003fQ!!q\fB7)\u0011\u0011\tGa\u001a\u0011\t\u0005K%1\r\t\u0005\u0005G\u0011)\u0007B\u0004\u0003(y\u0011\rA!\u000b\t\u000f\tMb\u0004q\u0001\u0003jA)\u0001Ma\u000e\u0003d!B!q\rB\u001f\u0005\u0013\u0012Y\u0005C\u0004\u0003\u0012y\u0001\rAa\u001c\u0011\r\u0015\u0013)\u0002\u0012B9!\u0015\t\u0016Q\u0001B2\u0003\u001d\u0019w\u000e\u001c7fGR$2a\u0014B<\u0011\u001d\u0011Ih\ba\u0001\u0005w\n!\u0001\u001d4\u0011\u000b\u0015\u0013i\b\u0012#\n\u0007\t}DHA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o+\u0011\u0011\u0019Ia#\u0015\t\t\u0015%1\u0013\u000b\u0005\u0005\u000f\u0013i\t\u0005\u0003B\u0013\n%\u0005\u0003\u0002B\u0012\u0005\u0017#qAa\n!\u0005\u0004\u0011I\u0003C\u0004\u00034\u0001\u0002\u001dAa$\u0011\u000b\u0001\u00149D!#)\u0011\t5%Q\bB%\u0005\u0017BqA!\u001f!\u0001\u0004\u0011)\n\u0005\u0004F\u0005{\"%\u0011R\u0001\u0004u&\u0004X\u0003\u0002BN\u0005S#BA!(\u00036R!!q\u0014BV!\u0011\t\u0015J!)\u0011\r\u0015\u0013\u0019\u000b\u0012BT\u0013\r\u0011)\u000b\u0010\u0002\u0007)V\u0004H.\u001a\u001a\u0011\t\t\r\"\u0011\u0016\u0003\b\u0005O\t#\u0019\u0001B\u0015\u0011\u001d\u0011\u0019$\ta\u0002\u0005[\u0003R\u0001\u0019B\u001c\u0005CC\u0003Ba+\u0003>\t%#\u0011W\u0011\u0003\u0005g\u000b\u00111\u0002(pA%l\u0007\u000f\\5dSR\u0004sJ\u001d3fe&twm\u0017\u0013|\u0005vl\u0006EZ8v]\u0012\u0004Co\u001c\u0011ck&dG\rI1!'>\u0014H/\u001a3TKR\\\u0006&\u00138uY\u0001\"3PQ?*;:\u0002\u0013l\\;![\u0006L\be^1oi\u0002\"x\u000eI;qG\u0006\u001cH\u000f\t;pA\u0005\u00043+\u001a;\\\u0013:$X\f\t4jeN$\bEY=!G\u0006dG.\u001b8hA\u0001,hn]8si\u0016$\u0007M\f\u0005\b\u0005o\u000b\u0003\u0019\u0001B]\u0003\u0011!\b.\u0019;\u0011\u000bE\u000b)Aa*\u0002\r\u0005$G-\u00117m)\u0011\tILa0\t\u000f\t\u0005'\u00051\u0001\u0002\u0004\u0005\u0011\u0001p]\u0001\tgV\u00147/\u001a;PMR!!q\u0019Bg!\r)%\u0011Z\u0005\u0004\u0005\u0017d$a\u0002\"p_2,\u0017M\u001c\u0005\b\u0005o\u001b\u0003\u0019AAo\u0003-\u0019XO\u0019;sC\u000e$\u0018\t\u001c7\u0015\t\u0005e&1\u001b\u0005\b\u0005\u0003$\u0003\u0019AA\u0002)\t\ty\"\u0001\u0003eS\u001a4GcA(\u0003\\\"9!q\u0017\u0014A\u0002\u0005u\u0017A\u00034jYR,'/S7qYR)qJ!9\u0003h\"9!1]\u0014A\u0002\t\u0015\u0018\u0001\u00029sK\u0012\u0004b!\u0012B\u000b\t\n\u001d\u0007b\u0002BuO\u0001\u0007!qY\u0001\nSN4E.\u001b9qK\u0012\fQBZ5mi\u0016\u0014\u0018J\u001c)mC\u000e,G\u0003BA]\u0005_DqA!=)\u0001\u0004\u0011)/A\u0001q\u0003%!xNQ5u\u001b\u0006\u001c8\u000eK\u0004+\u0003\u0007\u000bI)a#")
public class BitSet
extends AbstractSet<Object>
implements SortedSet<Object>,
StrictOptimizedSortedSetOps<Object, SortedSet, BitSet>,
scala.collection.BitSet,
Serializable {
    private long[] elems;

    public static BitSet fromBitMask(long[] elems) {
        return BitSet$.MODULE$.fromBitMask(elems);
    }

    public static Builder<Object, BitSet> newBuilder() {
        return BitSet$.MODULE$.newBuilder();
    }

    public static Factory<Object, BitSet> specificIterableFactory() {
        return BitSet$.MODULE$;
    }

    public static Object fill(int n, Function0 elem) {
        BitSet$ fill_this = BitSet$.MODULE$;
        View.Fill fromSpecific_it = new View.Fill(n, elem);
        return fill_this.fromSpecific((IterableOnce)fromSpecific_it);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.BitSet.stringPrefix$(this);
    }

    @Override
    public /* synthetic */ int scala$collection$BitSetOps$$super$max(Ordering ord) {
        return BoxesRunTime.unboxToInt(SortedSetOps.max$(this, ord));
    }

    @Override
    public /* synthetic */ int scala$collection$BitSetOps$$super$min(Ordering ord) {
        return BoxesRunTime.unboxToInt(SortedSetOps.min$(this, ord));
    }

    @Override
    public /* synthetic */ scala.collection.BitSet scala$collection$BitSetOps$$super$concat(IterableOnce that) {
        return (scala.collection.BitSet)StrictOptimizedSetOps.concat$(this, that);
    }

    @Override
    public /* synthetic */ scala.collection.BitSet scala$collection$BitSetOps$$super$intersect(Set that) {
        return (scala.collection.BitSet)scala.collection.SetOps.intersect$(this, that);
    }

    @Override
    public /* synthetic */ scala.collection.BitSet scala$collection$BitSetOps$$super$diff(Set that) {
        return (scala.collection.BitSet)((Object)SetOps.diff$(this, that));
    }

    @Override
    public final Ordering<Object> ordering() {
        return BitSetOps.ordering$(this);
    }

    @Override
    public boolean contains(int elem) {
        return BitSetOps.contains$(this, elem);
    }

    @Override
    public Iterator<Object> iterator() {
        return BitSetOps.iterator$(this);
    }

    @Override
    public Iterator<Object> iteratorFrom(int start2) {
        return BitSetOps.iteratorFrom$(this, start2);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
        return (S)BitSetOps.stepper$(this, shape);
    }

    @Override
    public int size() {
        return BitSetOps.size$(this);
    }

    @Override
    public boolean isEmpty() {
        return BitSetOps.isEmpty$(this);
    }

    @Override
    public <B> int max(Ordering<B> ord) {
        return BitSetOps.max$(this, ord);
    }

    @Override
    public <B> int min(Ordering<B> ord) {
        return BitSetOps.min$(this, ord);
    }

    @Override
    public <U> void foreach(Function1<Object, U> f) {
        BitSetOps.foreach$(this, f);
    }

    @Override
    public scala.collection.BitSet rangeImpl(Option from, Option until) {
        return BitSetOps.rangeImpl$(this, from, until);
    }

    @Override
    public scala.collection.BitSet concat(IterableOnce other) {
        return BitSetOps.concat$(this, other);
    }

    @Override
    public scala.collection.BitSet intersect(Set other) {
        return BitSetOps.intersect$(this, other);
    }

    @Override
    public scala.collection.BitSet xor(scala.collection.BitSet other) {
        return BitSetOps.xor$(this, other);
    }

    @Override
    public final scala.collection.BitSet $up(scala.collection.BitSet other) {
        return BitSetOps.$up$(this, other);
    }

    @Override
    public Tuple2<BitSet, BitSet> partition(Function1<Object, Object> p) {
        return BitSetOps.partition$(this, p);
    }

    @Override
    public Tuple2<BitSet, BitSet> span(Function1<Object, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>> unzip(Function1<Object, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>, scala.collection.mutable.Set<A3>> unzip3(Function1<Object, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Object, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Object, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Object, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Object, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Object, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Set<A1>, scala.collection.mutable.Set<A2>> partitionMap(Function1<Object, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public SortedIterableFactory<SortedSet> sortedIterableFactory() {
        return SortedSet.sortedIterableFactory$(this);
    }

    @Override
    public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
        return Set.equals$(this, that);
    }

    @Override
    public boolean equals(Object that) {
        return scala.collection.SortedSet.equals$(this, that);
    }

    @Override
    public SortedSetOps.WithFilter<Object, ?, ?> withFilter(Function1<Object, Object> p) {
        return SortedSetFactoryDefaults.withFilter$(this, p);
    }

    @Override
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$min(Ordering ord) {
        return IterableOnceOps.min$(this, ord);
    }

    @Override
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$max(Ordering ord) {
        return IterableOnceOps.max$(this, ord);
    }

    @Override
    public Iterator keysIteratorFrom(Object start2) {
        return SortedSetOps.keysIteratorFrom$(this, start2);
    }

    @Override
    public Object firstKey() {
        return SortedSetOps.firstKey$(this);
    }

    @Override
    public Object lastKey() {
        return SortedSetOps.lastKey$(this);
    }

    @Override
    public Option minAfter(Object key) {
        return SortedSetOps.minAfter$(this, key);
    }

    @Override
    public Option maxBefore(Object key) {
        return SortedSetOps.maxBefore$(this, key);
    }

    @Override
    public SortedSetOps rangeTo(Object to) {
        return SortedSetOps.rangeTo$(this, to);
    }

    @Override
    public int compare(Object k0, Object k1) {
        return SortedOps.compare$(this, k0, k1);
    }

    @Override
    public Object range(Object from, Object until) {
        return SortedOps.range$(this, from, until);
    }

    @Override
    public final Object from(Object from) {
        return SortedOps.from$(this, from);
    }

    @Override
    public Object rangeFrom(Object from) {
        return SortedOps.rangeFrom$(this, from);
    }

    @Override
    public final Object until(Object until) {
        return SortedOps.until$(this, until);
    }

    @Override
    public Object rangeUntil(Object until) {
        return SortedOps.rangeUntil$(this, until);
    }

    @Override
    public final Object to(Object to) {
        return SortedOps.to$(this, to);
    }

    public final long[] elems() {
        return this.elems;
    }

    public final void elems_$eq(long[] x$1) {
        this.elems = x$1;
    }

    @Override
    public BitSet fromSpecific(IterableOnce<Object> coll) {
        return this.bitSetFactory().fromSpecific((IterableOnce)coll);
    }

    @Override
    public Builder<Object, BitSet> newSpecificBuilder() {
        return this.bitSetFactory().newBuilder();
    }

    @Override
    public BitSet empty() {
        if (this.bitSetFactory() == null) {
            throw null;
        }
        return new BitSet();
    }

    public BitSet$ bitSetFactory() {
        return BitSet$.MODULE$;
    }

    @Override
    public scala.collection.mutable.Set<Object> unsorted() {
        return this;
    }

    @Override
    public final int nwords() {
        return this.elems().length;
    }

    @Override
    public final long word(int idx) {
        if (idx < this.elems().length) {
            return this.elems()[idx];
        }
        return 0L;
    }

    @Override
    public BitSet fromBitMaskNoCopy(long[] elems) {
        if (elems.length == 0) {
            return this.empty();
        }
        return new BitSet(elems);
    }

    public BitSet addOne(int elem) {
        Predef$.MODULE$.require(elem >= 0);
        if (!this.contains(elem)) {
            int idx = elem >> 6;
            this.updateWord(idx, this.word(idx) | 1L << elem);
        }
        return this;
    }

    public BitSet subtractOne(int elem) {
        Predef$.MODULE$.require(elem >= 0);
        if (this.contains(elem)) {
            int idx = elem >> 6;
            this.updateWord(idx, this.word(idx) & (1L << elem ^ 0xFFFFFFFFFFFFFFFFL));
        }
        return this;
    }

    @Override
    public void clear() {
        this.elems_$eq(new long[this.elems().length]);
    }

    public final void updateWord(int idx, long w) {
        this.ensureCapacity(idx);
        this.elems()[idx] = w;
    }

    public final void ensureCapacity(int idx) {
        Predef$.MODULE$.require(idx < 0x2000000);
        if (idx >= this.elems().length) {
            int newlen = this.elems().length;
            while (idx >= newlen) {
                newlen = Math.min(newlen * 2, 0x2000000);
            }
            long[] elems1 = new long[newlen];
            Array$.MODULE$.copy(this.elems(), 0, elems1, 0, this.elems().length);
            this.elems_$eq(elems1);
            return;
        }
    }

    public Set<Object> unconstrained() {
        return this;
    }

    public BitSet $bar$eq(scala.collection.BitSet other) {
        this.ensureCapacity(other.nwords() - 1);
        int othernwords = other.nwords();
        for (int i = 0; i < othernwords; ++i) {
            this.elems()[i] = this.elems()[i] | other.word(i);
        }
        return this;
    }

    public BitSet $amp$eq(scala.collection.BitSet other) {
        int thisnwords = this.elems().length;
        for (int i = 0; i < thisnwords; ++i) {
            this.elems()[i] = this.elems()[i] & other.word(i);
        }
        return this;
    }

    public BitSet $up$eq(scala.collection.BitSet other) {
        this.ensureCapacity(other.nwords() - 1);
        int othernwords = other.nwords();
        for (int i = 0; i < othernwords; ++i) {
            this.elems()[i] = this.elems()[i] ^ other.word(i);
        }
        return this;
    }

    public BitSet $amp$tilde$eq(scala.collection.BitSet other) {
        int max = Math.min(this.elems().length, other.nwords());
        for (int i = 0; i < max; ++i) {
            this.elems()[i] = this.elems()[i] & (other.word(i) ^ 0xFFFFFFFFFFFFFFFFL);
        }
        return this;
    }

    @Override
    public BitSet clone() {
        return new BitSet(Arrays.copyOf(this.elems(), this.elems().length));
    }

    public scala.collection.immutable.BitSet toImmutable() {
        return scala.collection.immutable.BitSet$.MODULE$.fromBitMask(this.elems());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public BitSet map(Function1<Object, Object> f) {
        Builder<Object, BitSet> strictOptimizedMap_b = this.newSpecificBuilder();
        Iterator strictOptimizedMap_it = this.iterator();
        while (strictOptimizedMap_it.hasNext()) {
            void strictOptimizedMap_$plus$eq_elem;
            Object object = f.apply(strictOptimizedMap_it.next());
            if (strictOptimizedMap_b == null) {
                throw null;
            }
            strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
            object = null;
        }
        return strictOptimizedMap_b.result();
    }

    @Override
    public <B> SortedSet<B> map(Function1<Object, B> f, Ordering<B> ev) {
        return (SortedSet)StrictOptimizedSortedSetOps.map$(this, f, ev);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public BitSet flatMap(Function1<Object, IterableOnce<Object>> f) {
        Builder<Object, BitSet> strictOptimizedFlatMap_b = this.newSpecificBuilder();
        Iterator strictOptimizedFlatMap_it = this.iterator();
        while (strictOptimizedFlatMap_it.hasNext()) {
            void strictOptimizedFlatMap_$plus$plus$eq_xs;
            IterableOnce<Object> iterableOnce = f.apply(strictOptimizedFlatMap_it.next());
            if (strictOptimizedFlatMap_b == null) {
                throw null;
            }
            strictOptimizedFlatMap_b.addAll((IterableOnce<Object>)strictOptimizedFlatMap_$plus$plus$eq_xs);
            iterableOnce = null;
        }
        return strictOptimizedFlatMap_b.result();
    }

    @Override
    public <B> SortedSet<B> flatMap(Function1<Object, IterableOnce<B>> f, Ordering<B> ev) {
        return (SortedSet)StrictOptimizedSortedSetOps.flatMap$(this, f, ev);
    }

    @Override
    public BitSet collect(PartialFunction<Object, Object> pf) {
        Builder<Object, BitSet> strictOptimizedCollect_b = this.newSpecificBuilder();
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            if (strictOptimizedCollect_b == null) {
                throw null;
            }
            strictOptimizedCollect_b.addOne(strictOptimizedCollect_v);
        }
        return strictOptimizedCollect_b.result();
    }

    @Override
    public <B> SortedSet<B> collect(PartialFunction<Object, B> pf, Ordering<B> ev) {
        return (SortedSet)StrictOptimizedSortedSetOps.collect$(this, pf, ev);
    }

    @Override
    public <B> SortedSet<Tuple2<Object, B>> zip(IterableOnce<B> that, Ordering<Tuple2<Object, B>> ev) {
        return (SortedSet)StrictOptimizedSortedSetOps.zip$(this, that, ev);
    }

    public BitSet addAll(IterableOnce<Object> xs) {
        if (xs instanceof scala.collection.BitSet) {
            scala.collection.BitSet bitSet = (scala.collection.BitSet)xs;
            return this.$bar$eq(bitSet);
        }
        if (xs instanceof Range) {
            Range range = (Range)xs;
            if (range.nonEmpty()) {
                int start2 = range.min(Ordering$Int$.MODULE$);
                if (start2 >= 0) {
                    int end = range.max(Ordering$Int$.MODULE$);
                    int endIdx = end >> 6;
                    this.ensureCapacity(endIdx);
                    if (range.step() == 1 || range.step() == -1) {
                        int startIdx = start2 >> 6;
                        int wordStart = startIdx * 64;
                        long wordMask = -1L << start2 - wordStart;
                        if (endIdx > startIdx) {
                            long[] lArray = this.elems();
                            lArray[startIdx] = lArray[startIdx] | wordMask;
                            Arrays.fill(this.elems(), startIdx + 1, endIdx, -1L);
                            long[] lArray2 = this.elems();
                            lArray2[endIdx] = lArray2[endIdx] | -1L >>> 64 - (end - endIdx * 64) - 1;
                        } else {
                            long[] lArray = this.elems();
                            lArray[endIdx] = lArray[endIdx] | wordMask & -1L >>> 64 - (end - wordStart) - 1;
                        }
                    } else {
                        Growable.addAll$(this, range);
                    }
                } else {
                    Growable.addAll$(this, range);
                }
            }
            return this;
        }
        if (xs instanceof scala.collection.SortedSet) {
            scala.collection.SortedSet sortedSet = (scala.collection.SortedSet)xs;
            if (sortedSet.nonEmpty()) {
                Ordering ord = sortedSet.ordering();
                if (ord == Ordering$Int$.MODULE$) {
                    this.ensureCapacity(BoxesRunTime.unboxToInt(sortedSet.lastKey()) >> 6);
                } else if (ord == Ordering$Int$.MODULE$.scala$math$Ordering$CachedReverse$$_reverse()) {
                    this.ensureCapacity(BoxesRunTime.unboxToInt(sortedSet.firstKey()) >> 6);
                }
                Iterator iter = sortedSet.iterator();
                while (iter.hasNext()) {
                    this.addOne(BoxesRunTime.unboxToInt(iter.next()));
                }
            }
            return this;
        }
        return (BitSet)Growable.addAll$(this, xs);
    }

    @Override
    public boolean subsetOf(Set<Object> that) {
        if (that instanceof scala.collection.BitSet) {
            scala.collection.BitSet bitSet = (scala.collection.BitSet)that;
            int thisnwords = this.elems().length;
            int bsnwords = bitSet.nwords();
            int minWords = Math.min(thisnwords, bsnwords);
            for (int i = bsnwords; i < thisnwords; ++i) {
                if (this.word(i) == 0L) continue;
                return false;
            }
            for (int j = 0; j < minWords; ++j) {
                if ((this.word(j) & (bitSet.word(j) ^ 0xFFFFFFFFFFFFFFFFL)) == 0L) continue;
                return false;
            }
            return true;
        }
        return this.forall(that);
    }

    public BitSet subtractAll(IterableOnce<Object> xs) {
        if (xs instanceof scala.collection.BitSet) {
            scala.collection.BitSet bitSet = (scala.collection.BitSet)xs;
            return this.$amp$tilde$eq(bitSet);
        }
        return (BitSet)Shrinkable.subtractAll$(this, xs);
    }

    public Object writeReplace() {
        return new SerializationProxy(this);
    }

    @Override
    public BitSet diff(Set<Object> that) {
        if (that instanceof scala.collection.BitSet) {
            int thisnwords;
            scala.collection.BitSet bitSet = (scala.collection.BitSet)that;
            int bsnwords = bitSet.nwords();
            if (bsnwords >= (thisnwords = this.elems().length)) {
                int i;
                long currentWord = 0L;
                for (i = thisnwords - 1; i >= 0 && currentWord == 0L; --i) {
                    currentWord = this.word(i) & (bitSet.word(i) ^ 0xFFFFFFFFFFFFFFFFL);
                }
                if (i < 0) {
                    return this.fromBitMaskNoCopy(new long[]{currentWord});
                }
                int minimumNonZeroIndex = i + 1;
                int take$extension_n = minimumNonZeroIndex + 1;
                long[] newArray = (long[])ArrayOps$.MODULE$.slice$extension(this.elems(), 0, take$extension_n);
                newArray[i + 1] = currentWord;
                while (i >= 0) {
                    newArray[i] = this.word(i) & (bitSet.word(i) ^ 0xFFFFFFFFFFFFFFFFL);
                    --i;
                }
                return this.fromBitMaskNoCopy(newArray);
            }
            long[] newElems = (long[])this.elems().clone();
            for (int i = bsnwords - 1; i >= 0; --i) {
                newElems[i] = this.word(i) & (bitSet.word(i) ^ 0xFFFFFFFFFFFFFFFFL);
            }
            return this.fromBitMaskNoCopy(newElems);
        }
        return (BitSet)BitSetOps.diff$(this, that);
    }

    @Override
    public BitSet filterImpl(Function1<Object, Object> pred, boolean isFlipped) {
        long[] newArray = null;
        for (int i = this.elems().length - 1; i >= 0; --i) {
            long w;
            long l;
            long computeWordForFilter_oldWord = this.word(i);
            if (computeWordForFilter_oldWord == 0L) {
                l = 0L;
            } else {
                long computeWordForFilter_w = computeWordForFilter_oldWord;
                int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros(computeWordForFilter_oldWord);
                long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                int computeWordForFilter_maxJ = (i + 1) * 64 - Long.numberOfLeadingZeros(computeWordForFilter_oldWord);
                for (int computeWordForFilter_j = i * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                    if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && pred.apply$mcZI$sp(computeWordForFilter_j) == isFlipped) {
                        computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                    }
                    computeWordForFilter_jmask <<= 1;
                }
                l = w = computeWordForFilter_w;
            }
            if (w == 0L) continue;
            if (newArray == null) {
                newArray = new long[i + 1];
            }
            newArray[i] = w;
        }
        if (newArray == null) {
            return this.empty();
        }
        return this.fromBitMaskNoCopy(newArray);
    }

    /*
     * WARNING - void declaration
     */
    public BitSet filterInPlace(Function1<Object, Object> p) {
        int thisnwords = this.elems().length;
        for (int i = 0; i < thisnwords; ++i) {
            long l;
            void computeWordForFilter_oldWord;
            long[] lArray = this.elems();
            long l2 = this.elems()[i];
            boolean computeWordForFilter_isFlipped = false;
            if (computeWordForFilter_oldWord == 0L) {
                l = 0L;
            } else {
                void computeWordForFilter_w = computeWordForFilter_oldWord;
                int computeWordForFilter_trailingZeroes = Long.numberOfTrailingZeros((long)computeWordForFilter_oldWord);
                long computeWordForFilter_jmask = 1L << computeWordForFilter_trailingZeroes;
                int computeWordForFilter_maxJ = (i + 1) * 64 - Long.numberOfLeadingZeros((long)computeWordForFilter_oldWord);
                for (int computeWordForFilter_j = i * 64 + computeWordForFilter_trailingZeroes; computeWordForFilter_j != computeWordForFilter_maxJ; ++computeWordForFilter_j) {
                    if ((computeWordForFilter_w & computeWordForFilter_jmask) != 0L && p.apply$mcZI$sp(computeWordForFilter_j) == computeWordForFilter_isFlipped) {
                        computeWordForFilter_w &= computeWordForFilter_jmask ^ 0xFFFFFFFFFFFFFFFFL;
                    }
                    computeWordForFilter_jmask <<= 1;
                }
                l = computeWordForFilter_w;
            }
            lArray[i] = l;
        }
        return this;
    }

    @Override
    public long[] toBitMask() {
        return (long[])this.elems().clone();
    }

    public BitSet(long[] elems) {
        this.elems = elems;
    }

    public BitSet(int initSize) {
        this(new long[Math.max(initSize + 63 >> 6, 1)]);
    }

    public BitSet() {
        this(0);
    }

    private static final class SerializationProxy
    extends BitSet.SerializationProxy {
        private static final long serialVersionUID = 3L;

        @Override
        public Object readResolve() {
            return BitSet$.MODULE$.fromBitMaskNoCopy(this.elems());
        }

        public SerializationProxy(BitSet coll) {
            super(coll);
        }
    }
}

