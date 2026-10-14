/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.StatefulMapConcat$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001da!B\u0007\u000f\u0005Q1\u0002\u0002\u0003\u001b\u0001\u0005\u000b\u0007I\u0011A\u001b\t\u0011!\u0003!\u0011!Q\u0001\nYBQ!\u0013\u0001\u0005\u0002)CqA\u0014\u0001C\u0002\u0013\u0005q\n\u0003\u0004T\u0001\u0001\u0006I\u0001\u0015\u0005\b)\u0002\u0011\r\u0011\"\u0001V\u0011\u0019I\u0006\u0001)A\u0005-\"9!\f\u0001b\u0001\n\u0003Z\u0006B\u0002/\u0001A\u0003%a\u0004C\u0003^\u0001\u0011\u0005c\fC\u0003c\u0001\u0011\u00051\rC\u0003s\u0001\u0011\u00053OA\tTi\u0006$XMZ;m\u001b\u0006\u00048i\u001c8dCRT!a\u0004\t\u0002\r\u0019,8/\u001b8h\u0015\t\t\"#\u0001\u0003j[Bd'BA\n\u0015\u0003\u0019\u0019HO]3b[*\tQ#\u0001\u0003bW.\fWcA\f%eM\u0011\u0001\u0001\u0007\t\u00043qqR\"\u0001\u000e\u000b\u0005m\u0011\u0012!B:uC\u001e,\u0017BA\u000f\u001b\u0005)9%/\u00199i'R\fw-\u001a\t\u0005?\u0001\u0012\u0013'D\u0001\u0013\u0013\t\t#CA\u0005GY><8\u000b[1qKB\u00111\u0005\n\u0007\u0001\t\u0015)\u0003A1\u0001(\u0005\tIen\u0001\u0001\u0012\u0005!r\u0003CA\u0015-\u001b\u0005Q#\"A\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00055R#a\u0002(pi\"Lgn\u001a\t\u0003S=J!\u0001\r\u0016\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$e\u0011)1\u0007\u0001b\u0001O\t\u0019q*\u001e;\u0002\u0003\u0019,\u0012A\u000e\t\u0004S]J\u0014B\u0001\u001d+\u0005%1UO\\2uS>t\u0007\u0007\u0005\u0003*u\tb\u0014BA\u001e+\u0005%1UO\\2uS>t\u0017\u0007E\u0002>\u000bFr!AP\"\u000f\u0005}\u0012U\"\u0001!\u000b\u0005\u00053\u0013A\u0002\u001fs_>$h(C\u0001,\u0013\t!%&A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0019;%\u0001D%uKJ\f'\r\\3P]\u000e,'B\u0001#+\u0003\t1\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003\u00176\u0003B\u0001\u0014\u0001#c5\ta\u0002C\u00035\u0007\u0001\u0007a'\u0001\u0002j]V\t\u0001\u000bE\u0002 #\nJ!A\u0015\n\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0002pkR,\u0012A\u0016\t\u0004?]\u000b\u0014B\u0001-\u0013\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003\u0015\u0019\b.\u00199f+\u0005q\u0012AB:iCB,\u0007%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012a\u0018\t\u0003?\u0001L!!\u0019\n\u0003\u0015\u0005#HO]5ckR,7/A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u00013q%\u0011)wM[7\u0007\t\u0019\\\u0001\u0001\u001a\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u00033!L!!\u001b\u000e\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000e\u0004\"!G6\n\u00051T\"!C%o\u0011\u0006tG\r\\3s!\tIb.\u0003\u0002p5\tQq*\u001e;IC:$G.\u001a:\t\u000bE\\\u0001\u0019A0\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001\u001e\t\u0003kjl\u0011A\u001e\u0006\u0003ob\fA\u0001\\1oO*\t\u00110\u0001\u0003kCZ\f\u0017BA>w\u0005\u0019\u0019FO]5oO\"\u0012\u0001! \t\u0004}\u0006\rQ\"A@\u000b\u0007\u0005\u0005A#\u0001\u0006b]:|G/\u0019;j_:L1!!\u0002\u0000\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class StatefulMapConcat<In, Out>
extends GraphStage<FlowShape<In, Out>> {
    private final Function0<Function1<In, IterableOnce<Out>>> f;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;

    public Function0<Function1<In, IterableOnce<Out>>> f() {
        return this.f;
    }

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.statefulMapConcat().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> akka$stream$impl$fusing$StatefulMapConcat$$anon$$decider;
            private Iterator<Out> currentIterator;
            private Function1<In, IterableOnce<Out>> plainFun;
            private final ContextPropagation contextPropagation;
            private volatile boolean bitmap$0;
            private final /* synthetic */ StatefulMapConcat $outer;
            private Attributes inheritedAttributes$20;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$47 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.akka$stream$impl$fusing$StatefulMapConcat$$anon$$decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$20.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$20 = null;
                return this.akka$stream$impl$fusing$StatefulMapConcat$$anon$$decider;
            }

            public Function1<Throwable, Supervision.Directive> akka$stream$impl$fusing$StatefulMapConcat$$anon$$decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.akka$stream$impl$fusing$StatefulMapConcat$$anon$$decider;
            }

            private Iterator<Out> currentIterator() {
                return this.currentIterator;
            }

            private void currentIterator_$eq(Iterator<Out> x$1) {
                this.currentIterator = x$1;
            }

            private Function1<In, IterableOnce<Out>> plainFun() {
                return this.plainFun;
            }

            private void plainFun_$eq(Function1<In, IterableOnce<Out>> x$1) {
                this.plainFun = x$1;
            }

            private ContextPropagation contextPropagation() {
                return this.contextPropagation;
            }

            private boolean hasNext() {
                if (this.currentIterator() != null) {
                    return this.currentIterator().hasNext();
                }
                return false;
            }

            private void pushPull(boolean shouldResumeContext) {
                if (this.hasNext()) {
                    if (shouldResumeContext) {
                        this.contextPropagation().resumeContext();
                    }
                    this.push(this.$outer.out(), this.currentIterator().next());
                    if (this.hasNext()) {
                        this.contextPropagation().suspendContext();
                        return;
                    }
                    if (this.isClosed(this.$outer.in())) {
                        this.completeStage();
                        return;
                    }
                    return;
                }
                if (!this.isClosed(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
                this.completeStage();
            }

            private void onFinish() {
                if (!this.hasNext()) {
                    this.completeStage();
                    return;
                }
            }

            public void onPush() {
                try {
                    this.currentIterator_$eq(this.plainFun().apply(this.grab(this.$outer.in())).iterator());
                    this.pushPull(false);
                }
                catch (Throwable x$11) {
                    PartialFunction<Throwable, BoxedUnit> catchExpr$1 = this.handleException();
                    if (catchExpr$1.isDefinedAt(x$11)) {
                        catchExpr$1.apply(x$11);
                    }
                    throw x$11;
                }
            }

            public void onUpstreamFinish() {
                this.onFinish();
            }

            public void onPull() {
                try {
                    this.pushPull(true);
                }
                catch (Throwable x$12) {
                    PartialFunction<Throwable, BoxedUnit> catchExpr$2 = this.handleException();
                    if (catchExpr$2.isDefinedAt(x$12)) {
                        catchExpr$2.apply(x$12);
                    }
                    throw x$12;
                }
            }

            private PartialFunction<Throwable, BoxedUnit> handleException() {
                return new Serializable(this){
                    private static final long serialVersionUID = 0L;
                    private final /* synthetic */ $anon$47 $outer;

                    public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                        Option<Throwable> option;
                        A1 A1 = x1;
                        if (A1 != null && !(option = NonFatal$.MODULE$.unapply(A1)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.$outer.failStage(ex);
                            } else if (Supervision$Resume$.MODULE$.equals(directive)) {
                                if (this.$outer.isClosed(this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer().in())) {
                                    this.$outer.completeStage();
                                } else if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer().in())) {
                                    this.$outer.pull(this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer().in());
                                }
                            } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                                if (this.$outer.isClosed(this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer().in())) {
                                    this.$outer.completeStage();
                                } else {
                                    this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$restartState();
                                    if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer().in())) {
                                        this.$outer.pull(this.$outer.akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer().in());
                                    }
                                }
                            } else {
                                throw new MatchError(directive);
                            }
                            return (B1)BoxedUnit.UNIT;
                        }
                        return function1.apply(x1);
                    }

                    public final boolean isDefinedAt(Throwable x1) {
                        Option<Throwable> option;
                        Throwable throwable = x1;
                        return throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                    }
                };
            }

            public void akka$stream$impl$fusing$StatefulMapConcat$$anon$$restartState() {
                this.plainFun_$eq(this.$outer.f().apply());
                this.currentIterator_$eq(null);
            }

            public /* synthetic */ StatefulMapConcat akka$stream$impl$fusing$StatefulMapConcat$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$20 = inheritedAttributes$20;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.plainFun = $outer.f().apply();
                this.contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "StatefulMapConcat";
    }

    public StatefulMapConcat(Function0<Function1<In, IterableOnce<Out>>> f) {
        this.f = f;
        this.in = Inlet$.MODULE$.apply("StatefulMapConcat.in");
        this.out = Outlet$.MODULE$.apply("StatefulMapConcat.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
    }
}

