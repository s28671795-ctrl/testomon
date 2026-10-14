/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Arrays;
import scala.MatchError;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector0$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.Vector2;
import scala.collection.immutable.Vector3;
import scala.collection.immutable.Vector4;
import scala.collection.immutable.Vector5;
import scala.collection.immutable.Vector6;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015c\u0001B\u000b\u0017\ruA\u0001B\t\u0001\u0003\u0002\u0003\u0006Ia\t\u0005\tM\u0001\u0011\t\u0011)A\u0005G!)q\u0005\u0001C\u0001Q!aQ\u0006\u0001C\u0001\u0002\u000b\u0005\t\u0011)A\u0005]!1!\u0007\u0001Q!\n\rBaa\r\u0001!B\u0013\u0019\u0003B\u0002\u001b\u0001A\u0003&1\u0005\u0003\u00076\u0001\u0011\u0005\tQ!A\u0001B\u0013%a\u0007\u0003\u0007>\u0001\u0011\u0005\tQ!A\u0001B\u0013%a\bC\u0003B\u0001\u0011\u0005!\t\u0003\u0004T\u0001\u0001&I\u0001\u0016\u0005\u0007;\u0002\u0001K\u0011\u00020\t\u000b\u0015\u0004A\u0011\u00014\t\rI\u0004\u0001\u0015\"\u0003t\u0011\u0019Y\b\u0001)C\u0005y\"A\u0011\u0011\u0002\u0001!\n\u0013\tY\u0001\u0003\u0005\u0002\u001c\u0001\u0001K\u0011BA\u000f\u0011!\t\t\u0003\u0001Q\u0005\n\u0005\r\u0002bBA\u0014\u0001\u0011\u0005\u0013\u0011\u0006\u0005\t\u0003\u0003\u0002A\u0011\u0001\f\u0002D\t\u0011b+Z2u_J\u001cF.[2f\u0005VLG\u000eZ3s\u0015\t9\u0002$A\u0005j[6,H/\u00192mK*\u0011\u0011DG\u0001\u000bG>dG.Z2uS>t'\"A\u000e\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001A\b\t\u0003?\u0001j\u0011AG\u0005\u0003Ci\u0011a!\u00118z%\u00164\u0017A\u00017p!\tyB%\u0003\u0002&5\t\u0019\u0011J\u001c;\u0002\u0005!L\u0017A\u0002\u001fj]&$h\bF\u0002*W1\u0002\"A\u000b\u0001\u000e\u0003YAQAI\u0002A\u0002\rBQAJ\u0002A\u0002\r\nQg]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%S6lW\u000f^1cY\u0016$c+Z2u_J\u001cF.[2f\u0005VLG\u000eZ3sI\u0011\u001aH.[2fgB\u0019qdL\u0019\n\u0005AR\"!B!se\u0006L\bcA\u00100=\u0005\u0019A.\u001a8\u0002\u0007A|7/\u0001\u0004nCb$\u0015.\\\u00019g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$\u0013.\\7vi\u0006\u0014G.\u001a\u0013WK\u000e$xN]*mS\u000e,')^5mI\u0016\u0014H\u0005\n9sK\u001aL\u00070\u00133y)\t\u0019s\u0007C\u00039\u0011\u0001\u00071%A\u0001oQ\tA!\b\u0005\u0002 w%\u0011AH\u0007\u0002\u0007S:d\u0017N\\3\u0002qM\u001c\u0017\r\\1%G>dG.Z2uS>tG%[7nkR\f'\r\\3%-\u0016\u001cGo\u001c:TY&\u001cWMQ;jY\u0012,'\u000f\n\u0013tk\u001a4\u0017\u000e_%eqR\u00111e\u0010\u0005\u0006q%\u0001\ra\t\u0015\u0003\u0013i\n\u0001bY8og&$WM]\u000b\u0003\u00076#2\u0001R$I!\tyR)\u0003\u0002G5\t!QK\\5u\u0011\u0015A$\u00021\u0001$\u0011\u0015I%\u00021\u0001K\u0003\u0005\t\u0007cA\u00100\u0017B\u0011A*\u0014\u0007\u0001\t\u0015q%B1\u0001P\u0005\u0005!\u0016C\u0001)\u001f!\ty\u0012+\u0003\u0002S5\t9aj\u001c;iS:<\u0017\u0001C1eINc\u0017nY3\u0016\u0005USF#\u0002#W/nc\u0006\"\u0002\u001d\f\u0001\u0004\u0019\u0003\"B%\f\u0001\u0004A\u0006cA\u001003B\u0011AJ\u0017\u0003\u0006\u001d.\u0011\ra\u0014\u0005\u0006E-\u0001\ra\t\u0005\u0006M-\u0001\raI\u0001\u0004C\u0012$WCA0e)\r!\u0005-\u0019\u0005\u0006q1\u0001\ra\t\u0005\u0006\u00132\u0001\rA\u0019\t\u0004?=\u001a\u0007C\u0001'e\t\u0015qEB1\u0001P\u0003\u0019\u0011Xm];miV\u0011q\r\u001c\u000b\u0002QB\u0019!&[6\n\u0005)4\"A\u0002,fGR|'\u000f\u0005\u0002MY\u0012)Q.\u0004b\u0001]\n\t\u0011)\u0005\u0002Q_B\u0011q\u0004]\u0005\u0003cj\u00111!\u00118z\u0003!\u0001(/\u001a4jq>\u0013XC\u0001;x)\r)\b0\u001f\t\u0004?=2\bC\u0001'x\t\u0015qeB1\u0001P\u0011\u0015Ad\u00021\u0001$\u0011\u0015Ie\u00021\u0001vQ\tq!(\u0001\u0005tk\u001a4\u0017\u000e_(s+\ri\u0018\u0011\u0001\u000b\u0006}\u0006\r\u0011Q\u0001\t\u0004?=z\bc\u0001'\u0002\u0002\u0011)aj\u0004b\u0001\u001f\")\u0001h\u0004a\u0001G!)\u0011j\u0004a\u0001}\"\u0012qBO\u0001\u0007I\u0006$\u0018m\u0014:\u0016\t\u00055\u00111\u0003\u000b\u0007\u0003\u001f\t)\"a\u0006\u0011\t}y\u0013\u0011\u0003\t\u0004\u0019\u0006MA!\u0002(\u0011\u0005\u0004y\u0005\"\u0002\u001d\u0011\u0001\u0004\u0019\u0003BB%\u0011\u0001\u0004\ty\u0001\u000b\u0002\u0011u\u0005i!-\u00197b]\u000e,\u0007K]3gSb$2\u0001RA\u0010\u0011\u0015A\u0014\u00031\u0001$\u00035\u0011\u0017\r\\1oG\u0016\u001cVO\u001a4jqR\u0019A)!\n\t\u000ba\u0012\u0002\u0019A\u0012\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u000b\u0011\t\u00055\u00121\b\b\u0005\u0003_\t9\u0004E\u0002\u00022ii!!a\r\u000b\u0007\u0005UB$\u0001\u0004=e>|GOP\u0005\u0004\u0003sQ\u0012A\u0002)sK\u0012,g-\u0003\u0003\u0002>\u0005}\"AB*ue&twMC\u0002\u0002:i\t\u0011bZ3u'2L7-Z:\u0016\u00039\u0002")
public final class VectorSliceBuilder {
    private final int lo;
    private final int hi;
    public final Object[][] scala$collection$immutable$VectorSliceBuilder$$slices;
    private int len;
    private int pos;
    private int maxDim;

