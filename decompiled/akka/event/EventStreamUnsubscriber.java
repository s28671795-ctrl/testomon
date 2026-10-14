/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.EventStream;
import akka.event.EventStreamUnsubscriber$;
import akka.event.Logging;
import akka.event.Logging$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0005b!B\u001d;\u0011qr\u0004\u0002C&\u0001\u0005\u0003\u0005\u000b\u0011B'\t\u0011E\u0003!\u0011!Q\u0001\nICQ!\u0016\u0001\u0005\u0002YCQA\u0017\u0001\u0005\u0002m;a!\u001a\u001e\t\u0002q2gAB\u001d;\u0011\u0003at\rC\u0003V\r\u0011\u0005\u0001\u000eC\u0004j\r\t\u0007I\u0011\u00026\t\r]4\u0001\u0015!\u0003l\r\u0011AhAQ=\t\u0013!S!Q3A\u0005\u0002\u0005M\u0001BCA\u000e\u0015\tE\t\u0015!\u0003\u0002\u0016!1QK\u0003C\u0001\u0003;A\u0011\"!\n\u000b\u0003\u0003%\t!a\n\t\u0013\u0005-\"\"%A\u0005\u0002\u00055\u0002\"CA\"\u0015\u0005\u0005I\u0011IA#\u0011%\t\u0019FCA\u0001\n\u0003\t)\u0006C\u0005\u0002^)\t\t\u0011\"\u0001\u0002`!I\u0011Q\r\u0006\u0002\u0002\u0013\u0005\u0013q\r\u0005\n\u0003kR\u0011\u0011!C\u0001\u0003oB\u0011\"a\u001f\u000b\u0003\u0003%\t%! \t\u0013\u0005\u0005%\"!A\u0005B\u0005\r\u0005\"CAC\u0015\u0005\u0005I\u0011IAD\u0011%\tIICA\u0001\n\u0003\nYiB\u0005\u0002\u0010\u001a\t\t\u0011#\u0001\u0002\u0012\u001aA\u0001PBA\u0001\u0012\u0003\t\u0019\n\u0003\u0004V5\u0011\u0005\u00111\u0016\u0005\n\u0003\u000bS\u0012\u0011!C#\u0003\u000fC\u0011\"!,\u001b\u0003\u0003%\t)a,\t\u0013\u0005M&$!A\u0005\u0002\u0006U\u0006\"CAa5\u0005\u0005I\u0011BAb\r\u0019\tYM\u0002\"\u0002N\"I\u0001\n\tBK\u0002\u0013\u0005\u00111\u0003\u0005\u000b\u00037\u0001#\u0011#Q\u0001\n\u0005U\u0001BB+!\t\u0003\ty\rC\u0005\u0002&\u0001\n\t\u0011\"\u0001\u0002V\"I\u00111\u0006\u0011\u0012\u0002\u0013\u0005\u0011Q\u0006\u0005\n\u0003\u0007\u0002\u0013\u0011!C!\u0003\u000bB\u0011\"a\u0015!\u0003\u0003%\t!!\u0016\t\u0013\u0005u\u0003%!A\u0005\u0002\u0005e\u0007\"CA3A\u0005\u0005I\u0011IA4\u0011%\t)\bIA\u0001\n\u0003\ti\u000eC\u0005\u0002|\u0001\n\t\u0011\"\u0011\u0002b\"I\u0011\u0011\u0011\u0011\u0002\u0002\u0013\u0005\u00131\u0011\u0005\n\u0003\u000b\u0003\u0013\u0011!C!\u0003\u000fC\u0011\"!#!\u0003\u0003%\t%!:\b\u0013\u0005%h!!A\t\u0002\u0005-h!CAf\r\u0005\u0005\t\u0012AAw\u0011\u0019)\u0006\u0007\"\u0001\u0002r\"I\u0011Q\u0011\u0019\u0002\u0002\u0013\u0015\u0013q\u0011\u0005\n\u0003[\u0003\u0014\u0011!CA\u0003gD\u0011\"a-1\u0003\u0003%\t)a>\t\u0013\u0005\u0005\u0007'!A\u0005\n\u0005\r\u0007bBA~\r\u0011%\u0011Q \u0005\b\u0005\u00131A\u0011\u0001B\u0006\u0011%\u0011YBBI\u0001\n\u0003\u0011iBA\fFm\u0016tGo\u0015;sK\u0006lWK\\:vEN\u001c'/\u001b2fe*\u00111\bP\u0001\u0006KZ,g\u000e\u001e\u0006\u0002{\u0005!\u0011m[6b'\r\u0001q(\u0012\t\u0003\u0001\u000ek\u0011!\u0011\u0006\u0002\u0005\u0006)1oY1mC&\u0011A)\u0011\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0019KU\"A$\u000b\u0005!c\u0014!B1di>\u0014\u0018B\u0001&H\u0005\u0015\t5\r^8s\u0003-)g/\u001a8u'R\u0014X-Y7\u0004\u0001A\u0011ajT\u0007\u0002u%\u0011\u0001K\u000f\u0002\f\u000bZ,g\u000e^*ue\u0016\fW.A\u0003eK\n,x\r\u0005\u0002A'&\u0011A+\u0011\u0002\b\u0005>|G.Z1o\u0003\u0019a\u0014N\\5u}Q\u0019q\u000bW-\u0011\u00059\u0003\u0001\"B&\u0004\u0001\u0004i\u0005bB)\u0004!\u0003\u0005\rAU\u0001\be\u0016\u001cW-\u001b<f+\u0005a\u0006\u0003\u0002!^?\nL!AX!\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:\u0004\"\u0001\u00111\n\u0005\u0005\f%aA!osB\u0011\u0001iY\u0005\u0003I\u0006\u0013A!\u00168ji\u00069RI^3oiN#(/Z1n+:\u001cXOY:de&\u0014WM\u001d\t\u0003\u001d\u001a\u0019\"AB \u0015\u0003\u0019\f!#\u001e8tk\n\u001c8M]5cKJ\u001c8i\\;oiV\t1\u000e\u0005\u0002mk6\tQN\u0003\u0002o_\u00061\u0011\r^8nS\u000eT!\u0001]9\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002sg\u0006!Q\u000f^5m\u0015\u0005!\u0018\u0001\u00026bm\u0006L!A^7\u0003\u001b\u0005#x.\\5d\u0013:$XmZ3s\u0003M)hn];cg\u000e\u0014\u0018NY3sg\u000e{WO\u001c;!\u0005!\u0011VmZ5ti\u0016\u00148\u0003\u0002\u0006@uv\u0004\"\u0001Q>\n\u0005q\f%a\u0002)s_\u0012,8\r\u001e\t\u0004}\u00065abA@\u0002\n9!\u0011\u0011AA\u0004\u001b\t\t\u0019AC\u0002\u0002\u00061\u000ba\u0001\u0010:p_Rt\u0014\"\u0001\"\n\u0007\u0005-\u0011)A\u0004qC\u000e\\\u0017mZ3\n\t\u0005=\u0011\u0011\u0003\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0004\u0003\u0017\tUCAA\u000b!\r1\u0015qC\u0005\u0004\u000339%\u0001C!di>\u0014(+\u001a4\u0002\r\u0005\u001cGo\u001c:!)\u0011\ty\"a\t\u0011\u0007\u0005\u0005\"\"D\u0001\u0007\u0011\u0019AU\u00021\u0001\u0002\u0016\u0005!1m\u001c9z)\u0011\ty\"!\u000b\t\u0011!s\u0001\u0013!a\u0001\u0003+\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u00020)\"\u0011QCA\u0019W\t\t\u0019\u0004\u0005\u0003\u00026\u0005}RBAA\u001c\u0015\u0011\tI$a\u000f\u0002\u0013Ut7\r[3dW\u0016$'bAA\u001f\u0003\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\u0005\u0013q\u0007\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002HA!\u0011\u0011JA(\u001b\t\tYEC\u0002\u0002NM\fA\u0001\\1oO&!\u0011\u0011KA&\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011q\u000b\t\u0004\u0001\u0006e\u0013bAA.\u0003\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019q,!\u0019\t\u0013\u0005\r$#!AA\u0002\u0005]\u0013a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002jA)\u00111NA9?6\u0011\u0011Q\u000e\u0006\u0004\u0003_\n\u0015AC2pY2,7\r^5p]&!\u00111OA7\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007I\u000bI\b\u0003\u0005\u0002dQ\t\t\u00111\u0001`\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005\u001d\u0013q\u0010\u0005\n\u0003G*\u0012\u0011!a\u0001\u0003/\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003/\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u000f\na!Z9vC2\u001cHc\u0001*\u0002\u000e\"A\u00111\r\r\u0002\u0002\u0003\u0007q,\u0001\u0005SK\u001eL7\u000f^3s!\r\t\tCG\n\u00065\u0005U\u0015\u0011\u0015\t\t\u0003/\u000bi*!\u0006\u0002 5\u0011\u0011\u0011\u0014\u0006\u0004\u00037\u000b\u0015a\u0002:v]RLW.Z\u0005\u0005\u0003?\u000bIJA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!a)\u0002*6\u0011\u0011Q\u0015\u0006\u0004\u0003O\u001b\u0018AA5p\u0013\u0011\ty!!*\u0015\u0005\u0005E\u0015!B1qa2LH\u0003BA\u0010\u0003cCa\u0001S\u000fA\u0002\u0005U\u0011aB;oCB\u0004H.\u001f\u000b\u0005\u0003o\u000bi\fE\u0003A\u0003s\u000b)\"C\u0002\u0002<\u0006\u0013aa\u00149uS>t\u0007\"CA`=\u0005\u0005\t\u0019AA\u0010\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u000b\u0004B!!\u0013\u0002H&!\u0011\u0011ZA&\u0005\u0019y%M[3di\n!SK\u001c:fO&\u001cH/\u001a:JM:{Wj\u001c:f'V\u00147o\u0019:jE\u0016$7\t[1o]\u0016d7o\u0005\u0003!\u007filH\u0003BAi\u0003'\u00042!!\t!\u0011\u0019A5\u00051\u0001\u0002\u0016Q!\u0011\u0011[Al\u0011!AE\u0005%AA\u0002\u0005UAcA0\u0002\\\"I\u00111\r\u0015\u0002\u0002\u0003\u0007\u0011q\u000b\u000b\u0004%\u0006}\u0007\u0002CA2U\u0005\u0005\t\u0019A0\u0015\t\u0005\u001d\u00131\u001d\u0005\n\u0003GZ\u0013\u0011!a\u0001\u0003/\"2AUAt\u0011!\t\u0019GLA\u0001\u0002\u0004y\u0016\u0001J+oe\u0016<\u0017n\u001d;fe&3gj\\'pe\u0016\u001cVOY:de&\u0014W\rZ\"iC:tW\r\\:\u0011\u0007\u0005\u0005\u0002gE\u00031\u0003_\f\t\u000b\u0005\u0005\u0002\u0018\u0006u\u0015QCAi)\t\tY\u000f\u0006\u0003\u0002R\u0006U\bB\u0002%4\u0001\u0004\t)\u0002\u0006\u0003\u00028\u0006e\b\"CA`i\u0005\u0005\t\u0019AAi\u0003\u0015\u0001(o\u001c9t)\u0019\tyP!\u0002\u0003\bA\u0019aI!\u0001\n\u0007\t\rqIA\u0003Qe>\u00048\u000fC\u0003Lm\u0001\u0007Q\nC\u0003Rm\u0001\u0007!+A\u0003ti\u0006\u0014H\u000f\u0006\u0004\u0002\u0016\t5!q\u0003\u0005\b\u0005\u001f9\u0004\u0019\u0001B\t\u0003\u0019\u0019\u0018p\u001d;f[B\u0019aIa\u0005\n\u0007\tUqIA\u0006BGR|'oU=ti\u0016l\u0007B\u0002B\ro\u0001\u0007Q*\u0001\u0004tiJ,\u0017-\\\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\t}!f\u0001*\u00022\u0001")
public class EventStreamUnsubscriber
implements Actor {
    public final EventStream akka$event$EventStreamUnsubscriber$$eventStream;
    public final boolean akka$event$EventStreamUnsubscriber$$debug;
    private ActorContext context;
    private ActorRef self;

    public static boolean $lessinit$greater$default$2() {
        return EventStreamUnsubscriber$.MODULE$.$lessinit$greater$default$2();
    }

    public static ActorRef start(ActorSystem system, EventStream stream) {
        return EventStreamUnsubscriber$.MODULE$.start(system, stream);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ EventStreamUnsubscriber $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                UnregisterIfNoMoreSubscribedChannels unregisterIfNoMoreSubscribedChannels = null;
                A1 A1 = x1;
                if (A1 instanceof Register) {
                    Register register = (Register)A1;
                    ActorRef actor = register.actor();
                    if (this.$outer.akka$event$EventStreamUnsubscriber$$debug) {
                        this.$outer.akka$event$EventStreamUnsubscriber$$eventStream.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.$outer.getClass()), this.$outer.getClass(), new StringBuilder(69).append("watching ").append(actor).append(" in order to unsubscribe from EventStream when it terminates").toString()));
                    }
                    this.$outer.context().watch(actor);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof UnregisterIfNoMoreSubscribedChannels) {
                    bl = true;
                    unregisterIfNoMoreSubscribedChannels = (UnregisterIfNoMoreSubscribedChannels)A1;
                    ActorRef actor = unregisterIfNoMoreSubscribedChannels.actor();
                    if (this.$outer.akka$event$EventStreamUnsubscriber$$eventStream.hasSubscriptions(actor)) {
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (bl) {
                    ActorRef actor = unregisterIfNoMoreSubscribedChannels.actor();
                    if (this.$outer.akka$event$EventStreamUnsubscriber$$debug) {
                        this.$outer.akka$event$EventStreamUnsubscriber$$eventStream.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.$outer.getClass()), this.$outer.getClass(), new StringBuilder(39).append("unwatching ").append(actor).append(", since has no subscriptions").toString()));
                    }
                    this.$outer.context().unwatch(actor);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef actor = terminated.actor();
                    if (this.$outer.akka$event$EventStreamUnsubscriber$$debug) {
                        this.$outer.akka$event$EventStreamUnsubscriber$$eventStream.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.$outer.getClass()), this.$outer.getClass(), new StringBuilder(45).append("unsubscribe ").append(actor).append(" from ").append(this.$outer.akka$event$EventStreamUnsubscriber$$eventStream).append(", because it was terminated").toString()));
                    }
                    this.$outer.akka$event$EventStreamUnsubscriber$$eventStream.unsubscribe(actor);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                boolean bl = false;
                UnregisterIfNoMoreSubscribedChannels unregisterIfNoMoreSubscribedChannels = null;
                Object object = x1;
                if (object instanceof Register) {
                    return true;
                }
                if (object instanceof UnregisterIfNoMoreSubscribedChannels) {
                    bl = true;
                    unregisterIfNoMoreSubscribedChannels = (UnregisterIfNoMoreSubscribedChannels)object;
                    ActorRef actor = unregisterIfNoMoreSubscribedChannels.actor();
                    if (this.$outer.akka$event$EventStreamUnsubscriber$$eventStream.hasSubscriptions(actor)) {
                        return true;
                    }
                }
                if (bl) {
                    return true;
                }
                return object instanceof Terminated;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public EventStreamUnsubscriber(EventStream eventStream, boolean debug) {
        this.akka$event$EventStreamUnsubscriber$$eventStream = eventStream;
        this.akka$event$EventStreamUnsubscriber$$debug = debug;
        Actor.$init$(this);
        Statics.releaseFence();
    }

    public static final class Register
    implements Product,
    Serializable {
        private final ActorRef actor;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public Register copy(ActorRef actor) {
            return new Register(actor);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        @Override
        public String productPrefix() {
            return "Register";
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
                    return this.actor();
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
            return x$1 instanceof Register;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
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
            if (!(object instanceof Register)) return false;
            boolean bl = true;
            if (!bl) return false;
            Register register = (Register)x$1;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = register.actor();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public Register(ActorRef actor) {
            this.actor = actor;
            Product.$init$(this);
        }
    }

    public static final class UnregisterIfNoMoreSubscribedChannels
    implements Product,
    Serializable {
        private final ActorRef actor;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public UnregisterIfNoMoreSubscribedChannels copy(ActorRef actor) {
            return new UnregisterIfNoMoreSubscribedChannels(actor);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        @Override
        public String productPrefix() {
            return "UnregisterIfNoMoreSubscribedChannels";
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
                    return this.actor();
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
            return x$1 instanceof UnregisterIfNoMoreSubscribedChannels;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
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
            if (!(object instanceof UnregisterIfNoMoreSubscribedChannels)) return false;
            boolean bl = true;
            if (!bl) return false;
            UnregisterIfNoMoreSubscribedChannels unregisterIfNoMoreSubscribedChannels = (UnregisterIfNoMoreSubscribedChannels)x$1;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = unregisterIfNoMoreSubscribedChannels.actor();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public UnregisterIfNoMoreSubscribedChannels(ActorRef actor) {
            this.actor = actor;
            Product.$init$(this);
        }
    }
}

