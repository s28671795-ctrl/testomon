/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorSystem;
import akka.actor.DynamicAccess;
import akka.actor.Scheduler;
import akka.annotation.InternalApi;
import akka.dispatch.DefaultDispatcherPrerequisites$;
import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.Mailboxes;
import akka.event.EventStream;
import java.io.Serializable;
import java.util.concurrent.ThreadFactory;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0005b!\u0002\u0015*\u0005.j\u0003\u0002\u0003%\u0001\u0005+\u0007I\u0011A%\t\u0011Q\u0003!\u0011#Q\u0001\n)C\u0001\"\u0016\u0001\u0003\u0016\u0004%\tA\u0016\u0005\t;\u0002\u0011\t\u0012)A\u0005/\"Aa\f\u0001BK\u0002\u0013\u0005q\f\u0003\u0005g\u0001\tE\t\u0015!\u0003a\u0011!9\u0007A!f\u0001\n\u0003A\u0007\u0002\u00037\u0001\u0005#\u0005\u000b\u0011B5\t\u00115\u0004!Q3A\u0005\u00029D\u0001B\u001e\u0001\u0003\u0012\u0003\u0006Ia\u001c\u0005\to\u0002\u0011)\u001a!C\u0001q\"AA\u0010\u0001B\tB\u0003%\u0011\u0010\u0003\u0005~\u0001\tU\r\u0011\"\u0001\u007f\u0011%\ty\u0001\u0001B\tB\u0003%q\u0010C\u0004\u0002\u0012\u0001!\t!a\u0005\t\u0013\u0005\u0015\u0002!!A\u0005\u0002\u0005\u001d\u0002\"CA\u001c\u0001E\u0005I\u0011AA\u001d\u0011%\ty\u0005AI\u0001\n\u0003\t\t\u0006C\u0005\u0002V\u0001\t\n\u0011\"\u0001\u0002X!I\u00111\f\u0001\u0012\u0002\u0013\u0005\u0011Q\f\u0005\n\u0003C\u0002\u0011\u0013!C\u0001\u0003GB\u0011\"a\u001a\u0001#\u0003%\t!!\u001b\t\u0013\u00055\u0004!%A\u0005\u0002\u0005=\u0004\"CA:\u0001\u0005\u0005I\u0011IA;\u0011%\t\u0019\tAA\u0001\n\u0003\t)\tC\u0005\u0002\u000e\u0002\t\t\u0011\"\u0001\u0002\u0010\"I\u00111\u0014\u0001\u0002\u0002\u0013\u0005\u0013Q\u0014\u0005\n\u0003W\u0003\u0011\u0011!C\u0001\u0003[C\u0011\"a.\u0001\u0003\u0003%\t%!/\t\u0013\u0005u\u0006!!A\u0005B\u0005}\u0006\"CAa\u0001\u0005\u0005I\u0011IAb\u0011%\t)\rAA\u0001\n\u0003\n9m\u0002\u0006\u0002X&\n\t\u0011#\u0001,\u000334\u0011\u0002K\u0015\u0002\u0002#\u00051&a7\t\u000f\u0005E!\u0005\"\u0001\u0002t\"I\u0011\u0011\u0019\u0012\u0002\u0002\u0013\u0015\u00131\u0019\u0005\n\u0003k\u0014\u0013\u0011!CA\u0003oD\u0011Ba\u0002#\u0003\u0003%\tI!\u0003\t\u0013\t]!%!A\u0005\n\te!A\b#fM\u0006,H\u000e\u001e#jgB\fGo\u00195feB\u0013XM]3rk&\u001c\u0018\u000e^3t\u0015\tQ3&\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005a\u0013\u0001B1lW\u0006\u001cR\u0001\u0001\u00185qm\u0002\"a\f\u001a\u000e\u0003AR\u0011!M\u0001\u0006g\u000e\fG.Y\u0005\u0003gA\u0012a!\u00118z%\u00164\u0007CA\u001b7\u001b\u0005I\u0013BA\u001c*\u0005]!\u0015n\u001d9bi\u000eDWM\u001d)sKJ,\u0017/^5tSR,7\u000f\u0005\u00020s%\u0011!\b\r\u0002\b!J|G-^2u!\taTI\u0004\u0002>\u0007:\u0011aHQ\u0007\u0002\u007f)\u0011\u0001)Q\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\t\u0011'\u0003\u0002Ea\u00059\u0001/Y2lC\u001e,\u0017B\u0001$H\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t!\u0005'A\u0007uQJ,\u0017\r\u001a$bGR|'/_\u000b\u0002\u0015B\u00111JU\u0007\u0002\u0019*\u0011QJT\u0001\u000bG>t7-\u001e:sK:$(BA(Q\u0003\u0011)H/\u001b7\u000b\u0003E\u000bAA[1wC&\u00111\u000b\u0014\u0002\u000e)\"\u0014X-\u00193GC\u000e$xN]=\u0002\u001dQD'/Z1e\r\u0006\u001cGo\u001c:zA\u0005YQM^3oiN#(/Z1n+\u00059\u0006C\u0001-\\\u001b\u0005I&B\u0001.,\u0003\u0015)g/\u001a8u\u0013\ta\u0016LA\u0006Fm\u0016tGo\u0015;sK\u0006l\u0017\u0001D3wK:$8\u000b\u001e:fC6\u0004\u0013!C:dQ\u0016$W\u000f\\3s+\u0005\u0001\u0007CA1e\u001b\u0005\u0011'BA2,\u0003\u0015\t7\r^8s\u0013\t)'MA\u0005TG\",G-\u001e7fe\u0006Q1o\u00195fIVdWM\u001d\u0011\u0002\u001b\u0011Lh.Y7jG\u0006\u001b7-Z:t+\u0005I\u0007CA1k\u0013\tY'MA\u0007Es:\fW.[2BG\u000e,7o]\u0001\u000fIft\u0017-\\5d\u0003\u000e\u001cWm]:!\u0003!\u0019X\r\u001e;j]\u001e\u001cX#A8\u0011\u0005A\u001chBA1r\u0013\t\u0011(-A\u0006BGR|'oU=ti\u0016l\u0017B\u0001;v\u0005!\u0019V\r\u001e;j]\u001e\u001c(B\u0001:c\u0003%\u0019X\r\u001e;j]\u001e\u001c\b%A\u0005nC&d'm\u001c=fgV\t\u0011\u0010\u0005\u00026u&\u001110\u000b\u0002\n\u001b\u0006LGNY8yKN\f!\"\\1jY\n|\u00070Z:!\u0003]!WMZ1vYR,\u00050Z2vi&|gnQ8oi\u0016DH/F\u0001\u0000!\u0015y\u0013\u0011AA\u0003\u0013\r\t\u0019\u0001\r\u0002\u0007\u001fB$\u0018n\u001c8\u0011\t\u0005\u001d\u00111B\u0007\u0003\u0003\u0013Q!!\u0014\u0019\n\t\u00055\u0011\u0011\u0002\u0002\u0011\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR\f\u0001\u0004Z3gCVdG/\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;!\u0003\u0019a\u0014N\\5u}Q\u0001\u0012QCA\f\u00033\tY\"!\b\u0002 \u0005\u0005\u00121\u0005\t\u0003k\u0001AQ\u0001S\bA\u0002)CQ!V\bA\u0002]CQAX\bA\u0002\u0001DQaZ\bA\u0002%DQ!\\\bA\u0002=DQa^\bA\u0002eDQ!`\bA\u0002}\fAaY8qsR\u0001\u0012QCA\u0015\u0003W\ti#a\f\u00022\u0005M\u0012Q\u0007\u0005\b\u0011B\u0001\n\u00111\u0001K\u0011\u001d)\u0006\u0003%AA\u0002]CqA\u0018\t\u0011\u0002\u0003\u0007\u0001\rC\u0004h!A\u0005\t\u0019A5\t\u000f5\u0004\u0002\u0013!a\u0001_\"9q\u000f\u0005I\u0001\u0002\u0004I\bbB?\u0011!\u0003\u0005\ra`\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tYDK\u0002K\u0003{Y#!a\u0010\u0011\t\u0005\u0005\u00131J\u0007\u0003\u0003\u0007RA!!\u0012\u0002H\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u0013\u0002\u0014AC1o]>$\u0018\r^5p]&!\u0011QJA\"\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t\u0019FK\u0002X\u0003{\tabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002Z)\u001a\u0001-!\u0010\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011q\f\u0016\u0004S\u0006u\u0012AD2paf$C-\u001a4bk2$H%N\u000b\u0003\u0003KR3a\\A\u001f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY*\"!a\u001b+\u0007e\fi$\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001c\u0016\u0005\u0005E$fA@\u0002>\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!a\u001e\u0011\t\u0005e\u0014qP\u0007\u0003\u0003wR1!! Q\u0003\u0011a\u0017M\\4\n\t\u0005\u0005\u00151\u0010\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005\u001d\u0005cA\u0018\u0002\n&\u0019\u00111\u0012\u0019\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005E\u0015q\u0013\t\u0004_\u0005M\u0015bAAKa\t\u0019\u0011I\\=\t\u0013\u0005e%$!AA\u0002\u0005\u001d\u0015a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002 B1\u0011\u0011UAT\u0003#k!!a)\u000b\u0007\u0005\u0015\u0006'\u0001\u0006d_2dWm\u0019;j_:LA!!+\u0002$\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\ty+!.\u0011\u0007=\n\t,C\u0002\u00024B\u0012qAQ8pY\u0016\fg\u000eC\u0005\u0002\u001ar\t\t\u00111\u0001\u0002\u0012\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t9(a/\t\u0013\u0005eU$!AA\u0002\u0005\u001d\u0015\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u001d\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005]\u0014AB3rk\u0006d7\u000f\u0006\u0003\u00020\u0006%\u0007\"CAMA\u0005\u0005\t\u0019AAIQ\r\u0001\u0011Q\u001a\t\u0005\u0003\u001f\f\u0019.\u0004\u0002\u0002R*\u0019\u0011\u0011J\u0016\n\t\u0005U\u0017\u0011\u001b\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u0010EK\u001a\fW\u000f\u001c;ESN\u0004\u0018\r^2iKJ\u0004&/\u001a:fcVL7/\u001b;fgB\u0011QGI\n\u0006E\u0005u\u0017\u0011\u001e\t\u000e\u0003?\f)OS,aS>Lx0!\u0006\u000e\u0005\u0005\u0005(bAAra\u00059!/\u001e8uS6,\u0017\u0002BAt\u0003C\u0014\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c88!\u0011\tY/!=\u000e\u0005\u00055(bAAx!\u0006\u0011\u0011n\\\u0005\u0004\r\u00065HCAAm\u0003\u0015\t\u0007\u000f\u001d7z)A\t)\"!?\u0002|\u0006u\u0018q B\u0001\u0005\u0007\u0011)\u0001C\u0003IK\u0001\u0007!\nC\u0003VK\u0001\u0007q\u000bC\u0003_K\u0001\u0007\u0001\rC\u0003hK\u0001\u0007\u0011\u000eC\u0003nK\u0001\u0007q\u000eC\u0003xK\u0001\u0007\u0011\u0010C\u0003~K\u0001\u0007q0A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\t-!1\u0003\t\u0006_\u0005\u0005!Q\u0002\t\u000b_\t=!j\u00161j_f|\u0018b\u0001B\ta\t1A+\u001e9mK^B\u0011B!\u0006'\u0003\u0003\u0005\r!!\u0006\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003\u001cA!\u0011\u0011\u0010B\u000f\u0013\u0011\u0011y\"a\u001f\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class DefaultDispatcherPrerequisites
implements DispatcherPrerequisites,
Product,
Serializable {
    private final ThreadFactory threadFactory;
    private final EventStream eventStream;
    private final Scheduler scheduler;
    private final DynamicAccess dynamicAccess;
    private final ActorSystem.Settings settings;
    private final Mailboxes mailboxes;
    private final Option<ExecutionContext> defaultExecutionContext;

    public static Option<Tuple7<ThreadFactory, EventStream, Scheduler, DynamicAccess, ActorSystem.Settings, Mailboxes, Option<ExecutionContext>>> unapply(DefaultDispatcherPrerequisites x$0) {
        return DefaultDispatcherPrerequisites$.MODULE$.unapply(x$0);
    }

    public static DefaultDispatcherPrerequisites apply(ThreadFactory threadFactory, EventStream eventStream, Scheduler scheduler, DynamicAccess dynamicAccess, ActorSystem.Settings settings, Mailboxes mailboxes, Option<ExecutionContext> defaultExecutionContext) {
        return DefaultDispatcherPrerequisites$.MODULE$.apply(threadFactory, eventStream, scheduler, dynamicAccess, settings, mailboxes, defaultExecutionContext);
    }

    public static Function1<Tuple7<ThreadFactory, EventStream, Scheduler, DynamicAccess, ActorSystem.Settings, Mailboxes, Option<ExecutionContext>>, DefaultDispatcherPrerequisites> tupled() {
        return DefaultDispatcherPrerequisites$.MODULE$.tupled();
    }

    public static Function1<ThreadFactory, Function1<EventStream, Function1<Scheduler, Function1<DynamicAccess, Function1<ActorSystem.Settings, Function1<Mailboxes, Function1<Option<ExecutionContext>, DefaultDispatcherPrerequisites>>>>>>> curried() {
        return DefaultDispatcherPrerequisites$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public ThreadFactory threadFactory() {
        return this.threadFactory;
    }

    @Override
    public EventStream eventStream() {
        return this.eventStream;
    }

    @Override
    public Scheduler scheduler() {
        return this.scheduler;
    }

    @Override
    public DynamicAccess dynamicAccess() {
        return this.dynamicAccess;
    }

    @Override
    public ActorSystem.Settings settings() {
        return this.settings;
    }

    @Override
    public Mailboxes mailboxes() {
        return this.mailboxes;
    }

    @Override
    public Option<ExecutionContext> defaultExecutionContext() {
        return this.defaultExecutionContext;
    }

    public DefaultDispatcherPrerequisites copy(ThreadFactory threadFactory, EventStream eventStream, Scheduler scheduler, DynamicAccess dynamicAccess, ActorSystem.Settings settings, Mailboxes mailboxes, Option<ExecutionContext> defaultExecutionContext) {
        return new DefaultDispatcherPrerequisites(threadFactory, eventStream, scheduler, dynamicAccess, settings, mailboxes, defaultExecutionContext);
    }

    public ThreadFactory copy$default$1() {
        return this.threadFactory();
    }

    public EventStream copy$default$2() {
        return this.eventStream();
    }

    public Scheduler copy$default$3() {
        return this.scheduler();
    }

    public DynamicAccess copy$default$4() {
        return this.dynamicAccess();
    }

    public ActorSystem.Settings copy$default$5() {
        return this.settings();
    }

    public Mailboxes copy$default$6() {
        return this.mailboxes();
    }

    public Option<ExecutionContext> copy$default$7() {
        return this.defaultExecutionContext();
    }

    @Override
    public String productPrefix() {
        return "DefaultDispatcherPrerequisites";
    }

    @Override
    public int productArity() {
        return 7;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.threadFactory();
            }
            case 1: {
                return this.eventStream();
            }
            case 2: {
                return this.scheduler();
            }
            case 3: {
                return this.dynamicAccess();
            }
            case 4: {
                return this.settings();
            }
            case 5: {
                return this.mailboxes();
            }
            case 6: {
                return this.defaultExecutionContext();
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
        return x$1 instanceof DefaultDispatcherPrerequisites;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "threadFactory";
            }
            case 1: {
                return "eventStream";
            }
            case 2: {
                return "scheduler";
            }
            case 3: {
                return "dynamicAccess";
            }
            case 4: {
                return "settings";
            }
            case 5: {
                return "mailboxes";
            }
            case 6: {
                return "defaultExecutionContext";
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
        if (!(object instanceof DefaultDispatcherPrerequisites)) return false;
        boolean bl = true;
        if (!bl) return false;
        DefaultDispatcherPrerequisites defaultDispatcherPrerequisites = (DefaultDispatcherPrerequisites)x$1;
        ThreadFactory threadFactory = this.threadFactory();
        ThreadFactory threadFactory2 = defaultDispatcherPrerequisites.threadFactory();
        if (threadFactory == null) {
            if (threadFactory2 != null) {
                return false;
            }
        } else if (!threadFactory.equals(threadFactory2)) return false;
        EventStream eventStream = this.eventStream();
        EventStream eventStream2 = defaultDispatcherPrerequisites.eventStream();
        if (eventStream == null) {
            if (eventStream2 != null) {
                return false;
            }
        } else if (!eventStream.equals(eventStream2)) return false;
        Scheduler scheduler = this.scheduler();
        Scheduler scheduler2 = defaultDispatcherPrerequisites.scheduler();
        if (scheduler == null) {
            if (scheduler2 != null) {
                return false;
            }
        } else if (!scheduler.equals(scheduler2)) return false;
        DynamicAccess dynamicAccess = this.dynamicAccess();
        DynamicAccess dynamicAccess2 = defaultDispatcherPrerequisites.dynamicAccess();
        if (dynamicAccess == null) {
            if (dynamicAccess2 != null) {
                return false;
            }
        } else if (!dynamicAccess.equals(dynamicAccess2)) return false;
        ActorSystem.Settings settings = this.settings();
        ActorSystem.Settings settings2 = defaultDispatcherPrerequisites.settings();
        if (settings == null) {
            if (settings2 != null) {
                return false;
            }
        } else if (!settings.equals(settings2)) return false;
        Mailboxes mailboxes = this.mailboxes();
        Mailboxes mailboxes2 = defaultDispatcherPrerequisites.mailboxes();
        if (mailboxes == null) {
            if (mailboxes2 != null) {
                return false;
            }
        } else if (!mailboxes.equals(mailboxes2)) return false;
        Option<ExecutionContext> option = this.defaultExecutionContext();
        Option<ExecutionContext> option2 = defaultDispatcherPrerequisites.defaultExecutionContext();
        if (option == null) {
            if (option2 == null) return true;
            return false;
        } else {
            if (!option.equals(option2)) return false;
            return true;
        }
    }

    public DefaultDispatcherPrerequisites(ThreadFactory threadFactory, EventStream eventStream, Scheduler scheduler, DynamicAccess dynamicAccess, ActorSystem.Settings settings, Mailboxes mailboxes, Option<ExecutionContext> defaultExecutionContext) {
        this.threadFactory = threadFactory;
        this.eventStream = eventStream;
        this.scheduler = scheduler;
        this.dynamicAccess = dynamicAccess;
        this.settings = settings;
        this.mailboxes = mailboxes;
        this.defaultExecutionContext = defaultExecutionContext;
        Product.$init$(this);
    }
}

