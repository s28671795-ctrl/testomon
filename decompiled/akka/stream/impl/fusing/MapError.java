/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Collect$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.MapError$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005g!\u0002\r\u001a\u0005~\t\u0003\u0002\u0003)\u0001\u0005+\u0007I\u0011A)\t\u0011a\u0003!\u0011#Q\u0001\nICQ!\u0017\u0001\u0005\u0002iCQA\u0018\u0001\u0005R}CQ\u0001\u001a\u0001\u0005B\u0015Dqa\u001e\u0001\u0002\u0002\u0013\u0005\u0001\u0010C\u0004\u007f\u0001E\u0005I\u0011A@\t\u0013\u0005e\u0001!!A\u0005B\u0005m\u0001\"CA\u0017\u0001\u0005\u0005I\u0011AA\u0018\u0011%\t9\u0004AA\u0001\n\u0003\tI\u0004C\u0005\u0002@\u0001\t\t\u0011\"\u0011\u0002B!I\u0011q\n\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u000b\u0005\n\u00037\u0002\u0011\u0011!C!\u0003;B\u0011\"!\u0019\u0001\u0003\u0003%\t%a\u0019\t\u0013\u0005\u0015\u0004!!A\u0005B\u0005\u001d\u0004\"CA5\u0001\u0005\u0005I\u0011IA6\u000f)\tY(GA\u0001\u0012\u0003y\u0012Q\u0010\u0004\n1e\t\t\u0011#\u0001 \u0003\u007fBa!\u0017\n\u0005\u0002\u0005E\u0005\"CA3%\u0005\u0005IQIA4\u0011%\t\u0019JEA\u0001\n\u0003\u000b)\nC\u0005\u0002\"J\t\t\u0011\"!\u0002$\"I\u0011q\u0017\n\u0002\u0002\u0013%\u0011\u0011\u0018\u0002\t\u001b\u0006\u0004XI\u001d:pe*\u0011!dG\u0001\u0007MV\u001c\u0018N\\4\u000b\u0005qi\u0012\u0001B5na2T!AH\u0010\u0002\rM$(/Z1n\u0015\u0005\u0001\u0013\u0001B1lW\u0006,\"A\t\u001d\u0014\t\u0001\u0019Ci\u0012\t\u0004IM2dBA\u00132\u001d\t1\u0003G\u0004\u0002(_9\u0011\u0001F\f\b\u0003S5j\u0011A\u000b\u0006\u0003W1\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002A%\u0011adH\u0005\u00039uI!AG\u000e\n\u0005IJ\u0012aC$sCBD7\u000b^1hKNL!\u0001N\u001b\u0003-MKW\u000e\u001d7f\u0019&tW-\u0019:He\u0006\u0004\bn\u0015;bO\u0016T!AM\r\u0011\u0005]BD\u0002\u0001\u0003\u0006s\u0001\u0011\rA\u000f\u0002\u0002)F\u00111(\u0011\t\u0003y}j\u0011!\u0010\u0006\u0002}\u0005)1oY1mC&\u0011\u0001)\u0010\u0002\b\u001d>$\b.\u001b8h!\ta$)\u0003\u0002D{\t\u0019\u0011I\\=\u0011\u0005q*\u0015B\u0001$>\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001S'\u000f\u0005%[eBA\u0015K\u0013\u0005q\u0014B\u0001'>\u0003\u001d\u0001\u0018mY6bO\u0016L!AT(\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u00051k\u0014!\u00014\u0016\u0003I\u0003B\u0001P*V+&\u0011A+\u0010\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011\u0001JV\u0005\u0003/>\u0013\u0011\u0002\u00165s_^\f'\r\\3\u0002\u0005\u0019\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002\\;B\u0019A\f\u0001\u001c\u000e\u0003eAQ\u0001U\u0002A\u0002I\u000b\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005\u0001\u0007CA1c\u001b\u0005i\u0012BA2\u001e\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002gkJ!q-[8s\r\u0011AW\u0001\u00014\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0005)lW\"A6\u000b\u00051l\u0012!B:uC\u001e,\u0017B\u00018l\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007C\u00016q\u0013\t\t8NA\u0005J]\"\u000bg\u000e\u001a7feB\u0011!n]\u0005\u0003i.\u0014!bT;u\u0011\u0006tG\r\\3s\u0011\u00151X\u00011\u0001a\u0003\u0011\tG\u000f\u001e:\u0002\t\r|\u0007/_\u000b\u0003sr$\"A_?\u0011\u0007q\u00031\u0010\u0005\u00028y\u0012)\u0011H\u0002b\u0001u!9\u0001K\u0002I\u0001\u0002\u0004\u0011\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0005\u0003\u0003\t9\"\u0006\u0002\u0002\u0004)\u001a!+!\u0002,\u0005\u0005\u001d\u0001\u0003BA\u0005\u0003'i!!a\u0003\u000b\t\u00055\u0011qB\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u0005>\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003+\tYAA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q!O\u0004C\u0002i\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\u000f!\u0011\ty\"!\u000b\u000e\u0005\u0005\u0005\"\u0002BA\u0012\u0003K\tA\u0001\\1oO*\u0011\u0011qE\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002,\u0005\u0005\"AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u00022A\u0019A(a\r\n\u0007\u0005URHA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002B\u0003wA\u0011\"!\u0010\u000b\u0003\u0003\u0005\r!!\r\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\u0019\u0005E\u0003\u0002F\u0005-\u0013)\u0004\u0002\u0002H)\u0019\u0011\u0011J\u001f\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002N\u0005\u001d#\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u0015\u0002ZA\u0019A(!\u0016\n\u0007\u0005]SHA\u0004C_>dW-\u00198\t\u0011\u0005uB\"!AA\u0002\u0005\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QDA0\u0011%\ti$DA\u0001\u0002\u0004\t\t$\u0001\u0005iCND7i\u001c3f)\t\t\t$\u0001\u0005u_N#(/\u001b8h)\t\ti\"\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003'\ni\u0007\u0003\u0005\u0002>A\t\t\u00111\u0001BQ\r\u0001\u0011\u0011\u000f\t\u0005\u0003g\n9(\u0004\u0002\u0002v)\u0019\u0011\u0011C\u0010\n\t\u0005e\u0014Q\u000f\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u0005NCB,%O]8s!\ta&cE\u0003\u0013\u0003\u0003\u000b9\tE\u0002=\u0003\u0007K1!!\">\u0005\u0019\te.\u001f*fMB!\u0011\u0011RAH\u001b\t\tYI\u0003\u0003\u0002\u000e\u0006\u0015\u0012AA5p\u0013\rq\u00151\u0012\u000b\u0003\u0003{\nQ!\u00199qYf,B!a&\u0002\u001eR!\u0011\u0011TAP!\u0011a\u0006!a'\u0011\u0007]\ni\nB\u0003:+\t\u0007!\bC\u0003Q+\u0001\u0007!+A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\u0005\u0015\u0016Q\u0017\u000b\u0005\u0003O\u000bi\u000b\u0005\u0003=\u0003S\u0013\u0016bAAV{\t1q\n\u001d;j_:D\u0011\"a,\u0017\u0003\u0003\u0005\r!!-\u0002\u0007a$\u0003\u0007\u0005\u0003]\u0001\u0005M\u0006cA\u001c\u00026\u0012)\u0011H\u0006b\u0001u\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u0018\t\u0005\u0003?\ti,\u0003\u0003\u0002@\u0006\u0005\"AB(cU\u0016\u001cG\u000f")
@InternalApi
public final class MapError<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final PartialFunction<Throwable, Throwable> f;

    public static <T> Option<PartialFunction<Throwable, Throwable>> unapply(MapError<T> x$0) {
        return MapError$.MODULE$.unapply(x$0);
    }

    public static <T> MapError<T> apply(PartialFunction<Throwable, Throwable> f) {
        return MapError$.MODULE$.apply(f);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public PartialFunction<Throwable, Throwable> f() {
        return this.f;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.mapError();
    }

    @Override
    public GraphStageLogic createLogic(Attributes attr) {
        return new InHandler(this){
            private final /* synthetic */ MapError $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onPush() {
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public void onUpstreamFailure(Throwable ex) {
                Object object = this.$outer.f().applyOrElse(ex, Collect$.MODULE$.NotApplied());
                Function1<Object, Object> function1 = Collect$.MODULE$.NotApplied();
                Object object2 = object;
                if (!(function1 != null ? !function1.equals(object2) : object2 != null)) {
                    InHandler.onUpstreamFailure$(this, ex);
                    return;
                }
                if (object instanceof Throwable) {
                    Throwable throwable = (Throwable)object;
                    InHandler.onUpstreamFailure$(this, throwable);
                    return;
                }
                throw new IllegalStateException();
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
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public <T> MapError<T> copy(PartialFunction<Throwable, Throwable> f) {
        return new MapError<T>(f);
    }

    public <T> PartialFunction<Throwable, Throwable> copy$default$1() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "MapError";
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
        return x$1 instanceof MapError;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "f";
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
        if (!(object instanceof MapError)) return false;
        boolean bl = true;
        if (!bl) return false;
        MapError mapError2 = (MapError)x$1;
        PartialFunction<Throwable, Throwable> partialFunction = this.f();
        PartialFunction<Throwable, Throwable> partialFunction2 = mapError2.f();
        if (partialFunction != null) {
            if (!partialFunction.equals(partialFunction2)) return false;
            return true;
        }
        if (partialFunction2 == null) return true;
        return false;
    }

    public MapError(PartialFunction<Throwable, Throwable> f) {
        this.f = f;
        Product.$init$(this);
    }
}

