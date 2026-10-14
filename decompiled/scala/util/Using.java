/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.util.Try;
import scala.util.Using$;

@ScalaSignature(bytes="\u0006\u0005\t5u!\u0002\u0013&\u0011\u0003Qc!\u0002\u0017&\u0011\u0003i\u0003\"\u0002\u001a\u0002\t\u0003\u0019\u0004\"\u0002\u001b\u0002\t\u0003)d\u0001B2\u0002\u0005\u0011DQA\r\u0003\u0005\n\u0015Dqa\u001a\u0003A\u0002\u0013%\u0001\u000eC\u0004m\t\u0001\u0007I\u0011B7\t\rA$\u0001\u0015)\u0003j\u0011\u0019\tH\u0001)Q\u0005e\"1A\u0007\u0002C\u0001\u0003{Aq!a\u0014\u0005\t\u0003\t\t\u0006C\u0004\u0002d\u0011!I!!\u001a\b\u000f\u0005\r\u0011\u0001#\u0001\u0002\u0006\u001911-\u0001E\u0001\u0003\u000fAaA\r\b\u0005\u0002\u0005%\u0001B\u0002\u001b\u000f\t\u0003\tYA\u0002\u0004\u0002\u001c91\u0011Q\u0004\u0005\n)F\u0011\t\u0011)A\u0005\u0003CA!\"!\n\u0012\u0005\u0003\u0005\u000b1BA\u0014\u0011\u0019\u0011\u0014\u0003\"\u0001\u0002*!1a*\u0005C\u0001\u0003kAq!!\u001d\u0002\t\u0013\t\u0019\b\u0003\u0004U\u0003\u0011\u0005\u00111\u0011\u0005\u0007c\u0006!\t!a(\t\rE\fA\u0011AAk\u0011\u0019\t\u0018\u0001\"\u0001\u0003\u0016\u001991*\u0001I\u0001$\u0003a\u0005\"\u0002(\u001c\r\u0003yua\u0002B2\u0003!\u0005!Q\r\u0004\u0007\u0017\u0006A\tAa\u001a\t\rIrB\u0011\u0001B5\u000f\u001d\u0011YG\bE\u0002\u0005[2qA!\u001d\u001f\u0011\u0003\u0011\u0019\b\u0003\u00043C\u0011\u0005!q\u0011\u0005\u0007\u001d\u0006\"\tA!#\u0002\u000bU\u001b\u0018N\\4\u000b\u0005\u0019:\u0013\u0001B;uS2T\u0011\u0001K\u0001\u0006g\u000e\fG.Y\u0002\u0001!\tY\u0013!D\u0001&\u0005\u0015)6/\u001b8h'\t\ta\u0006\u0005\u00020a5\tq%\u0003\u00022O\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\u0016\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0007YJf\b\u0006\u00028?R\u0011\u0001H\u0017\u000b\u0003s\u001d\u00032a\u000b\u001e=\u0013\tYTEA\u0002Uef\u0004\"!\u0010 \r\u0001\u0011)qh\u0001b\u0001\u0001\n\t\u0011)\u0005\u0002B\tB\u0011qFQ\u0005\u0003\u0007\u001e\u0012qAT8uQ&tw\r\u0005\u00020\u000b&\u0011ai\n\u0002\u0004\u0003:L\bb\u0002%\u0004\u0003\u0003\u0005\u001d!S\u0001\u000bKZLG-\u001a8dK\u0012\n\u0004c\u0001&\u001c16\t\u0011A\u0001\u0006SK2,\u0017m]1cY\u0016,\"!\u0014,\u0014\u0005mq\u0013a\u0002:fY\u0016\f7/\u001a\u000b\u0003!N\u0003\"aL)\n\u0005I;#\u0001B+oSRDQ\u0001\u0016\u000fA\u0002U\u000b\u0001B]3t_V\u00148-\u001a\t\u0003{Y#aaV\u000e\t\u0006\u0004\u0001%!\u0001*\u0011\u0005uJF!B,\u0004\u0005\u0004\u0001\u0005\"B.\u0004\u0001\u0004a\u0016!\u00014\u0011\t=j\u0006\fP\u0005\u0003=\u001e\u0012\u0011BR;oGRLwN\\\u0019\t\rQ\u001bA\u00111\u0001a!\ry\u0013\rW\u0005\u0003E\u001e\u0012\u0001\u0002\u00102z]\u0006lWM\u0010\u0002\b\u001b\u0006t\u0017mZ3s'\t!a\u0006F\u0001g!\tQE!\u0001\u0004dY>\u001cX\rZ\u000b\u0002SB\u0011qF[\u0005\u0003W\u001e\u0012qAQ8pY\u0016\fg.\u0001\u0006dY>\u001cX\rZ0%KF$\"\u0001\u00158\t\u000f=<\u0011\u0011!a\u0001S\u0006\u0019\u0001\u0010J\u0019\u0002\u000f\rdwn]3eA\u0005I!/Z:pkJ\u001cWm\u001d\t\u0004gnthB\u0001;z\u001d\t)\b0D\u0001w\u0015\t9\u0018&\u0001\u0004=e>|GOP\u0005\u0002Q%\u0011!pJ\u0001\ba\u0006\u001c7.Y4f\u0013\taXP\u0001\u0003MSN$(B\u0001>(a\ry\u0018\u0011\b\t\u0006\u0003\u0003\t\u0012q\u0007\b\u0003\u00156\tq!T1oC\u001e,'\u000f\u0005\u0002K\u001dM\u0011aB\f\u000b\u0003\u0003\u000b)B!!\u0004\u0002\u0014Q!\u0011qBA\u000b!\u0011Y#(!\u0005\u0011\u0007u\n\u0019\u0002B\u0003@!\t\u0007\u0001\tC\u0004\u0002\u0018A\u0001\r!!\u0007\u0002\u0005=\u0004\b#B\u0018^M\u0006E!\u0001\u0003*fg>,(oY3\u0016\t\u0005}\u00111E\n\u0003#9\u00022!PA\u0012\t\u00159\u0016C1\u0001A\u0003)\u0011X\r\\3bg\u0006\u0014G.\u001a\t\u0005\u0015n\t\t\u0003\u0006\u0003\u0002,\u0005MB\u0003BA\u0017\u0003c\u0001R!a\f\u0012\u0003Ci\u0011A\u0004\u0005\b\u0003K!\u00029AA\u0014\u0011\u0019!F\u00031\u0001\u0002\"Q\t\u0001\u000bE\u0002>\u0003s!!\"a\u000f\n\u0003\u0003\u0005\tQ!\u0001A\u0005\ryF%M\u000b\u0005\u0003\u007f\t)\u0005\u0006\u0003\u0002B\u00055C\u0003BA\"\u0003\u000f\u00022!PA#\t\u00159&B1\u0001A\u0011%\tIECA\u0001\u0002\b\tY%\u0001\u0006fm&$WM\\2fII\u0002BAS\u000e\u0002D!1AK\u0003a\u0001\u0003\u0007\nq!Y2rk&\u0014X-\u0006\u0003\u0002T\u0005}C\u0003BA+\u0003C\"2\u0001UA,\u0011%\tIfCA\u0001\u0002\b\tY&\u0001\u0006fm&$WM\\2fIM\u0002BAS\u000e\u0002^A\u0019Q(a\u0018\u0005\u000b][!\u0019\u0001!\t\rQ[\u0001\u0019AA/\u0003\u0019i\u0017M\\1hKV!\u0011qMA6)\u0011\tI'!\u001c\u0011\u0007u\nY\u0007B\u0003@\u0019\t\u0007\u0001\tC\u0004\u0002\u00181\u0001\r!a\u001c\u0011\u000b=jf-!\u001b\u0002-A\u0014XMZ3sK:$\u0018.\u00197msN+\b\u000f\u001d:fgN$b!!\u001e\u0002|\u0005}\u0004cA:\u0002x%\u0019\u0011\u0011P?\u0003\u0013QC'o\\<bE2,\u0007bBA?-\u0001\u0007\u0011QO\u0001\baJLW.\u0019:z\u0011\u001d\t\tI\u0006a\u0001\u0003k\n\u0011b]3d_:$\u0017M]=\u0016\r\u0005\u0015\u0015QSAG)\u0011\t9)!(\u0015\t\u0005%\u0015q\u0013\u000b\u0005\u0003\u0017\u000by\tE\u0002>\u0003\u001b#QaP\fC\u0002\u0001Cq!!\n\u0018\u0001\b\t\t\n\u0005\u0003K7\u0005M\u0005cA\u001f\u0002\u0016\u0012)qk\u0006b\u0001\u0001\"9\u0011\u0011T\fA\u0002\u0005m\u0015\u0001\u00022pIf\u0004baL/\u0002\u0014\u0006-\u0005B\u0002+\u0018\u0001\u0004\t\u0019*\u0006\u0005\u0002\"\u0006M\u0016qXAU)\u0019\t\u0019+a3\u0002PR!\u0011QUAb)\u0019\t9+a+\u00028B\u0019Q(!+\u0005\u000b}B\"\u0019\u0001!\t\u0013\u00055\u0006$!AA\u0004\u0005=\u0016AC3wS\u0012,gnY3%iA!!jGAY!\ri\u00141\u0017\u0003\u0007\u0003kC\"\u0019\u0001!\u0003\u0005I\u000b\u0004\"CA]1\u0005\u0005\t9AA^\u0003))g/\u001b3f]\u000e,G%\u000e\t\u0005\u0015n\ti\fE\u0002>\u0003\u007f#a!!1\u0019\u0005\u0004\u0001%A\u0001*3\u0011\u001d\tI\n\u0007a\u0001\u0003\u000b\u0004\u0012bLAd\u0003c\u000bi,a*\n\u0007\u0005%wEA\u0005Gk:\u001cG/[8oe!9\u0011Q\u001a\rA\u0002\u0005E\u0016!\u0003:fg>,(oY32\u0011!\t\t\u000e\u0007CA\u0002\u0005M\u0017!\u0003:fg>,(oY33!\u0011y\u0013-!0\u0016\u0015\u0005]\u0017\u0011^Az\u0003{\fy\u000e\u0006\u0005\u0002Z\n%!1\u0002B\b)\u0011\tYN!\u0001\u0015\u0011\u0005u\u0017\u0011]Av\u0003k\u00042!PAp\t\u0015y\u0014D1\u0001A\u0011%\t\u0019/GA\u0001\u0002\b\t)/\u0001\u0006fm&$WM\\2fIY\u0002BAS\u000e\u0002hB\u0019Q(!;\u0005\r\u0005U\u0016D1\u0001A\u0011%\ti/GA\u0001\u0002\b\ty/\u0001\u0006fm&$WM\\2fI]\u0002BAS\u000e\u0002rB\u0019Q(a=\u0005\r\u0005\u0005\u0017D1\u0001A\u0011%\t90GA\u0001\u0002\b\tI0\u0001\u0006fm&$WM\\2fIa\u0002BAS\u000e\u0002|B\u0019Q(!@\u0005\r\u0005}\u0018D1\u0001A\u0005\t\u00116\u0007C\u0004\u0002\u001af\u0001\rAa\u0001\u0011\u0017=\u0012)!a:\u0002r\u0006m\u0018Q\\\u0005\u0004\u0005\u000f9#!\u0003$v]\u000e$\u0018n\u001c84\u0011\u001d\ti-\u0007a\u0001\u0003OD\u0001\"!5\u001a\t\u0003\u0007!Q\u0002\t\u0005_\u0005\f\t\u0010\u0003\u0005\u0003\u0012e!\t\u0019\u0001B\n\u0003%\u0011Xm]8ve\u000e,7\u0007\u0005\u00030C\u0006mX\u0003\u0004B\f\u0005S\u0011\u0019D!\u0010\u0003H\t}AC\u0003B\r\u0005'\u0012)F!\u0017\u0003^Q!!1\u0004B&))\u0011iB!\t\u0003,\tU\"q\b\t\u0004{\t}A!B \u001b\u0005\u0004\u0001\u0005\"\u0003B\u00125\u0005\u0005\t9\u0001B\u0013\u0003))g/\u001b3f]\u000e,G%\u000f\t\u0005\u0015n\u00119\u0003E\u0002>\u0005S!a!!.\u001b\u0005\u0004\u0001\u0005\"\u0003B\u00175\u0005\u0005\t9\u0001B\u0018\u0003-)g/\u001b3f]\u000e,G%\r\u0019\u0011\t)[\"\u0011\u0007\t\u0004{\tMBABAa5\t\u0007\u0001\tC\u0005\u00038i\t\t\u0011q\u0001\u0003:\u0005YQM^5eK:\u001cW\rJ\u00192!\u0011Q5Da\u000f\u0011\u0007u\u0012i\u0004\u0002\u0004\u0002\u0000j\u0011\r\u0001\u0011\u0005\n\u0005\u0003R\u0012\u0011!a\u0002\u0005\u0007\n1\"\u001a<jI\u0016t7-\u001a\u00132eA!!j\u0007B#!\ri$q\t\u0003\u0007\u0005\u0013R\"\u0019\u0001!\u0003\u0005I#\u0004bBAM5\u0001\u0007!Q\n\t\u000e_\t=#q\u0005B\u0019\u0005w\u0011)E!\b\n\u0007\tEsEA\u0005Gk:\u001cG/[8oi!9\u0011Q\u001a\u000eA\u0002\t\u001d\u0002\u0002CAi5\u0011\u0005\rAa\u0016\u0011\t=\n'\u0011\u0007\u0005\t\u0005#QB\u00111\u0001\u0003\\A!q&\u0019B\u001e\u0011!\u0011yF\u0007CA\u0002\t\u0005\u0014!\u0003:fg>,(oY35!\u0011y\u0013M!\u0012\u0002\u0015I+G.Z1tC\ndW\r\u0005\u0002K=M\u0011aD\f\u000b\u0003\u0005K\n\u0011$Q;u_\u000ecwn]3bE2,\u0017j\u001d*fY\u0016\f7/\u00192mKB\u0019!qN\u0011\u000e\u0003y\u0011\u0011$Q;u_\u000ecwn]3bE2,\u0017j\u001d*fY\u0016\f7/\u00192mKN!\u0011E\fB;!\u0011Q5Da\u001e\u0011\t\te$1Q\u0007\u0003\u0005wRAA! \u0003\u0000\u0005!A.\u00198h\u0015\t\u0011\t)\u0001\u0003kCZ\f\u0017\u0002\u0002BC\u0005w\u0012Q\"Q;u_\u000ecwn]3bE2,GC\u0001B7)\r\u0001&1\u0012\u0005\u0007)\u000e\u0002\rAa\u001e")
public final class Using {
    public static <R1, R2, R3, R4, A> A resources(R1 resource1, Function0<R2> resource2, Function0<R3> resource3, Function0<R4> resource4, Function4<R1, R2, R3, R4, A> body2, Releasable<R1> evidence$9, Releasable<R2> evidence$10, Releasable<R3> evidence$11, Releasable<R4> evidence$12) {
        return Using$.MODULE$.resources(resource1, resource2, resource3, resource4, body2, evidence$9, evidence$10, evidence$11, evidence$12);
    }