    public int scala$collection$immutable$VectorSliceBuilder$$prefixIdx(int n) {
        return n - 1;
    }

    public int scala$collection$immutable$VectorSliceBuilder$$suffixIdx(int n) {
        return 11 - n;
    }

    public <T> void consider(int n, T[] a) {
        int count = a.length * (1 << 5 * (n - 1));
        int lo0 = Math.max(this.lo - this.pos, 0);
        int hi0 = Math.min(this.hi - this.pos, count);
        if (hi0 > lo0) {
            this.addSlice(n, a, lo0, hi0);
            this.len += hi0 - lo0;
        }
        this.pos += count;
    }

    private <T> void addSlice(int n, T[] a, int lo, int hi) {
        while (true) {
            if (n == 1) {
                this.add(1, lo == 0 && hi == a.length ? a : Arrays.copyOfRange(a, lo, hi));
                return;
            }
            int bitsN = 5 * (n - 1);
            int widthN = 1 << bitsN;
            int loN = lo >>> bitsN;
            int hiN = hi >>> bitsN;
            int loRest = lo & widthN - 1;
            int hiRest = hi & widthN - 1;
            if (loRest == 0) {
                if (hiRest == 0) {
                    this.add(n, loN == 0 && hiN == a.length ? a : Arrays.copyOfRange(a, loN, hiN));
                    return;
                }
                if (hiN > loN) {
                    this.add(n, loN == 0 && hiN == a.length ? a : Arrays.copyOfRange(a, loN, hiN));
                }
                hi = hiRest;
                lo = 0;
                a = (Object[])a[hiN];
                --n;
                continue;
            }
            if (hiN == loN) {
                hi = hiRest;
                lo = loRest;
                a = (Object[])a[loN];
                --n;
                continue;
            }
            this.addSlice(n - 1, (Object[])a[loN], loRest, widthN);
            if (hiRest == 0) {
                if (hiN <= loN + 1) break;
                int copyOrUse_start = loN + 1;
                this.add(n, copyOrUse_start == 0 && hiN == a.length ? a : Arrays.copyOfRange(a, copyOrUse_start, hiN));
                return;
            }
            if (hiN > loN + 1) {
                int copyOrUse_start = loN + 1;
                this.add(n, copyOrUse_start == 0 && hiN == a.length ? a : Arrays.copyOfRange(a, copyOrUse_start, hiN));
            }
            hi = hiRest;
            lo = 0;
            a = (Object[])a[hiN];
            --n;
        }
    }

