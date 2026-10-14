/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.Done;
import akka.annotation.InternalApi;
import akka.stream.stage.ConcurrentAsyncCallbackState$;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u001duA\u0002!B\u0011\u0003)uI\u0002\u0004J\u0003\"\u0005QI\u0013\u0005\u0006#\u0006!\ta\u0015\u0004\b)\u0006\u0001\n1%\tV\r\u0019\t\t%\u0001\"\u0002D!Q\u0011q\n\u0003\u0003\u0016\u0004%\t!!\u0015\t\u0015\u0005]GA!E!\u0002\u0013\t\u0019\u0006\u0003\u0004R\t\u0011\u0005\u0011\u0011\u001c\u0005\n\u0003\u001b#\u0011\u0011!C\u0001\u0003?D\u0011\"!(\u0005#\u0003%\t!a<\t\u000fY$\u0011\u0011!C!o\"I\u0011\u0011\u0001\u0003\u0002\u0002\u0013\u0005\u00111\u0001\u0005\n\u0003\u0017!\u0011\u0011!C\u0001\u0003oD\u0011\"a\u0005\u0005\u0003\u0003%\t%!\u0006\t\u0013\u0005\rB!!A\u0005\u0002\u0005m\b\"CAf\t\u0005\u0005I\u0011IA\u0000\u0011%\ty\u0003BA\u0001\n\u0003\n\t\u0004C\u0005\u00024\u0011\t\t\u0011\"\u0011\u00026!I\u0011\u0011\u001b\u0003\u0002\u0002\u0013\u0005#1A\u0004\n\u0005\u000f\t\u0011\u0011!E\u0001\u0005\u00131\u0011\"!\u0011\u0002\u0003\u0003E\tAa\u0003\t\rE#B\u0011\u0001B\f\u0011%\t\u0019\u0004FA\u0001\n\u000b\n)\u0004C\u0005\u0003\u001aQ\t\t\u0011\"!\u0003\u001c!I!1\u0006\u000b\u0002\u0002\u0013\u0005%Q\u0006\u0005\n\u0003o!\u0012\u0011!C\u0005\u0003s9aA!\u0012\u0002\u0011\u0003+h!B1\u0002\u0011\u0003\u0013\u0007\"B)\u001c\t\u0003!\bb\u0002<\u001c\u0003\u0003%\te\u001e\u0005\n\u0003\u0003Y\u0012\u0011!C\u0001\u0003\u0007A\u0011\"a\u0003\u001c\u0003\u0003%\t!!\u0004\t\u0013\u0005M1$!A\u0005B\u0005U\u0001\"CA\u00127\u0005\u0005I\u0011AA\u0013\u0011%\tycGA\u0001\n\u0003\n\t\u0004C\u0005\u00024m\t\t\u0011\"\u0011\u00026!I\u0011qG\u000e\u0002\u0002\u0013%\u0011\u0011\b\u0004\u0007\u00037\n!)!\u0018\t\u0015\u0005\u0005TE!f\u0001\n\u0003\t\u0019\u0007\u0003\u0006\u0002j\u0015\u0012\t\u0012)A\u0005\u0003KB!\"a\u001b&\u0005+\u0007I\u0011AA7\u0011)\t\u0019)\nB\tB\u0003%\u0011q\u000e\u0005\u0007#\u0016\"\t!!\"\t\u0013\u00055U%!A\u0005\u0002\u0005=\u0005\"CAOKE\u0005I\u0011AAP\u0011%\tI,JI\u0001\n\u0003\tY\fC\u0004wK\u0005\u0005I\u0011I<\t\u0013\u0005\u0005Q%!A\u0005\u0002\u0005\r\u0001\"CA\u0006K\u0005\u0005I\u0011AAb\u0011%\t\u0019\"JA\u0001\n\u0003\n)\u0002C\u0005\u0002$\u0015\n\t\u0011\"\u0001\u0002H\"I\u00111Z\u0013\u0002\u0002\u0013\u0005\u0013Q\u001a\u0005\n\u0003_)\u0013\u0011!C!\u0003cA\u0011\"a\r&\u0003\u0003%\t%!\u000e\t\u0013\u0005EW%!A\u0005B\u0005Mw!\u0003B$\u0003\u0005\u0005\t\u0012\u0001B%\r%\tY&AA\u0001\u0012\u0003\u0011Y\u0005\u0003\u0004Rq\u0011\u0005!Q\n\u0005\n\u0003gA\u0014\u0011!C#\u0003kA\u0011B!\u00079\u0003\u0003%\tIa\u0014\t\u0013\t-\u0002(!A\u0005\u0002\nu\u0003\"CA\u001cq\u0005\u0005I\u0011BA\u001d\u0011%\u0011\t(\u0001b\u0001\n\u0003\u0011\u0019\b\u0003\u0005\u0003x\u0005\u0001\u000b\u0011\u0002B;\u0003q\u0019uN\\2veJ,g\u000e^!ts:\u001c7)\u00197mE\u0006\u001c7n\u0015;bi\u0016T!AQ\"\u0002\u000bM$\u0018mZ3\u000b\u0005\u0011+\u0015AB:ue\u0016\fWNC\u0001G\u0003\u0011\t7n[1\u0011\u0005!\u000bQ\"A!\u00039\r{gnY;se\u0016tG/Q:z]\u000e\u001c\u0015\r\u001c7cC\u000e\\7\u000b^1uKN\u0011\u0011a\u0013\t\u0003\u0019>k\u0011!\u0014\u0006\u0002\u001d\u0006)1oY1mC&\u0011\u0001+\u0014\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012a\u0012\u0002\u0006'R\fG/Z\u000b\u0003-^\u001b\"aA&\u0005\ra\u001bAQ1\u0001Z\u0005\u0005)\u0015C\u0001.^!\ta5,\u0003\u0002]\u001b\n9aj\u001c;iS:<\u0007C\u0001'_\u0013\tyVJA\u0002B]fL3aA\u000e\u0005\u0005-Ie.\u001b;jC2L'0\u001a3\u0014\u000bmY5-\u001a5\u0011\u0007\u0011\u001c!,D\u0001\u0002!\tae-\u0003\u0002h\u001b\n9\u0001K]8ek\u000e$\bCA5r\u001d\tQwN\u0004\u0002l]6\tAN\u0003\u0002n%\u00061AH]8pizJ\u0011AT\u0005\u0003a6\u000bq\u0001]1dW\u0006<W-\u0003\u0002sg\na1+\u001a:jC2L'0\u00192mK*\u0011\u0001/\u0014\u000b\u0002kB\u0011AmG\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003a\u0004\"!\u001f@\u000e\u0003iT!a\u001f?\u0002\t1\fgn\u001a\u0006\u0002{\u0006!!.\u0019<b\u0013\ty(P\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000b\u00012\u0001TA\u0004\u0013\r\tI!\u0014\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004;\u0006=\u0001\"CA\t?\u0005\u0005\t\u0019AA\u0003\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0003\t\u0006\u00033\ty\"X\u0007\u0003\u00037Q1!!\bN\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\tYB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0014\u0003[\u00012\u0001TA\u0015\u0013\r\tY#\u0014\u0002\b\u0005>|G.Z1o\u0011!\t\t\"IA\u0001\u0002\u0004i\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u0015\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003a\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u000f\u0011\u0007e\fi$C\u0002\u0002@i\u0014aa\u00142kK\u000e$(a\u0002)f]\u0012LgnZ\u000b\u0005\u0003\u000b\nie\u0005\u0004\u0005\u0017\u0006\u001dS\r\u001b\t\u0005I\u000e\tI\u0005\u0005\u0003\u0002L\u00055C\u0002\u0001\u0003\u00061\u0012\u0011\r!W\u0001\u000ea\u0016tG-\u001b8h\u000bZ,g\u000e^:\u0016\u0005\u0005M\u0003#B5\u0002V\u0005e\u0013bAA,g\n!A*[:u!\u0011!W%!\u0013\u0003\u000b\u00153XM\u001c;\u0016\t\u0005}\u0013qM\n\u0005K-+\u0007.A\u0001f+\t\t)\u0007\u0005\u0003\u0002L\u0005\u001dDA\u0002-&\t\u000b\u0007\u0011,\u0001\u0002fA\u0005y\u0001.\u00198eY&tw\r\u0015:p[&\u001cX-\u0006\u0002\u0002pA1\u0011\u0011OA<\u0003wj!!a\u001d\u000b\u0007\u0005UT*\u0001\u0006d_:\u001cWO\u001d:f]RLA!!\u001f\u0002t\t9\u0001K]8nSN,\u0007\u0003BA?\u0003\u007fj\u0011!R\u0005\u0004\u0003\u0003+%\u0001\u0002#p]\u0016\f\u0001\u0003[1oI2Lgn\u001a)s_6L7/\u001a\u0011\u0015\r\u0005\u001d\u0015\u0011RAF!\u0011!W%!\u001a\t\u000f\u0005\u0005$\u00061\u0001\u0002f!9\u00111\u000e\u0016A\u0002\u0005=\u0014\u0001B2paf,B!!%\u0002\u0018R1\u00111SAM\u00037\u0003B\u0001Z\u0013\u0002\u0016B!\u00111JAL\t\u0015A6F1\u0001Z\u0011%\t\tg\u000bI\u0001\u0002\u0004\t)\nC\u0005\u0002l-\u0002\n\u00111\u0001\u0002p\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BAQ\u0003o+\"!a)+\t\u0005\u0015\u0014QU\u0016\u0003\u0003O\u0003B!!+\u000246\u0011\u00111\u0016\u0006\u0005\u0003[\u000by+A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011W'\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u00026\u0006-&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)\u0001\f\fb\u00013\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BA_\u0003\u0003,\"!a0+\t\u0005=\u0014Q\u0015\u0003\u000616\u0012\r!\u0017\u000b\u0004;\u0006\u0015\u0007\"CA\ta\u0005\u0005\t\u0019AA\u0003)\u0011\t9#!3\t\u0011\u0005E!'!AA\u0002u\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019\u00010a4\t\u0013\u0005E1'!AA\u0002\u0005\u0015\u0011AB3rk\u0006d7\u000f\u0006\u0003\u0002(\u0005U\u0007\u0002CA\tm\u0005\u0005\t\u0019A/\u0002\u001dA,g\u000eZ5oO\u00163XM\u001c;tAQ!\u00111\\Ao!\u0011!G!!\u0013\t\u000f\u0005=s\u00011\u0001\u0002TU!\u0011\u0011]At)\u0011\t\u0019/!;\u0011\t\u0011$\u0011Q\u001d\t\u0005\u0003\u0017\n9\u000fB\u0003Y\u0011\t\u0007\u0011\fC\u0005\u0002P!\u0001\n\u00111\u0001\u0002lB)\u0011.!\u0016\u0002nB!A-JAs+\u0011\t\t0!>\u0016\u0005\u0005M(\u0006BA*\u0003K#Q\u0001W\u0005C\u0002e#2!XA}\u0011%\t\t\u0002DA\u0001\u0002\u0004\t)\u0001\u0006\u0003\u0002(\u0005u\b\u0002CA\t\u001d\u0005\u0005\t\u0019A/\u0015\u0007a\u0014\t\u0001C\u0005\u0002\u0012=\t\t\u00111\u0001\u0002\u0006Q!\u0011q\u0005B\u0003\u0011!\t\tBEA\u0001\u0002\u0004i\u0016a\u0002)f]\u0012Lgn\u001a\t\u0003IR\u0019B\u0001F&\u0003\u000eA!!q\u0002B\u000b\u001b\t\u0011\tBC\u0002\u0003\u0014q\f!![8\n\u0007I\u0014\t\u0002\u0006\u0002\u0003\n\u0005)\u0011\r\u001d9msV!!Q\u0004B\u0012)\u0011\u0011yB!\n\u0011\t\u0011$!\u0011\u0005\t\u0005\u0003\u0017\u0012\u0019\u0003B\u0003Y/\t\u0007\u0011\fC\u0004\u0002P]\u0001\rAa\n\u0011\u000b%\f)F!\u000b\u0011\t\u0011,#\u0011E\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\u0011yC!\u0010\u0015\t\tE\"q\b\t\u0006\u0019\nM\"qG\u0005\u0004\u0005ki%AB(qi&|g\u000eE\u0003j\u0003+\u0012I\u0004\u0005\u0003eK\tm\u0002\u0003BA&\u0005{!Q\u0001\u0017\rC\u0002eC\u0011B!\u0011\u0019\u0003\u0003\u0005\rAa\u0011\u0002\u0007a$\u0003\u0007\u0005\u0003e\t\tm\u0012aC%oSRL\u0017\r\\5{K\u0012\fQ!\u0012<f]R\u0004\"\u0001\u001a\u001d\u0014\taZ%Q\u0002\u000b\u0003\u0005\u0013*BA!\u0015\u0003XQ1!1\u000bB-\u00057\u0002B\u0001Z\u0013\u0003VA!\u00111\nB,\t\u0015A6H1\u0001Z\u0011\u001d\t\tg\u000fa\u0001\u0005+Bq!a\u001b<\u0001\u0004\ty'\u0006\u0003\u0003`\t-D\u0003\u0002B1\u0005[\u0002R\u0001\u0014B\u001a\u0005G\u0002r\u0001\u0014B3\u0005S\ny'C\u0002\u0003h5\u0013a\u0001V;qY\u0016\u0014\u0004\u0003BA&\u0005W\"Q\u0001\u0017\u001fC\u0002eC\u0011B!\u0011=\u0003\u0003\u0005\rAa\u001c\u0011\t\u0011,#\u0011N\u0001\u0010\u001d>\u0004VM\u001c3j]\u001e,e/\u001a8ugV\u0011!Q\u000f\t\u0004I\u0012Q\u0016\u0001\u0005(p!\u0016tG-\u001b8h\u000bZ,g\u000e^:!Q\r\t!1\u0010\t\u0005\u0005{\u0012\t)\u0004\u0002\u0003\u0000)\u0019\u0011\u0011W#\n\t\t\r%q\u0010\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eK\u0002\u0001\u0005w\u0002")
@InternalApi
public final class ConcurrentAsyncCallbackState {
    public static Pending<Nothing$> NoPendingEvents() {
        return ConcurrentAsyncCallbackState$.MODULE$.NoPendingEvents();
    }

