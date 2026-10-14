/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.DropWhile$;
import akka.stream.impl.fusing.GraphStages;
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
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005g!\u0002\r\u001a\u0005~\t\u0003\u0002\u0003)\u0001\u0005+\u0007I\u0011A)\t\u0011a\u0003!\u0011#Q\u0001\nICQ!\u0017\u0001\u0005\u0002iCQA\u0018\u0001\u0005B}CQ\u0001\u001a\u0001\u0005\u0002\u0015DQa\u001e\u0001\u0005BaD\u0011\"a\u0001\u0001\u0003\u0003%\t!!\u0002\t\u0013\u0005M\u0001!%A\u0005\u0002\u0005U\u0001\"CA\u0018\u0001\u0005\u0005I\u0011IA\u0019\u0011%\t\u0019\u0004AA\u0001\n\u0003\t)\u0004C\u0005\u0002>\u0001\t\t\u0011\"\u0001\u0002@!I\u0011Q\t\u0001\u0002\u0002\u0013\u0005\u0013q\t\u0005\n\u0003+\u0002\u0011\u0011!C\u0001\u0003/B\u0011\"a\u0017\u0001\u0003\u0003%\t%!\u0018\t\u0013\u0005\u0005\u0004!!A\u0005B\u0005\r\u0004\"CA3\u0001\u0005\u0005I\u0011IA4\u000f)\t9(GA\u0001\u0012\u0003y\u0012\u0011\u0010\u0004\n1e\t\t\u0011#\u0001 \u0003wBa!\u0017\n\u0005\u0002\u00055\u0005bB<\u0013\u0003\u0003%)\u0005\u001f\u0005\n\u0003\u001f\u0013\u0012\u0011!CA\u0003#C\u0011\"a(\u0013\u0003\u0003%\t)!)\t\u0013\u0005]&#!A\u0005\n\u0005e&!\u0003#s_B<\u0006.\u001b7f\u0015\tQ2$\u0001\u0004gkNLgn\u001a\u0006\u00039u\tA![7qY*\u0011adH\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0001\nA!Y6lCV\u0011!\u0005O\n\u0005\u0001\r\"u\tE\u0002%gYr!!J\u0019\u000f\u0005\u0019\u0002dBA\u00140\u001d\tAcF\u0004\u0002*[5\t!F\u0003\u0002,Y\u00051AH]8piz\u001a\u0001!C\u0001!\u0013\tqr$\u0003\u0002\u001d;%\u0011!dG\u0005\u0003ee\t1b\u0012:ba\"\u001cF/Y4fg&\u0011A'\u000e\u0002\u0017'&l\u0007\u000f\\3MS:,\u0017M]$sCBD7\u000b^1hK*\u0011!'\u0007\t\u0003oab\u0001\u0001B\u0003:\u0001\t\u0007!HA\u0001U#\tY\u0014\t\u0005\u0002=\u007f5\tQHC\u0001?\u0003\u0015\u00198-\u00197b\u0013\t\u0001UHA\u0004O_RD\u0017N\\4\u0011\u0005q\u0012\u0015BA\">\u0005\r\te.\u001f\t\u0003y\u0015K!AR\u001f\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001*\u0014\b\u0003\u0013.s!!\u000b&\n\u0003yJ!\u0001T\u001f\u0002\u000fA\f7m[1hK&\u0011aj\u0014\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003\u0019v\n\u0011\u0001]\u000b\u0002%B!Ah\u0015\u001cV\u0013\t!VHA\u0005Gk:\u001cG/[8ocA\u0011AHV\u0005\u0003/v\u0012qAQ8pY\u0016\fg.\u0001\u0002qA\u00051A(\u001b8jiz\"\"aW/\u0011\u0007q\u0003a'D\u0001\u001a\u0011\u0015\u00016\u00011\u0001S\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002AB\u0011\u0011MY\u0007\u0002;%\u00111-\b\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aC2sK\u0006$X\rT8hS\u000e$\"AZ;\u0013\t\u001dLGN\u001d\u0004\u0005Q\u0016\u0001aM\u0001\u0007=e\u00164\u0017N\\3nK:$h\b\u0005\u0002]U&\u00111.\u0007\u0002\u001a'V\u0004XM\u001d<jg\u0016$wI]1qQN#\u0018mZ3M_\u001eL7\r\u0005\u0002na6\taN\u0003\u0002p;\u0005)1\u000f^1hK&\u0011\u0011O\u001c\u0002\n\u0013:D\u0015M\u001c3mKJ\u0004\"!\\:\n\u0005Qt'AC(vi\"\u000bg\u000e\u001a7fe\")a/\u0002a\u0001A\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\rF\u0001z!\tQx0D\u0001|\u0015\taX0\u0001\u0003mC:<'\"\u0001@\u0002\t)\fg/Y\u0005\u0004\u0003\u0003Y(AB*ue&tw-\u0001\u0003d_BLX\u0003BA\u0004\u0003\u001b!B!!\u0003\u0002\u0010A!A\fAA\u0006!\r9\u0014Q\u0002\u0003\u0006s\u001d\u0011\rA\u000f\u0005\t!\u001e\u0001\n\u00111\u0001\u0002\u0012A)AhUA\u0006+\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BA\f\u0003[)\"!!\u0007+\u0007I\u000bYb\u000b\u0002\u0002\u001eA!\u0011qDA\u0015\u001b\t\t\tC\u0003\u0003\u0002$\u0005\u0015\u0012!C;oG\",7m[3e\u0015\r\t9#P\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u0016\u0003C\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015I\u0004B1\u0001;\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t\u00110\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u00028A\u0019A(!\u000f\n\u0007\u0005mRHA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002B\u0003\u0003B\u0011\"a\u0011\f\u0003\u0003\u0005\r!a\u000e\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\tI\u0005E\u0003\u0002L\u0005E\u0013)\u0004\u0002\u0002N)\u0019\u0011qJ\u001f\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002T\u00055#\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2!VA-\u0011!\t\u0019%DA\u0001\u0002\u0004\t\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2!_A0\u0011%\t\u0019EDA\u0001\u0002\u0004\t9$\u0001\u0005iCND7i\u001c3f)\t\t9$\u0001\u0004fcV\fGn\u001d\u000b\u0004+\u0006%\u0004\u0002CA\"!\u0005\u0005\t\u0019A!)\u0007\u0001\ti\u0007\u0005\u0003\u0002p\u0005MTBAA9\u0015\r\t9cH\u0005\u0005\u0003k\n\tHA\u0006J]R,'O\\1m\u0003BL\u0017!\u0003#s_B<\u0006.\u001b7f!\ta&cE\u0003\u0013\u0003{\n\u0019\tE\u0002=\u0003\u007fJ1!!!>\u0005\u0019\te.\u001f*fMB!\u0011QQAF\u001b\t\t9IC\u0002\u0002\nv\f!![8\n\u00079\u000b9\t\u0006\u0002\u0002z\u0005)\u0011\r\u001d9msV!\u00111SAM)\u0011\t)*a'\u0011\tq\u0003\u0011q\u0013\t\u0004o\u0005eE!B\u001d\u0016\u0005\u0004Q\u0004B\u0002)\u0016\u0001\u0004\ti\nE\u0003='\u0006]U+A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\u0005\r\u0016q\u0016\u000b\u0005\u0003K\u000b\t\fE\u0003=\u0003O\u000bY+C\u0002\u0002*v\u0012aa\u00149uS>t\u0007#\u0002\u001fT\u0003[+\u0006cA\u001c\u00020\u0012)\u0011H\u0006b\u0001u!I\u00111\u0017\f\u0002\u0002\u0003\u0007\u0011QW\u0001\u0004q\u0012\u0002\u0004\u0003\u0002/\u0001\u0003[\u000bAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a/\u0011\u0007i\fi,C\u0002\u0002@n\u0014aa\u00142kK\u000e$\b")
@InternalApi
public final class DropWhile<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final Function1<T, Object> p;

    public static <T> Option<Function1<T, Object>> unapply(DropWhile<T> x$0) {
        return DropWhile$.MODULE$.unapply(x$0);
    }

    public static <T> DropWhile<T> apply(Function1<T, Object> p) {
        return DropWhile$.MODULE$.apply(p);
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
        return Stages$DefaultAttributes$.MODULE$.dropWhile().and(Attributes$SourceLocation$.MODULE$.forLambda(this.p()));
    }

    @Override
    public SupervisedGraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final /* synthetic */ DropWhile $outer;

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

            public void onPush() {
                T elem = this.grab(this.$outer.in());
                Boolean bl = (Boolean)this.withSupervision(() -> BoxesRunTime.unboxToBoolean($this.$outer.p().apply(elem)));
                Boolean bl2 = OptionVal$Some$.MODULE$.unapply(bl);
                if (!OptionVal$.MODULE$.isEmpty$extension(bl2)) {
                    boolean flag = BoxesRunTime.unboxToBoolean(OptionVal$.MODULE$.get$extension(bl2));
                    if (flag) {
                        this.pull(this.$outer.in());
                        return;
                    }
                    this.push(this.$outer.out(), elem);
                    this.setHandler(this.$outer.in(), this.rest());
                    return;
                }
            }

            private InHandler rest() {
                return new InHandler(this){
                    private final /* synthetic */ $anon$4 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$impl$fusing$DropWhile$$anon$$$outer().out(), this.$outer.grab(this.$outer.akka$stream$impl$fusing$DropWhile$$anon$$$outer().in()));
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                };
            }

            public void onResume(Throwable t2) {
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public /* synthetic */ DropWhile akka$stream$impl$fusing$DropWhile$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(inheritedAttributes$4, $outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onPush$1(akka.stream.impl.fusing.DropWhile$$anon$4 java.lang.Object )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "DropWhile";
    }

    public <T> DropWhile<T> copy(Function1<T, Object> p) {
        return new DropWhile<T>(p);
    }

    public <T> Function1<T, Object> copy$default$1() {
        return this.p();
    }

    @Override
    public String productPrefix() {
        return "DropWhile";
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
        return x$1 instanceof DropWhile;
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
        if (!(object instanceof DropWhile)) return false;
        boolean bl = true;
        if (!bl) return false;
        DropWhile dropWhile = (DropWhile)x$1;
        Function1<T, Object> function1 = this.p();
        Function1<T, Object> function12 = dropWhile.p();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public DropWhile(Function1<T, Object> p) {
        this.p = p;
        Product.$init$(this);
    }
}

