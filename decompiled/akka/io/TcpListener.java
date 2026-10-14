/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.NoSerializationVerificationNeeded;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.LoggingAdapter;
import akka.io.ChannelRegistration;
import akka.io.ChannelRegistry;
import akka.io.Inet;
import akka.io.SelectionHandler;
import akka.io.SelectionHandler$;
import akka.io.SelectionHandler$ChannelAcceptable$;
import akka.io.Tcp;
import akka.io.Tcp$Unbind$;
import akka.io.Tcp$Unbound$;
import akka.io.TcpExt;
import akka.io.TcpIncomingConnection;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\tEvA\u0002#F\u0011\u0003)\u0015J\u0002\u0004L\u000b\"\u0005Q\t\u0014\u0005\u0006'\u0006!\t!\u0016\u0004\u0005-\u0006\u0011u\u000b\u0003\u0005y\u0007\tU\r\u0011\"\u0001z\u0011%\tIa\u0001B\tB\u0003%!\u0010\u0003\u0004T\u0007\u0011\u0005\u00111\u0002\u0005\b\u0003'\u0019A\u0011AA\u000b\u0011%\t\tcAA\u0001\n\u0003\t9\nC\u0005\u0002(\r\t\n\u0011\"\u0001\u0002*!I\u0011qH\u0002\u0002\u0002\u0013\u0005\u0013\u0011\t\u0005\n\u0003\u001f\u001a\u0011\u0011!C\u0001\u0003#B\u0011\"!\u0017\u0004\u0003\u0003%\t!a'\t\u0013\u0005\u001d4!!A\u0005B\u0005%\u0004\"CA<\u0007\u0005\u0005I\u0011AAP\u0011%\t\u0019iAA\u0001\n\u0003\n\u0019\u000bC\u0005\u0002\n\u000e\t\t\u0011\"\u0011\u0002\f\"I\u0011QR\u0002\u0002\u0002\u0013\u0005\u0013q\u0012\u0005\n\u0003#\u001b\u0011\u0011!C!\u0003O;\u0011\"a+\u0002\u0003\u0003E\t!!,\u0007\u0011Y\u000b\u0011\u0011!E\u0001\u0003_Caa\u0015\u000b\u0005\u0002\u0005\u0015\u0007\"CAG)\u0005\u0005IQIAH\u0011%\t9\rFA\u0001\n\u0003\u000bI\rC\u0005\u0002NR\t\t\u0011\"!\u0002P\"I\u00111\u001c\u000b\u0002\u0002\u0013%\u0011Q\u001c\u0004\u0007\u00033\t!)a\u0007\t\u0011aT\"Q3A\u0005\u0002eD\u0011\"!\u0003\u001b\u0005#\u0005\u000b\u0011\u0002>\t\rMSB\u0011AA\u000f\u0011%\t\tCGA\u0001\n\u0003\t\u0019\u0003C\u0005\u0002(i\t\n\u0011\"\u0001\u0002*!I\u0011q\b\u000e\u0002\u0002\u0013\u0005\u0013\u0011\t\u0005\n\u0003\u001fR\u0012\u0011!C\u0001\u0003#B\u0011\"!\u0017\u001b\u0003\u0003%\t!a\u0017\t\u0013\u0005\u001d$$!A\u0005B\u0005%\u0004\"CA<5\u0005\u0005I\u0011AA=\u0011%\t\u0019IGA\u0001\n\u0003\n)\tC\u0005\u0002\nj\t\t\u0011\"\u0011\u0002\f\"I\u0011Q\u0012\u000e\u0002\u0002\u0013\u0005\u0013q\u0012\u0005\n\u0003#S\u0012\u0011!C!\u0003';\u0011\"!:\u0002\u0003\u0003E\t!a:\u0007\u0013\u0005e\u0011!!A\t\u0002\u0005%\bBB*+\t\u0003\ti\u000fC\u0005\u0002\u000e*\n\t\u0011\"\u0012\u0002\u0010\"I\u0011q\u0019\u0016\u0002\u0002\u0013\u0005\u0015q\u001e\u0005\n\u0003\u001bT\u0013\u0011!CA\u0003gD\u0011\"a7+\u0003\u0003%I!!8\u0007\r-+\u0005!RA|\u0011)\u00119\u0002\rB\u0001B\u0003%!\u0011\u0004\u0005\u000b\u0005?\u0001$\u0011!Q\u0001\n\t\u0005\u0002B\u0003B\u0014a\t\u0005\t\u0015!\u0003\u0003*!Q!q\u0006\u0019\u0003\u0002\u0003\u0006IA!\u0007\t\u0015\tE\u0002G!A!\u0002\u0013\u0011\u0019\u0004\u0003\u0004Ta\u0011\u0005!\u0011\t\u0005\tqB\u0012\r\u0011\"\u0001\u0003P!A\u0011\u0011\u0002\u0019!\u0002\u0013\u0011\t\u0006C\u0005\u0003XA\u0002\r\u0011\"\u0001\u0002R!I!\u0011\f\u0019A\u0002\u0013\u0005!1\f\u0005\t\u0005K\u0002\u0004\u0015)\u0003\u0002T!I!q\r\u0019C\u0002\u0013\u0005!\u0011\u000e\u0005\t\u0005W\u0002\u0004\u0015!\u0003\u0002^!9!Q\u000e\u0019\u0005B\t=\u0004b\u0002B<a\u0011\u0005!\u0011\u0010\u0005\b\u0005\u0007\u0003D\u0011\u0001BC\u0011\u001d\u0011\t\n\rC\u0001\u0005'CqA!'1\t\u000b\u0011Y\nC\u0004\u0003.B\"\tEa,\u0002\u0017Q\u001b\u0007\u000fT5ti\u0016tWM\u001d\u0006\u0003\r\u001e\u000b!![8\u000b\u0003!\u000bA!Y6lCB\u0011!*A\u0007\u0002\u000b\nYAk\u00199MSN$XM\\3s'\t\tQ\n\u0005\u0002O#6\tqJC\u0001Q\u0003\u0015\u00198-\u00197b\u0013\t\u0011vJ\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0011J\u0001\tSK\u001eL7\u000f^3s\u0013:\u001cw.\\5oON11!\u0014-gY>\u0004\"!W2\u000f\u0005i\u000bgBA.a\u001d\tav,D\u0001^\u0015\tqF+\u0001\u0004=e>|GOP\u0005\u0002\u0011&\u0011aiR\u0005\u0003E\u0016\u000b\u0001cU3mK\u000e$\u0018n\u001c8IC:$G.\u001a:\n\u0005\u0011,'!\u0005%bg\u001a\u000b\u0017\u000e\\;sK6+7o]1hK*\u0011!-\u0012\t\u0003O*l\u0011\u0001\u001b\u0006\u0003S\u001e\u000bQ!Y2u_JL!a\u001b5\u0003C9{7+\u001a:jC2L'0\u0019;j_:4VM]5gS\u000e\fG/[8o\u001d\u0016,G-\u001a3\u0011\u00059k\u0017B\u00018P\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001];\u000f\u0005E\u001chB\u0001/s\u0013\u0005\u0001\u0016B\u0001;P\u0003\u001d\u0001\u0018mY6bO\u0016L!A^<\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Q|\u0015aB2iC:tW\r\\\u000b\u0002uB\u001910!\u0002\u000e\u0003qT!! @\u0002\u0011\rD\u0017M\u001c8fYNT1a`A\u0001\u0003\rq\u0017n\u001c\u0006\u0003\u0003\u0007\tAA[1wC&\u0019\u0011q\u0001?\u0003\u001bM{7m[3u\u0007\"\fgN\\3m\u0003!\u0019\u0007.\u00198oK2\u0004C\u0003BA\u0007\u0003#\u00012!a\u0004\u0004\u001b\u0005\t\u0001\"\u0002=\u0007\u0001\u0004Q\u0018A\u00044bS2,(/Z'fgN\fw-Z\u000b\u0003\u0003/\u00012!a\u0004\u001b\u0005Y1\u0015-\u001b7fIJ+w-[:uKJLenY8nS:<7#\u0002\u000eNM2|G\u0003BA\f\u0003?AQ\u0001_\u000fA\u0002i\fAaY8qsR!\u0011qCA\u0013\u0011\u001dAh\u0004%AA\u0002i\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002,)\u001a!0!\f,\u0005\u0005=\u0002\u0003BA\u0019\u0003wi!!a\r\u000b\t\u0005U\u0012qG\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u000fP\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003{\t\u0019DA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\"!\u0011\t)%a\u0013\u000e\u0005\u0005\u001d#\u0002BA%\u0003\u0003\tA\u0001\\1oO&!\u0011QJA$\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u000b\t\u0004\u001d\u0006U\u0013bAA,\u001f\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011QLA2!\rq\u0015qL\u0005\u0004\u0003Cz%aA!os\"I\u0011Q\r\u0012\u0002\u0002\u0003\u0007\u00111K\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005-\u0004CBA7\u0003g\ni&\u0004\u0002\u0002p)\u0019\u0011\u0011O(\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002v\u0005=$\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u001f\u0002\u0002B\u0019a*! \n\u0007\u0005}tJA\u0004C_>dW-\u00198\t\u0013\u0005\u0015D%!AA\u0002\u0005u\u0013A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u0011\u0002\b\"I\u0011QM\u0013\u0002\u0002\u0003\u0007\u00111K\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111K\u0001\ti>\u001cFO]5oOR\u0011\u00111I\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005m\u0014Q\u0013\u0005\n\u0003KB\u0013\u0011!a\u0001\u0003;\"B!!\u0004\u0002\u001a\"9\u0001\u0010\u0003I\u0001\u0002\u0004QH\u0003BA/\u0003;C\u0011\"!\u001a\r\u0003\u0003\u0005\r!a\u0015\u0015\t\u0005m\u0014\u0011\u0015\u0005\n\u0003Kr\u0011\u0011!a\u0001\u0003;\"B!a\u0011\u0002&\"I\u0011QM\b\u0002\u0002\u0003\u0007\u00111\u000b\u000b\u0005\u0003w\nI\u000bC\u0005\u0002fI\t\t\u00111\u0001\u0002^\u0005\u0001\"+Z4jgR,'/\u00138d_6Lgn\u001a\t\u0004\u0003\u001f!2#\u0002\u000b\u00022\u0006u\u0006cBAZ\u0003sS\u0018QB\u0007\u0003\u0003kS1!a.P\u0003\u001d\u0011XO\u001c;j[\u0016LA!a/\u00026\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005}\u00161Y\u0007\u0003\u0003\u0003T1ARA\u0001\u0013\r1\u0018\u0011\u0019\u000b\u0003\u0003[\u000bQ!\u00199qYf$B!!\u0004\u0002L\")\u0001p\u0006a\u0001u\u00069QO\\1qa2LH\u0003BAi\u0003/\u0004BATAju&\u0019\u0011Q[(\u0003\r=\u0003H/[8o\u0011%\tI\u000eGA\u0001\u0002\u0004\ti!A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a8\u0011\t\u0005\u0015\u0013\u0011]\u0005\u0005\u0003G\f9E\u0001\u0004PE*,7\r^\u0001\u0017\r\u0006LG.\u001a3SK\u001eL7\u000f^3s\u0013:\u001cw.\\5oOB\u0019\u0011q\u0002\u0016\u0014\u000b)\nY/!0\u0011\u000f\u0005M\u0016\u0011\u0018>\u0002\u0018Q\u0011\u0011q\u001d\u000b\u0005\u0003/\t\t\u0010C\u0003y[\u0001\u0007!\u0010\u0006\u0003\u0002R\u0006U\b\"CAm]\u0005\u0005\t\u0019AA\f'!\u0001T*!?\u0002\u0000\n\u0015\u0001cA4\u0002|&\u0019\u0011Q 5\u0003\u000b\u0005\u001bGo\u001c:\u0011\u0007\u001d\u0014\t!C\u0002\u0003\u0004!\u0014A\"Q2u_JdunZ4j]\u001e\u0004bAa\u0002\u0003\u000e\tEQB\u0001B\u0005\u0015\r\u0011YaR\u0001\tI&\u001c\b/\u0019;dQ&!!q\u0002B\u0005\u0005Q\u0011V-];je\u0016\u001cX*Z:tC\u001e,\u0017+^3vKB!!q\u0001B\n\u0013\u0011\u0011)B!\u0003\u0003=Us'm\\;oI\u0016$W*Z:tC\u001e,\u0017+^3vKN+W.\u00198uS\u000e\u001c\u0018AD:fY\u0016\u001cGo\u001c:S_V$XM\u001d\t\u0004O\nm\u0011b\u0001B\u000fQ\nA\u0011i\u0019;peJ+g-A\u0002uGB\u00042A\u0013B\u0012\u0013\r\u0011)#\u0012\u0002\u0007)\u000e\u0004X\t\u001f;\u0002\u001f\rD\u0017M\u001c8fYJ+w-[:uef\u00042A\u0013B\u0016\u0013\r\u0011i#\u0012\u0002\u0010\u0007\"\fgN\\3m%\u0016<\u0017n\u001d;ss\u0006i!-\u001b8e\u0007>lW.\u00198eKJ\fAAY5oIB!!Q\u0007B\u001e\u001d\rQ&qG\u0005\u0004\u0005s)\u0015a\u0001+da&!!Q\bB \u0005\u0011\u0011\u0015N\u001c3\u000b\u0007\teR\t\u0006\u0007\u0003D\t\u0015#q\tB%\u0005\u0017\u0012i\u0005\u0005\u0002Ka!9!q\u0003\u001cA\u0002\te\u0001b\u0002B\u0010m\u0001\u0007!\u0011\u0005\u0005\b\u0005O1\u0004\u0019\u0001B\u0015\u0011\u001d\u0011yC\u000ea\u0001\u00053AqA!\r7\u0001\u0004\u0011\u0019$\u0006\u0002\u0003RA\u00191Pa\u0015\n\u0007\tUCPA\nTKJ4XM]*pG.,Go\u00115b]:,G.A\u0006bG\u000e,\u0007\u000f\u001e'j[&$\u0018aD1dG\u0016\u0004H\u000fT5nSR|F%Z9\u0015\t\tu#1\r\t\u0004\u001d\n}\u0013b\u0001B1\u001f\n!QK\\5u\u0011%\t)GOA\u0001\u0002\u0004\t\u0019&\u0001\u0007bG\u000e,\u0007\u000f\u001e'j[&$\b%\u0001\u0007m_\u000e\fG.\u00113ee\u0016\u001c8/\u0006\u0002\u0002^\u0005iAn\\2bY\u0006#GM]3tg\u0002\n!c];qKJ4\u0018n]8s'R\u0014\u0018\r^3hsV\u0011!\u0011\u000f\t\u0004O\nM\u0014b\u0001B;Q\n\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0003\u001d\u0011XmY3jm\u0016,\"Aa\u001f\u0011\t\tu$qP\u0007\u0002a%!!\u0011QA~\u0005\u001d\u0011VmY3jm\u0016\fQAY8v]\u0012$BAa\u001f\u0003\b\"9!\u0011\u0012!A\u0002\t-\u0015\u0001\u0004:fO&\u001cHO]1uS>t\u0007c\u0001&\u0003\u000e&\u0019!qR#\u0003'\rC\u0017M\u001c8fYJ+w-[:ue\u0006$\u0018n\u001c8\u0002\u001bUt'/Z4jgR,'/\u001b8h)\u0011\u0011YH!&\t\u000f\t]\u0015\t1\u0001\u0003\u001a\u0005I!/Z9vKN$XM]\u0001\u0011C\u000e\u001cW\r\u001d;BY2\u0004VM\u001c3j]\u001e$b!a\u0015\u0003\u001e\n}\u0005b\u0002BE\u0005\u0002\u0007!1\u0012\u0005\b\u0005C\u0013\u0005\u0019AA*\u0003\u0015a\u0017.\\5uQ\r\u0011%Q\u0015\t\u0005\u0005O\u0013I+\u0004\u0002\u00028%!!1VA\u001c\u0005\u001d!\u0018-\u001b7sK\u000e\f\u0001\u0002]8tiN#x\u000e\u001d\u000b\u0003\u0005;\u0002")
public class TcpListener
implements Actor,
ActorLogging,
RequiresMessageQueue<UnboundedMessageQueueSemantics> {
    private final ActorRef selectorRouter;
    private final TcpExt tcp;
    private final ChannelRegistry channelRegistry;
    public final ActorRef akka$io$TcpListener$$bindCommander;
    private final Tcp.Bind bind;
    private final ServerSocketChannel channel;
    private int acceptLimit;
    private final Object localAddress;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

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
    public void preStart() throws Exception {
        Actor.preStart$(this);
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

    public ServerSocketChannel channel() {
        return this.channel;
    }

    public int acceptLimit() {
        return this.acceptLimit;
    }

    public void acceptLimit_$eq(int x$1) {
        this.acceptLimit = x$1;
    }

    public Object localAddress() {
        return this.localAddress;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return SelectionHandler$.MODULE$.connectionSupervisorStrategy();
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpListener $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ChannelRegistration) {
                    ChannelRegistration channelRegistration = (ChannelRegistration)A1;
                    this.$outer.akka$io$TcpListener$$bindCommander.$bang(new Tcp.Bound((InetSocketAddress)this.$outer.channel().socket().getLocalSocketAddress()), this.$outer.self());
                    this.$outer.context().become(this.$outer.bound(channelRegistration));
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof ChannelRegistration;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> bound(ChannelRegistration registration) {
        return new Serializable(this, registration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpListener $outer;
            private final ChannelRegistration registration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (SelectionHandler$ChannelAcceptable$.MODULE$.equals(A1)) {
                    this.$outer.acceptLimit_$eq(this.$outer.acceptAllPending(this.registration$1, this.$outer.acceptLimit()));
                    if (this.$outer.acceptLimit() > 0) {
                        this.registration$1.enableInterest(16);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Tcp.ResumeAccepting) {
                    Tcp.ResumeAccepting resumeAccepting = (Tcp.ResumeAccepting)A1;
                    int batchSize = resumeAccepting.batchSize();
                    this.$outer.acceptLimit_$eq(batchSize);
                    this.registration$1.enableInterest(16);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof FailedRegisterIncoming) {
                    BoxedUnit boxedUnit;
                    FailedRegisterIncoming failedRegisterIncoming = (FailedRegisterIncoming)A1;
                    SocketChannel socketChannel = failedRegisterIncoming.channel();
                    this.$outer.log().warning("Could not register incoming connection since selector capacity limit is reached, closing connection");
                    try {
                        socketChannel.close();
                        boxedUnit = BoxedUnit.UNIT;
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 == null || (option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            throw throwable;
                        }
                        Throwable e = option.get();
                        this.$outer.log().debug("Error closing socket channel: {}", e);
                        boxedUnit = BoxedUnit.UNIT;
                    }
                    return (B1)boxedUnit;
                }
                if (Tcp$Unbind$.MODULE$.equals(A1)) {
                    this.$outer.log().debug("Unbinding endpoint {}", this.$outer.localAddress());
                    this.registration$1.cancelAndClose(() -> $this.$outer.self().$bang(Tcp$Unbound$.MODULE$, $this.$outer.self()));
                    this.$outer.context().become(this.$outer.unregistering(this.$outer.sender()));
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (SelectionHandler$ChannelAcceptable$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof Tcp.ResumeAccepting) {
                    return true;
                }
                if (object instanceof FailedRegisterIncoming) {
                    return true;
                }
                return Tcp$Unbind$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.registration$1 = registration$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.TcpListener$$anonfun$bound$1 )}, serializedLambda);
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> unregistering(ActorRef requester) {
        return new Serializable(this, requester){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpListener $outer;
            private final ActorRef requester$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (Tcp$Unbound$.MODULE$.equals(A1)) {
                    this.requester$1.$bang(Tcp$Unbound$.MODULE$, this.$outer.self());
                    this.$outer.log().debug("Unbound endpoint {}, stopping listener", this.$outer.localAddress());
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return Tcp$Unbound$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.requester$1 = requester$1;
            }
        };
    }

    public final int acceptAllPending(ChannelRegistration registration, int limit) {
        while (true) {
            SocketChannel socketChannel;
            SocketChannel socketChannel2;
            if (limit > 0) {
                try {
                    socketChannel2 = this.channel().accept();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable e = option.get();
                        this.log().error(e, "Accept error: could not accept new connection");
                        socketChannel2 = null;
                    }
                    throw throwable;
                }
            } else {
                socketChannel2 = null;
            }
            if ((socketChannel = socketChannel2) == null) break;
            this.log().debug("New connection accepted");
            socketChannel.configureBlocking(false);
            this.selectorRouter.$bang(new SelectionHandler.WorkerForCommand(new RegisterIncoming(socketChannel), this.self(), (Function1<ChannelRegistry, Props> & Serializable)registry -> this.props$1((ChannelRegistry)registry, socketChannel)), this.self());
            --limit;
        }
        if (this.bind.pullMode()) {
            return limit;
        }
        return this.tcp.Settings().BatchAcceptLimit();
    }

    @Override
    public void postStop() {
        try {
            if (this.channel().isOpen()) {
                this.log().debug("Closing serverSocketChannel after being stopped");
                this.channel().close();
            }
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.log().debug("Error closing ServerSocketChannel: {}", e);
            }
            throw throwable;
        }
    }

    public static final /* synthetic */ void $anonfun$localAddress$2(TcpListener $this, Inet.SocketOption x0$1) {
        Inet.SocketOption socketOption = x0$1;
        if (socketOption instanceof Inet.SocketOptionV2) {
            Inet.SocketOptionV2 socketOptionV2 = (Inet.SocketOptionV2)socketOption;
            socketOptionV2.afterBind($this.channel().socket());
            return;
        }
    }

    private final /* synthetic */ Object liftedTree1$1() {
        Serializable serializable;
        try {
            ServerSocket socket = this.channel().socket();
            this.bind.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x$1 -> {
                x$1.beforeServerSocketBind(socket);
                return BoxedUnit.UNIT;
            });
            socket.bind(this.bind.localAddress(), this.bind.backlog());
            SocketAddress socketAddress = socket.getLocalSocketAddress();
            if (!(socketAddress instanceof InetSocketAddress)) {
                throw new IllegalArgumentException(new StringBuilder(33).append("bound to unknown SocketAddress [").append(socketAddress).append("]").toString());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            InetSocketAddress ret = inetSocketAddress;
            this.channelRegistry.register(this.channel(), this.bind.pullMode() ? 0 : 16, this.self());
            this.log().debug("Successfully bound to {}", ret);
            this.bind.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x0$1 -> {
                TcpListener.$anonfun$localAddress$2(this, x0$1);
                return BoxedUnit.UNIT;
            });
            serializable = ret;
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable throwable3;
                Throwable e = option.get();
                if (e instanceof BindException) {
                    BindException newException = new BindException(new StringBuilder(3).append("[").append(this.bind.localAddress()).append("] ").append(e.getMessage()).toString());
                    newException.setStackTrace(e.getStackTrace());
                    throwable3 = newException;
                } else {
                    throwable3 = e;
                }
                Throwable exception = throwable3;
                this.akka$io$TcpListener$$bindCommander.$bang(new Tcp.CommandFailed(this.bind).withCause(exception), this.self());
                this.log().error(exception, "Bind failed for TCP channel on endpoint [{}]", (Object)this.bind.localAddress());
                this.context().stop(this.self());
                serializable = BoxedUnit.UNIT;
            }
            throw throwable;
        }
        return serializable;
    }

    private final Props props$1(ChannelRegistry registry, SocketChannel socketChannel$1) {
        return Props$.MODULE$.apply(TcpIncomingConnection.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.tcp, socketChannel$1, registry, this.bind.handler(), this.bind.options(), BoxesRunTime.boxToBoolean(this.bind.pullMode())}));
    }

    public TcpListener(ActorRef selectorRouter, TcpExt tcp, ChannelRegistry channelRegistry, ActorRef bindCommander, Tcp.Bind bind) {
        this.selectorRouter = selectorRouter;
        this.tcp = tcp;
        this.channelRegistry = channelRegistry;
        this.akka$io$TcpListener$$bindCommander = bindCommander;
        this.bind = bind;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.context().watch(bind.handler());
        this.channel = ServerSocketChannel.open();
        this.channel().configureBlocking(false);
        this.acceptLimit = bind.pullMode() ? 0 : tcp.Settings().BatchAcceptLimit();
        this.localAddress = this.liftedTree1$1();
        Statics.releaseFence();
    }

    public static final class FailedRegisterIncoming
    implements NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final SocketChannel channel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public SocketChannel channel() {
            return this.channel;
        }

        public FailedRegisterIncoming copy(SocketChannel channel) {
            return new FailedRegisterIncoming(channel);
        }

        public SocketChannel copy$default$1() {
            return this.channel();
        }

        @Override
        public String productPrefix() {
            return "FailedRegisterIncoming";
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
                    return this.channel();
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
            return x$1 instanceof FailedRegisterIncoming;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "channel";
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
            if (!(object instanceof FailedRegisterIncoming)) return false;
            boolean bl = true;
            if (!bl) return false;
            FailedRegisterIncoming failedRegisterIncoming = (FailedRegisterIncoming)x$1;
            SocketChannel socketChannel = this.channel();
            SocketChannel socketChannel2 = failedRegisterIncoming.channel();
            if (socketChannel != null) {
                if (!socketChannel.equals(socketChannel2)) return false;
                return true;
            }
            if (socketChannel2 == null) return true;
            return false;
        }

        public FailedRegisterIncoming(SocketChannel channel) {
            this.channel = channel;
            Product.$init$(this);
        }
    }

    public static final class RegisterIncoming
    implements SelectionHandler.HasFailureMessage,
    NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final SocketChannel channel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public SocketChannel channel() {
            return this.channel;
        }

        @Override
        public FailedRegisterIncoming failureMessage() {
            return new FailedRegisterIncoming(this.channel());
        }

        public RegisterIncoming copy(SocketChannel channel) {
            return new RegisterIncoming(channel);
        }

        public SocketChannel copy$default$1() {
            return this.channel();
        }

        @Override
        public String productPrefix() {
            return "RegisterIncoming";
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
                    return this.channel();
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
            return x$1 instanceof RegisterIncoming;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "channel";
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
            if (!(object instanceof RegisterIncoming)) return false;
            boolean bl = true;
            if (!bl) return false;
            RegisterIncoming registerIncoming = (RegisterIncoming)x$1;
            SocketChannel socketChannel = this.channel();
            SocketChannel socketChannel2 = registerIncoming.channel();
            if (socketChannel != null) {
                if (!socketChannel.equals(socketChannel2)) return false;
                return true;
            }
            if (socketChannel2 == null) return true;
            return false;
        }

        public RegisterIncoming(SocketChannel channel) {
            this.channel = channel;
            Product.$init$(this);
        }
    }
}

