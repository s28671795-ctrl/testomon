/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.UnfoldResourceSource$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005Q4Q\u0001D\u0007\u0003#MA\u0001B\f\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\tk\u0001\u0011\t\u0011)A\u0005m!AA\b\u0001B\u0001B\u0003%Q\bC\u0003B\u0001\u0011\u0005!\tC\u0004I\u0001\t\u0007I\u0011A%\t\r5\u0003\u0001\u0015!\u0003K\u0011\u001dq\u0005A1A\u0005B=Ca\u0001\u0015\u0001!\u0002\u0013Y\u0002\"B)\u0001\t\u0003\u0012\u0006\"\u0002,\u0001\t\u00039\u0006\"B2\u0001\t\u0003\"'\u0001F+oM>dGMU3t_V\u00148-Z*pkJ\u001cWM\u0003\u0002\u000f\u001f\u0005!\u0011.\u001c9m\u0015\t\u0001\u0012#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002%\u0005!\u0011m[6b+\r!2'I\n\u0003\u0001U\u00012AF\r\u001c\u001b\u00059\"B\u0001\r\u0010\u0003\u0015\u0019H/Y4f\u0013\tQrC\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u00042\u0001H\u000f \u001b\u0005y\u0011B\u0001\u0010\u0010\u0005-\u0019v.\u001e:dKNC\u0017\r]3\u0011\u0005\u0001\nC\u0002\u0001\u0003\u0006E\u0001\u0011\r\u0001\n\u0002\u0002)\u000e\u0001\u0011CA\u0013,!\t1\u0013&D\u0001(\u0015\u0005A\u0013!B:dC2\f\u0017B\u0001\u0016(\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\n\u0017\n\u00055:#aA!os\u000611M]3bi\u0016\u00042A\n\u00193\u0013\t\ttEA\u0005Gk:\u001cG/[8oaA\u0011\u0001e\r\u0003\u0006i\u0001\u0011\r\u0001\n\u0002\u0002%\u0006A!/Z1e\t\u0006$\u0018\r\u0005\u0003'oIJ\u0014B\u0001\u001d(\u0005%1UO\\2uS>t\u0017\u0007E\u0002'u}I!aO\u0014\u0003\r=\u0003H/[8o\u0003\u0015\u0019Gn\\:f!\u00111sG\r \u0011\u0005\u0019z\u0014B\u0001!(\u0005\u0011)f.\u001b;\u0002\rqJg.\u001b;?)\u0011\u0019UIR$\u0011\t\u0011\u0003!gH\u0007\u0002\u001b!)a\u0006\u0002a\u0001_!)Q\u0007\u0002a\u0001m!)A\b\u0002a\u0001{\u0005\u0019q.\u001e;\u0016\u0003)\u00032\u0001H& \u0013\tauB\u0001\u0004PkRdW\r^\u0001\u0005_V$\b%A\u0003tQ\u0006\u0004X-F\u0001\u001c\u0003\u0019\u0019\b.\u00199fA\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003M\u0003\"\u0001\b+\n\u0005U{!AC!uiJL'-\u001e;fg\u0006Y1M]3bi\u0016dunZ5d)\tA\u0016ME\u0002Z7z3AA\u0017\u0001\u00011\naAH]3gS:,W.\u001a8u}A\u0011a\u0003X\u0005\u0003;^\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\t\u0003-}K!\u0001Y\f\u0003\u0015=+H\u000fS1oI2,'\u000fC\u0003c\u0015\u0001\u00071+A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\u0005)\u0007C\u00014l\u001b\u00059'B\u00015j\u0003\u0011a\u0017M\\4\u000b\u0003)\fAA[1wC&\u0011An\u001a\u0002\u0007'R\u0014\u0018N\\4)\u0005\u0001q\u0007CA8s\u001b\u0005\u0001(BA9\u0012\u0003)\tgN\\8uCRLwN\\\u0005\u0003gB\u00141\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class UnfoldResourceSource<R, T>
extends GraphStage<SourceShape<T>> {
    public final Function0<R> akka$stream$impl$UnfoldResourceSource$$create;
    public final Function1<R, Option<T>> akka$stream$impl$UnfoldResourceSource$$readData;
    public final Function1<R, BoxedUnit> akka$stream$impl$UnfoldResourceSource$$close;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.unfoldResourceSource().and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$UnfoldResourceSource$$create));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private boolean open;
            private R resource;
            private volatile boolean bitmap$0;
            private final /* synthetic */ UnfoldResourceSource $outer;
            private Attributes inheritedAttributes$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$1 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            private boolean open() {
                return this.open;
            }

            private void open_$eq(boolean x$1) {
                this.open = x$1;
            }

            private R resource() {
                return this.resource;
            }

            private void resource_$eq(R x$1) {
                this.resource = x$1;
            }

            public void preStart() {
                this.resource_$eq(this.$outer.akka$stream$impl$UnfoldResourceSource$$create.apply());
                this.open_$eq(true);
            }

            public final void onPull() {
                boolean resumingMode;
                do {
                    resumingMode = false;
                    try {
                        Option<T> option = this.$outer.akka$stream$impl$UnfoldResourceSource$$readData.apply(this.resource());
                        if (option instanceof Some) {
                            Some some = (Some)option;
                            A data = some.value();
                            this.push(this.$outer.out(), data);
                            continue;
                        }
                        if (None$.MODULE$.equals(option)) {
                            this.closeStage();
                            continue;
                        }
                        throw new MatchError(option);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.open_$eq(false);
                                this.$outer.akka$stream$impl$UnfoldResourceSource$$close.apply(this.resource());
                                this.failStage(ex);
                                continue;
                            }
                            if (Supervision$Restart$.MODULE$.equals(directive)) {
                                this.restartState();
                                resumingMode = true;
                                continue;
                            }
                            if (Supervision$Resume$.MODULE$.equals(directive)) {
                                resumingMode = true;
                                continue;
                            }
                            throw new MatchError(directive);
                        }
                        throw throwable;
                    }
                } while (resumingMode);
            }

            public void onDownstreamFinish(Throwable cause) {
                this.closeStage();
            }

            private void restartState() {
                this.open_$eq(false);
                this.$outer.akka$stream$impl$UnfoldResourceSource$$close.apply(this.resource());
                this.resource_$eq(this.$outer.akka$stream$impl$UnfoldResourceSource$$create.apply());
                this.open_$eq(true);
            }

            private void closeStage() {
                try {
                    this.$outer.akka$stream$impl$UnfoldResourceSource$$close.apply(this.resource());
                    this.open_$eq(false);
                    this.completeStage();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        this.failStage(ex);
                    }
                    throw throwable;
                }
            }

            public void postStop() {
                if (this.open()) {
                    this.$outer.akka$stream$impl$UnfoldResourceSource$$close.apply(this.resource());
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                this.open = false;
                this.setHandler($outer.out(), this);
            }
        };
    }

    public String toString() {
        return "UnfoldResourceSource";
    }

    public UnfoldResourceSource(Function0<R> create, Function1<R, Option<T>> readData, Function1<R, BoxedUnit> close) {
        this.akka$stream$impl$UnfoldResourceSource$$create = create;
        this.akka$stream$impl$UnfoldResourceSource$$readData = readData;
        this.akka$stream$impl$UnfoldResourceSource$$close = close;
        this.out = Outlet$.MODULE$.apply("UnfoldResourceSource.out");
        this.shape = new SourceShape<T>(this.out());
    }
}

