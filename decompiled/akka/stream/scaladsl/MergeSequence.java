/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanInShape$;
import akka.stream.scaladsl.MergeSequence$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.collection.mutable.PriorityQueue;
import scala.collection.mutable.PriorityQueue$;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0005u!B\u00181\u0011\u00039d!B\u001d1\u0011\u0003Q\u0004\"B!\u0002\t\u0003\u0011e\u0001B\"\u0002\t\u0012C\u0001\"V\u0002\u0003\u0016\u0004%\tA\u0016\u0005\tM\u000e\u0011\t\u0012)A\u0005/\"Aqm\u0001BK\u0002\u0013\u0005\u0001\u000e\u0003\u0005m\u0007\tE\t\u0015!\u0003j\u0011!i7A!f\u0001\n\u0003q\u0007\u0002C8\u0004\u0005#\u0005\u000b\u0011B.\t\u000b\u0005\u001bA\u0011\u00019\t\u000fY\u001c\u0011\u0011!C\u0001o\"I\u0011\u0011A\u0002\u0012\u0002\u0013\u0005\u00111\u0001\u0005\n\u0003;\u0019\u0011\u0013!C\u0001\u0003?A\u0011\"a\n\u0004#\u0003%\t!!\u000b\t\u0013\u0005E2!!A\u0005B\u0005M\u0002\"CA#\u0007\u0005\u0005I\u0011AA$\u0011%\tyeAA\u0001\n\u0003\t\t\u0006C\u0005\u0002X\r\t\t\u0011\"\u0011\u0002Z!I\u0011qM\u0002\u0002\u0002\u0013\u0005\u0011\u0011\u000e\u0005\n\u0003g\u001a\u0011\u0011!C!\u0003kB\u0011\"!\u001f\u0004\u0003\u0003%\t%a\u001f\t\u0013\u0005u4!!A\u0005B\u0005}\u0004\"CAA\u0007\u0005\u0005I\u0011IAB\u000f%\t9)AA\u0001\u0012\u0013\tII\u0002\u0005D\u0003\u0005\u0005\t\u0012BAF\u0011\u0019\t\u0015\u0004\"\u0001\u0002\u0018\"I\u0011QP\r\u0002\u0002\u0013\u0015\u0013q\u0010\u0005\n\u00033K\u0012\u0011!CA\u00037C\u0011\"!,\u001a\u0003\u0003%\t)a,\t\u0013\u0005-\u0017$!A\u0005\n\u00055\u0007bBAk\u0003\u0011-\u0011q\u001b\u0005\b\u00033\u000bA\u0011AAt\u0011%\u0011\u0019\"AI\u0001\n\u0003\u0011)BB\u0003:a\t\u0011i\u0002\u0003\u0006\u0003\u0012\t\u0012)\u0019!C\u0001\u0003\u000fB!Ba\r#\u0005\u0003\u0005\u000b\u0011BA%\u0011)\u00119A\tB\u0001B\u0003%!Q\u0007\u0005\u0007\u0003\n\"\tAa\u000e\t\u0011U\u0013#\u0019!C\u0005\u0005\u0003BqA\u001a\u0012!\u0002\u0013\u0011\u0019\u0005C\u0005\u0003L\t\u0012\r\u0011\"\u0003\u0003N!A!Q\u000b\u0012!\u0002\u0013\u0011y\u0005C\u0005\u0003X\t\u0012\r\u0011\"\u0011\u0003Z!A!1\f\u0012!\u0002\u0013\u0011i\u0003C\u0004\u0003^\t\"\tEa\u0018\t\u000f\u0005u$\u0005\"\u0011\u0003r\u0005iQ*\u001a:hKN+\u0017/^3oG\u0016T!!\r\u001a\u0002\u0011M\u001c\u0017\r\\1eg2T!a\r\u001b\u0002\rM$(/Z1n\u0015\u0005)\u0014\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u00029\u00035\t\u0001GA\u0007NKJ<WmU3rk\u0016t7-Z\n\u0003\u0003m\u0002\"\u0001P \u000e\u0003uR\u0011AP\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0001v\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u00018\u0005\u0019\u0001Vo\u001d5fIV\u0011Q)X\n\u0005\u0007m2\u0015\n\u0005\u0002=\u000f&\u0011\u0001*\u0010\u0002\b!J|G-^2u!\tQ%K\u0004\u0002L!:\u0011AjT\u0007\u0002\u001b*\u0011aJN\u0001\u0007yI|w\u000e\u001e \n\u0003yJ!!U\u001f\u0002\u000fA\f7m[1hK&\u00111\u000b\u0016\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003#v\n!!\u001b8\u0016\u0003]\u00032\u0001W-\\\u001b\u0005\u0011\u0014B\u0001.3\u0005\u0015Ie\u000e\\3u!\taV\f\u0004\u0001\u0005\u000by\u001b!\u0019A0\u0003\u0003Q\u000b\"\u0001Y2\u0011\u0005q\n\u0017B\u00012>\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u00103\n\u0005\u0015l$aA!os\u0006\u0019\u0011N\u001c\u0011\u0002\u0011M,\u0017/^3oG\u0016,\u0012!\u001b\t\u0003y)L!a[\u001f\u0003\t1{gnZ\u0001\ng\u0016\fX/\u001a8dK\u0002\nA!\u001a7f[V\t1,A\u0003fY\u0016l\u0007\u0005\u0006\u0003rgR,\bc\u0001:\u000476\t\u0011\u0001C\u0003V\u0015\u0001\u0007q\u000bC\u0003h\u0015\u0001\u0007\u0011\u000eC\u0003n\u0015\u0001\u00071,\u0001\u0003d_BLXC\u0001=|)\u0011IHP`@\u0011\u0007I\u001c!\u0010\u0005\u0002]w\u0012)al\u0003b\u0001?\"9Qk\u0003I\u0001\u0002\u0004i\bc\u0001-Zu\"9qm\u0003I\u0001\u0002\u0004I\u0007bB7\f!\u0003\u0005\rA_\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\t)!a\u0007\u0016\u0005\u0005\u001d!fA,\u0002\n-\u0012\u00111\u0002\t\u0005\u0003\u001b\t9\"\u0004\u0002\u0002\u0010)!\u0011\u0011CA\n\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0016u\n!\"\u00198o_R\fG/[8o\u0013\u0011\tI\"a\u0004\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003_\u0019\t\u0007q,\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005\u0005\u0012QE\u000b\u0003\u0003GQ3![A\u0005\t\u0015qVB1\u0001`\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*B!a\u000b\u00020U\u0011\u0011Q\u0006\u0016\u00047\u0006%A!\u00020\u000f\u0005\u0004y\u0016!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u00026A!\u0011qGA!\u001b\t\tID\u0003\u0003\u0002<\u0005u\u0012\u0001\u00027b]\u001eT!!a\u0010\u0002\t)\fg/Y\u0005\u0005\u0003\u0007\nID\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u0013\u00022\u0001PA&\u0013\r\ti%\u0010\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004G\u0006M\u0003\"CA+#\u0005\u0005\t\u0019AA%\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\f\t\u0006\u0003;\n\u0019gY\u0007\u0003\u0003?R1!!\u0019>\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003K\nyF\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA6\u0003c\u00022\u0001PA7\u0013\r\ty'\u0010\u0002\b\u0005>|G.Z1o\u0011!\t)fEA\u0001\u0002\u0004\u0019\u0017A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u000e\u0002x!I\u0011Q\u000b\u000b\u0002\u0002\u0003\u0007\u0011\u0011J\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011J\u0001\ti>\u001cFO]5oOR\u0011\u0011QG\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005-\u0014Q\u0011\u0005\t\u0003+:\u0012\u0011!a\u0001G\u00061\u0001+^:iK\u0012\u0004\"A]\r\u0014\teY\u0014Q\u0012\t\u0005\u0003\u001f\u000b)*\u0004\u0002\u0002\u0012*!\u00111SA\u001f\u0003\tIw.C\u0002T\u0003##\"!!#\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u0005u\u00151\u0015\u000b\t\u0003?\u000b)+!+\u0002,B!!oAAQ!\ra\u00161\u0015\u0003\u0006=r\u0011\ra\u0018\u0005\u0007+r\u0001\r!a*\u0011\taK\u0016\u0011\u0015\u0005\u0006Or\u0001\r!\u001b\u0005\u0007[r\u0001\r!!)\u0002\u000fUt\u0017\r\u001d9msV!\u0011\u0011WAb)\u0011\t\u0019,!2\u0011\u000bq\n),!/\n\u0007\u0005]VH\u0001\u0004PaRLwN\u001c\t\ty\u0005m\u0016qX5\u0002B&\u0019\u0011QX\u001f\u0003\rQ+\b\u000f\\34!\u0011A\u0016,!1\u0011\u0007q\u000b\u0019\rB\u0003_;\t\u0007q\fC\u0005\u0002Hv\t\t\u00111\u0001\u0002J\u0006\u0019\u0001\u0010\n\u0019\u0011\tI\u001c\u0011\u0011Y\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u001f\u0004B!a\u000e\u0002R&!\u00111[A\u001d\u0005\u0019y%M[3di\u0006AqN\u001d3fe&tw-\u0006\u0003\u0002Z\u0006\u0015XCAAn!\u0015Q\u0015Q\\Aq\u0013\r\ty\u000e\u0016\u0002\t\u001fJ$WM]5oOB!!oAAr!\ra\u0016Q\u001d\u0003\u0006=~\u0011\raX\u000b\u0005\u0003S\fY\u0010\u0006\u0003\u0002l\n=A\u0003BAw\u0005\u000b\u0001r\u0001WAx\u0003g\fi0C\u0002\u0002rJ\u0012Qa\u0012:ba\"\u0004r\u0001WA{\u0003s\fI0C\u0002\u0002xJ\u0012\u0011#\u00168jM>\u0014XNR1o\u0013:\u001c\u0006.\u00199f!\ra\u00161 \u0003\u0006=\u0002\u0012\ra\u0018\t\u0005\u0003\u007f\u0014\t!D\u00015\u0013\r\u0011\u0019\u0001\u000e\u0002\b\u001d>$Xk]3e\u0011\u001d\u00119\u0001\ta\u0001\u0005\u0013\tq\"\u001a=ue\u0006\u001cGoU3rk\u0016t7-\u001a\t\u0007y\t-\u0011\u0011`5\n\u0007\t5QHA\u0005Gk:\u001cG/[8oc!I!\u0011\u0003\u0011\u0011\u0002\u0003\u0007\u0011\u0011J\u0001\u000bS:\u0004X\u000f\u001e)peR\u001c\u0018aD1qa2LH\u0005Z3gCVdG\u000fJ\u0019\u0016\t\t]!1D\u000b\u0003\u00053QC!!\u0013\u0002\n\u0011)a,\tb\u0001?V!!q\u0004B\u0019'\r\u0011#\u0011\u0005\t\u0007\u0005G\u0011IC!\f\u000e\u0005\t\u0015\"b\u0001B\u0014e\u0005)1\u000f^1hK&!!1\u0006B\u0013\u0005)9%/\u00199i'R\fw-\u001a\t\b1\u0006U(q\u0006B\u0018!\ra&\u0011\u0007\u0003\u0006=\n\u0012\raX\u0001\fS:\u0004X\u000f\u001e)peR\u001c\b\u0005\u0005\u0004=\u0005\u0017\u0011y#\u001b\u000b\u0005\u0005s\u0011y\u0004\u0006\u0003\u0003<\tu\u0002\u0003\u0002\u001d#\u0005_AqAa\u0002'\u0001\u0004\u0011)\u0004C\u0004\u0003\u0012\u0019\u0002\r!!\u0013\u0016\u0005\t\r\u0003#\u0002&\u0003F\t%\u0013b\u0001B$)\nQ\u0011J\u001c3fq\u0016$7+Z9\u0011\taK&qF\u0001\u0004_V$XC\u0001B(!\u0015A&\u0011\u000bB\u0018\u0013\r\u0011\u0019F\r\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0003\u0005[\taa\u001d5ba\u0016\u0004\u0013aC2sK\u0006$X\rT8hS\u000e$BA!\u0019\u0003hA!!1\u0005B2\u0013\u0011\u0011)G!\n\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDqA!\u001b.\u0001\u0004\u0011Y'A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000fE\u0002Y\u0005[J1Aa\u001c3\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u000b\u0003\u0005g\u0002BA!\u001e\u0003~9!!q\u000fB=!\taU(C\u0002\u0003|u\na\u0001\u0015:fI\u00164\u0017\u0002BA\"\u0005\u007fR1Aa\u001f>\u0001")
public final class MergeSequence<T>
extends GraphStage<UniformFanInShape<T, T>> {
    private final int inputPorts;
    public final Function1<T, Object> akka$stream$scaladsl$MergeSequence$$extractSequence;
    private final IndexedSeq<Inlet<T>> akka$stream$scaladsl$MergeSequence$$in;
    private final Outlet<T> akka$stream$scaladsl$MergeSequence$$out;
    private final UniformFanInShape<T, T> shape;

    public static <T> int apply$default$1() {
        return MergeSequence$.MODULE$.apply$default$1();
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts, Function1<T, Object> extractSequence) {
        return MergeSequence$.MODULE$.apply(inputPorts, extractSequence);
    }

    public int inputPorts() {
        return this.inputPorts;
    }

    public IndexedSeq<Inlet<T>> akka$stream$scaladsl$MergeSequence$$in() {
        return this.akka$stream$scaladsl$MergeSequence$$in;
    }

    public Outlet<T> akka$stream$scaladsl$MergeSequence$$out() {
        return this.akka$stream$scaladsl$MergeSequence$$out;
    }

    @Override
    public UniformFanInShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private long akka$stream$scaladsl$MergeSequence$$anon$$nextSequence;
            private final PriorityQueue<Pushed<T>> akka$stream$scaladsl$MergeSequence$$anon$$available;
            private int akka$stream$scaladsl$MergeSequence$$anon$$complete;
            private final /* synthetic */ MergeSequence $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public long akka$stream$scaladsl$MergeSequence$$anon$$nextSequence() {
                return this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence;
            }

            public void akka$stream$scaladsl$MergeSequence$$anon$$nextSequence_$eq(long x$1) {
                this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence = x$1;
            }

            public PriorityQueue<Pushed<T>> akka$stream$scaladsl$MergeSequence$$anon$$available() {
                return this.akka$stream$scaladsl$MergeSequence$$anon$$available;
            }

            public int akka$stream$scaladsl$MergeSequence$$anon$$complete() {
                return this.akka$stream$scaladsl$MergeSequence$$anon$$complete;
            }

            public void akka$stream$scaladsl$MergeSequence$$anon$$complete_$eq(int x$1) {
                this.akka$stream$scaladsl$MergeSequence$$anon$$complete = x$1;
            }

            public void onPull() {
                if (this.akka$stream$scaladsl$MergeSequence$$anon$$available().nonEmpty() && this.akka$stream$scaladsl$MergeSequence$$anon$$available().head().sequence() == this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence()) {
                    Pushed<T> pushed = this.akka$stream$scaladsl$MergeSequence$$anon$$available().dequeue();
                    this.push(this.$outer.akka$stream$scaladsl$MergeSequence$$out(), pushed.elem());
                    if (this.akka$stream$scaladsl$MergeSequence$$anon$$complete() == this.$outer.inputPorts() && this.akka$stream$scaladsl$MergeSequence$$anon$$available().isEmpty()) {
                        this.completeStage();
                        return;
                    }
                    if (this.akka$stream$scaladsl$MergeSequence$$anon$$available().nonEmpty() && this.akka$stream$scaladsl$MergeSequence$$anon$$available().head().sequence() == this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence()) {
                        this.failStage(new IllegalStateException(new StringBuilder(34).append("Duplicate sequence ").append(this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence()).append(" on ports ").append(pushed.in()).append(" and ").append(this.akka$stream$scaladsl$MergeSequence$$anon$$available().head().in()).toString()));
                    }
                    this.tryPull(pushed.in());
                    this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence_$eq(this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence() + 1L);
                    return;
                }
                this.akka$stream$scaladsl$MergeSequence$$anon$$detectMissedSequence();
            }

            public void akka$stream$scaladsl$MergeSequence$$anon$$detectMissedSequence() {
                if (this.isAvailable(this.$outer.akka$stream$scaladsl$MergeSequence$$out()) && this.akka$stream$scaladsl$MergeSequence$$anon$$available().size() + this.akka$stream$scaladsl$MergeSequence$$anon$$complete() >= this.$outer.inputPorts()) {
                    if (this.akka$stream$scaladsl$MergeSequence$$anon$$available().count((Function1<Pushed, Object> & Serializable)pushed -> BoxesRunTime.boxToBoolean($anon$26.$anonfun$detectMissedSequence$1(this, pushed))) + this.akka$stream$scaladsl$MergeSequence$$anon$$complete() == this.$outer.inputPorts()) {
                        this.failStage(new IllegalStateException(new StringBuilder(134).append("Expected sequence ").append(this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence()).append(", but all input ports have pushed or are complete, ").append("but none have pushed the next sequence number. Pushed sequences: ").append(((IterableOnceOps)this.akka$stream$scaladsl$MergeSequence$$anon$$available().toVector().map((Function1<Pushed, String> & Serializable)p -> new StringBuilder(2).append(p.in()).append(": ").append(p.sequence()).toString())).mkString(", ")).toString()));
                        return;
                    }
                    return;
                }
            }

            public void preStart() {
                this.$outer.akka$stream$scaladsl$MergeSequence$$in().foreach((Function1<Inlet, Object> & Serializable)in -> {
                    $anon$26.$anonfun$preStart$5(this, in);
                    return BoxedUnit.UNIT;
                });
            }

            public /* synthetic */ MergeSequence akka$stream$scaladsl$MergeSequence$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$new$17($anon$26 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    Inlet inPort = (Inlet)tuple2._1();
                    int idx = tuple2._2$mcI$sp();
                    $this.setHandler(inPort, new InHandler($this, inPort, idx){
                        private final /* synthetic */ $anon$26 $outer;
                        private final Inlet inPort$1;
                        private final int idx$3;

                        public void onUpstreamFailure(Throwable ex) throws Exception {
                            InHandler.onUpstreamFailure$(this, ex);
                        }

                        public void onPush() {
                            T elem = this.$outer.grab(this.inPort$1);
                            long sequence2 = BoxesRunTime.unboxToLong(this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$$outer().akka$stream$scaladsl$MergeSequence$$extractSequence.apply(elem));
                            if (sequence2 < this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence()) {
                                this.$outer.failStage(new IllegalStateException(new StringBuilder(38).append("Sequence regression from ").append(this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence()).append(" to ").append(sequence2).append(" on port ").append(this.idx$3).toString()));
                                return;
                            }
                            if (sequence2 == this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence() && this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$$outer().akka$stream$scaladsl$MergeSequence$$out())) {
                                this.$outer.push(this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$$outer().akka$stream$scaladsl$MergeSequence$$out(), elem);
                                this.$outer.tryPull(this.inPort$1);
                                this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence_$eq(this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence() + 1L);
                                return;
                            }
                            this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$available().enqueue(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Pushed[]{new Pushed<T>(this.inPort$1, sequence2, elem)}));
                            this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$detectMissedSequence();
                        }

                        public void onUpstreamFinish() {
                            this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$complete_$eq(this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$complete() + 1);
                            if (this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$complete() == this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$$outer().inputPorts() && this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$available().isEmpty()) {
                                this.$outer.completeStage();
                                return;
                            }
                            this.$outer.akka$stream$scaladsl$MergeSequence$$anon$$detectMissedSequence();
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.inPort$1 = inPort$1;
                            this.idx$3 = idx$3;
                            InHandler.$init$(this);
                        }
                    });
                    return;
                }
                throw new MatchError(tuple2);
            }

            public static final /* synthetic */ boolean $anonfun$detectMissedSequence$1($anon$26 $this, Pushed pushed) {
                return !$this.isClosed(pushed.in());
            }

            public static final /* synthetic */ void $anonfun$preStart$5($anon$26 $this, Inlet in) {
                $this.pull(in);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.akka$stream$scaladsl$MergeSequence$$anon$$nextSequence = 0L;
                this.akka$stream$scaladsl$MergeSequence$$anon$$available = PriorityQueue$.MODULE$.empty(MergeSequence$.MODULE$.akka$stream$scaladsl$MergeSequence$$ordering());
                this.akka$stream$scaladsl$MergeSequence$$anon$$complete = 0;
                this.setHandler($outer.akka$stream$scaladsl$MergeSequence$$out(), this);
                ((IterableOnceOps)$outer.akka$stream$scaladsl$MergeSequence$$in().zipWithIndex()).foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$26.$anonfun$new$17(this, x0$1);
                    return BoxedUnit.UNIT;
                });
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$detectMissedSequence$1$adapted(akka.stream.scaladsl.MergeSequence$$anon$26 akka.stream.scaladsl.MergeSequence$Pushed ), $anonfun$detectMissedSequence$2(akka.stream.scaladsl.MergeSequence$Pushed ), $anonfun$new$17$adapted(akka.stream.scaladsl.MergeSequence$$anon$26 scala.Tuple2 ), $anonfun$preStart$5$adapted(akka.stream.scaladsl.MergeSequence$$anon$26 akka.stream.Inlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return new StringBuilder(15).append("MergeSequence(").append(this.inputPorts()).append(")").toString();
    }

    public MergeSequence(int inputPorts, Function1<T, Object> extractSequence) {
        this.inputPorts = inputPorts;
        this.akka$stream$scaladsl$MergeSequence$$extractSequence = extractSequence;
        Predef$.MODULE$.require(inputPorts > 1, (Function0<Object>)(Function0<String> & Serializable)() -> "A MergeSequence must have more than 1 input ports");
        this.akka$stream$scaladsl$MergeSequence$$in = (IndexedSeq)package$.MODULE$.Vector().tabulate(inputPorts, (Function1<Object, Inlet> & Serializable)i -> Inlet$.MODULE$.apply(new StringBuilder(16).append("MergeSequence.in").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.akka$stream$scaladsl$MergeSequence$$out = Outlet$.MODULE$.apply("MergeSequence.out");
        this.shape = UniformFanInShape$.MODULE$.apply(this.akka$stream$scaladsl$MergeSequence$$out(), this.akka$stream$scaladsl$MergeSequence$$in());
    }

    private static class Pushed<T>
    implements Product,
    Serializable {
        private final Inlet<T> in;
        private final long sequence;
        private final T elem;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Inlet<T> in() {
            return this.in;
        }

        public long sequence() {
            return this.sequence;
        }

        public T elem() {
            return this.elem;
        }

        public <T> Pushed<T> copy(Inlet<T> in, long sequence2, T elem) {
            return new Pushed<T>(in, sequence2, elem);
        }

        public <T> Inlet<T> copy$default$1() {
            return this.in();
        }

        public <T> long copy$default$2() {
            return this.sequence();
        }

        public <T> T copy$default$3() {
            return this.elem();
        }

        @Override
        public String productPrefix() {
            return "Pushed";
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
                    return this.in();
                }
                case 1: {
                    return BoxesRunTime.boxToLong(this.sequence());
                }
                case 2: {
                    return this.elem();
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
            return x$1 instanceof Pushed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "in";
                }
                case 1: {
                    return "sequence";
                }
                case 2: {
                    return "elem";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.in()));
            n = Statics.mix(n, Statics.longHash(this.sequence()));
            n = Statics.mix(n, Statics.anyHash(this.elem()));
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof Pushed)) return false;
            boolean bl = true;
            if (!bl) return false;
            Pushed pushed = (Pushed)x$1;
            if (this.sequence() != pushed.sequence()) return false;
            Inlet<T> inlet = this.in();
            Inlet<T> inlet2 = pushed.in();
            if (inlet == null) {
                if (inlet2 != null) {
                    return false;
                }
            } else if (!((Object)inlet).equals(inlet2)) return false;
            if (!BoxesRunTime.equals(this.elem(), pushed.elem())) return false;
            if (!pushed.canEqual(this)) return false;
            return true;
        }

        public Pushed(Inlet<T> in, long sequence2, T elem) {
            this.in = in;
            this.sequence = sequence2;
            this.elem = elem;
            Product.$init$(this);
        }
    }
}