    public static <R1, R2, R3, A> A resources(R1 resource1, Function0<R2> resource2, Function0<R3> resource3, Function3<R1, R2, R3, A> body2, Releasable<R1> evidence$6, Releasable<R2> evidence$7, Releasable<R3> evidence$8) {
        return Using$.MODULE$.resources(resource1, resource2, resource3, body2, evidence$6, evidence$7, evidence$8);
    }

    public static <R1, R2, A> A resources(R1 resource1, Function0<R2> resource2, Function2<R1, R2, A> body2, Releasable<R1> evidence$4, Releasable<R2> evidence$5) {
        return Using$.MODULE$.resources(resource1, resource2, body2, evidence$4, evidence$5);
    }

    public static <R, A> A resource(R resource, Function1<R, A> body2, Releasable<R> releasable) {
        return Using$.MODULE$.resource(resource, body2, releasable);
    }

    public static <R, A> Try<A> apply(Function0<R> resource, Function1<R, A> f, Releasable<R> evidence$1) {
        return Using$.MODULE$.apply(resource, f, evidence$1);
    }

    public static final class Manager {
        private boolean closed = false;
        private List<Resource<?>> resources = Nil$.MODULE$;

        private boolean closed() {
            return this.closed;
        }

        private void closed_$eq(boolean x$1) {
            this.closed = x$1;
        }

