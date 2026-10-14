/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Specializable;
import scala.Specializable$;
import scala.annotation.Annotation;
import scala.annotation.StaticAnnotation;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2A!\u0002\u0004\u0003\u0013!A1\u0003\u0001B\u0001B\u0003%A\u0003C\u0003\u001d\u0001\u0011\u0005Q\u0004C\u0003\u001d\u0001\u0011\u0005\u0001\u0005C\u0003\u001d\u0001\u0011\u0005\u0011FA\u0006ta\u0016\u001c\u0017.\u00197ju\u0016$'\"A\u0004\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001A\u0003\t\u0011\u0005-qQ\"\u0001\u0007\u000b\u000551\u0011AC1o]>$\u0018\r^5p]&\u0011q\u0002\u0004\u0002\u000b\u0003:tw\u000e^1uS>t\u0007CA\u0006\u0012\u0013\t\u0011BB\u0001\tTi\u0006$\u0018nY!o]>$\u0018\r^5p]\u0006)qM]8vaB\u0011Q#\u0007\b\u0003-]i\u0011AB\u0005\u00031\u0019\tQb\u00159fG&\fG.\u001b>bE2,\u0017B\u0001\u000e\u001c\u0005A\u0019\u0006/Z2jC2L'0\u001a3He>,\bO\u0003\u0002\u0019\r\u00051A(\u001b8jiz\"\"AH\u0010\u0011\u0005Y\u0001\u0001\"B\n\u0003\u0001\u0004!BC\u0001\u0010\"\u0011\u0015\u00113\u00011\u0001$\u0003\u0015!\u0018\u0010]3t!\r1BEJ\u0005\u0003K\u0019\u0011!\u0002\u0010:fa\u0016\fG/\u001a3?!\t1r%\u0003\u0002)\r\ti1\u000b]3dS\u0006d\u0017N_1cY\u0016$\u0012A\b")
public final class specialized
extends Annotation
implements StaticAnnotation {
    public specialized(Specializable.SpecializedGroup group) {
    }

    public specialized(Seq<Specializable> types) {
        this(new Specializable.Group(types.toList()));
    }

    public specialized() {
        this(Specializable$.MODULE$.Primitives());
    }
}

