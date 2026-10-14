/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.event.LogMarker;
import akka.event.LogSource;
import akka.event.Logging$;
import akka.event.MarkerLoggingAdapter;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Materializer;
import akka.stream.Supervision;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.LogWithMarker$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\tmd!B\u0014)\u0005:\u0002\u0004\u0002C0\u0001\u0005+\u0007I\u0011\u00011\t\u0011%\u0004!\u0011#Q\u0001\n\u0005D\u0001B\u001b\u0001\u0003\u0016\u0004%\ta\u001b\u0005\tk\u0002\u0011\t\u0012)A\u0005Y\"Aa\u000f\u0001BK\u0002\u0013\u0005q\u000f\u0003\u0005z\u0001\tE\t\u0015!\u0003y\u0011!Q\bA!f\u0001\n\u0003Y\b\"CA\u0003\u0001\tE\t\u0015!\u0003}\u0011\u001d\t9\u0001\u0001C\u0001\u0003\u0013Aq!a\u0006\u0001\t\u0003\nI\u0002C\u0004\u0002*\u0001!\t%a\u000b\t\u0013\u0005\u0015\u0003!!A\u0005\u0002\u0005\u001d\u0003\"CA/\u0001E\u0005I\u0011AA0\u0011%\tI\bAI\u0001\n\u0003\tY\bC\u0005\u0002\u0004\u0002\t\n\u0011\"\u0001\u0002\u0006\"I\u0011Q\u0012\u0001\u0012\u0002\u0013\u0005\u0011q\u0012\u0005\n\u0003/\u0003\u0011\u0011!C!\u00033C\u0011\"a'\u0001\u0003\u0003%\t!!(\t\u0013\u0005\u0015\u0006!!A\u0005\u0002\u0005\u001d\u0006\"CAW\u0001\u0005\u0005I\u0011IAX\u0011%\ti\fAA\u0001\n\u0003\ty\fC\u0005\u0002J\u0002\t\t\u0011\"\u0011\u0002L\"I\u0011q\u001a\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u001b\u0005\n\u0003'\u0004\u0011\u0011!C!\u0003+<\u0001\"!:)\u0011\u0003q\u0013q\u001d\u0004\bO!B\tALAu\u0011\u001d\t9A\u0007C\u0001\u0003wD\u0011\"!@\u001b\u0005\u0004%)!a@\t\u0011\t\u001d!\u0004)A\u0007\u0005\u0003A\u0011B!\u0006\u001b\u0005\u0004%iAa\u0006\t\u0011\t}!\u0004)A\u0007\u00053A\u0011B!\t\u001b\u0005\u0004%i!!(\t\u0011\t\r\"\u0004)A\u0007\u0003?C\u0011B!\n\u001b\u0005\u0004%iAa\n\t\u0011\t]\"\u0004)A\u0007\u0005SA\u0011B!\u000f\u001b\u0003\u0003%\tIa\u000f\t\u0013\tE#$!A\u0005\u0002\nM\u0003\"\u0003B75\u0005\u0005I\u0011\u0002B8\u00055aunZ,ji\"l\u0015M]6fe*\u0011\u0011FK\u0001\u0007MV\u001c\u0018N\\4\u000b\u0005-b\u0013\u0001B5na2T!!\f\u0018\u0002\rM$(/Z1n\u0015\u0005y\u0013\u0001B1lW\u0006,\"!M$\u0014\t\u0001\u00114K\u0016\t\u0004g\t+eB\u0001\u001bA\u001d\t)tH\u0004\u00027}9\u0011q'\u0010\b\u0003qqj\u0011!\u000f\u0006\u0003um\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002_%\u0011QFL\u0005\u0003W1J!!\u000b\u0016\n\u0005\u0005C\u0013aC$sCBD7\u000b^1hKNL!a\u0011#\u0003-MKW\u000e\u001d7f\u0019&tW-\u0019:He\u0006\u0004\bn\u0015;bO\u0016T!!\u0011\u0015\u0011\u0005\u0019;E\u0002\u0001\u0003\u0006\u0011\u0002\u0011\r!\u0013\u0002\u0002)F\u0011!\n\u0015\t\u0003\u0017:k\u0011\u0001\u0014\u0006\u0002\u001b\u0006)1oY1mC&\u0011q\n\u0014\u0002\b\u001d>$\b.\u001b8h!\tY\u0015+\u0003\u0002S\u0019\n\u0019\u0011I\\=\u0011\u0005-#\u0016BA+M\u0005\u001d\u0001&o\u001c3vGR\u0004\"a\u0016/\u000f\u0005aSfB\u0001\u001dZ\u0013\u0005i\u0015BA.M\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u00180\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005mc\u0015\u0001\u00028b[\u0016,\u0012!\u0019\t\u0003E\u001at!a\u00193\u0011\u0005ab\u0015BA3M\u0003\u0019\u0001&/\u001a3fM&\u0011q\r\u001b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0015d\u0015!\u00028b[\u0016\u0004\u0013AB7be.,'/F\u0001m!\u0011YU.R8\n\u00059d%!\u0003$v]\u000e$\u0018n\u001c82!\t\u00018/D\u0001r\u0015\t\u0011h&A\u0003fm\u0016tG/\u0003\u0002uc\nIAj\\4NCJ\\WM]\u0001\b[\u0006\u00148.\u001a:!\u0003\u001d)\u0007\u0010\u001e:bGR,\u0012\u0001\u001f\t\u0005\u00176,\u0005+\u0001\u0005fqR\u0014\u0018m\u0019;!\u0003)awnZ!eCB$XM]\u000b\u0002yB\u00191*`@\n\u0005yd%AB(qi&|g\u000eE\u0002q\u0003\u0003I1!a\u0001r\u0005Qi\u0015M]6fe2{wmZ5oO\u0006#\u0017\r\u001d;fe\u0006YAn\\4BI\u0006\u0004H/\u001a:!\u0003\u0019a\u0014N\\5u}QQ\u00111BA\b\u0003#\t\u0019\"!\u0006\u0011\t\u00055\u0001!R\u0007\u0002Q!)q,\u0003a\u0001C\")!.\u0003a\u0001Y\")a/\u0003a\u0001q\")!0\u0003a\u0001y\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u001cA!\u0011QDA\u0014\u001b\t\tyB\u0003\u0003\u0002\"\u0005\r\u0012\u0001\u00027b]\u001eT!!!\n\u0002\t)\fg/Y\u0005\u0004O\u0006}\u0011aC2sK\u0006$X\rT8hS\u000e$B!!\f\u0002:A!\u0011qFA\u001b\u001b\t\t\tDC\u0002\u000241\nQa\u001d;bO\u0016LA!a\u000e\u00022\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0004\u0002<-\u0001\r!!\u0010\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\t\u0005}\u0012\u0011I\u0007\u0002Y%\u0019\u00111\t\u0017\u0003\u0015\u0005#HO]5ckR,7/\u0001\u0003d_BLX\u0003BA%\u0003\u001f\"\"\"a\u0013\u0002R\u0005M\u0013qKA.!\u0015\ti\u0001AA'!\r1\u0015q\n\u0003\u0006\u00112\u0011\r!\u0013\u0005\b?2\u0001\n\u00111\u0001b\u0011!QG\u0002%AA\u0002\u0005U\u0003#B&n\u0003\u001bz\u0007\u0002\u0003<\r!\u0003\u0005\r!!\u0017\u0011\u000b-k\u0017Q\n)\t\u000fid\u0001\u0013!a\u0001y\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BA1\u0003o*\"!a\u0019+\u0007\u0005\f)g\u000b\u0002\u0002hA!\u0011\u0011NA:\u001b\t\tYG\u0003\u0003\u0002n\u0005=\u0014!C;oG\",7m[3e\u0015\r\t\t\bT\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA;\u0003W\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015AUB1\u0001J\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*B!! \u0002\u0002V\u0011\u0011q\u0010\u0016\u0004Y\u0006\u0015D!\u0002%\u000f\u0005\u0004I\u0015AD2paf$C-\u001a4bk2$HeM\u000b\u0005\u0003\u000f\u000bY)\u0006\u0002\u0002\n*\u001a\u00010!\u001a\u0005\u000b!{!\u0019A%\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU!\u0011\u0011SAK+\t\t\u0019JK\u0002}\u0003K\"Q\u0001\u0013\tC\u0002%\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\u000e\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\ty\nE\u0002L\u0003CK1!a)M\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r\u0001\u0016\u0011\u0016\u0005\n\u0003W\u001b\u0012\u0011!a\u0001\u0003?\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAY!\u0015\t\u0019,!/Q\u001b\t\t)LC\u0002\u000282\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\tY,!.\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u0003\f9\rE\u0002L\u0003\u0007L1!!2M\u0005\u001d\u0011un\u001c7fC:D\u0001\"a+\u0016\u0003\u0003\u0005\r\u0001U\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002\u001c\u00055\u0007\"CAV-\u0005\u0005\t\u0019AAP\u0003!A\u0017m\u001d5D_\u0012,GCAAP\u0003\u0019)\u0017/^1mgR!\u0011\u0011YAl\u0011!\tY\u000bGA\u0001\u0002\u0004\u0001\u0006f\u0001\u0001\u0002\\B!\u0011Q\\Aq\u001b\t\tyNC\u0002\u0002r9JA!a9\u0002`\nY\u0011J\u001c;fe:\fG.\u00119j\u00035aunZ,ji\"l\u0015M]6feB\u0019\u0011Q\u0002\u000e\u0014\u000bi\tY/!=\u0011\u0007-\u000bi/C\u0002\u0002p2\u0013a!\u00118z%\u00164\u0007\u0003BAz\u0003sl!!!>\u000b\t\u0005]\u00181E\u0001\u0003S>L1!XA{)\t\t9/\u0001\tge>lW*\u0019;fe&\fG.\u001b>feV\u0011!\u0011\u0001\n\u0007\u0005\u0007\tYO!\u0003\u0007\r\t\u0015Q\u0004\u0001B\u0001\u00051a$/\u001a4j]\u0016lWM\u001c;?\u0003E1'o\\7NCR,'/[1mSj,'\u000f\t\t\u0006a\n-!qB\u0005\u0004\u0005\u001b\t(!\u0003'pON{WO]2f!\u0011\tyD!\u0005\n\u0007\tMAF\u0001\u0007NCR,'/[1mSj,'/A\tEK\u001a\fW\u000f\u001c;M_\u001e<WM\u001d(b[\u0016,\"A!\u0007\u0010\u0005\tm\u0011E\u0001B\u000f\u0003e\t7n[1/gR\u0014X-Y7/\u0019><w+\u001b;i\u001b\u0006\u00148.\u001a:\u0002%\u0011+g-Y;mi2{wmZ3s\u001d\u0006lW\rI\u0001\u0007\u001f\u001a4\u0017J\u001c;\u0002\u000f=3g-\u00138uA\u0005\u0001B)\u001a4bk2$Hj\\4MKZ,Gn]\u000b\u0003\u0005S\u0001BAa\u000b\u000329\u0019aG!\f\n\u0007\t=B&\u0001\u0006BiR\u0014\u0018NY;uKNLAAa\r\u00036\tIAj\\4MKZ,Gn\u001d\u0006\u0004\u0005_a\u0013!\u0005#fM\u0006,H\u000e\u001e'pO2+g/\u001a7tA\u0005)\u0011\r\u001d9msV!!Q\bB\"))\u0011yD!\u0012\u0003H\t-#q\n\t\u0006\u0003\u001b\u0001!\u0011\t\t\u0004\r\n\rC!\u0002%%\u0005\u0004I\u0005\"B0%\u0001\u0004\t\u0007B\u00026%\u0001\u0004\u0011I\u0005E\u0003L[\n\u0005s\u000e\u0003\u0004wI\u0001\u0007!Q\n\t\u0006\u00176\u0014\t\u0005\u0015\u0005\u0006u\u0012\u0002\r\u0001`\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\u0011)Fa\u0019\u0015\t\t]#q\r\t\u0005\u0017v\u0014I\u0006E\u0005L\u00057\n'q\fB3y&\u0019!Q\f'\u0003\rQ+\b\u000f\\35!\u0015YUN!\u0019p!\r1%1\r\u0003\u0006\u0011\u0016\u0012\r!\u0013\t\u0006\u00176\u0014\t\u0007\u0015\u0005\n\u0005S*\u0013\u0011!a\u0001\u0005W\n1\u0001\u001f\u00131!\u0015\ti\u0001\u0001B1\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011\t\b\u0005\u0003\u0002\u001e\tM\u0014\u0002\u0002B;\u0003?\u0011aa\u00142kK\u000e$\bf\u0001\u000e\u0002\\\"\u001a\u0011$a7")
@InternalApi
public final class LogWithMarker<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final String name;
    private final Function1<T, LogMarker> marker;
    private final Function1<T, Object> extract;
    private final Option<MarkerLoggingAdapter> logAdapter;

    public static <T> Option<Tuple4<String, Function1<T, LogMarker>, Function1<T, Object>, Option<MarkerLoggingAdapter>>> unapply(LogWithMarker<T> x$0) {
        return LogWithMarker$.MODULE$.unapply(x$0);
    }

    public static <T> LogWithMarker<T> apply(String name, Function1<T, LogMarker> marker, Function1<T, Object> extract, Option<MarkerLoggingAdapter> logAdapter) {
        return LogWithMarker$.MODULE$.apply(name, marker, extract, logAdapter);
    }

    public static LogSource<Materializer> fromMaterializer() {
        return LogWithMarker$.MODULE$.fromMaterializer();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String name() {
        return this.name;
    }

    public Function1<T, LogMarker> marker() {
        return this.marker;
    }

    public Function1<T, Object> extract() {
        return this.extract;
    }

    public Option<MarkerLoggingAdapter> logAdapter() {
        return this.logAdapter;
    }

    public String toString() {
        return "LogWithMarker";
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private Attributes.LogLevels logLevels;
            private MarkerLoggingAdapter log;
            private final /* synthetic */ LogWithMarker $outer;
            private final Attributes inheritedAttributes$16;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private Attributes.LogLevels logLevels() {
                return this.logLevels;
            }

            private void logLevels_$eq(Attributes.LogLevels x$1) {
                this.logLevels = x$1;
            }

            private MarkerLoggingAdapter log() {
                return this.log;
            }

            private void log_$eq(MarkerLoggingAdapter x$1) {
                this.log = x$1;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$16.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            public void preStart() {
                MarkerLoggingAdapter markerLoggingAdapter;
                this.logLevels_$eq(this.inheritedAttributes$16.get(LogWithMarker$.MODULE$.akka$stream$impl$fusing$LogWithMarker$$DefaultLogLevels(), ClassTag$.MODULE$.apply(Attributes.LogLevels.class)));
                Option<MarkerLoggingAdapter> option = this.$outer.logAdapter();
                if (option instanceof Some) {
                    MarkerLoggingAdapter l;
                    Some some = (Some)option;
                    markerLoggingAdapter = l = (MarkerLoggingAdapter)some.value();
                } else {
                    markerLoggingAdapter = Logging$.MODULE$.withMarker(this.materializer().system(), this.materializer(), LogWithMarker$.MODULE$.fromMaterializer());
                }
                this.log_$eq(markerLoggingAdapter);
            }

            public void onPush() {
                try {
                    T elem = this.grab(this.$outer.in());
                    if (this.isEnabled(this.logLevels().onElement())) {
                        this.log().log(this.$outer.marker().apply(elem), this.logLevels().onElement(), this.log().format("[{}] Element: {}", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.$outer.name(), this.$outer.extract().apply(elem)})));
                    }
                    this.push(this.$outer.out(), elem);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.failStage(ex);
                        } else {
                            this.pull(this.$outer.in());
                        }
                    }
                    throw throwable;
                }
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onUpstreamFailure(Throwable cause) {
                if (this.isEnabled(this.logLevels().onFailure())) {
                    int n = this.logLevels().onFailure();
                    if (Logging$.MODULE$.ErrorLevel() == n) {
                        this.log().error(cause, "[{}] Upstream failed.", (Object)this.$outer.name());
                    } else {
                        this.log().log(n, "[{}] Upstream failed, cause: {}: {}", this.$outer.name(), Logging$.MODULE$.simpleName(cause.getClass()), cause.getMessage());
                    }
                }
                InHandler.onUpstreamFailure$(this, cause);
            }

            public void onUpstreamFinish() {
                if (this.isEnabled(this.logLevels().onFinish())) {
                    this.log().log(this.logLevels().onFinish(), "[{}] Upstream finished.", (Object)this.$outer.name());
                }
                InHandler.onUpstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) {
                if (this.isEnabled(this.logLevels().onFinish())) {
                    this.log().log(this.logLevels().onFinish(), "[{}] Downstream finished, cause: {}: {}", this.$outer.name(), Logging$.MODULE$.simpleName(cause.getClass()), cause.getMessage());
                }
                OutHandler.onDownstreamFinish$(this, cause);
            }

            private boolean isEnabled(int l) {
                return l != LogWithMarker$.MODULE$.akka$stream$impl$fusing$LogWithMarker$$OffInt();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$16 = inheritedAttributes$16;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> LogWithMarker<T> copy(String name, Function1<T, LogMarker> marker, Function1<T, Object> extract, Option<MarkerLoggingAdapter> logAdapter) {
        return new LogWithMarker<T>(name, marker, extract, logAdapter);
    }

    public <T> String copy$default$1() {
        return this.name();
    }

    public <T> Function1<T, LogMarker> copy$default$2() {
        return this.marker();
    }

    public <T> Function1<T, Object> copy$default$3() {
        return this.extract();
    }

    public <T> Option<MarkerLoggingAdapter> copy$default$4() {
        return this.logAdapter();
    }

    @Override
    public String productPrefix() {
        return "LogWithMarker";
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
                return this.name();
            }
            case 1: {
                return this.marker();
            }
            case 2: {
                return this.extract();
            }
            case 3: {
                return this.logAdapter();
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
        return x$1 instanceof LogWithMarker;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "name";
            }
            case 1: {
                return "marker";
            }
            case 2: {
                return "extract";
            }
            case 3: {
                return "logAdapter";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof LogWithMarker)) return false;
        boolean bl = true;
        if (!bl) return false;
        LogWithMarker logWithMarker = (LogWithMarker)x$1;
        String string = this.name();
        String string2 = logWithMarker.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Function1<T, LogMarker> function1 = this.marker();
        Function1<T, LogMarker> function12 = logWithMarker.marker();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        Function1<T, Object> function13 = this.extract();
        Function1<T, Object> function14 = logWithMarker.extract();
        if (function13 == null) {
            if (function14 != null) {
                return false;
            }
        } else if (!function13.equals(function14)) return false;
        Option<MarkerLoggingAdapter> option = this.logAdapter();
        Option<MarkerLoggingAdapter> option2 = logWithMarker.logAdapter();
        if (option == null) {
            if (option2 == null) return true;
            return false;
        } else {
            if (!option.equals(option2)) return false;
            return true;
        }
    }

    public LogWithMarker(String name, Function1<T, LogMarker> marker, Function1<T, Object> extract, Option<MarkerLoggingAdapter> logAdapter) {
        this.name = name;
        this.marker = marker;
        this.extract = extract;
        this.logAdapter = logAdapter;
        Product.$init$(this);
    }
}

