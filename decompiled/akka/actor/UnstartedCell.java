/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.Cell;
import akka.actor.ChildRestartStats;
import akka.actor.DeadLetter;
import akka.actor.InternalActorRef;
import akka.actor.Nobody$;
import akka.actor.Props;
import akka.actor.RepointableActorRef;
import akka.actor.dungeon.ChildrenContainer;
import akka.actor.dungeon.ChildrenContainer$EmptyChildrenContainer$;
import akka.annotation.InternalStableApi;
import akka.dispatch.Envelope;
import akka.dispatch.sysmsg.EarliestFirstSystemMessageList$;
import akka.dispatch.sysmsg.LatestFirstSystemMessageList$;
import akka.dispatch.sysmsg.Recreate;
import akka.dispatch.sysmsg.Resume;
import akka.dispatch.sysmsg.Suspend;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.SystemMessageList$;
import akka.dispatch.sysmsg.Terminate;
import akka.event.Logging;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005}f!\u0002\u0011\"\u0001\r*\u0003\u0002\u0003\u0019\u0001\u0005\u000b\u0007I\u0011\u0001\u001a\t\u0011Y\u0002!\u0011!Q\u0001\nMB\u0001b\u000e\u0001\u0003\u0006\u0004%\t\u0001\u000f\u0005\ty\u0001\u0011\t\u0011)A\u0005s!AQ\b\u0001BC\u0002\u0013\u0005a\b\u0003\u0005C\u0001\t\u0005\t\u0015!\u0003@\u0011!\u0019\u0005A!b\u0001\n\u0003!\u0005\u0002\u0003%\u0001\u0005\u0003\u0005\u000b\u0011B#\t\u000b%\u0003A\u0011\u0001&\t\rA\u0003\u0001\u0015!\u0004R\u0011\u0019i\u0006\u0001)A\u0007=\"1\u0001\u000e\u0001Q!\n%DQa\u001c\u0001\u0005\u0002ADQA\u001e\u0001\u0005\u0002]DQa\u001f\u0001\u0005\u0002qDQA \u0001\u0005\u0002}Dq!!\u0001\u0001\t\u0003\t\u0019\u0001C\u0004\u0002\"\u0001!\t!a\t\t\r\u0005%\u0002\u0001\"\u0001\u0000\u0011!\tY\u0003\u0001C!G\u00055\u0002BBA\u001b\u0001\u0011\u0005A\tC\u0004\u00028\u0001!\t!!\u000f\t\u000f\u0005\u001d\u0003\u0001\"\u0001\u0002J!9\u00111\u000e\u0001\u0005B\u00055\u0004bBA9\u0001\u0011\u0005\u00111\u000f\u0005\b\u0003s\u0002A\u0011AA>\u0011\u001d\t)\t\u0001C\u0001\u0003[A\u0001\"a\"\u0001A\u00135\u0011\u0011\u0012\u0005\b\u0003\u001b\u0003A\u0011AA\u0017\u0011\u001d\ty\t\u0001C\u0001\u0003#C\u0001\"!'\u0001A\u00135\u00111\u0014\u0002\u000e+:\u001cH/\u0019:uK\u0012\u001cU\r\u001c7\u000b\u0005\t\u001a\u0013!B1di>\u0014(\"\u0001\u0013\u0002\t\u0005\\7.Y\n\u0004\u0001\u0019b\u0003CA\u0014+\u001b\u0005A#\"A\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005-B#AB!osJ+g\r\u0005\u0002.]5\t\u0011%\u0003\u00020C\t!1)\u001a7m\u0003)\u0019\u0018p\u001d;f[&k\u0007\u000f\\\u0002\u0001+\u0005\u0019\u0004CA\u00175\u0013\t)\u0014EA\bBGR|'oU=ti\u0016l\u0017*\u001c9m\u0003-\u0019\u0018p\u001d;f[&k\u0007\u000f\u001c\u0011\u0002\tM,GNZ\u000b\u0002sA\u0011QFO\u0005\u0003w\u0005\u00121CU3q_&tG/\u00192mK\u0006\u001bGo\u001c:SK\u001a\fQa]3mM\u0002\nQ\u0001\u001d:paN,\u0012a\u0010\t\u0003[\u0001K!!Q\u0011\u0003\u000bA\u0013x\u000e]:\u0002\rA\u0014x\u000e]:!\u0003)\u0019X\u000f]3sm&\u001cxN]\u000b\u0002\u000bB\u0011QFR\u0005\u0003\u000f\u0006\u0012\u0001#\u00138uKJt\u0017\r\\!di>\u0014(+\u001a4\u0002\u0017M,\b/\u001a:wSN|'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u000b-cUJT(\u0011\u00055\u0002\u0001\"\u0002\u0019\n\u0001\u0004\u0019\u0004\"B\u001c\n\u0001\u0004I\u0004\"B\u001f\n\u0001\u0004y\u0004\"B\"\n\u0001\u0004)\u0015\u0001\u00027pG.\u0004\"AU.\u000e\u0003MS!\u0001V+\u0002\u000b1|7m[:\u000b\u0005Y;\u0016AC2p]\u000e,(O]3oi*\u0011\u0001,W\u0001\u0005kRLGNC\u0001[\u0003\u0011Q\u0017M^1\n\u0005q\u001b&!\u0004*fK:$(/\u00198u\u0019>\u001c7.A\u0003rk\u0016,X\rE\u0002`A\nl\u0011aV\u0005\u0003C^\u0013!\u0002T5oW\u0016$G*[:u!\t\u0019g-D\u0001e\u0015\t)7%\u0001\u0005eSN\u0004\u0018\r^2i\u0013\t9GM\u0001\u0005F]Z,Gn\u001c9f\u0003-\u0019\u0018p]7tOF+X-^3\u0011\u0005)lW\"A6\u000b\u00051$\u0017AB:zg6\u001cx-\u0003\u0002oW\naB*\u0019;fgR4\u0015N]:u'f\u001cH/Z7NKN\u001c\u0018mZ3MSN$\u0018a\u0003:fa2\f7-Z,ji\"$\"!\u001d;\u0011\u0005\u001d\u0012\u0018BA:)\u0005\u0011)f.\u001b;\t\u000bUl\u0001\u0019\u0001\u0017\u0002\t\r,G\u000e\\\u0001\u0007gf\u001cH/Z7\u0016\u0003a\u0004\"!L=\n\u0005i\f#aC!di>\u00148+_:uK6\fQa\u001d;beR$\u0012!`\u0007\u0002\u0001\u000591/^:qK:$G#A9\u0002\rI,7/^7f)\r\t\u0018Q\u0001\u0005\b\u0003\u000f\t\u0002\u0019AA\u0005\u0003=\u0019\u0017-^:fI\nKh)Y5mkJ,\u0007\u0003BA\u0006\u00037qA!!\u0004\u0002\u00189!\u0011qBA\u000b\u001b\t\t\tBC\u0002\u0002\u0014E\na\u0001\u0010:p_Rt\u0014\"A\u0015\n\u0007\u0005e\u0001&A\u0004qC\u000e\\\u0017mZ3\n\t\u0005u\u0011q\u0004\u0002\n)\"\u0014xn^1cY\u0016T1!!\u0007)\u0003\u001d\u0011Xm\u001d;beR$2!]A\u0013\u0011\u001d\t9C\u0005a\u0001\u0003\u0013\tQaY1vg\u0016\fAa\u001d;pa\u0006a\u0011n\u001d+fe6Lg.\u0019;fIV\u0011\u0011q\u0006\t\u0004O\u0005E\u0012bAA\u001aQ\t9!i\\8mK\u0006t\u0017A\u00029be\u0016tG/\u0001\u0007dQ&dGM]3o%\u001647/\u0006\u0002\u0002<A!\u0011QHA\"\u001b\t\tyDC\u0002\u0002B\u0005\nq\u0001Z;oO\u0016|g.\u0003\u0003\u0002F\u0005}\"!E\"iS2$'/\u001a8D_:$\u0018-\u001b8fe\u0006qq-\u001a;DQ&dGMQ=OC6,G\u0003BA&\u0003/\u0002RaJA'\u0003#J1!a\u0014)\u0005\u0019y\u0005\u000f^5p]B\u0019Q&a\u0015\n\u0007\u0005U\u0013EA\tDQ&dGMU3ti\u0006\u0014Ho\u0015;biNDq!!\u0017\u0018\u0001\u0004\tY&\u0001\u0003oC6,\u0007\u0003BA/\u0003KrA!a\u0018\u0002bA\u0019\u0011q\u0002\u0015\n\u0007\u0005\r\u0004&\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003O\nIG\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003GB\u0013AD4fiNKgn\u001a7f\u0007\"LG\u000e\u001a\u000b\u0004\u000b\u0006=\u0004bBA-1\u0001\u0007\u00111L\u0001\fg\u0016tG-T3tg\u0006<W\rF\u0002r\u0003kBa!a\u001e\u001a\u0001\u0004\u0011\u0017aA7tO\u0006\t2/\u001a8e'f\u001cH/Z7NKN\u001c\u0018mZ3\u0015\u0007E\fi\bC\u0004\u0002xi\u0001\r!a \u0011\u0007)\f\t)C\u0002\u0002\u0004.\u0014QbU=ti\u0016lW*Z:tC\u001e,\u0017aB5t\u0019>\u001c\u0017\r\\\u0001\fG\u0016dG.S:SK\u0006$\u0017\u0010\u0006\u0003\u00020\u0005-\u0005\"B;\u001d\u0001\u0004a\u0013a\u00035bg6+7o]1hKN\f\u0001C\\;nE\u0016\u0014xJZ'fgN\fw-Z:\u0016\u0005\u0005M\u0005cA\u0014\u0002\u0016&\u0019\u0011q\u0013\u0015\u0003\u0007%sG/\u0001\u0004m_\u000e\\W\rZ\u000b\u0005\u0003;\u000b\u0019\u000b\u0006\u0003\u0002 \u0006U\u0006\u0003BAQ\u0003Gc\u0001\u0001B\u0004\u0002&~\u0011\r!a*\u0003\u0003Q\u000bB!!+\u00020B\u0019q%a+\n\u0007\u00055\u0006FA\u0004O_RD\u0017N\\4\u0011\u0007\u001d\n\t,C\u0002\u00024\"\u00121!\u00118z\u0011!\t9l\bCA\u0002\u0005e\u0016\u0001\u00022pIf\u0004RaJA^\u0003?K1!!0)\u0005!a$-\u001f8b[\u0016t\u0004")
public class UnstartedCell
implements Cell {
    private final ActorSystemImpl systemImpl;
    private final RepointableActorRef self;
    private final Props props;
    private final InternalActorRef supervisor;
    private final ReentrantLock lock;
    private final LinkedList<Envelope> queue;
    private SystemMessage sysmsgQueue;

    @Override
    @InternalStableApi
    public final void sendMessage(Object message, ActorRef sender) {
        Cell.sendMessage$(this, message, sender);
    }

    @Override
    public ActorSystemImpl systemImpl() {
        return this.systemImpl;
    }

    @Override
    public RepointableActorRef self() {
        return this.self;
    }

    @Override
    public Props props() {
        return this.props;
    }

    public InternalActorRef supervisor() {
        return this.supervisor;
    }

    public void replaceWith(Cell cell) {
        this.locked(() -> {
            try {
                this.drainSysmsgQueue$1(cell);
                while (!$this.queue.isEmpty()) {
                    cell.sendMessage($this.queue.poll());
                    this.drainSysmsgQueue$1(cell);
                }
            }
            finally {
                this.self().swapCell(cell);
            }
        });
    }

    @Override
    public ActorSystem system() {
        return this.systemImpl();
    }

    @Override
    public UnstartedCell start() {
        return this;
    }

    @Override
    public void suspend() {
        this.sendSystemMessage(new Suspend());
    }

    @Override
    public void resume(Throwable causedByFailure) {
        this.sendSystemMessage(new Resume(causedByFailure));
    }

    @Override
    public void restart(Throwable cause) {
        this.sendSystemMessage(new Recreate(cause));
    }

    @Override
    public void stop() {
        this.sendSystemMessage(new Terminate());
    }

    @Override
    public boolean isTerminated() {
        return BoxesRunTime.unboxToBoolean(this.locked(() -> {
            Cell cell = this.self().underlying();
            if (this.cellIsReady(cell)) {
                return cell.isTerminated();
            }
            return false;
        }));
    }

    @Override
    public InternalActorRef parent() {
        return this.supervisor();
    }

    @Override
    public ChildrenContainer childrenRefs() {
        return ChildrenContainer$EmptyChildrenContainer$.MODULE$;
    }

    public Option<ChildRestartStats> getChildByName(String name) {
        return None$.MODULE$;
    }

    @Override
    public InternalActorRef getSingleChild(String name) {
        return Nobody$.MODULE$;
    }

    @Override
    public void sendMessage(Envelope msg) {
        if (this.lock.tryLock(this.systemImpl().settings().UnstartedPushTimeout().duration().length(), this.systemImpl().settings().UnstartedPushTimeout().duration().unit())) {
            try {
                Cell cell = this.self().underlying();
                if (this.cellIsReady(cell)) {
                    cell.sendMessage(msg);
                } else if (!this.queue.offer(msg)) {
                    this.system().eventStream().publish(new Logging.Warning(this.self().path().toString(), this.getClass(), new StringBuilder(48).append("dropping message of type ").append(msg.message().getClass()).append(" due to enqueue failure").toString()));
                    this.system().deadLetters().tell(new DeadLetter(msg.message(), msg.sender(), this.self()), msg.sender());
                }
            }
            finally {
                this.lock.unlock();
            }
            return;
        }
        this.system().eventStream().publish(new Logging.Warning(this.self().path().toString(), this.getClass(), new StringBuilder(44).append("dropping message of type").append(msg.message().getClass()).append(" due to lock timeout").toString()));
        this.system().deadLetters().tell(new DeadLetter(msg.message(), msg.sender(), this.self()), msg.sender());
    }

    @Override
    public void sendSystemMessage(SystemMessage msg) {
        this.lock.lock();
        try {
            Cell cell = this.self().underlying();
            if (this.cellIsReady(cell)) {
                cell.sendSystemMessage(msg);
            } else {
                this.sysmsgQueue = LatestFirstSystemMessageList$.MODULE$.$colon$colon$extension(this.sysmsgQueue, msg);
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    private final boolean cellIsReady(Cell cell) {
        return cell != this && cell != null;
    }

    @Override
    public boolean hasMessages() {
        return BoxesRunTime.unboxToBoolean(this.locked(() -> {
            Cell cell = this.self().underlying();
            if (this.cellIsReady(cell)) {
                return cell.hasMessages();
            }
            return !$this.queue.isEmpty();
        }));
    }

    @Override
    public int numberOfMessages() {
        return BoxesRunTime.unboxToInt(this.locked(() -> {
            Cell cell = this.self().underlying();
            if (this.cellIsReady(cell)) {
                return cell.numberOfMessages();
            }
            return $this.queue.size();
        }));
    }

    private final <T> T locked(Function0<T> body2) {
        T t2;
        this.lock.lock();
        try {
            t2 = body2.apply();
        }
        finally {
            this.lock.unlock();
        }
        return t2;
    }

    private final void drainSysmsgQueue$1(Cell cell$1) {
        while (LatestFirstSystemMessageList$.MODULE$.nonEmpty$extension(this.sysmsgQueue)) {
            SystemMessage sysQ = LatestFirstSystemMessageList$.MODULE$.reverse$extension(this.sysmsgQueue);
            this.sysmsgQueue = SystemMessageList$.MODULE$.LNil();
            while (EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(sysQ)) {
                SystemMessage msg = sysQ;
                sysQ = EarliestFirstSystemMessageList$.MODULE$.tail$extension(sysQ);
                msg.unlink();
                cell$1.sendSystemMessage(msg);
            }
        }
    }

    public UnstartedCell(ActorSystemImpl systemImpl, RepointableActorRef self, Props props, InternalActorRef supervisor) {
        this.systemImpl = systemImpl;
        this.self = self;
        this.props = props;
        this.supervisor = supervisor;
        Cell.$init$(this);
        this.lock = new ReentrantLock();
        this.queue = new LinkedList();
        this.sysmsgQueue = SystemMessageList$.MODULE$.LNil();
    }
}

