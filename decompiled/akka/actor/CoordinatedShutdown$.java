/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.Done;
import akka.Done$;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.CoordinatedShutdown;
import akka.actor.CoordinatedShutdown$ActorSystemTerminateReason$;
import akka.actor.CoordinatedShutdown$ClusterDowningReason$;
import akka.actor.CoordinatedShutdown$ClusterJoinUnsuccessfulReason$;
import akka.actor.CoordinatedShutdown$ClusterLeavingReason$;
import akka.actor.CoordinatedShutdown$IncompatibleConfigurationDetectedReason$;
import akka.actor.CoordinatedShutdown$JvmExitReason$;
import akka.actor.CoordinatedShutdown$UnknownReason$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.actor.JVMShutdownHooks;
import akka.actor.JVMShutdownHooks$;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.immutable.List;
import scala.collection.immutable.Set;
import scala.concurrent.Await$;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.package$;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ObjectRef;
import scala.util.Try$;
import scala.util.control.NonFatal$;

public final class CoordinatedShutdown$
implements ExtensionId<CoordinatedShutdown>,
ExtensionIdProvider {
    public static final CoordinatedShutdown$ MODULE$ = new CoordinatedShutdown$();
    private static final String PhaseBeforeServiceUnbind;
    private static final String PhaseServiceUnbind;
    private static final String PhaseServiceRequestsDone;
    private static final String PhaseServiceStop;
    private static final String PhaseBeforeClusterShutdown;
    private static final String PhaseClusterShardingShutdownRegion;
    private static final String PhaseClusterLeave;
    private static final String PhaseClusterExiting;
    private static final String PhaseClusterExitingDone;
    private static final String PhaseClusterShutdown;
    private static final String PhaseBeforeActorSystemTerminate;
    private static final String PhaseActorSystemTerminate;
    private static volatile boolean akka$actor$CoordinatedShutdown$$runningJvmHook;

    static {
        ExtensionId.$init$(MODULE$);
        PhaseBeforeServiceUnbind = "before-service-unbind";
        PhaseServiceUnbind = "service-unbind";
        PhaseServiceRequestsDone = "service-requests-done";
        PhaseServiceStop = "service-stop";
        PhaseBeforeClusterShutdown = "before-cluster-shutdown";
        PhaseClusterShardingShutdownRegion = "cluster-sharding-shutdown-region";
        PhaseClusterLeave = "cluster-leave";
        PhaseClusterExiting = "cluster-exiting";
        PhaseClusterExitingDone = "cluster-exiting-done";
        PhaseClusterShutdown = "cluster-shutdown";
        PhaseBeforeActorSystemTerminate = "before-actor-system-terminate";
        PhaseActorSystemTerminate = "actor-system-terminate";
        akka$actor$CoordinatedShutdown$$runningJvmHook = false;
    }

    @Override
    public Extension apply(ActorSystem system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public Extension apply(ClassicActorSystemProvider system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    public JVMShutdownHooks $lessinit$greater$default$3() {
        return JVMShutdownHooks$.MODULE$;
    }

    public String PhaseBeforeServiceUnbind() {
        return PhaseBeforeServiceUnbind;
    }

    public String PhaseServiceUnbind() {
        return PhaseServiceUnbind;
    }

    public String PhaseServiceRequestsDone() {
        return PhaseServiceRequestsDone;
    }

    public String PhaseServiceStop() {
        return PhaseServiceStop;
    }

    public String PhaseBeforeClusterShutdown() {
        return PhaseBeforeClusterShutdown;
    }

    public String PhaseClusterShardingShutdownRegion() {
        return PhaseClusterShardingShutdownRegion;
    }

    public String PhaseClusterLeave() {
        return PhaseClusterLeave;
    }

    public String PhaseClusterExiting() {
        return PhaseClusterExiting;
    }

    public String PhaseClusterExitingDone() {
        return PhaseClusterExitingDone;
    }

    public String PhaseClusterShutdown() {
        return PhaseClusterShutdown;
    }

    public String PhaseBeforeActorSystemTerminate() {
        return PhaseBeforeActorSystemTerminate;
    }

    public String PhaseActorSystemTerminate() {
        return PhaseActorSystemTerminate;
    }

    public CoordinatedShutdown.Reason unknownReason() {
        return CoordinatedShutdown$UnknownReason$.MODULE$;
    }

    public CoordinatedShutdown.Reason actorSystemTerminateReason() {
        return CoordinatedShutdown$ActorSystemTerminateReason$.MODULE$;
    }

    public CoordinatedShutdown.Reason jvmExitReason() {
        return CoordinatedShutdown$JvmExitReason$.MODULE$;
    }

    public CoordinatedShutdown.Reason clusterDowningReason() {
        return CoordinatedShutdown$ClusterDowningReason$.MODULE$;
    }

    public CoordinatedShutdown.Reason clusterJoinUnsuccessfulReason() {
        return CoordinatedShutdown$ClusterJoinUnsuccessfulReason$.MODULE$;
    }

    public CoordinatedShutdown.Reason incompatibleConfigurationDetectedReason() {
        return CoordinatedShutdown$IncompatibleConfigurationDetectedReason$.MODULE$;
    }

    public CoordinatedShutdown.Reason clusterLeavingReason() {
        return CoordinatedShutdown$ClusterLeavingReason$.MODULE$;
    }

    public boolean akka$actor$CoordinatedShutdown$$runningJvmHook() {
        return akka$actor$CoordinatedShutdown$$runningJvmHook;
    }

    private void runningJvmHook_$eq(boolean x$1) {
        akka$actor$CoordinatedShutdown$$runningJvmHook = x$1;
    }

    @Override
    public CoordinatedShutdown get(ActorSystem system) {
        return (CoordinatedShutdown)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public CoordinatedShutdown get(ClassicActorSystemProvider system) {
        return (CoordinatedShutdown)ExtensionId.get$((ExtensionId)this, system);
    }

    public CoordinatedShutdown$ lookup() {
        return this;
    }

    @Override
    public CoordinatedShutdown createExtension(ExtendedActorSystem system) {
        Config conf = system.settings().config().getConfig("akka.coordinated-shutdown");
        scala.collection.immutable.Map<String, CoordinatedShutdown.Phase> phases = this.phasesFromConfig(conf);
        CoordinatedShutdown coord = new CoordinatedShutdown(system, phases, this.$lessinit$greater$default$3());
        this.init(system, conf, coord);
        return coord;
    }

    @InternalApi
    public Config confWithOverrides(Config conf, Option<CoordinatedShutdown.Reason> reason) {
        return (Config)reason.flatMap((Function1<CoordinatedShutdown.Reason, Option> & Serializable)r -> {
            String basePath = new StringBuilder(19).append("reason-overrides.\"").append(r.getClass().getName()).append("\"").toString();
            if (conf.hasPath(basePath)) {
                return new Some<Config>(conf.getConfig(basePath).withFallback(conf));
            }
            return None$.MODULE$;
        }).getOrElse((Function0<Config> & Serializable)() -> conf);
    }

    @InternalApi
    public void init(ExtendedActorSystem system, Config conf, CoordinatedShutdown coord) {
        this.initPhaseActorSystemTerminate(system, conf, coord);
        this.initJvmHook(system, conf, coord);
        try {
            system.registerOnTermination(() -> this.cleanupActorSystemJvmHook$1(coord));
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            this.cleanupActorSystemJvmHook$1(coord);
        }
    }

    private void initPhaseActorSystemTerminate(ExtendedActorSystem system, Config conf, CoordinatedShutdown coord) {
        coord.addTask(this.PhaseActorSystemTerminate(), "terminate-system", (Function0<Future<Done>>)(Function0<Future> & Serializable)() -> {
            Config confForReason = MODULE$.confWithOverrides(conf, coord.shutdownReason());
            boolean terminateActorSystem = confForReason.getBoolean("terminate-actor-system");
            boolean exitJvm = confForReason.getBoolean("exit-jvm");
            int exitCode = confForReason.getInt("exit-code");
            if (exitJvm && terminateActorSystem) {
                FiniteDuration timeout2 = coord.timeout(MODULE$.PhaseActorSystemTerminate());
                Thread t2 = new Thread(system, timeout2, exitCode){
                    private final ExtendedActorSystem system$1;
                    private final FiniteDuration timeout$1;
                    private final int exitCode$1;

                    public void run() {
                        if (Try$.MODULE$.apply((Function0<Future> & Serializable)() -> (Future)Await$.MODULE$.ready($this.system$1.whenTerminated(), $this.timeout$1)).isFailure() && !CoordinatedShutdown$.MODULE$.akka$actor$CoordinatedShutdown$$runningJvmHook()) {
                            System.exit(this.exitCode$1);
                            return;
                        }
                    }
                    {
                        this.system$1 = system$1;
                        this.timeout$1 = timeout$1;
                        this.exitCode$1 = exitCode$1;
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$run$1(akka.actor.CoordinatedShutdown$$anon$1 )}, serializedLambda);
                    }
                };
                t2.setName("CoordinatedShutdown-exit");
                t2.start();
            }
            if (terminateActorSystem) {
                system.finalTerminate();
                return system.whenTerminated().map((Function1<Terminated, Done$> & Serializable)x$1 -> {
                    block0: {
                        if (!exitJvm || MODULE$.akka$actor$CoordinatedShutdown$$runningJvmHook()) break block0;
                        System.exit(exitCode);
                    }
                    return Done$.MODULE$;
                }, ExecutionContexts$.MODULE$.parasitic());
            }
            if (exitJvm) {
                System.exit(exitCode);
                return Future$.MODULE$.successful(Done$.MODULE$);
            }
            return Future$.MODULE$.successful(Done$.MODULE$);
        });
    }

    private void initJvmHook(ActorSystem system, Config conf, CoordinatedShutdown coord) {
        boolean runByJvmShutdownHook;
        boolean bl = runByJvmShutdownHook = system.settings().JvmShutdownHooks() && conf.getBoolean("run-by-jvm-shutdown-hook");
        if (runByJvmShutdownHook) {
            coord.akka$actor$CoordinatedShutdown$$actorSystemJvmHook_$eq(OptionVal$Some$.MODULE$.apply(coord.addCancellableJvmShutdownHook((Function0<Object> & Serializable)() -> {
                MODULE$.runningJvmHook_$eq(true);
                if (!system.whenTerminated().isCompleted()) {
                    Object object;
                    coord.log().debug("Starting coordinated shutdown from JVM shutdown hook");
                    try {
                        FiniteDuration totalTimeout = coord.totalTimeout().max(new package.DurationInt(scala.concurrent.duration.package$.MODULE$.DurationInt(3)).seconds());
                        object = Await$.MODULE$.ready(coord.run(CoordinatedShutdown$JvmExitReason$.MODULE$), totalTimeout);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable e = option.get();
                            coord.log().warning("CoordinatedShutdown from JVM shutdown failed: {}", (Object)e.getMessage());
                            object = BoxedUnit.UNIT;
                        }
                        throw throwable;
                    }
                    return object;
                }
                return BoxedUnit.UNIT;
            })));
            return;
        }
    }

    public scala.collection.immutable.Map<String, CoordinatedShutdown.Phase> phasesFromConfig(Config conf) {
        String defaultPhaseTimeout = conf.getString("default-phase-timeout");
        Config phasesConf = conf.getConfig("phases");
        Config defaultPhaseConfig = ConfigFactory.parseString(new StringBuilder(86).append("\n      timeout = ").append(defaultPhaseTimeout).append("\n      recover = true\n      enabled = true\n      depends-on = []\n    ").toString());
        return (scala.collection.immutable.Map)package$JavaConverters$.MODULE$.MapHasAsScala(phasesConf.root().unwrapped()).asScala().toMap($less$colon$less$.MODULE$.refl()).map((Function1<Tuple2, Tuple2> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                String k = (String)tuple2._1();
                if (tuple2._2() instanceof Map) {
                    Config c = phasesConf.getConfig(k).withFallback(defaultPhaseConfig);
                    Set<String> dependsOn = package$JavaConverters$.MODULE$.ListHasAsScala(c.getStringList("depends-on")).asScala().toSet();
                    FiniteDuration timeout2 = new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(c.getDuration("timeout", TimeUnit.MILLISECONDS))).millis();
                    boolean recover2 = c.getBoolean("recover");
                    boolean enabled = c.getBoolean("enabled");
                    return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(k), new CoordinatedShutdown.Phase(dependsOn, timeout2, recover2, enabled));
                }
            }
            if (tuple2 != null) {
                String k = (String)tuple2._1();
                Object v = tuple2._2();
                throw new IllegalArgumentException(new StringBuilder(36).append("Expected object value for [").append(k).append("], got [").append(v).append("]").toString());
            }
            throw new MatchError(tuple2);
        });
    }

    public List<String> topologicalSort(scala.collection.immutable.Map<String, CoordinatedShutdown.Phase> phases) {
        ObjectRef<Object> result = ObjectRef.create(package$.MODULE$.List().empty());
        ObjectRef<Set> unmarked = ObjectRef.create((Set)phases.keySet().$plus$plus((IterableOnce)phases.values().flatMap((Function1<CoordinatedShutdown.Phase, Set> & Serializable)x$2 -> x$2.dependsOn())));
        ObjectRef<Object> tempMark = ObjectRef.create(Predef$.MODULE$.Set().empty());
        while (((Set)unmarked.elem).nonEmpty()) {
            CoordinatedShutdown$.depthFirstSearch$1((String)((Set)unmarked.elem).head(), tempMark, phases, unmarked, result);
        }
        return ((List)result.elem).reverse();
    }

    private final void cleanupActorSystemJvmHook$1(CoordinatedShutdown coord$1) {
        Cancellable cancellable = coord$1.akka$actor$CoordinatedShutdown$$actorSystemJvmHook();
        Cancellable cancellable2 = OptionVal$Some$.MODULE$.unapply(cancellable);
        if (!OptionVal$.MODULE$.isEmpty$extension(cancellable2)) {
            Cancellable cancellable3 = OptionVal$.MODULE$.get$extension(cancellable2);
            if (!this.akka$actor$CoordinatedShutdown$$runningJvmHook() && !cancellable3.isCancelled()) {
                cancellable3.cancel();
                OptionVal$.MODULE$.None();
                coord$1.akka$actor$CoordinatedShutdown$$actorSystemJvmHook_$eq(null);
                return;
            }
        }
    }

    private static final void depthFirstSearch$1(String u2, ObjectRef tempMark$1, scala.collection.immutable.Map phases$1, ObjectRef unmarked$1, ObjectRef result$1) {
        if (((Set)tempMark$1.elem).apply(u2)) {
            throw new IllegalArgumentException(new StringBuilder(112).append("Cycle detected in graph of phases. It must be a DAG. ").append("phase [").append(u2).append("] depends transitively on itself. All dependencies: ").append(phases$1).toString());
        }
        if (((Set)unmarked$1.elem).apply(u2)) {
            tempMark$1.elem = (Set)((Set)tempMark$1.elem).$plus(u2);
            Option option = phases$1.get(u2);
            if (option instanceof Some) {
                Some some = (Some)option;
                CoordinatedShutdown.Phase p = (CoordinatedShutdown.Phase)some.value();
                p.dependsOn().foreach((Function1<String, Object> & Serializable)u -> {
                    CoordinatedShutdown$.depthFirstSearch$1(u, tempMark$1, phases$1, unmarked$1, result$1);
                    return BoxedUnit.UNIT;
                });
            } else if (None$.MODULE$.equals(option)) {
            } else {
                throw new MatchError(option);
            }
            unmarked$1.elem = (Set)((Set)unmarked$1.elem).$minus(u2);
            tempMark$1.elem = (Set)((Set)tempMark$1.elem).$minus(u2);
            String string = u2;
            result$1.elem = ((List)result$1.elem).$colon$colon(string);
            return;
        }
    }

    private CoordinatedShutdown$() {
    }
}

