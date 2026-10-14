/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorInitializationException;
import akka.actor.ActorKilledException;
import akka.actor.DeathPactException;
import akka.actor.OneForOneStrategy;
import akka.actor.OneForOneStrategy$;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.actor.SupervisorStrategy$Escalate$;
import akka.actor.SupervisorStrategy$Restart$;
import akka.actor.SupervisorStrategy$Resume$;
import akka.actor.SupervisorStrategy$Stop$;
import akka.actor.SupervisorStrategyLowPriorityImplicits;
import akka.japi.Function;
import akka.japi.Util$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableFactory$;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuffer;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

public final class SupervisorStrategy$
implements SupervisorStrategyLowPriorityImplicits {
    public static final SupervisorStrategy$ MODULE$ = new SupervisorStrategy$();
    private static final PartialFunction<Throwable, SupervisorStrategy.Directive> defaultDecider;
    private static final SupervisorStrategy defaultStrategy;
    private static final SupervisorStrategy stoppingStrategy;
    private static final Function1<Object, SupervisorStrategy$Escalate$> escalateDefault;

    static {
        SupervisorStrategyLowPriorityImplicits.$init$(MODULE$);
        defaultDecider = new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ActorInitializationException) {
                    return (B1)SupervisorStrategy$Stop$.MODULE$;
                }
                if (A1 instanceof ActorKilledException) {
                    return (B1)SupervisorStrategy$Stop$.MODULE$;
                }
                if (A1 instanceof DeathPactException) {
                    return (B1)SupervisorStrategy$Stop$.MODULE$;
                }
                if (A1 instanceof Exception) {
                    return (B1)SupervisorStrategy$Restart$.MODULE$;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                if (throwable instanceof ActorInitializationException) {
                    return true;
                }
                if (throwable instanceof ActorKilledException) {
                    return true;
                }
                if (throwable instanceof DeathPactException) {
                    return true;
                }
                return throwable instanceof Exception;
            }
        };
        defaultStrategy = new OneForOneStrategy(OneForOneStrategy$.MODULE$.apply$default$1(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), MODULE$.defaultDecider());
        stoppingStrategy = new OneForOneStrategy(OneForOneStrategy$.MODULE$.apply$default$1(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), SupervisorStrategy$.stoppingDecider$1());
        escalateDefault = (Function1<Object, SupervisorStrategy$Escalate$> & Serializable)x$3 -> SupervisorStrategy$Escalate$.MODULE$;
    }

    @Override
    public PartialFunction<Throwable, SupervisorStrategy.Directive> seqCauseDirective2Decider(Iterable<Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>> trapExit) {
        return SupervisorStrategyLowPriorityImplicits.seqCauseDirective2Decider$(this, trapExit);
    }

    public SupervisorStrategy$Resume$ resume() {
        return SupervisorStrategy$Resume$.MODULE$;
    }

    public SupervisorStrategy.Directive resume(int logLevel) {
        return new SupervisorStrategy.Resume(logLevel);
    }

    public SupervisorStrategy$Restart$ restart() {
        return SupervisorStrategy$Restart$.MODULE$;
    }

    public SupervisorStrategy.Directive restart(int logLevel) {
        return new SupervisorStrategy.Restart(logLevel);
    }

    public SupervisorStrategy$Stop$ stop() {
        return SupervisorStrategy$Stop$.MODULE$;
    }

    public SupervisorStrategy.Directive stop(int logLevel) {
        return new SupervisorStrategy.Stop(logLevel);
    }

    public SupervisorStrategy$Escalate$ escalate() {
        return SupervisorStrategy$Escalate$.MODULE$;
    }

    public final PartialFunction<Throwable, SupervisorStrategy.Directive> defaultDecider() {
        return defaultDecider;
    }

    public final SupervisorStrategy defaultStrategy() {
        return defaultStrategy;
    }

    public final SupervisorStrategy stoppingStrategy() {
        return stoppingStrategy;
    }

    public PartialFunction<Throwable, SupervisorStrategy.Directive> seqThrowable2Decider(Seq<Class<? extends Throwable>> trapExit) {
        return this.makeDecider(trapExit);
    }

    public PartialFunction<Throwable, SupervisorStrategy.Directive> makeDecider(Seq<Class<? extends Throwable>> trapExit) {
        return new Serializable(trapExit){
            private static final long serialVersionUID = 0L;
            private final Seq trapExit$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (this.trapExit$1.exists((Function1<Class, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(anonfun.makeDecider.1.$anonfun$applyOrElse$1(A1, x$1)))) {
                    return (B1)SupervisorStrategy$Restart$.MODULE$;
                }
                return (B1)SupervisorStrategy$Escalate$.MODULE$;
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }

            public static final /* synthetic */ boolean $anonfun$applyOrElse$1(Throwable x1$1, Class x$1) {
                return x$1.isInstance(x1$1);
            }
            {
                this.trapExit$1 = trapExit$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(java.lang.Throwable java.lang.Class )}, serializedLambda);
            }
        };
    }

    public PartialFunction<Throwable, SupervisorStrategy.Directive> makeDecider(java.lang.Iterable<Class<? extends Throwable>> trapExit) {
        return this.makeDecider(Util$.MODULE$.immutableSeq(trapExit));
    }

    public PartialFunction<Throwable, SupervisorStrategy.Directive> makeDecider(Iterable<Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>> flat) {
        Seq<Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>> directives = this.sort(flat);
        return new Serializable(directives){
            private static final long serialVersionUID = 0L;
            private final Seq directives$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                return (B1)this.directives$1.collectFirst(new Serializable(null, A1){
                    private static final long serialVersionUID = 0L;
                    private final Throwable x1$2;

                    public final <A1 extends Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                        A1 A1 = x1;
                        if (A1 != null) {
                            Class<? extends Throwable> c = A1._1();
                            SupervisorStrategy.Directive d = A1._2();
                            if (c.isInstance(this.x1$2)) {
                                return (B1)d;
                            }
                        }
                        return function1.apply(x1);
                    }

                    public final boolean isDefinedAt(Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive> x1) {
                        Class<? extends Throwable> c;
                        Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive> tuple2 = x1;
                        return tuple2 != null && (c = tuple2._1()).isInstance(this.x1$2);
                    }
                    {
                        this.x1$2 = x1$2;
                    }
                }).getOrElse((Function0<SupervisorStrategy$Escalate$> & Serializable)() -> SupervisorStrategy$Escalate$.MODULE$);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }
            {
                this.directives$1 = directives$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$3()}, serializedLambda);
            }
        };
    }

    public PartialFunction<Throwable, SupervisorStrategy.Directive> makeDecider(Function<Throwable, SupervisorStrategy.Directive> func) {
        return new Serializable(func){
            private static final long serialVersionUID = 0L;
            private final Function func$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                return (B1)this.func$1.apply(A1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return true;
            }
            {
                this.func$1 = func$1;
            }
        };
    }

    public Seq<Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>> sort(Iterable<Tuple2<Class<? extends Throwable>, SupervisorStrategy.Directive>> in) {
        return ((IterableOnceOps)in.foldLeft(new ArrayBuffer(in.size()), (Function2<ArrayBuffer, Tuple2, ArrayBuffer> & Serializable)(buf, ca) -> {
            int n = buf.indexWhere((Function1<Tuple2, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(SupervisorStrategy$.$anonfun$sort$2(ca, x$2)));
            switch (n) {
                case -1: {
                    Object object = buf.append(ca);
                    break;
                }
                default: {
                    buf.insert(n, ca);
                    Object object = BoxedUnit.UNIT;
                    break;
                }
            }
            return buf;
        })).to(IterableFactory$.MODULE$.toFactory(IndexedSeq$.MODULE$));
    }

    public Option<Duration> withinTimeRangeOption(Duration withinTimeRange) {
        if (withinTimeRange.isFinite() && withinTimeRange.$greater$eq(Duration$.MODULE$.Zero())) {
            return new Some<Duration>(withinTimeRange);
        }
        return None$.MODULE$;
    }

    public Option<Object> maxNrOfRetriesOption(int maxNrOfRetries) {
        if (maxNrOfRetries < 0) {
            return None$.MODULE$;
        }
        return new Some<Object>(BoxesRunTime.boxToInteger(maxNrOfRetries));
    }

    public Function1<Object, SupervisorStrategy$Escalate$> escalateDefault() {
        return escalateDefault;
    }

    private static final PartialFunction stoppingDecider$1() {
        return new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Exception) {
                    return (B1)SupervisorStrategy$Stop$.MODULE$;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return throwable instanceof Exception;
            }
        };
    }

    public static final /* synthetic */ boolean $anonfun$sort$2(Tuple2 ca$1, Tuple2 x$2) {
        return ((Class)x$2._1()).isAssignableFrom((Class)ca$1._1());
    }

    private SupervisorStrategy$() {
    }
}

