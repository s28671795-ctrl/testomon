/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.actor.Cancellable;
import akka.actor.DeadLetterSuppression;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%q!\u0002\u001d:\u0011\u0013\u0001e!\u0002\":\u0011\u0013\u0019\u0005\"\u0002&\u0002\t\u0003Ye\u0001\u0002'\u0002\u00056C\u0001bY\u0002\u0003\u0016\u0004%\t\u0001\u001a\u0005\tQ\u000e\u0011\t\u0012)A\u0005K\"A\u0011n\u0001BK\u0002\u0013\u0005!\u000e\u0003\u0005o\u0007\tE\t\u0015!\u0003l\u0011!y7A!f\u0001\n\u0003\u0001\b\u0002\u0003;\u0004\u0005#\u0005\u000b\u0011B9\t\u000b)\u001bA\u0011A;\t\u000fm\u001c\u0011\u0011!C\u0001y\"I\u0011\u0011A\u0002\u0012\u0002\u0013\u0005\u00111\u0001\u0005\n\u00033\u0019\u0011\u0013!C\u0001\u00037A\u0011\"a\b\u0004#\u0003%\t!!\t\t\u0013\u0005\u00152!!A\u0005B\u0005\u001d\u0002\u0002CA\u001d\u0007\u0005\u0005I\u0011\u00016\t\u0013\u0005m2!!A\u0005\u0002\u0005u\u0002\"CA\"\u0007\u0005\u0005I\u0011IA#\u0011%\t\u0019fAA\u0001\n\u0003\t)\u0006C\u0005\u0002Z\r\t\t\u0011\"\u0011\u0002\\!I\u0011qL\u0002\u0002\u0002\u0013\u0005\u0013\u0011\r\u0005\n\u0003G\u001a\u0011\u0011!C!\u0003KB\u0011\"a\u001a\u0004\u0003\u0003%\t%!\u001b\b\u0013\u00055\u0014!!A\t\u0002\u0005=d\u0001\u0003'\u0002\u0003\u0003E\t!!\u001d\t\r)KB\u0011AAE\u0011%\t\u0019'GA\u0001\n\u000b\n)\u0007C\u0005\u0002\ff\t\t\u0011\"!\u0002\u000e\"I\u0011QS\r\u0002\u0002\u0013\u0005\u0015q\u0013\u0005\n\u0003SK\u0012\u0011!C\u0005\u0003W3a!a-\u0002\u0005\u0006U\u0006\"CA\\?\tU\r\u0011\"\u0001k\u0011%\tIl\bB\tB\u0003%1\u000e\u0003\u0006\u0002<~\u0011)\u001a!C\u0001\u0003{C!\"!2 \u0005#\u0005\u000b\u0011BA`\u0011\u0019Qu\u0004\"\u0001\u0002H\"A1pHA\u0001\n\u0003\ty\rC\u0005\u0002\u0002}\t\n\u0011\"\u0001\u0002\u001c!I\u0011\u0011D\u0010\u0012\u0002\u0013\u0005\u0011Q\u001b\u0005\n\u0003Ky\u0012\u0011!C!\u0003OA\u0001\"!\u000f \u0003\u0003%\tA\u001b\u0005\n\u0003wy\u0012\u0011!C\u0001\u00033D\u0011\"a\u0011 \u0003\u0003%\t%!\u0012\t\u0013\u0005Ms$!A\u0005\u0002\u0005u\u0007\"CA-?\u0005\u0005I\u0011IAq\u0011%\tyfHA\u0001\n\u0003\n\t\u0007C\u0005\u0002d}\t\t\u0011\"\u0011\u0002f!I\u0011qM\u0010\u0002\u0002\u0013\u0005\u0013Q]\u0004\n\u0003S\f\u0011\u0011!E\u0001\u0003W4\u0011\"a-\u0002\u0003\u0003E\t!!<\t\r)\u0013D\u0011AA{\u0011%\t\u0019GMA\u0001\n\u000b\n)\u0007C\u0005\u0002\fJ\n\t\u0011\"!\u0002x\"I\u0011Q\u0013\u001a\u0002\u0002\u0013\u0005\u0015Q \u0005\n\u0003S\u0013\u0014\u0011!C\u0005\u0003W\u000bQ\u0002V5nKJlUm]:bO\u0016\u001c(B\u0001\u001e<\u0003\u0015\u0019H/Y4f\u0015\taT(\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002}\u0005!\u0011m[6b\u0007\u0001\u0001\"!Q\u0001\u000e\u0003e\u0012Q\u0002V5nKJlUm]:bO\u0016\u001c8CA\u0001E!\t)\u0005*D\u0001G\u0015\u00059\u0015!B:dC2\f\u0017BA%G\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001\u0011\u0002\n'\u000eDW\rZ;mK\u0012\u001cRa\u0001#O)^\u0003\"a\u0014*\u000e\u0003AS!!U\u001f\u0002\u000b\u0005\u001cGo\u001c:\n\u0005M\u0003&!\u0006#fC\u0012dU\r\u001e;feN+\b\u000f\u001d:fgNLwN\u001c\t\u0003\u000bVK!A\u0016$\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001\f\u0019\b\u00033zs!AW/\u000e\u0003mS!\u0001X \u0002\rq\u0012xn\u001c;?\u0013\u00059\u0015BA0G\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u00192\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005}3\u0015\u0001\u0003;j[\u0016\u00148*Z=\u0016\u0003\u0015\u0004\"!\u00124\n\u0005\u001d4%aA!os\u0006IA/[7fe.+\u0017\u0010I\u0001\bi&lWM]%e+\u0005Y\u0007CA#m\u0013\tigIA\u0002J]R\f\u0001\u0002^5nKJLE\rI\u0001\ne\u0016\u0004X-\u0019;j]\u001e,\u0012!\u001d\t\u0003\u000bJL!a\u001d$\u0003\u000f\t{w\u000e\\3b]\u0006Q!/\u001a9fCRLgn\u001a\u0011\u0015\tYD\u0018P\u001f\t\u0003o\u000ei\u0011!\u0001\u0005\u0006G*\u0001\r!\u001a\u0005\u0006S*\u0001\ra\u001b\u0005\u0006_*\u0001\r!]\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003w{z|\bbB2\f!\u0003\u0005\r!\u001a\u0005\bS.\u0001\n\u00111\u0001l\u0011\u001dy7\u0002%AA\u0002E\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002\u0006)\u001aQ-a\u0002,\u0005\u0005%\u0001\u0003BA\u0006\u0003+i!!!\u0004\u000b\t\u0005=\u0011\u0011C\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u0005G\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003/\tiAA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002\u001e)\u001a1.a\u0002\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u00111\u0005\u0016\u0004c\u0006\u001d\u0011!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002*A!\u00111FA\u001b\u001b\t\tiC\u0003\u0003\u00020\u0005E\u0012\u0001\u00027b]\u001eT!!a\r\u0002\t)\fg/Y\u0005\u0005\u0003o\tiC\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r)\u0017q\b\u0005\t\u0003\u0003\n\u0012\u0011!a\u0001W\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0012\u0011\u000b\u0005%\u0013qJ3\u000e\u0005\u0005-#bAA'\r\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005E\u00131\n\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000eF\u0002r\u0003/B\u0001\"!\u0011\u0014\u0003\u0003\u0005\r!Z\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002*\u0005u\u0003\u0002CA!)\u0005\u0005\t\u0019A6\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012a[\u0001\ti>\u001cFO]5oOR\u0011\u0011\u0011F\u0001\u0007KF,\u0018\r\\:\u0015\u0007E\fY\u0007\u0003\u0005\u0002B]\t\t\u00111\u0001f\u0003%\u00196\r[3ek2,G\r\u0005\u0002x3M)\u0011$a\u001d\u0002\u0000AA\u0011QOA>K.\fh/\u0004\u0002\u0002x)\u0019\u0011\u0011\u0010$\u0002\u000fI,h\u000e^5nK&!\u0011QPA<\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gn\r\t\u0005\u0003\u0003\u000b9)\u0004\u0002\u0002\u0004*!\u0011QQA\u0019\u0003\tIw.C\u0002b\u0003\u0007#\"!a\u001c\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000fY\fy)!%\u0002\u0014\")1\r\ba\u0001K\")\u0011\u000e\ba\u0001W\")q\u000e\ba\u0001c\u00069QO\\1qa2LH\u0003BAM\u0003K\u0003R!RAN\u0003?K1!!(G\u0005\u0019y\u0005\u000f^5p]B1Q)!)fWFL1!a)G\u0005\u0019!V\u000f\u001d7fg!A\u0011qU\u000f\u0002\u0002\u0003\u0007a/A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!,\u0011\t\u0005-\u0012qV\u0005\u0005\u0003c\u000biC\u0001\u0004PE*,7\r\u001e\u0002\u0006)&lWM]\n\u0005?\u0011#v+\u0001\u0002jI\u0006\u0019\u0011\u000e\u001a\u0011\u0002\tQ\f7o[\u000b\u0003\u0003\u007f\u00032aTAa\u0013\r\t\u0019\r\u0015\u0002\f\u0007\u0006t7-\u001a7mC\ndW-A\u0003uCN\\\u0007\u0005\u0006\u0004\u0002J\u0006-\u0017Q\u001a\t\u0003o~Aa!a.%\u0001\u0004Y\u0007bBA^I\u0001\u0007\u0011q\u0018\u000b\u0007\u0003\u0013\f\t.a5\t\u0011\u0005]V\u0005%AA\u0002-D\u0011\"a/&!\u0003\u0005\r!a0\u0016\u0005\u0005]'\u0006BA`\u0003\u000f!2!ZAn\u0011!\t\tEKA\u0001\u0002\u0004YGcA9\u0002`\"A\u0011\u0011\t\u0017\u0002\u0002\u0003\u0007Q\r\u0006\u0003\u0002*\u0005\r\b\u0002CA![\u0005\u0005\t\u0019A6\u0015\u0007E\f9\u000f\u0003\u0005\u0002BA\n\t\u00111\u0001f\u0003\u0015!\u0016.\\3s!\t9(gE\u00033\u0003_\fy\bE\u0005\u0002v\u0005E8.a0\u0002J&!\u00111_A<\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gN\r\u000b\u0003\u0003W$b!!3\u0002z\u0006m\bBBA\\k\u0001\u00071\u000eC\u0004\u0002<V\u0002\r!a0\u0015\t\u0005}(q\u0001\t\u0006\u000b\u0006m%\u0011\u0001\t\u0007\u000b\n\r1.a0\n\u0007\t\u0015aI\u0001\u0004UkBdWM\r\u0005\n\u0003O3\u0014\u0011!a\u0001\u0003\u0013\u0004")
public final class TimerMessages {

