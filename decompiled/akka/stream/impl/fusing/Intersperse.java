/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.Intersperse$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ug!B\u000f\u001f\u0005\u00122\u0003\u0002C+\u0001\u0005+\u0007I\u0011\u0001,\t\u0011i\u0003!\u0011#Q\u0001\n]C\u0001b\u0017\u0001\u0003\u0016\u0004%\t\u0001\u0018\u0005\t;\u0002\u0011\t\u0012)A\u0005w!Aa\f\u0001BK\u0002\u0013\u0005a\u000b\u0003\u0005`\u0001\tE\t\u0015!\u0003X\u0011\u0015\u0001\u0007\u0001\"\u0001b\u0011\u00159\u0007\u0001\"\u0011i\u0011\u001d)\b!!A\u0005\u0002YD\u0001b \u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u00037\u0001\u0011\u0013!C\u0001\u0003;A\u0011\"!\n\u0001#\u0003%\t!a\n\t\u0013\u0005-\u0002!!A\u0005B\u00055\u0002\"CA \u0001\u0005\u0005I\u0011AA!\u0011%\tI\u0005AA\u0001\n\u0003\tY\u0005C\u0005\u0002R\u0001\t\t\u0011\"\u0011\u0002T!I\u0011\u0011\r\u0001\u0002\u0002\u0013\u0005\u00111\r\u0005\n\u0003[\u0002\u0011\u0011!C!\u0003_B\u0011\"a\u001d\u0001\u0003\u0003%\t%!\u001e\t\u0013\u0005]\u0004!!A\u0005B\u0005e\u0004\"CA>\u0001\u0005\u0005I\u0011IA?\u000f)\tiIHA\u0001\u0012\u0003!\u0013q\u0012\u0004\n;y\t\t\u0011#\u0001%\u0003#Ca\u0001Y\f\u0005\u0002\u0005\r\u0006\"CA</\u0005\u0005IQIA=\u0011%\t)kFA\u0001\n\u0003\u000b9\u000bC\u0005\u0002:^\t\t\u0011\"!\u0002<\"I\u00111[\f\u0002\u0002\u0013%\u0011Q\u001b\u0002\f\u0013:$XM]:qKJ\u001cXM\u0003\u0002 A\u00051a-^:j]\u001eT!!\t\u0012\u0002\t%l\u0007\u000f\u001c\u0006\u0003G\u0011\naa\u001d;sK\u0006l'\"A\u0013\u0002\t\u0005\\7.Y\u000b\u0003Ou\u001aB\u0001\u0001\u0015J\u0019B\u0019\u0011\u0006O\u001e\u000f\u0005)2dBA\u00166\u001d\taCG\u0004\u0002.g9\u0011aFM\u0007\u0002_)\u0011\u0001'M\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\tQ%\u0003\u0002$I%\u0011\u0011EI\u0005\u0003?\u0001J!a\u000e\u0010\u0002\u0017\u001d\u0013\u0018\r\u001d5Ti\u0006<Wm]\u0005\u0003si\u0012acU5na2,G*\u001b8fCJ<%/\u00199i'R\fw-\u001a\u0006\u0003oy\u0001\"\u0001P\u001f\r\u0001\u0011)a\b\u0001b\u0001\u007f\t\tA+\u0005\u0002A\rB\u0011\u0011\tR\u0007\u0002\u0005*\t1)A\u0003tG\u0006d\u0017-\u0003\u0002F\u0005\n9aj\u001c;iS:<\u0007CA!H\u0013\tA%IA\u0002B]f\u0004\"!\u0011&\n\u0005-\u0013%a\u0002)s_\u0012,8\r\u001e\t\u0003\u001bJs!A\u0014)\u000f\u00059z\u0015\"A\"\n\u0005E\u0013\u0015a\u00029bG.\fw-Z\u0005\u0003'R\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!!\u0015\"\u0002\u000bM$\u0018M\u001d;\u0016\u0003]\u00032!\u0011-<\u0013\tI&I\u0001\u0004PaRLwN\\\u0001\u0007gR\f'\u000f\u001e\u0011\u0002\r%t'.Z2u+\u0005Y\u0014aB5oU\u0016\u001cG\u000fI\u0001\u0004K:$\u0017\u0001B3oI\u0002\na\u0001P5oSRtD\u0003\u00022eK\u001a\u00042a\u0019\u0001<\u001b\u0005q\u0002\"B+\b\u0001\u00049\u0006\"B.\b\u0001\u0004Y\u0004\"\u00020\b\u0001\u00049\u0016aC2sK\u0006$X\rT8hS\u000e$\"![8\u0011\u0005)lW\"A6\u000b\u00051\u0014\u0013!B:uC\u001e,\u0017B\u00018l\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"\u00029\t\u0001\u0004\t\u0018\u0001B1uiJ\u0004\"A]:\u000e\u0003\tJ!\u0001\u001e\u0012\u0003\u0015\u0005#HO]5ckR,7/\u0001\u0003d_BLXCA<{)\u0011A80 @\u0011\u0007\r\u0004\u0011\u0010\u0005\u0002=u\u0012)a(\u0003b\u0001\u007f!9Q+\u0003I\u0001\u0002\u0004a\bcA!Ys\"91,\u0003I\u0001\u0002\u0004I\bb\u00020\n!\u0003\u0005\r\u0001`\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\t\u0019!!\u0007\u0016\u0005\u0005\u0015!fA,\u0002\b-\u0012\u0011\u0011\u0002\t\u0005\u0003\u0017\t)\"\u0004\u0002\u0002\u000e)!\u0011qBA\t\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0014\t\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t9\"!\u0004\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003?\u0015\t\u0007q(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005}\u00111E\u000b\u0003\u0003CQ3aOA\u0004\t\u0015q4B1\u0001@\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*B!a\u0001\u0002*\u0011)a\b\u0004b\u0001\u007f\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!a\f\u0011\t\u0005E\u00121H\u0007\u0003\u0003gQA!!\u000e\u00028\u0005!A.\u00198h\u0015\t\tI$\u0001\u0003kCZ\f\u0017\u0002BA\u001f\u0003g\u0011aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAA\"!\r\t\u0015QI\u0005\u0004\u0003\u000f\u0012%aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001$\u0002N!I\u0011qJ\b\u0002\u0002\u0003\u0007\u00111I\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005U\u0003#BA,\u0003;2UBAA-\u0015\r\tYFQ\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA0\u00033\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011QMA6!\r\t\u0015qM\u0005\u0004\u0003S\u0012%a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003\u001f\n\u0012\u0011!a\u0001\r\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\ty#!\u001d\t\u0013\u0005=##!AA\u0002\u0005\r\u0013\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\r\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005=\u0012AB3rk\u0006d7\u000f\u0006\u0003\u0002f\u0005}\u0004\u0002CA(+\u0005\u0005\t\u0019\u0001$)\u0007\u0001\t\u0019\t\u0005\u0003\u0002\u0006\u0006%UBAAD\u0015\r\t\u0019\u0002J\u0005\u0005\u0003\u0017\u000b9IA\u0006J]R,'O\\1m\u0003BL\u0017aC%oi\u0016\u00148\u000f]3sg\u0016\u0004\"aY\f\u0014\u000b]\t\u0019*!'\u0011\u0007\u0005\u000b)*C\u0002\u0002\u0018\n\u0013a!\u00118z%\u00164\u0007\u0003BAN\u0003Ck!!!(\u000b\t\u0005}\u0015qG\u0001\u0003S>L1aUAO)\t\ty)A\u0003baBd\u00170\u0006\u0003\u0002*\u0006=F\u0003CAV\u0003c\u000b),a.\u0011\t\r\u0004\u0011Q\u0016\t\u0004y\u0005=F!\u0002 \u001b\u0005\u0004y\u0004BB+\u001b\u0001\u0004\t\u0019\f\u0005\u0003B1\u00065\u0006BB.\u001b\u0001\u0004\ti\u000b\u0003\u0004_5\u0001\u0007\u00111W\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\ti,a3\u0015\t\u0005}\u0016Q\u001a\t\u0005\u0003b\u000b\t\rE\u0005B\u0003\u0007\f9-!3\u0002H&\u0019\u0011Q\u0019\"\u0003\rQ+\b\u000f\\34!\u0011\t\u0005,!3\u0011\u0007q\nY\rB\u0003?7\t\u0007q\bC\u0005\u0002Pn\t\t\u00111\u0001\u0002R\u0006\u0019\u0001\u0010\n\u0019\u0011\t\r\u0004\u0011\u0011Z\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003/\u0004B!!\r\u0002Z&!\u00111\\A\u001a\u0005\u0019y%M[3di\u0002")
@InternalApi
public final class Intersperse<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final Option<T> start;
    private final T inject;
    private final Option<T> end;

    public static <T> Option<Tuple3<Option<T>, T, Option<T>>> unapply(Intersperse<T> x$0) {
        return Intersperse$.MODULE$.unapply(x$0);
    }

    public static <T> Intersperse<T> apply(Option<T> start2, T inject, Option<T> end) {
        return Intersperse$.MODULE$.apply(start2, inject, end);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Option<T> start() {
        return this.start;
    }

    public T inject() {
        return this.inject;
    }

    public Option<T> end() {
        return this.end;
    }

    @Override
    public GraphStageLogic createLogic(Attributes attr) {
        return new OutHandler(this){
            private final InHandler startInHandler;
            private final InHandler akka$stream$impl$fusing$Intersperse$$anon$$restInHandler;
            private final /* synthetic */ Intersperse $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            private InHandler startInHandler() {
                return this.startInHandler;
            }

            public InHandler akka$stream$impl$fusing$Intersperse$$anon$$restInHandler() {
                return this.akka$stream$impl$fusing$Intersperse$$anon$$restInHandler;
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public /* synthetic */ Intersperse akka$stream$impl$fusing$Intersperse$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.startInHandler = new InHandler(this){
                    private final /* synthetic */ $anon$19 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        if (this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().start().isDefined()) {
                            this.$outer.emitMultiple(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().out(), package$.MODULE$.Iterator().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().start().get(), this.$outer.grab(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().in())})));
                        } else {
                            this.$outer.emit(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().out(), this.$outer.grab(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().in()));
                        }
                        this.$outer.setHandler(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().in(), this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$restInHandler());
                    }

                    public void onUpstreamFinish() {
                        this.$outer.emitMultiple(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().out(), package$.MODULE$.Iterator().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Option[]{this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().start(), this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().end()})).flatten(Predef$.MODULE$.$conforms()));
                        this.$outer.completeStage();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                };
                this.akka$stream$impl$fusing$Intersperse$$anon$$restInHandler = new InHandler(this){
                    private final /* synthetic */ $anon$19 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.emitMultiple(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().out(), package$.MODULE$.Iterator().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().inject(), this.$outer.grab(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().in())})));
                    }

                    public void onUpstreamFinish() {
                        if (this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().end().isDefined()) {
                            this.$outer.emit(this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().out(), this.$outer.akka$stream$impl$fusing$Intersperse$$anon$$$outer().end().get());
                        }
                        this.$outer.completeStage();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                };
                this.setHandler($outer.in(), this.startInHandler());
                this.setHandler($outer.out(), this);
            }
        };
    }

    public <T> Intersperse<T> copy(Option<T> start2, T inject, Option<T> end) {
        return new Intersperse<T>(start2, inject, end);
    }

    public <T> Option<T> copy$default$1() {
        return this.start();
    }

    public <T> T copy$default$2() {
        return this.inject();
    }

    public <T> Option<T> copy$default$3() {
        return this.end();
    }

    @Override
    public String productPrefix() {
        return "Intersperse";
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
                return this.start();
            }
            case 1: {
                return this.inject();
            }
            case 2: {
                return this.end();
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
        return x$1 instanceof Intersperse;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "start";
            }
            case 1: {
                return "inject";
            }
            case 2: {
                return "end";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
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
        if (!(object instanceof Intersperse)) return false;
        boolean bl = true;
        if (!bl) return false;
        Intersperse intersperse = (Intersperse)x$1;
        Option<T> option = this.start();
        Option<T> option2 = intersperse.start();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        if (!BoxesRunTime.equals(this.inject(), intersperse.inject())) return false;
        Option<T> option3 = this.end();
        Option<T> option4 = intersperse.end();
        if (option3 == null) {
            if (option4 == null) return true;
            return false;
        } else {
            if (!option3.equals(option4)) return false;
            return true;
        }
    }

    public Intersperse(Option<T> start2, T inject, Option<T> end) {
        block1: {
            this.start = start2;
            this.inject = inject;
            this.end = end;
            Product.$init$(this);
            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(inject);
            if (start2.isDefined()) {
                ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(start2.get());
            }
            if (!end.isDefined()) break block1;
            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(end.get());
        }
    }
}

