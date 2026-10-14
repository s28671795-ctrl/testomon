/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import java.io.Serializable;
import org.reactivestreams.Subscription;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tudA\u0002*T\u0003C9\u0016\fC\u0003j\u0001\u0011\u00051nB\u0004\u0003zMC\ta\u0016:\u0007\rI\u001b\u0006\u0012A,q\u0011\u0015I7\u0001\"\u0001r\r\u0011\u00198A\u0011;\t\u0015\u0005%QA!f\u0001\n\u0003\tY\u0001\u0003\u0006\u0002\u0014\u0015\u0011\t\u0012)A\u0005\u0003\u001bAa![\u0003\u0005\u0002\u0005U\u0001\"CA\u000f\u000b\u0005\u0005I\u0011AA\u0010\u0011%\t\u0019#BI\u0001\n\u0003\t)\u0003C\u0005\u0002<\u0015\t\t\u0011\"\u0011\u0002>!I\u0011qJ\u0003\u0002\u0002\u0013\u0005\u0011\u0011\u000b\u0005\n\u00033*\u0011\u0011!C\u0001\u00037B\u0011\"!\u0019\u0006\u0003\u0003%\t%a\u0019\t\u0013\u0005ET!!A\u0005\u0002\u0005M\u0004\"CA?\u000b\u0005\u0005I\u0011IA@\u0011%\t\u0019)BA\u0001\n\u0003\n)\tC\u0005\u0002\b\u0016\t\t\u0011\"\u0011\u0002\n\"I\u00111R\u0003\u0002\u0002\u0013\u0005\u0013QR\u0004\n\u0003#\u001b\u0011\u0011!E\u0001\u0003'3\u0001b]\u0002\u0002\u0002#\u0005\u0011Q\u0013\u0005\u0007SV!\t!!,\t\u0013\u0005\u001dU#!A\u0005F\u0005%\u0005\"CAX+\u0005\u0005I\u0011QAY\u0011%\t),FA\u0001\n\u0003\u000b9\fC\u0005\u0002DV\t\t\u0011\"\u0003\u0002F\u001a1\u0011QZ\u0002C\u0003\u001fD!\"!5\u001c\u0005+\u0007I\u0011AAj\u0011)\tYn\u0007B\tB\u0003%\u0011Q\u001b\u0005\u0007Sn!\t!!8\t\u0013\u0005u1$!A\u0005\u0002\u0005\r\b\"CA\u00127E\u0005I\u0011AAt\u0011%\tYdGA\u0001\n\u0003\ni\u0004C\u0005\u0002Pm\t\t\u0011\"\u0001\u0002R!I\u0011\u0011L\u000e\u0002\u0002\u0013\u0005\u00111\u001e\u0005\n\u0003CZ\u0012\u0011!C!\u0003GB\u0011\"!\u001d\u001c\u0003\u0003%\t!a<\t\u0013\u0005u4$!A\u0005B\u0005M\b\"CAB7\u0005\u0005I\u0011IAC\u0011%\t9iGA\u0001\n\u0003\nI\tC\u0005\u0002\fn\t\t\u0011\"\u0011\u0002x\u001eI\u00111`\u0002\u0002\u0002#\u0005\u0011Q \u0004\n\u0003\u001b\u001c\u0011\u0011!E\u0001\u0003\u007fDa![\u0016\u0005\u0002\t\r\u0001\"CADW\u0005\u0005IQIAE\u0011%\tykKA\u0001\n\u0003\u0013)\u0001C\u0005\u00026.\n\t\u0011\"!\u0003\n!I\u00111Y\u0016\u0002\u0002\u0013%\u0011QY\u0004\b\u0005\u001f\u0019\u0001\u0012\u0011B\t\r\u0019y7\u0001#!\u0003l!1\u0011N\rC\u0001\u0005[B\u0011\"a\u000f3\u0003\u0003%\t%!\u0010\t\u0013\u0005=#'!A\u0005\u0002\u0005E\u0003\"CA-e\u0005\u0005I\u0011\u0001B8\u0011%\t\tGMA\u0001\n\u0003\n\u0019\u0007C\u0005\u0002rI\n\t\u0011\"\u0001\u0003t!I\u00111\u0011\u001a\u0002\u0002\u0013\u0005\u0013Q\u0011\u0005\n\u0003\u000f\u0013\u0014\u0011!C!\u0003\u0013C\u0011\"a13\u0003\u0003%I!!2\u0007\r\tM1A\u0011B\u000b\u0011)\u00119\u0002\u0010BK\u0002\u0013\u0005!\u0011\u0004\u0005\u000b\u0005Wa$\u0011#Q\u0001\n\tm\u0001BB5=\t\u0003\u0011i\u0003C\u0005\u0002\u001eq\n\t\u0011\"\u0001\u00034!I\u00111\u0005\u001f\u0012\u0002\u0013\u0005!q\u0007\u0005\n\u0003wa\u0014\u0011!C!\u0003{A\u0011\"a\u0014=\u0003\u0003%\t!!\u0015\t\u0013\u0005eC(!A\u0005\u0002\tm\u0002\"CA1y\u0005\u0005I\u0011IA2\u0011%\t\t\bPA\u0001\n\u0003\u0011y\u0004C\u0005\u0002~q\n\t\u0011\"\u0011\u0003D!I\u00111\u0011\u001f\u0002\u0002\u0013\u0005\u0013Q\u0011\u0005\n\u0003\u000fc\u0014\u0011!C!\u0003\u0013C\u0011\"a#=\u0003\u0003%\tEa\u0012\b\u0013\t-3!!A\t\u0002\t5c!\u0003B\n\u0007\u0005\u0005\t\u0012\u0001B(\u0011\u0019IG\n\"\u0001\u0003T!I\u0011q\u0011'\u0002\u0002\u0013\u0015\u0013\u0011\u0012\u0005\n\u0003_c\u0015\u0011!CA\u0005+B\u0011\"!.M\u0003\u0003%\tI!\u0017\t\u0013\u0005\rG*!A\u0005\n\u0005\u0015'AF!di>\u00148+\u001e2tGJL'-\u001a:NKN\u001c\u0018mZ3\u000b\u0005Q+\u0016\u0001B5na2T!AV,\u0002\rM$(/Z1n\u0015\u0005A\u0016\u0001B1lW\u0006\u001cB\u0001\u0001.aMB\u00111LX\u0007\u00029*\tQ,A\u0003tG\u0006d\u0017-\u0003\u0002`9\n1\u0011I\\=SK\u001a\u0004\"!\u00193\u000e\u0003\tT!aY,\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u0015\u0014'!\u0006#fC\u0012dU\r\u001e;feN+\b\u000f\u001d:fgNLwN\u001c\t\u0003C\u001eL!\u0001\u001b2\u0003C9{7+\u001a:jC2L'0\u0019;j_:4VM]5gS\u000e\fG/[8o\u001d\u0016,G-\u001a3\u0002\rqJg.\u001b;?\u0007\u0001!\u0012\u0001\u001c\t\u0003[\u0002i\u0011aU\u0015\u0005\u0001IZRA\u0001\u0006P]\u000e{W\u000e\u001d7fi\u0016\u001c\"a\u0001.\u0015\u0003I\u0004\"!\\\u0002\u0003\r=sg*\u001a=u'\u0011)A.\u001e=\u0011\u0005m3\u0018BA<]\u0005\u001d\u0001&o\u001c3vGR\u00042!_A\u0002\u001d\tQxP\u0004\u0002|}6\tAP\u0003\u0002~U\u00061AH]8pizJ\u0011!X\u0005\u0004\u0003\u0003a\u0016a\u00029bG.\fw-Z\u0005\u0005\u0003\u000b\t9A\u0001\u0007TKJL\u0017\r\\5{C\ndWMC\u0002\u0002\u0002q\u000bq!\u001a7f[\u0016tG/\u0006\u0002\u0002\u000eA\u00191,a\u0004\n\u0007\u0005EALA\u0002B]f\f\u0001\"\u001a7f[\u0016tG\u000f\t\u000b\u0005\u0003/\tY\u0002E\u0002\u0002\u001a\u0015i\u0011a\u0001\u0005\b\u0003\u0013A\u0001\u0019AA\u0007\u0003\u0011\u0019w\u000e]=\u0015\t\u0005]\u0011\u0011\u0005\u0005\n\u0003\u0013I\u0001\u0013!a\u0001\u0003\u001b\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002()\"\u0011QBA\u0015W\t\tY\u0003\u0005\u0003\u0002.\u0005]RBAA\u0018\u0015\u0011\t\t$a\r\u0002\u0013Ut7\r[3dW\u0016$'bAA\u001b9\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005e\u0012q\u0006\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002@A!\u0011\u0011IA&\u001b\t\t\u0019E\u0003\u0003\u0002F\u0005\u001d\u0013\u0001\u00027b]\u001eT!!!\u0013\u0002\t)\fg/Y\u0005\u0005\u0003\u001b\n\u0019E\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003'\u00022aWA+\u0013\r\t9\u0006\u0018\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u001b\ti\u0006C\u0005\u0002`5\t\t\u00111\u0001\u0002T\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\u001a\u0011\r\u0005\u001d\u0014QNA\u0007\u001b\t\tIGC\u0002\u0002lq\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\ty'!\u001b\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003k\nY\bE\u0002\\\u0003oJ1!!\u001f]\u0005\u001d\u0011un\u001c7fC:D\u0011\"a\u0018\u0010\u0003\u0003\u0005\r!!\u0004\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u007f\t\t\tC\u0005\u0002`A\t\t\u00111\u0001\u0002T\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002T\u0005AAo\\*ue&tw\r\u0006\u0002\u0002@\u00051Q-];bYN$B!!\u001e\u0002\u0010\"I\u0011qL\n\u0002\u0002\u0003\u0007\u0011QB\u0001\u0007\u001f:tU\r\u001f;\u0011\u0007\u0005eQcE\u0003\u0016\u0003/\u000b\u0019\u000b\u0005\u0005\u0002\u001a\u0006}\u0015QBA\f\u001b\t\tYJC\u0002\u0002\u001er\u000bqA];oi&lW-\u0003\u0003\u0002\"\u0006m%!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u0011QUAV\u001b\t\t9K\u0003\u0003\u0002*\u0006\u001d\u0013AA5p\u0013\u0011\t)!a*\u0015\u0005\u0005M\u0015!B1qa2LH\u0003BA\f\u0003gCq!!\u0003\u0019\u0001\u0004\ti!A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005e\u0016q\u0018\t\u00067\u0006m\u0016QB\u0005\u0004\u0003{c&AB(qi&|g\u000eC\u0005\u0002Bf\t\t\u00111\u0001\u0002\u0018\u0005\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u001d\u0007\u0003BA!\u0003\u0013LA!a3\u0002D\t1qJ\u00196fGR\u0014qa\u00148FeJ|'o\u0005\u0003\u001cYVD\u0018!B2bkN,WCAAk!\rI\u0018q[\u0005\u0005\u00033\f9AA\u0005UQJ|w/\u00192mK\u000611-Y;tK\u0002\"B!a8\u0002bB\u0019\u0011\u0011D\u000e\t\u000f\u0005Eg\u00041\u0001\u0002VR!\u0011q\\As\u0011%\t\tn\bI\u0001\u0002\u0004\t).\u0006\u0002\u0002j*\"\u0011Q[A\u0015)\u0011\ti!!<\t\u0013\u0005}3%!AA\u0002\u0005MC\u0003BA;\u0003cD\u0011\"a\u0018&\u0003\u0003\u0005\r!!\u0004\u0015\t\u0005}\u0012Q\u001f\u0005\n\u0003?2\u0013\u0011!a\u0001\u0003'\"B!!\u001e\u0002z\"I\u0011qL\u0015\u0002\u0002\u0003\u0007\u0011QB\u0001\b\u001f:,%O]8s!\r\tIbK\n\u0006W\t\u0005\u00111\u0015\t\t\u00033\u000by*!6\u0002`R\u0011\u0011Q \u000b\u0005\u0003?\u00149\u0001C\u0004\u0002R:\u0002\r!!6\u0015\t\t-!Q\u0002\t\u00067\u0006m\u0016Q\u001b\u0005\n\u0003\u0003|\u0013\u0011!a\u0001\u0003?\f!b\u00148D_6\u0004H.\u001a;f!\r\tIB\r\u0002\f\u001f:\u001cVOY:de&\u0014Wm\u0005\u0004=5\u00024W\u000f_\u0001\rgV\u00147o\u0019:jaRLwN\\\u000b\u0003\u00057\u0001BA!\b\u0003(5\u0011!q\u0004\u0006\u0005\u0005C\u0011\u0019#A\bsK\u0006\u001cG/\u001b<fgR\u0014X-Y7t\u0015\t\u0011)#A\u0002pe\u001eLAA!\u000b\u0003 \ta1+\u001e2tGJL\u0007\u000f^5p]\u0006i1/\u001e2tGJL\u0007\u000f^5p]\u0002\"BAa\f\u00032A\u0019\u0011\u0011\u0004\u001f\t\u000f\t]q\b1\u0001\u0003\u001cQ!!q\u0006B\u001b\u0011%\u00119\u0002\u0011I\u0001\u0002\u0004\u0011Y\"\u0006\u0002\u0003:)\"!1DA\u0015)\u0011\tiA!\u0010\t\u0013\u0005}C)!AA\u0002\u0005MC\u0003BA;\u0005\u0003B\u0011\"a\u0018G\u0003\u0003\u0005\r!!\u0004\u0015\t\u0005}\"Q\t\u0005\n\u0003?:\u0015\u0011!a\u0001\u0003'\"B!!\u001e\u0003J!I\u0011q\f&\u0002\u0002\u0003\u0007\u0011QB\u0001\f\u001f:\u001cVOY:de&\u0014W\rE\u0002\u0002\u001a1\u001bR\u0001\u0014B)\u0003G\u0003\u0002\"!'\u0002 \nm!q\u0006\u000b\u0003\u0005\u001b\"BAa\f\u0003X!9!qC(A\u0002\tmA\u0003\u0002B.\u0005;\u0002RaWA^\u00057A\u0011\"!1Q\u0003\u0003\u0005\rAa\f)\u0007\r\u0011\t\u0007\u0005\u0003\u0003d\t\u001dTB\u0001B3\u0015\r\t)dV\u0005\u0005\u0005S\u0012)GA\u0006J]R,'O\\1m\u0003BL7\u0003\u0002\u001amkb$\"A!\u0005\u0015\t\u00055!\u0011\u000f\u0005\n\u0003?2\u0014\u0011!a\u0001\u0003'\"B!!\u001e\u0003v!I\u0011q\f\u001d\u0002\u0002\u0003\u0007\u0011Q\u0002\u0015\u0004\u0001\t\u0005\u0014AF!di>\u00148+\u001e2tGJL'-\u001a:NKN\u001c\u0018mZ3)\u0007\t\u0011\t\u0007")
@InternalApi
public abstract class ActorSubscriberMessage
implements DeadLetterSuppression,
NoSerializationVerificationNeeded {

    public static final class OnError
    extends ActorSubscriberMessage
    implements Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public OnError copy(Throwable cause) {
            return new OnError(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "OnError";
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
                    return this.cause();
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
            return x$1 instanceof OnError;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
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
            if (!(object instanceof OnError)) return false;
            boolean bl = true;
            if (!bl) return false;
            OnError onError2 = (OnError)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = onError2.cause();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public OnError(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static final class OnNext
    extends ActorSubscriberMessage
    implements Product,
    Serializable {
        private final Object element;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Object element() {
            return this.element;
        }

        public OnNext copy(Object element) {
            return new OnNext(element);
        }

        public Object copy$default$1() {
            return this.element();
        }

        @Override
        public String productPrefix() {
            return "OnNext";
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
                    return this.element();
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
            return x$1 instanceof OnNext;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "element";
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
            if (!(object instanceof OnNext)) return false;
            boolean bl = true;
            if (!bl) return false;
            OnNext onNext = (OnNext)x$1;
            if (!BoxesRunTime.equals(this.element(), onNext.element())) return false;
            return true;
        }

        public OnNext(Object element) {
            this.element = element;
            Product.$init$(this);
        }
    }

    public static final class OnSubscribe
    implements DeadLetterSuppression,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final Subscription subscription;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Subscription subscription() {
            return this.subscription;
        }

        public OnSubscribe copy(Subscription subscription) {
            return new OnSubscribe(subscription);
        }

        public Subscription copy$default$1() {
            return this.subscription();
        }

        @Override
        public String productPrefix() {
            return "OnSubscribe";
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
                    return this.subscription();
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
            return x$1 instanceof OnSubscribe;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "subscription";
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
            if (!(object instanceof OnSubscribe)) return false;
            boolean bl = true;
            if (!bl) return false;
            OnSubscribe onSubscribe = (OnSubscribe)x$1;
            Subscription subscription = this.subscription();
            Subscription subscription2 = onSubscribe.subscription();
            if (subscription != null) {
                if (!subscription.equals(subscription2)) return false;
                return true;
            }
            if (subscription2 == null) return true;
            return false;
        }

        public OnSubscribe(Subscription subscription) {
            this.subscription = subscription;
            Product.$init$(this);
        }
    }
}