    public static final class Event<E>
    implements Product,
    Serializable {
        private final E e;
        private final Promise<Done> handlingPromise;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public E e() {
            return this.e;
        }

        public Promise<Done> handlingPromise() {
            return this.handlingPromise;
        }

        public <E> Event<E> copy(E e, Promise<Done> handlingPromise) {
            return new Event<E>(e, handlingPromise);
        }

        public <E> E copy$default$1() {
            return this.e();
        }

        public <E> Promise<Done> copy$default$2() {
            return this.handlingPromise();
        }

        @Override
        public String productPrefix() {
            return "Event";
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
                    return this.e();
                }
                case 1: {
                    return this.handlingPromise();
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
            return x$1 instanceof Event;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "e";
                }
                case 1: {
                    return "handlingPromise";
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
            if (!(object instanceof Event)) return false;
            boolean bl = true;
            if (!bl) return false;
            Event event = (Event)x$1;
            if (!BoxesRunTime.equals(this.e(), event.e())) return false;
            Promise<Done> promise = this.handlingPromise();
            Promise<Done> promise2 = event.handlingPromise();
            if (promise != null) {
                if (!promise.equals(promise2)) return false;
                return true;
            }
            if (promise2 == null) return true;
            return false;
        }

        public Event(E e, Promise<Done> handlingPromise) {
            this.e = e;
            this.handlingPromise = handlingPromise;
            Product.$init$(this);
        }
    }

    public static final class Pending<E>
    implements State<E>,
    Product,
    Serializable {
        private final List<Event<E>> pendingEvents;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public List<Event<E>> pendingEvents() {
            return this.pendingEvents;
        }

        public <E> Pending<E> copy(List<Event<E>> pendingEvents) {
            return new Pending<E>(pendingEvents);
        }

        public <E> List<Event<E>> copy$default$1() {
            return this.pendingEvents();
        }

        @Override
        public String productPrefix() {
            return "Pending";
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
                    return this.pendingEvents();
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
            return x$1 instanceof Pending;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "pendingEvents";
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
            if (!(object instanceof Pending)) return false;
            boolean bl = true;
            if (!bl) return false;
            Pending pending = (Pending)x$1;
            List<Event<E>> list = this.pendingEvents();
            List<Event<E>> list2 = pending.pendingEvents();
            if (list != null) {
                if (!((Object)list).equals(list2)) return false;
                return true;
            }
            if (list2 == null) return true;
            return false;
        }

        public Pending(List<Event<E>> pendingEvents) {
            this.pendingEvents = pendingEvents;
            Product.$init$(this);
        }
    }

    public static interface State<E> {
    }
}

