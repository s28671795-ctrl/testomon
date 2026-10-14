/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.StreamLimitReachedException;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.LimitWeighted$;
import akka.stream.impl.fusing.SupervisedGraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%h!B\u000e\u001d\u0005\n\"\u0003\u0002C*\u0001\u0005+\u0007I\u0011\u0001+\t\u0011a\u0003!\u0011#Q\u0001\nUC\u0001\"\u0017\u0001\u0003\u0016\u0004%\tA\u0017\u0005\t=\u0002\u0011\t\u0012)A\u00057\")q\f\u0001C\u0001A\")Q\r\u0001C!M\")1\u000e\u0001C\u0001Y\")a\u0010\u0001C!\u007f\"I\u0011\u0011\u0003\u0001\u0002\u0002\u0013\u0005\u00111\u0003\u0005\n\u0003G\u0001\u0011\u0013!C\u0001\u0003KA\u0011\"a\u0010\u0001#\u0003%\t!!\u0011\t\u0013\u0005%\u0003!!A\u0005B\u0005-\u0003\"CA'\u0001\u0005\u0005I\u0011AA(\u0011%\t9\u0006AA\u0001\n\u0003\tI\u0006C\u0005\u0002`\u0001\t\t\u0011\"\u0011\u0002b!I\u0011q\u000e\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u000f\u0005\n\u0003w\u0002\u0011\u0011!C!\u0003{B\u0011\"!!\u0001\u0003\u0003%\t%a!\t\u0013\u0005\u0015\u0005!!A\u0005B\u0005\u001duACAL9\u0005\u0005\t\u0012\u0001\u0012\u0002\u001a\u001aI1\u0004HA\u0001\u0012\u0003\u0011\u00131\u0014\u0005\u0007?V!\t!!,\t\u000fy,\u0012\u0011!C#\u007f\"I\u0011qV\u000b\u0002\u0002\u0013\u0005\u0015\u0011\u0017\u0005\n\u0003\u0003,\u0012\u0011!CA\u0003\u0007D\u0011\"a8\u0016\u0003\u0003%I!!9\u0003\u001b1KW.\u001b;XK&<\u0007\u000e^3e\u0015\tib$\u0001\u0004gkNLgn\u001a\u0006\u0003?\u0001\nA![7qY*\u0011\u0011EI\u0001\u0007gR\u0014X-Y7\u000b\u0003\r\nA!Y6lCV\u0011QeO\n\u0005\u0001\u0019:%\nE\u0002(mer!\u0001\u000b\u001b\u000f\u0005%\u001adB\u0001\u00163\u001d\tY\u0013G\u0004\u0002-a5\tQF\u0003\u0002/_\u00051AH]8piz\u001a\u0001!C\u0001$\u0013\t\t#%\u0003\u0002 A%\u0011QDH\u0005\u0003kq\t1b\u0012:ba\"\u001cF/Y4fg&\u0011q\u0007\u000f\u0002\u0017'&l\u0007\u000f\\3MS:,\u0017M]$sCBD7\u000b^1hK*\u0011Q\u0007\b\t\u0003umb\u0001\u0001B\u0003=\u0001\t\u0007QHA\u0001U#\tqD\t\u0005\u0002@\u00056\t\u0001IC\u0001B\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0005IA\u0004O_RD\u0017N\\4\u0011\u0005}*\u0015B\u0001$A\u0005\r\te.\u001f\t\u0003\u007f!K!!\u0013!\u0003\u000fA\u0013x\u000eZ;diB\u00111\n\u0015\b\u0003\u0019:s!\u0001L'\n\u0003\u0005K!a\u0014!\u0002\u000fA\f7m[1hK&\u0011\u0011K\u0015\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u001f\u0002\u000b\u0011A\\\u000b\u0002+B\u0011qHV\u0005\u0003/\u0002\u0013A\u0001T8oO\u0006\u0011a\u000eI\u0001\u0007G>\u001cHO\u00128\u0016\u0003m\u0003Ba\u0010/:+&\u0011Q\f\u0011\u0002\n\rVt7\r^5p]F\nqaY8ti\u001as\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004C\u000e$\u0007c\u00012\u0001s5\tA\u0004C\u0003T\u000b\u0001\u0007Q\u000bC\u0003Z\u000b\u0001\u00071,A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012a\u001a\t\u0003Q&l\u0011\u0001I\u0005\u0003U\u0002\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003-\u0019'/Z1uK2{w-[2\u0015\u00055d(\u0003\u00028qgf4Aa\\\u0004\u0001[\naAH]3gS:,W.\u001a8u}A\u0011!-]\u0005\u0003er\u0011\u0011dU;qKJ4\u0018n]3e\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jGB\u0011Ao^\u0007\u0002k*\u0011a\u000fI\u0001\u0006gR\fw-Z\u0005\u0003qV\u0014\u0011\"\u00138IC:$G.\u001a:\u0011\u0005QT\u0018BA>v\u0005)yU\u000f\u001e%b]\u0012dWM\u001d\u0005\u0006{\u001e\u0001\raZ\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\u0011\u0011\u0011\u0001\t\u0005\u0003\u0007\ti!\u0004\u0002\u0002\u0006)!\u0011qAA\u0005\u0003\u0011a\u0017M\\4\u000b\u0005\u0005-\u0011\u0001\u00026bm\u0006LA!a\u0004\u0002\u0006\t11\u000b\u001e:j]\u001e\fAaY8qsV!\u0011QCA\u000e)\u0019\t9\"!\b\u0002 A!!\rAA\r!\rQ\u00141\u0004\u0003\u0006y%\u0011\r!\u0010\u0005\b'&\u0001\n\u00111\u0001V\u0011!I\u0016\u0002%AA\u0002\u0005\u0005\u0002#B ]\u00033)\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0005\u0003O\ti$\u0006\u0002\u0002*)\u001aQ+a\u000b,\u0005\u00055\u0002\u0003BA\u0018\u0003si!!!\r\u000b\t\u0005M\u0012QG\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u000eA\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003w\t\tDA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q\u0001\u0010\u0006C\u0002u\nabY8qs\u0012\"WMZ1vYR$#'\u0006\u0003\u0002D\u0005\u001dSCAA#U\rY\u00161\u0006\u0003\u0006y-\u0011\r!P\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u0005\u0011\u0001\u00049s_\u0012,8\r^!sSRLXCAA)!\ry\u00141K\u0005\u0004\u0003+\u0002%aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001#\u0002\\!I\u0011Q\f\b\u0002\u0002\u0003\u0007\u0011\u0011K\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\r\u0004#BA3\u0003W\"UBAA4\u0015\r\tI\u0007Q\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA7\u0003O\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u00111OA=!\ry\u0014QO\u0005\u0004\u0003o\u0002%a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003;\u0002\u0012\u0011!a\u0001\t\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t\t!a \t\u0013\u0005u\u0013#!AA\u0002\u0005E\u0013\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005E\u0013AB3rk\u0006d7\u000f\u0006\u0003\u0002t\u0005%\u0005\u0002CA/'\u0005\u0005\t\u0019\u0001#)\u0007\u0001\ti\t\u0005\u0003\u0002\u0010\u0006MUBAAI\u0015\r\t9DI\u0005\u0005\u0003+\u000b\tJA\u0006J]R,'O\\1m\u0003BL\u0017!\u0004'j[&$x+Z5hQR,G\r\u0005\u0002c+M)Q#!(\u0002$B\u0019q(a(\n\u0007\u0005\u0005\u0006I\u0001\u0004B]f\u0014VM\u001a\t\u0005\u0003K\u000bY+\u0004\u0002\u0002(*!\u0011\u0011VA\u0005\u0003\tIw.C\u0002R\u0003O#\"!!'\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u0005M\u0016\u0011\u0018\u000b\u0007\u0003k\u000bY,!0\u0011\t\t\u0004\u0011q\u0017\t\u0004u\u0005eF!\u0002\u001f\u0019\u0005\u0004i\u0004\"B*\u0019\u0001\u0004)\u0006BB-\u0019\u0001\u0004\ty\fE\u0003@9\u0006]V+A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\u0005\u0015\u0017q\u001b\u000b\u0005\u0003\u000f\fI\u000eE\u0003@\u0003\u0013\fi-C\u0002\u0002L\u0002\u0013aa\u00149uS>t\u0007CB \u0002PV\u000b\u0019.C\u0002\u0002R\u0002\u0013a\u0001V;qY\u0016\u0014\u0004#B ]\u0003+,\u0006c\u0001\u001e\u0002X\u0012)A(\u0007b\u0001{!I\u00111\\\r\u0002\u0002\u0003\u0007\u0011Q\\\u0001\u0004q\u0012\u0002\u0004\u0003\u00022\u0001\u0003+\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a9\u0011\t\u0005\r\u0011Q]\u0005\u0005\u0003O\f)A\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class LimitWeighted<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final long n;
    private final Function1<T, Object> costFn;

    public static <T> Option<Tuple2<Object, Function1<T, Object>>> unapply(LimitWeighted<T> x$0) {
        return LimitWeighted$.MODULE$.unapply(x$0);
    }

    public static <T> LimitWeighted<T> apply(long n, Function1<T, Object> costFn) {
        return LimitWeighted$.MODULE$.apply(n, costFn);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long n() {
        return this.n;
    }

    public Function1<T, Object> costFn() {
        return this.costFn;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.limitWeighted().and(Attributes$SourceLocation$.MODULE$.forLambda(this.costFn()));
    }

    @Override
    public SupervisedGraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private long left;
            private final /* synthetic */ LimitWeighted $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private long left() {
                return this.left;
            }

            private void left_$eq(long x$1) {
                this.left = x$1;
            }

            public void onPush() {
                T elem = this.grab(this.$outer.in());
                Long l = (Long)this.withSupervision(() -> BoxesRunTime.unboxToLong($this.$outer.costFn().apply(elem)));
                Long l2 = OptionVal$Some$.MODULE$.unapply(l);
                if (!OptionVal$.MODULE$.isEmpty$extension(l2)) {
                    long weight = BoxesRunTime.unboxToLong(OptionVal$.MODULE$.get$extension(l2));
                    this.left_$eq(this.left() - weight);
                    if (this.left() >= 0L) {
                        this.push(this.$outer.out(), elem);
                        return;
                    }
                    this.failStage(new StreamLimitReachedException(this.$outer.n()));
                    return;
                }
            }

            public void onResume(Throwable t2) {
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onRestart(Throwable t2) {
                this.left_$eq(this.$outer.n());
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(inheritedAttributes$10, $outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.left = $outer.n();
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onPush$2(akka.stream.impl.fusing.LimitWeighted$$anon$23 java.lang.Object )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "LimitWeighted";
    }

    public <T> LimitWeighted<T> copy(long n, Function1<T, Object> costFn) {
        return new LimitWeighted<T>(n, costFn);
    }

    public <T> long copy$default$1() {
        return this.n();
    }

    public <T> Function1<T, Object> copy$default$2() {
        return this.costFn();
    }

    @Override
    public String productPrefix() {
        return "LimitWeighted";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToLong(this.n());
            }
            case 1: {
                return this.costFn();
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
        return x$1 instanceof LimitWeighted;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "n";
            }
            case 1: {
                return "costFn";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.longHash(this.n()));
        n = Statics.mix(n, Statics.anyHash(this.costFn()));
        return Statics.finalizeHash(n, 2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof LimitWeighted)) return false;
        boolean bl = true;
        if (!bl) return false;
        LimitWeighted limitWeighted = (LimitWeighted)x$1;
        if (this.n() != limitWeighted.n()) return false;
        Function1<T, Object> function1 = this.costFn();
        Function1<T, Object> function12 = limitWeighted.costFn();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public LimitWeighted(long n, Function1<T, Object> costFn) {
        this.n = n;
        this.costFn = costFn;
        Product.$init$(this);
    }
}

