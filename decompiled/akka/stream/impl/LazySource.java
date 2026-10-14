/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.NeverMaterializedException;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.LazySource$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Source;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function0;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005-qA\u0002\b\u0010\u0011\u0003\u0019RC\u0002\u0004\u0018\u001f!\u00051\u0003\u0007\u0005\u0006?\u0005!\t!\t\u0005\u0006E\u0005!\ta\t\u0004\u0006/=\u00111C\n\u0005\t\r\u0012\u0011\t\u0011)A\u0005\u000f\")q\u0004\u0002C\u0001!\"91\u000b\u0002b\u0001\n\u0003!\u0006B\u0002-\u0005A\u0003%Q\u000bC\u0004Z\t\t\u0007I\u0011\t.\t\rm#\u0001\u0015!\u0003/\u0011\u0015aF\u0001\"\u0015^\u0011\u0015\tG\u0001\"\u0011c\u0011\u0015YG\u0001\"\u0011m\u0003)a\u0015M_=T_V\u00148-\u001a\u0006\u0003!E\tA![7qY*\u0011!cE\u0001\u0007gR\u0014X-Y7\u000b\u0003Q\tA!Y6lCB\u0011a#A\u0007\u0002\u001f\tQA*\u0019>z'>,(oY3\u0014\u0005\u0005I\u0002C\u0001\u000e\u001e\u001b\u0005Y\"\"\u0001\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yY\"AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005)\u0012!B1qa2LXc\u0001\u0013~\u007fR\u0019Q%!\u0001\u0011\tY!AP`\u000b\u0004OQ\"5C\u0001\u0003)!\u0011ICFL\u001f\u000e\u0003)R!aK\t\u0002\u000bM$\u0018mZ3\n\u00055R#aH$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKB\u0019q\u0006\r\u001a\u000e\u0003EI!!M\t\u0003\u0017M{WO]2f'\"\f\u0007/\u001a\t\u0003gQb\u0001\u0001B\u00036\t\t\u0007aGA\u0001U#\t9$\b\u0005\u0002\u001bq%\u0011\u0011h\u0007\u0002\b\u001d>$\b.\u001b8h!\tQ2(\u0003\u0002=7\t\u0019\u0011I\\=\u0011\u0007y\n5)D\u0001@\u0015\t\u00015$\u0001\u0006d_:\u001cWO\u001d:f]RL!AQ \u0003\r\u0019+H/\u001e:f!\t\u0019D\tB\u0003F\t\t\u0007aGA\u0001N\u00035\u0019x.\u001e:dK\u001a\u000b7\r^8ssB\u0019!\u0004\u0013&\n\u0005%[\"!\u0003$v]\u000e$\u0018n\u001c81!\u0011YeJM\"\u000e\u00031S!!T\t\u0002\u0011M\u001c\u0017\r\\1eg2L!a\u0014'\u0003\rM{WO]2f)\t\t&\u000b\u0005\u0003\u0017\tI\u001a\u0005\"\u0002$\u0007\u0001\u00049\u0015aA8viV\tQ\u000bE\u00020-JJ!aV\t\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f\u001e\u0011\u0002\u000bMD\u0017\r]3\u0016\u00039\naa\u001d5ba\u0016\u0004\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\ta\f\u0005\u00020?&\u0011\u0001-\u0005\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aH2sK\u0006$X\rT8hS\u000e\fe\u000eZ'bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKR\u00111-\u001b\t\u00055\u00114W(\u0003\u0002f7\t1A+\u001e9mKJ\u0002\"!K4\n\u0005!T#aD$sCBD7\u000b^1hK2{w-[2\t\u000b)d\u0001\u0019\u00010\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!\u001c\t\u0003]Nl\u0011a\u001c\u0006\u0003aF\fA\u0001\\1oO*\t!/\u0001\u0003kCZ\f\u0017B\u0001;p\u0005\u0019\u0019FO]5oO\"\u0012AA\u001e\t\u0003ojl\u0011\u0001\u001f\u0006\u0003sN\t!\"\u00198o_R\fG/[8o\u0013\tY\bPA\u0006J]R,'O\\1m\u0003BL\u0007CA\u001a~\t\u0015)4A1\u00017!\t\u0019t\u0010B\u0003F\u0007\t\u0007a\u0007\u0003\u0004G\u0007\u0001\u0007\u00111\u0001\t\u00055!\u000b)\u0001\u0005\u0003L\u001drt\bFA\u0001wQ\t\u0001a\u000f")
@InternalApi
public final class LazySource<T, M>
extends GraphStageWithMaterializedValue<SourceShape<T>, Future<M>> {
    public final Function0<Source<T, M>> akka$stream$impl$LazySource$$sourceFactory;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public static <T, M> LazySource<T, M> apply(Function0<Source<T, M>> sourceFactory) {
        return LazySource$.MODULE$.apply(sourceFactory);
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.lazySource().and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$LazySource$$sourceFactory));
    }

    @Override
    public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise matPromise = Promise$.MODULE$.apply();
        OutHandler logic = new OutHandler(this, matPromise, inheritedAttributes){
            private final /* synthetic */ LazySource $outer;
            private final Promise matPromise$1;
            private final Attributes inheritedAttributes$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) {
                this.matPromise$1.failure(new NeverMaterializedException(cause));
                this.completeStage();
            }

            public void onPull() {
                Source<T, M> source2;
                try {
                    source2 = this.$outer.akka$stream$impl$LazySource$$sourceFactory.apply();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        this.matPromise$1.tryFailure(ex);
                        throw ex;
                    }
                    throw throwable;
                }
                Source<T, M> source3 = source2;
                GraphStageLogic.SubSinkInlet<T> subSink = new GraphStageLogic.SubSinkInlet<T>(this, "LazySource");
                subSink.pull();
                this.setHandler(this.$outer.out(), new OutHandler(this, subSink){
                    private final /* synthetic */ $anon$1 $outer;
                    private final GraphStageLogic.SubSinkInlet subSink$1;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.subSink$1.pull();
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.subSink$1.cancel(cause);
                        this.$outer.completeStage();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.subSink$1 = subSink$1;
                        OutHandler.$init$(this);
                    }
                });
                subSink.setHandler(new InHandler(this, subSink){
                    private final /* synthetic */ $anon$1 $outer;
                    private final GraphStageLogic.SubSinkInlet subSink$1;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$impl$LazySource$$anon$$$outer().out(), this.subSink$1.grab());
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.subSink$1 = subSink$1;
                        InHandler.$init$(this);
                    }
                });
                try {
                    Mat matVal = this.subFusingMaterializer().materialize(source3.toMat(subSink.sink(), Keep$.MODULE$.left()), this.inheritedAttributes$1);
                    this.matPromise$1.trySuccess(matVal);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable3 = throwable;
                    if (throwable3 != null && !(option = NonFatal$.MODULE$.unapply(throwable3)).isEmpty()) {
                        Throwable ex = option.get();
                        subSink.cancel();
                        this.failStage(ex);
                        this.matPromise$1.tryFailure(ex);
                    }
                    throw throwable;
                }
            }

            public void postStop() {
                if (!this.matPromise$1.isCompleted()) {
                    this.matPromise$1.tryFailure(new AbruptStageTerminationException(this));
                    return;
                }
            }

            public /* synthetic */ LazySource akka$stream$impl$LazySource$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.matPromise$1 = matPromise$1;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                this.setHandler($outer.out(), this);
            }
        };
        return new Tuple2<GraphStageLogic, Future<M>>((GraphStageLogic)((Object)logic), matPromise.future());
    }

    public String toString() {
        return "LazySource";
    }

    public LazySource(Function0<Source<T, M>> sourceFactory) {
        this.akka$stream$impl$LazySource$$sourceFactory = sourceFactory;
        this.out = Outlet$.MODULE$.apply("LazySource.out");
        this.shape = new SourceShape<T>(this.out());
    }
}

