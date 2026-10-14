/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.event.LogSource;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Materializer;
import akka.stream.Supervision;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.Log$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\tEc!\u0002\u0013&\u0005.j\u0003\u0002\u0003/\u0001\u0005+\u0007I\u0011A/\t\u0011\u0019\u0004!\u0011#Q\u0001\nyC\u0001b\u001a\u0001\u0003\u0016\u0004%\t\u0001\u001b\u0005\tY\u0002\u0011\t\u0012)A\u0005S\"AQ\u000e\u0001BK\u0002\u0013\u0005a\u000e\u0003\u0005y\u0001\tE\t\u0015!\u0003p\u0011\u0015I\b\u0001\"\u0001{\u0011\u001d\t\t\u0001\u0001C!\u0003\u0007Aq!a\u0005\u0001\t\u0003\n)\u0002C\u0005\u00020\u0001\t\t\u0011\"\u0001\u00022!I\u00111\t\u0001\u0012\u0002\u0013\u0005\u0011Q\t\u0005\n\u0003?\u0002\u0011\u0013!C\u0001\u0003CB\u0011\"!\u001b\u0001#\u0003%\t!a\u001b\t\u0013\u0005M\u0004!!A\u0005B\u0005U\u0004\"CA<\u0001\u0005\u0005I\u0011AA=\u0011%\t\t\tAA\u0001\n\u0003\t\u0019\tC\u0005\u0002\n\u0002\t\t\u0011\"\u0011\u0002\f\"I\u0011\u0011\u0014\u0001\u0002\u0002\u0013\u0005\u00111\u0014\u0005\n\u0003K\u0003\u0011\u0011!C!\u0003OC\u0011\"a+\u0001\u0003\u0003%\t%!,\t\u0013\u0005=\u0006!!A\u0005B\u0005Ev\u0001CAaK!\u00051&a1\u0007\u000f\u0011*\u0003\u0012A\u0016\u0002F\"1\u0011p\u0006C\u0001\u0003/D\u0011\"!7\u0018\u0005\u0004%)!a7\t\u0011\u0005\rx\u0003)A\u0007\u0003;D\u0011\"!=\u0018\u0005\u0004%i!a=\t\u0011\u0005mx\u0003)A\u0007\u0003kD\u0011\"!@\u0018\u0005\u0004%i!!\u001f\t\u0011\u0005}x\u0003)A\u0007\u0003wB\u0011B!\u0001\u0018\u0005\u0004%iAa\u0001\t\u0011\tMq\u0003)A\u0007\u0005\u000bA\u0011B!\u0006\u0018\u0003\u0003%\tIa\u0006\t\u0013\t%r#!A\u0005\u0002\n-\u0002\"\u0003B\"/\u0005\u0005I\u0011\u0002B#\u0005\raun\u001a\u0006\u0003M\u001d\naAZ;tS:<'B\u0001\u0015*\u0003\u0011IW\u000e\u001d7\u000b\u0005)Z\u0013AB:ue\u0016\fWNC\u0001-\u0003\u0011\t7n[1\u0016\u00059\"5\u0003\u0002\u00010!N\u00032\u0001M C\u001d\t\tTH\u0004\u00023y9\u00111g\u000f\b\u0003iir!!N\u001d\u000e\u0003YR!a\u000e\u001d\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011\u0001L\u0005\u0003U-J!\u0001K\u0015\n\u0005\u0019:\u0013B\u0001 &\u0003-9%/\u00199i'R\fw-Z:\n\u0005\u0001\u000b%AF*j[BdW\rT5oK\u0006\u0014xI]1qQN#\u0018mZ3\u000b\u0005y*\u0003CA\"E\u0019\u0001!Q!\u0012\u0001C\u0002\u0019\u0013\u0011\u0001V\t\u0003\u000f6\u0003\"\u0001S&\u000e\u0003%S\u0011AS\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0019&\u0013qAT8uQ&tw\r\u0005\u0002I\u001d&\u0011q*\u0013\u0002\u0004\u0003:L\bC\u0001%R\u0013\t\u0011\u0016JA\u0004Qe>$Wo\u0019;\u0011\u0005QKfBA+X\u001d\t)d+C\u0001K\u0013\tA\u0016*A\u0004qC\u000e\\\u0017mZ3\n\u0005i[&\u0001D*fe&\fG.\u001b>bE2,'B\u0001-J\u0003\u0011q\u0017-\\3\u0016\u0003y\u0003\"aX2\u000f\u0005\u0001\f\u0007CA\u001bJ\u0013\t\u0011\u0017*\u0001\u0004Qe\u0016$WMZ\u0005\u0003I\u0016\u0014aa\u0015;sS:<'B\u00012J\u0003\u0015q\u0017-\\3!\u0003\u001d)\u0007\u0010\u001e:bGR,\u0012!\u001b\t\u0005\u0011*\u0014U*\u0003\u0002l\u0013\nIa)\u001e8di&|g.M\u0001\tKb$(/Y2uA\u0005QAn\\4BI\u0006\u0004H/\u001a:\u0016\u0003=\u00042\u0001\u00139s\u0013\t\t\u0018J\u0001\u0004PaRLwN\u001c\t\u0003gZl\u0011\u0001\u001e\u0006\u0003k.\nQ!\u001a<f]RL!a\u001e;\u0003\u001d1{wmZ5oO\u0006#\u0017\r\u001d;fe\u0006YAn\\4BI\u0006\u0004H/\u001a:!\u0003\u0019a\u0014N\\5u}Q!10 @\u0000!\ra\bAQ\u0007\u0002K!)Al\u0002a\u0001=\")qm\u0002a\u0001S\")Qn\u0002a\u0001_\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0006A!\u0011qAA\t\u001b\t\tIA\u0003\u0003\u0002\f\u00055\u0011\u0001\u00027b]\u001eT!!a\u0004\u0002\t)\fg/Y\u0005\u0004I\u0006%\u0011aC2sK\u0006$X\rT8hS\u000e$B!a\u0006\u0002$A!\u0011\u0011DA\u0010\u001b\t\tYBC\u0002\u0002\u001e%\nQa\u001d;bO\u0016LA!!\t\u0002\u001c\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0004\u0002&%\u0001\r!a\n\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\t\u0005%\u00121F\u0007\u0002S%\u0019\u0011QF\u0015\u0003\u0015\u0005#HO]5ckR,7/\u0001\u0003d_BLX\u0003BA\u001a\u0003s!\u0002\"!\u000e\u0002<\u0005u\u0012\u0011\t\t\u0005y\u0002\t9\u0004E\u0002D\u0003s!Q!\u0012\u0006C\u0002\u0019Cq\u0001\u0018\u0006\u0011\u0002\u0003\u0007a\f\u0003\u0005h\u0015A\u0005\t\u0019AA !\u0015A%.a\u000eN\u0011\u001di'\u0002%AA\u0002=\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0003\u0002H\u0005uSCAA%U\rq\u00161J\u0016\u0003\u0003\u001b\u0002B!a\u0014\u0002Z5\u0011\u0011\u0011\u000b\u0006\u0005\u0003'\n)&A\u0005v]\u000eDWmY6fI*\u0019\u0011qK%\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\\\u0005E#!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)Qi\u0003b\u0001\r\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BA2\u0003O*\"!!\u001a+\u0007%\fY\u0005B\u0003F\u0019\t\u0007a)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\t\u00055\u0014\u0011O\u000b\u0003\u0003_R3a\\A&\t\u0015)UB1\u0001G\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011QA\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003w\u00022\u0001SA?\u0013\r\ty(\u0013\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\u001b\u0006\u0015\u0005\"CAD!\u0005\u0005\t\u0019AA>\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\u0012\t\u0006\u0003\u001f\u000b)*T\u0007\u0003\u0003#S1!a%J\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003/\u000b\tJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAO\u0003G\u00032\u0001SAP\u0013\r\t\t+\u0013\u0002\b\u0005>|G.Z1o\u0011!\t9IEA\u0001\u0002\u0004i\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u0002\u0002*\"I\u0011qQ\n\u0002\u0002\u0003\u0007\u00111P\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111P\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005u\u00151\u0017\u0005\t\u0003\u000f+\u0012\u0011!a\u0001\u001b\"\u001a\u0001!a.\u0011\t\u0005e\u0016QX\u0007\u0003\u0003wS1!a\u0016,\u0013\u0011\ty,a/\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0004\u0019><\u0007C\u0001?\u0018'\u00159\u0012qYAg!\rA\u0015\u0011Z\u0005\u0004\u0003\u0017L%AB!osJ+g\r\u0005\u0003\u0002P\u0006UWBAAi\u0015\u0011\t\u0019.!\u0004\u0002\u0005%|\u0017b\u0001.\u0002RR\u0011\u00111Y\u0001\u0011MJ|W.T1uKJL\u0017\r\\5{KJ,\"!!8\u0013\r\u0005}\u0017qYAs\r\u0019\t\tO\u0007\u0001\u0002^\naAH]3gS:,W.\u001a8u}\u0005\tbM]8n\u001b\u0006$XM]5bY&TXM\u001d\u0011\u0011\u000bM\f9/a;\n\u0007\u0005%HOA\u0005M_\u001e\u001cv.\u001e:dKB!\u0011\u0011FAw\u0013\r\ty/\u000b\u0002\r\u001b\u0006$XM]5bY&TXM]\u0001\u0012\t\u00164\u0017-\u001e7u\u0019><w-\u001a:OC6,WCAA{\u001f\t\t90\t\u0002\u0002z\u0006y\u0011m[6b]M$(/Z1n]1{w-\u0001\nEK\u001a\fW\u000f\u001c;M_\u001e<WM\u001d(b[\u0016\u0004\u0013AB(gM&sG/A\u0004PM\u001aLe\u000e\u001e\u0011\u0002!\u0011+g-Y;mi2{w\rT3wK2\u001cXC\u0001B\u0003!\u0011\u00119A!\u0004\u000f\u0007M\u0012I!C\u0002\u0003\f%\n!\"\u0011;ue&\u0014W\u000f^3t\u0013\u0011\u0011yA!\u0005\u0003\u00131{w\rT3wK2\u001c(b\u0001B\u0006S\u0005\tB)\u001a4bk2$Hj\\4MKZ,Gn\u001d\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\te!q\u0004\u000b\t\u00057\u0011\tCa\t\u0003(A!A\u0010\u0001B\u000f!\r\u0019%q\u0004\u0003\u0006\u000b\u0006\u0012\rA\u0012\u0005\u00069\u0006\u0002\rA\u0018\u0005\u0007O\u0006\u0002\rA!\n\u0011\u000b!S'QD'\t\u000b5\f\u0003\u0019A8\u0002\u000fUt\u0017\r\u001d9msV!!Q\u0006B\u001e)\u0011\u0011yC!\u0010\u0011\t!\u0003(\u0011\u0007\t\b\u0011\nMbLa\u000ep\u0013\r\u0011)$\u0013\u0002\u0007)V\u0004H.Z\u001a\u0011\u000b!S'\u0011H'\u0011\u0007\r\u0013Y\u0004B\u0003FE\t\u0007a\tC\u0005\u0003@\t\n\t\u00111\u0001\u0003B\u0005\u0019\u0001\u0010\n\u0019\u0011\tq\u0004!\u0011H\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005\u000f\u0002B!a\u0002\u0003J%!!1JA\u0005\u0005\u0019y%M[3di\"\u001aq#a.)\u0007Y\t9\f")
@InternalApi
public final class Log<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final String name;
    private final Function1<T, Object> extract;
    private final Option<LoggingAdapter> logAdapter;

    public static <T> Option<Tuple3<String, Function1<T, Object>, Option<LoggingAdapter>>> unapply(Log<T> x$0) {
        return Log$.MODULE$.unapply(x$0);
    }

    public static <T> Log<T> apply(String name, Function1<T, Object> extract, Option<LoggingAdapter> logAdapter) {
        return Log$.MODULE$.apply(name, extract, logAdapter);
    }

    public static LogSource<Materializer> fromMaterializer() {
        return Log$.MODULE$.fromMaterializer();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String name() {
        return this.name;
    }

    public Function1<T, Object> extract() {
        return this.extract;
    }

    public Option<LoggingAdapter> logAdapter() {
        return this.logAdapter;
    }

    public String toString() {
        return "Log";
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private Attributes.LogLevels logLevels;
            private LoggingAdapter log;
            private final /* synthetic */ Log $outer;
            private final Attributes inheritedAttributes$15;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private Attributes.LogLevels logLevels() {
                return this.logLevels;
            }

            private void logLevels_$eq(Attributes.LogLevels x$1) {
                this.logLevels = x$1;
            }

            private LoggingAdapter log() {
                return this.log;
            }

            private void log_$eq(LoggingAdapter x$1) {
                this.log = x$1;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$15.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            public void preStart() {
                LoggingAdapter loggingAdapter;
                this.logLevels_$eq(this.inheritedAttributes$15.get(Log$.MODULE$.akka$stream$impl$fusing$Log$$DefaultLogLevels(), ClassTag$.MODULE$.apply(Attributes.LogLevels.class)));
                Option<LoggingAdapter> option = this.$outer.logAdapter();
                if (option instanceof Some) {
                    LoggingAdapter l;
                    Some some = (Some)option;
                    loggingAdapter = l = (LoggingAdapter)some.value();
                } else {
                    loggingAdapter = Logging$.MODULE$.apply(this.materializer().system(), this.materializer(), Log$.MODULE$.fromMaterializer());
                }
                this.log_$eq(loggingAdapter);
            }

            public void onPush() {
                try {
                    T elem = this.grab(this.$outer.in());
                    if (this.isEnabled(this.logLevels().onElement())) {
                        this.log().log(this.logLevels().onElement(), "[{}] Element: {}", this.$outer.name(), this.$outer.extract().apply(elem));
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
                    this.log().log(this.logLevels().onFinish(), "[{}] Upstream finished.", this.$outer.name());
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
                return l != Log$.MODULE$.akka$stream$impl$fusing$Log$$OffInt();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$15 = inheritedAttributes$15;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> Log<T> copy(String name, Function1<T, Object> extract, Option<LoggingAdapter> logAdapter) {
        return new Log<T>(name, extract, logAdapter);
    }

    public <T> String copy$default$1() {
        return this.name();
    }

    public <T> Function1<T, Object> copy$default$2() {
        return this.extract();
    }

    public <T> Option<LoggingAdapter> copy$default$3() {
        return this.logAdapter();
    }

    @Override
    public String productPrefix() {
        return "Log";
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
                return this.name();
            }
            case 1: {
                return this.extract();
            }
            case 2: {
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
        return x$1 instanceof Log;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "name";
            }
            case 1: {
                return "extract";
            }
            case 2: {
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
        if (!(object instanceof Log)) return false;
        boolean bl = true;
        if (!bl) return false;
        Log log = (Log)x$1;
        String string = this.name();
        String string2 = log.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Function1<T, Object> function1 = this.extract();
        Function1<T, Object> function12 = log.extract();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        Option<LoggingAdapter> option = this.logAdapter();
        Option<LoggingAdapter> option2 = log.logAdapter();
        if (option == null) {
            if (option2 == null) return true;
            return false;
        } else {
            if (!option.equals(option2)) return false;
            return true;
        }
    }

    public Log(String name, Function1<T, Object> extract, Option<LoggingAdapter> logAdapter) {
        this.name = name;
        this.extract = extract;
        this.logAdapter = logAdapter;
        Product.$init$(this);
    }
}

