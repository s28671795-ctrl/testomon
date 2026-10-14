/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime.java8;

import java.io.Serializable;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005q2q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u00030\u0001\u0011\u0005\u0003G\u0001\nK\rVt7\r^5p]F\"Sn\u0019$GIM\u0004(B\u0001\u0004\b\u0003\u0015Q\u0017M^19\u0015\tA\u0011\"A\u0004sk:$\u0018.\\3\u000b\u0003)\tQa]2bY\u0006\u001c\u0001a\u0005\u0003\u0001\u001bE9\u0002C\u0001\b\u0010\u001b\u0005I\u0011B\u0001\t\n\u0005\u0019\te.\u001f*fMB!aB\u0005\u000b\u0015\u0013\t\u0019\u0012BA\u0005Gk:\u001cG/[8ocA\u0011a\"F\u0005\u0003-%\u00111!\u00118z!\tA\u0002E\u0004\u0002\u001a=9\u0011!$H\u0007\u00027)\u0011AdC\u0001\u0007yI|w\u000e\u001e \n\u0003)I!aH\u0005\u0002\u000fA\f7m[1hK&\u0011\u0011E\t\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003?%\ta\u0001J5oSR$C#A\u0013\u0011\u000591\u0013BA\u0014\n\u0005\u0011)f.\u001b;\u0002\u001b\u0005\u0004\b\u000f\\=%[\u000e4e\tJ:q)\tQS\u0006\u0005\u0002\u000fW%\u0011A&\u0003\u0002\u0006\r2|\u0017\r\u001e\u0005\u0006]\t\u0001\rAK\u0001\u0003mF\nQ!\u00199qYf$\"\u0001F\u0019\t\u000bI\u001a\u0001\u0019\u0001\u000b\u0002\u0003QD#\u0001\u0001\u001b\u0011\u0005URT\"\u0001\u001c\u000b\u0005]B\u0014\u0001\u00027b]\u001eT\u0011!O\u0001\u0005U\u00064\u0018-\u0003\u0002<m\t\u0019b)\u001e8di&|g.\u00197J]R,'OZ1dK\u0002")
public interface JFunction1$mcFF$sp
extends Function1<Object, Object>,
Serializable {
    @Override
    public float apply$mcFF$sp(float var1);

    public static /* synthetic */ Object apply$(JFunction1$mcFF$sp $this, Object t2) {
        return $this.apply(t2);
    }

    @Override
    default public Object apply(Object t2) {
        return Float.valueOf(this.apply$mcFF$sp(BoxesRunTime.unboxToFloat(t2)));
    }

    public static void $init$(JFunction1$mcFF$sp $this) {
    }
}

