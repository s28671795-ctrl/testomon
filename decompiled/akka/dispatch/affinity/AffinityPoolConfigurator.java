/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;
import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.ExecutorServiceConfigurator;
import akka.dispatch.ExecutorServiceFactory;
import akka.dispatch.MonitorableThreadFactory;
import akka.dispatch.ThreadPoolConfig$;
import akka.dispatch.affinity.AffinityPool;
import akka.dispatch.affinity.QueueSelectorFactory;
import akka.dispatch.affinity.RejectionHandlerFactory;
import akka.util.Helpers$;
import akka.util.Helpers$Requiring$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005a!B\n\u0015\u0005aQ\u0002\u0002C\u0010\u0001\u0005\u0003\u0005\u000b\u0011B\u0011\t\u0011)\u0002!\u0011!Q\u0001\n-BQA\f\u0001\u0005\u0002=Bq\u0001\u000e\u0001C\u0002\u0013%Q\u0007\u0003\u0004=\u0001\u0001\u0006IA\u000e\u0005\b{\u0001\u0011\r\u0011\"\u00036\u0011\u0019q\u0004\u0001)A\u0005m!9q\b\u0001b\u0001\n\u0013)\u0004B\u0002!\u0001A\u0003%a\u0007C\u0004B\u0001\t\u0007I\u0011\u0002\"\t\r-\u0003\u0001\u0015!\u0003D\u0011\u001da\u0005A1A\u0005\n5Ca!\u0015\u0001!\u0002\u0013q\u0005b\u0002*\u0001\u0005\u0004%IA\u0011\u0005\u0007'\u0002\u0001\u000b\u0011B\"\t\u000fQ\u0003!\u0019!C\u0005+\"1\u0011\f\u0001Q\u0001\nYCQA\u0017\u0001\u0005Bm\u0013\u0001$\u00114gS:LG/\u001f)p_2\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0015\t)b#\u0001\u0005bM\u001aLg.\u001b;z\u0015\t9\u0002$\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005I\u0012\u0001B1lW\u0006\u001c\"\u0001A\u000e\u0011\u0005qiR\"\u0001\f\n\u0005y1\"aG#yK\u000e,Ho\u001c:TKJ4\u0018nY3D_:4\u0017nZ;sCR|'/\u0001\u0004d_:4\u0017nZ\u0002\u0001!\t\u0011\u0003&D\u0001$\u0015\tyBE\u0003\u0002&M\u0005AA/\u001f9fg\u00064WMC\u0001(\u0003\r\u0019w.\\\u0005\u0003S\r\u0012aaQ8oM&<\u0017!\u00049sKJ,\u0017/^5tSR,7\u000f\u0005\u0002\u001dY%\u0011QF\u0006\u0002\u0018\t&\u001c\b/\u0019;dQ\u0016\u0014\bK]3sKF,\u0018n]5uKN\fa\u0001P5oSRtDc\u0001\u00193gA\u0011\u0011\u0007A\u0007\u0002)!)qd\u0001a\u0001C!)!f\u0001a\u0001W\u0005A\u0001o\\8m'&TX-F\u00017!\t9$(D\u00019\u0015\u0005I\u0014!B:dC2\f\u0017BA\u001e9\u0005\rIe\u000e^\u0001\na>|GnU5{K\u0002\nQ\u0002^1tWF+X-^3TSj,\u0017A\u0004;bg.\fV/Z;f'&TX\rI\u0001\rS\u0012dWm\u00119v\u0019\u00164X\r\\\u0001\u000eS\u0012dWm\u00119v\u0019\u00164X\r\u001c\u0011\u00021E,X-^3TK2,7\r^8s\r\u0006\u001cGo\u001c:z\rF\u001be*F\u0001D!\t!\u0015*D\u0001F\u0015\t1u)\u0001\u0003mC:<'\"\u0001%\u0002\t)\fg/Y\u0005\u0003\u0015\u0016\u0013aa\u0015;sS:<\u0017!G9vKV,7+\u001a7fGR|'OR1di>\u0014\u0018PR)D\u001d\u0002\nA#];fk\u0016\u001cV\r\\3di>\u0014h)Y2u_JLX#\u0001(\u0011\u0005Ez\u0015B\u0001)\u0015\u0005Q\tV/Z;f'\u0016dWm\u0019;pe\u001a\u000b7\r^8ss\u0006)\u0012/^3vKN+G.Z2u_J4\u0015m\u0019;pef\u0004\u0013a\u0007:fU\u0016\u001cG/[8o\u0011\u0006tG\r\\3s\r\u0006\u001cGo\u001c:z\r\u000e\u000bf*\u0001\u000fsK*,7\r^5p]\"\u000bg\u000e\u001a7fe\u001a\u000b7\r^8ss\u001a\u001b\u0015K\u0014\u0011\u0002/I,'.Z2uS>t\u0007*\u00198eY\u0016\u0014h)Y2u_JLX#\u0001,\u0011\u0005E:\u0016B\u0001-\u0015\u0005]\u0011VM[3di&|g\u000eS1oI2,'OR1di>\u0014\u00180\u0001\rsK*,7\r^5p]\"\u000bg\u000e\u001a7fe\u001a\u000b7\r^8ss\u0002\nAd\u0019:fCR,W\t_3dkR|'oU3sm&\u001cWMR1di>\u0014\u0018\u0010F\u0002]?.\u0004\"\u0001H/\n\u0005y3\"AF#yK\u000e,Ho\u001c:TKJ4\u0018nY3GC\u000e$xN]=\t\u000b\u0001\u0014\u0002\u0019A1\u0002\u0005%$\u0007C\u00012j\u001d\t\u0019w\r\u0005\u0002eq5\tQM\u0003\u0002gA\u00051AH]8pizJ!\u0001\u001b\u001d\u0002\rA\u0013X\rZ3g\u0013\tQ%N\u0003\u0002iq!)AN\u0005a\u0001[\u0006iA\u000f\u001b:fC\u00124\u0015m\u0019;pef\u0004\"A\\:\u000e\u0003=T!\u0001]9\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002s\u000f\u0006!Q\u000f^5m\u0013\t!xNA\u0007UQJ,\u0017\r\u001a$bGR|'/\u001f\u0015\u0003\u0001Y\u0004\"a\u001e>\u000e\u0003aT!!\u001f\r\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002|q\na\u0011\t]5NCf\u001c\u0005.\u00198hK\"\u0012\u0001! \t\u0003ozL!a =\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
@ApiMayChange
public final class AffinityPoolConfigurator
extends ExecutorServiceConfigurator {
    private final int akka$dispatch$affinity$AffinityPoolConfigurator$$poolSize;
    private final int akka$dispatch$affinity$AffinityPoolConfigurator$$taskQueueSize;
    private final int akka$dispatch$affinity$AffinityPoolConfigurator$$idleCpuLevel;
    private final String akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactoryFQCN;
    private final QueueSelectorFactory akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactory;
    private final String akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactoryFCQN;
    private final RejectionHandlerFactory akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactory;

    public int akka$dispatch$affinity$AffinityPoolConfigurator$$poolSize() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$poolSize;
    }

    public int akka$dispatch$affinity$AffinityPoolConfigurator$$taskQueueSize() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$taskQueueSize;
    }

    public int akka$dispatch$affinity$AffinityPoolConfigurator$$idleCpuLevel() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$idleCpuLevel;
    }

    public String akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactoryFQCN() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactoryFQCN;
    }

    public QueueSelectorFactory akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactory() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactory;
    }

    public String akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactoryFCQN() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactoryFCQN;
    }

    public RejectionHandlerFactory akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactory() {
        return this.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactory;
    }

    @Override
    public ExecutorServiceFactory createExecutorServiceFactory(String id, ThreadFactory threadFactory) {
        ThreadFactory threadFactory2;
        ThreadFactory threadFactory3 = threadFactory;
        if (threadFactory3 instanceof MonitorableThreadFactory) {
            MonitorableThreadFactory monitorableThreadFactory = (MonitorableThreadFactory)threadFactory3;
            threadFactory2 = monitorableThreadFactory.withName(new StringBuilder(1).append(monitorableThreadFactory.name()).append("-").append(id).toString());
        } else {
            threadFactory2 = threadFactory3;
        }
        ThreadFactory tf = threadFactory2;
        return new ExecutorServiceFactory(this, id, tf){
            private final /* synthetic */ AffinityPoolConfigurator $outer;
            private final String id$1;
            private final ThreadFactory tf$1;

            public ExecutorService createExecutorService() {
                return new AffinityPool(this.id$1, this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$poolSize(), this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$taskQueueSize(), this.tf$1, this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$idleCpuLevel(), this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactory().create(), this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactory().create()).start();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.id$1 = id$1;
                this.tf$1 = tf$1;
            }
        };
    }

    public AffinityPoolConfigurator(Config config, DispatcherPrerequisites prerequisites) {
        super(config, prerequisites);
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$poolSize = ThreadPoolConfig$.MODULE$.scaledPoolSize(config.getInt("parallelism-min"), config.getDouble("parallelism-factor"), config.getInt("parallelism-max"));
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$taskQueueSize = config.getInt("task-queue-size");
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$idleCpuLevel = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(config.getInt("idle-cpu-level"))), level -> 1 <= level && level <= 10, (Function0<Object>)(Function0<String> & Serializable)() -> "idle-cpu-level must be between 1 and 10"));
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactoryFQCN = config.getString("queue-selector");
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactory = (QueueSelectorFactory)((Object)prerequisites.dynamicAccess().createInstanceFor(this.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactoryFQCN(), new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), config)), Nil$.MODULE$), ClassTag$.MODULE$.apply(QueueSelectorFactory.class)).recover(new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ AffinityPoolConfigurator $outer;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                throw new IllegalArgumentException(new StringBuilder(134).append("Cannot instantiate QueueSelectorFactory(queueSelector = ").append(this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$queueSelectorFactoryFQCN()).append("), make sure it has an accessible constructor which accepts a Config parameter").toString());
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }).get());
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactoryFCQN = config.getString("rejection-handler");
        this.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactory = (RejectionHandlerFactory)((Object)prerequisites.dynamicAccess().createInstanceFor(this.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactoryFCQN(), Nil$.MODULE$, ClassTag$.MODULE$.apply(RejectionHandlerFactory.class)).recover(new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ AffinityPoolConfigurator $outer;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x2, Function1<A1, B1> function1) {
                A1 A1 = x2;
                throw new IllegalArgumentException(new StringBuilder(114).append("Cannot instantiate RejectionHandlerFactory(rejection-handler = ").append(this.$outer.akka$dispatch$affinity$AffinityPoolConfigurator$$rejectionHandlerFactoryFCQN()).append("), make sure it has an accessible empty constructor").toString(), A1);
            }

            public final boolean isDefinedAt(Throwable x2) {
                Throwable throwable = x2;
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }).get());
    }
}

