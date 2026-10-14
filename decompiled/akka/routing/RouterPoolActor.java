/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorInitializationException$;
import akka.actor.SupervisorStrategy;
import akka.routing.AdjustPoolSize;
import akka.routing.Pool;
import akka.routing.Routee;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005M2Qa\u0002\u0005\u0001\u00151A\u0001\"\u0005\u0001\u0003\u0006\u0004%\te\u0005\u0005\t5\u0001\u0011\t\u0011)A\u0005)!)1\u0004\u0001C\u00019!9q\u0004\u0001b\u0001\n\u0003\u0001\u0003B\u0002\u0013\u0001A\u0003%\u0011\u0005C\u0003&\u0001\u0011\u0005cEA\bS_V$XM\u001d)p_2\f5\r^8s\u0015\tI!\"A\u0004s_V$\u0018N\\4\u000b\u0003-\tA!Y6lCN\u0011\u0001!\u0004\t\u0003\u001d=i\u0011\u0001C\u0005\u0003!!\u00111BU8vi\u0016\u0014\u0018i\u0019;pe\u0006\u00112/\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0007\u0001)\u0012\u0001\u0006\t\u0003+ai\u0011A\u0006\u0006\u0003/)\tQ!Y2u_JL!!\u0007\f\u0003%M+\b/\u001a:wSN|'o\u0015;sCR,w-_\u0001\u0014gV\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u0017\u0010I\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005uq\u0002C\u0001\b\u0001\u0011\u0015\t2\u00011\u0001\u0015\u0003\u0011\u0001xn\u001c7\u0016\u0003\u0005\u0002\"A\u0004\u0012\n\u0005\rB!\u0001\u0002)p_2\fQ\u0001]8pY\u0002\nqA]3dK&4X-F\u0001(!\u0011A3&\f\u0019\u000e\u0003%R\u0011AK\u0001\u0006g\u000e\fG.Y\u0005\u0003Y%\u0012q\u0002U1si&\fGNR;oGRLwN\u001c\t\u0003Q9J!aL\u0015\u0003\u0007\u0005s\u0017\u0010\u0005\u0002)c%\u0011!'\u000b\u0002\u0005+:LG\u000f")
public class RouterPoolActor
extends RouterActor {
    private final SupervisorStrategy supervisorStrategy;
    private final Pool pool;

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    public Pool pool() {
        return this.pool;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ RouterPoolActor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof AdjustPoolSize) {
                    AdjustPoolSize adjustPoolSize = (AdjustPoolSize)A1;
                    int change = adjustPoolSize.change();
                    if (true) {
                        int n = change;
                        if (n > 0) {
                            Vector newRoutees = (Vector)package$.MODULE$.Vector().fill(n, (Function0<Routee> & Serializable)() -> $this.$outer.pool().newRoutee($this.$outer.cell().routeeProps(), $this.$outer.context()));
                            this.$outer.cell().addRoutees(newRoutees);
                            return (B1)BoxedUnit.UNIT;
                        }
                        if (n < 0) {
                            IndexedSeq<Routee> currentRoutees = this.$outer.cell().router().routees();
                            IndexedSeq abandon = (IndexedSeq)currentRoutees.drop(currentRoutees.length() + n);
                            this.$outer.cell().removeRoutees(abandon, true);
                            return (B1)BoxedUnit.UNIT;
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof AdjustPoolSize && true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$2(akka.routing.RouterPoolActor$$anonfun$receive$2 )}, serializedLambda);
            }
        }.orElse(super.receive());
    }

    public RouterPoolActor(SupervisorStrategy supervisorStrategy) {
        this.supervisorStrategy = supervisorStrategy;
        RouterConfig routerConfig = this.cell().routerConfig();
        if (!(routerConfig instanceof Pool)) {
            throw ActorInitializationException$.MODULE$.apply(new StringBuilder(48).append("RouterPoolActor can only be used with Pool, not ").append(routerConfig.getClass()).toString());
        }
        Pool pool = (Pool)routerConfig;
        this.pool = pool;
    }
}

