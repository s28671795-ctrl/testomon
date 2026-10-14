/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005A3Q!\u0003\u0006\u0002\u00065AQA\u0005\u0001\u0005\u0002MAQ!\u0006\u0001\u0005BY9QA\t\u0006\t\u0002\r2Q!\u0003\u0006\t\u0002\u0011BQA\u0005\u0003\u0005\u0002-BQ\u0001\f\u0003\u0005\u00025BQA\u000e\u0003\u0005\u0002]BQ!\u0011\u0003\u0005B\t\u0013A!\u00168ji*\t1\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001q\u0001CA\b\u0011\u001b\u0005Q\u0011BA\t\u000b\u0005\u0019\te.\u001f,bY\u00061A(\u001b8jiz\"\u0012\u0001\u0006\t\u0003\u001f\u0001\t\u0001bZ3u\u00072\f7o\u001d\u000b\u0002/A\u0019\u0001d\b\u000b\u000f\u0005ei\u0002C\u0001\u000e\u000b\u001b\u0005Y\"B\u0001\u000f\r\u0003\u0019a$o\\8u}%\u0011aDC\u0001\u0007!J,G-\u001a4\n\u0005\u0001\n#!B\"mCN\u001c(B\u0001\u0010\u000b\u0003\u0011)f.\u001b;\u0011\u0005=!1c\u0001\u0003&QA\u0011qBJ\u0005\u0003O)\u0011a!\u00118z%\u00164\u0007CA\b*\u0013\tQ#BA\bB]f4\u0016\r\\\"p[B\fg.[8o)\u0005\u0019\u0013a\u00012pqR\u0011a\u0006\u000e\t\u0003_Ij\u0011\u0001\r\u0006\u0003c)\tqA];oi&lW-\u0003\u00024a\tI!i\u001c=fIVs\u0017\u000e\u001e\u0005\u0006k\u0019\u0001\r\u0001F\u0001\u0002q\u0006)QO\u001c2pqR\u0011A\u0003\u000f\u0005\u0006k\u001d\u0001\r!\u000f\t\u0003u}j\u0011a\u000f\u0006\u0003yu\nA\u0001\\1oO*\ta(\u0001\u0003kCZ\f\u0017B\u0001!<\u0005\u0019y%M[3di\u0006AAo\\*ue&tw\rF\u0001D!\tQD)\u0003\u0002Fw\t11\u000b\u001e:j]\u001eD3\u0001B$N!\tA5*D\u0001J\u0015\tQ%\"\u0001\u0006b]:|G/\u0019;j_:L!\u0001T%\u0003\u001f\r|W\u000e]5mKRKW.Z(oYf\f\u0013AT\u0001WAVs\u0017\u000e\u001e1!G>l\u0007/\u00198j_:\u0004sN\u00196fGR\u0004\u0013n\u001d\u0011o_R\u0004\u0013\r\u001c7po\u0016$\u0007%\u001b8!g>,(oY3<A%t7\u000f^3bI2\u0002So]3!A\"J\u0003\r\t4pe\u0002\"\b.\u001a\u0011v]&$\bE^1mk\u0016D3aA$N\u0001")
public abstract class Unit {
    public static String toString() {
        return "object scala.Unit";
    }

    public static void unbox(Object x) {
        BoxedUnit cfr_ignored_0 = (BoxedUnit)x;
    }

    public static BoxedUnit box(BoxedUnit x) {
        return BoxedUnit.UNIT;
    }
}

