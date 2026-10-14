/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.DoNotInherit;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d3\u0001BA\u0002\u0011\u0002G\u0005q!\u0003\u0005\u0006#\u00011\ta\u0005\u0002\u0006!\"\f7/\u001a\u0006\u0003\t\u0015\tA![7qY*\u0011aaB\u0001\u0007gR\u0014X-Y7\u000b\u0003!\tA!Y6lCV\u0011!BG\n\u0003\u0001-\u0001\"\u0001D\b\u000e\u00035Q\u0011AD\u0001\u0006g\u000e\fG.Y\u0005\u0003!5\u0011a!\u00118z%\u00164\u0017!B1qa2L8\u0001\u0001\u000b\u0006)\rJcf\r\t\u0004+YAR\"A\u0002\n\u0005]\u0019!a\u0003)iCN,\u0017j\u001d7b]\u0012\u0004\"!\u0007\u000e\r\u0001\u0011)1\u0004\u0001b\u00019\t\tQ*\u0005\u0002\u001eAA\u0011ABH\u0005\u0003?5\u0011qAT8uQ&tw\r\u0005\u0002\rC%\u0011!%\u0004\u0002\u0004\u0003:L\b\"\u0002\u0013\u0002\u0001\u0004)\u0013\u0001C:fiRLgnZ:\u0011\u0005\u0019:S\"A\u0003\n\u0005!*!!G!di>\u0014X*\u0019;fe&\fG.\u001b>feN+G\u000f^5oONDQAK\u0001A\u0002-\n1#\u001a4gK\u000e$\u0018N^3BiR\u0014\u0018NY;uKN\u0004\"A\n\u0017\n\u00055*!AC!uiJL'-\u001e;fg\")q&\u0001a\u0001a\u0005aQ.\u0019;fe&\fG.\u001b>feB\u0011Q#M\u0005\u0003e\r\u0011Q\u0004\u00155bg\u0016$g)^:j]\u001e\f5\r^8s\u001b\u0006$XM]5bY&TXM\u001d\u0005\u0006i\u0005\u0001\r!N\u0001\u000bSNd\u0017M\u001c3OC6,\u0007C\u0001\u001c>\u001d\t94\b\u0005\u00029\u001b5\t\u0011H\u0003\u0002;%\u00051AH]8pizJ!\u0001P\u0007\u0002\rA\u0013X\rZ3g\u0013\tqtH\u0001\u0004TiJLgn\u001a\u0006\u0003y5A#\u0001A!\u0011\u0005\t+U\"A\"\u000b\u0005\u0011;\u0011AC1o]>$\u0018\r^5p]&\u0011ai\u0011\u0002\r\t>tu\u000e^%oQ\u0016\u0014\u0018\u000e\u001e")
@DoNotInherit
public interface Phase<M> {
    public PhaseIsland<M> apply(ActorMaterializerSettings var1, Attributes var2, PhasedFusingActorMaterializer var3, String var4);
}

