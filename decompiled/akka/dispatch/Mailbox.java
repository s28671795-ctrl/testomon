/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.dispatch.AbstractMailbox;
import akka.dispatch.Envelope;
import akka.dispatch.Mailbox$;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.MessageQueue;
import akka.dispatch.SystemMessageQueue;
import akka.dispatch.sysmsg.EarliestFirstSystemMessageList$;
import akka.dispatch.sysmsg.NoMessage$;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.SystemMessageList$;
import akka.event.Logging;
import akka.util.Unsafe;
import java.util.concurrent.ForkJoinTask;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t-uAB!C\u0011\u0003!eI\u0002\u0004I\u0005\"\u0005A)\u0013\u0005\u00061\u0006!\tAW\u0003\u00057\u0006\u0001A\fC\u0004`\u0003\t\u0007IQ\u00011\t\r\r\f\u0001\u0015!\u0004b\u0011\u001d!\u0017A1A\u0005\u0006\u0015Da\u0001[\u0001!\u0002\u001b1\u0007bB5\u0002\u0005\u0004%)A\u001b\u0005\u0007[\u0006\u0001\u000bQB6\t\u000f9\f!\u0019!C\u0003_\"1!/\u0001Q\u0001\u000eADqa]\u0001C\u0002\u0013\u0015A\u000f\u0003\u0004v\u0003\u0001\u0006i\u0001\u0018\u0005\bm\u0006\u0011\r\u0011\"\u0002u\u0011\u00199\u0018\u0001)A\u00079\"9\u00010\u0001b\u0001\n\u000bI\bB\u0002?\u0002A\u00035!\u0010C\u0004~\u0003\t\u0007IQ\u0001@\t\u000f\u0005\r\u0011\u0001)A\u0007\u007f\"I\u0011QA\u0001\u0002\u0002\u0013%\u0011q\u0001\u0004\b\u0011\n\u000b\t\u0001RA\u000b\u0011)\tI$\u0006BC\u0002\u0013\u0005\u00111\b\u0005\u000b\u0003\u0007*\"\u0011!Q\u0001\n\u0005u\u0002B\u0002-\u0016\t\u0003\t)\u0005C\u0006\u0002LU\u0001\r\u00111A\u0005\u0002\u00055\u0003bCA-+\u0001\u0007\t\u0019!C\u0001\u00037B1\"!\u0019\u0016\u0001\u0004\u0005\t\u0015)\u0003\u0002P!9\u00111N\u000b\u0005\u0002\u00055\u0004bBA:+\u0011\u0005\u0011Q\u000f\u0005\b\u0003{*B\u0011AA@\u0011\u001d\t)*\u0006C\u0001\u0003/Cq!!'\u0016\t\u0003\tY\n\u0003\u0004\u0002$V!\t\u0001\u001e\u0005\f\u0003K+\u0002\u0019!a\u0001\n#\t9\u000bC\u0006\u0002.V\u0001\r\u00111A\u0005\u0012\u0005=\u0006bCAZ+\u0001\u0007\t\u0011)Q\u0005\u0003SC1\"a.\u0016\u0001\u0004\u0005\r\u0011\"\u0005\u0002:\"Y\u0011qY\u000bA\u0002\u0003\u0007I\u0011CAe\u0011-\ti-\u0006a\u0001\u0002\u0003\u0006K!a/\t\u000f\u0005EW\u0003\"\u0002\u0002(\"9\u00111\\\u000b\u0005\u0006\u0005m\u0005BBAp+\u0011\u0015A\u000fC\u0004\u0002dV!)!a'\t\u000f\u0005\u001dX\u0003\"\u0002\u0002\u001c\"9\u00111^\u000b\u0005\u0006\u0005m\u0005bBAx+\u0011U\u0011\u0011\u001f\u0005\b\u0003{,BQCA\u0000\u0011\u001d\u0011)!\u0006C\u0003\u0005\u000fAqAa\u0006\u0016\t\u000b\u00119\u0001C\u0004\u0003\u001cU!)Aa\u0002\t\u000f\t}Q\u0003\"\u0002\u0003\b!9!1E\u000b\u0005\u0006\t\u001d\u0001b\u0002B\u0014+\u0011U!\u0011\u0006\u0005\b\u0005c)BQ\u0003B\u001a\u0011\u001d\u0011i$\u0006C\u0003\u0005\u007fAqA!\u0013\u0016\t\u000b\u0012Y\u0005C\u0004\u0003NU!)Ea\u0013\t\u000f\t=S\u0003\"\u0012\u0003R!9!qK\u000b\u0005F\t\u001d\u0001b\u0002B-+\u00115!1\f\u0005\n\u0005[*\u0012\u0013!C\u0007\u0005_B\u0011B!!\u0016#\u0003%iAa!\t\u000f\t\u001dU\u0003\"\u0002\u0003L!A!\u0011R\u000b\u0005\u0012\t\u0013Y%A\u0004NC&d'm\u001c=\u000b\u0005\r#\u0015\u0001\u00033jgB\fGo\u00195\u000b\u0003\u0015\u000bA!Y6lCB\u0011q)A\u0007\u0002\u0005\n9Q*Y5mE>D8cA\u0001K!B\u00111JT\u0007\u0002\u0019*\tQ*A\u0003tG\u0006d\u0017-\u0003\u0002P\u0019\n1\u0011I\\=SK\u001a\u0004\"!\u0015,\u000e\u0003IS!a\u0015+\u0002\u0005%|'\"A+\u0002\t)\fg/Y\u0005\u0003/J\u0013AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002\r\n11\u000b^1ukN\u0004\"aS/\n\u0005yc%aA%oi\u0006!q\n]3o+\u0005\tw\"\u00012\u001e\u0003\u0001\tQa\u00149f]\u0002\naa\u00117pg\u0016$W#\u00014\u0010\u0003\u001dl\u0012!A\u0001\b\u00072|7/\u001a3!\u0003%\u00196\r[3ek2,G-F\u0001l\u001f\u0005aW$\u0001\u0002\u0002\u0015M\u001b\u0007.\u001a3vY\u0016$\u0007%\u0001\ntQ>,H\u000eZ*dQ\u0016$W\u000f\\3NCN\\W#\u00019\u0010\u0003El\u0012aA\u0001\u0014g\"|W\u000f\u001c3TG\",G-\u001e7f\u001b\u0006\u001c8\u000eI\u0001\u0015g\"|W\u000f\u001c3O_R\u0004&o\\2fgNl\u0015m]6\u0016\u0003q\u000bQc\u001d5pk2$gj\u001c;Qe>\u001cWm]:NCN\\\u0007%A\u0006tkN\u0004XM\u001c3NCN\\\u0017\u0001D:vgB,g\u000eZ'bg.\u0004\u0013aC:vgB,g\u000eZ+oSR,\u0012A_\b\u0002wv\tA!\u0001\u0007tkN\u0004XM\u001c3V]&$\b%A\u0003eK\n,x-F\u0001\u0000\u001f\t\t\t!G\u0001\u0001\u0003\u0019!WMY;hA\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u0002\t\u0005\u0003\u0017\t\t\"\u0004\u0002\u0002\u000e)\u0019\u0011q\u0002+\u0002\t1\fgnZ\u0005\u0005\u0003'\tiA\u0001\u0004PE*,7\r^\n\b+\u0005]\u0011QFA\u001a!\u0019\tI\"a\t\u0002(5\u0011\u00111\u0004\u0006\u0005\u0003;\ty\"\u0001\u0006d_:\u001cWO\u001d:f]RT1!!\tU\u0003\u0011)H/\u001b7\n\t\u0005\u0015\u00121\u0004\u0002\r\r>\u00148NS8j]R\u000b7o\u001b\t\u0004\u0017\u0006%\u0012bAA\u0016\u0019\n!QK\\5u!\r9\u0015qF\u0005\u0004\u0003c\u0011%AE*zgR,W.T3tg\u0006<W-U;fk\u0016\u0004B!a\u0003\u00026%!\u0011qGA\u0007\u0005!\u0011VO\u001c8bE2,\u0017\u0001D7fgN\fw-Z)vKV,WCAA\u001f!\r9\u0015qH\u0005\u0004\u0003\u0003\u0012%\u0001D'fgN\fw-Z)vKV,\u0017!D7fgN\fw-Z)vKV,\u0007\u0005\u0006\u0003\u0002H\u0005%\u0003CA$\u0016\u0011\u001d\tI\u0004\u0007a\u0001\u0003{\tQ!Y2u_J,\"!a\u0014\u0011\t\u0005E\u0013QK\u0007\u0003\u0003'R1!a\u0013E\u0013\u0011\t9&a\u0015\u0003\u0013\u0005\u001bGo\u001c:DK2d\u0017!C1di>\u0014x\fJ3r)\u0011\t9#!\u0018\t\u0013\u0005}#$!AA\u0002\u0005=\u0013a\u0001=%c\u00051\u0011m\u0019;pe\u0002B3aGA3!\rY\u0015qM\u0005\u0004\u0003Sb%\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002\u0011M,G/Q2u_J$B!a\n\u0002p!9\u0011\u0011\u000f\u000fA\u0002\u0005=\u0013\u0001B2fY2\f!\u0002Z5ta\u0006$8\r[3s+\t\t9\bE\u0002H\u0003sJ1!a\u001fC\u0005EiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\u0001\bK:\fX/Z;f)\u0019\t9#!!\u0002\f\"9\u00111\u0011\u0010A\u0002\u0005\u0015\u0015\u0001\u0003:fG\u0016Lg/\u001a:\u0011\t\u0005E\u0013qQ\u0005\u0005\u0003\u0013\u000b\u0019F\u0001\u0005BGR|'OU3g\u0011\u001d\tiI\ba\u0001\u0003\u001f\u000b1!\\:h!\r9\u0015\u0011S\u0005\u0004\u0003'\u0013%\u0001C#om\u0016dw\u000e]3\u0002\u000f\u0011,\u0017/^3vKR\u0011\u0011qR\u0001\fQ\u0006\u001cX*Z:tC\u001e,7/\u0006\u0002\u0002\u001eB\u00191*a(\n\u0007\u0005\u0005FJA\u0004C_>dW-\u00198\u0002!9,XNY3s\u001f\u001alUm]:bO\u0016\u001c\u0018AG0ti\u0006$Xo\u001d#p\u001d>$8)\u00197m\u001b\u0016$\u0015N]3di2LXCAAU!\r\tYk\u0001\b\u0003\u000f\u0002\tadX:uCR,8\u000fR8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYf|F%Z9\u0015\t\u0005\u001d\u0012\u0011\u0017\u0005\n\u0003?\u001a\u0013\u0011!a\u0001\u0003S\u000b1dX:uCR,8\u000fR8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYf\u0004\u0003f\u0001\u0013\u0002f\u0005yrl]=ti\u0016l\u0017+^3vK\u0012{gj\u001c;DC2dW*\u001a#je\u0016\u001cG\u000f\\=\u0016\u0005\u0005m\u0006\u0003BA_\u0003\u0007l!!a0\u000b\u0007\u0005\u0005')\u0001\u0004tsNl7oZ\u0005\u0005\u0003\u000b\fyLA\u0007TsN$X-\\'fgN\fw-Z\u0001$?NL8\u000f^3n#V,W/\u001a#p\u001d>$8)\u00197m\u001b\u0016$\u0015N]3di2Lx\fJ3r)\u0011\t9#a3\t\u0013\u0005}c%!AA\u0002\u0005m\u0016\u0001I0tsN$X-\\)vKV,Gi\u001c(pi\u000e\u000bG\u000e\\'f\t&\u0014Xm\u0019;ms\u0002B3aJA3\u00035\u0019WO\u001d:f]R\u001cF/\u0019;vg\"\u001a\u0001&!6\u0011\u0007-\u000b9.C\u0002\u0002Z2\u0013a!\u001b8mS:,\u0017\u0001F:i_VdG\r\u0015:pG\u0016\u001c8/T3tg\u0006<W\rK\u0002*\u0003+\fAb];ta\u0016tGmQ8v]RD3AKAk\u0003-I7oU;ta\u0016tG-\u001a3)\u0007-\n).\u0001\u0005jg\u000ecwn]3eQ\ra\u0013Q[\u0001\fSN\u001c6\r[3ek2,G\rK\u0002.\u0003+\fA\"\u001e9eCR,7\u000b^1ukN$b!!(\u0002t\u0006]\bbBA{]\u0001\u0007\u0011\u0011V\u0001\n_2$7\u000b^1ukNDq!!?/\u0001\u0004\tI+A\u0005oK^\u001cF/\u0019;vg\"\u001aa&!6\u0002\u0013M,Go\u0015;biV\u001cH\u0003BA\u0014\u0005\u0003Aq!!?0\u0001\u0004\tI\u000bK\u00020\u0003+\faA]3tk6,GCAAOQ\r\u0001$1\u0002\t\u0005\u0005\u001b\u0011\u0019\"\u0004\u0002\u0003\u0010)\u0019!\u0011\u0003'\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003\u0016\t=!a\u0002;bS2\u0014XmY\u0001\bgV\u001c\b/\u001a8eQ\r\t$1B\u0001\rE\u0016\u001cw.\\3DY>\u001cX\r\u001a\u0015\u0004e\t-\u0011AD:fi\u0006\u001b8k\u00195fIVdW\r\u001a\u0015\u0004g\t-\u0011!C:fi\u0006\u001b\u0018\n\u001a7fQ\r!$1B\u0001\u000fgf\u001cH/Z7Rk\u0016,XmR3u+\t\u0011Y\u0003\u0005\u0003\u0002>\n5\u0012\u0002\u0002B\u0018\u0003\u007f\u0013A\u0004T1uKN$h)\u001b:tiNK8\u000f^3n\u001b\u0016\u001c8/Y4f\u0019&\u001cH/\u0001\btsN$X-\\)vKV,\u0007+\u001e;\u0015\r\u0005u%Q\u0007B\u001d\u0011\u001d\u00119D\u000ea\u0001\u0005W\tAaX8mI\"9!1\b\u001cA\u0002\t-\u0012\u0001B0oK^\f!dY1o\u0005\u0016\u001c6\r[3ek2,GMR8s\u000bb,7-\u001e;j_:$b!!(\u0003B\t\u0015\u0003b\u0002B\"o\u0001\u0007\u0011QT\u0001\u000fQ\u0006\u001cX*Z:tC\u001e,\u0007*\u001b8u\u0011\u001d\u00119e\u000ea\u0001\u0003;\u000bA\u0003[1t'f\u001cH/Z7NKN\u001c\u0018mZ3IS:$\u0018a\u0001:v]R\u0011\u0011qE\u0001\rO\u0016$(+Y<SKN,H\u000e^\u0001\rg\u0016$(+Y<SKN,H\u000e\u001e\u000b\u0005\u0003O\u0011\u0019\u0006C\u0004\u0003Vi\u0002\r!a\n\u0002\tUt\u0017\u000e^\u0001\u0005Kb,7-\u0001\bqe>\u001cWm]:NC&d'm\u001c=\u0015\r\u0005\u001d\"Q\fB1\u0011!\u0011y\u0006\u0010I\u0001\u0002\u0004a\u0016\u0001\u00027fMRD\u0011Ba\u0019=!\u0003\u0005\rA!\u001a\u0002\u0015\u0011,\u0017\r\u001a7j]\u0016t5\u000fE\u0002L\u0005OJ1A!\u001bM\u0005\u0011auN\\4)\u0007q\u0012Y!\u0001\rqe>\u001cWm]:NC&d'm\u001c=%I\u00164\u0017-\u001e7uIE*\"A!\u001d+\u0007q\u0013\u0019h\u000b\u0002\u0003vA!!q\u000fB?\u001b\t\u0011IH\u0003\u0003\u0003|\t=\u0011!C;oG\",7m[3e\u0013\u0011\u0011yH!\u001f\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\rqe>\u001cWm]:NC&d'm\u001c=%I\u00164\u0017-\u001e7uII*\"A!\"+\t\t\u0015$1O\u0001\u0019aJ|7-Z:t\u00032d7+_:uK6lUm]:bO\u0016\u001c\u0018aB2mK\u0006tW\u000b\u001d")
public abstract class Mailbox
extends ForkJoinTask<BoxedUnit>
implements SystemMessageQueue,
Runnable {
    private final MessageQueue messageQueue;
    private volatile ActorCell actor;
    private volatile int _statusDoNotCallMeDirectly;
    private volatile SystemMessage _systemQueueDoNotCallMeDirectly;

    public static boolean debug() {
        return Mailbox$.MODULE$.debug();
    }

    public static int suspendUnit() {
        return Mailbox$.MODULE$.suspendUnit();
    }

    public static int suspendMask() {
        return Mailbox$.MODULE$.suspendMask();
    }

    public static int shouldNotProcessMask() {
        return Mailbox$.MODULE$.shouldNotProcessMask();
    }

    public static int shouldScheduleMask() {
        return Mailbox$.MODULE$.shouldScheduleMask();
    }

    public static int Scheduled() {
        return Mailbox$.MODULE$.Scheduled();
    }

    public static int Closed() {
        return Mailbox$.MODULE$.Closed();
    }

    public static int Open() {
        return Mailbox$.MODULE$.Open();
    }

    public MessageQueue messageQueue() {
        return this.messageQueue;
    }

    public ActorCell actor() {
        return this.actor;
    }

    public void actor_$eq(ActorCell x$1) {
        this.actor = x$1;
    }

    public void setActor(ActorCell cell) {
        this.actor_$eq(cell);
    }

    public MessageDispatcher dispatcher() {
        return this.actor().dispatcher();
    }

    public void enqueue(ActorRef receiver, Envelope msg) {
        this.messageQueue().enqueue(receiver, msg);
    }

    public Envelope dequeue() {
        return this.messageQueue().dequeue();
    }

    public boolean hasMessages() {
        return this.messageQueue().hasMessages();
    }

    public int numberOfMessages() {
        return this.messageQueue().numberOfMessages();
    }

    public int _statusDoNotCallMeDirectly() {
        return this._statusDoNotCallMeDirectly;
    }

    public void _statusDoNotCallMeDirectly_$eq(int x$1) {
        this._statusDoNotCallMeDirectly = x$1;
    }

    public SystemMessage _systemQueueDoNotCallMeDirectly() {
        return this._systemQueueDoNotCallMeDirectly;
    }

    public void _systemQueueDoNotCallMeDirectly_$eq(SystemMessage x$1) {
        this._systemQueueDoNotCallMeDirectly = x$1;
    }

    public final int currentStatus() {
        return Unsafe.instance.getIntVolatile(this, AbstractMailbox.mailboxStatusOffset);
    }

    public final boolean shouldProcessMessage() {
        return (this.currentStatus() & Mailbox$.MODULE$.shouldNotProcessMask()) == 0;
    }

    public final int suspendCount() {
        return this.currentStatus() / 4;
    }

    public final boolean isSuspended() {
        return (this.currentStatus() & Mailbox$.MODULE$.suspendMask()) != 0;
    }

    public final boolean isClosed() {
        return this.currentStatus() == 1;
    }

    public final boolean isScheduled() {
        return (this.currentStatus() & 2) != 0;
    }

    public final boolean updateStatus(int oldStatus, int newStatus) {
        return Unsafe.instance.compareAndSwapInt(this, AbstractMailbox.mailboxStatusOffset, oldStatus, newStatus);
    }

    public final void setStatus(int newStatus) {
        Unsafe.instance.putIntVolatile(this, AbstractMailbox.mailboxStatusOffset, newStatus);
    }

    public final boolean resume() {
        int next;
        int n;
        do {
            n = this.currentStatus();
            switch (n) {
                case 1: {
                    this.setStatus(1);
                    return false;
                }
            }
        } while (!this.updateStatus(n, next = n < 4 ? n : n - 4));
        return next < 4;
    }

    public final boolean suspend() {
        int n;
        do {
            n = this.currentStatus();
            switch (n) {
                case 1: {
                    this.setStatus(1);
                    return false;
                }
            }
        } while (!this.updateStatus(n, n + 4));
        return n < 4;
    }

    public final boolean becomeClosed() {
        int n;
        do {
            n = this.currentStatus();
            switch (n) {
                case 1: {
                    this.setStatus(1);
                    return false;
                }
            }
        } while (!this.updateStatus(n, 1));
        return true;
    }

    public final boolean setAsScheduled() {
        int s2;
        do {
            if (((s2 = this.currentStatus()) & 3) == 0) continue;
            return false;
        } while (!this.updateStatus(s2, s2 | 2));
        return true;
    }

    public final boolean setAsIdle() {
        int s2;
        while (!this.updateStatus(s2 = this.currentStatus(), s2 & ~2)) {
        }
        return true;
    }

    public final SystemMessage systemQueueGet() {
        return (SystemMessage)Unsafe.instance.getObjectVolatile(this, AbstractMailbox.systemMessageOffset);
    }

    public final boolean systemQueuePut(SystemMessage _old, SystemMessage _new) {
        return _old == _new || Unsafe.instance.compareAndSwapObject(this, AbstractMailbox.systemMessageOffset, _old, _new);
    }

    public final boolean canBeScheduledForExecution(boolean hasMessageHint, boolean hasSystemMessageHint) {
        int n = this.currentStatus();
        switch (n) {
            case 0: 
            case 2: {
                return hasMessageHint || hasSystemMessageHint || this.hasSystemMessages() || this.hasMessages();
            }
            case 1: {
                return false;
            }
        }
        return hasSystemMessageHint || this.hasSystemMessages();
    }

    @Override
    public final void run() {
        try {
            if (!this.isClosed()) {
                this.processAllSystemMessages();
                this.processMailbox(this.processMailbox$default$1(), this.processMailbox$default$2());
            }
        }
        finally {
            this.setAsIdle();
            this.dispatcher().registerForExecution(this, false, false);
        }
    }

    @Override
    public final void getRawResult() {
    }

    @Override
    public final void setRawResult(BoxedUnit unit) {
    }

    @Override
    public final boolean exec() {
        boolean bl;
        try {
            this.run();
            bl = false;
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            bl = false;
        }
        catch (Throwable anything) {
            Thread t2 = Thread.currentThread();
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = t2.getUncaughtExceptionHandler();
            if (uncaughtExceptionHandler == null) {
            } else {
                uncaughtExceptionHandler.uncaughtException(t2, anything);
            }
            throw anything;
        }
        return bl;
    }

    private final void processMailbox(int left, long deadlineNs) {
        block5: {
            BoxedUnit boxedUnit;
            while (this.shouldProcessMessage()) {
                Envelope next = this.dequeue();
                if (next != null) {
                    this.actor().invoke(next);
                    if (Thread.interrupted()) {
                        throw new InterruptedException("Interrupted while processing actor messages");
                    }
                    this.processAllSystemMessages();
                    if (!(left <= 1 || this.dispatcher().isThroughputDeadlineTimeDefined() && System.nanoTime() - deadlineNs >= 0L)) {
                        --left;
                        continue;
                    }
                    boxedUnit = BoxedUnit.UNIT;
                } else {
                    boxedUnit = BoxedUnit.UNIT;
                }
                break block5;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    private final int processMailbox$default$1() {
        return Math.max(this.dispatcher().throughput(), 1);
    }

    private final long processMailbox$default$2() {
        if (this.dispatcher().isThroughputDeadlineTimeDefined()) {
            return System.nanoTime() + this.dispatcher().throughputDeadlineTime().toNanos();
        }
        return 0L;
    }

    public final void processAllSystemMessages() {
        Mailbox dlm;
        InterruptedException interruption = null;
        SystemMessage messageList = this.systemDrain(SystemMessageList$.MODULE$.LNil());
        while (EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(messageList) && !this.isClosed()) {
            SystemMessage msg = messageList;
            messageList = EarliestFirstSystemMessageList$.MODULE$.tail$extension(messageList);
            msg.unlink();
            this.actor().systemInvoke(msg);
            if (Thread.interrupted()) {
                interruption = new InterruptedException("Interrupted while processing system messages");
            }
            if (!EarliestFirstSystemMessageList$.MODULE$.isEmpty$extension(messageList) || this.isClosed()) continue;
            messageList = this.systemDrain(SystemMessageList$.MODULE$.LNil());
        }
        Mailbox mailbox = dlm = EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(messageList) ? this.actor().dispatcher().mailboxes().deadLetterMailbox() : null;
        while (EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(messageList)) {
            SystemMessage msg = messageList;
            messageList = EarliestFirstSystemMessageList$.MODULE$.tail$extension(messageList);
            msg.unlink();
            try {
                dlm.systemEnqueue(this.actor().self(), msg);
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 instanceof InterruptedException) {
                    InterruptedException interruptedException;
                    interruption = interruptedException = (InterruptedException)throwable2;
                    continue;
                }
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable e = option.get();
                    this.actor().system().eventStream().publish(new Logging.Error(e, this.actor().self().path().toString(), this.getClass(), new StringBuilder(39).append("error while enqueuing ").append(msg).append(" to deadLetters: ").append(e.getMessage()).toString()));
                    continue;
                }
                throw throwable;
            }
        }
        if (interruption != null) {
            Thread.interrupted();
            throw interruption;
        }
    }

    public void cleanUp() {
        if (this.actor() != null) {
            Mailbox dlm = this.actor().dispatcher().mailboxes().deadLetterMailbox();
            SystemMessage messageList = this.systemDrain(NoMessage$.MODULE$);
            while (EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(messageList)) {
                SystemMessage msg = messageList;
                messageList = EarliestFirstSystemMessageList$.MODULE$.tail$extension(messageList);
                msg.unlink();
                dlm.systemEnqueue(this.actor().self(), msg);
            }
            if (this.messageQueue() != null) {
                this.messageQueue().cleanUp(this.actor().self(), this.actor().dispatcher().mailboxes().deadLetterMailbox().messageQueue());
                return;
            }
            return;
        }
    }

    public Mailbox(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }
}