        public <R> R apply(R resource, Releasable<R> evidence$2) {
            this.acquire(resource, evidence$2);
            return resource;
        }

        public <R> void acquire(R resource, Releasable<R> evidence$3) {
            if (resource == null) {
                throw new NullPointerException("null resource");
            }
            if (this.closed()) {
                throw new IllegalStateException("Manager has already been closed");
            }
            Resource<R> resource2 = new Resource<R>(resource, evidence$3);
            List<Resource<?>> list = this.resources;
            if (list == null) {
                throw null;
            }
            List<Resource<?>> $colon$colon_this = list;
            this.resources = new $colon$colon(resource2, $colon$colon_this);
        }

        public <A> A scala$util$Using$Manager$$manage(Function1<Manager, A> op) {
            A a;
            Throwable toThrow2 = null;
            try {
                try {
                    a = op.apply(this);
                }
                catch (Throwable toThrow2) {
                    a = null;
                }
            }
            catch (Throwable throwable) {
                this.closed_$eq(true);
                List rs = this.resources;
                this.resources = null;
                while (rs.nonEmpty()) {
                    Resource resource = (Resource)rs.head();
                    rs = (List)rs.tail();
                    try {
                        resource.release();
                    }
                    catch (Throwable t2) {
                        if (toThrow2 == null) {
                            toThrow2 = t2;
                            continue;
                        }
                        toThrow2 = Using$.MODULE$.scala$util$Using$$preferentiallySuppress(toThrow2, t2);
                    }
                }
                if (toThrow2 != null) {
                    throw toThrow2;
                }
                throw throwable;
            }
            A a2 = a;
            this.closed_$eq(true);
            List rs = this.resources;
            this.resources = null;
            while (rs.nonEmpty()) {
                Resource resource = (Resource)rs.head();
                rs = (List)rs.tail();
                try {
                    resource.release();
                }
                catch (Throwable t3) {
                    if (toThrow2 == null) {
                        toThrow2 = t3;
                        continue;
                    }
                    toThrow2 = Using$.MODULE$.scala$util$Using$$preferentiallySuppress(toThrow2, t3);
                }
            }
            if (toThrow2 != null) {
                throw toThrow2;
            }
            return a2;
        }

        private static final class Resource<R> {
            private final R resource;
            private final Releasable<R> releasable;

            public void release() {
                this.releasable.release(this.resource);
            }

            public Resource(R resource, Releasable<R> releasable) {
                this.resource = resource;
                this.releasable = releasable;
            }
        }
    }

    public static interface Releasable<R> {
        public void release(R var1);
    }
}

