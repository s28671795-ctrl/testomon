/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActorRef;
import akka.actor.Actor$;
import akka.actor.ActorCell;
import akka.actor.ActorCell$;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSystemImpl;
import akka.actor.Cell;
import akka.actor.ChildRestartStats;
import akka.actor.ChildStats;
import akka.actor.InternalActorRef;
import akka.actor.Nobody$;
import akka.actor.Props;
import akka.actor.RepointableRef;
import akka.actor.SerializedActorRef$;
import akka.actor.UnstartedCell;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.sysmsg.Supervise;
import akka.dispatch.sysmsg.SystemMessage;
import akka.util.Unsafe;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.MatchError;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t]c!B\u0017/\u0001A\u0012\u0004\u0002\u0003\u001e\u0001\u0005\u000b\u0007I\u0011\u0001\u001f\t\u0011\u0001\u0003!\u0011!Q\u0001\nuB\u0001\"\u0011\u0001\u0003\u0006\u0004%\tA\u0011\u0005\t\r\u0002\u0011\t\u0011)A\u0005\u0007\"Aq\t\u0001BC\u0002\u0013\u0005\u0001\n\u0003\u0005P\u0001\t\u0005\t\u0015!\u0003J\u0011!\u0001\u0006A!b\u0001\n\u0003\t\u0006\u0002C+\u0001\u0005\u0003\u0005\u000b\u0011\u0002*\t\u0011Y\u0003!Q1A\u0005\u0002]C\u0001b\u0017\u0001\u0003\u0002\u0003\u0006I\u0001\u0017\u0005\t9\u0002\u0011)\u0019!C\u0001;\"A\u0011\r\u0001B\u0001B\u0003%a\fC\u0003c\u0001\u0011\u00051\rC\u0005l\u0001\u0001\u0007\t\u0019!C\u0005Y\"I\u0001\u000f\u0001a\u0001\u0002\u0004%I!\u001d\u0005\nu\u0002\u0001\r\u0011!Q!\n5D\u0011b \u0001A\u0002\u0003\u0007I\u0011\u00027\t\u0017\u0005\u0005\u0001\u00011AA\u0002\u0013%\u00111\u0001\u0005\u000b\u0003\u000f\u0001\u0001\u0019!A!B\u0013i\u0007BBA\u0006\u0001\u0011%A\u000e\u0003\u0004\u0002\u000e\u0001!\t\u0001\u001c\u0005\u0007\u0003\u001f\u0001A\u0011\u00017\t\u000f\u0005E\u0001\u0001\"\u0002\u0002\u0014!9\u0011q\u0005\u0001\u0005\u0006\u0005%\u0002bBA\u0018\u0001\u0011\u0005\u0011\u0011\u0007\u0005\b\u0003\u007f\u0001A\u0011AA!\u0011\u001d\t9\u0005\u0001C\u0001\u0003\u0013Bq!a\u0019\u0001\t\u0003\t)\u0007C\u0004\u0002h\u0001!\t!!\u001a\t\u000f\u0005%\u0004\u0001\"\u0001\u0002l!9\u0011\u0011\u0012\u0001\u0005\u0002\u0005\u0015\u0004bBAF\u0001\u0011\u0005\u0011Q\u0012\u0005\b\u0003'\u0003A\u0011AAK\u0011\u001d\t9\n\u0001C\u0001\u0003+Cq!!,\u0001\t\u0003\ty\u000bC\u0004\u00028\u0002!\t!!&\t\r\u0005e\u0006\u0001\"\u0001X\u0011\u001d\tY\f\u0001C\u0001\u0003{Cq!!7\u0001\t\u0003\tY\u000eC\u0004\u0002`\u0002!\t!!9\t\u000f\u0005e\b\u0001\"\u0001\u0002|\"I!1\u0002\u0001\u0012\u0002\u0013\u0005!Q\u0002\u0005\b\u0005C\u0001A\u0011\u0001B\u0012\u0011\u001d\u0011\u0019\u0004\u0001C\t\u0005k\u00111CU3q_&tG/\u00192mK\u0006\u001bGo\u001c:SK\u001aT!a\f\u0019\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003E\nA!Y6lCN\u0019\u0001aM\u001c\u0011\u0005Q*T\"\u0001\u0018\n\u0005Yr#\u0001E!di>\u0014(+\u001a4XSRD7)\u001a7m!\t!\u0004(\u0003\u0002:]\tq!+\u001a9pS:$\u0018M\u00197f%\u00164\u0017AB:zgR,Wn\u0001\u0001\u0016\u0003u\u0002\"\u0001\u000e \n\u0005}r#aD!di>\u00148+_:uK6LU\u000e\u001d7\u0002\u000fML8\u000f^3nA\u0005)\u0001O]8qgV\t1\t\u0005\u00025\t&\u0011QI\f\u0002\u0006!J|\u0007o]\u0001\u0007aJ|\u0007o\u001d\u0011\u0002\u0015\u0011L7\u000f]1uG\",'/F\u0001J!\tQU*D\u0001L\u0015\ta\u0005'\u0001\u0005eSN\u0004\u0018\r^2i\u0013\tq5JA\tNKN\u001c\u0018mZ3ESN\u0004\u0018\r^2iKJ\f1\u0002Z5ta\u0006$8\r[3sA\u0005YQ.Y5mE>DH+\u001f9f+\u0005\u0011\u0006C\u0001&T\u0013\t!6JA\u0006NC&d'm\u001c=UsB,\u0017\u0001D7bS2\u0014w\u000e\u001f+za\u0016\u0004\u0013AC:va\u0016\u0014h/[:peV\t\u0001\f\u0005\u000253&\u0011!L\f\u0002\u0011\u0013:$XM\u001d8bY\u0006\u001bGo\u001c:SK\u001a\f1b];qKJ4\u0018n]8sA\u0005!\u0001/\u0019;i+\u0005q\u0006C\u0001\u001b`\u0013\t\u0001gFA\u0005BGR|'\u000fU1uQ\u0006)\u0001/\u0019;iA\u00051A(\u001b8jiz\"r\u0001Z3gO\"L'\u000e\u0005\u00025\u0001!)!(\u0004a\u0001{!)\u0011)\u0004a\u0001\u0007\")q)\u0004a\u0001\u0013\")\u0001+\u0004a\u0001%\")a+\u0004a\u00011\")A,\u0004a\u0001=\u0006ArlY3mY\u0012{gj\u001c;DC2dW*\u001a#je\u0016\u001cG\u000f\\=\u0016\u00035\u0004\"\u0001\u000e8\n\u0005=t#\u0001B\"fY2\fAdX2fY2$uNT8u\u0007\u0006dG.T3ESJ,7\r\u001e7z?\u0012*\u0017\u000f\u0006\u0002sqB\u00111O^\u0007\u0002i*\tQ/A\u0003tG\u0006d\u0017-\u0003\u0002xi\n!QK\\5u\u0011\u001dIx\"!AA\u00025\f1\u0001\u001f\u00132\u0003ey6-\u001a7m\t>tu\u000e^\"bY2lU\rR5sK\u000e$H.\u001f\u0011)\u0005Aa\bCA:~\u0013\tqHO\u0001\u0005w_2\fG/\u001b7f\u0003iyFn\\8lkB$uNT8u\u0007\u0006dG.T3ESJ,7\r\u001e7z\u0003yyFn\\8lkB$uNT8u\u0007\u0006dG.T3ESJ,7\r\u001e7z?\u0012*\u0017\u000fF\u0002s\u0003\u000bAq!\u001f\n\u0002\u0002\u0003\u0007Q.A\u000e`Y>|7.\u001e9E_:{GoQ1mY6+G)\u001b:fGRd\u0017\u0010\t\u0015\u0003'q\fAd\u00189sKZ,g\u000e\u001e)sSZ\fG/Z+okN,G-\u0012:bgV\u0014X-\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fa\u0001\\8pWV\u0004\u0018\u0001C:xCB\u001cU\r\u001c7\u0015\u00075\f)\u0002\u0003\u0004\u0002\u0018]\u0001\r!\\\u0001\u0005]\u0016DH\u000fK\u0002\u0018\u00037\u0001B!!\b\u0002$5\u0011\u0011q\u0004\u0006\u0004\u0003C!\u0018AC1o]>$\u0018\r^5p]&!\u0011QEA\u0010\u0005\u001d!\u0018-\u001b7sK\u000e\f!b]<ba2{wn[;q)\ri\u00171\u0006\u0005\u0007\u0003/A\u0002\u0019A7)\u0007a\tY\"\u0001\u0006j]&$\u0018.\u00197ju\u0016$B!a\r\u000265\t\u0001\u0001C\u0004\u00028e\u0001\r!!\u000f\u0002\u000b\u0005\u001c\u0018P\\2\u0011\u0007M\fY$C\u0002\u0002>Q\u0014qAQ8pY\u0016\fg.A\u0003q_&tG\u000f\u0006\u0003\u00024\u0005\r\u0003bBA#5\u0001\u0007\u0011\u0011H\u0001\u000eG\u0006$8\r\u001b$bS2,(/Z:\u0002\u000f9,woQ3mYR\u0019Q.a\u0013\t\u000f\u000553\u00041\u0001\u0002P\u0005\u0019q\u000e\u001c3\u0011\u0007Q\n\t&C\u0002\u0002T9\u0012Q\"\u00168ti\u0006\u0014H/\u001a3DK2d\u0007\u0006BA&\u0003/\u0002B!!\u0017\u0002`5\u0011\u00111\f\u0006\u0004\u0003;\u0002\u0014\u0001B;uS2LA!!\u0019\u0002\\\t1QO\\;tK\u0012\fQa\u001d;beR$\u0012A]\u0001\bgV\u001c\b/\u001a8e\u0003\u0019\u0011Xm];nKR\u0019!/!\u001c\t\u000f\u0005=d\u00041\u0001\u0002r\u0005y1-Y;tK\u0012\u0014\u0015PR1jYV\u0014X\r\u0005\u0003\u0002t\u0005\re\u0002BA;\u0003\u007frA!a\u001e\u0002~5\u0011\u0011\u0011\u0010\u0006\u0004\u0003wZ\u0014A\u0002\u001fs_>$h(C\u0001v\u0013\r\t\t\t^\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t))a\"\u0003\u0013QC'o\\<bE2,'bAAAi\u0006!1\u000f^8q\u0003\u001d\u0011Xm\u001d;beR$2A]AH\u0011\u001d\t\t\n\ta\u0001\u0003c\nQaY1vg\u0016\f\u0011\"[:Ti\u0006\u0014H/\u001a3\u0016\u0005\u0005e\u0012\u0001D5t)\u0016\u0014X.\u001b8bi\u0016$\u0007f\u0003\u0012\u0002\u001c\u0006\u0005\u00161UAT\u0003S\u00032a]AO\u0013\r\ty\n\u001e\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0003\u0003K\u000ba'V:fA\r|g\u000e^3yi::\u0018\r^2iQ\u0005\u001cGo\u001c:*A\u0005tG\r\t:fG\u0016Lg/\u001a\u0011UKJl\u0017N\\1uK\u0012D\u0013m\u0019;pe&\nQa]5oG\u0016\f#!a+\u0002\u0007Ir#'\u0001\u0005qe>4\u0018\u000eZ3s+\t\t\t\fE\u00025\u0003gK1!!./\u0005A\t5\r^8s%\u00164\u0007K]8wS\u0012,'/A\u0004jg2{7-\u00197\u0002\u0013\u001d,G\u000fU1sK:$\u0018\u0001C4fi\u000eC\u0017\u000e\u001c3\u0015\u0007a\u000by\fC\u0004\u0002B\u001a\u0002\r!a1\u0002\t9\fW.\u001a\t\u0007\u0003g\n)-!3\n\t\u0005\u001d\u0017q\u0011\u0002\t\u0013R,'/\u0019;peB!\u00111ZAj\u001d\u0011\ti-a4\u0011\u0007\u0005]D/C\u0002\u0002RR\fa\u0001\u0015:fI\u00164\u0017\u0002BAk\u0003/\u0014aa\u0015;sS:<'bAAii\u0006qq-\u001a;TS:<G.Z\"iS2$Gc\u0001-\u0002^\"9\u0011\u0011Y\u0014A\u0002\u0005%\u0017\u0001C2iS2$'/\u001a8\u0016\u0005\u0005\r\bCBAs\u0003_\f\u00190\u0004\u0002\u0002h*!\u0011\u0011^Av\u0003%IW.\\;uC\ndWMC\u0002\u0002nR\f!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\t0a:\u0003\u0011%#XM]1cY\u0016\u00042\u0001NA{\u0013\r\t9P\f\u0002\t\u0003\u000e$xN\u001d*fM\u0006)AEY1oOR!\u0011Q B\u0002)\r\u0011\u0018q \u0005\n\u0005\u0003I\u0003\u0013!a\u0002\u0003g\faa]3oI\u0016\u0014\bbBAQS\u0001\u0007!Q\u0001\t\u0004g\n\u001d\u0011b\u0001B\u0005i\n\u0019\u0011I\\=\u0002\u001f\u0011\u0012\u0017M\\4%I\u00164\u0017-\u001e7uII\"BAa\u0004\u0003 )\"\u00111\u001fB\tW\t\u0011\u0019\u0002\u0005\u0003\u0003\u0016\tmQB\u0001B\f\u0015\u0011\u0011I\"a\b\u0002\u0013Ut7\r[3dW\u0016$\u0017\u0002\u0002B\u000f\u0005/\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0011\u001d\t\tK\u000ba\u0001\u0005\u000b\t\u0011c]3oINK8\u000f^3n\u001b\u0016\u001c8/Y4f)\r\u0011(Q\u0005\u0005\b\u0003C[\u0003\u0019\u0001B\u0014!\u0011\u0011ICa\f\u000e\u0005\t-\"b\u0001B\u0017\u0017\u000611/_:ng\u001eLAA!\r\u0003,\ti1+_:uK6lUm]:bO\u0016\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa\u000e\u0011\u0007M\u0014I$C\u0002\u0003<Q\u0014a!\u00118z%\u00164\u0007&\u0002\u0017\u0003@\tU\u0003#B:\u0003B\t\u0015\u0013b\u0001B\"i\n1A\u000f\u001b:poN\u0004BAa\u0012\u0003R5\u0011!\u0011\n\u0006\u0005\u0005\u0017\u0012i%\u0001\u0002j_*\u0011!qJ\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003T\t%#!F(cU\u0016\u001cGo\u0015;sK\u0006lW\t_2faRLwN\\\u0012\u0003\u0005\u000b\u0002")
public class RepointableActorRef
extends ActorRefWithCell
implements RepointableRef {
    private final ActorSystemImpl system;
    private final Props props;
    private final MessageDispatcher dispatcher;
    private final MailboxType mailboxType;
    private final InternalActorRef supervisor;
    private final ActorPath path;
    private volatile Cell _cellDoNotCallMeDirectly;
    private volatile Cell _lookupDoNotCallMeDirectly;

    public ActorSystemImpl system() {
        return this.system;
    }

    public Props props() {
        return this.props;
    }

    public MessageDispatcher dispatcher() {
        return this.dispatcher;
    }

    public MailboxType mailboxType() {
        return this.mailboxType;
    }

    public InternalActorRef supervisor() {
        return this.supervisor;
    }

    @Override
    public ActorPath path() {
        return this.path;
    }

    private Cell _cellDoNotCallMeDirectly() {
        return this._cellDoNotCallMeDirectly;
    }

    private void _cellDoNotCallMeDirectly_$eq(Cell x$1) {
        this._cellDoNotCallMeDirectly = x$1;
    }

    private Cell _lookupDoNotCallMeDirectly() {
        return this._lookupDoNotCallMeDirectly;
    }

    private void _lookupDoNotCallMeDirectly_$eq(Cell x$1) {
        this._lookupDoNotCallMeDirectly = x$1;
    }

    private Cell _preventPrivateUnusedErasure() {
        this._cellDoNotCallMeDirectly();
        return this._lookupDoNotCallMeDirectly();
    }

    @Override
    public Cell underlying() {
        return (Cell)Unsafe.instance.getObjectVolatile(this, AbstractActorRef.cellOffset);
    }

    public Cell lookup() {
        return (Cell)Unsafe.instance.getObjectVolatile(this, AbstractActorRef.lookupOffset);
    }

    public final Cell swapCell(Cell next) {
        while (true) {
            Cell old;
            if (!Unsafe.instance.compareAndSwapObject(this, AbstractActorRef.cellOffset, old = this.underlying(), next)) continue;
            return old;
        }
    }

    public final Cell swapLookup(Cell next) {
        while (true) {
            Cell old;
            if (!Unsafe.instance.compareAndSwapObject(this, AbstractActorRef.lookupOffset, old = this.lookup(), next)) continue;
            return old;
        }
    }

    public RepointableActorRef initialize(boolean async) {
        Cell cell = this.underlying();
        if (cell == null) {
            this.swapCell(new UnstartedCell(this.system(), this, this.props(), this.supervisor()));
            this.swapLookup(this.underlying());
            this.supervisor().sendSystemMessage(new Supervise(this, async));
            Serializable serializable = !async ? this.point(false) : BoxedUnit.UNIT;
            return this;
        }
        throw new IllegalStateException("initialize called more than once!");
    }

    public RepointableActorRef point(boolean catchFailures) {
        Cell cell = this.underlying();
        if (cell instanceof UnstartedCell) {
            Cell cell2;
            UnstartedCell unstartedCell = (UnstartedCell)cell;
            try {
                cell2 = this.newCell(unstartedCell);
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable ex = option.get();
                    if (catchFailures) {
                        MessageDispatcher safeDispatcher = this.system().dispatchers().defaultGlobalDispatcher();
                        cell2 = new ActorCell(this.system(), this, this.props(), safeDispatcher, this.supervisor()).initWithFailure(ex);
                    }
                }
                throw throwable;
            }
            Cell cell3 = cell2;
            this.swapLookup(cell3);
            cell3.start();
            unstartedCell.replaceWith(cell3);
            return this;
        }
        if (cell == null) {
            throw new IllegalStateException("underlying cell is null");
        }
        return this;
    }

    public Cell newCell(UnstartedCell old) {
        return new ActorCell(this.system(), this, this.props(), this.dispatcher(), this.supervisor()).init(false, this.mailboxType());
    }

    @Override
    public void start() {
    }

    @Override
    public void suspend() {
        this.underlying().suspend();
    }

    @Override
    public void resume(Throwable causedByFailure) {
        this.underlying().resume(causedByFailure);
    }

    @Override
    public void stop() {
        this.underlying().stop();
    }

    @Override
    public void restart(Throwable cause) {
        this.underlying().restart(cause);
    }

    @Override
    public boolean isStarted() {
        Cell cell = this.underlying();
        if (cell instanceof UnstartedCell) {
            return false;
        }
        if (cell == null) {
            throw new IllegalStateException("isStarted called before initialized");
        }
        return true;
    }

    @Override
    public boolean isTerminated() {
        return this.underlying().isTerminated();
    }

    @Override
    public ActorRefProvider provider() {
        return this.system().provider();
    }

    @Override
    public boolean isLocal() {
        return this.underlying().isLocal();
    }

    @Override
    public InternalActorRef getParent() {
        return this.underlying().parent();
    }

    @Override
    public InternalActorRef getChild(Iterator<String> name) {
        if (name.hasNext()) {
            Cell cell;
            Some some;
            ChildStats crs;
            String string = name.next();
            switch (string == null ? 0 : string.hashCode()) {
                case 0: {
                    if (!"".equals(string)) break;
                    return this.getChild(name);
                }
                case 1472: {
                    if (!"..".equals(string)) break;
                    return this.getParent().getChild(name);
                }
            }
            Tuple2<String, Object> tuple2 = ActorCell$.MODULE$.splitNameAndUid(string);
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            String childName = tuple2._1();
            int uid = tuple2._2$mcI$sp();
            Tuple2<String, Integer> tuple22 = new Tuple2<String, Integer>(childName, BoxesRunTime.boxToInteger(uid));
            String childName2 = tuple22._1();
            int uid2 = tuple22._2$mcI$sp();
            Option<ChildStats> option = this.lookup().getChildByName(childName2);
            if (option instanceof Some && (crs = (ChildStats)(some = (Some)option).value()) instanceof ChildRestartStats) {
                ChildRestartStats childRestartStats = (ChildRestartStats)crs;
                if (uid2 == 0 || uid2 == childRestartStats.uid()) {
                    return ((InternalActorRef)childRestartStats.child()).getChild(name);
                }
            }
            if ((cell = this.lookup()) instanceof ActorCell) {
                ActorCell actorCell = (ActorCell)cell;
                return actorCell.getFunctionRefOrNobody(childName2, uid2);
            }
            return Nobody$.MODULE$;
        }
        return this;
    }

    @Override
    public InternalActorRef getSingleChild(String name) {
        return this.lookup().getSingleChild(name);
    }

    @Override
    public Iterable<ActorRef> children() {
        return this.lookup().childrenRefs().children();
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        this.underlying().sendMessage(message, sender);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        this.underlying().sendSystemMessage(message);
    }

    public Object writeReplace() throws ObjectStreamException {
        return SerializedActorRef$.MODULE$.apply(this);
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    public RepointableActorRef(ActorSystemImpl system, Props props, MessageDispatcher dispatcher, MailboxType mailboxType, InternalActorRef supervisor, ActorPath path) {
        this.system = system;
        this.props = props;
        this.dispatcher = dispatcher;
        this.mailboxType = mailboxType;
        this.supervisor = supervisor;
        this.path = path;
    }
}