    public static final class Scheduled
    implements DeadLetterSuppression,
    Product,
    Serializable {
        private final Object timerKey;
        private final int timerId;
        private final boolean repeating;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Object timerKey() {
            return this.timerKey;
        }

        public int timerId() {
            return this.timerId;
        }

        public boolean repeating() {
            return this.repeating;
        }

        public Scheduled copy(Object timerKey, int timerId, boolean repeating) {
            return new Scheduled(timerKey, timerId, repeating);
        }

        public Object copy$default$1() {
            return this.timerKey();
        }

        public int copy$default$2() {
            return this.timerId();
        }

        public boolean copy$default$3() {
            return this.repeating();
        }

        @Override
        public String productPrefix() {
            return "Scheduled";
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
                    return this.timerKey();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.timerId());
                }
                case 2: {
                    return BoxesRunTime.boxToBoolean(this.repeating());
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
            return x$1 instanceof Scheduled;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "timerKey";
                }
                case 1: {
                    return "timerId";
                }
                case 2: {
                    return "repeating";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.timerKey()));
            n = Statics.mix(n, this.timerId());
            n = Statics.mix(n, this.repeating() ? 1231 : 1237);
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof Scheduled)) return false;
            boolean bl = true;
            if (!bl) return false;
            Scheduled scheduled = (Scheduled)x$1;
            if (this.timerId() != scheduled.timerId()) return false;
            if (this.repeating() != scheduled.repeating()) return false;
            if (!BoxesRunTime.equals(this.timerKey(), scheduled.timerKey())) return false;
            return true;
        }

        public Scheduled(Object timerKey, int timerId, boolean repeating) {
            this.timerKey = timerKey;
            this.timerId = timerId;
            this.repeating = repeating;
            Product.$init$(this);
        }
    }

    public static final class Timer
    implements Product,
    Serializable {
        private final int id;
        private final Cancellable task;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int id() {
            return this.id;
        }

        public Cancellable task() {
            return this.task;
        }

        public Timer copy(int id, Cancellable task) {
            return new Timer(id, task);
        }

        public int copy$default$1() {
            return this.id();
        }

        public Cancellable copy$default$2() {
            return this.task();
        }

        @Override
        public String productPrefix() {
            return "Timer";
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
                    return BoxesRunTime.boxToInteger(this.id());
                }
                case 1: {
                    return this.task();
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
            return x$1 instanceof Timer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "task";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.id());
            n = Statics.mix(n, Statics.anyHash(this.task()));
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
            if (!(object instanceof Timer)) return false;
            boolean bl = true;
            if (!bl) return false;
            Timer timer = (Timer)x$1;
            if (this.id() != timer.id()) return false;
            Cancellable cancellable = this.task();
            Cancellable cancellable2 = timer.task();
            if (cancellable != null) {
                if (!cancellable.equals(cancellable2)) return false;
                return true;
            }
            if (cancellable2 == null) return true;
            return false;
        }

        public Timer(int id, Cancellable task) {
            this.id = id;
            this.task = task;
            Product.$init$(this);
        }
    }
}

