/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Stash;
import akka.actor.StashSupport;
import akka.actor.SupervisorStrategy;
import akka.actor.UnrestrictedStash;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.Tcp$Connect$;
import akka.io.Tcp$PeerClosed$;
import akka.io.Tcp$Register$;
import akka.io.Tcp$Write$;
import akka.io.dns.ResourceRecord;
import akka.io.dns.internal.DnsClient;
import akka.io.dns.internal.Message;
import akka.io.dns.internal.Message$;
import akka.io.dns.internal.MessageFlags$;
import akka.io.dns.internal.ResponseCode$;
import akka.io.dns.internal.TcpDnsClient$;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.Serializable;
import java.net.InetSocketAddress;
import scala.Enumeration;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005uc!B\n\u0015\u0001ia\u0002\u0002C\u0018\u0001\u0005\u0003\u0005\u000b\u0011B\u0019\t\u0011Q\u0002!\u0011!Q\u0001\nUB\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006I!\r\u0005\u0006}\u0001!\ta\u0010\u0005\u0006\u000b\u0002!\tE\u0012\u0005\b\u0017\u0002\u0011\r\u0011\"\u0001G\u0011\u0019a\u0005\u0001)A\u0005\u000f\"9Q\n\u0001b\u0001\n\u00031\u0005B\u0002(\u0001A\u0003%q\tC\u0003P\u0001\u0011\u0005\u0001\u000bC\u0004\\\u0001E\u0005I\u0011\u0001/\t\u000b\u001d\u0004A\u0011\u00025\b\u0011\u0005\u001dA\u0003#\u0001\u0015\u0003\u00131qa\u0005\u000b\t\u0002Q\tY\u0001\u0003\u0004?\u001d\u0011\u0005\u0011Q\u0002\u0005\b\u0003\u001fqA\u0011AA\t\u0011\u001d\tiB\u0004C\u0001\u0003?Aq!a\t\u000f\t\u0003\t)C\u0001\u0007UGB$en]\"mS\u0016tGO\u0003\u0002\u0016-\u0005A\u0011N\u001c;fe:\fGN\u0003\u0002\u00181\u0005\u0019AM\\:\u000b\u0005eQ\u0012AA5p\u0015\u0005Y\u0012\u0001B1lW\u0006\u001cR\u0001A\u000f$S1\u0002\"AH\u0011\u000e\u0003}Q\u0011\u0001I\u0001\u0006g\u000e\fG.Y\u0005\u0003E}\u0011a!\u00118z%\u00164\u0007C\u0001\u0013(\u001b\u0005)#B\u0001\u0014\u001b\u0003\u0015\t7\r^8s\u0013\tASEA\u0003BGR|'\u000f\u0005\u0002%U%\u00111&\n\u0002\r\u0003\u000e$xN\u001d'pO\u001eLgn\u001a\t\u0003I5J!AL\u0013\u0003\u000bM#\u0018m\u001d5\u0002\u0007Q\u001c\u0007o\u0001\u0001\u0011\u0005\u0011\u0012\u0014BA\u001a&\u0005!\t5\r^8s%\u00164\u0017A\u00018t!\t14(D\u00018\u0015\tA\u0014(A\u0002oKRT\u0011AO\u0001\u0005U\u00064\u0018-\u0003\u0002=o\t\t\u0012J\\3u'>\u001c7.\u001a;BI\u0012\u0014Xm]:\u0002\u001f\u0005t7o^3s%\u0016\u001c\u0017\u000e]5f]R\fa\u0001P5oSRtD\u0003\u0002!C\u0007\u0012\u0003\"!\u0011\u0001\u000e\u0003QAQa\f\u0003A\u0002EBQ\u0001\u000e\u0003A\u0002UBQ!\u0010\u0003A\u0002E\nqA]3dK&4X-F\u0001H!\tA\u0015*D\u0001\u0001\u0013\tQuEA\u0004SK\u000e,\u0017N^3\u0002\t%$G.Z\u0001\u0006S\u0012dW\rI\u0001\u000bG>tg.Z2uS:<\u0017aC2p]:,7\r^5oO\u0002\nQA]3bIf$2aR)T\u0011\u0015\u0011&\u00021\u00012\u0003)\u0019wN\u001c8fGRLwN\u001c\u0005\b)*\u0001\n\u00111\u0001V\u0003\u0019\u0011WO\u001a4feB\u0011a+W\u0007\u0002/*\u0011\u0001LG\u0001\u0005kRLG.\u0003\u0002[/\nQ!)\u001f;f'R\u0014\u0018N\\4\u0002\u001fI,\u0017\rZ=%I\u00164\u0017-\u001e7uII*\u0012!\u0018\u0016\u0003+z[\u0013a\u0018\t\u0003A\u0016l\u0011!\u0019\u0006\u0003E\u000e\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u0011|\u0012AC1o]>$\u0018\r^5p]&\u0011a-\u0019\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049beN,'+Z:q_:\u001cX\r\u0006\u0002jwB\u0011!\u000e\u001f\b\u0003WZt!\u0001\\;\u000f\u00055$hB\u00018t\u001d\ty'/D\u0001q\u0015\t\t\b'\u0001\u0004=e>|GOP\u0005\u00027%\u0011\u0011DG\u0005\u0003/aI!!\u0006\f\n\u0005]$\u0012!\u0003#og\u000ec\u0017.\u001a8u\u0013\tI(P\u0001\u0004B]N<XM\u001d\u0006\u0003oRAQ\u0001 \u0007A\u0002U\u000bA\u0001Z1uC\"\u0012\u0001A \t\u0004\u007f\u0006\rQBAA\u0001\u0015\t!'$\u0003\u0003\u0002\u0006\u0005\u0005!aC%oi\u0016\u0014h.\u00197Ba&\fA\u0002V2q\t:\u001c8\t\\5f]R\u0004\"!\u0011\b\u0014\u00059iBCAA\u0005\u00031)gnY8eK2+gn\u001a;i)\r)\u00161\u0003\u0005\b\u0003+\u0001\u0002\u0019AA\f\u0003\u0019aWM\\4uQB\u0019a$!\u0007\n\u0007\u0005mqDA\u0002J]R\fA\u0002Z3d_\u0012,G*\u001a8hi\"$B!a\u0006\u0002\"!)A0\u0005a\u0001+\u0006aA\u000f\u001b:po\u001a\u000b\u0017\u000e\\;sKR1\u0011qEA\u0017\u0003\u0003\u00022AHA\u0015\u0013\r\tYc\b\u0002\u0005+:LG\u000fC\u0004\u00020I\u0001\r!!\r\u0002\u000f5,7o]1hKB!\u00111GA\u001e\u001d\u0011\t)$a\u000e\u0011\u0005=|\u0012bAA\u001d?\u00051\u0001K]3eK\u001aLA!!\u0010\u0002@\t11\u000b\u001e:j]\u001eT1!!\u000f \u0011\u001d\t\u0019E\u0005a\u0001\u0003\u000b\nQaY1vg\u0016\u0004RAHA$\u0003\u0017J1!!\u0013 \u0005\u0019y\u0005\u000f^5p]B!\u0011QJA,\u001d\u0011\ty%a\u0015\u000f\u0007=\f\t&C\u0001!\u0013\r\t)fH\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\tI&a\u0017\u0003\u0013QC'o\\<bE2,'bAA+?\u0001")
@InternalApi
public class TcpDnsClient
implements ActorLogging,
Stash {
    public final ActorRef akka$io$dns$internal$TcpDnsClient$$tcp;
    public final InetSocketAddress akka$io$dns$internal$TcpDnsClient$$ns;
    public final ActorRef akka$io$dns$internal$TcpDnsClient$$answerRecipient;
    private final PartialFunction<Object, BoxedUnit> idle;
    private final PartialFunction<Object, BoxedUnit> connecting;
    private Vector<Envelope> akka$actor$StashSupport$$theStash;
    private int akka$actor$StashSupport$$capacity;
    private DequeBasedMessageQueueSemantics mailbox;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    public static void throwFailure(String message, Option<Throwable> cause) {
        TcpDnsClient$.MODULE$.throwFailure(message, cause);
    }

    public static int decodeLength(ByteString data) {
        return TcpDnsClient$.MODULE$.decodeLength(data);
    }

    public static ByteString encodeLength(int length) {
        return TcpDnsClient$.MODULE$.encodeLength(length);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$preRestart(Throwable reason, Option message) {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$postStop() {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        UnrestrictedStash.preRestart$(this, reason, message);
    }

    @Override
    public void postStop() throws Exception {
        UnrestrictedStash.postStop$(this);
    }

    @Override
    public void stash() {
        StashSupport.stash$(this);
    }

    @Override
    public void prepend(Seq<Envelope> others) {
        StashSupport.prepend$(this, others);
    }

    @Override
    public void unstash() {
        StashSupport.unstash$(this);
    }

    @Override
    public void unstashAll() {
        StashSupport.unstashAll$(this);
    }

    @Override
    @InternalStableApi
    public void unstashAll(Function1<Object, Object> filterPredicate) {
        StashSupport.unstashAll$(this, filterPredicate);
    }

    @Override
    @InternalStableApi
    public Vector<Envelope> clearStash() {
        return StashSupport.clearStash$(this);
    }

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
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
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public Vector<Envelope> akka$actor$StashSupport$$theStash() {
        return this.akka$actor$StashSupport$$theStash;
    }

    @Override
    public void akka$actor$StashSupport$$theStash_$eq(Vector<Envelope> x$1) {
        this.akka$actor$StashSupport$$theStash = x$1;
    }

    @Override
    public int akka$actor$StashSupport$$capacity() {
        return this.akka$actor$StashSupport$$capacity;
    }

    @Override
    public DequeBasedMessageQueueSemantics mailbox() {
        return this.mailbox;
    }

    @Override
    public final void akka$actor$StashSupport$_setter_$akka$actor$StashSupport$$capacity_$eq(int x$1) {
        this.akka$actor$StashSupport$$capacity = x$1;
    }

    @Override
    public void akka$actor$StashSupport$_setter_$mailbox_$eq(DequeBasedMessageQueueSemantics x$1) {
        this.mailbox = x$1;
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
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
        return this.idle();
    }

    public PartialFunction<Object, BoxedUnit> idle() {
        return this.idle;
    }

    public PartialFunction<Object, BoxedUnit> connecting() {
        return this.connecting;
    }

    public PartialFunction<Object, BoxedUnit> ready(ActorRef connection, ByteString buffer) {
        return new Serializable(this, connection, buffer){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpDnsClient $outer;
            private final ActorRef connection$1;
            private final ByteString buffer$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                Tcp.CommandFailed commandFailed;
                A1 A1 = x1;
                if (A1 instanceof Message) {
                    Message message = (Message)A1;
                    ByteString bytes = message.write();
                    this.connection$1.$bang(Tcp$Write$.MODULE$.apply(TcpDnsClient$.MODULE$.encodeLength(bytes.length()).$plus$plus(bytes)), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.CommandFailed && (commandFailed = (Tcp.CommandFailed)A1).cmd() instanceof Tcp.Write) {
                    TcpDnsClient$.MODULE$.throwFailure("Write failed", commandFailed.cause());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.Received) {
                    Tcp.Received received = (Tcp.Received)A1;
                    ByteString newData = received.data();
                    ByteString data = this.buffer$1.$plus$plus(newData);
                    int prefixSize = 2;
                    if (data.length() < prefixSize) {
                        this.$outer.context().become(this.$outer.ready(this.connection$1, data));
                        return (B1)BoxedUnit.UNIT;
                    }
                    int expectedPayloadLength = TcpDnsClient$.MODULE$.decodeLength(data);
                    if (data.drop(prefixSize).length() < expectedPayloadLength) {
                        this.$outer.context().become(this.$outer.ready(this.connection$1, data));
                        return (B1)BoxedUnit.UNIT;
                    }
                    this.$outer.akka$io$dns$internal$TcpDnsClient$$answerRecipient.$bang(this.$outer.akka$io$dns$internal$TcpDnsClient$$parseResponse(data.drop(prefixSize)), this.$outer.self());
                    this.$outer.context().become(this.$outer.ready(this.connection$1, ByteString$.MODULE$.empty()));
                    if (data.length() > prefixSize + expectedPayloadLength) {
                        this.$outer.self().$bang(new Tcp.Received(data.drop(prefixSize + expectedPayloadLength)), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (Tcp$PeerClosed$.MODULE$.equals(A1)) {
                    this.$outer.context().become(this.$outer.idle());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Tcp.CommandFailed commandFailed;
                Object object = x1;
                if (object instanceof Message) {
                    return true;
                }
                if (object instanceof Tcp.CommandFailed && (commandFailed = (Tcp.CommandFailed)object).cmd() instanceof Tcp.Write) {
                    return true;
                }
                if (object instanceof Tcp.Received) {
                    return true;
                }
                return Tcp$PeerClosed$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.connection$1 = connection$1;
                this.buffer$1 = buffer$1;
            }
        };
    }

    public ByteString ready$default$2() {
        return ByteString$.MODULE$.empty();
    }

    public DnsClient.Answer akka$io$dns$internal$TcpDnsClient$$parseResponse(ByteString data) {
        Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>> tuple2;
        Message msg = Message$.MODULE$.parse(data);
        this.log().debug("Decoded TCP DNS response [{}]", msg);
        if (MessageFlags$.MODULE$.isTruncated$extension(msg.flags())) {
            this.log().warning("TCP DNS response truncated");
        }
        Enumeration.Value value = MessageFlags$.MODULE$.responseCode$extension(msg.flags());
        Enumeration.Value value2 = ResponseCode$.MODULE$.SUCCESS();
        Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>> tuple22 = !(value != null ? !((Object)value).equals(value2) : value2 != null) ? new Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>>(msg.answerRecs(), msg.additionalRecs()) : (tuple2 = new Tuple2<Nil$, Nil$>(Nil$.MODULE$, Nil$.MODULE$));
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Seq<ResourceRecord> recs = tuple2._1();
        Seq<ResourceRecord> additionalRecs = tuple2._2();
        Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>> tuple23 = new Tuple2<Seq<ResourceRecord>, Seq<ResourceRecord>>(recs, additionalRecs);
        Seq<ResourceRecord> recs2 = tuple23._1();
        Seq<ResourceRecord> additionalRecs2 = tuple23._2();
        return new DnsClient.Answer(msg.id(), recs2, additionalRecs2);
    }

    public TcpDnsClient(ActorRef tcp, InetSocketAddress ns, ActorRef answerRecipient) {
        this.akka$io$dns$internal$TcpDnsClient$$tcp = tcp;
        this.akka$io$dns$internal$TcpDnsClient$$ns = ns;
        this.akka$io$dns$internal$TcpDnsClient$$answerRecipient = answerRecipient;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        StashSupport.$init$(this);
        UnrestrictedStash.$init$(this);
        this.idle = new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpDnsClient $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Message) {
                    this.$outer.stash();
                    this.$outer.log().debug("Connecting to [{}]", this.$outer.akka$io$dns$internal$TcpDnsClient$$ns);
                    this.$outer.akka$io$dns$internal$TcpDnsClient$$tcp.$bang(new Tcp.Connect(this.$outer.akka$io$dns$internal$TcpDnsClient$$ns, Tcp$Connect$.MODULE$.apply$default$2(), Tcp$Connect$.MODULE$.apply$default$3(), Tcp$Connect$.MODULE$.apply$default$4(), Tcp$Connect$.MODULE$.apply$default$5()), this.$outer.self());
                    this.$outer.context().become(this.$outer.connecting());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof Message;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
        this.connecting = new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpDnsClient $outer;

            public final <A1, B1> B1 applyOrElse(A1 x2, Function1<A1, B1> function1) {
                Tcp.CommandFailed commandFailed;
                A1 A1 = x2;
                if (A1 instanceof Tcp.CommandFailed && (commandFailed = (Tcp.CommandFailed)A1).cmd() instanceof Tcp.Connect) {
                    TcpDnsClient$.MODULE$.throwFailure(new StringBuilder(41).append("Failed to connect to TCP DNS server at [").append(this.$outer.akka$io$dns$internal$TcpDnsClient$$ns).append("]").toString(), commandFailed.cause());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.Connected) {
                    this.$outer.log().debug("Connected to TCP address [{}]", this.$outer.akka$io$dns$internal$TcpDnsClient$$ns);
                    ActorRef connection = this.$outer.sender();
                    this.$outer.context().become(this.$outer.ready(connection, this.$outer.ready$default$2()));
                    connection.$bang(new Tcp.Register(this.$outer.self(), Tcp$Register$.MODULE$.apply$default$2(), Tcp$Register$.MODULE$.apply$default$3()), this.$outer.self());
                    this.$outer.unstashAll();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Message) {
                    this.$outer.stash();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x2);
            }

            public final boolean isDefinedAt(Object x2) {
                Tcp.CommandFailed commandFailed;
                Object object = x2;
                if (object instanceof Tcp.CommandFailed && (commandFailed = (Tcp.CommandFailed)object).cmd() instanceof Tcp.Connect) {
                    return true;
                }
                if (object instanceof Tcp.Connected) {
                    return true;
                }
                return object instanceof Message;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
        Statics.releaseFence();
    }
}

