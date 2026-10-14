/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00012qAA\u0002\u0011\u0002G\u0005\u0001\u0002\u0003\u0004\u0010\u0001\u0019\u0005Q\u0001\u0005\u0002\u001c\u00072\f7o]5d\u0003\u000e$xN]\"p]R,\u0007\u0010\u001e)s_ZLG-\u001a:\u000b\u0005\u0011)\u0011!B1di>\u0014(\"\u0001\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001\u0011\u0002\u0005\u0002\u000b\u001b5\t1BC\u0001\r\u0003\u0015\u00198-\u00197b\u0013\tq1B\u0001\u0004B]f\u0014VMZ\u0001\u0014G2\f7o]5d\u0003\u000e$xN]\"p]R,\u0007\u0010^\u000b\u0002#A\u0011!cE\u0007\u0002\u0007%\u0011Ac\u0001\u0002\r\u0003\u000e$xN]\"p]R,\u0007\u0010\u001e\u0015\u0003\u0003Y\u0001\"a\u0006\u000e\u000e\u0003aQ!!G\u0003\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002\u001c1\tY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001Q\u0004\u0005\u0002\u0018=%\u0011q\u0004\u0007\u0002\r\t>tu\u000e^%oQ\u0016\u0014\u0018\u000e\u001e")
@DoNotInherit
public interface ClassicActorContextProvider {
    @InternalApi
    public ActorContext classicActorContext();
}