    private <T> void add(int n, T[] a) {
        int n2;
        if (n <= this.maxDim) {
            n2 = 11 - n;
        } else {
            this.maxDim = n;
            n2 = n - 1;
        }
        int idx = n2;
        this.scala$collection$immutable$VectorSliceBuilder$$slices[idx] = a;
    }

    /*
     * WARNING - void declaration
     */
    public <A> Vector<A> result() {
        if (this.len <= 32) {
            Object[] objectArray;
            if (this.len == 0) {
                return Vector0$.MODULE$;
            }
            Object[] prefix1 = this.scala$collection$immutable$VectorSliceBuilder$$slices[1 - 1];
            int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n = 1;
            Object[] suffix1 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n];
            if (prefix1 != null) {
                if (suffix1 != null) {
                    Object[] concatArrays_dest = Arrays.copyOf(prefix1, prefix1.length + suffix1.length);
                    System.arraycopy(suffix1, 0, concatArrays_dest, prefix1.length, suffix1.length);
                    objectArray = concatArrays_dest;
                    Object var47_4 = null;
                } else {
                    objectArray = prefix1;
                }
            } else if (suffix1 != null) {
                objectArray = suffix1;
            } else {
                Object[][] prefix2 = (Object[][])this.scala$collection$immutable$VectorSliceBuilder$$slices[2 - 1];
                if (prefix2 != null) {
                    objectArray = prefix2[0];
                } else {
                    int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n2 = 2;
                    objectArray = ((Object[][])this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n2])[0];
                }
            }
            Object[] a = objectArray;
            return new Vector1(a);
        }
        this.balancePrefix(1);
        this.balanceSuffix(1);
        int resultDim = this.maxDim;
        if (resultDim < 6) {
            Object[] pre = this.scala$collection$immutable$VectorSliceBuilder$$slices[this.maxDim - 1];
            int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n = this.maxDim;
            Object[] suf = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n];
            if (pre != null && suf != null) {
                if (pre.length + suf.length <= 30) {
                    Object[] concatArrays_dest = Arrays.copyOf(pre, pre.length + suf.length);
                    System.arraycopy(suf, 0, concatArrays_dest, pre.length, suf.length);
                    Object var50_12 = null;
                    this.scala$collection$immutable$VectorSliceBuilder$$slices[this.maxDim - 1] = concatArrays_dest;
                    int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n3 = this.maxDim;
                    this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n3] = null;
                } else {
                    ++resultDim;
                }
            } else if ((pre != null ? pre : suf).length > 30) {
                ++resultDim;
            }
        }
        Object[] prefix1 = this.scala$collection$immutable$VectorSliceBuilder$$slices[1 - 1];
        int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n = 1;
        Object[] suffix1 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n];
        int len1 = prefix1.length;
        switch (resultDim) {
            case 2: {
                void dataOr_a;
                Object[] dataOr_s;
                Object[][] objectArray = VectorStatics$.MODULE$.empty2();
                int dataOr_n = 2;
                Object[] dataOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[dataOr_n - 1];
                objectArray = null;
                Object var55_20 = null;
                Object var56_21 = null;
                Object[][] data2 = (Object[][])(dataOr_p != null ? dataOr_p : ((dataOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - dataOr_n]) != null ? dataOr_s : dataOr_a));
                return new Vector2(prefix1, len1, data2, suffix1, this.len);
            }
            case 3: {
                void suffixOr_a;
                void dataOr_a;
                Object[] dataOr_s;
                void prefixOr_a;
                Object[][] objectArray = VectorStatics$.MODULE$.empty2();
                int prefixOr_n = 2;
                Object[] prefixOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n - 1];
                objectArray = null;
                Object var59_25 = null;
                Object[][] prefix2 = (Object[][])(prefixOr_p != null ? prefixOr_p : prefixOr_a);
                Object[][][] objectArray2 = VectorStatics$.MODULE$.empty3();
                int dataOr_n = 3;
                Object[] dataOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[dataOr_n - 1];
                objectArray2 = null;
                Object var62_29 = null;
                Object var63_30 = null;
                Object[][][] data3 = (Object[][][])(dataOr_p != null ? dataOr_p : ((dataOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - dataOr_n]) != null ? dataOr_s : dataOr_a));
                Object[][] objectArray3 = VectorStatics$.MODULE$.empty2();
                int suffixOr_n = 2;
                Object[] suffixOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n];
                objectArray3 = null;
                Object var66_34 = null;
                Object[][] suffix2 = (Object[][])(suffixOr_s != null ? suffixOr_s : suffixOr_a);
                int len12 = len1 + prefix2.length * 32;
                return new Vector3(prefix1, len1, prefix2, len12, data3, suffix2, suffix1, this.len);
            }
            case 4: {
                void suffixOr_a;
                void suffixOr_a2;
                void dataOr_a;
                Object[] dataOr_s;
                void prefixOr_a;
                void prefixOr_a2;
                Object[][] objectArray = VectorStatics$.MODULE$.empty2();
                int prefixOr_n = 2;
                Object[] prefixOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n - 1];
                objectArray = null;
                Object var69_39 = null;
                Object[][] prefix2 = (Object[][])(prefixOr_p != null ? prefixOr_p : prefixOr_a2);
                Object[][][] objectArray4 = VectorStatics$.MODULE$.empty3();
                int prefixOr_n2 = 3;
                Object[] prefixOr_p2 = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n2 - 1];
                objectArray4 = null;
                Object var72_43 = null;
                Object[][][] prefix3 = (Object[][][])(prefixOr_p2 != null ? prefixOr_p2 : prefixOr_a);
                Object[][][][] objectArray5 = VectorStatics$.MODULE$.empty4();
                int dataOr_n = 4;
                Object[] dataOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[dataOr_n - 1];
                objectArray5 = null;
                Object var75_47 = null;
                Object var76_48 = null;
                Object[][][][] data4 = (Object[][][][])(dataOr_p != null ? dataOr_p : ((dataOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - dataOr_n]) != null ? dataOr_s : dataOr_a));
                Object[][][] objectArray6 = VectorStatics$.MODULE$.empty3();
                int suffixOr_n = 3;
                Object[] suffixOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n];
                objectArray6 = null;
                Object var79_52 = null;
                Object[][][] suffix3 = (Object[][][])(suffixOr_s != null ? suffixOr_s : suffixOr_a2);
                Object[][] objectArray7 = VectorStatics$.MODULE$.empty2();
                int suffixOr_n2 = 2;
                Object[] suffixOr_s2 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n2];
                objectArray7 = null;
                Object var82_56 = null;
                Object[][] suffix2 = (Object[][])(suffixOr_s2 != null ? suffixOr_s2 : suffixOr_a);
                int len12 = len1 + prefix2.length * 32;
                int len123 = len12 + prefix3.length * 1024;
                return new Vector4(prefix1, len1, prefix2, len12, prefix3, len123, data4, suffix3, suffix2, suffix1, this.len);
            }
            case 5: {
                void suffixOr_a;
                void suffixOr_a3;
                void suffixOr_a4;
                void dataOr_a;
                Object[] dataOr_s;
                void prefixOr_a;
                void prefixOr_a3;
                void prefixOr_a4;
                Object[][] objectArray = VectorStatics$.MODULE$.empty2();
                int prefixOr_n = 2;
                Object[] prefixOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n - 1];
                objectArray = null;
                Object var85_62 = null;
                Object[][] prefix2 = (Object[][])(prefixOr_p != null ? prefixOr_p : prefixOr_a4);
                Object[][][] objectArray8 = VectorStatics$.MODULE$.empty3();
                int prefixOr_n3 = 3;
                Object[] prefixOr_p3 = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n3 - 1];
                objectArray8 = null;
                Object var88_66 = null;
                Object[][][] prefix3 = (Object[][][])(prefixOr_p3 != null ? prefixOr_p3 : prefixOr_a3);
                Object[][][][] objectArray9 = VectorStatics$.MODULE$.empty4();
                int prefixOr_n4 = 4;
                Object[] prefixOr_p4 = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n4 - 1];
                objectArray9 = null;
                Object var91_70 = null;
                Object[][][][] prefix4 = (Object[][][][])(prefixOr_p4 != null ? prefixOr_p4 : prefixOr_a);
                Object[][][][][] objectArray10 = VectorStatics$.MODULE$.empty5();
                int dataOr_n = 5;
                Object[] dataOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[dataOr_n - 1];
                objectArray10 = null;
                Object var94_74 = null;
                Object var95_75 = null;
                Object[][][][][] data5 = (Object[][][][][])(dataOr_p != null ? dataOr_p : ((dataOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - dataOr_n]) != null ? dataOr_s : dataOr_a));
                Object[][][][] objectArray11 = VectorStatics$.MODULE$.empty4();
                int suffixOr_n = 4;
                Object[] suffixOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n];
                objectArray11 = null;
                Object var98_79 = null;
                Object[][][][] suffix4 = (Object[][][][])(suffixOr_s != null ? suffixOr_s : suffixOr_a4);
                Object[][][] objectArray12 = VectorStatics$.MODULE$.empty3();
                int suffixOr_n3 = 3;
                Object[] suffixOr_s3 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n3];
                objectArray12 = null;
                Object var101_83 = null;
                Object[][][] suffix3 = (Object[][][])(suffixOr_s3 != null ? suffixOr_s3 : suffixOr_a3);
                Object[][] objectArray13 = VectorStatics$.MODULE$.empty2();
                int suffixOr_n4 = 2;
                Object[] suffixOr_s4 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n4];
                objectArray13 = null;
                Object var104_87 = null;
                Object[][] suffix2 = (Object[][])(suffixOr_s4 != null ? suffixOr_s4 : suffixOr_a);
                int len12 = len1 + prefix2.length * 32;
                int len123 = len12 + prefix3.length * 1024;
                int len1234 = len123 + prefix4.length * 32768;
                return new Vector5(prefix1, len1, prefix2, len12, prefix3, len123, prefix4, len1234, data5, suffix4, suffix3, suffix2, suffix1, this.len);
            }
            case 6: {
                void suffixOr_a;
                void suffixOr_a5;
                void suffixOr_a6;
                void suffixOr_a7;
                void dataOr_a;
                Object[] dataOr_s;
                void prefixOr_a;
                void prefixOr_a5;
                void prefixOr_a6;
                void prefixOr_a7;
                Object[][] objectArray = VectorStatics$.MODULE$.empty2();
                int prefixOr_n = 2;
                Object[] prefixOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n - 1];
                objectArray = null;
                Object var107_94 = null;
                Object[][] prefix2 = (Object[][])(prefixOr_p != null ? prefixOr_p : prefixOr_a7);
                Object[][][] objectArray14 = VectorStatics$.MODULE$.empty3();
                int prefixOr_n5 = 3;
                Object[] prefixOr_p5 = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n5 - 1];
                objectArray14 = null;
                Object var110_98 = null;
                Object[][][] prefix3 = (Object[][][])(prefixOr_p5 != null ? prefixOr_p5 : prefixOr_a6);
                Object[][][][] objectArray15 = VectorStatics$.MODULE$.empty4();
                int prefixOr_n6 = 4;
                Object[] prefixOr_p6 = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n6 - 1];
                objectArray15 = null;
                Object var113_102 = null;
                Object[][][][] prefix4 = (Object[][][][])(prefixOr_p6 != null ? prefixOr_p6 : prefixOr_a5);
                Object[][][][][] objectArray16 = VectorStatics$.MODULE$.empty5();
                int prefixOr_n7 = 5;
                Object[] prefixOr_p7 = this.scala$collection$immutable$VectorSliceBuilder$$slices[prefixOr_n7 - 1];
                objectArray16 = null;
                Object var116_106 = null;
                Object[][][][][] prefix5 = (Object[][][][][])(prefixOr_p7 != null ? prefixOr_p7 : prefixOr_a);
                Object[][][][][][] objectArray17 = VectorStatics$.MODULE$.empty6();
                int dataOr_n = 6;
                Object[] dataOr_p = this.scala$collection$immutable$VectorSliceBuilder$$slices[dataOr_n - 1];
                objectArray17 = null;
                Object var119_110 = null;
                Object var120_111 = null;
                Object[][][][][][] data6 = (Object[][][][][][])(dataOr_p != null ? dataOr_p : ((dataOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - dataOr_n]) != null ? dataOr_s : dataOr_a));
                Object[][][][][] objectArray18 = VectorStatics$.MODULE$.empty5();
                int suffixOr_n = 5;
                Object[] suffixOr_s = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n];
                objectArray18 = null;
                Object var123_115 = null;
                Object[][][][][] suffix5 = (Object[][][][][])(suffixOr_s != null ? suffixOr_s : suffixOr_a7);
                Object[][][][] objectArray19 = VectorStatics$.MODULE$.empty4();
                int suffixOr_n5 = 4;
                Object[] suffixOr_s5 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n5];
                objectArray19 = null;
                Object var126_119 = null;
                Object[][][][] suffix4 = (Object[][][][])(suffixOr_s5 != null ? suffixOr_s5 : suffixOr_a6);
                Object[][][] objectArray20 = VectorStatics$.MODULE$.empty3();
                int suffixOr_n6 = 3;
                Object[] suffixOr_s6 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n6];
                objectArray20 = null;
                Object var129_123 = null;
                Object[][][] suffix3 = (Object[][][])(suffixOr_s6 != null ? suffixOr_s6 : suffixOr_a5);
                Object[][] objectArray21 = VectorStatics$.MODULE$.empty2();
                int suffixOr_n7 = 2;
                Object[] suffixOr_s7 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - suffixOr_n7];
                objectArray21 = null;
                Object var132_127 = null;
                Object[][] suffix2 = (Object[][])(suffixOr_s7 != null ? suffixOr_s7 : suffixOr_a);
                int len12 = len1 + prefix2.length * 32;
                int len123 = len12 + prefix3.length * 1024;
                int len1234 = len123 + prefix4.length * 32768;
                int len12345 = len1234 + prefix5.length * 0x100000;
                return new Vector6(prefix1, len1, prefix2, len12, prefix3, len123, prefix4, len1234, prefix5, len12345, data6, suffix5, suffix4, suffix3, suffix2, suffix1, this.len);
            }
        }
        throw new MatchError(resultDim);
    }

    private <T> T[] prefixOr(int n, T[] a) {
        Object[] p = this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1];
        if (p != null) {
            return p;
        }
        return a;
    }

    private <T> T[] suffixOr(int n, T[] a) {
        Object[] s2 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n];
        if (s2 != null) {
            return s2;
        }
        return a;
    }

    private <T> T[] dataOr(int n, T[] a) {
        Object[] p = this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1];
        if (p != null) {
            return p;
        }
        Object[] s2 = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n];
        if (s2 != null) {
            return s2;
        }
        return a;
    }

    private void balancePrefix(int n) {
        if (this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1] == null) {
            if (n == this.maxDim) {
                this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1] = this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n];
                this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n] = null;
                return;
            }
            this.balancePrefix(n + 1);
            Object[][] preN1 = (Object[][])this.scala$collection$immutable$VectorSliceBuilder$$slices[n + 1 - 1];
            this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1] = preN1[0];
            if (preN1.length == 1) {
                int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n;
                this.scala$collection$immutable$VectorSliceBuilder$$slices[n + 1 - 1] = null;
                if (this.maxDim == n + 1 && this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - (scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n = n + 1)] == null) {
                    this.maxDim = n;
                    return;
                }
                return;
            }
            this.scala$collection$immutable$VectorSliceBuilder$$slices[n + 1 - 1] = Arrays.copyOfRange(preN1, 1, preN1.length);
            return;
        }
    }

    private void balanceSuffix(int n) {
        if (this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n] == null) {
            if (n == this.maxDim) {
                this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n] = this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1];
                this.scala$collection$immutable$VectorSliceBuilder$$slices[n - 1] = null;
                return;
            }
            this.balanceSuffix(n + 1);
            int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n = n + 1;
            Object[][] sufN1 = (Object[][])this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n];
            this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - n] = sufN1[sufN1.length - 1];
            if (sufN1.length == 1) {
                int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n2 = n + 1;
                this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n2] = null;
                if (this.maxDim == n + 1 && this.scala$collection$immutable$VectorSliceBuilder$$slices[n + 1 - 1] == null) {
                    this.maxDim = n;
                    return;
                }
                return;
            }
            int scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n3 = n + 1;
            this.scala$collection$immutable$VectorSliceBuilder$$slices[11 - scala$collection$immutable$VectorSliceBuilder$$suffixIdx_n3] = Arrays.copyOfRange(sufN1, 0, sufN1.length - 1);
            return;
        }
    }

    public String toString() {
        return new StringBuilder(49).append("VectorSliceBuilder(lo=").append(this.lo).append(", hi=").append(this.hi).append(", len=").append(this.len).append(", pos=").append(this.pos).append(", maxDim=").append(this.maxDim).append(")").toString();
    }

    public Object[][] getSlices() {
        return this.scala$collection$immutable$VectorSliceBuilder$$slices;
    }

    public VectorSliceBuilder(int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.scala$collection$immutable$VectorSliceBuilder$$slices = new Object[11][];
        this.len = 0;
        this.pos = 0;
        this.maxDim = 0;
    }
}

