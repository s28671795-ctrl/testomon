/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Collect$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.Recover$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005Uf!\u0002\r\u001a\u0005~\t\u0003\u0002\u0003)\u0001\u0005+\u0007I\u0011A)\t\u0011a\u0003!\u0011#Q\u0001\nICQ!\u0017\u0001\u0005\u0002iCQA\u0018\u0001\u0005R}CQ\u0001\u001a\u0001\u0005B\u0015DqA\u001c\u0001\u0002\u0002\u0013\u0005q\u000eC\u0004w\u0001E\u0005I\u0011A<\t\u0013\u0005%\u0001!!A\u0005B\u0005-\u0001\"CA\u000f\u0001\u0005\u0005I\u0011AA\u0010\u0011%\t9\u0003AA\u0001\n\u0003\tI\u0003C\u0005\u00020\u0001\t\t\u0011\"\u0011\u00022!I\u0011q\b\u0001\u0002\u0002\u0013\u0005\u0011\u0011\t\u0005\n\u0003\u0017\u0002\u0011\u0011!C!\u0003\u001bB\u0011\"!\u0015\u0001\u0003\u0003%\t%a\u0015\t\u0013\u0005U\u0003!!A\u0005B\u0005]\u0003\"CA-\u0001\u0005\u0005I\u0011IA.\u000f)\tY'GA\u0001\u0012\u0003y\u0012Q\u000e\u0004\n1e\t\t\u0011#\u0001 \u0003_Ba!\u0017\n\u0005\u0002\u0005\u0005\u0005\"CA+%\u0005\u0005IQIA,\u0011%\t\u0019IEA\u0001\n\u0003\u000b)\tC\u0005\u0002\u0014J\t\t\u0011\"!\u0002\u0016\"I\u00111\u0016\n\u0002\u0002\u0013%\u0011Q\u0016\u0002\b%\u0016\u001cwN^3s\u0015\tQ2$\u0001\u0004gkNLgn\u001a\u0006\u00039u\tA![7qY*\u0011adH\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0001\nA!Y6lCV\u0011!\u0005O\n\u0005\u0001\r\"u\tE\u0002%gYr!!J\u0019\u000f\u0005\u0019\u0002dBA\u00140\u001d\tAcF\u0004\u0002*[5\t!F\u0003\u0002,Y\u00051AH]8piz\u001a\u0001!C\u0001!\u0013\tqr$\u0003\u0002\u001d;%\u0011!dG\u0005\u0003ee\t1b\u0012:ba\"\u001cF/Y4fg&\u0011A'\u000e\u0002\u0017'&l\u0007\u000f\\3MS:,\u0017M]$sCBD7\u000b^1hK*\u0011!'\u0007\t\u0003oab\u0001\u0001B\u0003:\u0001\t\u0007!HA\u0001U#\tY\u0014\t\u0005\u0002=\u007f5\tQHC\u0001?\u0003\u0015\u00198-\u00197b\u0013\t\u0001UHA\u0004O_RD\u0017N\\4\u0011\u0005q\u0012\u0015BA\">\u0005\r\te.\u001f\t\u0003y\u0015K!AR\u001f\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001*\u0014\b\u0003\u0013.s!!\u000b&\n\u0003yJ!\u0001T\u001f\u0002\u000fA\f7m[1hK&\u0011aj\u0014\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u0019v\n!\u0001\u001d4\u0016\u0003I\u0003B\u0001P*Vm%\u0011A+\u0010\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011\u0001JV\u0005\u0003/>\u0013\u0011\u0002\u00165s_^\f'\r\\3\u0002\u0007A4\u0007%\u0001\u0004=S:LGO\u0010\u000b\u00037v\u00032\u0001\u0018\u00017\u001b\u0005I\u0002\"\u0002)\u0004\u0001\u0004\u0011\u0016!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\t\u0001\r\u0005\u0002bE6\tQ$\u0003\u0002d;\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003M2\u0004\"a\u001a6\u000e\u0003!T!![\u000f\u0002\u000bM$\u0018mZ3\n\u0005-D'aD$sCBD7\u000b^1hK2{w-[2\t\u000b5,\u0001\u0019\u00011\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\t\r|\u0007/_\u000b\u0003aN$\"!\u001d;\u0011\u0007q\u0003!\u000f\u0005\u00028g\u0012)\u0011H\u0002b\u0001u!9\u0001K\u0002I\u0001\u0002\u0004)\b\u0003\u0002\u001fT+J\fabY8qs\u0012\"WMZ1vYR$\u0013'F\u0002y\u0003\u000f)\u0012!\u001f\u0016\u0003%j\\\u0013a\u001f\t\u0004y\u0006\rQ\"A?\u000b\u0005y|\u0018!C;oG\",7m[3e\u0015\r\t\t!P\u0001\u000bC:tw\u000e^1uS>t\u0017bAA\u0003{\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000be:!\u0019\u0001\u001e\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ti\u0001\u0005\u0003\u0002\u0010\u0005eQBAA\t\u0015\u0011\t\u0019\"!\u0006\u0002\t1\fgn\u001a\u0006\u0003\u0003/\tAA[1wC&!\u00111DA\t\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011\u0011\u0005\t\u0004y\u0005\r\u0012bAA\u0013{\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019\u0011)a\u000b\t\u0013\u00055\"\"!AA\u0002\u0005\u0005\u0012a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u00024A)\u0011QGA\u001e\u00036\u0011\u0011q\u0007\u0006\u0004\u0003si\u0014AC2pY2,7\r^5p]&!\u0011QHA\u001c\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\r\u0013\u0011\n\t\u0004y\u0005\u0015\u0013bAA${\t9!i\\8mK\u0006t\u0007\u0002CA\u0017\u0019\u0005\u0005\t\u0019A!\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u001b\ty\u0005C\u0005\u0002.5\t\t\u00111\u0001\u0002\"\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\"\u0005AAo\\*ue&tw\r\u0006\u0002\u0002\u000e\u00051Q-];bYN$B!a\u0011\u0002^!A\u0011Q\u0006\t\u0002\u0002\u0003\u0007\u0011\tK\u0002\u0001\u0003C\u0002B!a\u0019\u0002h5\u0011\u0011Q\r\u0006\u0004\u0003\u0003y\u0012\u0002BA5\u0003K\u00121\"\u00138uKJt\u0017\r\\!qS\u00069!+Z2pm\u0016\u0014\bC\u0001/\u0013'\u0015\u0011\u0012\u0011OA<!\ra\u00141O\u0005\u0004\u0003kj$AB!osJ+g\r\u0005\u0003\u0002z\u0005}TBAA>\u0015\u0011\ti(!\u0006\u0002\u0005%|\u0017b\u0001(\u0002|Q\u0011\u0011QN\u0001\u0006CB\u0004H._\u000b\u0005\u0003\u000f\u000bi\t\u0006\u0003\u0002\n\u0006=\u0005\u0003\u0002/\u0001\u0003\u0017\u00032aNAG\t\u0015ITC1\u0001;\u0011\u0019\u0001V\u00031\u0001\u0002\u0012B)AhU+\u0002\f\u00069QO\\1qa2LX\u0003BAL\u0003G#B!!'\u0002&B)A(a'\u0002 &\u0019\u0011QT\u001f\u0003\r=\u0003H/[8o!\u0015a4+VAQ!\r9\u00141\u0015\u0003\u0006sY\u0011\rA\u000f\u0005\n\u0003O3\u0012\u0011!a\u0001\u0003S\u000b1\u0001\u001f\u00131!\u0011a\u0006!!)\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005=\u0006\u0003BA\b\u0003cKA!a-\u0002\u0012\t1qJ\u00196fGR\u0004")
@InternalApi
public final class Recover<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final PartialFunction<Throwable, T> pf;

    public static <T> Option<PartialFunction<Throwable, T>> unapply(Recover<T> x$0) {
        return Recover$.MODULE$.unapply(x$0);
    }

    public static <T> Recover<T> apply(PartialFunction<Throwable, T> pf) {
        return Recover$.MODULE$.apply(pf);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public PartialFunction<Throwable, T> pf() {
        return this.pf;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.recover().and(Attributes$SourceLocation$.MODULE$.forLambda(this.pf()));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private T recovered;
            private final /* synthetic */ Recover $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            private T recovered() {
                return this.recovered;
            }

            private void recovered_$eq(T x$1) {
                this.recovered = x$1;
            }

            public void onPush() {
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public void onPull() {
                T t2 = this.recovered();
                T t3 = OptionVal$Some$.MODULE$.unapply(t2);
                if (!OptionVal$.MODULE$.isEmpty$extension(t3)) {
                    T elem = OptionVal$.MODULE$.get$extension(t3);
                    this.push(this.$outer.out(), elem);
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in());
            }

            public void onUpstreamFailure(Throwable ex) {
                block7: {
                    try {
                        Object object = this.$outer.pf().applyOrElse(ex, Collect$.MODULE$.NotApplied());
                        Function1<Object, Object> function1 = Collect$.MODULE$.NotApplied();
                        Object object2 = object;
                        if (!(function1 != null ? !function1.equals(object2) : object2 != null)) {
                            this.failStage(ex);
                            break block7;
                        }
                        if (object instanceof Object) {
                            Object object3 = object;
                            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(object3);
                            if (this.isAvailable(this.$outer.out())) {
                                this.push(this.$outer.out(), object3);
                                this.completeStage();
                            } else {
                                this.recovered_$eq(OptionVal$Some$.MODULE$.apply(object3));
                            }
                            break block7;
                        }
                        throw new IllegalStateException();
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex2 = option.get();
                            this.failStage(ex2);
                        }
                        throw throwable;
                    }
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.recovered = OptionVal$.MODULE$.none();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> Recover<T> copy(PartialFunction<Throwable, T> pf) {
        return new Recover<T>(pf);
    }

    public <T> PartialFunction<Throwable, T> copy$default$1() {
        return this.pf();
    }

    @Override
    public String productPrefix() {
        return "Recover";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.pf();
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
        return x$1 instanceof Recover;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "pf";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
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
        if (!(object instanceof Recover)) return false;
        boolean bl = true;
        if (!bl) return false;
        Recover recover2 = (Recover)x$1;
        PartialFunction<Throwable, T> partialFunction = this.pf();
        PartialFunction<Throwable, T> partialFunction2 = recover2.pf();
        if (partialFunction != null) {
            if (!partialFunction.equals(partialFunction2)) return false;
            return true;
        }
        if (partialFunction2 == null) return true;
        return false;
    }

    public Recover(PartialFunction<Throwable, T> pf) {
        this.pf = pf;
        Product.$init$(this);
    }
}

