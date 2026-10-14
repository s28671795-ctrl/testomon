/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.Deploy$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props$;
import akka.io.BufferPool;
import akka.io.DirectByteBufferPool;
import akka.io.IO;
import akka.io.Udp;
import akka.io.UdpConnectedManager;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005!3AAC\u0006\u0001!!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003'\u0001\u0011\u0005q\u0005C\u0004+\u0001\t\u0007I\u0011A\u0016\t\ri\u0002\u0001\u0015!\u0003-\u0011\u001dY\u0004A1A\u0005\u0002qBa\u0001\u0011\u0001!\u0002\u0013i\u0004\"B!\u0001\t\u0003a\u0004b\u0002\"\u0001\u0005\u0004%\ta\u0011\u0005\u0007\u000f\u0002\u0001\u000b\u0011\u0002#\u0003\u001fU#\u0007oQ8o]\u0016\u001cG/\u001a3FqRT!\u0001D\u0007\u0002\u0005%|'\"\u0001\b\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001\u0011c\u0006\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005aabBA\r\u001b\u001b\u0005Y\u0011BA\u000e\f\u0003\tIu*\u0003\u0002\u001e=\tIQ\t\u001f;f]NLwN\u001c\u0006\u00037-\taa]=ti\u0016l\u0007CA\u0011%\u001b\u0005\u0011#BA\u0012\u000e\u0003\u0015\t7\r^8s\u0013\t)#EA\nFqR,g\u000eZ3e\u0003\u000e$xN]*zgR,W.\u0001\u0004=S:LGO\u0010\u000b\u0003Q%\u0002\"!\u0007\u0001\t\u000b}\u0011\u0001\u0019\u0001\u0011\u0002\u0011M,G\u000f^5oON,\u0012\u0001\f\t\u0003[]r!AL\u001b\u000f\u0005=\"dB\u0001\u00194\u001b\u0005\t$B\u0001\u001a\u0010\u0003\u0019a$o\\8u}%\ta\"\u0003\u0002\r\u001b%\u0011agC\u0001\u0004+\u0012\u0004\u0018B\u0001\u001d:\u0005-)F\r]*fiRLgnZ:\u000b\u0005YZ\u0011!C:fiRLgnZ:!\u0003\u001di\u0017M\\1hKJ,\u0012!\u0010\t\u0003CyJ!a\u0010\u0012\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0001\"\\1oC\u001e,'\u000fI\u0001\u000bO\u0016$X*\u00198bO\u0016\u0014\u0018A\u00032vM\u001a,'\u000fU8pYV\tA\t\u0005\u0002\u001a\u000b&\u0011ai\u0003\u0002\u000b\u0005V4g-\u001a:Q_>d\u0017a\u00032vM\u001a,'\u000fU8pY\u0002\u0002")
public class UdpConnectedExt
implements IO.Extension {
    private final Udp.UdpSettings settings;
    private final ActorRef manager;
    private final BufferPool bufferPool;

    public Udp.UdpSettings settings() {
        return this.settings;
    }

    @Override
    public ActorRef manager() {
        return this.manager;
    }

    public ActorRef getManager() {
        return this.manager();
    }

    public BufferPool bufferPool() {
        return this.bufferPool;
    }

    public UdpConnectedExt(ExtendedActorSystem system) {
        this.settings = new Udp.UdpSettings(system.settings().config().getConfig("akka.io.udp-connected"));
        this.manager = system.systemActorOf(Props$.MODULE$.apply(UdpConnectedManager.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this})).withDispatcher(this.settings().ManagementDispatcher()).withDeploy(Deploy$.MODULE$.local()), "IO-UDP-CONN");
        this.bufferPool = new DirectByteBufferPool(this.settings().DirectBufferSize(), this.settings().MaxDirectBufferPoolSize());
    }
}

