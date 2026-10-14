/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.ApiMayChange;
import akka.annotation.DoNotInherit;
import akka.stream.snapshot.LogicSnapshot;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=2qAA\u0002\u0011\u0002G\u0005\"\u0002C\u0003\u0012\u0001\u0019\u0005!CA\nJ]R,'\u000f\u001d:fi\u0016\u00148K\\1qg\"|GO\u0003\u0002\u0005\u000b\u0005A1O\\1qg\"|GO\u0003\u0002\u0007\u000f\u000511\u000f\u001e:fC6T\u0011\u0001C\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001Y\u0001C\u0001\u0007\u0010\u001b\u0005i!\"\u0001\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Ai!AB!osJ+g-\u0001\u0004m_\u001eL7m]\u000b\u0002'A\u0019A#G\u000e\u000e\u0003UQ!AF\f\u0002\u0013%lW.\u001e;bE2,'B\u0001\r\u000e\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u00035U\u00111aU3r!\taR$D\u0001\u0004\u0013\tq2AA\u0007M_\u001eL7m\u00158baNDw\u000e^\u0015\u0004\u0001\u0001\u0012\u0013BA\u0011\u0004\u0005I\u0011VO\u001c8j]\u001eLe\u000e^3saJ,G/\u001a:\n\u0005\r\u001a!\u0001G+oS:LG/[1mSj,G-\u00138uKJ\u0004(/\u001a;fe\"\u0012\u0001!\n\t\u0003M%j\u0011a\n\u0006\u0003Q\u001d\t!\"\u00198o_R\fG/[8o\u0013\tQsE\u0001\u0007Ba&l\u0015-_\"iC:<W\r\u000b\u0002\u0001YA\u0011a%L\u0005\u0003]\u001d\u0012A\u0002R8O_RLe\u000e[3sSR\u0004")
@DoNotInherit
@ApiMayChange
public interface InterpreterSnapshot {
    public Seq<LogicSnapshot> logics();
}

