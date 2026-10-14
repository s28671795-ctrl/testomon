/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Drop$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ef!\u0002\r\u001a\u0005~\t\u0003\u0002\u0003)\u0001\u0005+\u0007I\u0011A)\t\u0011U\u0003!\u0011#Q\u0001\nICQA\u0016\u0001\u0005\u0002]CQa\u0017\u0001\u0005BqCQ!\u0019\u0001\u0005B\tDQa\u001b\u0001\u0005B1Dq!\u001e\u0001\u0002\u0002\u0013\u0005a\u000fC\u0004}\u0001E\u0005I\u0011A?\t\u0013\u0005U\u0001!!A\u0005B\u0005]\u0001\"CA\u0014\u0001\u0005\u0005I\u0011AA\u0015\u0011%\t\t\u0004AA\u0001\n\u0003\t\u0019\u0004C\u0005\u0002:\u0001\t\t\u0011\"\u0011\u0002<!I\u0011\u0011\n\u0001\u0002\u0002\u0013\u0005\u00111\n\u0005\n\u0003+\u0002\u0011\u0011!C!\u0003/B\u0011\"a\u0017\u0001\u0003\u0003%\t%!\u0018\t\u0013\u0005}\u0003!!A\u0005B\u0005\u0005tACA93\u0005\u0005\t\u0012A\u0010\u0002t\u0019I\u0001$GA\u0001\u0012\u0003y\u0012Q\u000f\u0005\u0007-J!\t!a\"\t\u0011-\u0014\u0012\u0011!C#\u0003\u0013C\u0011\"a#\u0013\u0003\u0003%\t)!$\t\u0013\u0005e%#!A\u0005\u0002\u0006m\u0005\"CAX%\u0005\u0005I\u0011BAY\u0005\u0011!%o\u001c9\u000b\u0005iY\u0012A\u00024vg&twM\u0003\u0002\u001d;\u0005!\u0011.\u001c9m\u0015\tqr$\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002A\u0005!\u0011m[6b+\t\u0011\u0003h\u0005\u0003\u0001G\u0011;\u0005c\u0001\u00134m9\u0011Q%\r\b\u0003MAr!aJ\u0018\u000f\u0005!rcBA\u0015.\u001b\u0005Q#BA\u0016-\u0003\u0019a$o\\8u}\r\u0001\u0011\"\u0001\u0011\n\u0005yy\u0012B\u0001\u000f\u001e\u0013\tQ2$\u0003\u000233\u0005YqI]1qQN#\u0018mZ3t\u0013\t!TG\u0001\fTS6\u0004H.\u001a'j]\u0016\f'o\u0012:ba\"\u001cF/Y4f\u0015\t\u0011\u0014\u0004\u0005\u00028q1\u0001A!B\u001d\u0001\u0005\u0004Q$!\u0001+\u0012\u0005m\n\u0005C\u0001\u001f@\u001b\u0005i$\"\u0001 \u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0001k$a\u0002(pi\"Lgn\u001a\t\u0003y\tK!aQ\u001f\u0003\u0007\u0005s\u0017\u0010\u0005\u0002=\u000b&\u0011a)\u0010\u0002\b!J|G-^2u!\tAUJ\u0004\u0002J\u0017:\u0011\u0011FS\u0005\u0002}%\u0011A*P\u0001\ba\u0006\u001c7.Y4f\u0013\tquJ\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002M{\u0005)1m\\;oiV\t!\u000b\u0005\u0002='&\u0011A+\u0010\u0002\u0005\u0019>tw-\u0001\u0004d_VtG\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005aS\u0006cA-\u0001m5\t\u0011\u0004C\u0003Q\u0007\u0001\u0007!+A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012!\u0018\t\u0003=~k\u0011!H\u0005\u0003Av\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005\rL\u0007C\u00013h\u001b\u0005)'B\u00014\u001e\u0003\u0015\u0019H/Y4f\u0013\tAWMA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015QW\u00011\u0001^\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<G#A7\u0011\u00059\u0014hBA8q!\tIS(\u0003\u0002r{\u00051\u0001K]3eK\u001aL!a\u001d;\u0003\rM#(/\u001b8h\u0015\t\tX(\u0001\u0003d_BLXCA<{)\tA8\u0010E\u0002Z\u0001e\u0004\"a\u000e>\u0005\u000be:!\u0019\u0001\u001e\t\u000fA;\u0001\u0013!a\u0001%\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTc\u0001@\u0002\u0014U\tqPK\u0002S\u0003\u0003Y#!a\u0001\u0011\t\u0005\u0015\u0011qB\u0007\u0003\u0003\u000fQA!!\u0003\u0002\f\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u001bi\u0014AC1o]>$\u0018\r^5p]&!\u0011\u0011CA\u0004\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006s!\u0011\rAO\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005e\u0001\u0003BA\u000e\u0003Ki!!!\b\u000b\t\u0005}\u0011\u0011E\u0001\u0005Y\u0006twM\u0003\u0002\u0002$\u0005!!.\u0019<b\u0013\r\u0019\u0018QD\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003W\u00012\u0001PA\u0017\u0013\r\ty#\u0010\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\u0003\u0006U\u0002\"CA\u001c\u0017\u0005\u0005\t\u0019AA\u0016\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\b\t\u0006\u0003\u007f\t)%Q\u0007\u0003\u0003\u0003R1!a\u0011>\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u000f\n\tE\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA'\u0003'\u00022\u0001PA(\u0013\r\t\t&\u0010\u0002\b\u0005>|G.Z1o\u0011!\t9$DA\u0001\u0002\u0004\t\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u0007\u0002Z!I\u0011q\u0007\b\u0002\u0002\u0003\u0007\u00111F\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111F\u0001\u0007KF,\u0018\r\\:\u0015\t\u00055\u00131\r\u0005\t\u0003o\u0001\u0012\u0011!a\u0001\u0003\"\u001a\u0001!a\u001a\u0011\t\u0005%\u0014QN\u0007\u0003\u0003WR1!!\u0004 \u0013\u0011\ty'a\u001b\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0005\tJ|\u0007\u000f\u0005\u0002Z%M)!#a\u001e\u0002~A\u0019A(!\u001f\n\u0007\u0005mTH\u0001\u0004B]f\u0014VM\u001a\t\u0005\u0003\u007f\n))\u0004\u0002\u0002\u0002*!\u00111QA\u0011\u0003\tIw.C\u0002O\u0003\u0003#\"!a\u001d\u0015\u0005\u0005e\u0011!B1qa2LX\u0003BAH\u0003+#B!!%\u0002\u0018B!\u0011\fAAJ!\r9\u0014Q\u0013\u0003\u0006sU\u0011\rA\u000f\u0005\u0006!V\u0001\rAU\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\ti*!,\u0015\t\u0005}\u0015Q\u0015\t\u0005y\u0005\u0005&+C\u0002\u0002$v\u0012aa\u00149uS>t\u0007\"CAT-\u0005\u0005\t\u0019AAU\u0003\rAH\u0005\r\t\u00053\u0002\tY\u000bE\u00028\u0003[#Q!\u000f\fC\u0002i\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a-\u0011\t\u0005m\u0011QW\u0005\u0005\u0003o\u000biB\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class Drop<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final long count;

    public static <T> Option<Object> unapply(Drop<T> x$0) {
        return Drop$.MODULE$.unapply(x$0);
    }

    public static <T> Drop<T> apply(long count) {
        return Drop$.MODULE$.apply(count);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long count() {
        return this.count;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.drop();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private long left;
            private final /* synthetic */ Drop $outer;

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
                if (this.left() > 0L) {
                    this.left_$eq(this.left() - 1L);
                    this.pull(this.$outer.in());
                    return;
                }
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.left = $outer.count();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "Drop";
    }

    public <T> Drop<T> copy(long count) {
        return new Drop<T>(count);
    }

    public <T> long copy$default$1() {
        return this.count();
    }

    @Override
    public String productPrefix() {
        return "Drop";
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
                return BoxesRunTime.boxToLong(this.count());
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
        return x$1 instanceof Drop;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "count";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.longHash(this.count()));
        return Statics.finalizeHash(n, 1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Drop)) return false;
        boolean bl = true;
        if (!bl) return false;
        Drop drop = (Drop)x$1;
        if (this.count() != drop.count()) return false;
        return true;
    }

    public Drop(long count) {
        this.count = count;
        Product.$init$(this);
    }
}

