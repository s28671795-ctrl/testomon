/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.IOOperationIncompleteException;
import akka.stream.IOResult;
import akka.stream.IOResult$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.InputStream;
import java.io.Serializable;
import scala.Function0;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005E4QAC\u0006\u0003#MA\u0001b\f\u0001\u0003\u0002\u0003\u0006I!\r\u0005\ty\u0001\u0011\t\u0011)A\u0005{!)\u0001\t\u0001C\u0001\u0003\"9a\t\u0001b\u0001\n\u00139\u0005BB&\u0001A\u0003%\u0001\nC\u0003M\u0001\u0011\u0005S\nC\u0003O\u0001\u0011Es\nC\u0003T\u0001\u0011\u0005C\u000bC\u0003^\u0001\u0011\u0005cLA\tJ]B,Ho\u0015;sK\u0006l7k\\;sG\u0016T!\u0001D\u0007\u0002\u0005%|'B\u0001\b\u0010\u0003\u0011IW\u000e\u001d7\u000b\u0005A\t\u0012AB:ue\u0016\fWNC\u0001\u0013\u0003\u0011\t7n[1\u0014\u0005\u0001!\u0002\u0003B\u000b\u00195\u0011j\u0011A\u0006\u0006\u0003/=\tQa\u001d;bO\u0016L!!\u0007\f\u0003?\u001d\u0013\u0018\r\u001d5Ti\u0006<WmV5uQ6\u000bG/\u001a:jC2L'0\u001a3WC2,X\rE\u0002\u001c9yi\u0011aD\u0005\u0003;=\u00111bU8ve\u000e,7\u000b[1qKB\u0011qDI\u0007\u0002A)\u0011\u0011%E\u0001\u0005kRLG.\u0003\u0002$A\tQ!)\u001f;f'R\u0014\u0018N\\4\u0011\u0007\u0015RC&D\u0001'\u0015\t9\u0003&\u0001\u0006d_:\u001cWO\u001d:f]RT\u0011!K\u0001\u0006g\u000e\fG.Y\u0005\u0003W\u0019\u0012aAR;ukJ,\u0007CA\u000e.\u0013\tqsB\u0001\u0005J\u001fJ+7/\u001e7u\u0003\u001d1\u0017m\u0019;pef\u001c\u0001\u0001E\u00023gUj\u0011\u0001K\u0005\u0003i!\u0012\u0011BR;oGRLwN\u001c\u0019\u0011\u0005YRT\"A\u001c\u000b\u00051A$\"A\u001d\u0002\t)\fg/Y\u0005\u0003w]\u00121\"\u00138qkR\u001cFO]3b[\u0006I1\r[;oWNK'0\u001a\t\u0003eyJ!a\u0010\u0015\u0003\u0007%sG/\u0001\u0004=S:LGO\u0010\u000b\u0004\u0005\u0012+\u0005CA\"\u0001\u001b\u0005Y\u0001\"B\u0018\u0004\u0001\u0004\t\u0004\"\u0002\u001f\u0004\u0001\u0004i\u0014aA8viV\t\u0001\nE\u0002\u001c\u0013zI!AS\b\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f\u001e\u0011\u0002\u000bMD\u0017\r]3\u0016\u0003i\t\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005\u0001\u0006CA\u000eR\u0013\t\u0011vB\u0001\u0006BiR\u0014\u0018NY;uKN\fqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\t)6\f\u0005\u00033-b#\u0013BA,)\u0005\u0019!V\u000f\u001d7feA\u0011Q#W\u0005\u00035Z\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u00069\"\u0001\r\u0001U\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\tq\f\u0005\u0002aO:\u0011\u0011-\u001a\t\u0003E\"j\u0011a\u0019\u0006\u0003IB\na\u0001\u0010:p_Rt\u0014B\u00014)\u0003\u0019\u0001&/\u001a3fM&\u0011\u0001.\u001b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0019D\u0003F\u0001\u0001l!\taw.D\u0001n\u0015\tq\u0017#\u0001\u0006b]:|G/\u0019;j_:L!\u0001]7\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class InputStreamSource
extends GraphStageWithMaterializedValue<SourceShape<ByteString>, Future<IOResult>> {
    public final Function0<InputStream> akka$stream$impl$io$InputStreamSource$$factory;
    public final int akka$stream$impl$io$InputStreamSource$$chunkSize;
    private final Outlet<ByteString> akka$stream$impl$io$InputStreamSource$$out;

    public Outlet<ByteString> akka$stream$impl$io$InputStreamSource$$out() {
        return this.akka$stream$impl$io$InputStreamSource$$out;
    }

    @Override
    public SourceShape<ByteString> shape() {
        return new SourceShape<ByteString>(this.akka$stream$impl$io$InputStreamSource$$out());
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.inputStreamSource();
    }

    @Override
    public Tuple2<GraphStageLogic, Future<IOResult>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise mat = Promise$.MODULE$.apply();
        OutHandler logic = new OutHandler(this, mat){
            private final byte[] buffer;
            private long readBytesTotal;
            private InputStream inputStream;
            private final /* synthetic */ InputStreamSource $outer;
            private final Promise mat$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private byte[] buffer() {
                return this.buffer;
            }

            private long readBytesTotal() {
                return this.readBytesTotal;
            }

            private void readBytesTotal_$eq(long x$1) {
                this.readBytesTotal = x$1;
            }

            private InputStream inputStream() {
                return this.inputStream;
            }

            private void inputStream_$eq(InputStream x$1) {
                this.inputStream = x$1;
            }

            private boolean isClosed() {
                return this.mat$1.isCompleted();
            }

            public Class<?> logSource() {
                return InputStreamSource.class;
            }

            public void preStart() {
                try {
                    this.inputStream_$eq(this.$outer.akka$stream$impl$io$InputStreamSource$$factory.apply());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.mat$1.failure(new IOOperationIncompleteException(0L, t2));
                        this.failStage(t2);
                    }
                    throw throwable;
                }
            }

            public void onPull() {
                try {
                    int n = this.inputStream().read(this.buffer());
                    switch (n) {
                        case -1: {
                            this.closeStage();
                            break;
                        }
                        default: {
                            this.readBytesTotal_$eq(this.readBytesTotal() + (long)n);
                            this.push(this.$outer.akka$stream$impl$io$InputStreamSource$$out(), ByteString$.MODULE$.fromArray(this.buffer(), 0, n));
                            break;
                        }
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.failStream(t2);
                        this.failStage(t2);
                    }
                    throw throwable;
                }
            }

            public void onDownstreamFinish(Throwable cause) {
                if (!this.isClosed()) {
                    this.closeInputStream();
                    Throwable throwable = cause;
                    if (throwable instanceof SubscriptionWithCancelException.NonFailureCancellation) {
                        this.mat$1.trySuccess(IOResult$.MODULE$.apply(this.readBytesTotal()));
                        return;
                    }
                    if (throwable != null) {
                        Throwable throwable2 = throwable;
                        this.mat$1.tryFailure(new IOOperationIncompleteException("Downstream failed before input stream reached end", this.readBytesTotal(), throwable2));
                        return;
                    }
                    throw new MatchError((Object)throwable);
                }
            }

            public void postStop() {
                if (!this.isClosed()) {
                    this.mat$1.tryFailure(new AbruptStageTerminationException(this));
                    return;
                }
            }

            private void closeStage() {
                this.closeInputStream();
                this.mat$1.trySuccess(IOResult$.MODULE$.apply(this.readBytesTotal()));
                this.completeStage();
            }

            private void failStream(Throwable reason) {
                this.closeInputStream();
                this.mat$1.tryFailure(new IOOperationIncompleteException(this.readBytesTotal(), reason));
            }

            private void closeInputStream() {
                try {
                    if (this.inputStream() != null) {
                        this.inputStream().close();
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        this.mat$1.tryFailure(new IOOperationIncompleteException(this.readBytesTotal(), ex));
                        this.failStage(ex);
                    }
                    throw throwable;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.mat$1 = mat$1;
                super($outer.shape());
                OutHandler.$init$(this);
                this.buffer = new byte[$outer.akka$stream$impl$io$InputStreamSource$$chunkSize];
                this.readBytesTotal = 0L;
                this.setHandler($outer.akka$stream$impl$io$InputStreamSource$$out(), this);
            }
        };
        return new Tuple2<GraphStageLogic, Future<IOResult>>((GraphStageLogic)((Object)logic), mat.future());
    }

    public String toString() {
        return "InputStreamSource";
    }

    public InputStreamSource(Function0<InputStream> factory2, int chunkSize) {
        this.akka$stream$impl$io$InputStreamSource$$factory = factory2;
        this.akka$stream$impl$io$InputStreamSource$$chunkSize = chunkSize;
        Predef$.MODULE$.require(chunkSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(28).append("chunkSize must be > 0 (was ").append($this.akka$stream$impl$io$InputStreamSource$$chunkSize).append(")").toString());
        this.akka$stream$impl$io$InputStreamSource$$out = Outlet$.MODULE$.apply("InputStreamSource.out");
    }
}

