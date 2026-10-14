/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorSystem;
import akka.actor.DynamicAccess;
import akka.actor.Scheduler;
import akka.dispatch.Mailboxes;
import akka.event.EventStream;
import java.util.concurrent.ThreadFactory;
import scala.Option;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=3q\u0001C\u0005\u0011\u0002G\u0005a\u0002C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003\"\u0001\u0019\u0005!\u0005C\u0003*\u0001\u0019\u0005!\u0006C\u00032\u0001\u0019\u0005!\u0007C\u00037\u0001\u0019\u0005q\u0007C\u0003@\u0001\u0019\u0005\u0001\tC\u0003F\u0001\u0019\u0005aIA\fESN\u0004\u0018\r^2iKJ\u0004&/\u001a:fcVL7/\u001b;fg*\u0011!bC\u0001\tI&\u001c\b/\u0019;dQ*\tA\"\u0001\u0003bW.\f7\u0001A\n\u0003\u0001=\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0017!\u0004;ie\u0016\fGMR1di>\u0014\u00180F\u0001\u0018!\tAr$D\u0001\u001a\u0015\tQ2$\u0001\u0006d_:\u001cWO\u001d:f]RT!\u0001H\u000f\u0002\tU$\u0018\u000e\u001c\u0006\u0002=\u0005!!.\u0019<b\u0013\t\u0001\u0013DA\u0007UQJ,\u0017\r\u001a$bGR|'/_\u0001\fKZ,g\u000e^*ue\u0016\fW.F\u0001$!\t!s%D\u0001&\u0015\t13\"A\u0003fm\u0016tG/\u0003\u0002)K\tYQI^3oiN#(/Z1n\u0003%\u00198\r[3ek2,'/F\u0001,!\tas&D\u0001.\u0015\tq3\"A\u0003bGR|'/\u0003\u00021[\tI1k\u00195fIVdWM]\u0001\u000eIft\u0017-\\5d\u0003\u000e\u001cWm]:\u0016\u0003M\u0002\"\u0001\f\u001b\n\u0005Uj#!\u0004#z]\u0006l\u0017nY!dG\u0016\u001c8/\u0001\u0005tKR$\u0018N\\4t+\u0005A\u0004CA\u001d=\u001d\ta#(\u0003\u0002<[\u0005Y\u0011i\u0019;peNK8\u000f^3n\u0013\tidH\u0001\u0005TKR$\u0018N\\4t\u0015\tYT&A\u0005nC&d'm\u001c=fgV\t\u0011\t\u0005\u0002C\u00076\t\u0011\"\u0003\u0002E\u0013\tIQ*Y5mE>DXm]\u0001\u0018I\u00164\u0017-\u001e7u\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR,\u0012a\u0012\t\u0004!!S\u0015BA%\u0012\u0005\u0019y\u0005\u000f^5p]B\u00111*T\u0007\u0002\u0019*\u0011!$E\u0005\u0003\u001d2\u0013\u0001#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;")
public interface DispatcherPrerequisites {
    public ThreadFactory threadFactory();

    public EventStream eventStream();

    public Scheduler scheduler();

    public DynamicAccess dynamicAccess();

    public ActorSystem.Settings settings();

    public Mailboxes mailboxes();

    public Option<ExecutionContext> defaultExecutionContext();
}

