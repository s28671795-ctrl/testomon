/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Supervision;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Filter$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005}f!\u0002\r\u001a\u0005~\t\u0003\u0002\u0003)\u0001\u0005+\u0007I\u0011A)\t\u0011a\u0003!\u0011#Q\u0001\nICQ!\u0017\u0001\u0005\u0002iCQA\u0018\u0001\u0005B}CQ\u0001\u001a\u0001\u0005B\u0015DQA\u001c\u0001\u0005B=Dq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0011\u0010C\u0005\u0002\u0002\u0001\t\n\u0011\"\u0001\u0002\u0004!I\u0011Q\u0004\u0001\u0002\u0002\u0013\u0005\u0013q\u0004\u0005\n\u0003_\u0001\u0011\u0011!C\u0001\u0003cA\u0011\"!\u000f\u0001\u0003\u0003%\t!a\u000f\t\u0013\u0005\u0005\u0003!!A\u0005B\u0005\r\u0003\"CA)\u0001\u0005\u0005I\u0011AA*\u0011%\t9\u0006AA\u0001\n\u0003\nI\u0006C\u0005\u0002^\u0001\t\t\u0011\"\u0011\u0002`!I\u0011\u0011\r\u0001\u0002\u0002\u0013\u0005\u00131M\u0004\u000b\u0003gJ\u0012\u0011!E\u0001?\u0005Ud!\u0003\r\u001a\u0003\u0003E\taHA<\u0011\u0019I&\u0003\"\u0001\u0002\n\"AAMEA\u0001\n\u000b\nY\tC\u0005\u0002\u000eJ\t\t\u0011\"!\u0002\u0010\"I\u0011Q\u0014\n\u0002\u0002\u0013\u0005\u0015q\u0014\u0005\n\u0003k\u0013\u0012\u0011!C\u0005\u0003o\u0013aAR5mi\u0016\u0014(B\u0001\u000e\u001c\u0003\u00191Wo]5oO*\u0011A$H\u0001\u0005S6\u0004HN\u0003\u0002\u001f?\u000511\u000f\u001e:fC6T\u0011\u0001I\u0001\u0005C.\\\u0017-\u0006\u0002#qM!\u0001a\t#H!\r!3G\u000e\b\u0003KEr!A\n\u0019\u000f\u0005\u001dzcB\u0001\u0015/\u001d\tIS&D\u0001+\u0015\tYC&\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005\u0001\u0013B\u0001\u0010 \u0013\taR$\u0003\u0002\u001b7%\u0011!'G\u0001\f\u000fJ\f\u0007\u000f[*uC\u001e,7/\u0003\u00025k\t12+[7qY\u0016d\u0015N\\3be\u001e\u0013\u0018\r\u001d5Ti\u0006<WM\u0003\u000233A\u0011q\u0007\u000f\u0007\u0001\t\u0015I\u0004A1\u0001;\u0005\u0005!\u0016CA\u001eB!\tat(D\u0001>\u0015\u0005q\u0014!B:dC2\f\u0017B\u0001!>\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u0010\"\n\u0005\rk$aA!osB\u0011A(R\u0005\u0003\rv\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002I\u001b:\u0011\u0011j\u0013\b\u0003S)K\u0011AP\u0005\u0003\u0019v\nq\u0001]1dW\u0006<W-\u0003\u0002O\u001f\na1+\u001a:jC2L'0\u00192mK*\u0011A*P\u0001\u0002aV\t!\u000b\u0005\u0003='Z*\u0016B\u0001+>\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002=-&\u0011q+\u0010\u0002\b\u0005>|G.Z1o\u0003\t\u0001\b%\u0001\u0004=S:LGO\u0010\u000b\u00037v\u00032\u0001\u0018\u00017\u001b\u0005I\u0002\"\u0002)\u0004\u0001\u0004\u0011\u0016!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\t\u0001\r\u0005\u0002bE6\tQ$\u0003\u0002d;\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A\u001a\t\u0003O.t!\u0001[5\u0011\u0005%j\u0014B\u00016>\u0003\u0019\u0001&/\u001a3fM&\u0011A.\u001c\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005)l\u0014aC2sK\u0006$X\rT8hS\u000e$\"\u0001\u001d<\u0011\u0005E$X\"\u0001:\u000b\u0005Ml\u0012!B:uC\u001e,\u0017BA;s\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B<\u0007\u0001\u0004\u0001\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001B2paf,\"A_?\u0015\u0005mt\bc\u0001/\u0001yB\u0011q' \u0003\u0006s\u001d\u0011\rA\u000f\u0005\b!\u001e\u0001\n\u00111\u0001\u0000!\u0011a4\u000b`+\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU!\u0011QAA\u000e+\t\t9AK\u0002S\u0003\u0013Y#!a\u0003\u0011\t\u00055\u0011qC\u0007\u0003\u0003\u001fQA!!\u0005\u0002\u0014\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003+i\u0014AC1o]>$\u0018\r^5p]&!\u0011\u0011DA\b\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006s!\u0011\rAO\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u0005\u0002\u0003BA\u0012\u0003[i!!!\n\u000b\t\u0005\u001d\u0012\u0011F\u0001\u0005Y\u0006twM\u0003\u0002\u0002,\u0005!!.\u0019<b\u0013\ra\u0017QE\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003g\u00012\u0001PA\u001b\u0013\r\t9$\u0010\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\u0003\u0006u\u0002\"CA \u0017\u0005\u0005\t\u0019AA\u001a\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\t\t\u0006\u0003\u000f\ni%Q\u0007\u0003\u0003\u0013R1!a\u0013>\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u001f\nIE\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGcA+\u0002V!A\u0011qH\u0007\u0002\u0002\u0003\u0007\u0011)\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u0011\u00037B\u0011\"a\u0010\u000f\u0003\u0003\u0005\r!a\r\u0002\u0011!\f7\u000f[\"pI\u0016$\"!a\r\u0002\r\u0015\fX/\u00197t)\r)\u0016Q\r\u0005\t\u0003\u007f\u0001\u0012\u0011!a\u0001\u0003\"\u001a\u0001!!\u001b\u0011\t\u0005-\u0014qN\u0007\u0003\u0003[R1!!\u0006 \u0013\u0011\t\t(!\u001c\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0007\r&dG/\u001a:\u0011\u0005q\u00132#\u0002\n\u0002z\u0005}\u0004c\u0001\u001f\u0002|%\u0019\u0011QP\u001f\u0003\r\u0005s\u0017PU3g!\u0011\t\t)a\"\u000e\u0005\u0005\r%\u0002BAC\u0003S\t!![8\n\u00079\u000b\u0019\t\u0006\u0002\u0002vQ\u0011\u0011\u0011E\u0001\u0006CB\u0004H._\u000b\u0005\u0003#\u000b9\n\u0006\u0003\u0002\u0014\u0006e\u0005\u0003\u0002/\u0001\u0003+\u00032aNAL\t\u0015ITC1\u0001;\u0011\u0019\u0001V\u00031\u0001\u0002\u001cB)AhUAK+\u00069QO\\1qa2LX\u0003BAQ\u0003[#B!a)\u00020B)A(!*\u0002*&\u0019\u0011qU\u001f\u0003\r=\u0003H/[8o!\u0015a4+a+V!\r9\u0014Q\u0016\u0003\u0006sY\u0011\rA\u000f\u0005\n\u0003c3\u0012\u0011!a\u0001\u0003g\u000b1\u0001\u001f\u00131!\u0011a\u0006!a+\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005e\u0006\u0003BA\u0012\u0003wKA!!0\u0002&\t1qJ\u00196fGR\u0004")
@InternalApi
public final class Filter<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final Function1<T, Object> p;

    public static <T> Option<Function1<T, Object>> unapply(Filter<T> x$0) {
        return Filter$.MODULE$.unapply(x$0);
    }

    public static <T> Filter<T> apply(Function1<T, Object> p) {
        return Filter$.MODULE$.apply(p);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Function1<T, Object> p() {
        return this.p;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.filter().and(Attributes$SourceLocation$.MODULE$.forLambda(this.p()));
    }

    public String toString() {
        return "Filter";
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private T buffer;
            private final ContextPropagation contextPropagation;
            private final /* synthetic */ Filter $outer;
            private final Attributes inheritedAttributes$2;

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$2.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            private T buffer() {
                return this.buffer;
            }

            private void buffer_$eq(T x$1) {
                this.buffer = x$1;
            }

            private ContextPropagation contextPropagation() {
                return this.contextPropagation;
            }

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                try {
                    T elem = this.grab(this.$outer.in());
                    if (BoxesRunTime.unboxToBoolean(this.$outer.p().apply(elem))) {
                        if (this.isAvailable(this.$outer.out())) {
                            this.push(this.$outer.out(), elem);
                            this.pull(this.$outer.in());
                        } else {
                            this.buffer_$eq(OptionVal$Some$.MODULE$.apply(elem));
                            this.contextPropagation().suspendContext();
                        }
                    } else {
                        this.pull(this.$outer.in());
                    }
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
                T t2 = this.buffer();
                T t3 = OptionVal$Some$.MODULE$.unapply(t2);
                if (!OptionVal$.MODULE$.isEmpty$extension(t3)) {
                    T value = OptionVal$.MODULE$.get$extension(t3);
                    this.contextPropagation().resumeContext();
                    this.push(this.$outer.out(), value);
                    this.buffer_$eq(OptionVal$.MODULE$.none());
                    if (!this.isClosed(this.$outer.in())) {
                        this.pull(this.$outer.in());
                        return;
                    }
                    this.completeStage();
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (OptionVal$.MODULE$.isEmpty$extension(this.buffer())) {
                    InHandler.onUpstreamFinish$(this);
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$2 = inheritedAttributes$2;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.buffer = OptionVal$.MODULE$.none();
                this.contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> Filter<T> copy(Function1<T, Object> p) {
        return new Filter<T>(p);
    }

    public <T> Function1<T, Object> copy$default$1() {
        return this.p();
    }

    @Override
    public String productPrefix() {
        return "Filter";
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
                return this.p();
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
        return x$1 instanceof Filter;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "p";
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
        if (!(object instanceof Filter)) return false;
        boolean bl = true;
        if (!bl) return false;
        Filter filter2 = (Filter)x$1;
        Function1<T, Object> function1 = this.p();
        Function1<T, Object> function12 = filter2.p();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public Filter(Function1<T, Object> p) {
        this.p = p;
        Product.$init$(this);
    }
}

