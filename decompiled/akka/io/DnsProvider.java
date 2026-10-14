/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.io.Dns;
import akka.io.SimpleDnsCache;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3q!\u0002\u0004\u0011\u0002\u0007\u00051\u0002C\u0003\u0013\u0001\u0011\u00051\u0003C\u0003\u0018\u0001\u0011\u0005\u0001\u0004C\u0003\u001e\u0001\u0019\u0005a\u0004C\u0003:\u0001\u0019\u0005!HA\u0006E]N\u0004&o\u001c<jI\u0016\u0014(BA\u0004\t\u0003\tIwNC\u0001\n\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001\u0001\u0004\t\u0003\u001bAi\u0011A\u0004\u0006\u0002\u001f\u0005)1oY1mC&\u0011\u0011C\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005!\u0002CA\u0007\u0016\u0013\t1bB\u0001\u0003V]&$\u0018!B2bG\",W#A\r\u0011\u0005iYR\"\u0001\u0004\n\u0005q1!a\u0001#og\u0006Q\u0011m\u0019;pe\u000ec\u0017m]:\u0016\u0003}\u0001$\u0001I\u0017\u0011\u0007\u0005B3F\u0004\u0002#MA\u00111ED\u0007\u0002I)\u0011QEC\u0001\u0007yI|w\u000e\u001e \n\u0005\u001dr\u0011A\u0002)sK\u0012,g-\u0003\u0002*U\t)1\t\\1tg*\u0011qE\u0004\t\u0003Y5b\u0001\u0001B\u0005/\u0007\u0005\u0005\t\u0011!B\u0001_\t\u0019q\fJ\u0019\u0012\u0005A\u001a\u0004CA\u00072\u0013\t\u0011dBA\u0004O_RD\u0017N\\4\u0011\u0005Q:T\"A\u001b\u000b\u0005YB\u0011!B1di>\u0014\u0018B\u0001\u001d6\u0005\u0015\t5\r^8s\u00031i\u0017M\\1hKJ\u001cE.Y:t+\u0005Y\u0004G\u0001\u001f?!\r\t\u0003&\u0010\t\u0003Yy\"\u0011b\u0010\u0003\u0002\u0002\u0003\u0005)\u0011A\u0018\u0003\u0007}##\u0007\u000b\u0004\u0001\u0003\u0012+u\t\u0013\t\u0003\u001b\tK!a\u0011\b\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003\u0019\u000bAj\u0014<feJLG-\u001b8hAQDW\r\t#O'\u0002JW\u000e\u001d7f[\u0016tG/\u0019;j_:\u0004s/\u001b7mA\t,\u0007E]3n_Z,G\rI5oA\u0019,H/\u001e:fAY,'o]5p]N\u0004sN\u001a\u0011BW.\f\u0017!B:j]\u000e,\u0017%A%\u0002\u000bIrcG\f\u0019")
public interface DnsProvider {
    public static /* synthetic */ Dns cache$(DnsProvider $this) {
        return $this.cache();
    }

    default public Dns cache() {
        return new SimpleDnsCache();
    }

    public Class<? extends Actor> actorClass();

    public Class<? extends Actor> managerClass();

    public static void $init$(DnsProvider $this) {
    }
}

