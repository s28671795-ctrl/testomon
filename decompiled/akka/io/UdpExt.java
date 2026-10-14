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
import akka.io.UdpManager;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u00053AAC\u0006\u0001!!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003'\u0001\u0011\u0005q\u0005C\u0004+\u0001\t\u0007I\u0011A\u0016\t\rM\u0002\u0001\u0015!\u0003-\u0011\u001d!\u0004A1A\u0005\u0002UBa!\u000f\u0001!\u0002\u00131\u0004\"\u0002\u001e\u0001\t\u0003)\u0004\u0002C\u001e\u0001\u0005\u0004%\ta\u0003\u001f\t\r\u0001\u0003\u0001\u0015!\u0003>\u0005\u0019)F\r]#yi*\u0011A\"D\u0001\u0003S>T\u0011AD\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001\tr\u0003\u0005\u0002\u0013+5\t1CC\u0001\u0015\u0003\u0015\u00198-\u00197b\u0013\t12C\u0001\u0004B]f\u0014VM\u001a\t\u00031qq!!\u0007\u000e\u000e\u0003-I!aG\u0006\u0002\u0005%{\u0015BA\u000f\u001f\u0005%)\u0005\u0010^3og&|gN\u0003\u0002\u001c\u0017\u000511/_:uK6\u0004\"!\t\u0013\u000e\u0003\tR!aI\u0007\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u0015\u0012#aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017A\u0002\u001fj]&$h\b\u0006\u0002)SA\u0011\u0011\u0004\u0001\u0005\u0006?\t\u0001\r\u0001I\u0001\tg\u0016$H/\u001b8hgV\tA\u0006\u0005\u0002.a9\u0011\u0011DL\u0005\u0003_-\t1!\u00163q\u0013\t\t$GA\u0006VIB\u001cV\r\u001e;j]\u001e\u001c(BA\u0018\f\u0003%\u0019X\r\u001e;j]\u001e\u001c\b%A\u0004nC:\fw-\u001a:\u0016\u0003Y\u0002\"!I\u001c\n\u0005a\u0012#\u0001C!di>\u0014(+\u001a4\u0002\u00115\fg.Y4fe\u0002\n!bZ3u\u001b\u0006t\u0017mZ3s\u0003)\u0011WO\u001a4feB{w\u000e\\\u000b\u0002{A\u0011\u0011DP\u0005\u0003\u007f-\u0011!BQ;gM\u0016\u0014\bk\\8m\u0003-\u0011WO\u001a4feB{w\u000e\u001c\u0011")
public class UdpExt
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

    public UdpExt(ExtendedActorSystem system) {
        this.settings = new Udp.UdpSettings(system.settings().config().getConfig("akka.io.udp"));
        this.manager = system.systemActorOf(Props$.MODULE$.apply(UdpManager.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this})).withDispatcher(this.settings().ManagementDispatcher()).withDeploy(Deploy$.MODULE$.local()), "IO-UDP-FF");
        this.bufferPool = new DirectByteBufferPool(this.settings().DirectBufferSize(), this.settings().MaxDirectBufferPoolSize());
    }
}

