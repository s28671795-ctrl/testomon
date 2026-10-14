/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import java.io.Serializable;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\r3QAB\u0004\u0002\u00021AQ!\u0005\u0001\u0005\u0002IAQ\u0001\u0006\u0001\u0005FUAQ!\b\u0001\u0005ByAQa\u000b\u0001\u0007\u00021BQ\u0001\u0011\u0001\u0005B\u0005\u0013A#\u00168usB,G-\u00112tiJ\f7\r^!di>\u0014(B\u0001\u0005\n\u0003\u0015\t7\r^8s\u0015\u0005Q\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001bA\u0011abD\u0007\u0002\u000f%\u0011\u0001c\u0002\u0002\u000e\u0003\n\u001cHO]1di\u0006\u001bGo\u001c:\u0002\rqJg.\u001b;?)\u0005\u0019\u0002C\u0001\b\u0001\u00035\u0019'/Z1uKJ+7-Z5wKR\ta\u0003\u0005\u0002\u001859\u0011a\u0002G\u0005\u00033\u001d\tQ\"\u00112tiJ\f7\r^!di>\u0014\u0018BA\u000e\u001d\u0005\u001d\u0011VmY3jm\u0016T!!G\u0004\u0002\u000fI,7-Z5wKV\tq\u0004\u0005\u0003!G\u0015BS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:\u0004\"\u0001\t\u0014\n\u0005\u001d\n#aA!osB\u0011\u0001%K\u0005\u0003U\u0005\u0012A!\u00168ji\u0006IqN\u001c*fG\u0016Lg/\u001a\u000b\u0003Q5BQA\f\u0003A\u0002\u0015\nq!\\3tg\u0006<W\rK\u0002\u0005a}\u00022\u0001I\u00194\u0013\t\u0011\u0014E\u0001\u0004uQJ|wo\u001d\t\u0003iqr!!\u000e\u001e\u000f\u0005YJT\"A\u001c\u000b\u0005aZ\u0011A\u0002\u001fs_>$h(C\u0001#\u0013\tY\u0014%A\u0004qC\u000e\\\u0017mZ3\n\u0005ur$!\u0003+ie><\u0018M\u00197f\u0015\tY\u0014eI\u00014\u0003%)h\u000e[1oI2,G\r\u0006\u0002)\u0005\")a&\u0002a\u0001K\u0001")
public abstract class UntypedAbstractActor
extends AbstractActor {
    @Override
    public final AbstractActor.Receive createReceive() {
        throw new UnsupportedOperationException("createReceive should not be used by UntypedAbstractActor");
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UntypedAbstractActor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                this.$outer.onReceive(A1);
                return (B1)BoxedUnit.UNIT;
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return true;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public abstract void onReceive(Object var1) throws Throwable;

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }
}

