/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.ApiMayChange;
import akka.annotation.DoNotInherit;
import akka.stream.snapshot.RunningInterpreter;
import akka.stream.snapshot.UninitializedInterpreter;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2qa\u0001\u0003\u0011\u0002G\u00052\u0002C\u0003\u0013\u0001\u0019\u00051\u0003C\u0003%\u0001\u0019\u0005QE\u0001\bTiJ,\u0017-\\*oCB\u001c\bn\u001c;\u000b\u0005\u00151\u0011\u0001C:oCB\u001c\bn\u001c;\u000b\u0005\u001dA\u0011AB:ue\u0016\fWNC\u0001\n\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001\u0001\u0004\t\u0003\u001bAi\u0011A\u0004\u0006\u0002\u001f\u0005)1oY1mC&\u0011\u0011C\u0004\u0002\u0007\u0003:L(+\u001a4\u0002%\u0005\u001cG/\u001b<f\u0013:$XM\u001d9sKR,'o]\u000b\u0002)A\u0019Q#\b\u0011\u000f\u0005YYbBA\f\u001b\u001b\u0005A\"BA\r\u000b\u0003\u0019a$o\\8u}%\tq\"\u0003\u0002\u001d\u001d\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u0010 \u0005\r\u0019V-\u001d\u0006\u000399\u0001\"!\t\u0012\u000e\u0003\u0011I!a\t\u0003\u0003%I+hN\\5oO&sG/\u001a:qe\u0016$XM]\u0001\n]\u0016<8\u000b[3mYN,\u0012A\n\t\u0004+u9\u0003CA\u0011)\u0013\tICA\u0001\rV]&t\u0017\u000e^5bY&TX\rZ%oi\u0016\u0014\bO]3uKJL#\u0001A\u0016\n\u00051\"!AE*ue\u0016\fWn\u00158baNDw\u000e^%na2D#\u0001\u0001\u0018\u0011\u0005=\u0012T\"\u0001\u0019\u000b\u0005EB\u0011AC1o]>$\u0018\r^5p]&\u00111\u0007\r\u0002\r\u0003BLW*Y=DQ\u0006tw-\u001a\u0015\u0003\u0001U\u0002\"a\f\u001c\n\u0005]\u0002$\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
@ApiMayChange
public interface StreamSnapshot {
    public Seq<RunningInterpreter> activeInterpreters();

    public Seq<UninitializedInterpreter> newShells();
}

