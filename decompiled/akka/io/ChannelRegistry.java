/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import java.nio.channels.SelectableChannel;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592\u0001BA\u0002\u0011\u0002G\u00051a\u0002\u0005\u0006\u001d\u00011\t\u0001\u0005\u0002\u0010\u0007\"\fgN\\3m%\u0016<\u0017n\u001d;ss*\u0011A!B\u0001\u0003S>T\u0011AB\u0001\u0005C.\\\u0017m\u0005\u0002\u0001\u0011A\u0011\u0011\u0002D\u0007\u0002\u0015)\t1\"A\u0003tG\u0006d\u0017-\u0003\u0002\u000e\u0015\t1\u0011I\\=SK\u001a\f\u0001B]3hSN$XM]\u0002\u0001)\r\tR$\u000b\u000b\u0003%U\u0001\"!C\n\n\u0005QQ!\u0001B+oSRDQAF\u0001A\u0004]\tAb\u00195b]:,G.Q2u_J\u0004\"\u0001G\u000e\u000e\u0003eQ!AG\u0003\u0002\u000b\u0005\u001cGo\u001c:\n\u0005qI\"\u0001C!di>\u0014(+\u001a4\t\u000by\t\u0001\u0019A\u0010\u0002\u000f\rD\u0017M\u001c8fYB\u0011\u0001eJ\u0007\u0002C)\u0011!eI\u0001\tG\"\fgN\\3mg*\u0011A%J\u0001\u0004]&|'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\u0005\u0012\u0011cU3mK\u000e$\u0018M\u00197f\u0007\"\fgN\\3m\u0011\u0015Q\u0013\u00011\u0001,\u0003)Ig.\u001b;jC2|\u0005o\u001d\t\u0003\u00131J!!\f\u0006\u0003\u0007%sG\u000f")
public interface ChannelRegistry {
    public void register(SelectableChannel var1, int var2, ActorRef var3);
}

