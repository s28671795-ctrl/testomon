/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function1;
import scala.collection.immutable.VectorImpl;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054Q\u0001C\u0005\u0002*AA\u0011B\t\u0001\u0003\u0002\u0003\u0006IaI\u001a\t\u0013]\u0002!Q1A\u0005\u0002%A\u0004\u0002C\u001d\u0001\u0005\u0003\u0005\u000b\u0011B\u0012\t\u0013i\u0002!Q1A\u0005\u0002%Y\u0004\u0002C \u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001f\t\u000b\u0001\u0003A\u0011A!\t\r\u0019\u0003AQC\u0005H\u0005%\u0011\u0015n\u001a,fGR|'O\u0003\u0002\u000b\u0017\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u00195\t!bY8mY\u0016\u001cG/[8o\u0015\u0005q\u0011!B:dC2\f7\u0001A\u000b\u0003#a\u0019\"\u0001\u0001\n\u0011\u0007M!b#D\u0001\n\u0013\t)\u0012B\u0001\u0006WK\u000e$xN]%na2\u0004\"a\u0006\r\r\u0001\u00111\u0011\u0004\u0001CC\u0002i\u0011\u0011!Q\t\u00037}\u0001\"\u0001H\u000f\u000e\u00035I!AH\u0007\u0003\u000f9{G\u000f[5oOB\u0011A\u0004I\u0005\u0003C5\u00111!\u00118z\u0003!y\u0006O]3gSb\f\u0004C\u0001\u00131\u001d\t)cF\u0004\u0002'[9\u0011q\u0005\f\b\u0003Q-j\u0011!\u000b\u0006\u0003U=\ta\u0001\u0010:p_Rt\u0014\"\u0001\b\n\u00051i\u0011B\u0001\u0006\f\u0013\ty\u0013\"\u0001\u0007WK\u000e$xN]%oY&tW-\u0003\u00022e\t!\u0011I\u001d:2\u0015\ty\u0013\"\u0003\u00025k\u00059\u0001O]3gSb\f\u0014B\u0001\u001c\n\u0005\u00191Vm\u0019;pe\u000691/\u001e4gSb\fT#A\u0012\u0002\u0011M,hMZ5yc\u0001\nq\u0001\\3oORD\u0007'F\u0001=!\taR(\u0003\u0002?\u001b\t\u0019\u0011J\u001c;\u0002\u00111,gn\u001a;ia\u0001\na\u0001P5oSRtD\u0003\u0002\"D\t\u0016\u00032a\u0005\u0001\u0017\u0011\u0015\u0011c\u00011\u0001$\u0011\u00159d\u00011\u0001$\u0011\u0015Qd\u00011\u0001=\u0003-1wN]3bG\"\u0014Vm\u001d;\u0016\u0005!\u0013FCA%M!\ta\"*\u0003\u0002L\u001b\t!QK\\5u\u0011\u0015iu\u00011\u0001O\u0003\u00051\u0007\u0003\u0002\u000fP-EK!\u0001U\u0007\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA\fS\t\u0015\u0019vA1\u0001\u001b\u0005\u0005)\u0016f\u0002\u0001V/f[Vl\u0018\u0006\u0003-&\tqAV3di>\u0014\b'\u0003\u0002Y\u0013\t9a+Z2u_J\u0014\u0014B\u0001.\n\u0005\u001d1Vm\u0019;peNJ!\u0001X\u0005\u0003\u000fY+7\r^8si%\u0011a,\u0003\u0002\b-\u0016\u001cGo\u001c:6\u0013\t\u0001\u0017BA\u0004WK\u000e$xN\u001d\u001c")
public abstract class BigVector<A>
extends VectorImpl<A> {
    private final Object[] suffix1;
    private final int length0;

    public Object[] suffix1() {
        return this.suffix1;
    }

    public int length0() {
        return this.length0;
    }

    /*
     * WARNING - void declaration
     */
    public final <U> void foreachRest(Function1<A, U> f) {
        int c = this.vectorSliceCount();
        for (int i = 1; i < c; ++i) {
            int foreachRec_i;
            void foreachRec_level;
            void foreachRec_a;
            int vectorSliceDim_c = c / 2;
            Object[] objectArray = this.vectorSlice(i);
            int n = vectorSliceDim_c + 1 - Math.abs(i - vectorSliceDim_c) - 1;
            VectorStatics$ foreachRec_this = VectorStatics$.MODULE$;
            int foreachRec_len = ((void)foreachRec_a).length;
            if (foreachRec_level == false) {
                for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                    f.apply(foreachRec_a[foreachRec_i]);
                }
            } else {
                void foreachRec_l = foreachRec_level - true;
                while (foreachRec_i < foreachRec_len) {
                    foreachRec_this.foreachRec((int)foreachRec_l, (Object[])foreachRec_a[foreachRec_i], f);
                    ++foreachRec_i;
                }
            }
            Object var5_5 = null;
            objectArray = null;
        }
    }

    public BigVector(Object[] _prefix1, Object[] suffix1, int length0) {
        this.suffix1 = suffix1;
        this.length0 = length0;
        super(_prefix1);
    }
}

