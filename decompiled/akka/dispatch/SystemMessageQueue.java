/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.annotation.InternalStableApi;
import akka.dispatch.sysmsg.SystemMessage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2\u0001\u0002B\u0003\u0011\u0002G\u0005q!\u0003\u0005\u0006!\u00011\tA\u0005\u0005\u0006[\u00011\tA\f\u0005\u0006o\u00011\t\u0001\u000f\u0002\u0013'f\u001cH/Z7NKN\u001c\u0018mZ3Rk\u0016,XM\u0003\u0002\u0007\u000f\u0005AA-[:qCR\u001c\u0007NC\u0001\t\u0003\u0011\t7n[1\u0014\u0005\u0001Q\u0001CA\u0006\u000f\u001b\u0005a!\"A\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u0005=a!AB!osJ+g-A\u0007tsN$X-\\#ocV,W/Z\u0002\u0001)\r\u0019bC\b\t\u0003\u0017QI!!\u0006\u0007\u0003\tUs\u0017\u000e\u001e\u0005\u0006/\u0005\u0001\r\u0001G\u0001\te\u0016\u001cW-\u001b<feB\u0011\u0011\u0004H\u0007\u00025)\u00111dB\u0001\u0006C\u000e$xN]\u0005\u0003;i\u0011\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006?\u0005\u0001\r\u0001I\u0001\b[\u0016\u001c8/Y4f!\t\tC%D\u0001#\u0015\t\u0019S!\u0001\u0004tsNl7oZ\u0005\u0003K\t\u0012QbU=ti\u0016lW*Z:tC\u001e,\u0007FA\u0001(!\tA3&D\u0001*\u0015\tQs!\u0001\u0006b]:|G/\u0019;j_:L!\u0001L\u0015\u0003#%sG/\u001a:oC2\u001cF/\u00192mK\u0006\u0003\u0018.A\u0006tsN$X-\u001c#sC&tGCA\u00183!\t\t\u0003'\u0003\u00022E\tqR)\u0019:mS\u0016\u001cHOR5sgR\u001c\u0016p\u001d;f[6+7o]1hK2K7\u000f\u001e\u0005\u0006g\t\u0001\r\u0001N\u0001\f]\u0016<8i\u001c8uK:$8\u000f\u0005\u0002\"k%\u0011aG\t\u0002\u001d\u0019\u0006$Xm\u001d;GSJ\u001cHoU=ti\u0016lW*Z:tC\u001e,G*[:u\u0003EA\u0017m]*zgR,W.T3tg\u0006<Wm]\u000b\u0002sA\u00111BO\u0005\u0003w1\u0011qAQ8pY\u0016\fg\u000e")
public interface SystemMessageQueue {
    @InternalStableApi
    public void systemEnqueue(ActorRef var1, SystemMessage var2);

    public SystemMessage systemDrain(SystemMessage var1);

    public boolean hasSystemMessages();
}

