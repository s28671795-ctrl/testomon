/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.ApiMayChange;
import akka.annotation.DoNotInherit;
import akka.stream.snapshot.ConnectionSnapshot;
import akka.stream.snapshot.InterpreterSnapshot;
import akka.stream.snapshot.LogicSnapshot;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2q!\u0002\u0004\u0011\u0002G\u0005R\u0002C\u0003\u0019\u0001\u0019\u0005\u0011\u0004C\u0003&\u0001\u0019\u0005a\u0005C\u0003,\u0001\u0019\u0005A\u0006C\u00031\u0001\u0019\u0005\u0011D\u0001\nSk:t\u0017N\\4J]R,'\u000f\u001d:fi\u0016\u0014(BA\u0004\t\u0003!\u0019h.\u00199tQ>$(BA\u0005\u000b\u0003\u0019\u0019HO]3b[*\t1\"\u0001\u0003bW.\f7\u0001A\n\u0004\u00019!\u0002CA\b\u0013\u001b\u0005\u0001\"\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0001\"AB!osJ+g\r\u0005\u0002\u0016-5\ta!\u0003\u0002\u0018\r\t\u0019\u0012J\u001c;feB\u0014X\r^3s':\f\u0007o\u001d5pi\u00061An\\4jGN,\u0012A\u0007\t\u00047\u0001\u0012S\"\u0001\u000f\u000b\u0005uq\u0012!C5n[V$\u0018M\u00197f\u0015\ty\u0002#\u0001\u0006d_2dWm\u0019;j_:L!!\t\u000f\u0003\u0007M+\u0017\u000f\u0005\u0002\u0016G%\u0011AE\u0002\u0002\u000e\u0019><\u0017nY*oCB\u001c\bn\u001c;\u0002\u0017\r|gN\\3di&|gn]\u000b\u0002OA\u00191\u0004\t\u0015\u0011\u0005UI\u0013B\u0001\u0016\u0007\u0005I\u0019uN\u001c8fGRLwN\\*oCB\u001c\bn\u001c;\u0002%I,hN\\5oO2{w-[2t\u0007>,h\u000e^\u000b\u0002[A\u0011qBL\u0005\u0003_A\u00111!\u00138u\u00035\u0019Ho\u001c9qK\u0012dunZ5dg&\u0012\u0001AM\u0005\u0003g\u0019\u0011aCU;o]&tw-\u00138uKJ\u0004(/\u001a;fe&k\u0007\u000f\u001c\u0015\u0003\u0001U\u0002\"AN\u001d\u000e\u0003]R!\u0001\u000f\u0006\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002;o\ta\u0011\t]5NCf\u001c\u0005.\u00198hK\"\u0012\u0001\u0001\u0010\t\u0003muJ!AP\u001c\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;")
@DoNotInherit
@ApiMayChange
public interface RunningInterpreter
extends InterpreterSnapshot {
    @Override
    public Seq<LogicSnapshot> logics();

    public Seq<ConnectionSnapshot> connections();

    public int runningLogicsCount();

    public Seq<LogicSnapshot> stoppedLogics();
}

