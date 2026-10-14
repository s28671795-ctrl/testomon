/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.FanInShape2;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.scaladsl.MergeSorted$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.math.Ordering;
import scala.math.Ordering$Implicits$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005q3A\u0001D\u0007\u0003)!Aa\u0006\u0001B\u0002B\u0003-q\u0006C\u0003<\u0001\u0011\u0005A\bC\u0004B\u0001\t\u0007I\u0011\u0002\"\t\r\u0019\u0003\u0001\u0015!\u0003D\u0011\u001d9\u0005A1A\u0005\n\tCa\u0001\u0013\u0001!\u0002\u0013\u0019\u0005bB%\u0001\u0005\u0004%IA\u0013\u0005\u0007\u001d\u0002\u0001\u000b\u0011B&\t\u000f=\u0003!\u0019!C!!\"1\u0011\u000b\u0001Q\u0001\nqAQA\u0015\u0001\u0005BM\u00131\"T3sO\u0016\u001cvN\u001d;fI*\u0011abD\u0001\tg\u000e\fG.\u00193tY*\u0011\u0001#E\u0001\u0007gR\u0014X-Y7\u000b\u0003I\tA!Y6lC\u000e\u0001QCA\u000b#'\t\u0001a\u0003E\u0002\u00185qi\u0011\u0001\u0007\u0006\u00033=\tQa\u001d;bO\u0016L!a\u0007\r\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rE\u0003\u001e=\u0001\u0002\u0003%D\u0001\u0010\u0013\tyrBA\u0006GC:Len\u00155ba\u0016\u0014\u0004CA\u0011#\u0019\u0001!Qa\t\u0001C\u0002\u0011\u0012\u0011\u0001V\t\u0003K-\u0002\"AJ\u0015\u000e\u0003\u001dR\u0011\u0001K\u0001\u0006g\u000e\fG.Y\u0005\u0003U\u001d\u0012qAT8uQ&tw\r\u0005\u0002'Y%\u0011Qf\n\u0002\u0004\u0003:L\u0018AC3wS\u0012,gnY3%cA\u0019\u0001\u0007\u000f\u0011\u000f\u0005E2dB\u0001\u001a6\u001b\u0005\u0019$B\u0001\u001b\u0014\u0003\u0019a$o\\8u}%\t\u0001&\u0003\u00028O\u00059\u0001/Y2lC\u001e,\u0017BA\u001d;\u0005!y%\u000fZ3sS:<'BA\u001c(\u0003\u0019a\u0014N\\5u}Q\tQ\b\u0006\u0002?\u0001B\u0019q\b\u0001\u0011\u000e\u00035AQA\f\u0002A\u0004=\nA\u0001\\3giV\t1\tE\u0002\u001e\t\u0002J!!R\b\u0003\u000b%sG.\u001a;\u0002\u000b1,g\r\u001e\u0011\u0002\u000bILw\r\u001b;\u0002\rILw\r\u001b;!\u0003\ryW\u000f^\u000b\u0002\u0017B\u0019Q\u0004\u0014\u0011\n\u00055{!AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,W#\u0001\u000f\u0002\rMD\u0017\r]3!\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005Q;\u0006CA\fV\u0013\t1\u0006DA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015A6\u00021\u0001Z\u0003\u0011\tG\u000f\u001e:\u0011\u0005uQ\u0016BA.\u0010\u0005)\tE\u000f\u001e:jEV$Xm\u001d")
public final class MergeSorted<T>
extends GraphStage<FanInShape2<T, T, T>> {
    public final Ordering<T> akka$stream$scaladsl$MergeSorted$$evidence$1;
    private final Inlet<T> akka$stream$scaladsl$MergeSorted$$left;
    private final Inlet<T> akka$stream$scaladsl$MergeSorted$$right;
    private final Outlet<T> akka$stream$scaladsl$MergeSorted$$out;
    private final FanInShape2<T, T, T> shape;

    public Inlet<T> akka$stream$scaladsl$MergeSorted$$left() {
        return this.akka$stream$scaladsl$MergeSorted$$left;
    }

    public Inlet<T> akka$stream$scaladsl$MergeSorted$$right() {
        return this.akka$stream$scaladsl$MergeSorted$$right;
    }

    public Outlet<T> akka$stream$scaladsl$MergeSorted$$out() {
        return this.akka$stream$scaladsl$MergeSorted$$out;
    }

    @Override
    public FanInShape2<T, T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes attr) {
        return new GraphStageLogic(this){
            private T other;
            private final Function1<T, BoxedUnit> dispatchR;
            private final Function1<T, BoxedUnit> dispatchL;
            private final Function0<BoxedUnit> passR;
            private final Function0<BoxedUnit> passL;
            private final Function0<BoxedUnit> readR;
            private final Function0<BoxedUnit> readL;
            private final /* synthetic */ MergeSorted $outer;

            private T other() {
                return this.other;
            }

            private void other_$eq(T x$1) {
                this.other = x$1;
            }

            private void nullOut() {
                this.other_$eq(null);
            }

            private void dispatch(T l, T r) {
                if (Ordering$Implicits$.MODULE$.infixOrderingOps(l, this.$outer.akka$stream$scaladsl$MergeSorted$$evidence$1).$less(r)) {
                    this.other_$eq(r);
                    this.emit(this.$outer.akka$stream$scaladsl$MergeSorted$$out(), l, this.readL());
                    return;
                }
                this.other_$eq(l);
                this.emit(this.$outer.akka$stream$scaladsl$MergeSorted$$out(), r, this.readR());
            }

            private Function1<T, BoxedUnit> dispatchR() {
                return this.dispatchR;
            }

            private Function1<T, BoxedUnit> dispatchL() {
                return this.dispatchL;
            }

            private Function0<BoxedUnit> passR() {
                return this.passR;
            }

            private Function0<BoxedUnit> passL() {
                return this.passL;
            }

            private Function0<BoxedUnit> readR() {
                return this.readR;
            }

            private Function0<BoxedUnit> readL() {
                return this.readL;
            }

            public void preStart() {
                this.pull(this.$outer.akka$stream$scaladsl$MergeSorted$$right());
                this.read(this.$outer.akka$stream$scaladsl$MergeSorted$$left(), (Function1<Object, Object> & Serializable)(T l) -> {
                    $anon$10.$anonfun$preStart$3(this, l);
                    return BoxedUnit.UNIT;
                }, () -> this.passAlong($this.$outer.akka$stream$scaladsl$MergeSorted$$right(), $this.$outer.akka$stream$scaladsl$MergeSorted$$out(), this.passAlong$default$3(), this.passAlong$default$4(), this.passAlong$default$5()));
            }

            public static final /* synthetic */ void $anonfun$dispatchR$1($anon$10 $this, Object x$4) {
                $this.dispatch($this.other(), x$4);
            }

            public static final /* synthetic */ void $anonfun$dispatchL$1($anon$10 $this, Object x$5) {
                $this.dispatch(x$5, $this.other());
            }

            public static final /* synthetic */ void $anonfun$preStart$3($anon$10 $this, Object l) {
                $this.other_$eq(l);
                $this.readR().apply$mcV$sp();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.setHandler($outer.akka$stream$scaladsl$MergeSorted$$left(), this.ignoreTerminateInput());
                this.setHandler($outer.akka$stream$scaladsl$MergeSorted$$right(), this.ignoreTerminateInput());
                this.setHandler($outer.akka$stream$scaladsl$MergeSorted$$out(), this.eagerTerminateOutput());
                this.dispatchR = (Function1<Object, Object> & Serializable)x$4 -> {
                    $anon$10.$anonfun$dispatchR$1(this, x$4);
                    return BoxedUnit.UNIT;
                };
                this.dispatchL = (Function1<Object, Object> & Serializable)x$5 -> {
                    $anon$10.$anonfun$dispatchL$1(this, x$5);
                    return BoxedUnit.UNIT;
                };
                this.passR = () -> this.emit($this.$outer.akka$stream$scaladsl$MergeSorted$$out(), this.other(), () -> {
                    this.nullOut();
                    Inlet<T> x$1 = $this.$outer.akka$stream$scaladsl$MergeSorted$$right();
                    Outlet<T> x$2 = $this.$outer.akka$stream$scaladsl$MergeSorted$$out();
                    boolean x$3 = true;
                    boolean x$4 = this.passAlong$default$3();
                    boolean x$5 = this.passAlong$default$4();
                    this.passAlong(x$1, x$2, x$4, x$5, true);
                });
                this.passL = () -> this.emit($this.$outer.akka$stream$scaladsl$MergeSorted$$out(), this.other(), () -> {
                    this.nullOut();
                    Inlet<T> x$6 = $this.$outer.akka$stream$scaladsl$MergeSorted$$left();
                    Outlet<T> x$7 = $this.$outer.akka$stream$scaladsl$MergeSorted$$out();
                    boolean x$8 = true;
                    boolean x$9 = this.passAlong$default$3();
                    boolean x$10 = this.passAlong$default$4();
                    this.passAlong(x$6, x$7, x$9, x$10, true);
                });
                this.readR = () -> this.read($this.$outer.akka$stream$scaladsl$MergeSorted$$right(), this.dispatchR(), this.passL());
                this.readL = () -> this.read($this.$outer.akka$stream$scaladsl$MergeSorted$$left(), this.dispatchL(), this.passR());
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$dispatchL$1$adapted(akka.stream.scaladsl.MergeSorted$$anon$10 java.lang.Object ), $anonfun$dispatchR$1$adapted(akka.stream.scaladsl.MergeSorted$$anon$10 java.lang.Object ), $anonfun$passL$1(akka.stream.scaladsl.MergeSorted$$anon$10 ), $anonfun$passL$2(akka.stream.scaladsl.MergeSorted$$anon$10 ), $anonfun$passR$1(akka.stream.scaladsl.MergeSorted$$anon$10 ), $anonfun$passR$2(akka.stream.scaladsl.MergeSorted$$anon$10 ), $anonfun$preStart$3$adapted(akka.stream.scaladsl.MergeSorted$$anon$10 java.lang.Object ), $anonfun$preStart$4(akka.stream.scaladsl.MergeSorted$$anon$10 ), $anonfun$readL$1(akka.stream.scaladsl.MergeSorted$$anon$10 ), $anonfun$readR$1(akka.stream.scaladsl.MergeSorted$$anon$10 )}, serializedLambda);
            }
        };
    }

    public MergeSorted(Ordering<T> evidence$1) {
        this.akka$stream$scaladsl$MergeSorted$$evidence$1 = evidence$1;
        this.akka$stream$scaladsl$MergeSorted$$left = Inlet$.MODULE$.apply("left");
        this.akka$stream$scaladsl$MergeSorted$$right = Inlet$.MODULE$.apply("right");
        this.akka$stream$scaladsl$MergeSorted$$out = Outlet$.MODULE$.apply("out");
        this.shape = new FanInShape2<T, T, T>(this.akka$stream$scaladsl$MergeSorted$$left(), this.akka$stream$scaladsl$MergeSorted$$right(), this.akka$stream$scaladsl$MergeSorted$$out());
    }
}

