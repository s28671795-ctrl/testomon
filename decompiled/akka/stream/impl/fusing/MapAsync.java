/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.MapAsync$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.Future;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t\u0015wAB\u00181\u0011\u00031\u0004H\u0002\u0004;a!\u0005ag\u000f\u0005\u0006\u0015\u0006!\t\u0001\u0014\u0004\u0005\u001b\u0006\u0011a\n\u0003\u0005h\u0007\t\u0005\r\u0011\"\u0001i\u0011!I7A!a\u0001\n\u0003Q\u0007\u0002C7\u0004\u0005\u0003\u0005\u000b\u0015B*\t\u00119\u001c!Q1A\u0005\u0002=D\u0001\u0002_\u0002\u0003\u0002\u0003\u0006I\u0001\u001d\u0005\u0006\u0015\u000e!\t!\u001f\u0005\by\u000e\u0001\r\u0011\"\u0003~\u0011%\t9b\u0001a\u0001\n\u0013\tI\u0002C\u0004\u0002\u001e\r\u0001\u000b\u0015\u0002@\t\u000f\u0005}1\u0001\"\u0001\u0002\"!9\u0011\u0011J\u0002\u0005\u0002\u0005-\u0003bBA)\u0007\u0011\u0005\u00131\u000b\u0005\n\u0003/\n!\u0019!C\u0001\u00033B\u0001\"!\u0019\u0002A\u0003%\u00111\f\u0005\n\u0003#\n\u0011\u0011!CA\u0003GB\u0011Ba%\u0002\u0003\u0003%\tI!&\t\u0013\t]\u0016!!A\u0005\n\tefA\u0002\u001e1\u0005Z\nI\u0007\u0003\u0006\u0002\u0010V\u0011)\u001a!C\u0001\u0003#C!\"!'\u0016\u0005#\u0005\u000b\u0011BAJ\u0011)\tY*\u0006BK\u0002\u0013\u0005\u0011Q\u0014\u0005\u000b\u0003[+\"\u0011#Q\u0001\n\u0005}\u0005B\u0002&\u0016\t\u0003\ty\u000bC\u0005\u00028V\u0011\r\u0011\"\u0003\u0002:\"A\u0011\u0011Y\u000b!\u0002\u0013\tY\fC\u0005\u0002DV\u0011\r\u0011\"\u0003\u0002F\"A\u0011QZ\u000b!\u0002\u0013\t9\rC\u0004\u0002PV!\t%!5\t\u0013\u0005eWC1A\u0005B\u0005m\u0007\u0002CAo+\u0001\u0006I!a\u001d\t\u000f\u0005}W\u0003\"\u0011\u0002b\"I\u0011Q^\u000b\u0002\u0002\u0013\u0005\u0011q\u001e\u0005\n\u0005\u000b)\u0012\u0013!C\u0001\u0005\u000fA\u0011Ba\t\u0016#\u0003%\tA!\n\t\u0013\t=R#!A\u0005B\tE\u0002\"\u0003B +\u0005\u0005I\u0011AAI\u0011%\u0011\t%FA\u0001\n\u0003\u0011\u0019\u0005C\u0005\u0003HU\t\t\u0011\"\u0011\u0003J!I!qK\u000b\u0002\u0002\u0013\u0005!\u0011\f\u0005\n\u0005G*\u0012\u0011!C!\u0005KB\u0011B!\u001b\u0016\u0003\u0003%\tEa\u001b\t\u0013\t5T#!A\u0005B\t=\u0004\"\u0003B9+\u0005\u0005I\u0011\tB:\u0003!i\u0015\r]!ts:\u001c'BA\u00193\u0003\u00191Wo]5oO*\u00111\u0007N\u0001\u0005S6\u0004HN\u0003\u00026m\u000511\u000f\u001e:fC6T\u0011aN\u0001\u0005C.\\\u0017\r\u0005\u0002:\u00035\t\u0001G\u0001\u0005NCB\f5/\u001f8d'\r\tAH\u0011\t\u0003{\u0001k\u0011A\u0010\u0006\u0002\u007f\u0005)1oY1mC&\u0011\u0011I\u0010\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\rCU\"\u0001#\u000b\u0005\u00153\u0015AA5p\u0015\u00059\u0015\u0001\u00026bm\u0006L!!\u0013#\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?\u0007\u0001!\u0012\u0001\u000f\u0002\u0007\u0011>dG-\u001a:\u0016\u0005=[6cA\u0002=!B!Q(U*e\u0013\t\u0011fHA\u0005Gk:\u001cG/[8ocA\u0019AkV-\u000e\u0003US!A\u0016 \u0002\tU$\u0018\u000e\\\u0005\u00031V\u00131\u0001\u0016:z!\tQ6\f\u0004\u0001\u0005\u000bq\u001b!\u0019A/\u0003\u0003Q\u000b\"AX1\u0011\u0005uz\u0016B\u00011?\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u00102\n\u0005\rt$aA!osB\u0011Q(Z\u0005\u0003Mz\u0012A!\u00168ji\u0006!Q\r\\3n+\u0005\u0019\u0016\u0001C3mK6|F%Z9\u0015\u0005\u0011\\\u0007b\u00027\u0006\u0003\u0003\u0005\raU\u0001\u0004q\u0012\n\u0014!B3mK6\u0004\u0013AA2c+\u0005\u0001\bcA9um6\t!O\u0003\u0002ti\u0005)1\u000f^1hK&\u0011QO\u001d\u0002\u000e\u0003NLhnY\"bY2\u0014\u0017mY6\u0011\u0007]\u001c\u0011,D\u0001\u0002\u0003\r\u0019'\r\t\u000b\u0004mj\\\b\"B4\n\u0001\u0004\u0019\u0006\"\u00028\n\u0001\u0004\u0001\u0018AG2bG\",GmU;qKJ4\u0018n]5p]\u0012K'/Z2uSZ,W#\u0001@\u0011\u000b}\f\u0019!a\u0002\u000e\u0005\u0005\u0005!B\u0001,7\u0013\u0011\t)!!\u0001\u0003\u0013=\u0003H/[8o-\u0006d\u0007\u0003BA\u0005\u0003#qA!a\u0003\u0002\u000e5\tA'C\u0002\u0002\u0010Q\n1bU;qKJ4\u0018n]5p]&!\u00111CA\u000b\u0005%!\u0015N]3di&4XMC\u0002\u0002\u0010Q\nadY1dQ\u0016$7+\u001e9feZL7/[8o\t&\u0014Xm\u0019;jm\u0016|F%Z9\u0015\u0007\u0011\fY\u0002C\u0004m\u0017\u0005\u0005\t\u0019\u0001@\u00027\r\f7\r[3e'V\u0004XM\u001d<jg&|g\u000eR5sK\u000e$\u0018N^3!\u0003]\u0019X\u000f]3sm&\u001c\u0018n\u001c8ESJ,7\r^5wK\u001a{'\u000f\u0006\u0004\u0002\b\u0005\r\u0012Q\u0006\u0005\b\u0003Ki\u0001\u0019AA\u0014\u0003\u001d!WmY5eKJ\u0004B!!\u0003\u0002*%!\u00111FA\u000b\u0005\u001d!UmY5eKJDq!a\f\u000e\u0001\u0004\t\t$\u0001\u0002fqB!\u00111GA\"\u001d\u0011\t)$a\u0010\u000f\t\u0005]\u0012QH\u0007\u0003\u0003sQ1!a\u000fL\u0003\u0019a$o\\8u}%\tq(C\u0002\u0002By\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002F\u0005\u001d#!\u0003+ie><\u0018M\u00197f\u0015\r\t\tEP\u0001\bg\u0016$X\t\\3n)\r!\u0017Q\n\u0005\u0007\u0003\u001fr\u0001\u0019A*\u0002\u0003Q\fQ!\u00199qYf$2\u0001ZA+\u0011\u0019\tye\u0004a\u0001'\u0006Yaj\u001c;ZKR$\u0006.\u001a:f+\t\tY\u0006\u0005\u0003U\u0003;r\u0016bAA0+\n9a)Y5mkJ,\u0017\u0001\u0004(pif+G\u000f\u00165fe\u0016\u0004SCBA3\u0005\u000b\u0013I\t\u0006\u0004\u0002h\t-%Q\u0012\t\u0007sU\u0011\u0019Ia\"\u0016\r\u0005-\u00141PAA'\u001d)\u0012QNAC\u0003\u0017\u0003R!]A8\u0003gJ1!!\u001ds\u0005)9%/\u00199i'R\fw-\u001a\t\t\u0003\u0017\t)(!\u001f\u0002\u0000%\u0019\u0011q\u000f\u001b\u0003\u0013\u0019cwn^*iCB,\u0007c\u0001.\u0002|\u00111\u0011QP\u000bC\u0002u\u0013!!\u00138\u0011\u0007i\u000b\t\t\u0002\u0004\u0002\u0004V\u0011\r!\u0018\u0002\u0004\u001fV$\bcA\u001f\u0002\b&\u0019\u0011\u0011\u0012 \u0003\u000fA\u0013x\u000eZ;diB!\u00111GAG\u0013\rI\u0015qI\u0001\fa\u0006\u0014\u0018\r\u001c7fY&\u001cX.\u0006\u0002\u0002\u0014B\u0019Q(!&\n\u0007\u0005]eHA\u0002J]R\fA\u0002]1sC2dW\r\\5t[\u0002\n\u0011AZ\u000b\u0003\u0003?\u0003b!P)\u0002z\u0005\u0005\u0006CBAR\u0003S\u000by(\u0004\u0002\u0002&*\u0019\u0011q\u0015 \u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0002,\u0006\u0015&A\u0002$viV\u0014X-\u0001\u0002gAQ1\u0011\u0011WAZ\u0003k\u0003b!O\u000b\u0002z\u0005}\u0004bBAH5\u0001\u0007\u00111\u0013\u0005\b\u00037S\u0002\u0019AAP\u0003\tIg.\u0006\u0002\u0002<B1\u00111BA_\u0003sJ1!a05\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\u0004_V$XCAAd!\u0019\tY!!3\u0002\u0000%\u0019\u00111\u001a\u001b\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f\u001e\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/\u0006\u0002\u0002TB!\u00111BAk\u0013\r\t9\u000e\u000e\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,WCAA:\u0003\u0019\u0019\b.\u00199fA\u0005Y1M]3bi\u0016dunZ5d)\u0011\t\u0019/!;\u0011\u0007E\f)/C\u0002\u0002hJ\u0014qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u0003W\u0014\u0003\u0019AAj\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003\u0011\u0019w\u000e]=\u0016\r\u0005E\u0018q_A~)\u0019\t\u00190!@\u0002\u0000B1\u0011(FA{\u0003s\u00042AWA|\t\u0019\tih\tb\u0001;B\u0019!,a?\u0005\r\u0005\r5E1\u0001^\u0011%\tyi\tI\u0001\u0002\u0004\t\u0019\nC\u0005\u0002\u001c\u000e\u0002\n\u00111\u0001\u0003\u0002A1Q(UA{\u0005\u0007\u0001b!a)\u0002*\u0006e\u0018AD2paf$C-\u001a4bk2$H%M\u000b\u0007\u0005\u0013\u0011yB!\t\u0016\u0005\t-!\u0006BAJ\u0005\u001bY#Aa\u0004\u0011\t\tE!1D\u0007\u0003\u0005'QAA!\u0006\u0003\u0018\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u00053q\u0014AC1o]>$\u0018\r^5p]&!!Q\u0004B\n\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0007\u0003{\"#\u0019A/\u0005\r\u0005\rEE1\u0001^\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*bAa\n\u0003,\t5RC\u0001B\u0015U\u0011\tyJ!\u0004\u0005\r\u0005uTE1\u0001^\t\u0019\t\u0019)\nb\u0001;\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"Aa\r\u0011\t\tU\"1H\u0007\u0003\u0005oQ1A!\u000fG\u0003\u0011a\u0017M\\4\n\t\tu\"q\u0007\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019\u0011M!\u0012\t\u00111D\u0013\u0011!a\u0001\u0003'\u000bq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005\u0017\u0002RA!\u0014\u0003T\u0005l!Aa\u0014\u000b\u0007\tEc(\u0001\u0006d_2dWm\u0019;j_:LAA!\u0016\u0003P\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011YF!\u0019\u0011\u0007u\u0012i&C\u0002\u0003`y\u0012qAQ8pY\u0016\fg\u000eC\u0004mU\u0005\u0005\t\u0019A1\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0005g\u00119\u0007\u0003\u0005mW\u0005\u0005\t\u0019AAJ\u0003!A\u0017m\u001d5D_\u0012,GCAAJ\u0003!!xn\u0015;sS:<GC\u0001B\u001a\u0003\u0019)\u0017/^1mgR!!1\fB;\u0011\u001dag&!AA\u0002\u0005D3!\u0006B=!\u0011\u0011YHa \u000e\u0005\tu$b\u0001B\rm%!!\u0011\u0011B?\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0011\u0007i\u0013)\t\u0002\u0004\u0002~I\u0011\r!\u0018\t\u00045\n%EABAB%\t\u0007Q\fC\u0004\u0002\u0010J\u0001\r!a%\t\u000f\u0005m%\u00031\u0001\u0003\u0010B1Q(\u0015BB\u0005#\u0003b!a)\u0002*\n\u001d\u0015aB;oCB\u0004H._\u000b\u0007\u0005/\u0013IKa,\u0015\t\te%\u0011\u0017\t\u0006{\tm%qT\u0005\u0004\u0005;s$AB(qi&|g\u000eE\u0004>\u0005C\u000b\u0019J!*\n\u0007\t\rfH\u0001\u0004UkBdWM\r\t\u0007{E\u00139Ka+\u0011\u0007i\u0013I\u000b\u0002\u0004\u0002~M\u0011\r!\u0018\t\u0007\u0003G\u000bIK!,\u0011\u0007i\u0013y\u000b\u0002\u0004\u0002\u0004N\u0011\r!\u0018\u0005\n\u0005g\u001b\u0012\u0011!a\u0001\u0005k\u000b1\u0001\u001f\u00131!\u0019ITCa*\u0003.\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!1\u0018\t\u0005\u0005k\u0011i,\u0003\u0003\u0003@\n]\"AB(cU\u0016\u001cG\u000fK\u0002\u0002\u0005sB3\u0001\u0001B=\u0001")
@InternalApi
public final class MapAsync<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final int parallelism;
    private final Function1<In, Future<Out>> f;
    private final Inlet<In> akka$stream$impl$fusing$MapAsync$$in;
    private final Outlet<Out> akka$stream$impl$fusing$MapAsync$$out;
    private final FlowShape<In, Out> shape;

    public static <In, Out> Option<Tuple2<Object, Function1<In, Future<Out>>>> unapply(MapAsync<In, Out> x$0) {
        return MapAsync$.MODULE$.unapply(x$0);
    }

    public static <In, Out> MapAsync<In, Out> apply(int parallelism, Function1<In, Future<Out>> f) {
        return MapAsync$.MODULE$.apply(parallelism, f);
    }

    public static Failure<Nothing$> NotYetThere() {
        return MapAsync$.MODULE$.NotYetThere();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int parallelism() {
        return this.parallelism;
    }

    public Function1<In, Future<Out>> f() {
        return this.f;
    }

    public Inlet<In> akka$stream$impl$fusing$MapAsync$$in() {
        return this.akka$stream$impl$fusing$MapAsync$$in;
    }

    public Outlet<Out> akka$stream$impl$fusing$MapAsync$$out() {
        return this.akka$stream$impl$fusing$MapAsync$$out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.mapAsync().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private Buffer<Holder<Out>> buffer;
            private final AsyncCallback<Holder<Out>> futureCB;
            private volatile boolean bitmap$0;
            private final /* synthetic */ MapAsync $outer;
            private final Attributes inheritedAttributes$13;

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
                $anon$29 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$13.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            private Buffer<Holder<Out>> buffer() {
                return this.buffer;
            }

            private void buffer_$eq(Buffer<Holder<Out>> x$1) {
                this.buffer = x$1;
            }

            private AsyncCallback<Holder<Out>> futureCB() {
                return this.futureCB;
            }

            public void preStart() {
                this.buffer_$eq(Buffer$.MODULE$.apply(this.$outer.parallelism(), this.inheritedAttributes$13));
            }

            public void onPull() {
                this.pushNextIfPossible();
            }

            /*
             * Unable to fully structure code
             */
            public void onPush() {
                block7: {
                    try {
                        block8: {
                            future = this.$outer.f().apply(this.grab(this.$outer.akka$stream$impl$fusing$MapAsync$$in()));
                            holder = new Holder<Nothing$>(MapAsync$.MODULE$.NotYetThere(), this.futureCB());
                            this.buffer().enqueue(holder);
                            var6_3 = future.value();
                            if (None$.MODULE$.equals(var6_3)) {
                                future.onComplete(holder, ExecutionContexts$.MODULE$.parasitic());
                                break block7;
                            }
                            if (!(var6_3 instanceof Some)) break block8;
                            var7_4 = (Some)var6_3;
                            v = (Try)var7_4.value();
                            holder.setElem(v);
                            var9_6 = v;
                            if (!(var9_6 instanceof Failure)) ** GOTO lbl-1000
                            var10_7 = (Failure)var9_6;
                            ex = var10_7.exception();
                            v0 = holder.supervisionDirectiveFor(this.decider(), ex);
                            var12_9 = Supervision$Stop$.MODULE$;
                            if (!(v0 != null ? v0.equals(var12_9) == false : var12_9 != null)) {
                                this.failStage(ex);
                            } else lbl-1000:
                            // 2 sources

                            {
                                this.pushNextIfPossible();
                            }
                            break block7;
                        }
                        throw new MatchError(var6_3);
                    }
                    catch (Throwable var13_10) {
                        var14_11 = var13_10;
                        if (var14_11 != null && !(var15_12 = NonFatal$.MODULE$.unapply(var14_11)).isEmpty()) {
                            ex = var15_12.get();
                            v1 = this.decider().apply(ex);
                            var17_14 = Supervision$Stop$.MODULE$;
                            if (!(v1 != null ? v1.equals(var17_14) == false : var17_14 != null)) {
                                this.failStage(ex);
                            }
                        }
                        throw var13_10;
                    }
                }
                this.pullIfNeeded();
            }

            public void onUpstreamFinish() {
                if (this.buffer().isEmpty()) {
                    this.completeStage();
                    return;
                }
            }

            private void pushNextIfPossible() {
                block6: {
                    BoxedUnit boxedUnit;
                    block7: {
                        block8: {
                            Try<Out> try_;
                            Failure failure;
                            boolean bl;
                            block9: {
                                Throwable ex;
                                while (true) {
                                    Option<Throwable> option;
                                    if (this.buffer().isEmpty()) {
                                        this.pullIfNeeded();
                                        boxedUnit = BoxedUnit.UNIT;
                                        break block6;
                                    }
                                    if (this.buffer().peek().elem() == MapAsync$.MODULE$.NotYetThere()) {
                                        this.pullIfNeeded();
                                        boxedUnit = BoxedUnit.UNIT;
                                        break block6;
                                    }
                                    if (!this.isAvailable(this.$outer.akka$stream$impl$fusing$MapAsync$$out())) break block7;
                                    Holder<Out> holder = this.buffer().dequeue();
                                    bl = false;
                                    failure = null;
                                    try_ = holder.elem();
                                    if (try_ instanceof Success) {
                                        Success success = (Success)try_;
                                        T elem = success.value();
                                        if (elem != null) {
                                            this.push(this.$outer.akka$stream$impl$fusing$MapAsync$$out(), elem);
                                            this.pullIfNeeded();
                                            break block8;
                                        }
                                        this.pullIfNeeded();
                                        continue;
                                    }
                                    if (!(try_ instanceof Failure)) break block9;
                                    bl = true;
                                    failure = (Failure)try_;
                                    Throwable throwable = failure.exception();
                                    if (throwable == null || (option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) break block9;
                                    ex = option.get();
                                    Supervision.Directive directive = holder.supervisionDirectiveFor(this.decider(), ex);
                                    if (Supervision$Stop$.MODULE$.equals(directive)) break;
                                }
                                this.failStage(ex);
                                break block8;
                            }
                            if (bl) {
                                Throwable ex = failure.exception();
                                throw ex;
                            }
                            throw new MatchError(try_);
                        }
                        boxedUnit = BoxedUnit.UNIT;
                        break block6;
                    }
                    boxedUnit = BoxedUnit.UNIT;
                }
            }

            private void pullIfNeeded() {
                if (this.isClosed(this.$outer.akka$stream$impl$fusing$MapAsync$$in()) && this.buffer().isEmpty()) {
                    this.completeStage();
                    return;
                }
                if (this.buffer().used() < this.$outer.parallelism() && !this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsync$$in())) {
                    this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsync$$in());
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$futureCB$5($anon$29 $this, Holder holder) {
                Try<T> try_ = holder.elem();
                if (try_ instanceof Success) {
                    $this.pushNextIfPossible();
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    Supervision.Directive directive = holder.supervisionDirectiveFor($this.decider(), ex);
                    if (Supervision$Stop$.MODULE$.equals(directive)) {
                        $this.failStage(ex);
                    } else {
                        $this.pushNextIfPossible();
                    }
                    return;
                }
                throw new MatchError(try_);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$13 = inheritedAttributes$13;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.futureCB = this.getAsyncCallback((Function1<Holder, Object> & Serializable)holder -> {
                    $anon$29.$anonfun$futureCB$5(this, holder);
                    return BoxedUnit.UNIT;
                });
                this.setHandlers($outer.akka$stream$impl$fusing$MapAsync$$in(), $outer.akka$stream$impl$fusing$MapAsync$$out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$futureCB$5$adapted(akka.stream.impl.fusing.MapAsync$$anon$29 akka.stream.impl.fusing.MapAsync$Holder )}, serializedLambda);
            }
        };
    }

    public <In, Out> MapAsync<In, Out> copy(int parallelism, Function1<In, Future<Out>> f) {
        return new MapAsync<In, Out>(parallelism, f);
    }

    public <In, Out> int copy$default$1() {
        return this.parallelism();
    }

    public <In, Out> Function1<In, Future<Out>> copy$default$2() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "MapAsync";
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
                return BoxesRunTime.boxToInteger(this.parallelism());
            }
            case 1: {
                return this.f();
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
        return x$1 instanceof MapAsync;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "parallelism";
            }
            case 1: {
                return "f";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.parallelism());
        n = Statics.mix(n, Statics.anyHash(this.f()));
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof MapAsync)) return false;
        boolean bl = true;
        if (!bl) return false;
        MapAsync mapAsync = (MapAsync)x$1;
        if (this.parallelism() != mapAsync.parallelism()) return false;
        Function1<In, Future<Out>> function1 = this.f();
        Function1<In, Future<Out>> function12 = mapAsync.f();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public MapAsync(int parallelism, Function1<In, Future<Out>> f) {
        this.parallelism = parallelism;
        this.f = f;
        Product.$init$(this);
        this.akka$stream$impl$fusing$MapAsync$$in = Inlet$.MODULE$.apply("MapAsync.in");
        this.akka$stream$impl$fusing$MapAsync$$out = Outlet$.MODULE$.apply("MapAsync.out");
        this.shape = new FlowShape<In, Out>(this.akka$stream$impl$fusing$MapAsync$$in(), this.akka$stream$impl$fusing$MapAsync$$out());
    }

    public static final class Holder<T>
    implements Function1<Try<T>, BoxedUnit> {
        private Try<T> elem;
        private final AsyncCallback<Holder<T>> cb;
        private Supervision.Directive cachedSupervisionDirective;

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A> Function1<A, BoxedUnit> compose(Function1<A, Try<T>> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A> Function1<Try<T>, A> andThen(Function1<BoxedUnit, A> g2) {
            return Function1.andThen$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        public Try<T> elem() {
            return this.elem;
        }

        public void elem_$eq(Try<T> x$1) {
            this.elem = x$1;
        }

        public AsyncCallback<Holder<T>> cb() {
            return this.cb;
        }

        private Supervision.Directive cachedSupervisionDirective() {
            return this.cachedSupervisionDirective;
        }

        private void cachedSupervisionDirective_$eq(Supervision.Directive x$1) {
            this.cachedSupervisionDirective = x$1;
        }

        public Supervision.Directive supervisionDirectiveFor(Function1<Throwable, Supervision.Directive> decider, Throwable ex) {
            Supervision.Directive directive = this.cachedSupervisionDirective();
            Supervision.Directive directive2 = OptionVal$Some$.MODULE$.unapply(directive);
            if (!OptionVal$.MODULE$.isEmpty$extension(directive2)) {
                Supervision.Directive d = OptionVal$.MODULE$.get$extension(directive2);
                return d;
            }
            Supervision.Directive d = decider.apply(ex);
            this.cachedSupervisionDirective_$eq(OptionVal$Some$.MODULE$.apply(d));
            return d;
        }

        public void setElem(Try<T> t2) {
            this.elem_$eq(t2);
        }

        @Override
        public void apply(Try<T> t2) {
            this.setElem(t2);
            this.cb().invoke(this);
        }

        public Holder(Try<T> elem, AsyncCallback<Holder<T>> cb) {
            this.elem = elem;
            this.cb = cb;
            Function1.$init$(this);
            OptionVal$.MODULE$.None();
            this.cachedSupervisionDirective = null;
        }
    }
}

