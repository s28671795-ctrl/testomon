/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.dispatch.AbstractBoundedNodeQueue;
import akka.stream.Attributes;
import akka.stream.BoundedSourceQueue;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.QueueCompletionResult;
import akka.stream.SourceShape;
import akka.stream.impl.BoundedSourceQueueStage$Logic$1$;
import akka.stream.impl.BoundedSourceQueueStage$Mat$1$;
import akka.stream.impl.BoundedSourceQueueStage$NeedsActivation$;
import akka.stream.impl.BoundedSourceQueueStage$Running$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.LazyRef;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\rrA\u0002\u001d:\u0011\u0003itH\u0002\u0004Bs!\u0005QH\u0011\u0005\u0006\u0013\u0006!\ta\u0013\u0004\b\u0019\u0006\u0001\n1%\tN\u000f\u001d\ti(\u0001EA\u000332q!a\u0015\u0002\u0011\u0003\u000b)\u0006\u0003\u0004J\u000b\u0011\u0005\u0011q\u000b\u0005\bw\u0016\t\t\u0011\"\u0011}\u0011%\tY!BA\u0001\n\u0003\ti\u0001C\u0005\u0002\u0016\u0015\t\t\u0011\"\u0001\u0002\\!I\u00111E\u0003\u0002\u0002\u0013\u0005\u0013Q\u0005\u0005\n\u0003g)\u0011\u0011!C\u0001\u0003?B\u0011\"!\u0012\u0006\u0003\u0003%\t%a\u0012\t\u0013\u0005%S!!A\u0005B\u0005-\u0003\"CA2\u000b\u0005\u0005I\u0011BA3\u000f\u001d\ty(\u0001EA\u0003g2q!!\u001c\u0002\u0011\u0003\u000by\u0007\u0003\u0004J!\u0011\u0005\u0011\u0011\u000f\u0005\bwB\t\t\u0011\"\u0011}\u0011%\tY\u0001EA\u0001\n\u0003\ti\u0001C\u0005\u0002\u0016A\t\t\u0011\"\u0001\u0002v!I\u00111\u0005\t\u0002\u0002\u0013\u0005\u0013Q\u0005\u0005\n\u0003g\u0001\u0012\u0011!C\u0001\u0003sB\u0011\"!\u0012\u0011\u0003\u0003%\t%a\u0012\t\u0013\u0005%\u0003#!A\u0005B\u0005-\u0003\"CA2!\u0005\u0005I\u0011BA3\r\u0011y\u0015\u0001\u0011)\t\u0011\tT\"Q3A\u0005\u0002\rD\u0001\u0002\u001b\u000e\u0003\u0012\u0003\u0006I\u0001\u001a\u0005\u0006\u0013j!\t!\u001b\u0005\bYj\t\t\u0011\"\u0001n\u0011\u001dy'$%A\u0005\u0002ADqa\u001f\u000e\u0002\u0002\u0013\u0005C\u0010C\u0005\u0002\fi\t\t\u0011\"\u0001\u0002\u000e!I\u0011Q\u0003\u000e\u0002\u0002\u0013\u0005\u0011q\u0003\u0005\n\u0003GQ\u0012\u0011!C!\u0003KA\u0011\"a\r\u001b\u0003\u0003%\t!!\u000e\t\u0013\u0005}\"$!A\u0005B\u0005\u0005\u0003\"CA#5\u0005\u0005I\u0011IA$\u0011%\tIEGA\u0001\n\u0003\nY\u0005C\u0005\u0002Ni\t\t\u0011\"\u0011\u0002P\u001dI\u0011\u0011Q\u0001\u0002\u0002#\u0005\u00111\u0011\u0004\t\u001f\u0006\t\t\u0011#\u0001\u0002\u0006\"1\u0011J\u000bC\u0001\u0003;C\u0011\"!\u0013+\u0003\u0003%)%a\u0013\t\u0013\u0005}%&!A\u0005\u0002\u0006\u0005\u0006\"CASU\u0005\u0005I\u0011QAT\u0011%\t\u0019GKA\u0001\n\u0013\t)G\u0002\u0004Bs\ti\u0014\u0011\u0019\u0005\u000b\u0003[\u0004$\u0011!Q\u0001\n\u0005=\u0001BB%1\t\u0003\ty\u000fC\u0005\u0002vB\u0012\r\u0011\"\u0001\u0002x\"A\u0011q \u0019!\u0002\u0013\tI\u0010C\u0005\u0003\u0002A\u0012\r\u0011\"\u0001\u0003\u0004!A!Q\u0001\u0019!\u0002\u0013\t\t\u000eC\u0004\u0003\bA\"\tE!\u0003\u0002/\t{WO\u001c3fIN{WO]2f#V,W/Z*uC\u001e,'B\u0001\u001e<\u0003\u0011IW\u000e\u001d7\u000b\u0005qj\u0014AB:ue\u0016\fWNC\u0001?\u0003\u0011\t7n[1\u0011\u0005\u0001\u000bQ\"A\u001d\u0003/\t{WO\u001c3fIN{WO]2f#V,W/Z*uC\u001e,7CA\u0001D!\t!u)D\u0001F\u0015\u00051\u0015!B:dC2\f\u0017B\u0001%F\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001@\u0005\u0015\u0019F/\u0019;f'\t\u00191)\u000b\u0003\u00045\u0015\u0001\"\u0001\u0002#p]\u0016\u001cRAG\"R'Z\u0003\"AU\u0002\u000e\u0003\u0005\u0001\"\u0001\u0012+\n\u0005U+%a\u0002)s_\u0012,8\r\u001e\t\u0003/~s!\u0001W/\u000f\u0005ecV\"\u0001.\u000b\u0005mS\u0015A\u0002\u001fs_>$h(C\u0001G\u0013\tqV)A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0001\f'\u0001D*fe&\fG.\u001b>bE2,'B\u00010F\u0003\u0019\u0011Xm];miV\tA\r\u0005\u0002fM6\t1(\u0003\u0002hw\t)\u0012+^3vK\u000e{W\u000e\u001d7fi&|gNU3tk2$\u0018a\u0002:fgVdG\u000f\t\u000b\u0003U.\u0004\"A\u0015\u000e\t\u000b\tl\u0002\u0019\u00013\u0002\t\r|\u0007/\u001f\u000b\u0003U:DqA\u0019\u0010\u0011\u0002\u0003\u0007A-\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003ET#\u0001\u001a:,\u0003M\u0004\"\u0001^=\u000e\u0003UT!A^<\u0002\u0013Ut7\r[3dW\u0016$'B\u0001=F\u0003)\tgN\\8uCRLwN\\\u0005\u0003uV\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tQ\u0010E\u0002\u007f\u0003\u000fi\u0011a \u0006\u0005\u0003\u0003\t\u0019!\u0001\u0003mC:<'BAA\u0003\u0003\u0011Q\u0017M^1\n\u0007\u0005%qP\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u001f\u00012\u0001RA\t\u0013\r\t\u0019\"\u0012\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u00033\ty\u0002E\u0002E\u00037I1!!\bF\u0005\r\te.\u001f\u0005\n\u0003C\u0011\u0013\u0011!a\u0001\u0003\u001f\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0014!\u0019\tI#a\f\u0002\u001a5\u0011\u00111\u0006\u0006\u0004\u0003[)\u0015AC2pY2,7\r^5p]&!\u0011\u0011GA\u0016\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005]\u0012Q\b\t\u0004\t\u0006e\u0012bAA\u001e\u000b\n9!i\\8mK\u0006t\u0007\"CA\u0011I\u0005\u0005\t\u0019AA\r\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007u\f\u0019\u0005C\u0005\u0002\"\u0015\n\t\u00111\u0001\u0002\u0010\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u0010\u0005AAo\\*ue&tw\rF\u0001~\u0003\u0019)\u0017/^1mgR!\u0011qGA)\u0011%\t\t\u0003KA\u0001\u0002\u0004\tIBA\bOK\u0016$7/Q2uSZ\fG/[8o'\u0015)1)U*W)\t\tI\u0006\u0005\u0002S\u000bQ!\u0011\u0011DA/\u0011%\t\t#CA\u0001\u0002\u0004\ty\u0001\u0006\u0003\u00028\u0005\u0005\u0004\"CA\u0011\u0017\u0005\u0005\t\u0019AA\r\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t9\u0007E\u0002\u007f\u0003SJ1!a\u001b\u0000\u0005\u0019y%M[3di\n9!+\u001e8oS:<7#\u0002\tD#N3FCAA:!\t\u0011\u0006\u0003\u0006\u0003\u0002\u001a\u0005]\u0004\"CA\u0011)\u0005\u0005\t\u0019AA\b)\u0011\t9$a\u001f\t\u0013\u0005\u0005b#!AA\u0002\u0005e\u0011a\u0004(fK\u0012\u001c\u0018i\u0019;jm\u0006$\u0018n\u001c8\u0002\u000fI+hN\\5oO\u0006!Ai\u001c8f!\t\u0011&fE\u0003+\u0003\u000f\u000b\u0019\n\u0005\u0004\u0002\n\u0006=EM[\u0007\u0003\u0003\u0017S1!!$F\u0003\u001d\u0011XO\u001c;j[\u0016LA!!%\u0002\f\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005U\u00151T\u0007\u0003\u0003/SA!!'\u0002\u0004\u0005\u0011\u0011n\\\u0005\u0004A\u0006]ECAAB\u0003\u0015\t\u0007\u000f\u001d7z)\rQ\u00171\u0015\u0005\u0006E6\u0002\r\u0001Z\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\tI+a,\u0011\t\u0011\u000bY\u000bZ\u0005\u0004\u0003[+%AB(qi&|g\u000e\u0003\u0005\u00022:\n\t\u00111\u0001k\u0003\rAH\u0005\r\u0015\u0004\u0003\u0005U\u0006\u0003BA\\\u0003wk!!!/\u000b\u0005al\u0014\u0002BA_\u0003s\u00131\"\u00138uKJt\u0017\r\\!qS\"\u001a\u0001!!.\u0016\t\u0005\r\u00171\\\n\u0004a\u0005\u0015\u0007\u0003CAd\u0003\u001b\f\t.a:\u000e\u0005\u0005%'bAAfw\u0005)1\u000f^1hK&!\u0011qZAe\u0005}9%/\u00199i'R\fw-Z,ji\"l\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/\u001a\t\u0006K\u0006M\u0017q[\u0005\u0004\u0003+\\$aC*pkJ\u001cWm\u00155ba\u0016\u0004B!!7\u0002\\2\u0001AaBAoa\t\u0007\u0011q\u001c\u0002\u0002)F!\u0011\u0011]A\r!\r!\u00151]\u0005\u0004\u0003K,%a\u0002(pi\"Lgn\u001a\t\u0006K\u0006%\u0018q[\u0005\u0004\u0003W\\$A\u0005\"pk:$W\rZ*pkJ\u001cW-U;fk\u0016\f!BY;gM\u0016\u00148+\u001b>f)\u0011\t\t0a=\u0011\t\u0001\u0003\u0014q\u001b\u0005\b\u0003[\u0014\u0004\u0019AA\b\u0003\ryW\u000f^\u000b\u0003\u0003s\u0004R!ZA~\u0003/L1!!@<\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003\u0015\u0019\b.\u00199f+\t\t\t.\u0001\u0004tQ\u0006\u0004X\rI\u0001 GJ,\u0017\r^3M_\u001eL7-\u00118e\u001b\u0006$XM]5bY&TX\r\u001a,bYV,G\u0003\u0002B\u0006\u0005/\u0001r\u0001\u0012B\u0007\u0005#\t9/C\u0002\u0003\u0010\u0015\u0013a\u0001V;qY\u0016\u0014\u0004\u0003BAd\u0005'IAA!\u0006\u0002J\nyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0004\u0003\u001a]\u0002\rAa\u0007\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\u0007\u0015\u0014i\"C\u0002\u0003 m\u0012!\"\u0011;ue&\u0014W\u000f^3tQ\r\u0001\u0014Q\u0017")
@InternalApi
public final class BoundedSourceQueueStage<T>
extends GraphStageWithMaterializedValue<SourceShape<T>, BoundedSourceQueue<T>> {
    public final int akka$stream$impl$BoundedSourceQueueStage$$bufferSize;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, BoundedSourceQueue<T>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        LazyRef Logic$module = new LazyRef();
        LazyRef Mat$module = new LazyRef();
        AtomicReference<BoundedSourceQueueStage$Running$> state = new AtomicReference<BoundedSourceQueueStage$Running$>(BoundedSourceQueueStage$Running$.MODULE$);
        AbstractBoundedNodeQueue queue = new AbstractBoundedNodeQueue<T>(this){};
        return new Tuple2<GraphStageLogic, BoundedSourceQueue<T>>(this.akka$stream$impl$BoundedSourceQueueStage$$Logic$2(Logic$module, queue, state), this.Mat$2(Mat$module, state, queue, Logic$module));
    }

    private final /* synthetic */ BoundedSourceQueueStage$Logic$1$ Logic$lzycompute$1(LazyRef Logic$module$1, AbstractBoundedNodeQueue queue$1, AtomicReference state$1) {
        BoundedSourceQueueStage$Logic$1$ boundedSourceQueueStage$Logic$1$;
        LazyRef lazyRef = Logic$module$1;
        synchronized (lazyRef) {
            boundedSourceQueueStage$Logic$1$ = Logic$module$1.initialized() ? (BoundedSourceQueueStage$Logic$1$)Logic$module$1.value() : Logic$module$1.initialize(new BoundedSourceQueueStage$Logic$1$(this, queue$1, state$1));
        }
        return boundedSourceQueueStage$Logic$1$;
    }

    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    public final BoundedSourceQueueStage$Logic$1$ akka$stream$impl$BoundedSourceQueueStage$$Logic$2(LazyRef Logic$module$1, AbstractBoundedNodeQueue queue$1, AtomicReference state$1) {
        if (Logic$module$1.initialized()) {
            return (BoundedSourceQueueStage$Logic$1$)Logic$module$1.value();
        }
        return this.Logic$lzycompute$1(Logic$module$1, queue$1, state$1);
    }

    private final /* synthetic */ BoundedSourceQueueStage$Mat$1$ Mat$lzycompute$1(LazyRef Mat$module$1, AtomicReference state$1, AbstractBoundedNodeQueue queue$1, LazyRef Logic$module$1) {
        BoundedSourceQueueStage$Mat$1$ boundedSourceQueueStage$Mat$1$;
        LazyRef lazyRef = Mat$module$1;
        synchronized (lazyRef) {
            boundedSourceQueueStage$Mat$1$ = Mat$module$1.initialized() ? (BoundedSourceQueueStage$Mat$1$)Mat$module$1.value() : Mat$module$1.initialize(new BoundedSourceQueueStage$Mat$1$(this, state$1, queue$1, Logic$module$1));
        }
        return boundedSourceQueueStage$Mat$1$;
    }

    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    private final BoundedSourceQueueStage$Mat$1$ Mat$2(LazyRef Mat$module$1, AtomicReference state$1, AbstractBoundedNodeQueue queue$1, LazyRef Logic$module$1) {
        if (Mat$module$1.initialized()) {
            return (BoundedSourceQueueStage$Mat$1$)Mat$module$1.value();
        }
        return this.Mat$lzycompute$1(Mat$module$1, state$1, queue$1, Logic$module$1);
    }

    public final boolean akka$stream$impl$BoundedSourceQueueStage$$setDone$1(Done done, AtomicReference state$1) {
        State state;
        do {
            if (!((state = (State)state$1.get()) instanceof Done)) continue;
            return false;
        } while (!state$1.compareAndSet(state, done));
        return true;
    }

    public final boolean akka$stream$impl$BoundedSourceQueueStage$$clearNeedsActivation$1(AtomicReference state$1) {
        State state;
        while (BoundedSourceQueueStage$NeedsActivation$.MODULE$.equals(state = (State)state$1.get())) {
            if (!state$1.compareAndSet(BoundedSourceQueueStage$NeedsActivation$.MODULE$, BoundedSourceQueueStage$Running$.MODULE$)) continue;
            return true;
        }
        return false;
    }

    public final boolean akka$stream$impl$BoundedSourceQueueStage$$setNeedsActivation$1(AtomicReference state$1) {
        State state;
        while (BoundedSourceQueueStage$Running$.MODULE$.equals(state = (State)state$1.get())) {
            if (!state$1.compareAndSet(BoundedSourceQueueStage$Running$.MODULE$, BoundedSourceQueueStage$NeedsActivation$.MODULE$)) continue;
            return true;
        }
        return false;
    }

    public BoundedSourceQueueStage(int bufferSize) {
        this.akka$stream$impl$BoundedSourceQueueStage$$bufferSize = bufferSize;
        Predef$.MODULE$.require(bufferSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "BoundedSourceQueueStage.bufferSize must be > 0");
        this.out = Outlet$.MODULE$.apply("BoundedSourceQueueStage.out");
        this.shape = new SourceShape<T>(this.out());
    }

    public static class Done
    implements State,
    Product,
    Serializable {
        private final QueueCompletionResult result;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public QueueCompletionResult result() {
            return this.result;
        }

        public Done copy(QueueCompletionResult result) {
            return new Done(result);
        }

        public QueueCompletionResult copy$default$1() {
            return this.result();
        }

        @Override
        public String productPrefix() {
            return "Done";
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
                    return this.result();
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
            return x$1 instanceof Done;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "result";
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
            if (!(object instanceof Done)) return false;
            boolean bl = true;
            if (!bl) return false;
            Done done = (Done)x$1;
            QueueCompletionResult queueCompletionResult = this.result();
            QueueCompletionResult queueCompletionResult2 = done.result();
            if (queueCompletionResult == null) {
                if (queueCompletionResult2 != null) {
                    return false;
                }
            } else if (!queueCompletionResult.equals(queueCompletionResult2)) return false;
            if (!done.canEqual(this)) return false;
            return true;
        }

        public Done(QueueCompletionResult result) {
            this.result = result;
            Product.$init$(this);
        }
    }

    public static interface State {
    }
}

