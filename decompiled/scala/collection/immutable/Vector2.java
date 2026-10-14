/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Function1;
import scala.MatchError;
import scala.collection.IterableOnce;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector3;
import scala.collection.immutable.VectorSliceBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dg\u0001\u0002\u000f\u001e\r\u0011B\u0011B\u000e\u0001\u0003\u0002\u0003\u0006IaN$\t\u0013-\u0003!Q1A\u0005\u0002ua\u0005\u0002\u0003)\u0001\u0005\u0003\u0005\u000b\u0011B'\t\u0013E\u0003!Q1A\u0005\u0002u\u0011\u0006\u0002\u0003,\u0001\u0005\u0003\u0005\u000b\u0011B*\t\u0013]\u0003!\u0011!Q\u0001\n]B\u0006\"\u0003.\u0001\u0005\u0003\u0005\u000b\u0011B'\\\u0011\u0015i\u0006\u0001\"\u0001_\u0011\u0019)\u0007\u0001)C\u0005M\"9\u0011\u000fAI\u0001\n\u0013\u0011\bbB?\u0001#\u0003%IA \u0005\n\u0003\u0003\u0001\u0011\u0013!C\u0005\u0003\u0007A\u0001\"a\u0002\u0001#\u0003%IA\u001d\u0005\t\u0003\u0013\u0001\u0011\u0013!C\u0005}\"9\u00111\u0002\u0001\u0005\u0002\u00055\u0001bBA\u000b\u0001\u0011\u0005\u0013q\u0003\u0005\b\u0003W\u0001A\u0011IA\u0017\u0011\u001d\tI\u0004\u0001C!\u0003wAq!a\u0012\u0001\t\u0003\nI\u0005\u0003\u0005\u0002^\u0001\u0001K\u0011CA0\u0011\u001d\tY\u0007\u0001C!\u0003[Bq!a\u001c\u0001\t\u0003\ni\u0007C\u0004\u0002r\u0001!\t\"\b'\t\u0011\u0005M\u0004\u0001\"\u0005\u001e\u0003kB\u0001\"!%\u0001\t#i\u00121\u0013\u0005\t\u0003/\u0003\u0001\u0015\"\u0015\u0002\u001a\"A\u00111\u0017\u0001!\n#\n)LA\u0004WK\u000e$xN\u001d\u001a\u000b\u0005yy\u0012!C5n[V$\u0018M\u00197f\u0015\t\u0001\u0013%\u0001\u0006d_2dWm\u0019;j_:T\u0011AI\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t)Cf\u0005\u0002\u0001MA\u0019q\u0005\u000b\u0016\u000e\u0003uI!!K\u000f\u0003\u0013\tKwMV3di>\u0014\bCA\u0016-\u0019\u0001!a!\f\u0001\u0005\u0006\u0004q#!A!\u0012\u0005=\u001a\u0004C\u0001\u00192\u001b\u0005\t\u0013B\u0001\u001a\"\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\r\u001b\n\u0005U\n#aA!os\u0006Aq\f\u001d:fM&D\u0018\u0007\u0005\u00029\t:\u0011\u0011H\u0011\b\u0003u\u0005s!a\u000f!\u000f\u0005qzT\"A\u001f\u000b\u0005y\u001a\u0013A\u0002\u001fs_>$h(C\u0001#\u0013\t\u0001\u0013%\u0003\u0002\u001f?%\u00111)H\u0001\r-\u0016\u001cGo\u001c:J]2Lg.Z\u0005\u0003\u000b\u001a\u0013A!\u0011:sc)\u00111)H\u0005\u0003\u0011&\u000bq\u0001\u001d:fM&D\u0018'\u0003\u0002K;\t1a+Z2u_J\fA\u0001\\3ocU\tQ\n\u0005\u00021\u001d&\u0011q*\t\u0002\u0004\u0013:$\u0018!\u00027f]F\u0002\u0013!\u00023bi\u0006\u0014T#A*\u0011\u0005a\"\u0016BA+G\u0005\u0011\t%O\u001d\u001a\u0002\r\u0011\fG/\u0019\u001a!\u0003!y6/\u001e4gSb\f\u0014BA-)\u0003\u001d\u0019XO\u001a4jqF\n\u0001b\u00187f]\u001e$\b\u000eM\u0005\u00039\"\nq\u0001\\3oORD\u0007'\u0001\u0004=S:LGO\u0010\u000b\u0007?\u0002\f'm\u00193\u0011\u0007\u001d\u0002!\u0006C\u00037\u0011\u0001\u0007q\u0007C\u0003L\u0011\u0001\u0007Q\nC\u0003R\u0011\u0001\u00071\u000bC\u0003X\u0011\u0001\u0007q\u0007C\u0003[\u0011\u0001\u0007Q*\u0001\u0003d_BLHCB4iS*\\G\u000eE\u0002(\u0001=Bq\u0001S\u0005\u0011\u0002\u0003\u0007q\u0007C\u0004L\u0013A\u0005\t\u0019A'\t\u000fEK\u0001\u0013!a\u0001'\"9\u0011,\u0003I\u0001\u0002\u00049\u0004b\u0002/\n!\u0003\u0005\r!\u0014\u0015\u0003\u00139\u0004\"\u0001M8\n\u0005A\f#AB5oY&tW-\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003MT#a\u000e;,\u0003U\u0004\"A^>\u000e\u0003]T!\u0001_=\u0002\u0013Ut7\r[3dW\u0016$'B\u0001>\"\u0003)\tgN\\8uCRLwN\\\u0005\u0003y^\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\u0012a \u0016\u0003\u001bR\fabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002\u0006)\u00121\u000b^\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU\nQ!\u00199qYf$2AKA\b\u0011\u0019\t\tb\u0004a\u0001\u001b\u0006)\u0011N\u001c3fq\"\u0012qB\\\u0001\bkB$\u0017\r^3e+\u0011\tI\"a\b\u0015\r\u0005m\u0011QEA\u0014!\u00119\u0013*!\b\u0011\u0007-\ny\u0002B\u0004\u0002\"A\u0011\r!a\t\u0003\u0003\t\u000b\"AK\u001a\t\r\u0005E\u0001\u00031\u0001N\u0011\u001d\tI\u0003\u0005a\u0001\u0003;\tA!\u001a7f[\u0006A\u0011\r\u001d9f]\u0012,G-\u0006\u0003\u00020\u0005UB\u0003BA\u0019\u0003o\u0001BaJ%\u00024A\u00191&!\u000e\u0005\u000f\u0005\u0005\u0012C1\u0001\u0002$!9\u0011\u0011F\tA\u0002\u0005M\u0012!\u00039sKB,g\u000eZ3e+\u0011\ti$a\u0011\u0015\t\u0005}\u0012Q\t\t\u0005O%\u000b\t\u0005E\u0002,\u0003\u0007\"q!!\t\u0013\u0005\u0004\t\u0019\u0003C\u0004\u0002*I\u0001\r!!\u0011\u0002\u00075\f\u0007/\u0006\u0003\u0002L\u0005EC\u0003BA'\u0003'\u0002BaJ%\u0002PA\u00191&!\u0015\u0005\r\u0005\u00052C1\u0001/\u0011\u001d\t)f\u0005a\u0001\u0003/\n\u0011A\u001a\t\u0007a\u0005e#&a\u0014\n\u0007\u0005m\u0013EA\u0005Gk:\u001cG/[8oc\u000511\u000f\\5dKB\"b!!\u0019\u0002d\u0005\u001d\u0004cA\u0014JU!1\u0011Q\r\u000bA\u00025\u000b!\u0001\\8\t\r\u0005%D\u00031\u0001N\u0003\tA\u0017.\u0001\u0003uC&dWCAA1\u0003\u0011Ig.\u001b;\u0002!Y,7\r^8s'2L7-Z\"pk:$\u0018a\u0003<fGR|'o\u00157jG\u0016$B!a\u001e\u0002\u000eB\"\u0011\u0011PAA!\u0015\u0001\u00141PA@\u0013\r\ti(\t\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0004W\u0005\u0005EaCAB1\u0005\u0005\t\u0011!B\u0001\u0003\u000b\u0013Aa\u0018\u00132gE\u0019q&a\"\u0011\u0007A\nI)C\u0002\u0002\f\u0006\u0012a!\u00118z%\u00164\u0007BBAH1\u0001\u0007Q*A\u0002jIb\fqC^3di>\u00148\u000b\\5dKB\u0013XMZ5y\u0019\u0016tw\r\u001e5\u0015\u00075\u000b)\n\u0003\u0004\u0002\u0010f\u0001\r!T\u0001\u000eaJ,\u0007/\u001a8eK\u0012\fE\u000e\u001c\u0019\u0016\t\u0005m\u0015\u0011\u0015\u000b\u0007\u0003;\u000b\u0019+a,\u0011\t\u001dJ\u0015q\u0014\t\u0004W\u0005\u0005FaBA\u00115\t\u0007\u00111\u0005\u0005\b\u0003KS\u0002\u0019AAT\u0003\u0019\u0001(/\u001a4jqB1\u0011\u0011VAV\u0003?k\u0011aH\u0005\u0004\u0003[{\"\u0001D%uKJ\f'\r\\3P]\u000e,\u0007BBAY5\u0001\u0007Q*A\u0001l\u00031\t\u0007\u000f]3oI\u0016$\u0017\t\u001c71+\u0011\t9,!0\u0015\r\u0005e\u0016qXAc!\u00119\u0013*a/\u0011\u0007-\ni\fB\u0004\u0002\"m\u0011\r!a\t\t\u000f\u0005\u00057\u00041\u0001\u0002D\u000611/\u001e4gSb\u0004b!!+\u0002,\u0006m\u0006BBAY7\u0001\u0007Q\n")
public final class Vector2<A>
extends BigVector<A> {
    private final int len1;
    private final Object[][] data2;

    public int len1() {
        return this.len1;
    }

    public Object[][] data2() {
        return this.data2;
    }

    private Vector2<Nothing$> copy(Object[] prefix1, int len1, Object[][] data2, Object[] suffix1, int length0) {
        return new Vector2<Nothing$>(prefix1, len1, data2, suffix1, length0);
    }

    private Object[] copy$default$1() {
        return this.prefix1();
    }

    private int copy$default$2() {
        return this.len1();
    }

    private Object[][] copy$default$3() {
        return this.data2();
    }

    private Object[] copy$default$4() {
        return this.suffix1();
    }

    private int copy$default$5() {
        return this.length0();
    }

    @Override
    public A apply(int index) {
        if (index >= 0 && index < this.length0()) {
            int io = index - this.len1();
            if (io >= 0) {
                int i2 = io >>> 5;
                int i1 = io & 0x1F;
                if (i2 < this.data2().length) {
                    return (A)this.data2()[i2][i1];
                }
                return (A)this.suffix1()[io & 0x1F];
            }
            return (A)this.prefix1()[index];
        }
        throw this.ioob(index);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> updated(int index, B elem) {
        if (index >= 0 && index < this.length0()) {
            void copy_length0;
            void copy_suffix1;
            void copy_data2;
            void copy_len1;
            if (index >= this.len1()) {
                int io = index - this.len1();
                int i2 = io >>> 5;
                int i1 = io & 0x1F;
                if (i2 < this.data2().length) {
                    Object[][] copyUpdate_a2 = this.data2();
                    Object[][] copyUpdate_a2c = (Object[][])copyUpdate_a2.clone();
                    Object[] copyUpdate_copyUpdate_a1c = (Object[])copyUpdate_a2c[i2].clone();
                    copyUpdate_copyUpdate_a1c[i1] = elem;
                    Object var18_8 = null;
                    copyUpdate_a2c[i2] = copyUpdate_copyUpdate_a1c;
                    Object var16_6 = null;
                    Object var17_7 = null;
                    Object[][] x$1 = copyUpdate_a2c;
                    Object[] x$2 = this.prefix1();
                    int x$3 = this.len1();
                    Object[] x$4 = this.suffix1();
                    int x$5 = this.length0();
                    return new Vector2<A>(x$2, x$3, x$1, x$4, x$5);
                }
                Object[] copyUpdate_a1 = this.suffix1();
                Object[] copyUpdate_a1c = (Object[])copyUpdate_a1.clone();
                copyUpdate_a1c[i1] = elem;
                Object var19_14 = null;
                Object var20_15 = null;
                Object[] x$6 = copyUpdate_a1c;
                Object[] x$7 = this.prefix1();
                int x$8 = this.len1();
                Object[][] x$9 = this.data2();
                int x$10 = this.length0();
                return new Vector2<A>(x$7, x$8, x$9, x$6, x$10);
            }
            Object[] copyUpdate_a1c = (Object[])this.prefix1().clone();
            copyUpdate_a1c[index] = elem;
            Object var21_21 = null;
            int n = this.length0();
            Object[] objectArray = this.suffix1();
            Object[][] objectArray2 = this.data2();
            int n2 = this.len1();
            Object[] copy_prefix1 = copyUpdate_a1c;
            return new Vector2<A>(copy_prefix1, (int)copy_len1, (Object[][])copy_data2, (Object[])copy_suffix1, (int)copy_length0);
        }
        throw this.ioob(index);
    }

    @Override
    public <B> Vector<B> appended(B elem) {
        if (this.suffix1().length < 32) {
            Object[] x$1 = VectorStatics$.MODULE$.copyAppend1(this.suffix1(), elem);
            int x$2 = this.length0() + 1;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.data2();
            return new Vector2<A>(x$3, x$4, x$5, x$1, x$2);
        }
        if (this.data2().length < 30) {
            Object[][] x$6 = VectorStatics$.MODULE$.copyAppend((T[])this.data2(), this.suffix1());
            Object[] wrap1_a = new Object[]{elem};
            Object var12_8 = null;
            Object[] x$7 = wrap1_a;
            int x$8 = this.length0() + 1;
            Object[] x$9 = this.prefix1();
            int x$10 = this.len1();
            return new Vector2<A>(x$9, x$10, x$6, x$7, x$8);
        }
        Object[] wrap2_x = this.suffix1();
        Object[][] wrap2_a = new Object[][]{wrap2_x};
        Object var13_13 = null;
        Object var14_14 = null;
        Object[] wrap1_a = new Object[]{elem};
        Object var15_15 = null;
        return new Vector3(this.prefix1(), this.len1(), this.data2(), 960 + this.len1(), VectorStatics$.MODULE$.empty3(), wrap2_a, wrap1_a, this.length0() + 1);
    }

    @Override
    public <B> Vector<B> prepended(B elem) {
        if (this.len1() < 32) {
            Object[] x$1 = VectorStatics$.MODULE$.copyPrepend1(elem, this.prefix1());
            int x$2 = this.len1() + 1;
            int x$3 = this.length0() + 1;
            Object[][] x$4 = this.data2();
            Object[] x$5 = this.suffix1();
            return new Vector2<A>(x$1, x$2, x$4, x$5, x$3);
        }
        if (this.data2().length < 30) {
            Object[] wrap1_a = new Object[]{elem};
            Object var11_7 = null;
            Object[] x$6 = wrap1_a;
            Object[][] x$8 = VectorStatics$.MODULE$.copyPrepend(this.prefix1(), (T[])this.data2());
            int x$9 = this.length0() + 1;
            Object[] x$10 = this.suffix1();
            int copy_len1 = 1;
            return new Vector2<A>(x$6, copy_len1, x$8, x$10, x$9);
        }
        Object[] wrap1_a = new Object[]{elem};
        Object var13_13 = null;
        Object[] wrap2_x = this.prefix1();
        Object[][] wrap2_a = new Object[][]{wrap2_x};
        Object var14_14 = null;
        Object var15_15 = null;
        return new Vector3(wrap1_a, 1, wrap2_a, this.len1() + 1, VectorStatics$.MODULE$.empty3(), this.data2(), this.suffix1(), this.length0() + 1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Vector<B> map(Function1<A, B> f) {
        Object var28_37;
        Object[] objectArray;
        Object[][] x$2;
        Object[] x$1;
        block25: {
            Object var21_14;
            Object var20_13;
            Object var22_15;
            Object[] objectArray2;
            Object[][] objectArray3;
            block24: {
                void mapElems_a;
                void mapElems_n;
                Object var11_6;
                Object[] objectArray4;
                block20: {
                    Object[] mapElems1_a = this.prefix1();
                    for (int mapElems1_i = 0; mapElems1_i < mapElems1_a.length; ++mapElems1_i) {
                        Object mapElems1_v1 = mapElems1_a[mapElems1_i];
                        B mapElems1_v2 = f.apply(mapElems1_v1);
                        if (mapElems1_v1 == mapElems1_v2) continue;
                        Object[] mapElems1_mapElems1Rest_ac = new Object[mapElems1_a.length];
                        if (mapElems1_i > 0) {
                            System.arraycopy(mapElems1_a, 0, mapElems1_mapElems1Rest_ac, 0, mapElems1_i);
                        }
                        mapElems1_mapElems1Rest_ac[mapElems1_i] = mapElems1_v2;
                        for (int mapElems1_mapElems1Rest_i = mapElems1_i + 1; mapElems1_mapElems1Rest_i < mapElems1_a.length; ++mapElems1_mapElems1Rest_i) {
                            mapElems1_mapElems1Rest_ac[mapElems1_mapElems1Rest_i] = f.apply(mapElems1_a[mapElems1_mapElems1Rest_i]);
                        }
                        objectArray4 = mapElems1_mapElems1Rest_ac;
                        var11_6 = null;
                        break block20;
                    }
                    objectArray4 = mapElems1_a;
                }
                Object var7_2 = null;
                Object var9_4 = null;
                Object var10_5 = null;
                var11_6 = null;
                x$1 = objectArray4;
                objectArray3 = this.data2();
                int n = 2;
                VectorStatics$ mapElems_this = VectorStatics$.MODULE$;
                if (mapElems_n == true) {
                    block21: {
                        for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a).length; ++mapElems_mapElems1_i) {
                            void mapElems_mapElems1_v1 = mapElems_a[mapElems_mapElems1_i];
                            B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                            if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                            Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a).length];
                            if (mapElems_mapElems1_i > 0) {
                                System.arraycopy(mapElems_a, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                            }
                            mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                            for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a[mapElems_mapElems1_mapElems1Rest_i]);
                            }
                            objectArray2 = mapElems_mapElems1_mapElems1Rest_ac;
                            var22_15 = null;
                            break block21;
                        }
                        objectArray2 = mapElems_a;
                    }
                    var20_13 = null;
                    var21_14 = null;
                    var22_15 = null;
                } else {
                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a).length; ++mapElems_i) {
                        Object var36_26;
                        Object var35_25;
                        Object var39_29;
                        Object var38_28;
                        Object var40_30;
                        Object var33_23;
                        void mapElems_v1 = mapElems_a[mapElems_i];
                        T[] mapElems_v2 = mapElems_this.mapElems((int)(mapElems_n - true), (T[])mapElems_v1, f);
                        if (mapElems_v1 == mapElems_v2) continue;
                        Object[] mapElemsRest_ac = (Object[])Array.newInstance(mapElems_a.getClass().getComponentType(), ((void)mapElems_a).length);
                        if (mapElems_i > 0) {
                            System.arraycopy(mapElems_a, 0, mapElemsRest_ac, 0, mapElems_i);
                        }
                        mapElemsRest_ac[mapElems_i] = mapElems_v2;
                        for (int mapElemsRest_i = mapElems_i + 1; mapElemsRest_i < ((void)mapElems_a).length; ++mapElemsRest_i) {
                            Object[] objectArray5;
                            block23: {
                                void mapElemsRest_mapElems_a;
                                var33_23 = mapElems_a[mapElemsRest_i];
                                void mapElemsRest_mapElems_n = mapElems_n - true;
                                if (mapElemsRest_mapElems_n == true) {
                                    block22: {
                                        for (int mapElemsRest_mapElems_mapElems1_i = 0; mapElemsRest_mapElems_mapElems1_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_mapElems1_i) {
                                            void mapElemsRest_mapElems_mapElems1_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_mapElems1_i];
                                            B mapElemsRest_mapElems_mapElems1_v2 = f.apply(mapElemsRest_mapElems_mapElems1_v1);
                                            if (mapElemsRest_mapElems_mapElems1_v1 == mapElemsRest_mapElems_mapElems1_v2) continue;
                                            Object[] mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElemsRest_mapElems_a).length];
                                            if (mapElemsRest_mapElems_mapElems1_i > 0) {
                                                System.arraycopy(mapElemsRest_mapElems_a, 0, mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac, 0, mapElemsRest_mapElems_mapElems1_i);
                                            }
                                            mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac[mapElemsRest_mapElems_mapElems1_i] = mapElemsRest_mapElems_mapElems1_v2;
                                            for (int mapElemsRest_mapElems_mapElems1_mapElems1Rest_i = mapElemsRest_mapElems_mapElems1_i + 1; mapElemsRest_mapElems_mapElems1_mapElems1Rest_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_mapElems1_mapElems1Rest_i) {
                                                mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac[mapElemsRest_mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElemsRest_mapElems_a[mapElemsRest_mapElems_mapElems1_mapElems1Rest_i]);
                                            }
                                            objectArray5 = mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac;
                                            var40_30 = null;
                                            break block22;
                                        }
                                        objectArray5 = mapElemsRest_mapElems_a;
                                    }
                                    var38_28 = null;
                                    var39_29 = null;
                                    var40_30 = null;
                                } else {
                                    for (int mapElemsRest_mapElems_i = 0; mapElemsRest_mapElems_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_i) {
                                        void mapElemsRest_mapElems_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_i];
                                        Object[] mapElemsRest_mapElems_v2 = mapElems_this.mapElems((int)(mapElemsRest_mapElems_n - true), (Object[])mapElemsRest_mapElems_v1, f);
                                        if (mapElemsRest_mapElems_v1 == mapElemsRest_mapElems_v2) continue;
                                        objectArray5 = mapElems_this.mapElemsRest((int)mapElemsRest_mapElems_n, (T[])mapElemsRest_mapElems_a, f, mapElemsRest_mapElems_i, mapElemsRest_mapElems_v2);
                                        break block23;
                                    }
                                    objectArray5 = mapElemsRest_mapElems_a;
                                }
                            }
                            var33_23 = null;
                            var35_25 = null;
                            var36_26 = null;
                            var38_28 = null;
                            var39_29 = null;
                            var40_30 = null;
                            mapElemsRest_ac[mapElemsRest_i] = objectArray5;
                        }
                        objectArray2 = mapElemsRest_ac;
                        Object var30_20 = null;
                        var33_23 = null;
                        var35_25 = null;
                        var36_26 = null;
                        var38_28 = null;
                        var39_29 = null;
                        var40_30 = null;
                        break block24;
                    }
                    objectArray2 = mapElems_a;
                }
            }
            Object var13_11 = null;
            objectArray3 = null;
            Object var17_18 = null;
            Object var18_19 = null;
            var20_13 = null;
            var21_14 = null;
            var22_15 = null;
            x$2 = (Object[][])objectArray2;
            Object[] mapElems1_a = this.suffix1();
            for (int mapElems1_i = 0; mapElems1_i < mapElems1_a.length; ++mapElems1_i) {
                Object mapElems1_v1 = mapElems1_a[mapElems1_i];
                B mapElems1_v2 = f.apply(mapElems1_v1);
                if (mapElems1_v1 == mapElems1_v2) continue;
                Object[] mapElems1_mapElems1Rest_ac = new Object[mapElems1_a.length];
                if (mapElems1_i > 0) {
                    System.arraycopy(mapElems1_a, 0, mapElems1_mapElems1Rest_ac, 0, mapElems1_i);
                }
                mapElems1_mapElems1Rest_ac[mapElems1_i] = mapElems1_v2;
                for (int mapElems1_mapElems1Rest_i = mapElems1_i + 1; mapElems1_mapElems1Rest_i < mapElems1_a.length; ++mapElems1_mapElems1Rest_i) {
                    mapElems1_mapElems1Rest_ac[mapElems1_mapElems1Rest_i] = f.apply(mapElems1_a[mapElems1_mapElems1Rest_i]);
                }
                objectArray = mapElems1_mapElems1Rest_ac;
                var28_37 = null;
                break block25;
            }
            objectArray = mapElems1_a;
        }
        Object var24_33 = null;
        Object var26_35 = null;
        Object var27_36 = null;
        var28_37 = null;
        Object[] x$3 = objectArray;
        int x$4 = this.len1();
        int x$5 = this.length0();
        return new Vector2<A>(x$1, x$4, x$2, x$3, x$5);
    }

    @Override
    public Vector<A> slice0(int lo, int hi) {
        VectorSliceBuilder b = new VectorSliceBuilder(lo, hi);
        b.consider(1, this.prefix1());
        b.consider(2, (T[])this.data2());
        b.consider(1, this.suffix1());
        return b.result();
    }

    @Override
    public Vector<A> tail() {
        if (this.len1() > 1) {
            Object[] copyTail_a = this.prefix1();
            Object var6_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyTail_a, 1, copyTail_a.length);
            int x$2 = this.len1() - 1;
            int x$3 = this.length0() - 1;
            Object[][] x$4 = this.data2();
            Object[] x$5 = this.suffix1();
            return new Vector2<A>(x$1, x$2, x$4, x$5, x$3);
        }
        return this.slice0(1, this.length0());
    }

    @Override
    public Vector<A> init() {
        if (this.suffix1().length > 1) {
            Object[] copyInit_a = this.suffix1();
            Object var6_1 = null;
            Object[] x$1 = Arrays.copyOfRange(copyInit_a, 0, copyInit_a.length - 1);
            int x$2 = this.length0() - 1;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.data2();
            return new Vector2<A>(x$3, x$4, x$5, x$1, x$2);
        }
        return this.slice0(0, this.length0() - 1);
    }

    @Override
    public int vectorSliceCount() {
        return 3;
    }

    @Override
    public Object[] vectorSlice(int idx) {
        switch (idx) {
            case 0: {
                return this.prefix1();
            }
            case 1: {
                return this.data2();
            }
            case 2: {
                return this.suffix1();
            }
        }
        throw new MatchError(idx);
    }

    @Override
    public int vectorSlicePrefixLength(int idx) {
        switch (idx) {
            case 0: {
                return this.len1();
            }
            case 1: {
                return this.length0() - this.suffix1().length;
            }
            case 2: {
                return this.length0();
            }
        }
        throw new MatchError(idx);
    }

    @Override
    public <B> Vector<B> prependedAll0(IterableOnce<B> prefix, int k) {
        Object[] objectArray = VectorStatics$.MODULE$.prepend1IfSpace(this.prefix1(), prefix);
        if (objectArray == null) {
            return super.prependedAll0(prefix, k);
        }
        int diff = objectArray.length - this.prefix1().length;
        int x$2 = this.len1() + diff;
        int x$3 = this.length0() + diff;
        Object[][] x$4 = this.data2();
        Object[] x$5 = this.suffix1();
        return new Vector2<A>(objectArray, x$2, x$4, x$5, x$3);
    }

    @Override
    public <B> Vector<B> appendedAll0(IterableOnce<B> suffix, int k) {
        Object[] suffix1b = VectorStatics$.MODULE$.append1IfSpace(this.suffix1(), suffix);
        if (suffix1b != null) {
            int x$2 = this.length0() - this.suffix1().length + suffix1b.length;
            Object[] x$3 = this.prefix1();
            int x$4 = this.len1();
            Object[][] x$5 = this.data2();
            return new Vector2<A>(x$3, x$4, x$5, suffix1b, x$2);
        }
        return super.appendedAll0(suffix, k);
    }

    public Vector2(Object[] _prefix1, int len1, Object[][] data2, Object[] _suffix1, int _length0) {
        this.len1 = len1;
        this.data2 = data2;
        super(_prefix1, _suffix1, _length0);
    }
}

