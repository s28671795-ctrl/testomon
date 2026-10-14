/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.setup;

import akka.actor.setup.ActorSystemSetup;
import akka.actor.setup.ActorSystemSetup$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005u1Qa\u0001\u0003\u0002\u0002-AQA\u0005\u0001\u0005\u0002MAQA\u0006\u0001\u0005\u0006]\u0011QaU3ukBT!!\u0002\u0004\u0002\u000bM,G/\u001e9\u000b\u0005\u001dA\u0011!B1di>\u0014(\"A\u0005\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001A\u0002\u0005\u0002\u000e!5\taBC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0013\t\tbB\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003Q\u0001\"!\u0006\u0001\u000e\u0003\u0011\t1!\u00198e)\tA2\u0004\u0005\u0002\u00163%\u0011!\u0004\u0002\u0002\u0011\u0003\u000e$xN]*zgR,WnU3ukBDQ\u0001\b\u0002A\u0002Q\tQa\u001c;iKJ\u0004")
public abstract class Setup {
    public final ActorSystemSetup and(Setup other) {
        return ActorSystemSetup$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Setup[]{this, other}));
    }
}

