/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.ActorRefWithCell;
import akka.actor.Cell;
import akka.routing.ActorRefRoutee;
import akka.routing.NoRoutee$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.routing.SmallestMailboxRoutingLogic$;
import java.util.concurrent.ThreadLocalRandom;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dq!B\t\u0013\u0011\u00039b!B\r\u0013\u0011\u0003Q\u0002\"B\u0011\u0002\t\u0003\u0011\u0003\"B\u0012\u0002\t\u0003!c\u0001B\r\u0013\u0001\u0019BQ!\t\u0003\u0005\u0002\u0011BQA\u000b\u0003\u0005B-BQA\u0010\u0003\u0005\n}BqA\u0017\u0003\u0012\u0002\u0013%1\fC\u0004e\tE\u0005I\u0011B3\t\u000f\u001d$\u0011\u0013!C\u0005Q\"9!\u000eBI\u0001\n\u0013Y\u0007\"B7\u0005\t#q\u0007\"B9\u0005\t#\u0011\b\"\u0002;\u0005\t#)\b\"B<\u0005\t#A\b\"\u0002>\u0005\t#Y\u0018aG*nC2dWm\u001d;NC&d'm\u001c=S_V$\u0018N\\4M_\u001eL7M\u0003\u0002\u0014)\u00059!o\\;uS:<'\"A\u000b\u0002\t\u0005\\7.Y\u0002\u0001!\tA\u0012!D\u0001\u0013\u0005m\u0019V.\u00197mKN$X*Y5mE>D(k\\;uS:<Gj\\4jGN\u0011\u0011a\u0007\t\u00039}i\u0011!\b\u0006\u0002=\u0005)1oY1mC&\u0011\u0001%\b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u00059\u0012!B1qa2LH#A\u0013\u0011\u0005a!1c\u0001\u0003\u001cOA\u0011\u0001\u0004K\u0005\u0003SI\u0011ABU8vi&tw\rT8hS\u000e\faa]3mK\u000e$Hc\u0001\u00170iA\u0011\u0001$L\u0005\u0003]I\u0011aAU8vi\u0016,\u0007\"\u0002\u0019\u0007\u0001\u0004\t\u0014aB7fgN\fw-\u001a\t\u00039IJ!aM\u000f\u0003\u0007\u0005s\u0017\u0010C\u00036\r\u0001\u0007a'A\u0004s_V$X-Z:\u0011\u0007]bD&D\u00019\u0015\tI$(A\u0005j[6,H/\u00192mK*\u00111(H\u0001\u000bG>dG.Z2uS>t\u0017BA\u001f9\u0005)Ie\u000eZ3yK\u0012\u001cV-]\u0001\u000bg\u0016dWm\u0019;OKb$HC\u0002\u0017A\u0005\u0012Ke\nC\u0003B\u000f\u0001\u0007a'A\u0004uCJ<W\r^:\t\u000f\r;\u0001\u0013!a\u0001Y\u0005q\u0001O]8q_N,G\rV1sO\u0016$\bbB#\b!\u0003\u0005\rAR\u0001\rGV\u0014(/\u001a8u'\u000e|'/\u001a\t\u00039\u001dK!\u0001S\u000f\u0003\t1{gn\u001a\u0005\b\u0015\u001e\u0001\n\u00111\u0001L\u0003\t\tG\u000f\u0005\u0002\u001d\u0019&\u0011Q*\b\u0002\u0004\u0013:$\bbB(\b!\u0003\u0005\r\u0001U\u0001\u0005I\u0016,\u0007\u000f\u0005\u0002\u001d#&\u0011!+\b\u0002\b\u0005>|G.Z1oQ\t9A\u000b\u0005\u0002V16\taK\u0003\u0002X;\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005e3&a\u0002;bS2\u0014XmY\u0001\u0015g\u0016dWm\u0019;OKb$H\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003qS#\u0001L/,\u0003y\u0003\"a\u00182\u000e\u0003\u0001T!!\u0019,\u0002\u0013Ut7\r[3dW\u0016$\u0017BA2a\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u0015g\u0016dWm\u0019;OKb$H\u0005Z3gCVdG\u000fJ\u001a\u0016\u0003\u0019T#AR/\u0002)M,G.Z2u\u001d\u0016DH\u000f\n3fM\u0006,H\u000e\u001e\u00135+\u0005I'FA&^\u0003Q\u0019X\r\\3di:+\u0007\u0010\u001e\u0013eK\u001a\fW\u000f\u001c;%kU\tAN\u000b\u0002Q;\u0006a\u0011n\u001d+fe6Lg.\u0019;fIR\u0011\u0001k\u001c\u0005\u0006a2\u0001\r\u0001L\u0001\u0002C\u0006\u0019\u0012n\u001d)s_\u000e,7o]5oO6+7o]1hKR\u0011\u0001k\u001d\u0005\u0006a6\u0001\r\u0001L\u0001\fQ\u0006\u001cX*Z:tC\u001e,7\u000f\u0006\u0002Qm\")\u0001O\u0004a\u0001Y\u0005Y\u0011n]*vgB,g\u000eZ3e)\t\u0001\u0016\u0010C\u0003q\u001f\u0001\u0007A&\u0001\tok6\u0014WM](g\u001b\u0016\u001c8/Y4fgR\u00111\n \u0005\u0006aB\u0001\r\u0001\f\u0015\u0007\ty\f\u0019!!\u0002\u0011\u0005qy\u0018bAA\u0001;\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0001")
public class SmallestMailboxRoutingLogic
implements RoutingLogic {
    private static final long serialVersionUID = 1L;

    public static SmallestMailboxRoutingLogic apply() {
        return SmallestMailboxRoutingLogic$.MODULE$.apply();
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.isEmpty()) {
            return NoRoutee$.MODULE$;
        }
        return this.selectNext(routees, this.selectNext$default$2(), this.selectNext$default$3(), this.selectNext$default$4(), this.selectNext$default$5());
    }

    private Routee selectNext(IndexedSeq<Routee> targets, Routee proposedTarget, long currentScore, int at, boolean deep) {
        while (!targets.isEmpty()) {
            long newScore;
            long l;
            if (at >= targets.size()) {
                if (deep) {
                    if (this.isTerminated(proposedTarget)) {
                        return (Routee)targets.apply(ThreadLocalRandom.current().nextInt(targets.size()));
                    }
                    return proposedTarget;
                }
                deep = true;
                at = 0;
                continue;
            }
            Routee target = (Routee)targets.apply(at);
            if (this.isSuspended(target)) {
                l = 0x7FFFFFFFFFFFFFFEL;
            } else {
                long l2;
                long l3 = this.isProcessingMessage(target) ? 1L : 0L;
                if (!this.hasMessages(target)) {
                    l2 = 0L;
                } else {
                    long noOfMsgs = deep ? (long)this.numberOfMessages(target) : 0L;
                    l2 = noOfMsgs > 0L ? noOfMsgs : 0x7FFFFFFFFFFFFFFCL;
                }
                l = newScore = l3 + l2;
            }
            if (newScore == 0L) {
                return target;
            }
            if (newScore < 0L || newScore >= currentScore) {
                ++at;
                continue;
            }
            ++at;
            currentScore = newScore;
            proposedTarget = target;
        }
        return NoRoutee$.MODULE$;
    }

    private Routee selectNext$default$2() {
        return NoRoutee$.MODULE$;
    }

    private long selectNext$default$3() {
        return Long.MAX_VALUE;
    }

    private int selectNext$default$4() {
        return 0;
    }

    private boolean selectNext$default$5() {
        return false;
    }

    public boolean isTerminated(Routee a) {
        Routee routee = a;
        if (routee instanceof ActorRefRoutee) {
            ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee;
            ActorRef ref = actorRefRoutee.ref();
            return ref.isTerminated();
        }
        return false;
    }

    public boolean isProcessingMessage(Routee a) {
        ActorRefRoutee actorRefRoutee;
        ActorRef x;
        Routee routee = a;
        if (routee instanceof ActorRefRoutee && (x = (actorRefRoutee = (ActorRefRoutee)routee).ref()) instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)x;
            Cell cell = actorRefWithCell.underlying();
            if (cell instanceof ActorCell) {
                ActorCell actorCell = (ActorCell)cell;
                return actorCell.mailbox().isScheduled() && actorCell.currentMessage() != null;
            }
            return false;
        }
        return false;
    }

    public boolean hasMessages(Routee a) {
        ActorRefRoutee actorRefRoutee;
        ActorRef x;
        Routee routee = a;
        if (routee instanceof ActorRefRoutee && (x = (actorRefRoutee = (ActorRefRoutee)routee).ref()) instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)x;
            return actorRefWithCell.underlying().hasMessages();
        }
        return false;
    }

    public boolean isSuspended(Routee a) {
        ActorRefRoutee actorRefRoutee;
        ActorRef x;
        Routee routee = a;
        if (routee instanceof ActorRefRoutee && (x = (actorRefRoutee = (ActorRefRoutee)routee).ref()) instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)x;
            Cell cell = actorRefWithCell.underlying();
            if (cell instanceof ActorCell) {
                ActorCell actorCell = (ActorCell)cell;
                return actorCell.mailbox().isSuspended();
            }
            return true;
        }
        return false;
    }

    public int numberOfMessages(Routee a) {
        ActorRefRoutee actorRefRoutee;
        ActorRef x;
        Routee routee = a;
        if (routee instanceof ActorRefRoutee && (x = (actorRefRoutee = (ActorRefRoutee)routee).ref()) instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)x;
            return actorRefWithCell.underlying().numberOfMessages();
        }
        return 0;
    }
}

